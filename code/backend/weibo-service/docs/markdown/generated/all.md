# Weibo Backend API Documentation


<a name="overview"></a>
## 概览

### 版本信息
*版本* : 1.0.0-SNAPSHOT


### 联系方式
*名字* : 杨亘@sjtu  
*邮箱* : 888888@qq.com


### URI scheme
*域名* : localhost:8080  
*基础路径* : /


### 标签

* user-service-controller : User Service Controller
* weibo-service-controller : Weibo Service Controller




<a name="paths"></a>
## 资源

<a name="user-service-controller_resource"></a>
### User-service-controller
User Service Controller


<a name="deluserbyuseridusingget_7"></a>
#### delete one user
```
GET /user/delById
```


##### 说明
Delete one user info specified by userId


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userId**  <br>*可选*|The user ID of a WeiBo user,should be a Long Integer|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/user/delById
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="getuserbyidusingget_7"></a>
#### get user info
```
GET /user/getById
```


##### 说明
Get user info by userID


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userId**  <br>*可选*|The user ID of a WeiBo user,should be a Long Integer|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[User](#user)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/user/getById
```


##### HTTP响应示例

###### 响应 200
```json
{
  "birthday" : "string",
  "description" : "string",
  "education" : "string",
  "followers" : 0,
  "following" : 0,
  "gender" : "string",
  "id" : 0,
  "location" : "string",
  "nickname" : "string",
  "weibo_num" : 0,
  "work" : "string"
}
```


<a name="getuserbynicknameusingget_7"></a>
#### get user info
```
GET /user/getByName
```


##### 说明
Get user info by nickname


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**nickname**  <br>*可选*|The nickname of a WeiBo user,should be a String|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[User](#user)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/user/getByName
```


##### HTTP响应示例

###### 响应 200
```json
{
  "birthday" : "string",
  "description" : "string",
  "education" : "string",
  "followers" : 0,
  "following" : 0,
  "gender" : "string",
  "id" : 0,
  "location" : "string",
  "nickname" : "string",
  "weibo_num" : 0,
  "work" : "string"
}
```


<a name="weibo-service-controller_resource"></a>
### Weibo-service-controller
Weibo Service Controller


<a name="deleteweibousingget_7"></a>
#### delete one Weibo
```
GET /weibo/deleteWeibo
```


##### 说明
Delete one Weibos from database specified by Weibo ID,success if the response.status = 200


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**Id**  <br>*可选*|The ID of a WeiBo,should be a String|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**501**|weiboId not exists|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/weibo/deleteWeibo
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="deleteweibosusingget_7"></a>
#### delete one user's Weibos
```
GET /weibo/deleteWeibos
```


##### 说明
Delete all Weibos from database of one user specified by userID,success if the response.status = 200


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userId**  <br>*可选*|The user ID of a WeiBo user,should be a Long Integer|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**501**|userId not exists|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/weibo/deleteWeibos
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="getweibousingget_7"></a>
#### get one Weibo
```
GET /weibo/getWeibo
```


##### 说明
Get One Weibo from database specified by Weibo ID


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**Id**  <br>*可选*|The ID of a WeiBo,should be a String|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Weibo](#weibo)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/weibo/getWeibo
```


##### HTTP响应示例

###### 响应 200
```json
{
  "comment_count" : 0,
  "content" : "string",
  "id" : "string",
  "publish_place" : "string",
  "publish_time" : "string",
  "retweet_num" : 0,
  "uid" : 0,
  "up_num" : 0
}
```


<a name="getweibosusingget_7"></a>
#### get one user's Weibos
```
GET /weibo/getWeibos
```


##### 说明
Get all Weibos from database of one user specified by userID


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userId**  <br>*可选*|The user ID of a WeiBo user,should be a Long Integer|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|< [Weibo](#weibo) > array|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/weibo/getWeibos
```


##### HTTP响应示例

###### 响应 200
```json
[ {
  "comment_count" : 0,
  "content" : "string",
  "id" : "string",
  "publish_place" : "string",
  "publish_time" : "string",
  "retweet_num" : 0,
  "uid" : 0,
  "up_num" : 0
} ]
```




<a name="definitions"></a>
## 定义

<a name="user"></a>
### User

|名称|说明|类型|
|---|---|---|
|**birthday**  <br>*可选*|**样例** : `"string"`|string|
|**description**  <br>*可选*|**样例** : `"string"`|string|
|**education**  <br>*可选*|**样例** : `"string"`|string|
|**followers**  <br>*可选*|**样例** : `0`|integer (int32)|
|**following**  <br>*可选*|**样例** : `0`|integer (int32)|
|**gender**  <br>*可选*|**样例** : `"string"`|string|
|**id**  <br>*可选*|**样例** : `0`|integer (int64)|
|**location**  <br>*可选*|**样例** : `"string"`|string|
|**nickname**  <br>*可选*|**样例** : `"string"`|string|
|**weibo_num**  <br>*可选*|**样例** : `0`|integer (int32)|
|**work**  <br>*可选*|**样例** : `"string"`|string|


<a name="weibo"></a>
### Weibo

|名称|说明|类型|
|---|---|---|
|**comment_count**  <br>*可选*|**样例** : `0`|integer (int32)|
|**content**  <br>*可选*|**样例** : `"string"`|string|
|**id**  <br>*可选*|**样例** : `"string"`|string|
|**publish_place**  <br>*可选*|**样例** : `"string"`|string|
|**publish_time**  <br>*可选*|**样例** : `"string"`|string (date-time)|
|**retweet_num**  <br>*可选*|**样例** : `0`|integer (int32)|
|**uid**  <br>*可选*|**样例** : `0`|integer (int64)|
|**up_num**  <br>*可选*|**样例** : `0`|integer (int32)|





