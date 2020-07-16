## Database Deploy

### mongoDB

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

---

**Mysql** 

---

```
配置允许远程连接
$ vim /etc/mysql/mysql.conf.d/mysqld.cnf
修改bind-address = 0.0.0.0
$ mysql
> grant all privileges on *.* to 'username'@'%' identified by 'password' with grant option;
> flush privileges;
```



```	
$ vim /etc/mysql/mysql.conf.d/mysqld.cnf
加一行 character-set-server = utf8mb4
$ service mysql restart
```

