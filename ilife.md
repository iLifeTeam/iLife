# 个人数据管理系统 iLife

## 项目业务功能
* 七种平台八类数据的获取和展示
* 数据的统计和报表，多样展示数据
* 词频分析，情感分析，体现用户画像
* 相似推荐系统，包括B站视频和网易云歌曲
* 娱乐推荐与博主推荐，深度分析个人喜好
* 消费分析，提供消费建议
## 功能需求
* 收集散落于互联网角落的各种数据，一共八类，分别是豆瓣电影、豆瓣书籍、知乎、微博、网易云、哔哩哔哩、淘宝、京东
* 合理储存并展示这些数据，提供方便的数据查询接口
* 对每项数据进行基本分析，展示统计数据报表
* 对不同类的数据进行综合分析，进行个性化推荐
## 非功能需求
* 安全性需求：接口安全，用户账户安全
* 稳定性需求：服务稳定性， 爬虫稳定性
* 高效运维管理
* 高并发，低时延，提供良好用户体验
* 兼容不同主流浏览器
## 项目架构
### 前端
* 框架：React 
* UI设计：AdminLTE + Ant Design
* 数据展示：eCharts + Datatables.net
* 登录加密：crypto-js  AES192 + md5
### 后端
* 基于Spring Boot的MVC架构
* Hibernate + MySQL实现持久存储
* Spring Cloud 实现的微服务架构  
	Eureka + Zuul + Config-Server 实现服务注册,网关路由,配置中心
七个微服务：
哔哩哔哩，微博，豆瓣，知乎，京东，淘宝，网易云音乐  
八个爬虫相关服务
* docker打包部署,docker swarm集群管理

## 爬虫
爬虫分类：官方API，第三方API，自写爬虫  
用到的爬虫技术：多进程加速，增量式爬取，Selenuim模拟登陆，滑块验证码破解，IP代理
## 测试
### 前端测试
* Jest + Cypress 进行功能测试
* 八类数据主页代码测试覆盖率达到80%
### 后端测试
* 使用DbForge创造合乎逻辑的测试数据
* JUnit 进行功能性测试  
  覆盖所有暴露给前端的接口
  对核心业务逻辑覆盖率超过95%
* Jmeter对与爬虫无关的业务逻辑进行压力测试  
  大部分核心业务在100个并发下控制在700ms左右

## 安全
* Spring Security 保护后端接口
* Spring Session Redis 实现session持久化
* Zuul网关实现请求转发
* MD5加密算法保存用户密码

## 运维
* MicroService监控
* Spring Actuator + Prometheus + Grafana 进行图形化展示
*
* 部署：Docker Swarm   
  2 AWS nodes （ m5.xlarge,  t3.large)  
  2 华为云结点
* 监控与管理：Portainer.io + Visualizer
* CI/CD：Github Action
## 项目管理
* git
* SwaggerHub做API管理，