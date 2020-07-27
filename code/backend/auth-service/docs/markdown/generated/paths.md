
<a name="paths"></a>
## 资源

<a name="user-service-controller_resource"></a>
### User Service Controller
Everything about auth & CRUD of iLife User


<a name="authusingpost_7"></a>
#### User log in
```
POST /auth/auth
```


##### 说明
Auth one iLife user by giving account.password


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**501**|user not exists|无内容|
|**502**|account and password not match|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/auth
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="deletebyidusingpost_7"></a>
#### delete one user
```
POST /auth/delById
```


##### 说明
Delete one ilife user By user Id


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Body**|**userId**  <br>*可选*|The user ID of a iLife user|< string, string > map|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**501**|account or nickname already exists|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/delById
```


###### 请求 body
```json
{ }
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="getuserbyaccountusingget_7"></a>
#### get user info by account
```
GET /auth/getByAccount
```


##### 说明
Get user info by account


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**account**  <br>*可选*|The account number of a iLife user|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Users](#users)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/getByAccount
```


##### HTTP响应示例

###### 响应 200
```json
{
  "account" : "string",
  "biliid" : 0,
  "doubanid" : "string",
  "email" : "string",
  "id" : 0,
  "nickname" : "string",
  "password" : "string",
  "tbid" : "string",
  "type" : "string",
  "weibid" : 0,
  "wyyid" : 0,
  "zhid" : "string"
}
```


<a name="getuserbyidusingget_7"></a>
#### get user info by id
```
GET /auth/getById
```


##### 说明
Get user info by userID


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**userId**  <br>*可选*|The user ID of a iLife user|integer (int64)|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Users](#users)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/getById
```


##### HTTP响应示例

###### 响应 200
```json
{
  "account" : "string",
  "biliid" : 0,
  "doubanid" : "string",
  "email" : "string",
  "id" : 0,
  "nickname" : "string",
  "password" : "string",
  "tbid" : "string",
  "type" : "string",
  "weibid" : 0,
  "wyyid" : 0,
  "zhid" : "string"
}
```


<a name="getuserbynicknameusingget_7"></a>
#### get user info by nickname
```
GET /auth/getByNickname
```


##### 说明
Get user info by Nickname


##### 参数

|类型|名称|说明|类型|
|---|---|---|---|
|**Query**|**nickname**  <br>*可选*|The user nickname of a iLife user|string|


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|[Users](#users)|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/getByNickname
```


##### HTTP响应示例

###### 响应 200
```json
{
  "account" : "string",
  "biliid" : 0,
  "doubanid" : "string",
  "email" : "string",
  "id" : 0,
  "nickname" : "string",
  "password" : "string",
  "tbid" : "string",
  "type" : "string",
  "weibid" : 0,
  "wyyid" : 0,
  "zhid" : "string"
}
```


<a name="registerusingpost_7"></a>
#### User register
```
POST /auth/register
```


##### 说明
Register one iLife user by giving nickname,account.password and email


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|
|**500**|account already exists|无内容|
|**501**|nickname already exists|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/register
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="updatewbusingpost_7"></a>
#### update Weibo ID
```
POST /auth/updateWbId
```


##### 说明
update user's Weibo ID,return the number of affected rows


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/updateWbId
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="updatewyyusingpost_7"></a>
#### update wyy ID
```
POST /auth/updateWyyId
```


##### 说明
update user's WangYiYun ID，return the number of affected rows


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/updateWyyId
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="updatezhusingpost_7"></a>
#### update Zhihu ID
```
POST /auth/updateZhId
```


##### 说明
update user's Zhihu ID,return the number of affected rows


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/auth/updateZhId
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```


<a name="loginusingpost_7"></a>
#### update Weibo ID
```
POST /login
```


##### 说明
update user's Weibo ID,return the number of affected rows


##### 响应

|HTTP代码|说明|类型|
|---|---|---|
|**200**|OK|object|
|**204**|No Content|无内容|
|**401**|Unauthorized|无内容|
|**403**|Forbidden|无内容|


##### 消耗

* `application/json`


##### 生成

* `\*/*`


##### HTTP请求示例

###### 请求 path
```
/login
```


##### HTTP响应示例

###### 响应 200
```json
"object"
```



