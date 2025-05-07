-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: net_gaming_management
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `staffs`
--

DROP TABLE IF EXISTS `staffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staffs` (
  `staff_id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `birth_date` date NOT NULL,
  `gender` enum('Nam','Nữ','Chưa đặt') DEFAULT 'Chưa đặt',
  `phone` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `cccd` varchar(12) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`staff_id`),
  UNIQUE KEY `account_id` (`account_id`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `cccd` (`cccd`),
  CONSTRAINT `staffs_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staffs`
--

LOCK TABLES `staffs` WRITE;
/*!40000 ALTER TABLE `staffs` DISABLE KEYS */;
INSERT INTO `staffs` VALUES (1,1,'Chưa cập nhật','2000-01-01','Chưa đặt',NULL,NULL,NULL,NULL,NULL),(2,2,'Chưa cập nhật','2000-01-01','Chưa đặt',NULL,NULL,NULL,NULL,NULL),(3,3,'Chưa cập nhật','2000-01-01','Chưa đặt',NULL,NULL,NULL,NULL,NULL),(4,4,'Chưa cập nhật','2000-01-01','Chưa đặt',NULL,NULL,NULL,NULL,NULL),(5,5,'Chưa cập nhật','2000-01-01','Chưa đặt',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `staffs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07  9:05:19
