drop database if exists iLife;
drop database if exists wyy;
drop database if exists weibo;

/* wyy */
CREATE database wyy;
USE wyy;
/*==============================================================*/
/* Table: musics                                                */
/*==============================================================*/
create table musics
(
   m_id                 int not null,
   mname                varchar(20),
   style                varchar(20),
   times                varchar(20),
   primary key (m_id)
);

/*==============================================================*/
/* Table: singers                                               */
/*==============================================================*/
create table singers
(
   s_id                 int not null,
   sname                varchar(20),
   primary key (s_id)
);

/*==============================================================*/
/* Table: sing                                                  */
/*==============================================================*/
create table sing
(
   m_id                 int not null,
   s_id                 int not null,
   primary key (m_id, s_id),
   foreign key (m_id) references musics (m_id) ,
   foreign key (s_id) references singers (s_id)
);

/*==============================================================*/
/* Table: wyyuser                                               */
/*==============================================================*/
create table wyyuser
(
   wyyid                int not null,
   m_id                 int not null,
   playcount            int,
   score                int,
   primary key (wyyid,m_id),
   foreign key (m_id) references musics (m_id) 
);

/* weibo */
create database weibo;
use weibo;


/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     7/8/2020 11:49:25 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: comments                                              */
/*==============================================================*/
create table comments
(
   pid                  bigint not null,
   uid                  bigint,
   wid                  bigint,
   text                 varchar(1024),
   created_at           date,
   attitudes_count      integer,
   primary key (pid)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   uid                  bigint not null,
   name                 varchar(1024) not null,
   followers_count      int,
   followings_count     int,
   weibo_count          int,
   friends_count        int,
   register_time        date,
   username             varchar(1024),
   primary key (uid)
);

/*==============================================================*/
/* Table: weibo                                                 */
/*==============================================================*/
create table weibo
(
   wid                  bigint not null,
   uid                  bigint,
   text                 varchar(1024),
   reposts_count        integer,
   comments_count       integer,
   attitudes_count      integer,
   read_count           integer,
   primary key (wid)
);

alter table comments add constraint FK_comment foreign key (wid)
      references weibo (wid) on delete restrict on update restrict;

alter table comments add constraint FK_issue foreign key (uid)
      references user (uid) on delete restrict on update restrict;

alter table weibo add constraint FK_publish foreign key (uid)
      references user (uid) on delete restrict on update restrict;



/* ilife  */
create database iLife;
use iLife;
create table users
(
   id                   int not null,
   wyyid                int,
   weibid               bigint,
   nickname             varchar(20),
   account              char(20),
   password             char(20),
   email                varchar(50),
   primary key (id),
   foreign key (wyyid) references wyy.wyyuser (wyyid),
   foreign key (weibid) references weibo.user (uid)
);