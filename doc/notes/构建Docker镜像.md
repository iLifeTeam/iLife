### Docker

---

### 构建docker 镜像

**基本流程**

1. 创建Dockerfile [reference](https://docs.docker.com/engine/reference/builder/)

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

**Dockerfile配置**

---

#####  知乎python爬虫docker镜像经验

1. 使用python:3.7-slim 比 python:3.7小。 python:3.7-alpine更小，但是出现了找不到包错误。不知道有没有best-practice
2. 将改动不大的事情放在Dockerfile最前面（如apt install, pip install -r requirements）,可以利用cache
3. docker内服务器不能运行在127.0.0.1:4001上，而是得运行在 0.0.0.0:4001 或是:4001上，才能监听对外端口

4. 使用.dockerignore来防止无用文件被加入, 如/venv/

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

**Docker其他指令**

```zsh
删除全部 <none> 镜像
$ docker rmi $(docker images | grep "none" | awk '{print $3}')
移除全部 <Exited>容器
$ docker rm $(docker ps -a | grep "Exited" | awk '{print $1 }')
停止全部容器
$ docker stop $(docker ps -aq)
```

----

### Docker 运行指令



- [**Volume**](https://docs.docker.com/engine/reference/builder/)	

  Docker内存储数据，在内存中利用tmpfs，而持久化需要用到volume。

  Volume区别于bind mount(将docker中文件夹和主机文件夹进行映射)，是一块专门映射给docker的存储，跨平台可用，更好的在多个container间共享，可以存储在远端。

![截屏2020-07-16 上午9.15.09](https://tva1.sinaimg.cn/large/007S8ZIlgy1ggsme4fv3mj30kd0afq4b.jpg)