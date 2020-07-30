import json
import time
import grpc
from concurrent import futures
from base64 import b64encode

from zhihu_oauth import ZhihuClient, ActType, ts2str, act2str
from zhihu_oauth.exception import NeedCaptchaException
import os
import zhihu_pb2
import zhihu_pb2_grpc
# patch a buggy function
import zhihu_oauth.zhcls.activity
from zhihu_oauth.zhcls.normal import normal_attr
from zhihu_oauth.zhcls.streaming import StreamingJSON
from zhihu_oauth.zhcls.utils import build_zhihu_obj_from_dict, get_class_from_name, SimpleEnum
from zhihu_oauth.exception import UnimplementedException
from zhihu_oauth.zhcls.collection import Collection

def my_get_target(self):
        pos = self._type.rfind('_')
        if pos == -1:
            raise UnimplementedException('Unable to get class from type name')
        filename = self._type[pos + 1:].lower()
        class_name = filename.capitalize()

        obj_cls = get_class_from_name(class_name, filename)
        self._target = build_zhihu_obj_from_dict(
            self._data['target'], self._session, cls=obj_cls
        )
        # 对收藏答案类型的特殊处理
        if self._type in {ActType.COLLECT_ANSWER, ActType.COLLECT_ARTICLE}:
            
            obj = self._target
            collection = build_zhihu_obj_from_dict(
                self._data['target'], self._session,
                cls=Collection,
            )
            self._target = {
                'collection': collection,
                class_name.lower(): obj,
            }
# patch complete here

small_prime = 300001573


class Question:

    def __init__(self):
        self.id = 0,
        self.title = ""
        self.create_time = 0
        self.update_time = 0
        self.excerpt = ""
        self.content = ""
        self.answer_count = 0
        self.answers = []

    def setValue(self, question):
        self.id = question.id,
        self.title = question.title
        self.content = question.detail
        self.excerpt = question.excerpt
        self.create_time = question.created_time
        self.update_time = question.updated_time
        self.answer_count = question.answer_count


class Answer:

    def __init__(self):
        self.id = 0,
        self.author = ""
        self.create_time = 0
        self.update_time = 0
        self.excerpt = ""
        self.content = ""
        self.voteup_count = 0
        self.comment_count = 0
        self.question = Question()

    def setValue(self, answer):
        self.id = answer.id,
        self.author = answer.author.name
        self.content = answer.content
        self.excerpt = answer.excerpt
        self.create_time = answer.created_time
        self.update_time = answer.updated_time
        self.voteup_count = answer.voteup_count
        self.comment_count = answer.comment_count


class Article:

    def __init__(self):
        self.id = 0,
        self.title = ""
        self.author = ""
        self.update_time = 0
        self.excerpt = ""
        self.content = ""
        self.image_url = ""
        self.column_name = ""

    def setValue(self, article):
        self.id = article.id,
        self.title = article.title
        self.author = article.author.name
        self.content = article.content
        self.excerpt = article.excerpt
        self.update_time = article.updated_time
        self.image_url = article.image_url
        if article.column != None:
            self.column_name = article.column.title
        else:
            self.column_name = ""

class Activity:
    def __init__(self,type, action_text, create_time):
        self.id = ""
        self.type = type
        self.action_text = action_text
        self.create_time = create_time
        if type == ActType.CREATE_ANSWER or type == ActType.VOTEUP_ANSWER:
            self.answer = Answer()
        if type == ActType.CREATE_QUESTION or type == ActType.FOLLOW_QUESTION:
            self.question = Question()
        if type == ActType.CREATE_ARTICLE or type == ActType.VOTEUP_ARTICLE:
            self.article = Article()


class User:
    def __init__(self):
        self.uid = ""
        self.name = ""
        self.email = ""
        self.phone = ""
        self.answer_count = 0
        self.gender = -1
        self.thanked_count = 0
        self.voteup_count = 0

    def setValue(self, me):
        self.uid = me.uid
        self.name = me.name
        self.email = me.email
        self.phone = me.phone_no
        self.gender = me.gender 
        self.answer_count = me.answer_count
        self.thanked_count = me.thanked_count
        self.voteup_count = me.voteup_count


