### Docker Deploy

---

### 构建docker 镜像

1. 创建Dockerfile

2. 利用Dockerfile构建

   ```shell
   $ docker build 路径 -t  镜像名:tag 
   会默认选择路径下的Dockerfile文件构建
   $ docker build . -t zhihu-crawller:0.1
   ```

3. 运行

   ```shell
   $ docker run 镜像名:tag
   -p docker端口:主机端口  （端口映射，让docker里面的端口和主机端口对接.）
   -d 后台运行
   $ docker run -p 4001:4001 zhihu-crawller:0.1
   ```

4. 上传到Docker Hub

   ```shell
   # 登录
   $ docker login
   # 打tag  第一个参数是docker IMAGE ID, 第二个参数是你发布后的 仓库名/镜像名:版本
   $ docker tag 87f716f546b3  ilife2020/zhihu-python-crawller:latest
   # 上传
   $ docker push ilife2020/zhihu-python-crawller:latest
   ```

   或者通过CI/CD，在git push的时候进行自动化上传。这部分参考CI/CD文档。

---



#####  知乎python爬虫docker镜像经验

1. 使用python:3.7-slim 比 python:3.7小。 python:3.7-alpine更小，但是出现了找不到包错误。不知道有没有best-practice
2. 将改动不大的事情放在Dockerfile最前面（如apt install, pip install -r requirements）,可以利用cache
3. docker内服务器不能运行在127.0.0.1:4001上，而是得运行在 0.0.0.0:4001 或是:4001上，才能监听对外端口

4. 使用.dockerignore来防止无用文件被加入, 如/venv/

---

##### Spring Boot项目使用docker部署



---

#### [区分Dockerfile中的CMD与ENTRYPOINT](https://www.cnblogs.com/sparkdev/p/8461576.html)

1. CMD, ENTRYPOINT是否带括号

   ```dockerfile
   FROM ubuntu
   CMD [ "top" ]
   # docker直接执行的是 top 指令
   ```

   ```dockerfile
   FROM ubuntu
   CMD top
   # docker直接执行的是/bin/sh -c top
   ```

2. 区分CMD和ENTRYPOINT

   **CMD**可以被启动容器时的参数覆盖

   ```shell
   $ docker run container_name ps aux
   # 如果是CMD top, 则会被覆盖为 ps aux
   ```

   **ENTRYPOINT** 启动容器时的参数会添加到参数列表中	(如果有CMD，CMD会附加到后面)

   ```dockerfile
   $ docker run container_name -c
   # 如果是ENTRYPOINT ["top","-b"], 实际执行的是 top -b -c
   ```

---

### Docker 网络

https://docs.docker.com/network/

一共提供五种不同的网络模式

- bridge: 默认的网络驱动模式。当应用程序运行在需要通信的独立容器 (standalone containers) 中时，通常会选择 bridge 模式。
- host：容器直接使用主机的网络。host 模式仅适用于 Docker 17.06+。
- overlay：overlay 网络将多个 Docker 守护进程连接在一起，并使集群服务能够相互通信。您还可以使用 overlay 网络来实现 swarm 集群和独立容器之间的通信，或者不同 Docker 守护进程上的两个独立容器之间的通信。该策略实现了在这些容器之间进行操作系统级别路由的需求。
- macvlan：Macvlan 网络允许为容器分配 MAC 地址，使其显示为网络上的物理设备。 Docker 守护进程通过其 MAC 地址将流量路由到容器。对于希望直连到物理网络的传统应用程序而言，使用 macvlan 模式一般是最佳选择，而不应该通过 Docker 宿主机的网络进行路由。
- none：对于此容器，禁用所有联网。通常与自定义网络驱动程序一起使用。none 模式不适用于集群服务。
---
### Docker-Compose

使用docker-compose可以同时启动多个docker镜像，默认情况下，所有docker镜像会被加入 `当前目录名称+_default`的bridge network，并可以互相通过services内定义的名称作为hostname进行互相访问。

样例docker-compose.yml可以参考

```yml
version: "3"
services:
  zhihu-service:
    image: zhihu-service:latest
    ports:
      - "8090:8090"
  python-crawller:
    image: zhihu-python-crawller:latest
    expose:
      - "4001"
```

[菜鸟](https://www.runoob.com/docker/docker-compose.html)

**Docker其他指令**

```zsh
删除全部 <none> 镜像
$ docker rmi $(docker images | grep "none" | awk '{print $3}')
移除全部 <Exited>容器
$ docker rm $(docker ps -a | grep "Exited" | awk '{print $1 }')
停止全部容器
$ docker stop $(docker ps -aq)
```

