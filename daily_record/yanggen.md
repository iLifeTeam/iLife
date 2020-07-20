# 7.7  
### 微博爬虫尝试
* 为项目申请了华为云top域名  
* 尝试了微博的SDK和API，弄清了爬取微博数据的逻辑，由于SDK和API都版本过旧且无人维护，决定到github上找现成的轮子爬取数据；  
# 7.8
### sql
根据微博网页版能获取的html数据，写了一份sql，涵盖的字段有     

* 用户个人信息，包括粉丝数，微博数，注册时间等  
* 用户收到的评论和发出的评论
* 用户发出的微博和所关注用户发布的微博(timeline)
### 后端搭建
* 用spring initializer创建了一份初始框架，作为微服务weibo_service。  
* 后端分层controller-service-dto-dao-repository-entity
* 建立了初步的ORM
* 个人觉得用户应该可以对爬到的数据进行修改，以及可以保存一些想要的数据(TODO：设置一个字段，则爬虫更新数据库时不会删除这个tuple)
* 在dao层和repository层实现了了评论(Comment)，微博(Weibo)，用户(User)的CRUD  
# 7.9
### 微博数据获取  
* 在github上使用了现成的轮子进行改装，成功爬取了任意用户的微博数据和个人数据
* 更改sql，连接远端服务器数据库，使得爬虫的数据可以直接存入mysql中
* 根据修改的字段，重新编写后端CRUD逻辑和Entity类
### 服务器端
* 配置了本机免密登录
* 在服务器上读到了本机写入的数据
# 7.10
### 阅读
* 学习了python爬虫的更多知识，并翻阅的os,os.path.json库的API，在CSDN上发表了学习的记录
(https://editor.csdn.net/md/?articleId=107264784)[os库使用]
### 微博后端完善
* 写了一个调用爬虫的python工具，可以根据传入的命令行参数修改json，并和后端service连接
* 写好了用户获取微博，获取个人信息的接口
* 修复了某些微博id会导致后端报错的问题
# 7.11 Saturday
### 博客撰写
写了两篇博客，一篇是关于python的json库，配置爬虫时会用到
https://blog.csdn.net/weixin_44602409/article/details/107281176
一篇是关于swagger的使用记录
https://blog.csdn.net/weixin_44602409/article/details/107285187
### 引入swagger
在微博后端的接口中引入了swagger注解，生成了标准的API文档

# 7.12 Sunday
### 网课&博客撰写
今天看了拉钩教育爬虫网课的前七讲，尝试爬了一些静态网页和简单的动态网页，并记录两篇笔记在CSDN博客上
https://editor.csdn.net/md/?articleId=107306337
https://editor.csdn.net/md/?articleId=107299196

# 7.13
### 登录后端
* 创建了springboot项目，写好了iLife主程序登录的后端，提供了RESTful风格接口并用swagger自动生成文档
### 淘宝数据
* 尝试和淘宝客服请求授权，不成功；淘宝开放平台也没有向个人用户提供的接口；github上缺少可用的淘宝爬虫轮子，
* 决定购买商用接口或者是采用其他手段获取数据。
### swagger
* 解决了swagger对POST方法文档描述不清的问题，更新了博客。
# 7.14
### docker
* 在windows上安装docker,踩了很多坑并写成了文档
https://blog.csdn.net/weixin_44602409/article/details/107358001
### 接口更新
* 在dao层，update将返回影响行数，delete将返回void
* 在service层,update和delete都返回Responsentity
# 7.15
### 单元测试
引入了junit，完成了对weibo的controller层的单元测试
### 爬虫学习
继续学习拉钩教育的爬虫网课，更新了requests库的使用
https://editor.csdn.net/md/?articleId=107299196
# 7.16
### 单元测试
* 完成了weibo service层与auth controller层的单元测试
* 系统学习了mockMvc的使用
### docker
* 部署了微博后端在服务器上，部分接口仍存在问题
# 7.17
### 单元测试
* 完成了auth service层的单元测试，并修改了weibo service层单元测试中的问题
### docker
* 尝试解决微博后端部署依赖缺失的问题，决定将后端CRUD与爬虫分离
* 更新docker博客，增加exec,logs,status等指令的使用：
https://blog.csdn.net/weixin_44602409/article/details/107358001
### 爬虫学习
* 看了正则表达式相关的文章
# 7.18 Saturday
### 
* 回学校拿显示屏，方便更好工作
# 7.19 Sunday
### 部署
* 用docker部署了auth-service
### 静态文档
* 利用IDEA插件生成了接口的静态文档，有.adoc,.md,.html三种格式
# 7.20
### 松耦合
* 将微博后端的CRUD和爬虫分开来
* 新建了一个branch负责微博爬虫
### 部署
* 学会打包python项目，部署了微博爬虫到服务器上
### Spring Security
* 学习spring security,写了一些demo
### docker
* 整理了后端的container和images

