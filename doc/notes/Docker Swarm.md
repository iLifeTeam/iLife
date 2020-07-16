### Docker Swarm

##### Swarm mode key concepts 核心概念

**[What is a swarm](https://docs.docker.com/engine/swarm/key-concepts/)**

- **Swarm**（群）是一组运行在swarm模式下的**Docker host**（运行docker的机器），有的是Manager（管理membership和delegation）有的是Worker（运行着服务。一个**Docker host **既可以是Manager也可以是Worker，也可以都是（有点像是Paxos)
- 创建服务的时候，你可以定义他的最佳运行状态（几个副本、网络和存储、端口...）docker在运行的时候会尽量满足需求，如果有某一个机器节点挂了，会将任务分配到其他机器。
- Swarm的好处之一是，可以不重启服务的情况下修改一个service的配置，包括端口，volume

- 参与Swarm的机器仍然可以运行其他的stand-alone container，但是作为swarm service在swarm里面运行的服务，只能由Manager节点(们)来控制。

**总结**：Swarm就是一组机器集群，在上面可以运行一组Docker服务，并由Manager节点来管理，提供容错能力。在swarm里面运行的服务的配置可以动态改变，而无需手动重新启动。

[**Nodes**](https://docs.docker.com/engine/swarm/key-concepts/#nodes)

- Node(节点)是一个Docker **engine**的实例，可以加入Docker Swarm参与工作。一个机器上可以运行多个D从ocker Node，但是一般来说生产环境的Docker Node分布在不同的物理机器上。
- 当想要在Docker Swarm上运行一个应用时，需要将service提交到Manager Node，再由其转发
- Manager节点的任务是协调不同机器从而达到预期的运行状态，多个Manager节点会选出一个作为leader负责协调。
- Worker节点负责完成task，并且会有一个**agent**不断向Manager汇报Worker的工作情况，从而让Manager更好的分配任务。Manager也可以是Worker。

**总结**：Node是执行任务的对象，Worker和Manager协调使得整体趋向预期运行状态。

**[Services and tasks](https://docs.docker.com/engine/swarm/key-concepts/#services-and-tasks)**

- [Service](https://docs.docker.com/engine/swarm/how-swarm-mode-works/services/)（服务）是Node上运行的东西，是Swarm的核心，也是整个设计最终的目的。
- 一个服务就是一个Container Image和对应运行的Command
- **replicated mode**（副本模式）下，manager会将特定的task复制多份。
- **global mode**（全局模式）下，所有的Node上都会跑这个task
- task是调度的原子粒度，一旦一个task被分配到一个结点上，就不能迁移到其他节点上。

**总结**：Service是运行在Swarm上的服务，一个正在某个Node上运行的Service实例叫Task，由Manager来调度。

[**Load balancing**](https://docs.docker.com/engine/swarm/key-concepts/#load-balancing)

- Swarm使用了**Ingress load balance**(控制请求的进入路由来控制workload)
- 外部请求可以通过任何一个Node进入Swarm（即使这个Node不负责这个服务），然后Manager会在内部控制请求的转发，Manager会根据Swarm内置的DNS转发到对应的task实例上。

**总结**

- Swarm就是一组机器（Node）集群，在上面可以运行一组Service（即Container镜像+运行Command）

- 由Manager节点来管理,由Worker来执行，两者协调使得整体运行状态趋向预设的运行状态。
- 在Swarm里面运行的服务的配置可以动态改变，而无需手动重新启动
- 从任何一个节点都能访问到服务，在内部进行load balance。

---

##### Docker Swarm Mode配置 https://docs.docker.com/engine/swarm/swarm-tutorial/

- [ ] Deploy Service to a Swarmhttps://docs.docker.com/engine/swarm/services/

- 初始化一个Docker Swarm

  ```zsh
  $ docker swarm init
  Swarm initialized: current node (0jfhggl4q79wdtn0hq3qyjgtc) is now a manager.
  To add a worker to this swarm, run the following command:
      docker swarm join --token a-lengthy-token 192.168.65.3:2377
  To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.
  $ docker swarm join-token [manager/worker]
  查看token
  ```

- 往swarm上部署一个service

  ```zsh
  $ docker service create --name alipay-service --replica 2 --publish 8090:8090 ilife2020/alipay-service:latest
  ```

- 往swarm上部署一个compose file

  ```shell
  $ docker-compose up -d
  WARNING: The Docker Engine you're using is running in swarm mode.
  
  Compose does not use swarm mode to deploy services to multiple nodes in a swarm. All containers will be scheduled on the current node.
  
  To deploy your application across the swarm, use `docker stack deploy`.
  直接up会有提示，请用docker stack deploy
  $ docker-compose down
  $ docker stack deploy --compose-file docker-compose.yml ilife
  Creating network ilife_stack_default
  Creating service ilife_stack_python-crawller
  Creating service ilife_stack_zhihu-service
  ```

---

### Docker Compose

- Compose是一个定义并一步运行多个docker container的工具，利用YAML文件来进行声明式的配置

**使用Compose一般有三步**

- 使用Dockerfile定义运行环境
- 使用docker-compose.yml定义你的应用需要的所有container service
- `docker-compose up`来启动

- [ ] **docker-compose.yml [reference](https://docs.docker.com/compose/compose-file/)**

**compose的feature**

- 单机上多个独立的运行环境

  compose文件利用project name来区分不同的隔离环境，可以利用这一点：

  - 在开发时：可以在不同的feature branch上，独立的运行服务
  - 在CI 上：为了避免不同的build互相影响，使用build-number作为project name

  - 在一台机器上可能不同的项目会用同一个service名字，利用project name来隔离。

  默认的project name时父目录的名称，可以使用-p option来制定，或者`COMPOSE_PROJECT_NAME`环境变量

- 保存volume中的数据

  使用docker-compose up运行时，如果之前有相同容器在运行，那么会自动将volume复制过来

- 只更新改变过的container

  配置没有改变的话，直接重用原来的container

- [支持变量](https://docs.docker.com/compose/compose-file/#variable-substitution) 

**常见用例**

- 开发环境

  方便一键部署开发环境

- 自动化测试环境

  ```
  $ docker-compose up -d
  $ ./run_tests
  $ docker-compose down
  ```

- 单机部署

  compose传统上用于单机部署和测试，但是渐渐的也可以拿来使用在生产环境。

**[如何在生产环境使用](https://docs.docker.com/compose/production/)**

可以在Swarm集群上运行compose

- 需要对配置做一些修改

  - 去除所有的volume-binding，彻底与外界隔离
  - 绑定到不同的port
  - 配置restart:always
  - 加入额外的服务比如log-aggregator

  - 可以使用production.yml对原本对yml配置中的修改[进行覆盖](https://docs.docker.com/compose/extends/#different-environments)

    **Example**

    docker-compose.yml

    ```yml
    web:
      image: example/my_web_app:latest
      depends_on:
        - db
        - cache
        
    db:
      image: postgres:latest
      
    cache:
      image: redis:latest
    ```

    docker-compose.prod.yml

    ```yml
    web:
      ports:
        - 80:80
      environment:
        PRODUCTION: 'true'
    
    cache:
      environment:
        TTL: '500'
    ```

    运行

    ```shell
    $ docker-compose -f docker-compose.yml -f docker-compose.prod.yml up -d
    ```

- 只部署改变

  ```shell
  $ docker-compose build web
  $ docker-compose up --no-deps -d web
  ```

  重新构建web，并重新启动和web所依赖的container

- 在单机上运行（远程）
  - 通过配置环境变量`DOCKER_HOST`, `DOCKER_TLS_VERIFY`, and `DOCKER_CERT_PATH`
  - - [ ] [Docker Machine Overview](https://docs.docker.com/machine/overview/)

- 在Swarm上运行
  - [ ] [Use Compose with Swarm](https://docs.docker.com/compose/swarm/)

#### [Networking in compose](https://docs.docker.com/compose/networking/)

- compose会按照project name默认设置一个bridge network，并且每个服务以service的名字在里面注册DNS

  比如如下的docker-compose.yml, 运行`	docker-compose up`时，会启动`myapp_default`网络, 两个container的hostname分别为`web`, `db`.

  ```yml
  version: "3"
  services:
    web:
      build: .
      ports:
        - "8000:8000"
    db:
      image: postgres
      ports:
        - "8001:5432"
  ```

- 使用Swarm来运行compose时，会自动启动一个overlay network.在集群的上方提供统一的网络抽象。

----

### Docker Stack

用于将docker-compose部署到Swarm上，每一个节点的同一个port都能访问到这个服务。