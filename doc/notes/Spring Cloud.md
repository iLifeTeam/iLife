### Spring Cloud

---

#### Spring Cloud宏观结构

![截屏2020-07-16 上午8.40.57](https://tva1.sinaimg.cn/large/007S8ZIlgy1ggsmej1eo8j30mg0ef74w.jpg)

> Eureka：维护每个服务的生命周期，每一个服务都要被注册到Eureka服务器上，被注册到Eureka的服务又称为Client。Eureka通过心跳来确定服务是否正常。Eureka只做请求转发。Eureka支持集群
> Zuul：类似于网关，反向代理。为外部请求提供统一入口。
> Ribbon/Feign：可以理解为调用服务的客户端。
> Hystrix：断路器，服务调用通常是深层的，一个底层服务通常为多个上层服务提供服务，那么如果底层服务失败则会造成大面积失败，Hystrix就是就调用失败后触发定义好的处理方法，从而更友好的解决出错。也是微服务的容错机制。

---

**Eureka: 服务注册（服务发现）**

- 新建一个SpringBoot应用程序，带上Eureka的依赖

- 加一个@EnableEurekaServer的标签

```java
@EnableEurekaServer
@SpringBootApplication
public class RegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistryApplication.class, args);
    }

}
```

- 配置application.yml对Eurekaj进行设置（当前初始配置如下)

```yml
spring:
  application:
    name:  eureka-server
server:
  port: 1001
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
```

访问localhost:1001就能看到eureka的管理页面

---

**服务注册的流程**(写新的service的时候,部署时加上这些来注册)

- 需要注册的Spring应用添加maven依赖（也可以仿照的music-service)

```xml
		<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
     </dependency>		
     <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
     </dependency>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

- 加一个标签@EnableDiscoveryClient

  ```java
  @EnableDiscoveryClient
  @SpringBootApplication
  public class MusicServiceApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(MusicServiceApplication.class, args);
      }
  }
  ```

- 修改配置文件application.yml(或application.property)

  ```xml
  spring.application.name= {服务名}
  server.port= {服务的端口}
  eureka.client.serviceUrl.defaultZone= http://localhost:1001/eureka/  <= 对应前面eureka的配置
  ```

- 在Controller里面使用discoveryClient来访问在eureka上注册的service

  ```java
  @RestController
  public class MusicServiceController {
  
      @Autowired
      DiscoveryClient discoveryClient;
  ```

- 启动之后就可以在eureka的管理页面看到注册的信息

---

### Config-server

---

config-server提供一个中心化的配置中心，spring-boot可以从config-server获取配置。

##### config-server配置

```yml
server:
  port: 8888 # map port to 8888
spring:
  application:
    name: config
  cloud:
    config:
      server:
        native:
          searchLocations: classpath:/config
          # 定义从本地获取配置文件，这样配置后配置文件放置在resources/config/目录下即可
      discovery:
        enabled: true
  profiles:
    active: native
```

假设已经添加了文件resources/config/registry-prod.yml

从URL尝试获取文件, `http://localhost:8888/registry/prod`

##### spring-boot程序配置

使用在resourcce下添加bootstrap.yml从而被优先读取,此处的name和profile要对应config-server中的配置文件名. 如下配置对应的就是registry-prod

```yml
spring:
    cloud:
      config:
        name: registry
        profile: prod
        uri: http://18.166.111.161:8888
```

##### 将现有的spring-boot程序配置移到config-server

1. 修改sprintboot的配置文件名为 ($spring.application.name)-prod.(yml/properties)，放在config-server的resources/config目录下

2. 在配置文件目录下增加bootstrap.yml文件并配置spring.cloud.config参数
3. 重启config-server和springboot程序
4. 建议开发的时候还是用本地的配置文件，可以配置一个bootstrap-dev.yml