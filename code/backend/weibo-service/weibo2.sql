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
);

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
);

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
);

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
   id                   int not null,
   wyyid                int,
   weibid               bigint,
   nickname             varchar(20),
   account              char(20),
   password             char(20),
   email                varchar(50),
   primary key (id),
   foreign key (wyyid) references wyy.wyyuser (wyyid),
   foreign key (weibid) references weibo.user (u_id)
);
