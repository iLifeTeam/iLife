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
    def __init__(self, title,excerpt,content, create_time, update_time,answer_count):
        self.title = title
        self.create_time = create_time
        self.update_time = update_time
        self.excerpt =excerpt
        self.content=content
        self.answer_count = answer_count
        self.answers = []
    def __init__(self):
        self.title = ""
        self.create_time = 0
        self.update_time = 0
        self.excerpt =""
        self.content=""
        self.answer_count = 0
        self.answers = []

    def setValue(self,question):
        self.title = question.title
        self.content = question.detail
        self.excerpt = question.excerpt
        self.create_time = question.created_time
        self.update_time = question.updated_time
        self.answer_count = question.answer_count

class Answer:
    def __init__(self, author,excerp,content, create_time, update_time,voteup_count,comment_count):
        self.author = author
        self.create_time = create_time
        self.update_time = update_time
        self.excerpt =excerpt
        self.content=content
        self.voteup_count = voteup_count
        self.comment_count = comment_count
        self.question = Question()
    def __init__(self):
        self.author = ""
        self.create_time = 0
        self.update_time = 0
        self.excerpt =""
        self.content=""
        self.voteup_count = 0
        self.comment_count = 0
        self.question = Question()
    def setValue(self,answer):
        self.author = answer.author.name
        self.content = answer.content
        self.excerpt = answer.excerpt
        self.create_time = answer.created_time
        self.update_time = answer.updated_time
        self.voteup_count = answer.voteup_count
        self.comment_count = answer.comment_count

class Article:
    def __init__(self,title, author,excerp,content, update_time,image_url,column_name):
        self.title =title
        self.author = author
        self.update_time = update_time
        self.excerpt =excerpt
        self.content=content
        self.image_url = image_url
        self.column_name = column_name
    def __init__(self):
        self.title =""
        self.author = ""
        self.update_time = 0
        self.excerpt =""
        self.content=""
        self.image_url = ""
        self.column_name = ""
    def setValue(self,article):
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
    def __init__(self,type,action_text,create_time):
        self.type = type
        self.action_text = action_text
        self.create_time =create_time
        if type == ActType.CREATE_ANSWER or type == ActType.VOTEUP_ANSWER:
            self.answer = Answer()
        if type == ActType.CREATE_QUESTION or type == ActType.FOLLOW_QUESTION:
            self.question = Question()
        if type == ActType.CREATE_ARTICLE or type == ActType.VOTEUP_ARTICLE:
            self.article = Article()
    

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
        return json.dumps(self.GetActivityDict(me),default=lambda o: o.__dict__, sort_keys=True, indent=4) 
    def GetActivityDict(self,me):
        result = []
        for act in  me.activities:
            activity = Activity(act.type,act.action_text,act.created_time)

            if activity.type == ActType.CREATE_ANSWER or activity.type == ActType.VOTEUP_ANSWER:
                activity.answer.setValue(act.target)
                print(activity.answer.content)
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
                counter = 10
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
            if len(result) > 50:
                break
        return result
            
        
    def GetActivity(self,request, context):
        username = request.username
        client = self.clientpool.get(username)
        if client == None:
            return zhihu_pb2.ActivityResponse("not login")
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
