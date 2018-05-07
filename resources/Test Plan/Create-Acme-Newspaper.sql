start transaction;

drop database if exists `acmenewspaper`;
create database `acmenewspaper`;

use `acmenewspaper`;

create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `acmenewspaper`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `acmenewspaper`.* to 'acme-manager'@'%';


-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: AcmeNewspaper
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actor`
--

DROP TABLE IF EXISTS `actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_cgls5lrufx91ufsyh467spwa3` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor`
--

LOCK TABLES `actor` WRITE;
/*!40000 ALTER TABLE `actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actor_folder`
--

DROP TABLE IF EXISTS `actor_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actor_folder` (
  `Actor_id` int(11) NOT NULL,
  `folders_id` int(11) NOT NULL,
  UNIQUE KEY `UK_dp573h40udupcm5m1kgn2bc2r` (`folders_id`),
  CONSTRAINT `FK_dp573h40udupcm5m1kgn2bc2r` FOREIGN KEY (`folders_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actor_folder`
--

LOCK TABLES `actor_folder` WRITE;
/*!40000 ALTER TABLE `actor_folder` DISABLE KEYS */;
INSERT INTO `actor_folder` VALUES (259,317),(259,318),(259,319),(259,320),(259,321),(259,322),(260,323),(260,324),(260,325),(260,326),(260,327),(260,328),(261,329),(261,330),(261,331),(261,332),(261,333),(261,334),(262,335),(262,336),(262,337),(262,338),(262,339),(262,340),(297,341),(297,342),(297,343),(297,344),(297,345),(297,346),(263,347),(263,348),(263,349),(263,350),(263,351),(263,352),(264,353),(264,354),(264,355),(264,356),(264,357),(264,358);
/*!40000 ALTER TABLE `actor_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`),
  CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (259,0,'ponsavi@acme.org','Paco','656343000','Calle Fontenla, 2','Samper Villagrán',252);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advertisement`
--

DROP TABLE IF EXISTS `advertisement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advertisement` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `taboo` bit(1) NOT NULL,
  `targetPage` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `agent_id` int(11) NOT NULL,
  `creditCard_id` int(11) NOT NULL,
  `newspaper_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_nvmnxmvv7037fjmrvbish1ggd` (`taboo`),
  KEY `FK_7n9ussuxsi3k6rm34vajrccvn` (`agent_id`),
  KEY `FK_t1egal141du0gw5ya0o6j11ya` (`creditCard_id`),
  KEY `FK_2a9jqcvexg35eohaebb71i4xu` (`newspaper_id`),
  CONSTRAINT `FK_2a9jqcvexg35eohaebb71i4xu` FOREIGN KEY (`newspaper_id`) REFERENCES `newspaper` (`id`),
  CONSTRAINT `FK_7n9ussuxsi3k6rm34vajrccvn` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`id`),
  CONSTRAINT `FK_t1egal141du0gw5ya0o6j11ya` FOREIGN KEY (`creditCard_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advertisement`
--

LOCK TABLES `advertisement` WRITE;
/*!40000 ALTER TABLE `advertisement` DISABLE KEYS */;
INSERT INTO `advertisement` VALUES (362,0,'http://www.us.es/hereyouhaveanattachment','\0','http://www.us.es/hereyouhaveanattachment','Pepón',263,293,308),(363,0,'http://www.us.es/hereyouhaveanattachment','\0','http://www.us.es/hereyouhaveanattachment','Pepón',263,293,310),(364,0,'http://www.us.es/hereyouhaveanattachment','','http://www.us.es/hereyouhaveanattachment','Sex',264,294,308);
/*!40000 ALTER TABLE `advertisement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `agent` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5cg6nedtnilfs6spfq209syse` (`userAccount_id`),
  CONSTRAINT `FK_5cg6nedtnilfs6spfq209syse` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent`
--

LOCK TABLES `agent` WRITE;
/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
INSERT INTO `agent` VALUES (263,0,'ponsavi@acme.org','Agent1','656343000','Calle Fontenla, 2','Samper Villagrán',257),(264,0,'ponsavi@acme.org','Agent2','656343000','Calle Fontenla, 2','Samper Villagrán',258);
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `draftMode` bit(1) NOT NULL,
  `publishMoment` date DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `taboo` bit(1) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_n9l0oa2ioqjbnpvj56txwy75r` (`publishMoment`),
  KEY `UK_6rcu5ngbg90klwheb98n47gja` (`taboo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (265,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo1'),(266,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo2'),(267,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo3'),(268,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo4'),(269,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo5'),(270,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo6'),(271,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo7'),(272,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo8'),(273,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo9'),(274,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo10'),(275,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo11'),(276,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo12'),(277,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo13'),(278,0,'\0','2014-01-02','Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo14'),(279,0,'\0','2014-01-02','Sex','','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo15'),(280,0,'',NULL,'Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo16'),(281,0,'\0',NULL,'Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo17'),(282,0,'\0',NULL,'Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo18'),(283,0,'\0',NULL,'Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo19'),(284,0,'\0',NULL,'Sumary','\0','texto : Lorem ipsum dolormodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','articulo20');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_pictures`
--

DROP TABLE IF EXISTS `article_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_pictures` (
  `Article_id` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_slh5rr6y2n4ml5s20v5nlr52g` (`Article_id`),
  CONSTRAINT `FK_slh5rr6y2n4ml5s20v5nlr52g` FOREIGN KEY (`Article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_pictures`
--

LOCK TABLES `article_pictures` WRITE;
/*!40000 ALTER TABLE `article_pictures` DISABLE KEYS */;
INSERT INTO `article_pictures` VALUES (265,'http://imagen.png'),(266,'http://imagen.png'),(267,'http://imagen.png'),(268,'http://imagen.png'),(269,'http://imagen.png'),(270,'http://imagen.png'),(271,'http://imagen.png'),(272,'http://imagen.png'),(273,'http://imagen.png'),(274,'http://imagen.png'),(275,'http://imagen.png'),(276,'http://imagen.png'),(277,'http://imagen.png'),(278,'http://imagen.png'),(279,'http://imagen.png'),(280,'http://imagen.png'),(281,'http://imagen.png'),(282,'http://imagen.png'),(283,'http://imagen.png'),(284,'http://imagen.png');
/*!40000 ALTER TABLE `article_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chirp`
--

DROP TABLE IF EXISTS `chirp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chirp` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `containsTaboo` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `moment` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_2of1f2nqtpb8g68xintbje7am` (`containsTaboo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chirp`
--

LOCK TABLES `chirp` WRITE;
/*!40000 ALTER TABLE `chirp` DISABLE KEYS */;
INSERT INTO `chirp` VALUES (285,0,'','viagra descripcion 1: Loreugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','2014-01-02','chirp1'),(286,0,'\0','descripcion 1: Loreugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','2014-01-02','chirp2'),(287,0,'\0','descripcion 1: Loreugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','2014-01-02','chirp3'),(288,0,'\0','descripcion 1: Loreugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','2014-01-02','chirp4'),(289,0,'\0','descripcion 1: Loreugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','2014-01-02','chirp5');
/*!40000 ALTER TABLE `chirp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (290,0);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_taboowords`
--

DROP TABLE IF EXISTS `config_taboowords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config_taboowords` (
  `Config_id` int(11) NOT NULL,
  `tabooWords` varchar(255) DEFAULT NULL,
  KEY `FK_6arwpfegkhokwyqva5fdu63xq` (`Config_id`),
  CONSTRAINT `FK_6arwpfegkhokwyqva5fdu63xq` FOREIGN KEY (`Config_id`) REFERENCES `config` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_taboowords`
--

LOCK TABLES `config_taboowords` WRITE;
/*!40000 ALTER TABLE `config_taboowords` DISABLE KEYS */;
INSERT INTO `config_taboowords` VALUES (290,'sex'),(290,'sexo'),(290,'viagra'),(290,'cialis');
/*!40000 ALTER TABLE `config_taboowords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcard` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `CVV` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `expirationMonth` int(11) NOT NULL,
  `expirationYear` int(11) NOT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `agent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_vjyobtpwu0jo8nh6i6u9i8pw` (`agent_id`),
  CONSTRAINT `FK_vjyobtpwu0jo8nh6i6u9i8pw` FOREIGN KEY (`agent_id`) REFERENCES `agent` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
INSERT INTO `creditcard` VALUES (291,0,124,'Visa',10,2020,'Francisco Perez','4532013067246621',NULL),(292,0,124,'Visa',10,2020,'Señor que esta cansado de rellenar el populate','4532013067246621',NULL),(293,0,124,'Visa',10,2020,'Francisco Perez','4532013067246621',263),(294,0,124,'Visa',10,2020,'Juan Perez','5480848314079443',264);
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard_newspaper`
--

DROP TABLE IF EXISTS `creditcard_newspaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcard_newspaper` (
  `CreditCard_id` int(11) NOT NULL,
  `newspapers_id` int(11) NOT NULL,
  KEY `FK_g04p1f61k7h37jfomil8rsv9i` (`newspapers_id`),
  KEY `FK_2mq7ivtemammg3t9j64e36daw` (`CreditCard_id`),
  CONSTRAINT `FK_2mq7ivtemammg3t9j64e36daw` FOREIGN KEY (`CreditCard_id`) REFERENCES `creditcard` (`id`),
  CONSTRAINT `FK_g04p1f61k7h37jfomil8rsv9i` FOREIGN KEY (`newspapers_id`) REFERENCES `newspaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard_newspaper`
--

LOCK TABLES `creditcard_newspaper` WRITE;
/*!40000 ALTER TABLE `creditcard_newspaper` DISABLE KEYS */;
INSERT INTO `creditcard_newspaper` VALUES (291,310),(293,310),(293,308),(294,308);
/*!40000 ALTER TABLE `creditcard_newspaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard_volume`
--

DROP TABLE IF EXISTS `creditcard_volume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creditcard_volume` (
  `CreditCard_id` int(11) NOT NULL,
  `volumes_id` int(11) NOT NULL,
  KEY `FK_nmrujnl5j09x6tcsdou2v1xb3` (`volumes_id`),
  KEY `FK_4wgu6upto53a4gc10kxobluhx` (`CreditCard_id`),
  CONSTRAINT `FK_4wgu6upto53a4gc10kxobluhx` FOREIGN KEY (`CreditCard_id`) REFERENCES `creditcard` (`id`),
  CONSTRAINT `FK_nmrujnl5j09x6tcsdou2v1xb3` FOREIGN KEY (`volumes_id`) REFERENCES `volume` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard_volume`
--

LOCK TABLES `creditcard_volume` WRITE;
/*!40000 ALTER TABLE `creditcard_volume` DISABLE KEYS */;
INSERT INTO `creditcard_volume` VALUES (292,295);
/*!40000 ALTER TABLE `creditcard_volume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pwmktpkay2yx7v00mrwmuscl8` (`userAccount_id`),
  CONSTRAINT `FK_pwmktpkay2yx7v00mrwmuscl8` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (297,0,'ponsavi@acme.org','Pepón','656222111','Calle Fontenla, 2','Samper Villagrán',254);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_creditcard`
--

DROP TABLE IF EXISTS `customer_creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_creditcard` (
  `Customer_id` int(11) NOT NULL,
  `creditCard_id` int(11) NOT NULL,
  UNIQUE KEY `UK_trew4puloaajjiyfj763utxjy` (`creditCard_id`),
  KEY `FK_h2ciytevbkctxgx839stph85m` (`Customer_id`),
  CONSTRAINT `FK_h2ciytevbkctxgx839stph85m` FOREIGN KEY (`Customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `FK_trew4puloaajjiyfj763utxjy` FOREIGN KEY (`creditCard_id`) REFERENCES `creditcard` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_creditcard`
--

LOCK TABLES `customer_creditcard` WRITE;
/*!40000 ALTER TABLE `customer_creditcard` DISABLE KEYS */;
INSERT INTO `customer_creditcard` VALUES (297,291),(297,292);
/*!40000 ALTER TABLE `customer_creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `systemFolders` bit(1) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_e6lcmpm09goh6x4n16fbq5uka` (`parent_id`),
  CONSTRAINT `FK_e6lcmpm09goh6x4n16fbq5uka` FOREIGN KEY (`parent_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (317,0,'Root','',NULL),(318,0,'Inbox','',317),(319,0,'Outbox','',317),(320,0,'Notification Box','',317),(321,0,'TrashBox','',317),(322,0,'Spam','',317),(323,0,'Root','',NULL),(324,0,'Inbox','',323),(325,0,'Outbox','',323),(326,0,'Notification Box','',323),(327,0,'TrashBox','',323),(328,0,'Spam','',323),(329,0,'Root','',NULL),(330,0,'Inbox','',329),(331,0,'Outbox','',329),(332,0,'Notification Box','',329),(333,0,'TrashBox','',329),(334,0,'Spam','',329),(335,0,'Root','',NULL),(336,0,'Inbox','',335),(337,0,'Outbox','',335),(338,0,'Notification Box','',335),(339,0,'TrashBox','',335),(340,0,'Spam','',335),(341,0,'Root','',NULL),(342,0,'Inbox','',341),(343,0,'Outbox','',341),(344,0,'Notification Box','',341),(345,0,'TrashBox','',341),(346,0,'Spam','',341),(347,0,'Root','',NULL),(348,0,'Inbox','',347),(349,0,'Outbox','',347),(350,0,'Notification Box','',347),(351,0,'TrashBox','',347),(352,0,'Spam','',347),(353,0,'Root','',NULL),(354,0,'Inbox','',353),(355,0,'Outbox','',353),(356,0,'Notification Box','',353),(357,0,'TrashBox','',353),(358,0,'Spam','',353);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_message`
--

DROP TABLE IF EXISTS `folder_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder_message` (
  `Folder_id` int(11) NOT NULL,
  `messages_id` int(11) NOT NULL,
  KEY `FK_5nh3mwey9bw25ansh2thcbcdh` (`messages_id`),
  KEY `FK_dwna03p0i8so6ov91ouups81r` (`Folder_id`),
  CONSTRAINT `FK_dwna03p0i8so6ov91ouups81r` FOREIGN KEY (`Folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_5nh3mwey9bw25ansh2thcbcdh` FOREIGN KEY (`messages_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder_message`
--

LOCK TABLES `folder_message` WRITE;
/*!40000 ALTER TABLE `folder_message` DISABLE KEYS */;
INSERT INTO `folder_message` VALUES (324,360),(325,359),(325,361),(330,359),(331,360),(348,361);
/*!40000 ALTER TABLE `folder_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followup`
--

DROP TABLE IF EXISTS `followup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `followup` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `publishMoment` date DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_s5wo0vp1qu4337prqg4ltx79r` (`publishMoment`),
  KEY `FK_aer0q20rslre6418yqlfwmeek` (`article_id`),
  CONSTRAINT `FK_aer0q20rslre6418yqlfwmeek` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followup`
--

LOCK TABLES `followup` WRITE;
/*!40000 ALTER TABLE `followup` DISABLE KEYS */;
INSERT INTO `followup` VALUES (298,0,'2014-01-02','sumario','texto1','titulodelfollowup1',265),(299,0,'2014-01-02','sumario','texto1','titulodelfollowup2',266),(300,0,'2014-01-02','sumario','texto1','titulodelfollowup3',267),(301,0,'2014-01-02','sumario','texto1','titulodelfollowup4',268),(302,0,'2014-01-02','sumario','texto1','titulodelfollowup5',269),(303,0,'2014-01-02','sumario','texto1','titulodelfollowup6',270),(304,0,'2014-01-02','sumario','texto1','titulodelfollowup6',275),(305,0,'2014-01-02','sumario','texto1','titulodelfollowup6',276),(306,0,'2014-01-02','sumario','texto1','titulodelfollowup6',277),(307,0,'2014-01-02','sumario','texto1','titulodelfollowup6',278);
/*!40000 ALTER TABLE `followup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followup_pictures`
--

DROP TABLE IF EXISTS `followup_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `followup_pictures` (
  `FollowUp_id` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_be66suxjlinls1y3yymi3tc0d` (`FollowUp_id`),
  CONSTRAINT `FK_be66suxjlinls1y3yymi3tc0d` FOREIGN KEY (`FollowUp_id`) REFERENCES `followup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followup_pictures`
--

LOCK TABLES `followup_pictures` WRITE;
/*!40000 ALTER TABLE `followup_pictures` DISABLE KEYS */;
INSERT INTO `followup_pictures` VALUES (298,'http://imagen.png'),(299,'http://imagen.png'),(300,'http://imagen.png'),(301,'http://imagen.png'),(302,'http://imagen.png'),(303,'http://imagen.png'),(304,'http://imagen.png'),(305,'http://imagen.png'),(306,'http://imagen.png'),(307,'http://imagen.png');
/*!40000 ALTER TABLE `followup_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (359,0,'cuerpo del mensaje 1.','2017-10-01 19:00:00','NEUTRAL','Asunto1',260),(360,0,'cuerpo del mensaje 2.','2017-10-01 19:00:00','NEUTRAL','Asunto1',261),(361,0,'cuerpo del mensaje 3.','2017-10-01 19:00:00','NEUTRAL','Asunto1',260);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_actor`
--

DROP TABLE IF EXISTS `message_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_actor` (
  `messagesReceived_id` int(11) NOT NULL,
  `recipients_id` int(11) NOT NULL,
  KEY `FK_apm75cjw83uf1irk3vn5616eq` (`messagesReceived_id`),
  CONSTRAINT `FK_apm75cjw83uf1irk3vn5616eq` FOREIGN KEY (`messagesReceived_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_actor`
--

LOCK TABLES `message_actor` WRITE;
/*!40000 ALTER TABLE `message_actor` DISABLE KEYS */;
INSERT INTO `message_actor` VALUES (359,261),(360,260),(361,263);
/*!40000 ALTER TABLE `message_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newspaper`
--

DROP TABLE IF EXISTS `newspaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newspaper` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `publicationDate` date DEFAULT NULL,
  `publicity` bit(1) DEFAULT NULL,
  `taboo` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_1tf2y7ybe3u3285xf6w2v4mfx` (`taboo`),
  KEY `UK_v2y7dxoqcqmpdhq0uscs773q` (`publicationDate`),
  KEY `UK_edqcgdnjk7taxbw89phisohe8` (`publicity`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newspaper`
--

LOCK TABLES `newspaper` WRITE;
/*!40000 ALTER TABLE `newspaper` DISABLE KEYS */;
INSERT INTO `newspaper` VALUES (308,0,'descripcion del periodico','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png','2014-01-02','\0','\0','titulo del periodico'),(309,0,'descripcion del periodico','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png','2014-01-02','\0','\0','titulo del periodico 2'),(310,0,'descripcion del periodico','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png','2014-01-02','','\0','Periodico privado'),(311,0,'descripcion del periodico','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png','2014-01-02','\0','\0','Periodico en draft publico'),(312,0,'descripcion del periodico','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png',NULL,'','\0','Periodico en draft privado'),(313,0,'sexo free sex sida dildo dialis aprobado dp','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png',NULL,'\0','','periodico prohibido taboo +18'),(314,0,'descripcion del periodico','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png',NULL,'\0','\0','Periodico en draft publico'),(315,0,'descripcion del periodico','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png','2014-01-02','','\0','Periodico privado2'),(316,0,'descripcion del periodico','https://vignette.wikia.nocookie.net/medabots/images/7/73/Imagen_de_ejemplo.png',NULL,'\0','\0','Periodico por publicar');
/*!40000 ALTER TABLE `newspaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newspaper_article`
--

DROP TABLE IF EXISTS `newspaper_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newspaper_article` (
  `Newspaper_id` int(11) NOT NULL,
  `articles_id` int(11) NOT NULL,
  UNIQUE KEY `UK_b6yh4ur28qo9smnd995jo356g` (`articles_id`),
  KEY `FK_53pw01kjsjxn1ycjlwa1q6j13` (`Newspaper_id`),
  CONSTRAINT `FK_53pw01kjsjxn1ycjlwa1q6j13` FOREIGN KEY (`Newspaper_id`) REFERENCES `newspaper` (`id`),
  CONSTRAINT `FK_b6yh4ur28qo9smnd995jo356g` FOREIGN KEY (`articles_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newspaper_article`
--

LOCK TABLES `newspaper_article` WRITE;
/*!40000 ALTER TABLE `newspaper_article` DISABLE KEYS */;
INSERT INTO `newspaper_article` VALUES (308,265),(308,266),(308,267),(308,268),(308,269),(308,283),(309,270),(309,271),(309,272),(309,273),(309,274),(310,275),(310,276),(310,277),(310,278),(310,279),(314,280),(314,281),(315,282),(316,284);
/*!40000 ALTER TABLE `newspaper_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o6s94d43co03sx067ili5760c` (`userAccount_id`),
  CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (260,0,'ponsavi@acme.org','Pepón','656343002','Calle Fontenla, 2','Samper Villagrán',253),(261,0,'user2@mail.com','userName2','656222111','user2PostalAdress','userSurname2',255),(262,0,'user3@mail.com','userName3','656222113','user3PostalAdress','userSurname3',256);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_article`
--

DROP TABLE IF EXISTS `user_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_article` (
  `User_id` int(11) NOT NULL,
  `articles_id` int(11) NOT NULL,
  UNIQUE KEY `UK_siug4boflrp0p0dncq76a13wu` (`articles_id`),
  KEY `FK_bkiti988ki4sxkoqc8ro1wnsa` (`User_id`),
  CONSTRAINT `FK_bkiti988ki4sxkoqc8ro1wnsa` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_siug4boflrp0p0dncq76a13wu` FOREIGN KEY (`articles_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_article`
--

LOCK TABLES `user_article` WRITE;
/*!40000 ALTER TABLE `user_article` DISABLE KEYS */;
INSERT INTO `user_article` VALUES (260,265),(260,266),(260,267),(260,268),(260,269),(260,280),(260,281),(260,282),(260,283),(260,284),(261,270),(261,271),(261,272),(261,273),(261,274),(262,275),(262,276),(262,277),(262,278),(262,279);
/*!40000 ALTER TABLE `user_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_chirp`
--

DROP TABLE IF EXISTS `user_chirp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_chirp` (
  `User_id` int(11) NOT NULL,
  `chirps_id` int(11) NOT NULL,
  UNIQUE KEY `UK_ls9bn8hbpkktyfahbm4wujrps` (`chirps_id`),
  KEY `FK_8lvf5igmdhhmxc70p7ujd36ym` (`User_id`),
  CONSTRAINT `FK_8lvf5igmdhhmxc70p7ujd36ym` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_ls9bn8hbpkktyfahbm4wujrps` FOREIGN KEY (`chirps_id`) REFERENCES `chirp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_chirp`
--

LOCK TABLES `user_chirp` WRITE;
/*!40000 ALTER TABLE `user_chirp` DISABLE KEYS */;
INSERT INTO `user_chirp` VALUES (260,285),(260,286),(260,287),(261,288),(261,289);
/*!40000 ALTER TABLE `user_chirp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_followup`
--

DROP TABLE IF EXISTS `user_followup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_followup` (
  `User_id` int(11) NOT NULL,
  `followUp_id` int(11) NOT NULL,
  UNIQUE KEY `UK_lfv2rycqwreg4jbq1ajh216cm` (`followUp_id`),
  KEY `FK_d2qpetfkbbkwvy6gqcjm2yowh` (`User_id`),
  CONSTRAINT `FK_d2qpetfkbbkwvy6gqcjm2yowh` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_lfv2rycqwreg4jbq1ajh216cm` FOREIGN KEY (`followUp_id`) REFERENCES `followup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_followup`
--

LOCK TABLES `user_followup` WRITE;
/*!40000 ALTER TABLE `user_followup` DISABLE KEYS */;
INSERT INTO `user_followup` VALUES (260,298),(260,299),(260,300),(260,301),(260,302),(261,303),(262,304),(262,305),(262,306),(262,307);
/*!40000 ALTER TABLE `user_followup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_newspaper`
--

DROP TABLE IF EXISTS `user_newspaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_newspaper` (
  `User_id` int(11) NOT NULL,
  `newspapers_id` int(11) NOT NULL,
  UNIQUE KEY `UK_oqhrpkgl440xehybmm713ru36` (`newspapers_id`),
  KEY `FK_24xv3fsqc505dhy1bv4ldnor9` (`User_id`),
  CONSTRAINT `FK_24xv3fsqc505dhy1bv4ldnor9` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_oqhrpkgl440xehybmm713ru36` FOREIGN KEY (`newspapers_id`) REFERENCES `newspaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_newspaper`
--

LOCK TABLES `user_newspaper` WRITE;
/*!40000 ALTER TABLE `user_newspaper` DISABLE KEYS */;
INSERT INTO `user_newspaper` VALUES (260,308),(260,311),(260,312),(260,313),(260,314),(260,316),(261,309),(262,310);
/*!40000 ALTER TABLE `user_newspaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_user`
--

DROP TABLE IF EXISTS `user_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_user` (
  `User_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  KEY `FK_skc3d47tu4nnp2kx12n5lsiur` (`users_id`),
  KEY `FK_nlnx78x3m38aq2r86t1d5eio1` (`User_id`),
  CONSTRAINT `FK_nlnx78x3m38aq2r86t1d5eio1` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_skc3d47tu4nnp2kx12n5lsiur` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_user`
--

LOCK TABLES `user_user` WRITE;
/*!40000 ALTER TABLE `user_user` DISABLE KEYS */;
INSERT INTO `user_user` VALUES (261,260),(262,260),(262,261);
/*!40000 ALTER TABLE `user_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (252,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(253,0,'24c9e15e52afc47c225b757e7bee1f9d','user1'),(254,0,'ffbc4675f864e0e9aab8bdf7a0437010','customer1'),(255,0,'7e58d63b60197ceb55a1c487989a3720','user2'),(256,0,'92877af70a45fd6a2ed7fe81e1236b78','user3'),(257,0,'83a87fd756ab57199c0bb6d5e11168cb','agent1'),(258,0,'b1a4a6b01cc297d4677c4ca6656e14d7','agent2');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (252,'ADMIN'),(253,'USER'),(254,'CUSTOMER'),(255,'USER'),(256,'USER'),(257,'AGENT'),(258,'AGENT');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volume`
--

DROP TABLE IF EXISTS `volume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `volume` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1g852qpv1irbrshl0rmqgfm3a` (`user_id`),
  CONSTRAINT `FK_1g852qpv1irbrshl0rmqgfm3a` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volume`
--

LOCK TABLES `volume` WRITE;
/*!40000 ALTER TABLE `volume` DISABLE KEYS */;
INSERT INTO `volume` VALUES (295,0,'Muchas cosas bonitas','Flores',2018,260),(296,0,'Sex, sex and more sex ban','Porn',2017,260);
/*!40000 ALTER TABLE `volume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volume_newspaper`
--

DROP TABLE IF EXISTS `volume_newspaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `volume_newspaper` (
  `Volume_id` int(11) NOT NULL,
  `newspapers_id` int(11) NOT NULL,
  KEY `FK_55de0xvt5cb2u4p2xkeofporj` (`newspapers_id`),
  KEY `FK_piiv98lkttksatc6qx4cncuas` (`Volume_id`),
  CONSTRAINT `FK_piiv98lkttksatc6qx4cncuas` FOREIGN KEY (`Volume_id`) REFERENCES `volume` (`id`),
  CONSTRAINT `FK_55de0xvt5cb2u4p2xkeofporj` FOREIGN KEY (`newspapers_id`) REFERENCES `newspaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volume_newspaper`
--

LOCK TABLES `volume_newspaper` WRITE;
/*!40000 ALTER TABLE `volume_newspaper` DISABLE KEYS */;
INSERT INTO `volume_newspaper` VALUES (295,308),(295,309),(295,310),(296,308),(296,311);
/*!40000 ALTER TABLE `volume_newspaper` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-07 19:09:36
