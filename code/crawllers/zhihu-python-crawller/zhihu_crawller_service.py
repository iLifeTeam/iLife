import json
import time
import grpc
import jsonpickle
from concurrent import futures

from zhihu_oauth import ZhihuClient,ActType
from zhihu_oauth.exception import NeedCaptchaException
import os 
import zhihu_pb2
import zhihu_pb2_grpc
class MyEncoder(json.JSONEncoder):
    def default(self, obj):
        if not isinstance(obj, Activity):
            return super(MyEncoder, self).default(obj)
        return obj.__dict__ 



class Question:
    title = ""
    create_time = 0
    upate_time = 0
    excerp = ""
    content = ""
    answer_count = 0
    answers = []

class Answer:
    author = ""
    create_time = 0
    udpate_time = 0
    content = ""
    excerpt = ""
    voteup_count = 0
    comment_count = 0
    question = Question()

class Article:
    title =""
    author = ""
    create_time = 0
    udpate_time = 0
    excerp = ""
    content = ""
    image_url = ""
    column_name = ""

class Activity:
    type = ""
    action_text = ""
    create_time = 0
    article = Article()
    answer = Answer()
    question = Question()

class ZhihuService(zhihu_pb2_grpc.ZhihuServiceServicer):
    clientpool = {}
    userpool = {}
    def Login(self,request, context):
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
            return zhihu_pb2.LoginResponse(response = "success")
        
        if captcha == "":
            try: 
                success,err = client.login(username, password)
                print(success,",",err)
            except NeedCaptchaException:
                with open('a.gif', 'wb') as f:
                    f.write(client.get_captcha())
                
                return zhihu_pb2.LoginResponse(response = "need captcha")
            client.save_token('./tokens/' + username + '.pkl')
            self.userpool[username] = 1
        else:
            try:
                print("try with captcha:"+captcha+"---")
                success, err = client.login(username, password, captcha)
                print(success,",",err)
            except NeedCaptchaException:
                with open('a.gif', 'wb') as f:
                    f.write(client.get_captcha())
                return zhihu_pb2.LoginResponse(response = "wrong captcha")
            if success:
                client.save_token('./tokens/' + username + '.pkl')
                self.userpool[username] = 1
                return zhihu_pb2.LoginResponse(response = "success")
            else :
                return zhihu_pb2.LoginResponse(response = err)

    def GenActivityJson(self,me):
        frozen = jsonpickle.encode(self.GetActivityDict(me))
        return frozen
        # return json.dumps(self.GetActivityDict(me),cls=MyEncoder) 
    def GetActivityDict(self,me):
        result = []
        for act in  me.activities:
            activity = Activity()
            activity.action_text = act.action_text
            activity.type = act.type 
            activity.create_time = act.created_time

            if activity.type == ActType.CREATE_ANSWER or activity.type == ActType.VOTEUP_ANSWER:
                activity.answer.author = act.target.author.name
                activity.answer.content = act.target.content
                activity.answer.excerpt = act.target.excerpt
                activity.answer.create_time = act.target.created_time
                activity.answer.udpate_time = act.target.updated_time
                activity.answer.voteup_count = act.target.voteup_count
                activity.answer.comment_count = act.target.comment_count
                question = act.target.question
                activity.question.title = question.title
                activity.question.answer_count = question.answer_count
                activity.question.content = question.detail
                activity.question.excerp = question.excerpt
                activity.question.create_time = question.created_time
                activity.question.upate_time = question.updated_time
            
            result.append(activity)
            if len(result) >= 10:
                break
        return result
            
        
    def GetActivity(self,request, context):
        username = request.username
        client = self.clientpool.get(username)
        if client == None:
            return zhihu_pb2.ActivityResponse("not log in\n")
        me = client.me()
        json_object = self.GenActivityJson(me)
        return zhihu_pb2.ActivityResponse(responseJson = json_object)


_ONE_DAY_IN_SECONDS = 60 * 60 * 24
ip = "127.0.0.1"
port = "4001"
serverString = ip + ":" + port
def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    zhihu_pb2_grpc.add_ZhihuServiceServicer_to_server(ZhihuService(),server)
    server.add_insecure_port(serverString)
    server.start()
    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)

if __name__ == '__main__':
    print("server running. at port 4001")
    serve()
