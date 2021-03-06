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
- 7.10 
  - 使用github action 实现 zhihu-service和zhihu-python-crawller的CICD。在分支push的时候自动部署到云服务器上
  - 使用docker 打包zhihu-service和zhihu-python-crawller
- 7.11~7.12
  - 完善zhihu-service，提供Question/Answer/Article接口。
- 7.13
  - 对接前端，完善zhihu-service API。
  - 讨论本周计划
  - 对alipay爬虫进行调查
- 7.14
  - 学习Junit进行简单的测试
  - 对alipay爬虫进行尝试
- 7.15
  - 完成alipay后端（假设已经通过爬虫获取了用户账单csv文件）
  - docker打包后端服务
  - 学习docker swarm/ docker compose相关技术，整理资料，为后续更好的开发/部署做准备。
- 7.16 
  - 实现了简单的word-cloud，根据历史的zhihu浏览数据分析出现的高频词汇。并绘制词云，尚未服务化。
  - 完善了单元测试
- 7.17 
  - fix a bug with zhihu-oauth library, refine zhihu-service to remove duplicate entries.
  - add more docker swarm/ docker-compose notes.
  - refine API
- 7.18～7.19
  - 完善了zhihu-service，alipay-service的单元测试，提高了覆盖率
- 7.20
  - 尝试taobao爬虫，使用puppetter + nodejs使用浏览器进行爬取，初步获取数据
  - 制定本周计划，初步了解spring security
- 7.21
  - 完善taobao爬虫拉取历史订单
  - 使用express打包成http server
- 7.22
  - try jingdong crawler, try to bypass slider verification, but failed. abort Jingdong
  - basic taobao-service backend
- 7.23
  - finish taobao-service backend, support incremental crawling.
  - try alipay crawling, integrate with alipay-backend.
- 7.24 
  - wrap up jingdong-python crawler with flask http server.
  - explore and abandon alipay-service crawler and alipay service backend
  - refine jd-python crawler with functionality to pull all orders with qrcode login
- 7.25 ~7.26
  - deploy zhihu,taobao,jinddong service, deloy zhihu,taobao,jingdong crawler. with docker
  - fix bugs with services and crawlers
- 7.27
  - help hyy with frontend, build page for jingdong
  - refine jingdong service add security configuration, fix backend cors issues
- 7.28
  - front-end: add page for taobao
  - change taobao to login with smscode, because passwd,username login will be banned
  - add session config on all servers
- 7.29
  - try actuator,prometheus and graphana, find a bug with fastjson and actuator
  - deploy product classification service
- 7.30 
  - open all security settings and try to connect with frontend
  - refine login procedures of jd,tb,zh front-end to avoid retype username.
  - write logic to show word-cloud
- 7.31
  - refine front-end pages, write logic for weibo/douban report.
  - refine backend unit tests.
  

