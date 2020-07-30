### Spring Cloud Zuul (MicroService 请求转发)

---

**Zuul 路由网关**

- 新建一个SpringBoot应用程序，带上Zuul的依赖

- 加一个@@EnableZuulProxy的标签，启动路由转发功能
- 加一个@EnableDiscoveryClient的标签，用于从Registry获取信息

```java
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
```

- 配置application.yml对Zullj进行设置（当前初始配置如下)

```yml
server:
  port: 8801
spring:
  application:
    name: zuul-proxy
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:1001/eureka/

zuul:
  add-host-header: true #设置为true重定向是会添加host请求头
  routes: #给服务配置路由
    music-service:            # 服务名，无实际意义
      service-id: music-service  # 注册于eureka的服务名
      path: /music-service/**    # 识别到该前缀就会转发
      sensitive-headers:         # 过滤header，空为不过滤
```

启动后访问eureka的管理页面，可以看到网关已经注册

如上配置后，请求  zuul:8801/music-service/** 将会被转发到注册为music-service的服务器上，转发后的请求为 /**

---

