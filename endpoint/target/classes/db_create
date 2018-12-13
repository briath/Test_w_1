# Create user (execute under root)
CREATE USER 'user'@'localhost' IDENTIFIED BY 'user';
GRANT ALL PRIVILEGES ON *.* TO 'user'@'localhost' WITH GRANT OPTION;

# Create database (execute under the user)
CREATE DATABASE testmethods;
USE testmethods;

# Create tables
CREATE TABLE `CUSTOMER` (
	`id` TEXT NOT NULL,
	`first_name` TEXT NOT NULL,
	`last_name` TEXT NOT NULL,
	`login` TEXT NOT NULL,
	`pass` TEXT NOT NULL,
	`balance` INT(11) NOT NULL DEFAULT '0'
);

CREATE TABLE `PLAN` (
	`id` TEXT NOT NULL,
	`name` TEXT NOT NULL,
	`details` TEXT NOT NULL,
	`fee` INT(11) NOT NULL
);

CREATE TABLE `SUBSCRIPTION` (
	`id` TEXT NOT NULL,
	`customer_id` TEXT NOT NULL,
	`plan_id` TEXT NOT NULL
);
