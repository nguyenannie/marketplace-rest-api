# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.20)
# Database: marketplace
# Generation Time: 2018-12-27 21:57:25 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;

INSERT INTO `category` (`id`, `name`)
VALUES
	(1,'cat'),
	(2,'dog'),
	(3,'human'),
	(4,'book'),
	(5,'computers'),
	(6,'kitchen'),
	(7,'games'),
	(8,'beauty'),
	(9,'movie'),
	(10,'sport');

/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table product
# ------------------------------------------------------------

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `sales_unit` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `times_queried` int(11) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  KEY `FKesd6fy52tk7esoo2gcls4lfe3` (`seller_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;

INSERT INTO `product` (`id`, `description`, `name`, `price`, `sales_unit`, `stock`, `times_queried`, `category_id`, `seller_id`)
VALUES
	(1,'food for cat','cat food',800,5,6,5,1,3),
	(2,'toy for cat','cat toy',300,7,4,12,1,1),
	(3,'clothes for cat','cat clothes',1700,2,13,2,1,2),
	(4,'clothes for dog','dog clothes',2700,12,9,3,2,2),
	(5,'food for dog','dog food',400,8,2,10,2,4),
	(6,'toy for dog','dog toy',800,13,2,3,2,1),
	(7,'toy for human','human toy',1800,4,11,6,3,1),
	(8,'food for human','human food',1100,3,12,1,3,2),
	(9,'clothes for human','human clothes',3100,15,0,19,3,3),
	(10,'author: F. Scott Fitzgerald','The Great Gatsby',7000,0,25,0,4,16),
	(11,'author: Margaret Atwood','The Handmaid\'s Tale',7500,0,32,0,4,13),
	(12,'author: Harper Lee','To Kill A Mockingbird.',4500,0,21,0,4,13),
	(13,'author: Aldous Huxley','Brave New World',5100,6,19,6,4,16),
	(14,'author: J. D. Salinger','The Catcher in the Rye',5500,0,17,0,4,16),
	(15,'author: George Orwell','Nineteen Eighty-four',7100,0,21,2,4,10),
	(16,'author: Italo Calvino','If On A Winter\'s Night A Traveller',3700,0,25,0,4,10),
	(17,'author: William Golding','Lord of the Flies',6200,0,100,0,4,10),
	(18,'author: Agatha Christie','Murder on the Orient Express',4700,0,150,0,4,11),
	(19,'Nintendo Switch','Pokemon: Let\'s Go, Pikachu!',5330,0,98,0,7,12),
	(20,'Nintendo Switch','Super Mario Party',5690,0,91,0,7,12),
	(21,'XBOX 1','Farming Simulator 19',3999,0,76,0,7,9),
	(22,'Nintendo 3DS','Zelda',1100,0,79,0,7,9),
	(23,'Nintendo Wii','Just Dance 3',1200,5,84,7,7,8),
	(24,'PlayStation 4','Tetris Effect',1500,0,98,0,7,8),
	(25,'Original Beeswax with Vitamin E & Peppermint Oil –  4 Tubes','Burt\'s Bees 100% Natural Moisturizing Lip Balm',664,0,34,0,8,7),
	(26,'16 Combo Pack A','DERMAL Collagen Essence Full Face Facial Mask Sheet',1299,0,67,0,8,7),
	(27,'LilyAna Naturals','LilyAna Naturals Retinol Cream Moisturizer 1.7 Oz',1999,0,48,0,8,7),
	(28,'8 Fluid Ounce','True+real Foam Wash Daily Cleanser for Acne-prone Skin',699,0,91,0,8,6),
	(29,'12 Ounce | Face & Body Lotion for Dry Skin with Hyaluronic Acid | Fragrance Free','CeraVe Daily Moisturizing Lotion',997,0,103,0,8,6),
	(30,'All Natural Scrub to Exfoliate & Moisturize Skin, 10 oz','Majestic Pure Himalayan Salt Body Scrub with Lychee Essential Oil',1398,0,170,0,8,5),
	(31,'can be used for eczema, psoriasis, ichthyosis, and itch - dermatologist tested – free of dye, fragrance, and preservatives - 16 oz','Vanicream Moisturizing Skin Cream with pump for sensitive skin',1300,0,150,0,8,5),
	(32,' 7-in-1 Multi- Use Programmable Pressure Cooker, Slow Cooker, Rice Cooker, Steamer, Sauté, Yogurt Maker and Warmer','Instant Pot DUO80 8 Qt',8995,0,15,0,6,14),
	(33,'with Foil Cutter, FFP','Oster Cordless Electric Wine Bottle Opener',1779,0,17,0,6,14),
	(34,'K-Cup Pod, Single Serve, Programmable, Black','Keurig K55/K-Classic Coffee Maker',10334,0,21,0,6,14),
	(35,'16 XL Rolls, Pick-A-Size, White, 16 = 32 Regular Rolls','Brawny Paper Towels',2657,0,24,0,6,15),
	(36,'24 Rolls = 47 Regular Rolls, Longer Lasting Rolls, Pick-A-Size Plus Sheets','Sparkle Paper Towels',199,0,56,0,6,15),
	(37,'Pre-Seasoned Cast Iron Skillet Pan for Stovetop of Oven Use','Lodge 10.25 Inch Cast Iron Skillet',2190,0,24,0,6,17),
	(38,'Enclosure for 7mm and 9.5mm 2.5 Inch SATA HDD/SSD Tool Free [UASP Supported] Black(2189U3)','ORICO USB3.0 to SATA III 2.5\" External Hard Drive',2787,0,15,0,5,18),
	(39,'Lay-Flat Docking Station for 2.5 or 3.5in HDD, SSD [Support UASP] (EC-DFLT)','Sabrent USB 3.0 to SATA External Hard Drive ',1997,0,17,0,5,18),
	(40,'Surround Sound Over-Ear Headphones with Noise Cancelling Mic, LED Lights, Volume Control for Laptop, Mac, iPad, Nintendo Switch Games -Blue\nby VersionTECH','VersionTECH. G2000 Stereo Gaming Headset for Xbox One PS4 PC',2699,0,10,0,5,19),
	(41,'5-Movie Collection [Blu-ray]','Jurassic World',2799,0,0,0,9,20),
	(42,'70% off','Back to the Future: The Complete Adventures',1799,0,0,0,9,20),
	(43,'Fast & Furious: The Ultimate Ride Collection','Fast & Furious: The Ultimate Ride Collection',1999,0,31,0,9,14);

/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table rating
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `feedback` varchar(255) DEFAULT NULL,
  `rate` int(11) NOT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbk9wq1vygaoep00gcyb11dknu` (`seller_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;

INSERT INTO `rating` (`id`, `feedback`, `rate`, `seller_id`)
VALUES
	(1,'fine',3,2),
	(2,'fine',3,1),
	(3,'nice',4,1),
	(4,'nice',4,1),
	(5,'awesome',5,1),
	(6,'ehhh',2,2),
	(7,'ok',3,2),
	(8,'ehhh',2,2),
	(9,'nice',4,3),
	(10,'nice',4,3),
	(11,'very nice',5,3),
	(12,'nice',4,3),
	(13,'awesome',5,4),
	(14,'awesome',5,4),
	(15,'nice',4,4),
	(16,'nice',4,13);

/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table seller
# ------------------------------------------------------------

DROP TABLE IF EXISTS `seller`;

CREATE TABLE `seller` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_crgbovyy4gvgsum2yyb3fbfn7` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;

INSERT INTO `seller` (`id`, `address`, `email`, `first_name`, `last_name`, `phone_number`)
VALUES
	(1,'686-7190 Odio Rd.','dapibus@maurisipsumporta.ca','Neil','Langley','0860 028 7320'),
	(2,'4301 Quisque Av.','Nunc@dictum.co.uk','Nash','Figueroa','07624 847184'),
	(3,'P.O. Box 164, 2238 Sed Ave','Nam.consequat.dolor@dictum.ca','Lance','Murphy','076 0951 5983	'),
	(4,'Ap #372-6041 Sed Rd.','feugiat.nec@nonbibendumsed.co.uk','Octavius','Maxwell','0900 269 3812	'),
	(5,'150-2570 Gravida Av.','at@Crasloremlorem.edu','Branden','Cervantes','070 4516 7493'),
	(6,'7333 Aliquam Rd.','adipiscing.lacus.Ut@mus.org','Xavier','Newton','0800 722144'),
	(7,'543-4013 Ridiculus Rd.','orci@magna.edu','Tate','Shaffer','(01057) 443230'),
	(8,'218-7844 Sed Road','purus.mauris@idsapien.org','Geoffrey','Gaines','(0131) 088 9519'),
	(9,'4551 Sed Rd.','nec.tempus.scelerisque@malesuadaaugueut.com','Lamar','Hamilton','(0131) 062 0082'),
	(10,'890-6033 Mus. Rd.','dui.Suspendisse.ac@in.edu','Erasmus','Chambers','(01367) 72716'),
	(11,'508-5492 Suspendisse Street','tristique.neque@non.edu','Cole','Ferrell','(014170) 22515'),
	(12,'270-943 Varius Rd.','porttitor.interdum@arcuVestibulumut.co.uk','Galvin','Hill','(0121) 771 7436'),
	(13,'625-5112 Iaculis Av.','Maecenas.malesuada.fringilla@liberodui.edu','Yardley','Salazar','07624 232496'),
	(14,'559 Vel Rd.','feugiat.tellus.lorem@velitjustonec.com','Boris','Abbott','0500 228795'),
	(15,'Ap #367-7717 Vivamus St.','est.tempor.bibendum@elit.ca','Nissim','Case','(027) 3667 5321'),
	(16,'455-3540 Urna Avenue','metus.Aliquam@a.org','Elvis','Vazquez','(016977) 1802'),
	(17,'P.O. Box 266, 8998 Non Ave','Duis@velsapienimperdiet.com	','Caldwell','Norris','056 7862 4110	'),
	(18,'Ap #905-218 Suspendisse Av.','risus.Morbi.metus@quismassaMauris.ca','Hashim','Morin','(01689) 99975'),
	(19,'270-3645 Rutrum St.','ligula.tortor.dictum@quamPellentesque.ca','Blake','Romero','070 5323 6843	'),
	(20,'4898 Eros. St.','lobortis@sodalespurus.edu','Marsden','Valencia','076 8864 1821	');

/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
