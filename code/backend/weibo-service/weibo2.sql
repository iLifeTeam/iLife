/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     7/8/2020 3:13:31 PM                          */
/*==============================================================*/


drop table if exists comment;

drop table if exists user;

drop table if exists weibo;

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

