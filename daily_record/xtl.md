### XTL's work record
#### week1
- 7.7 
  - 针对网易云音乐与用户建立数据库。
- 7.8 
  - 数据库
    - 整合数据库，将不同软件数据封装在不同的数据库中
    - 新增微博数据库
    - ilife用户新增weibid，与微博绑定
  - 数据爬取
    - 爬取大麦网演唱会信息，用于后续数据分析
- 7.9
  - 数据库
    - 完善微博数据库
    - 新建大麦网数据库
  - 数据爬取
    - 将大麦网爬取数据存入数据库中
- 7.10
  - 数据库
    - 脚本中加入了知乎的建库
    - user新增zhid字段
#### week2
- 7.13
  - 数据库更加简化，增加了必要冗余，简化后端数据库查询逻辑
  - 网易云音乐后端基本实现
  - TODO: 接口测试和docker部署
- 7.14
  - docker 打包jar
- 7.15，7.16
  - 实现分页查询，降低时延
  - TODO:部署在服务器端
- 7.17
  - 实现网易云推荐相似歌曲接口
#### week3
- 7.20
  - 初步部署b站接口，实现b站登录
  - 建立b站数据库
- 7.21
  - 获取用户个人数据，完善单元测试
  - 修复跨域bug
- 7.22
  - 初步进行压力测试
  - 加入安全
- 7.23 24
  - 获取b站视频信息，做博主和分区推荐
#### week4
- 7.27
  - 使用百度ai进行情感分析
- 7.28
  - 完善各个后端单元测试
  - 简单部署
- 7.29，30
  - 修复接口bug，b站实现后端分页
- 7.31
  - 对现有接口进行压测并分析优化 
