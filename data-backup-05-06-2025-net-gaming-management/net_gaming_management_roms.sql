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
-- Table structure for table `roms`
--

DROP TABLE IF EXISTS `roms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roms` (
  `rom_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `type` enum('EPROM','EEPROM','Flash') NOT NULL,
  `capacity` int NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`rom_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roms`
--

LOCK TABLES `roms` WRITE;
/*!40000 ALTER TABLE `roms` DISABLE KEYS */;
INSERT INTO `roms` VALUES (1,'AMI','AMI BIOS 2023',1870000.00,'Flash',32,'2025-03-14','2027-03-14','Đang sử dụng'),(2,'Phoenix','Phoenix SecureCore Tiano',1200000.00,'EEPROM',16,'2025-02-10','2026-02-10','Đang sử dụng'),(3,'Award','Award BIOS Modular',1870000.00,'Flash',64,'2024-12-15','2026-12-15','Đang sử dụng'),(4,'Intel','Intel UEFI v2.8',1400000.00,'Flash',128,'2025-01-05','2028-01-05','Đang sử dụng'),(5,'AMI','AMI Aptio V',1830000.00,'Flash',32,'2025-03-13','2027-03-13','Đang sử dụng'),(6,'Phoenix','Phoenix BIOS 4.0',1700000.00,'EPROM',8,'2025-02-20','2026-02-20','Đang sử dụng'),(7,'Award','AwardBIOS Elite',1100000.00,'EEPROM',16,'2024-11-25','2025-11-25','Đang sử dụng'),(8,'Intel','Intel UEFI v2.7',1300000.00,'Flash',64,'2025-03-01','2027-03-01','Đang sử dụng'),(9,'AMI','AMI BIOS 2024',1840000.00,'Flash',32,'2025-01-15','2026-01-15','Đang sử dụng'),(10,'Phoenix','Phoenix SecureCore DXE',1500000.00,'Flash',64,'2025-02-28','2027-02-28','Đang sử dụng'),(11,'Award','Award BIOS Legacy',1860000.00,'EPROM',8,'2024-12-01','2025-12-01','Đang sử dụng'),(12,'Intel','Intel UEFI v2.9',1500000.00,'Flash',128,'2025-03-10','2028-03-10','Đang sử dụng'),(13,'AMI','AMI Aptio 6',1820000.00,'Flash',32,'2025-02-05','2026-02-05','Đang sử dụng'),(14,'Phoenix','Phoenix TrustedCore',800000.00,'EEPROM',16,'2025-03-12','2027-03-12','Đang sử dụng'),(15,'Award','Award BIOS Pro',1880000.00,'Flash',64,'2024-11-10','2026-11-10','Đang sử dụng'),(16,'Intel','Intel UEFI v2.6',1200000.00,'Flash',32,'2025-01-25','2026-01-25','Đang sử dụng'),(17,'AMI','AMI BIOS Core',1850000.00,'EEPROM',16,'2025-03-07','2027-03-07','Đang sử dụng'),(18,'Phoenix','Phoenix BIOS 5.0',1800000.00,'EPROM',8,'2025-02-15','2026-02-15','Đang sử dụng'),(19,'Award','Award BIOS Ultra',1890000.00,'Flash',64,'2024-12-20','2026-12-20','Đang sử dụng'),(20,'Intel','Intel UEFI v3.0',1600000.00,'Flash',128,'2025-03-13','2028-03-13','Đang sử dụng'),(21,'Intel','Intel UEFI v2.6',1200000.00,'Flash',32,'2025-01-25','2026-01-25','Trong kho'),(22,'AMI','AMI BIOS Core',1850000.00,'EEPROM',16,'2025-03-07','2027-03-07','Trong kho'),(23,'Phoenix','Phoenix BIOS 5.0',1800000.00,'EPROM',8,'2025-02-15','2026-02-15','Trong kho'),(24,'Award','Award BIOS Ultra',1890000.00,'Flash',64,'2024-12-20','2026-12-20','Trong kho'),(25,'Intel','Intel UEFI v3.0',1600000.00,'Flash',128,'2025-03-13','2028-03-13','Trong kho');
/*!40000 ALTER TABLE `roms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07  9:05:21
