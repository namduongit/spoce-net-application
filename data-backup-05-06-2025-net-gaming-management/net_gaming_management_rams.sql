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
-- Table structure for table `rams`
--

DROP TABLE IF EXISTS `rams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rams` (
  `ram_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` int NOT NULL DEFAULT '0',
  `capacity` int NOT NULL,
  `speed` int NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`ram_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rams`
--

LOCK TABLES `rams` WRITE;
/*!40000 ALTER TABLE `rams` DISABLE KEYS */;
INSERT INTO `rams` VALUES (1,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-03-14','2035-03-14','Đang sử dụng'),(2,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-03-13','2035-03-13','Đang sử dụng'),(3,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-01-15','2035-01-15','Đang sử dụng'),(4,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-02-05','2035-02-05','Đang sử dụng'),(5,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-03-07','2035-03-07','Đang sử dụng'),(6,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-03-14','2035-03-14','Đang sử dụng'),(7,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-03-13','2035-03-13','Đang sử dụng'),(8,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-01-15','2035-01-15','Đang sử dụng'),(9,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-02-05','2035-02-05','Đang sử dụng'),(10,'Corsair','Corsair Vengeance LPX',1200000,8,3200,'2025-03-07','2035-03-07','Đang sử dụng'),(11,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-02-10','2035-02-10','Đang sử dụng'),(12,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-02-20','2035-02-20','Đang sử dụng'),(13,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-02-28','2035-02-28','Đang sử dụng'),(14,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-03-12','2035-03-12','Đang sử dụng'),(15,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-02-15','2035-02-15','Đang sử dụng'),(16,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-02-10','2035-02-10','Đang sử dụng'),(17,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-02-20','2035-02-20','Đang sử dụng'),(18,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-02-28','2035-02-28','Đang sử dụng'),(19,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-03-12','2035-03-12','Đang sử dụng'),(20,'Kingston','Kingston Fury Beast',1500000,8,5200,'2025-02-15','2035-02-15','Đang sử dụng'),(21,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-12-15','2034-12-15','Đang sử dụng'),(22,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-11-25','2034-11-25','Đang sử dụng'),(23,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-12-01','2034-12-01','Đang sử dụng'),(24,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-11-10','2034-11-10','Đang sử dụng'),(25,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-12-20','2034-12-20','Đang sử dụng'),(26,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-12-15','2034-12-15','Đang sử dụng'),(27,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-11-25','2034-11-25','Đang sử dụng'),(28,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-12-01','2034-12-01','Đang sử dụng'),(29,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-11-10','2034-11-10','Đang sử dụng'),(30,'G.Skill','G.Skill Ripjaws V',2200000,16,3600,'2024-12-20','2034-12-20','Đang sử dụng'),(31,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-01-05','2030-01-05','Đang sử dụng'),(32,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-01','2030-03-01','Đang sử dụng'),(33,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-10','2030-03-10','Đang sử dụng'),(34,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-01-25','2030-01-25','Đang sử dụng'),(35,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-13','2030-03-13','Đang sử dụng'),(36,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-01-05','2030-01-05','Đang sử dụng'),(37,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-01','2030-03-01','Đang sử dụng'),(38,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-10','2030-03-10','Đang sử dụng'),(39,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-01-25','2030-01-25','Đang sử dụng'),(40,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-13','2030-03-13','Đang sử dụng'),(41,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-01-05','2030-01-05','Đang sử dụng'),(42,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-01','2030-03-01','Đang sử dụng'),(43,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-10','2030-03-10','Trong kho'),(44,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-01-25','2030-01-25','Trong kho'),(45,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-13','2030-03-13','Trong kho'),(46,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-01-05','2030-01-05','Trong kho'),(47,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-01','2030-03-01','Trong kho'),(48,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-10','2030-03-10','Trong kho'),(49,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-01-25','2030-01-25','Trong kho'),(50,'Crucial','Crucial DDR5 Pro',2800000,16,5600,'2025-03-13','2030-03-13','Trong kho');
/*!40000 ALTER TABLE `rams` ENABLE KEYS */;
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
