create database if not exists `zhihu`;
use `zhihu`;
DROP TABLE IF EXISTS `zhihu`.`answer`;
DROP TABLE IF EXISTS `zhihu`.`activity`;
DROP TABLE IF EXISTS `zhihu`.`question`;
DROP TABLE IF EXISTS `zhihu`.`article`;
DROP TABLE IF EXISTS `zhihu`.`user`;

CREATE TABLE `zhihu`.`user`
(
    `uid`           VARCHAR(45) NOT NULL,
    `name`          VARCHAR(45) NULL,
    `email`         VARCHAR(45) NULL,
    `phone`         VARCHAR(45) NULL,
    `answer_count`  INT         NULL,
    `gender`        INT         NULL,
    `voteup_count`  INT         NULL,
    `thanked_count` INT         NULL,
    PRIMARY KEY (`uid`)
) character set utf8mb4;

CREATE TABLE `zhihu`.`activity`
(
    `id`          VARCHAR(30)  NOT NULL,
    `target_id`   VARCHAR(30)  NOT NULL,
    `zhihu_uid`   VARCHAR(55)  NOT NULL,
    `type`        VARCHAR(50)  NOT NULL,
    `action_text` VARCHAR(200) NULL,
    `create_time` TIMESTAMP    NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`zhihu_uid`) references `zhihu`.`user` (`uid`)
) character set utf8mb4;

-- activity type :

CREATE TABLE `zhihu`.`question`
(
    `id`           VARCHAR(30)  NOT NULL,
    `title`        VARCHAR(100)  NULL,
    `create_time`  TIMESTAMP    NULL,
    `update_time`  TIMESTAMP    NULL,
    `excerpt`      VARCHAR(3000) NULL,
    `content`      MEDIUMTEXT   NULL,
    `answer_count` INT          NULL,
    PRIMARY KEY (`id`)
) character set utf8mb4;

CREATE TABLE `zhihu`.`answer`
(
    `id`            VARCHAR(20)  NOT NULL,
    `question_id`   VARCHAR(30)  NOT NULL,
    `author`        VARCHAR(55)  NULL,
    `content`       MEDIUMTEXT   NULL,
    `create_time`   TIMESTAMP    NULL,
    `update_time`   TIMESTAMP    NULL,
    `excerpt`       VARCHAR(3000) NULL,
    `voteup_count`  INT          NULL,
    `comment_count` INT          NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`question_id`) references `zhihu`.`question` (`id`)
) character set utf8mb4;
CREATE TABLE `zhihu`.`article`
(
    `id`          VARCHAR(20)  NOT NULL,
    `title`       VARCHAR(45)  NULL,
    `author`      VARCHAR(45)  NULL,
    `excerpt`     VARCHAR(3000) NULL,
    `content`     MEDIUMTEXT   NULL,
    `image_url`   VARCHAR(250) NULL,
    `column_name` VARCHAR(400)  NULL,
    `update_time` TIMESTAMP    NULL,
    PRIMARY KEY (`id`)
) character set utf8mb4;


