from zhihu_oauth import ZhihuClient, ActType
from zhihu_oauth.exception import NeedCaptchaException
import time

client = ZhihuClient()
client.load_token('tokens/zxy771906409@163.com.pkl')
me = client.me()

print('name', me.name)


l1 = '<meta name="referrer" content="no-referrer" />'
l2 = '<meta charset="utf-8" />'

filename = 'test.md'
file = open(filename,'w')
for act in me.activities:
    file.write("--------------------------\n")
    file.write(act.type + "\n")
    file.write(act.action_text)
    if act.type == ActType.CREATE_ANSWER:
        file.write("question: " + act.target.question.title +" \n")
        file.write("author: " + act.target.author.name + "\n")
        ans_time = time.ctime(act.target.created_time)
        file.write("time: " + ans_time + "\n")
        file.write("content: " + act.target.content + "\n")
        file.write("感谢: "+ str(act.target.thanks_count) + ",点赞:"+str(act.target.voteup_count) +"\n")
    if act.type == ActType.VOTEUP_ANSWER:
        file.write("question: " + act.target.question.title +" \n")
        file.write("author: " + act.target.author.name + "\n")
        ans_time = time.ctime(act.target.created_time)
        file.write("time: " + ans_time + "\n")
        file.write("content: " + act.target.content + "\n")
        file.write("感谢: "+ str(act.target.thanks_count) + ",点赞:"+ str(act.target.voteup_count) +"\n")
    if act.type == ActType.CREATE_QUESTION:
        file.write("act.target.title  \n")
        # file.write("topics: " + act.target.topics+"\n")
        ans_time = time.ctime(act.target.created_time)
        file.write("time: " + ans_time + "\n")
        file.write("detail: " + act.target.detail + "\n")
        file.write("answer count: " +  str(act.target.answer_count) + " \n")
    if act.type == ActType.FOLLOW_QUESTION:
        file.write(act.target.title +  " \n")
        # file.write("topics: " + act.target.topics+"\n")
        ans_time = time.ctime(act.target.created_time)
        file.write("time: " + ans_time + "\n")
        file.write("detail: " + act.target.detail + "\n")
        file.write("answer count: " +  str(act.target.answer_count) + "\n")
    if act.type == ActType.CREATE_ARTICLE:
        file.write(act.target.title +" \n")
        # file.write("column: " + act.target.column + "\n")
        file.write("content: " + act.target.content + "\n")
        updated_time = time.ctime(act.target.updated_time)
        file.write("updated time: " + updated_time +  "\n")
