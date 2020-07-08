### Spring Cloud Eureka (MicroService 服务注册)

---

**Eureka: 服务注册应用**

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

需要把服务部署在云上时，将