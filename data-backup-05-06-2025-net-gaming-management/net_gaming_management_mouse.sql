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
-- Table structure for table `mouse`
--

DROP TABLE IF EXISTS `mouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mouse` (
  `mouse_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` int NOT NULL DEFAULT '0',
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`mouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mouse`
--

LOCK TABLES `mouse` WRITE;
/*!40000 ALTER TABLE `mouse` DISABLE KEYS */;
INSERT INTO `mouse` VALUES (1,'Logitech','Logitech G102',500000,'2025-03-14','2026-03-14','Đang sử dụng'),(2,'Logitech','Logitech G304',800000,'2025-03-14','2026-03-14','Đang sử dụng'),(3,'Logitech','Logitech G Pro X Superlight',3000000,'2025-03-14','2026-03-14','Đang sử dụng'),(4,'Logitech','Logitech G Pro X Superlight 2',3500000,'2025-03-14','2026-03-14','Đang sử dụng'),(5,'Logitech','Logitech MX Master 3',2500000,'2025-03-01','2028-03-01','Đang sử dụng'),(6,'SteelSeries','SteelSeries Rival 3',600000,'2024-12-01','2025-12-01','Đang sử dụng'),(7,'SteelSeries','Aerox 5 Wireless',2000000,'2025-03-13','2027-03-13','Đang sử dụng'),(8,'SteelSeries','Aerox 3 Wireless',1800000,'2025-02-15','2026-02-15','Đang sử dụng'),(9,'SteelSeries','Prime Wireless',2500000,'2024-11-01','2026-11-01','Đang sử dụng'),(10,'Corsair','Corsair Harpoon RGB',700000,'2025-02-15','2026-02-15','Đang sử dụng'),(11,'Corsair','Corsair Harpoon RGB',700000,'2025-02-15','2026-02-15','Đang sử dụng'),(12,'Razer','Razer Basilisk V3',1500000,'2024-11-20','2025-11-20','Đang sử dụng'),(13,'Razer','Razer DeathAdder V2',1200000,'2025-01-10','2027-01-10','Đang sử dụng'),(14,'HyperX','HyperX Pulsefire Surge',900000,'2025-03-13','2026-03-13','Đang sử dụng'),(15,'Zowie','Zowie EC2',1800000,'2025-02-28','2027-02-28','Đang sử dụng'),(16,'DareU','DareU A950 Pro',1200000,'2025-03-13','2027-03-13','Đang sử dụng'),(17,'DareU','DareU A980 Pro Max',1500000,'2025-01-15','2028-01-15','Đang sử dụng'),(18,'DareU','DareU A955',1000000,'2024-12-20','2026-12-20','Đang sử dụng'),(19,'DareU','DareU EM901X',800000,'2025-02-10','2026-02-10','Đang sử dụng'),(20,'DareU','DareU A970',900000,'2025-03-01','2026-03-01','Đang sử dụng'),(21,'Logitech','Logitech G102',500000,'2025-03-14','2026-03-14','Trong kho'),(22,'Logitech','Logitech G304',800000,'2025-03-14','2026-03-14','Trong kho'),(23,'Logitech','Logitech G Pro X Superlight',3000000,'2025-03-14','2026-03-14','Trong kho'),(24,'Logitech','Logitech G Pro X Superlight 2',3500000,'2025-03-14','2026-03-14','Trong kho'),(25,'Logitech','Logitech MX Master 3',2500000,'2025-03-01','2028-03-01','Trong kho');
/*!40000 ALTER TABLE `mouse` ENABLE KEYS */;
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