class ZhihuService(zhihu_pb2_grpc.ZhihuServiceServicer):
    clientpool = {}
    userpool = {}

    def Login(self, request, context):
        username = request.username
        password = request.password
        captcha = request.captcha
        print("login request: " + username, ", ", password, ", ", captcha)
        client = self.clientpool.get(username)
        if client == None:
            client = ZhihuClient()
            self.clientpool[username] = client
        tokenPath = './tokens/' + username + '.pkl'
        if os.path.isfile(tokenPath):
            client.load_token(tokenPath)
            return zhihu_pb2.LoginResponse(response="success")

        if captcha == "":
            try:
                success, err = client.login(username, password)
                print(success, ",", err)
            except NeedCaptchaException:
                with open('a.gif', 'wb') as f:
                    f.write(client.get_captcha())
                    base64_bytes = b64encode(client.get_captcha())
                return zhihu_pb2.LoginResponse(response=base64_bytes)
            client.save_token('./tokens/' + username + '.pkl')
            self.userpool[username] = 1
        else:
            try:
                print("try with captcha:"+captcha+"---")
                success, err = client.login(username, password, captcha)
                print(success, ",", err)
            except NeedCaptchaException:
                with open('a.gif', 'wb') as f:
                    f.write(client.get_captcha())
                return zhihu_pb2.LoginResponse(response="wrong captcha")
            if success:
                client.save_token('./tokens/' + username + '.pkl')
                self.userpool[username] = 1
                return zhihu_pb2.LoginResponse(response="success")
            else:
                return zhihu_pb2.LoginResponse(response=err)

    def GenActivityJson(self, me):
        return json.dumps(self.GetActivityDict(me), default=lambda o: o.__dict__, sort_keys=True, indent=4)

    def GetActivityDict(self, me):
        result = []
        filter_types = {
            ActType.VOTEUP_ANSWER,
            ActType.VOTEUP_ARTICLE,
            ActType.FOLLOW_QUESTION,
            ActType.CREATE_QUESTION,
            ActType.CREATE_ANSWER,
            ActType.CREATE_ARTICLE,
        }

        for act in me.activities.filter(filter_types):
            # print(act.type,flush=True)
            activity = Activity(act.type, act2str(act), act.created_time)
            # print(act.target.id, ",", act.type, ",", type(act.target.id))
            print(ts2str(act.created_time),act2str(act))
            activity.id = str(act.created_time) + str((hash(act.action_text) % small_prime))
            # print(activity.id)
            if activity.type == ActType.CREATE_ANSWER or activity.type == ActType.VOTEUP_ANSWER:
                activity.answer.setValue(act.target)
                # activity.answer.author = act.target.author.name
                # activity.answer.content = act.target.content
                # activity.answer.excerpt = act.target.excerpt
                # activity.answer.create_time = act.target.created_time
                # activity.answer.udpate_time = act.target.updated_time
                # activity.answer.voteup_count = act.target.voteup_count
                # activity.answer.comment_count = act.target.comment_count
                activity.answer.question.setValue(act.target.question)

                # question = act.target.question
                # activity.answer.question.title = question.title
                # activity.answer.question.answer_count = question.answer_count
                # activity.answer.question.content = question.detail
                # activity.answer.question.excerp = question.excerpt
                # activity.answer.question.create_time = question.created_time
                # activity.answer.question.upate_time = question.updated_time
            if activity.type == ActType.CREATE_QUESTION or activity.type == ActType.FOLLOW_QUESTION:
                activity.question.setValue(act.target)
                # activity.question.title = act.target.title
                # activity.question.content = act.question.content
                # activity.question.excerpt = act.question.excerpt
                # activity.question.create_time = act.question.created_time
                # activity.question.udpate_time = act.question.updated_time
                # activity.question.answer_count = act.target.answer_count
                counter = 10 # only load first 10 answers into database
                for answer in act.target.answers:
                    ans = Answer()
                    ans.setValue(answer)
                    activity.question.answers.append(ans)
                    counter = counter - 1
                    if counter == 0:
                        break
            if activity.type == ActType.CREATE_ARTICLE or activity.type == ActType.VOTEUP_ARTICLE:
                activity.article.setValue(act.target)
            result.append(activity)
            if len(result) > 30:
                break
        return result

    def GetActivity(self, request, context):
        username = request.username
        client = self.clientpool.get(username)
        if client == None:
            return zhihu_pb2.ActivityResponse("not login")
        me = client.me()
        json_object = self.GenActivityJson(me)
        # f = open("test.txt","w")
        # f.write(json_object)
        # f.close()
        return zhihu_pb2.ActivityResponse(responseJson=json_object)
    
    def GenUserJson(self, user):
        return json.dumps(user, default=lambda o: o.__dict__, sort_keys=True, indent=4)

    def GetUserInfo(self, request, context):
        username = request.username
        client = self.clientpool.get(username)
        if client == None:
            return zhihu_pb2.ActivityResponse("not login")
        me = client.me()
        user = User()
        user.setValue(me)
        json_object = self.GenUserJson(user)
        return zhihu_pb2.UserinfoResponse(responseJson = json_object)

_ONE_DAY_IN_SECONDS = 60 * 60 * 24
ip = "0.0.0.0"
port = "8103"
serverString = ip + ":" + port

def serve():
    if not os.path.exists("./tokens"):
        os.makedirs("./tokens")
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    zhihu_pb2_grpc.add_ZhihuServiceServicer_to_server(ZhihuService(), server)
    server.add_insecure_port(serverString)
    server.start()
    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)


if __name__ == '__main__':

    zhihu_oauth.zhcls.activity.Activity._get_target = my_get_target ## reload buggy function
    print("server running. at port 8103\n")
    serve()
