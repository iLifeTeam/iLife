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

### 使用GithubAction进行CI/CD

