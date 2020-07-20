drop database if exists iLife;
drop database if exists wyy;
drop database if exists weibo;






/* zhihu */
drop database if exists `zhihu`;
create database `zhihu`;
use `zhihu`;
DROP TABLE IF EXISTS `zhihu`.`answer`;
DROP TABLE IF EXISTS `zhihu`.`activity`;
DROP TABLE IF EXISTS `zhihu`.`question`;
DROP TABLE IF EXISTS `zhihu`.`article`;

CREATE TABLE `zhihu`.`activity` (
  `activity_id` INT NOT NULL AUTO_INCREMENT,
  `zhihu_user_id` VARCHAR(30) NOT NULL,
  `type` VARCHAR(20) NOT NULL,
  `action_text` VARCHAR(20) NULL,
  `target_id` INT NULL,
  `create_time` TIMESTAMP NULL,
  PRIMARY KEY (`activity_id`)
  )character set utf8mb4;
  
-- activity type : 

CREATE TABLE `zhihu`.`question` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NULL,
  `create_time` TIMESTAMP NULL,
  `update_time` TIMESTAMP NULL,
  `excerpt` VARCHAR(300) NULL,
  `content` MEDIUMTEXT NULL,
  `answer_count` INT NULL,
  PRIMARY KEY (`question_id`)
  )character set utf8mb4;
  
CREATE TABLE `zhihu`.`answer` (
  `answer_id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `author` VARCHAR(45) NULL,
  `content` MEDIUMTEXT NULL,
  `create_time` TIMESTAMP NULL,
  `update_time` TIMESTAMP NULL,
  `excerpt` VARCHAR(300) NULL,
  `voteup_count` INT NULL,
  `comment_count` INT NULL,
  PRIMARY KEY (`answer_id`),
  FOREIGN KEY (`question_id`) references `zhihu`.`question`(`question_id`)
  )character set utf8mb4;
CREATE TABLE `zhihu`.`article` (
  `article_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `author` VARCHAR(45) NULL,
  `excerpt` VARCHAR(300) NULL,
  `content` MEDIUMTEXT NULL,
  `image_url` VARCHAR(200) NULL,
  `column_name` VARCHAR(45) NULL,
  `update_time` TIMESTAMP NULL,
  PRIMARY KEY (`article_id`)
  )character set utf8mb4;





/* wyy */
CREATE database wyy;
USE wyy;
/*==============================================================*/
/* Table: musics                                                */
/*==============================================================*/
create table musics
(
   m_id                 bigint not null,
   mname                varchar(128),
   style                varchar(20),
   times                varchar(20),
   primary key (m_id)
)DEFAULT CHARSET=utf8mb4;



/*==============================================================*/
/* Table: sing                                                  */
/*==============================================================*/
create table sing
(
   singid               bigint not null AUTO_INCREMENT,
   m_id                 bigint not null,
   s_id                 bigint not null,
   sname                varchar(128),
   primary key (singid),
   foreign key (m_id) references musics (m_id)
)DEFAULT CHARSET=utf8mb4;

/*==============================================================*/
/* Table: wyyuser                                               */
/*==============================================================*/
create table wyyuser
(
   hisid                bigint not null AUTO_INCREMENT,
   wyyid                bigint not null,
   m_id                 bigint not null,
   playcount            int,
   score                int,
   primary key (hisid),
   foreign key (m_id) references musics (m_id) 
)DEFAULT CHARSET=utf8mb4;

/* weibo */
create database weibo;
use weibo;



/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                 bigint not null,
   followers            int,
   following            int,
   weibo_num            int,
   birthday             varchar(128),
   nickname             varchar(128),
   gender               varchar(128),
   location             varchar(128),
   description          varchar(128),
   education            varchar(128),
   work                 varchar(128),
   primary key (id)
)DEFAULT CHARSET=utf8mb4;

/*==============================================================*/
/* Table: weibo                                                 */
/*==============================================================*/
create table weibo
(
   id                   varchar(32) not null,
   user_id              bigint,
   content              varchar(1024),
   publish_place        varchar(32),
   up_num               integer,
   retweet_num          integer,
   comment_num          integer,
   publish_time         DATETIME,
   primary key (id)
)DEFAULT CHARSET=utf8mb4;


alter table weibo add constraint FK_publish foreign key (user_id)
       references user (id) on delete restrict on update restrict;



/* ilife  */
create database iLife;
use iLife;
create table users
(
   id                   bigint not null AUTO_INCREMENT,
   wyyid                bigint,
   weibid               bigint,
   zhid                 varchar(30),
   nickname             varchar(20),
   account              char(20),
   password             char(20),
   email                varchar(50),
   primary key (id)
)DEFAULT CHARSET=utf8mb4;



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
)DEFAULT CHARSET=utf8mb4;