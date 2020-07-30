import jieba
import pymysql
from wordcloud import WordCloud
import matplotlib.pyplot as plt
import re

exclude_word = {'我们', '你们', '他们', '它们', '因为', '因而', '所以', '如果', '那么', '如此', '只是', '但是', '就是', '这是', '那是', '而是', '而且',
                '虽然',
                '这些', '有些', '然后', '已经', '于是', '一种', '一个', '一样', '时候', '没有', '什么', '这样', '这种', '这里', '不会', '一些', '这个',
                '仍然', '不是',
                '自己', '知道', '可以', '看到', '那儿', '问题', '一会儿', '一点', '现在', '两个', '三个', '一次', '各位', '可能', '一下', '需要', }
exclude_char = "[a-zA-Z0-9<>\/\-\=\"\:]"
db = pymysql.connect("18.166.111.161", "ilife", "ilife2020", "zhihu")
cursor = db.cursor()


def selectUserActivityOfType(uid, type):
    return "SELECT target_id FROM activity where zhihu_uid =\"" + uid + "\" and type like \'%" + type + "\'"


def selectRowsFromTableByIds(table, rows, ids):
    return "SELECT " + rows + " FROM " + table + " where id in (" + ",".join(ids) + ")"


def generateWordCloud(uid, activity_type):
    activity_sql = selectUserActivityOfType(uid, activity_type)
    print("activity_sql:", activity_sql)
    cursor.execute(activity_sql)
    targets = cursor.fetchall()
    target_ids = list(map(lambda x: ('\"' + str(x[0]) + '\"'), targets))

    content_sql = selectRowsFromTableByIds("answer", "content", target_ids)
    print("content_sql:", content_sql)
    cursor.execute(content_sql)
    text = ""
    rowcount = cursor.rowcount
    for i in range(rowcount):
        text += " " + cursor.fetchone()[0]

    text = re.sub(exclude_char, '', text)
    word_list = jieba.cut(text)
    # print(text)
    wl = " ".join(word_list)

    wc = WordCloud(background_color="white",  # 设置背景颜色
                   # mask = "图片",  #设置背景图片
                   max_words=2000,  # 设置最大显示的字数
                   # stopwords = "", #设置停用词
                   font_path="font.ttf",
                   max_font_size=50,  # 设置字体最大值
                   random_state=30,  # 设置有多少种随机生成状态，即有多少种配色方案
                   stopwords=exclude_word
                   )
    wc.generate(wl)
    print("generate successfully")
    return wc
