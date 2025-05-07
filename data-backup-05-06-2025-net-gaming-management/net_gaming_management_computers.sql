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
-- Table structure for table `computers`
--

DROP TABLE IF EXISTS `computers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `computers` (
  `computer_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price_per_hour` int NOT NULL,
  `motherboard_id` int NOT NULL,
  `mouse_id` int DEFAULT NULL,
  `keyboard_id` int DEFAULT NULL,
  `monitor_id` int DEFAULT NULL,
  `headphone_id` int DEFAULT NULL,
  `rom_id` int DEFAULT NULL,
  `room_id` int DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Thiếu linh kiện','Bảo trì','Hỏng','Đang chờ sử dụng') DEFAULT 'Trong kho',
  PRIMARY KEY (`computer_id`),
  KEY `motherboard_id` (`motherboard_id`),
  KEY `mouse_id` (`mouse_id`),
  KEY `keyboard_id` (`keyboard_id`),
  KEY `monitor_id` (`monitor_id`),
  KEY `headphone_id` (`headphone_id`),
  KEY `rom_id` (`rom_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `computers_ibfk_1` FOREIGN KEY (`motherboard_id`) REFERENCES `motherboards` (`motherboard_id`) ON DELETE CASCADE,
  CONSTRAINT `computers_ibfk_2` FOREIGN KEY (`mouse_id`) REFERENCES `mouse` (`mouse_id`) ON DELETE SET NULL,
  CONSTRAINT `computers_ibfk_3` FOREIGN KEY (`keyboard_id`) REFERENCES `keyboards` (`keyboard_id`) ON DELETE SET NULL,
  CONSTRAINT `computers_ibfk_4` FOREIGN KEY (`monitor_id`) REFERENCES `monitors` (`monitor_id`) ON DELETE SET NULL,
  CONSTRAINT `computers_ibfk_5` FOREIGN KEY (`headphone_id`) REFERENCES `headphones` (`headphone_id`) ON DELETE SET NULL,
  CONSTRAINT `computers_ibfk_6` FOREIGN KEY (`rom_id`) REFERENCES `roms` (`rom_id`) ON DELETE SET NULL,
  CONSTRAINT `computers_ibfk_7` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `computers`
--

LOCK TABLES `computers` WRITE;
/*!40000 ALTER TABLE `computers` DISABLE KEYS */;
INSERT INTO `computers` VALUES (1,'Máy thường Spoce 1',10000,1,1,1,1,1,1,1,'Đang chờ sử dụng'),(2,'Máy thường Spoce 2',10000,2,2,2,2,2,2,1,'Đang chờ sử dụng'),(3,'Máy thường Spoce 3',10000,3,3,3,3,3,3,1,'Đang chờ sử dụng'),(4,'Máy thường Spoce 4',10000,4,4,4,4,4,4,1,'Đang chờ sử dụng'),(5,'Máy thường Spoce 5',10000,5,5,5,5,5,5,1,'Đang chờ sử dụng'),(6,'Máy vip Spoce 1',15000,6,6,6,6,6,6,1,'Đang chờ sử dụng'),(7,'Máy vip Spoce 2',15000,7,7,7,7,7,7,1,'Đang chờ sử dụng'),(8,'Máy vip Spoce 3',15000,8,8,8,8,8,8,1,'Đang chờ sử dụng'),(9,'Máy vip Spoce 4',15000,9,9,9,9,9,9,1,'Đang chờ sử dụng'),(10,'Máy vip Spoce 5',15000,10,10,10,10,10,10,1,'Đang chờ sử dụng'),(11,'Máy cao cấp Spoce 1',20000,11,11,11,11,11,11,1,'Đang chờ sử dụng'),(12,'Máy cao cấp Spoce 2',20000,12,12,12,12,12,12,1,'Đang chờ sử dụng'),(13,'Máy cao cấp Spoce 3',20000,13,13,13,13,13,13,1,'Đang chờ sử dụng'),(14,'Máy cao cấp Spoce 4',20000,14,14,14,14,14,14,1,'Đang chờ sử dụng'),(15,'Máy cao cấp Spoce 5',20000,15,15,15,15,15,15,1,'Đang chờ sử dụng'),(16,'Siêu máy tính Spoce 1',30000,16,16,16,16,16,16,1,'Đang chờ sử dụng'),(17,'Siêu máy tính Spoce 2',30000,17,17,17,17,17,17,1,'Đang chờ sử dụng'),(18,'Siêu máy tính Spoce 3',30000,18,18,18,18,18,18,1,'Đang chờ sử dụng'),(19,'Siêu máy tính Spoce 4',30000,19,19,19,19,19,19,1,'Đang chờ sử dụng'),(20,'Siêu máy tính Spoce 5',30000,20,20,20,20,20,20,1,'Đang chờ sử dụng');
/*!40000 ALTER TABLE `computers` ENABLE KEYS */;
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
