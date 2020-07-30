drop database if exists bilibili;
create database bilibili;
use bilibili;
create table biliuser
(
    mid                  bigint not null,
    uname                VARCHAR(1024),
    primary key (mid)
)DEFAULT CHARSET=utf8mb4;

create table video
(
    oid                  bigint not null,
    type                 VARCHAR(20),
    auther_name          VARCHAR(1024),
    auther_id            bigint not null,
    tag_name             VARCHAR(100),
    title                VARCHAR(1024),
    primary key(oid,type)
)DEFAULT CHARSET=utf8mb4;

create TABLE history
(
    hisid                bigint not null AUTO_INCREMENT,
    mid                  bigint not null,
    oid                  bigint not null,
    type                 VARCHAR(20),
    is_fav               boolean,
    primary key(hisid),
    foreign key(mid) references biliuser(mid),
    foreign key(oid,type) references video(oid,type)
)DEFAULT CHARSET=utf8mb4;