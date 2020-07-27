use `taobao`;


DROP TABLE IF EXISTS `taobao`.`item`;
DROP TABLE IF EXISTS `taobao`.`order`;
DROP TABLE IF EXISTS `taobao`.`user`;


CREATE TABLE `taobao`.`user` (
  `uid` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NULL,
  `last_update` DATE NULL,
  PRIMARY KEY (`uid`));

CREATE TABLE `taobao`.`order` (
  `id` BIGINT(20) NOT NULL,
  `date` DATE NULL,
  `total` DOUBLE NULL,
  `shop` VARCHAR(100) NULL,
  `uid` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uid`) references `taobao`.`user`(`uid`)
  );

CREATE TABLE `taobao`.`item` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `product` VARCHAR(200) NULL DEFAULT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  `number` INT(11) NULL DEFAULT NULL,
  `img_url` VARCHAR(200) NULL DEFAULT NULL,
  `order_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`order_id`) references `taobao`.`order`(`id`)
  );
