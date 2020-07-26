use `jingdong`;


DROP TABLE IF EXISTS `jingdong`.`item`;
DROP TABLE IF EXISTS `jingdong`.`order`;
DROP TABLE IF EXISTS `jingdong`.`user`;


CREATE TABLE `jingdong`.`user` (
  `uid` VARCHAR(20) NOT NULL,
  `last_update` int null ,
  PRIMARY KEY (`uid`));

CREATE TABLE `jingdong`.`order` (
  `id` BIGINT(20) NOT NULL,
  `date` DATE NULL,
  `total` DOUBLE NULL,
  `shop` VARCHAR(100) NULL,
  `uid` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uid`) references `jingdong`.`user`(`uid`)
  );

CREATE TABLE `jingdong`.`item` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `product` VARCHAR(200) NULL DEFAULT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  `number` INT(11) NULL DEFAULT NULL,
  `img_url` VARCHAR(200) NULL DEFAULT NULL,
  `order_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`order_id`) references `jingdong`.`order`(`id`)
  );
