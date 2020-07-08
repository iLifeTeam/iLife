/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/7 15:44:12                            */
/*==============================================================*/


drop table if exists musics;

drop table if exists sing;

drop table if exists singers;

drop table if exists users;

drop table if exists wyyuser;

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



/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   id                   int not null,
   wyyid                int,
   nickname             varchar(20),
   account              char(20),
   password             char(20),
   email                varchar(50),
   primary key (id),
   foreign key (wyyid) references wyyuser (wyyid) 
);







