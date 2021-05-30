DROP DATABASE IF EXISTS `db_sr03`;
CREATE DATABASE `db_sr03`;
USE `db_sr03`;

CREATE USER 'chat_user'@'localhost' IDENTIFIED WITH mysql_native_password BY '1gqjwTazw3qIaFgQ';
GRANT SELECT, INSERT, UPDATE, DELETE, FILE ON *.* TO 'chat_user'@'localhost';

CREATE TABLE `users` (
 	`id` INT NOT NULL UNIQUE AUTO_INCREMENT,
  	`fname` VARCHAR(50) NOT NULL,
  	`lname` VARCHAR(50) NOT NULL,
  	`login` VARCHAR(50) NOT NULL UNIQUE,
  	`gender` enum('M','F') NOT NULL,
  	`pwd` varchar(50) NOT NULL,
  	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE `chat` (
	`id` INT NOT NULL UNIQUE AUTO_INCREMENT,
	`title` VARCHAR(50) NOT NULL UNIQUE,
	`owner` VARCHAR(50) NOT NULL,
	`description` VARCHAR(250) NOT NULL,
	`date_creation` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`date_peremption` DATE NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
	
CREATE TABLE `participant_chat` (
	`login` VARCHAR(50) NOT NULL,
	`chat` VARCHAR(50) NOT NULL,
	PRIMARY KEY(`login`,`chat`),
	FOREIGN KEY(`login`) REFERENCES users(login),
	FOREIGN KEY(`chat`) REFERENCES chat(title)
)  ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;