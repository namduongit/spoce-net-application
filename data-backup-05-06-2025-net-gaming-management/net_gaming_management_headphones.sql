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
-- Table structure for table `headphones`
--

DROP TABLE IF EXISTS `headphones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `headphones` (
  `headphone_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` int NOT NULL DEFAULT '0',
  `type` enum('Wired','Wireless') NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`headphone_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `headphones`
--

LOCK TABLES `headphones` WRITE;
/*!40000 ALTER TABLE `headphones` DISABLE KEYS */;
INSERT INTO `headphones` VALUES (1,'Logitech','Logitech G733',2500000,'Wireless','2025-03-14','2026-03-14','Đang sử dụng'),(2,'Razer','Razer BlackShark V2',2000000,'Wired','2025-02-10','2027-02-10','Đang sử dụng'),(3,'Sony','Sony WH-1000XM5',8500000,'Wireless','2024-12-15','2027-12-15','Đang sử dụng'),(4,'HyperX','HyperX Cloud II',2000000,'Wired','2025-01-05','2026-01-05','Đang sử dụng'),(5,'SteelSeries','SteelSeries Arctis Nova Pro',6000000,'Wireless','2025-03-13','2028-03-13','Đang sử dụng'),(6,'Sennheiser','Sennheiser HD 660S',9000000,'Wired','2025-02-20','2027-02-20','Đang sử dụng'),(7,'Logitech','Logitech G435',1500000,'Wireless','2024-11-25','2025-11-25','Đang sử dụng'),(8,'Razer','Razer Kraken V3',2500000,'Wired','2025-03-01','2026-03-01','Đang sử dụng'),(9,'Sony','Sony WF-1000XM4',6000000,'Wireless','2025-01-15','2026-01-15','Đang sử dụng'),(10,'HyperX','HyperX Cloud Alpha',2200000,'Wired','2025-02-28','2026-02-28','Đang sử dụng'),(11,'SteelSeries','SteelSeries Arctis 7+',4000000,'Wireless','2024-12-01','2026-12-01','Đang sử dụng'),(12,'Logitech','Logitech Zone Vibe 100',2000000,'Wireless','2025-03-10','2026-03-10','Đang sử dụng'),(13,'Razer','Razer Barracuda X',2500000,'Wireless','2025-02-05','2026-02-05','Đang sử dụng'),(14,'Sony','Sony MDR-7506',3000000,'Wired','2025-03-12','2026-03-12','Đang sử dụng'),(15,'HyperX','HyperX Cloud Stinger',1200000,'Wired','2024-11-10','2025-11-10','Đang sử dụng'),(16,'SteelSeries','SteelSeries Arctis 1',1500000,'Wired','2025-01-25','2026-01-25','Đang sử dụng'),(17,'Sennheiser','Sennheiser Momentum 4',7500000,'Wireless','2025-03-07','2028-03-07','Đang sử dụng'),(18,'Logitech','Logitech G Pro X',3000000,'Wired','2025-02-15','2026-02-15','Đang sử dụng'),(19,'Razer','Razer Nari Ultimate',4000000,'Wireless','2024-12-20','2026-12-20','Đang sử dụng'),(20,'Sony','Sony Pulse 3D',2500000,'Wireless','2025-03-13','2026-03-13','Đang sử dụng'),(21,'SteelSeries','SteelSeries Arctis 1',1500000,'Wired','2025-01-25','2026-01-25','Trong kho'),(22,'Sennheiser','Sennheiser Momentum 4',7500000,'Wireless','2025-03-07','2028-03-07','Trong kho'),(23,'Logitech','Logitech G Pro X',3000000,'Wired','2025-02-15','2026-02-15','Trong kho'),(24,'Razer','Razer Nari Ultimate',4000000,'Wireless','2024-12-20','2026-12-20','Trong kho'),(25,'Sony','Sony Pulse 3D',2500000,'Wireless','2025-03-13','2026-03-13','Trong kho');
/*!40000 ALTER TABLE `headphones` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07  9:05:20
