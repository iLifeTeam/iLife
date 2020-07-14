# 拉钩-爬虫知识点
## map() 
自动任务分配，依次执行url数组，方便多进程爬虫

    def scrape(url):
    try:
        urllib.request.urlopen(url)
        print(f'URL {url} Scraped')
    except (urllib.error.HTTPError, urllib.error.URLError):
        print(f'URL {url} not Scraped')
	if __name__ == '__main__':
    pool = Pool(processes=3)
    urls = [
        'https://www.baidu.com',
        'http://www.meituan.com/',
        'http://blog.csdn.net/',
        'http://xxxyxxx.net'
    ]
    pool.map(scrape, urls)
    pool.close()
 
## request的几个用法
需要获取图片时，直接存成对应储存格式即可.注意r.content才是原生（字节式）的字符串，而r.text返回的是编码过的unicode  

	r = requests.get(CONST.RESOURCES[0], headers=headers)
	# print(r.text)
	with open("picTest.png",'wb') as pic:
    pic.write(r.content)
 r.cookies 可以获取和设置cookies，然后在headers里可以设置Cookie:
 
	 'Cookie': '_octo=GH1.1.1849343058.1576602081; _ga=GA1.2.90460451.1576602
也可以在request里单独指定cookies

	jar = requests.cookies.RequestsCookieJar()
	for cookie in cookies.split(';'):
    key, value = cookie.split('=', 1)
    jar.set(key, value)
	r = requests.get('https://github.com/', cookies=jar, headers=headers)
## Session与SSL证书
request.Session()建立会话  
SSL 证书验证错误

	ssl.SSLCertVerificationError: [SSL: CERTIFICATE_VERIFY_FAILED] certificate verify failed: unable to get local issuer certificate (_ssl.c:1108)
通过添加verified参数解决

	response = requests.get('url', verify=False)
## 超时设置
不加参数时，默认不触发timeout

	r = requests.get('url', timeout=(5, 30))
## 身份认证
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200712220524167.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NDYwMjQwOQ==,size_16,color_FFFFFF,t_70)
使用request自带的auth参数即可

	r = requests.get('url', auth=('admin', 'admin'))
### 代理设置
在网上寻找有效的代理池，替换下文的IP

	proxies = {
  	'http': 'http://10.10.10.10:1080',
  	'https': 'http://10.10.10.10:1080',
	}
	requests.get('https://httpbin.org/get', proxies=proxies)
