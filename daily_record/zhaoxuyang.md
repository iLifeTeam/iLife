### ZXY's work record

- 7.7 
  - 简单的网易云音乐信息拉取爬虫，需要显示提供账号密码。
  - 简单的eureka，zuul配置，用于后端服务的注册和访问。
- 7.8 
  - 尝试知乎爬虫，发现Zhihu_Oauth库，并试图使用
  - 部署mongodb,mysql在云服务器上
- 7.9
  - 完善zhihu-service后端，数据库设计，实现ORM和spring boot service
  - 利用Zhihu_OAuth库创建一个爬虫service
  - 使用grpc框架连接爬虫server和zhihu-service后端，并开放 /login, /updateAcitivities接口
  - 成功将用户数据存储在数据库中