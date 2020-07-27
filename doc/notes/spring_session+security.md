# 配置
还没用spring session的时候，一直不明白如何让后端把session主动存到持久层(比如redis)中，这几天看了看spring session的文档和博客，算是明白了，简单来说就是下面一段话：
> Spring Session creates a Spring bean with the name of springSessionRepositoryFilter that implements Filter. The filter is in charge of replacing the HttpSession implementation to be backed by Spring Session.

不过[spring.io官网的配置tutorial](https://docs.spring.io/spring-session/docs/current/reference/html5/guides/boot-redis.html)属实不行，配出来根本无法向redis里面插入数据，且给的demo也没法在本机运行。
现在给出我的配置方案
## 依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```
## application.properties
```python
# 必须要加，否则你加依赖不加这个连启动都报错
spring.session.store-type=redis
# 默认为localhost:6379，可以通过配置更改
spring.redis.host=localhost
# Redis server password
# 如果密码为空，就不要写了，否则也报错
# spring.redis.password=
# Redis server port.
spring.redis.port=6379
# 指定储存的位置
spring.session.redis.namespace=redis:session
spring.session.redis.flush-mode=immediate
spring.session.timeout=5m
# 超时断连，不指定可能会报unable to connect to redis
spring.redis.timeout=3000
```
## 查看redis
发送任意一个请求，然后到redis上看看 keys *,就可以看到存入的session
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200724160303828.png)
## 配置spring security
作为非登录端的security配置不需要很复杂，只需要一个简单的securityConfig类就好
### 引入依赖

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

### SecurityConfig类
```java
package com.ilife.weiboservice.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}

```
