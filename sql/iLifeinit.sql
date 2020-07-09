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
   m_id                 bigint not null,
   mname                varchar(20),
   style                varchar(20),
   times                varchar(20),
   primary key (m_id)
)CHARSET=utf8;

/*==============================================================*/
/* Table: singers                                               */
/*==============================================================*/
create table singers
(
   s_id                 bigint not null,
   sname                varchar(20),
   primary key (s_id)
)CHARSET=utf8;

/*==============================================================*/
/* Table: sing                                                  */
/*==============================================================*/
create table sing
(
   m_id                 bigint not null,
   s_id                 bigint not null,
   primary key (m_id, s_id),
   foreign key (m_id) references musics (m_id) ,
   foreign key (s_id) references singers (s_id)
)CHARSET=utf8;

/*==============================================================*/
/* Table: wyyuser                                               */
/*==============================================================*/
create table wyyuser
(
   wyyid                bigint not null,
   m_id                 bigint not null,
   playcount            int,
   score                int,
   primary key (wyyid,m_id),
   foreign key (m_id) references musics (m_id) 
)CHARSET=utf8;

/* weibo */
create database weibo;
use weibo;


/*==============================================================*/
/* Table: comment                                              */
/*==============================================================*/
create table comment
(
   p_id                 bigint not null,
   u_id                 bigint,
   w_id                 bigint,
   text                 varchar(1024),
   created_at           date,
   attitudes_count      integer,
   direction            varchar(32),
   primary key (p_id)
)CHARSET=utf8;

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   u_id                 bigint not null,
   followers_count      int,
   followings_count     int,
   weibo_count          int,
   friends_count        int,
   register_time        date,
   nickname             varchar(1024),
   primary key (u_id)
)CHARSET=utf8;

/*==============================================================*/
/* Table: weibo                                                 */
/*==============================================================*/
create table weibo
(
   w_id                 bigint not null,
   u_id                 bigint,
   text                 varchar(1024),
   reposts_count        integer,
   comments_count       integer,
   attitudes_count      integer,
   read_count           integer,
   publish_time         date,
   primary key (w_id)
)CHARSET=utf8;

alter table comment add constraint FK_comment foreign key (w_id)
      references weibo (w_id) on delete restrict on update restrict;

alter table comment add constraint FK_issue foreign key (u_id)
      references user (u_id) on delete restrict on update restrict;

alter table weibo add constraint FK_publish foreign key (u_id)
      references user (u_id) on delete restrict on update restrict;



/* ilife  */
create database iLife;
use iLife;
create table users
(
   id                   bigint not null,
   wyyid                bigint,
   weibid               bigint,
   nickname             varchar(20),
   account              char(20),
   password             char(20),
   email                varchar(50),
   primary key (id),
   foreign key (wyyid) references wyy.wyyuser (wyyid),
   foreign key (weibid) references weibo.user (u_id)
)CHARSET=utf8;



/* 大麦网数据存储 */
drop database if exists dmw;
create database dmw;
use dmw;
create table dmw
(
	projectid            bigint not null,
	actors	            varchar(1024),
	name	               varchar(1024),
	price_str	         varchar(1024),
	showtime	            varchar(1024),
	img	               varchar(1024),
	showstatus	         varchar(1024),
   venue                varchar(1024),
   venuecity            varchar(1024),
	primary key(projectid)
)CHARSET=utf8;