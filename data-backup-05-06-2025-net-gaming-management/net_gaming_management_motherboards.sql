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
-- Table structure for table `motherboards`
--

DROP TABLE IF EXISTS `motherboards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `motherboards` (
  `motherboard_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` int NOT NULL DEFAULT '0',
  `socket` varchar(20) NOT NULL,
  `chipset` varchar(50) NOT NULL,
  `ram_slots` int NOT NULL,
  `max_ram` int NOT NULL,
  `ram_speed` int NOT NULL,
  `storage_slots` int NOT NULL,
  `sata_ports` int NOT NULL,
  `m2_slots` int NOT NULL,
  `max_storage_capacity` int NOT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  `cpu_id` int DEFAULT NULL,
  `psu_id` int DEFAULT NULL,
  `gpu_id` int DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  PRIMARY KEY (`motherboard_id`),
  KEY `cpu_id` (`cpu_id`),
  KEY `psu_id` (`psu_id`),
  KEY `gpu_id` (`gpu_id`),
  CONSTRAINT `motherboards_ibfk_1` FOREIGN KEY (`cpu_id`) REFERENCES `cpus` (`cpu_id`) ON DELETE SET NULL,
  CONSTRAINT `motherboards_ibfk_2` FOREIGN KEY (`psu_id`) REFERENCES `psus` (`psu_id`) ON DELETE SET NULL,
  CONSTRAINT `motherboards_ibfk_3` FOREIGN KEY (`gpu_id`) REFERENCES `gpus` (`gpu_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motherboards`
--

LOCK TABLES `motherboards` WRITE;
/*!40000 ALTER TABLE `motherboards` DISABLE KEYS */;
INSERT INTO `motherboards` VALUES (1,'ASUS','ASUS TUF Gaming B550-Plus',3500000,'AM4','B550',4,128,4400,6,4,2,96000,'Đang sử dụng',1,1,1,'2025-03-14','2028-03-14'),(2,'ASUS','ASUS TUF Gaming B550-Plus',3500000,'AM4','B550',4,128,4400,6,4,2,96000,'Đang sử dụng',2,2,3,'2025-03-13','2028-03-13'),(3,'ASUS','ASUS TUF Gaming B550-Plus',3500000,'AM4','B550',4,128,4400,6,4,2,96000,'Đang sử dụng',3,3,3,'2025-01-15','2028-01-15'),(4,'ASUS','ASUS TUF Gaming B550-Plus',3500000,'AM4','B550',4,128,4400,6,4,2,96000,'Đang sử dụng',4,4,4,'2025-02-05','2028-02-05'),(5,'ASUS','ASUS TUF Gaming B550-Plus',3500000,'AM4','B550',4,128,4400,6,4,2,96000,'Đang sử dụng',5,5,5,'2025-03-07','2028-03-07'),(6,'MSI','MSI PRO B760M-A',4000000,'LGA1700','B760',4,128,5333,6,4,2,96000,'Đang sử dụng',6,6,6,'2025-02-10','2028-02-10'),(7,'MSI','MSI PRO B760M-A',4000000,'LGA1700','B760',4,128,5333,6,4,2,96000,'Đang sử dụng',7,7,7,'2025-02-20','2028-02-20'),(8,'MSI','MSI PRO B760M-A',4000000,'LGA1700','B760',4,128,5333,6,4,2,96000,'Đang sử dụng',8,8,8,'2025-02-28','2028-02-28'),(9,'MSI','MSI PRO B760M-A',4000000,'LGA1700','B760',4,128,5333,6,4,2,96000,'Đang sử dụng',9,9,9,'2025-03-12','2028-03-12'),(10,'MSI','MSI PRO B760M-A',4000000,'LGA1700','B760',4,128,5333,6,4,2,96000,'Đang sử dụng',10,10,10,'2025-02-15','2028-02-15'),(11,'Gigabyte','Gigabyte X670 AORUS Elite',6500000,'AM5','X670',4,128,6000,8,6,2,144000,'Đang sử dụng',11,11,11,'2024-12-15','2029-12-15'),(12,'Gigabyte','Gigabyte X670 AORUS Elite',6500000,'AM5','X670',4,128,6000,8,6,2,144000,'Đang sử dụng',12,12,12,'2024-11-25','2029-11-25'),(13,'Gigabyte','Gigabyte X670 AORUS Elite',6500000,'AM5','X670',4,128,6000,8,6,2,144000,'Đang sử dụng',13,13,13,'2024-12-01','2029-12-01'),(14,'Gigabyte','Gigabyte X670 AORUS Elite',6500000,'AM5','X670',4,128,6000,8,6,2,144000,'Đang sử dụng',14,14,14,'2024-11-10','2029-11-10'),(15,'Gigabyte','Gigabyte X670 AORUS Elite',6500000,'AM5','X670',4,128,6000,8,6,2,144000,'Đang sử dụng',15,15,15,'2024-12-20','2029-12-20'),(16,'ASUS','ASUS ROG Strix Z790-E',9500000,'LGA1700','Z790',4,192,7200,8,6,2,144000,'Đang sử dụng',16,16,16,'2025-01-05','2030-01-05'),(17,'ASUS','ASUS ROG Strix Z790-E',9500000,'LGA1700','Z790',4,192,7200,8,6,2,144000,'Đang sử dụng',17,17,17,'2025-03-01','2030-03-01'),(18,'ASUS','ASUS ROG Strix Z790-E',9500000,'LGA1700','Z790',4,192,7200,8,6,2,144000,'Đang sử dụng',18,18,18,'2025-03-10','2030-03-10'),(19,'ASUS','ASUS ROG Strix Z790-E',9500000,'LGA1700','Z790',4,192,7200,8,6,2,144000,'Đang sử dụng',19,19,19,'2025-01-25','2030-01-25'),(20,'ASUS','ASUS ROG Strix Z790-E',9500000,'LGA1700','Z790',4,192,7200,8,6,2,144000,'Đang sử dụng',20,20,20,'2025-03-13','2030-03-13'),(21,'ASUS','ASUS ROG Strix Z790-E',9500000,'LGA1700','Z790',4,192,7200,8,6,2,144000,'Trong kho',21,21,21,'2025-03-13','2030-03-13');
/*!40000 ALTER TABLE `motherboards` ENABLE KEYS */;
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
