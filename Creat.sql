CREATE SCHEMA `cs_6359`;

CREATE TABLE `cs_6359`.`Users` (
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  `Usertype` TINYINT NOT NULL,
  PRIMARY KEY (`Username`)
);

CREATE TABLE `cs_6359`.`Booklist`(
`ISBN` VARCHAR(45) NOT NULL,
`Title` VARCHAR(45) NOT NULL,
`Author` VARCHAR(45) NOT NULL,
`Category` VARCHAR(45)  NULL,
`Edition` VARCHAR(45)  NULL,
PRIMARY KEY (`ISBN`)
);

CREATE TABLE `cs_6359`.`Collection` (
  `ISBN` VARCHAR(20) NOT NULL,
  `CopyID` INT NOT NULL,
  `Status` TINYINT NULL,
  `RentedBy` VARCHAR(45) NULL,
  `CheckOutDate` DATETIME NULL,
  `RentedByDate` DATETIME NULL,
  PRIMARY KEY (`ISBN`,`CopyId`),
  FOREIGN KEY (`ISBN`) REFERENCES `Booklist`(`ISBN`),
  FOREIGN KEY (`RentedBy`) REFERENCES `Users`(`Username`)
);

INSERT INTO `cs_6359`.`Users` VALUES(
'admin','admin','Admin','1'
);
INSERT INTO `cs_6359`.`Users` VALUES(
'customer 1','customer 1','Customer 1','0'
);
INSERT INTO `cs_6359`.`Booklist` VALUES(
'111-111-11-1','testTitle','testAuthor','testCategory','testEdition'
);
