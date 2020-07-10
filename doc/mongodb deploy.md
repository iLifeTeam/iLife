### MongoDB Deploy

---

```
$ apt install mongodb
$ service mongodb start
```

创建一个有admin权限的用户

```
$ mongo
进入mongo客户端,输入
> use admin
> db.createUser(
   {
     user: "ilife",
     pwd: "ilife2020",
     roles:
       [
         { role: "readWriteAnyDatabase", db: "admin" },
       ]
   }
)
```

暴露端口到公网

```
$ vim /etc/mongod.conf
修改如下两行
bind_ip = 0.0.0.0 (暴露到公网ip)
port = 27017 (监听的端口，未来部署的时候应该设置为非默认端口)
```

