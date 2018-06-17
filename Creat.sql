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
`Status` TINYINT NULL,
PRIMARY KEY (`ISBN`)
);

CREATE TABLE `cs_6359`.`Collection` (
  `ISBN` VARCHAR(20) NOT NULL,
  `CopyID` INT NOT NULL,
  `Status` TINYINT NULL,
  `RentedBy` VARCHAR(45) NULL,
  `CheckOutDate` DATETIME NULL,
  `ReturnByDate` DATETIME NULL,
  PRIMARY KEY (`ISBN`,`CopyId`),
  FOREIGN KEY (`ISBN`) REFERENCES `Booklist`(`ISBN`),
  FOREIGN KEY (`RentedBy`) REFERENCES `Users`(`Username`)
);


Insert Data
INSERT INTO `cs_6359`.`Users`
 VALUES 	('admin','admin','Admin','1'),
		    ('admin1','admin','Admin1','1'),
        		('admin2','admin','Admin2','1'),
		('customer1','1234','Customer 1','0'),        
		('customer2','1234','Customer 1','0'),        
		('customer3','1234','Customer 1','0');
        
INSERT INTO `cs_6359`.`Booklist` 
 VALUES	('111-111-11-1','testTitle','testAuthor','testCategory','testEdition',1),
		('111-111-11-2','Harry Potter','J. K. Rowling','Fantasy Fiction','1',1),
		('111-111-11-3','Twilight','Stephenie Meyer','fantasy','2',0),
       		('111-111-11-4','testTitle1','testAuthor1','testCategory1','1',0),
        		('111-111-11-5','testTitle2','testAuthor2','testCategory2','3',1),
        		('111-111-11-6','testTitle3','testAuthor3','testCategory3','1',1);







