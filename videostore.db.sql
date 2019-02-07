BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `movie` (
	`id`	INTEGER,
	`name`	TEXT,
	`genre`	TEXT,
	`year`	INTEGER,
	`actor`	TEXT,
	`production`	TEXT,
	`quantity`	INTEGER,
	PRIMARY KEY(`id`)
);
INSERT INTO `movie` VALUES (1,'Bumblebee','Akcija',2018,'Hailee Steinfeld ','Marvel',5);
CREATE TABLE IF NOT EXISTS `member` (
	`id`	INTEGER,
	`name`	TEXT,
	`mobile`	TEXT,
	`email`	TEXT,
	PRIMARY KEY(`id`)
);
INSERT INTO `member` VALUES (1,'Mujo Mujic','060/123-456','mujo@live.com');
CREATE TABLE IF NOT EXISTS `issued` (
	`id`	INTEGER,
	`movie`	TEXT,
	`member`	TEXT,
	`date_charge`	DATE,
	`date_discharge`	DATE,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`movie`) REFERENCES `movie`(`id`),
	FOREIGN KEY(`member`) REFERENCES `member`(`id`)
);
INSERT INTO `issued` VALUES (1,'1','1','01.01.2019.','');
COMMIT;
