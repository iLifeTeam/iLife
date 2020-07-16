use `zhihu`;
DROP TABLE IF EXISTS `zhihu`.`answer`;
DROP TABLE IF EXISTS `zhihu`.`activity`;
DROP TABLE IF EXISTS `zhihu`.`question`;
DROP TABLE IF EXISTS `zhihu`.`article`;

CREATE TABLE `zhihu`.`activity` (
  `activity_id` INT NOT NULL,
  `zhihu_user_id` VARCHAR(30) NOT NULL,
  `type` VARCHAR(20) NOT NULL,
  `action_text` TIMESTAMP NULL,
  `target_id` INT NULL,
  `created_time` TIMESTAMP NULL,
  PRIMARY KEY (`activity_id`));

-- activity type :

CREATE TABLE `zhihu`.`question` (
  `question_id` INT NOT NULL,
  `title` VARCHAR(50) NULL,
  `create_time` TIMESTAMP NULL,
  `update_time` TIMESTAMP NULL,
  `excerpt` VARCHAR(100) NULL,
  `content` VARCHAR(1000) NULL,
  `answer_count` INT NULL,
  PRIMARY KEY (`question_id`));

CREATE TABLE `zhihu`.`answer` (
  `answer_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  `author` VARCHAR(45) NULL,
  `content` VARCHAR(1000) NULL,
  `create_time` TIMESTAMP NULL,
  `update_time` TIMESTAMP NULL,
  `excerpt` VARCHAR(100) NULL,
  `voteup_count` INT NULL,
  `comment_count` INT NULL,
  PRIMARY KEY (`answer_id`),
  FOREIGN KEY (`question_id`) references `zhihu`.`question`(`question_id`)
  );
CREATE TABLE `zhihu`.`article` (
  `article_id` INT NOT NULL,
  `title` VARCHAR(45) NULL,
  `author` VARCHAR(45) NULL,
  `excerpt` VARCHAR(100) NULL,
  `content` VARCHAR(1000) NULL,
  `image_url` VARCHAR(45) NULL,
  `column_name` VARCHAR(45) NULL,
  `create_time` TIMESTAMP NULL,
  `update_time` TIMESTAMP NULL,
  PRIMARY KEY (`article_id`));

