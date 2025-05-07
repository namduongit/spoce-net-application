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
-- Table structure for table `cpus`
--

DROP TABLE IF EXISTS `cpus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cpus` (
  `cpu_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` int NOT NULL DEFAULT '0',
  `clock_speed` float NOT NULL,
  `cores` int NOT NULL,
  `threads` int NOT NULL,
  `integrated_gpu` tinyint(1) DEFAULT '0',
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`cpu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cpus`
--

LOCK TABLES `cpus` WRITE;
/*!40000 ALTER TABLE `cpus` DISABLE KEYS */;
INSERT INTO `cpus` VALUES (1,'AMD','AMD Ryzen 5 5600X',4500000,4.6,6,12,0,'2025-03-14','2028-03-14','Đang sử dụng'),(2,'AMD','AMD Ryzen 5 5600X',4500000,4.6,6,12,0,'2025-03-13','2028-03-13','Đang sử dụng'),(3,'AMD','AMD Ryzen 5 5600X',4500000,4.6,6,12,0,'2025-01-15','2028-01-15','Đang sử dụng'),(4,'AMD','AMD Ryzen 5 5600X',4500000,4.6,6,12,0,'2025-02-05','2028-02-05','Đang sử dụng'),(5,'AMD','AMD Ryzen 5 5600X',4500000,4.6,6,12,0,'2025-03-07','2028-03-07','Đang sử dụng'),(6,'Intel','Intel Core i5-13400',5500000,4.6,10,16,1,'2025-02-20','2028-02-20','Đang sử dụng'),(7,'Intel','Intel Core i5-13400',5500000,4.6,10,16,1,'2025-02-10','2028-02-10','Đang sử dụng'),(8,'Intel','Intel Core i5-13400',5500000,4.6,10,16,1,'2025-02-28','2028-02-28','Đang sử dụng'),(9,'Intel','Intel Core i5-13400',5500000,4.6,10,16,1,'2025-03-12','2028-03-12','Đang sử dụng'),(10,'Intel','Intel Core i5-13400',5500000,4.6,10,16,1,'2025-02-15','2028-02-15','Đang sử dụng'),(11,'AMD','AMD Ryzen 7 7700X',8500000,5.4,8,16,1,'2024-12-15','2029-12-15','Đang sử dụng'),(12,'AMD','AMD Ryzen 7 7700X',8500000,5.4,8,16,1,'2024-11-25','2029-11-25','Đang sử dụng'),(13,'AMD','AMD Ryzen 7 7700X',8500000,5.4,8,16,1,'2024-12-01','2029-12-01','Đang sử dụng'),(14,'AMD','AMD Ryzen 7 7700X',8500000,5.4,8,16,1,'2024-11-10','2029-11-10','Đang sử dụng'),(15,'AMD','AMD Ryzen 7 7700X',8500000,5.4,8,16,1,'2024-12-20','2029-12-20','Đang sử dụng'),(16,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-01-05','2030-01-05','Đang sử dụng'),(17,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-03-01','2030-03-01','Đang sử dụng'),(18,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-03-10','2030-03-10','Đang sử dụng'),(19,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-01-25','2030-01-25','Đang sử dụng'),(20,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-03-13','2030-03-13','Đang sử dụng'),(21,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-01-05','2030-01-05','Đang sử dụng'),(22,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-03-01','2030-03-01','Trong kho'),(23,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-03-10','2030-03-10','Trong kho'),(24,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-01-25','2030-01-25','Trong kho'),(25,'Intel','Intel Core i9-13900K',14500000,5.8,24,32,1,'2025-03-13','2030-03-13','Trong kho');
/*!40000 ALTER TABLE `cpus` ENABLE KEYS */;
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
