# Jmeter的配置与使用
## Jmeter的配置与简单使用
https://www.cnblogs.com/monjeo/p/9330464.html  
## Jmeter解决security（携带cookie访问）
https://www.cnblogs.com/mululu/p/8664031.html  （只需添加cookie即可）
**********
# Jmeter的完整使用步骤
1. 右击test plan，add thread group
2. 添加http请求：右击**Thread group，add->Sampler->http request**
3. 添加cookie管理器：右击**Thread group，add->Config Element->Http Cookie Manager**，cookie需手动设置，postman发送请求到http://18.162.168.229:8686/login获取cookie
4. 添加监听器：右击**Thread grouo，add->Listener->Summary Report,View Results Tree,Graph Results**
5. 配置http请求以及thread，之后点击start就可以测试了