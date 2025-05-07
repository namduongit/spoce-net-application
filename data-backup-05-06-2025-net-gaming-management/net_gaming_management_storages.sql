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
-- Table structure for table `storages`
--

DROP TABLE IF EXISTS `storages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storages` (
  `storage_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` int NOT NULL DEFAULT '0',
  `type` enum('HDD','SSD','NVMe') NOT NULL,
  `capacity` int NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`storage_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storages`
--

LOCK TABLES `storages` WRITE;
/*!40000 ALTER TABLE `storages` DISABLE KEYS */;
INSERT INTO `storages` VALUES (1,'Samsung','Samsung 970 EVO Plus',2500000,'NVMe',1000,'2025-03-14','2030-03-14','Đang sử dụng'),(2,'Western Digital','Western Digital WD Black SN850X',4500000,'NVMe',2000,'2025-02-10','2030-02-10','Đang sử dụng'),(3,'Samsung','Samsung 990 PRO',3000000,'NVMe',1000,'2025-03-13','2030-03-13','Đang sử dụng'),(4,'Seagate','Seagate FireCuda 530',2000000,'NVMe',500,'2024-11-25','2029-11-25','Đang sử dụng'),(5,'Crucial','Crucial P3 Plus',3500000,'NVMe',2000,'2025-03-01','2028-03-01','Đang sử dụng'),(6,'Western Digital','Western Digital WD Black SN770',2200000,'NVMe',1000,'2025-03-12','2030-03-12','Đang sử dụng'),(7,'Seagate','Seagate BarraCuda 510',1500000,'NVMe',512,'2024-11-10','2029-11-10','Đang sử dụng'),(8,'Crucial','Crucial P5',2300000,'NVMe',1000,'2025-01-25','2030-01-25','Đang sử dụng'),(9,'Samsung','Samsung 980',1800000,'NVMe',500,'2025-03-07','2028-03-07','Đang sử dụng'),(10,'Crucial','Crucial P2',3200000,'NVMe',2000,'2025-03-13','2030-03-13','Đang sử dụng'),(11,'Western Digital','Western Digital WD Blue 3D',1200000,'SSD',256,'2025-02-20','2028-02-20','Đang sử dụng'),(12,'Crucial','Crucial MX500',1400000,'SSD',500,'2025-01-05','2028-01-05','Đang sử dụng'),(13,'Samsung',' Samsung 870 QVO',0,'SSD',1000,'2025-01-15','2028-01-15','Đang sử dụng'),(14,'Samsung','Samsung 860 EVO',1000000,'SSD',256,'2025-02-05','2027-02-05','Đang sử dụng'),(15,'Crucial','Crucial BX500',1100000,'SSD',500,'2025-03-10','2028-03-10','Đang sử dụng'),(16,'Western Digital','Western Digital WD Green',1300000,'SSD',480,'2025-02-15','2028-02-15','Đang sử dụng'),(17,'Seagate','Seagate Barracuda',1500000,'HDD',4000,'2024-12-15','2027-12-15','Đang sử dụng'),(18,'Western Digital','Western Digital WD Red',2000000,'HDD',2000,'2025-02-28','2028-02-28','Đang sử dụng'),(19,'Seagate','Seagate IronWolf',2500000,'HDD',4000,'2024-12-01','2027-12-01','Đang sử dụng'),(20,'Seagate','Seagate Expansion',1800000,'HDD',2000,'2024-12-20','2027-12-20','Đang sử dụng'),(21,'Western Digital','Western Digital WD Green',1300000,'SSD',480,'2025-02-15','2028-02-15','Đang sử dụng'),(22,'Seagate','Seagate Barracuda',1500000,'HDD',4000,'2024-12-15','2027-12-15','Trong kho'),(23,'Western Digital','Western Digital WD Red',2000000,'HDD',2000,'2025-02-28','2028-02-28','Trong kho'),(24,'Seagate','Seagate IronWolf',2500000,'HDD',4000,'2024-12-01','2027-12-01','Trong kho'),(25,'Seagate','Seagate Expansion',1800000,'HDD',2000,'2024-12-20','2027-12-20','Trong kho');
/*!40000 ALTER TABLE `storages` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07  9:05:22
