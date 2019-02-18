CREATE TABLE `lacus`.`users` (
  `userid` INT(5) NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(32) NOT NULL,
  `surname` VARCHAR(32) NOT NULL,
  `id` VARCHAR(32) NOT NULL,
  `psw` VARCHAR(32) NOT NULL,
  `email` VARCHAR(32) NOT NULL,
  `cf` CHAR(16) NOT NULL,
  `city` VARCHAR(32) NOT NULL,
  `street` VARCHAR(64) NOT NULL,
  `street_number` INT(5) NOT NULL,
  PRIMARY KEY (`userid`, `cf`));
  
CREATE TABLE `lacus`.`shipment` (
  `shipment_id` INT(5) NOT NULL AUTO_INCREMENT,
  `status` TINYINT(1) NOT NULL,
  `description` VARCHAR(64) NOT NULL,
  `sender_id` INT(5) NOT NULL,
  `sender_city` VARCHAR(32) NOT NULL,
  `sender_street` VARCHAR(64) NOT NULL,
  `sender_street_number` INT(5) NOT NULL,
  `carrier_id` INT(5),
  `payment` DOUBLE,
  `recipient_id` INT(5) NOT NULL,
  `recipient_city` VARCHAR(32) NOT NULL,
  `recipient_street` VARCHAR(64) NOT NULL,
  `recipient_street_number` INT(5) NOT NULL,
  PRIMARY KEY (`shipment_id`));

CREATE TABLE `lacus`.`cities` (
  `city` VARCHAR(32) NOT NULL,
  `province` CHAR(2) NOT NULL,
  `region` CHAR(3) NOT NULL,
  `CAP` INT(5) NOT NULL,
  PRIMARY KEY (`city`));
  
CREATE TABLE `lacus`.`email` (
  `email` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`email`));