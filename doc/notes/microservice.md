# Microservice
## 理解什么是微服务  
>* 单体服务往往适合早期的项目开放，因为服务较少，测试方便  
>* 但是随着功能逐渐增多，单体项目会变得更加庞大，使得部署与管理变得不方便  
>* 微服务则是把各个服务之间实现解耦，每个服务部署在云虚拟机或者docker中，对外仅提供相应接口，同时各个服务之间的通信由**API Gateway**中介来负责，他负责负载均衡、缓存、访问控制、API计量和监控等功能，可以通过使用NGINX来实现
------
## 微服务的优缺点
> ### 优点
> 1. 解决了复杂问题，将庞大的单体应用分解成多个服务，更易理解与维护
> 2. 可以实现每个微服务的独立部署，独立扩展
------
## Docker：一次编译，到处运行
### Docker引擎
1. 生成镜像：
   1. mvn package生成jar包
   2. dockerfile配置（基本直接套用就可以）
   3. docker build -t name
   4. docker image
2. push到docker hub
   1. docker tag name ilife/containname:v
   2. docker push ilife/containname:v