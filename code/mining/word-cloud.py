import jieba
import pymysql
from wordcloud import WordCloud
import matplotlib.pyplot as plt
db = pymysql.connect("47.97.206.169","ilife","ilife2020","zhihu")
cursor = db.cursor()

def selectUserActivityOfType(uid, type):
    return "SELECT target_id FROM activity where zhihu_uid =\"" + uid +"\" and type = \"" + type + "\""
def selectRowsFromTableByIds(table,rows,ids):
    return "SELECT " + rows + " FROM " + table + " where id in (" + ",".join(ids) + ")"
uid = "552399318699515904"
activity_type = "VOTEUP_ANSWER"
sql = selectUserActivityOfType(uid,activity_type)
print("sql:",sql)
cursor.execute(sql)
targets = cursor.fetchall()
tids = list(map(lambda x: ('\"'+str(x[0])+'\"'), targets))
# def requestForRowsFromTableByIds(table, rows, ids):
sql = selectRowsFromTableByIds("answer","content",tids) 
print("sql:",sql)
cursor.execute(sql)

text = ""
rowcount = cursor.rowcount
for i in range(rowcount):
    text += " " + cursor.fetchone()[0]
    
import re
text = re.sub('[a-zA-Z0-9<>\/\-\=\"\:]','',text)
wordlist = jieba.cut(text)
print(text)
wl = " ".join(wordlist)
exclude={'我们','你们','他们','它们','因为','因而','所以','如果','那么',\
          '如此','只是','但是','就是','这是','那是','而是','而且','虽然',\
          '这些','有些','然后','已经','于是','一种','一个','一样','时候',\
    '没有','什么','这样','这种','这里','不会','一些','这个','仍然','不是',\
    '自己','知道','可以','看到','那儿','问题','一会儿','一点','现在','两个',\
         '三个','一次','各位','可能','一下','需要',\
          }
wc = WordCloud(background_color = "white", #设置背景颜色
               #mask = "图片",  #设置背景图片
               max_words = 2000, #设置最大显示的字数
               #stopwords = "", #设置停用词
               font_path = "font.ttf",
               max_font_size = 50,  #设置字体最大值
               random_state = 30, #设置有多少种随机生成状态，即有多少种配色方案
               stopwords=exclude
    )
myword = wc.generate(wl)
plt.imshow(myword)
plt.axis("off")
plt.show()
