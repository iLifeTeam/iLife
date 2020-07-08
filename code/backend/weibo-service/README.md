# 微博后端 & sql

### sql
根据微博网页版能获取的html数据，写了一份sql，涵盖的字段有     

* 用户个人信息，包括粉丝数，微博数，注册时间等  
* 用户收到的评论和发出的评论
* 用户发出的微博和所关注用户发布的微博(timeline)
### 后端搭建
* 用spring initializer创建了一份初始框架，作为微服务weibo_service。  
* 后端分层controller-service-dto-dao-repository-entity
* 建立了初步的ORM
* 个人觉得用户应该可以对爬到的数据进行修改，以及可以保存一些想要的数据(设置一个字段，则爬虫更新数据库时不会删除这个tuple)
* 在dao层和repository层实现了了评论(Comment)，微博(Weibo)，用户(User)的CRUD

