use `alipay`;


DROP TABLE IF EXISTS `alipay`.`bill`;
DROP TABLE IF EXISTS `alipay`.`user`;

CREATE TABLE `alipay`.`user` (
  `username` VARCHAR(45) NULL,
  `phone` VARCHAR(20) NOT NULL,
  `uid` INT NOT NULL AUTO_INCREMENT,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC),
  PRIMARY KEY (`uid`));


CREATE TABLE `alipay`.`bill` (
  `bid` VARCHAR(45) NOT NULL,
  `tid` VARCHAR(45) NOT NULL,
  `uid` INT NOT NULL,
  `shop_bid` VARCHAR(45),
  `product_info` VARCHAR(80) NULL,
  `time` TIMESTAMP NOT NULL,
  `target_account` VARCHAR(20) NULL,
  `in` FLOAT NOT NULL,
  `out` FLOAT NOT NULL,
  `payment_approach` VARCHAR(16) NULL,
  `comment` VARCHAR(60) NULL,
  PRIMARY KEY (`bid`),
  UNIQUE INDEX `shop_bid_UNIQUE` (`shop_bid` ASC),
  FOREIGN KEY (`uid`) references `alipay`.`user`(`uid`)
  );
