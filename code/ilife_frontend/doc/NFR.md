# NFR 测试

### 简介
- **Non-funtional requirements**
- 非功能测试是针对软件应用程序或系统的非功能需求进行的测试： 系统的运行方式，而不是系统的特定行为。 这与功能测试相反，功能测试针对描述系统功能及其组件的功能需求进行测试。 由于各种非功能需求之间的范围重叠，因此许多非功能测试的名称通常可以互换使用。 例如，软件性能是一个广义术语，其中包括许多特定要求，例如可靠性和可伸缩性。
- 一般的需求分为FRS和NFR，指的是功能性需求和非功能性需求
- 一般的NFR就是指PT了，所有一般安全测试也是属于性能测试的范围，但一般的性能测试主要是测试稳定性，压力测试，负载测试等。

### NFR例子
- 一个系统要显示数据库中记录的条目的数量，这个是功能需求，但如何更新显示条目数的数据就是非功能性需求。若需要即时更新显示数据，系统架构需允许系统在数据条目数量变化后，经过一小段可接受的时间后就要更新显示数据。足够的网络带宽可能就是非功能性需求中的一部分。
- 具体的NFR例子可以参考[维基百科](https://en.wikipedia.org/wiki/Non-functional_requirement)

### 以移动端举例
- **Mobile App中的CFR** [参考链接](https://www.cnblogs.com/mengdd/p/cross-functional-requirements-for-mobile-apps.html)
- Accessibility 无障碍
  - 是否支持有特殊需求的人使用, 比如支持屏幕阅读.
  - UX设计上是否要考虑对比度要求?

- Authentication & Authorisation 认证和授权
  - 用户是如何认证的?
  - 应该遵循哪些标准或应用哪些现有的身份验证系统?
  - 哪些角色和权限是必须的?

- Capacity 容量
  - 数据体量是多大?
  - 会有多少用户同时使用?
  - 单个用户所使用到的数据类型和大小?
  - 有特殊的存储要求吗?

- Compatibility 兼容性
  - 支持Android和iOS?
  - Android/iOS最低/最高支持版本?
  - Android需要支持哪些厂商的哪些机型?

- Configurability 可配置性
  - 用户或管理员可以配置功能行为吗?
  - 这是如何管理的?
  - 用Firebase做A/B test?

- Continuity 连续性
  - 灾难恢复计划?

- Data Integrity and Consistency 数据完整和一致性
  - 需要数据同步吗?
  - 需要支持离线操作吗?
  - 数据校验?
  - 冲突解决?

- Data Privacy 隐私
  - 什么数据不应该存储?
  - 哪些数据需要加密?
  - 哪些数据可见/不可见?
  - 开发测试时是否可以利用产品数据?

- Distributability 分发性
  - App使用是否有地域限制?
  - 可以离线使用吗?
  - 如何同步信息?

- Extensibility 扩展性
  - 是否提供比如插件平台, 用户可以自行扩展?
  - 需要考虑什么样的限制和安全需求?

- Help & Support 帮助支持
  - 需要文档支持吗?
  - 需要提供用户使用向导吗?
  - 需要客服吗?

- Installability & Deployment 安装和部署
  - 需要支持什么平台? (Android/iOS/?)
  - 需要在哪里分发应用?

- Legal Compliance 法律规定
  - 是否有任何法律上的限制?
  - 使用的第三方软件/库的license?
  - 是否需要用户同意/授权/查看相关条款?

- Localisation & Internationalisation 国际化
  - 是否支持多个语言?
  - 日期/时间/货币的转换和翻译?

- Maintainability 可维护性
  - 崩溃率要求?
  - ANR率要求?
  - 最大可容忍的停机时间? (Maximum tolerable downtime (RTO / Recovery Time Objective))
  - 是否支持热更新?

- Monitoring 监控
  - 需要监控哪些数据? (Crash, ANR, ...)
  - 怎么监控的?
  - 需要什么样的警报?
  - 是否运用了合理的工具?

- Performance 性能
  - 应用安装包大小? -> 用户下载安装一个应用的时间.
  - 启动时间要求?
  - 设备内存使用要求?
  - 哪些请求的响应时间要求是比较重要的? (比如登录时间必须要在5s内?)
  - 弱网络下的要求?
  - 屏幕帧率要求? 卡顿容忍?
  - 电量消耗要求?
  - 是否有做性能测试的要求?

- Resilience & Fault Tolerance 弹性和容错性
  - 如果发生了错误(比如外部依赖不可用或出错), 系统的功能应该如何降级表现?

- Reliability 可靠性
  - 需要搞清楚系统不准确的商业代价是什么, 需要多大程度上的保证准确性.
  - (比如航天飞机和游戏应用的准确性可靠性要求肯定是不一样的).

- Scalability 可扩展性
  - 数据量的变化范围?
  - 如何测试这一点?

- Security 安全性
  - 哪些数据需要被保护? (考虑加密, 存储位置, 传输方式).
  - 是否需要限制设备类型(比如限制root设备)?
  - 商业准则?
  - API使用限制?
  - WebView中的安全措施?
  - 是否需要安全键盘? (如银行类应用)
  - 哪些地方需要对用户输入进行合法性验证?
  - 第三方工具的安全漏洞?
  - 代码防反编译?
  - 需要进行哪些安全测试?

- Usability and user experience 用户体验
  - UX的重要性? (只是可用就可以? 还是Good to have? 美观大方非常重要?)
  - 需要考虑不同的设备(平板?)和屏幕尺寸吗? 兼顾范围?
  - 要支持横竖屏切换吗?
  - 有企业内部需要遵循的UX Guidelines吗?