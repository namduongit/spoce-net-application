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
-- Table structure for table `keyboards`
--

DROP TABLE IF EXISTS `keyboards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `keyboards` (
  `keyboard_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` int NOT NULL DEFAULT '0',
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`keyboard_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyboards`
--

LOCK TABLES `keyboards` WRITE;
/*!40000 ALTER TABLE `keyboards` DISABLE KEYS */;
INSERT INTO `keyboards` VALUES (1,'Logitech','Logitech G Pro X TKL',3000000,'2025-03-14','2027-03-14','Đang sử dụng'),(2,'Razer','Razer BlackWidow V4 Pro',4500000,'2025-02-10','2026-02-10','Đang sử dụng'),(3,'SteelSeries','SteelSeries Apex Pro Mini',4000000,'2024-12-15','2026-12-15','Đang sử dụng'),(4,'Corsair','Corsair K95 RGB Platinum',3500000,'2025-01-05','2027-01-05','Đang sử dụng'),(5,'Keychron','Keychron K8 Pro',2000000,'2025-03-13','2026-03-13','Đang sử dụng'),(6,'Ducky','Ducky One 3 TKL',2500000,'2025-02-20','2027-02-20','Đang sử dụng'),(7,'Logitech','Logitech MX Keys',2500000,'2024-11-25','2025-11-25','Đang sử dụng'),(8,'Razer','Raxer Huntsman Mini',3000000,'2025-03-01','2026-03-01','Đang sử dụng'),(9,'SteelSeries','SteelSeries Apex 7 TKL',2800000,'2025-01-15','2026-01-15','Đang sử dụng'),(10,'Corsair','Corsair K70 RGB TKL',3200000,'2025-02-28','2027-02-28','Đang sử dụng'),(11,'DarkAlien','DarkAlien R65',1800000,'2025-01-24','2027-01-24','Đang sử dụng'),(12,'DarkAlien','DarkAlien R65',1800000,'2025-01-24','2027-01-24','Đang sử dụng'),(13,'DarkAlien','DarkAlien R65',1800000,'2025-01-24','2027-01-24','Đang sử dụng'),(14,'HyperX','HyperX Alloy Origins 60',2000000,'2024-12-01','2025-12-01','Đang sử dụng'),(15,'Logitech','Logitech G915 TKL',5000000,'2025-03-10','2028-03-10','Đang sử dụng'),(16,'Razer','Razer DeathStalker V2',3500000,'2025-02-05','2026-02-05','Đang sử dụng'),(17,'SteelSeries','SteelSeries Apex 3',1500000,'2025-03-12','2026-03-12','Đang sử dụng'),(18,'Corsair','Corsair K100 RGB',4500000,'2024-11-10','2026-11-10','Đang sử dụng'),(19,'Keychron','Keychron Q1',3000000,'2025-01-25','2027-01-25','Đang sử dụng'),(20,'Ducky','Ducky Shine 7',3500000,'2025-03-07','2026-03-07','Đang sử dụng'),(21,'HyperX','HyperX Alloy Elite 2',2500000,'2025-02-15','2026-02-15','Đang sử dụng'),(22,'Logitech','Logitech G613',2000000,'2024-12-20','2025-12-20','Đang sử dụng'),(23,'Razer','Razer Ornata V3',1800000,'2025-03-13','2026-03-13','Đang sử dụng'),(24,'Keychron','Keychron Q1',3000000,'2025-01-25','2027-01-25','Trong kho'),(25,'Ducky','Ducky Shine 7',3500000,'2025-03-07','2026-03-07','Trong kho'),(26,'HyperX','HyperX Alloy Elite 2',2500000,'2025-02-15','2026-02-15','Trong kho'),(27,'Logitech','Logitech G613',2000000,'2024-12-20','2025-12-20','Trong kho'),(28,'Razer','Razer Ornata V3',1800000,'2025-03-13','2026-03-13','Trong kho');
/*!40000 ALTER TABLE `keyboards` ENABLE KEYS */;
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
