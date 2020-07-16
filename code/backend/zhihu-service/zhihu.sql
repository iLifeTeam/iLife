use `zhihu`;
DROP TABLE IF EXISTS `zhihu`.`answer`;
DROP TABLE IF EXISTS `zhihu`.`activity`;
DROP TABLE IF EXISTS `zhihu`.`question`;
DROP TABLE IF EXISTS `zhihu`.`article`;
DROP TABLE IF EXISTS `zhihu`.`user`;

CREATE TABLE `zhihu`.`user` (
  `uid` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  `answer_count` INT NULL,
  `gender` INT NULL,
  `voteup_count` INT NULL,
  `thanked_count` INT NULL,
  PRIMARY KEY (`uid`)
  )character set utf8mb4;

CREATE TABLE `zhihu`.`activity` (
  `activity_id` INT NOT NULL AUTO_INCREMENT,
  `zhihu_uid` VARCHAR(45) NOT NULL,
  `type` VARCHAR(20) NOT NULL,
  `action_text` VARCHAR(20) NULL,
  `target_id` INT NULL,
  `create_time` TIMESTAMP NULL,
  PRIMARY KEY (`activity_id`),
  FOREIGN KEY (`zhihu_uid`) references `zhihu`.`user`(`uid`)
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


