## 7.8

#### 前端处理weibo授权

- 尝试使用`Oauth2/authorize`与`oauth2/access_token`接口处理微博授权
- 使用`authorize`接口时碰到跨域问题，转而使用postman直接发送请求，页面问题较多，重定向也问题连连，未果。
- 前端直接使用axios发送post请求，出现问题较多较为杂乱，且同组杨亘尝试别的微博爬虫有进展，暂时先放一放前端weibo授权。
- 考虑先配置好Nginx，后统一解决代理跨域问题，进而完成后续第三方授权功能。

#### Docker+Nginx部署React前端到云服务器

- 架构：
  - 腾讯云服务器
    - 配置：1核 | 2GB | 1Mbps | 系统盘：高性能云硬盘 | 网络：Default-VPC
    - 公网IP：49.234.125.131
    - 内网IP：172.17.0.12
    - OS：CentOS 7 
  - 容器：docker + docker compose
    - 使用国内源加速 `https://registry.docker-cn.com`
  - 反向代理：Nginx
    - TODO：跨域（weibo、alipay等）

- 暂时部署在云服务器上

#### 后续工作

1. 跨域代理，第三方授权
2. 与后端同学商量api接口，统一对应用功能的认知
3. 确定后续前端组件切分方向，完善逻辑（这是个长远的计划）
4. 下周初步安排：查查负载均衡和单元测试、压测方法，在前端先尝试，后续写成文档可以用于同组成员参考。

## 7.9

#### 研究Nginx跨域

#### alipay
- 在支付宝开放中心注册应用，并生成公钥私钥
- npm搭载alipay-SDK
- 不会，好难