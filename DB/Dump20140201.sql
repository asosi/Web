CREATE DATABASE  IF NOT EXISTS `new_schema` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `new_schema`;
-- MySQL dump 10.13  Distrib 5.5.35, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: new_schema
-- ------------------------------------------------------
-- Server version	5.5.35-0ubuntu0.12.04.1

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-01  0:40:48
CREATE DATABASE  IF NOT EXISTS `new_schema2` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `new_schema2`;
-- MySQL dump 10.13  Distrib 5.5.35, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: new_schema2
-- ------------------------------------------------------
-- Server version	5.5.35-0ubuntu0.12.04.1

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
-- Table structure for table `post_file`
--

DROP TABLE IF EXISTS `post_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_file` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_post` int(11) NOT NULL,
  `post_file` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_file`
--

LOCK TABLES `post_file` WRITE;
/*!40000 ALTER TABLE `post_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `email` varchar(45) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `password` varchar(45) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL,
  `avatar` varchar(100) NOT NULL DEFAULT 'img/users/default.jpg',
  `lastvisit` timestamp NULL DEFAULT NULL,
  `countdown` timestamp NULL DEFAULT NULL,
  `moderatore` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Amedeo','Capuzzo','amedeo.capuzzo@gmail.com','ycz8fc@Hb','img/users/default.jpg',NULL,'2014-01-31 15:25:51',''),(2,'Andrea','Sosi','sosi.andrea@gmail.com','CIAO','img/users/default.jpg',NULL,'2014-01-31 15:39:27','\0'),(3,'Davide','Campigotto','empy92@hotmail.it','+++','img/users/default.jpg',NULL,'2014-01-31 23:31:06','\0'),(7,'Silvia','Bordin1','silvia1@gmail.com','asd','img/users/default.jpg',NULL,NULL,'\0'),(8,'Silvia','Bordin2','silvia2@gmail.com','asd','img/users/default.jpg',NULL,NULL,'\0'),(9,'asd','asd','asd@asd.it','asd','img/users/default.jpg',NULL,NULL,'\0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `ID_owner` int(11) NOT NULL,
  `born_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `avatar` varchar(100) NOT NULL DEFAULT 'img/users/default.png',
  `flag` int(11) NOT NULL,
  `blocked` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES (1,'ciaoxgcfhvjkl-kasdòlaskidasoòpdikasèdasè',1,'2014-01-31 14:15:15','icon.png',0,1),(2,'ciao',2,'2014-01-31 14:17:13','icon.png',0,1),(3,'asdf',1,'2014-01-31 14:15:16','icon.png',0,0),(4,'prova',1,'2014-01-31 14:15:16','icon.png',0,0),(5,'Rinominato da asd',2,'2014-01-31 14:15:16','icon.png',0,0),(6,'prova',1,'2014-01-31 14:15:16','icon.png',0,0),(7,'Ciao sosi',1,'2014-01-31 14:15:16','icon10.png',1,0);
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ask`
--

DROP TABLE IF EXISTS `ask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ask` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_groups` int(11) NOT NULL,
  `ID_users` int(11) NOT NULL,
  `state` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Unique` (`ID_users`,`ID_groups`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ask`
--

LOCK TABLES `ask` WRITE;
/*!40000 ALTER TABLE `ask` DISABLE KEYS */;
INSERT INTO `ask` VALUES (1,1,3,1),(2,1,2,1),(3,3,3,1),(4,3,2,1),(5,4,3,2),(6,4,2,1),(7,0,1,2),(11,5,1,1),(12,6,3,1),(13,7,2,1);
/*!40000 ALTER TABLE `ask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_users` int(11) NOT NULL,
  `news` varchar(200) CHARACTER SET latin1 NOT NULL,
  `see` bit(1) NOT NULL DEFAULT b'0',
  `page` varchar(100) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,3,'Hai nuovi inviti','','Inviti'),(2,2,'Hai nuovi inviti','','Inviti'),(3,3,'Hai nuovi inviti','','Inviti.jsp'),(4,2,'Hai nuovi inviti','','Inviti.jsp'),(5,2,'Nuovi post nel gruppo qwertyui','','GroupPage.jsp?numero=1'),(6,2,'Nuovi post nel gruppo qwertyui','','GroupPage.jsp?numero=1'),(7,1,'Nuovi post nel gruppo qwertyui','','GroupPage.jsp?numero=1'),(8,2,'Hai nuovi inviti','','Inviti.jsp'),(9,1,'Hai nuovi inviti','','Inviti.jsp'),(10,3,'Hai nuovi inviti','','Inviti.jsp'),(11,2,'Hai nuovi inviti','','Inviti.jsp');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_groups` int(11) NOT NULL,
  `ID_users` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `text` text NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,1,1,'2014-01-23 15:34:45','xdcfvbnm'),(2,1,1,'2014-01-23 15:41:36','fghjkl'),(3,1,2,'2014-01-23 15:41:42','ciao'),(4,1,2,'2014-01-23 15:41:56','hey'),(5,4,1,'2014-01-23 15:41:59','cvbnm'),(6,5,2,'2014-01-23 15:46:15','sd'),(7,1,1,'2014-01-25 10:20:33','cucu');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_groups`
--

DROP TABLE IF EXISTS `users_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_groups` (
  `ID_groups` int(11) NOT NULL,
  `ID_users` int(11) NOT NULL,
  `active` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`ID_groups`,`ID_users`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_groups`
--

LOCK TABLES `users_groups` WRITE;
/*!40000 ALTER TABLE `users_groups` DISABLE KEYS */;
INSERT INTO `users_groups` VALUES (1,2,''),(1,3,''),(3,2,''),(3,3,''),(4,2,''),(4,3,''),(5,1,''),(6,3,''),(7,2,'');
/*!40000 ALTER TABLE `users_groups` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-01  0:40:49
