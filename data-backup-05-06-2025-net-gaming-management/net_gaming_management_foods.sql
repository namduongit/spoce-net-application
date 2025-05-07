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
-- Table structure for table `foods`
--

DROP TABLE IF EXISTS `foods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `foods` (
  `food_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `price` int NOT NULL,
  `category_id` int NOT NULL,
  `quantity` int DEFAULT '0',
  `image` varchar(100) DEFAULT NULL,
  `status` varchar(10) GENERATED ALWAYS AS ((case when (`quantity` > 0) then _utf8mb4'Còn hàng' else _utf8mb4'Hết hàng' end)) STORED,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`food_id`),
  UNIQUE KEY `name` (`name`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `foods_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foods`
--

LOCK TABLES `foods` WRITE;
/*!40000 ALTER TABLE `foods` DISABLE KEYS */;
INSERT INTO `foods` (`food_id`, `name`, `price`, `category_id`, `quantity`, `image`, `created_at`) VALUES (1,'Cơm gà xối mỡ',50000,1,110,'com-ga-xoi-mo.png','2025-05-04 14:17:10'),(2,'Bún bò Huế',45000,1,15,'bun-bo-hue.png','2025-05-04 14:17:10'),(3,'Phở tái chín',40000,1,20,'pho-tai-chin.png','2025-05-04 14:17:10'),(4,'Cơm tấm sườn bì chả',55000,1,12,'com-tam-suon-bi-cha.png','2025-05-04 14:17:10'),(5,'Mì Quảng tôm thịt',48000,1,18,'mi-quang-tom-thit.png','2025-05-04 14:17:10'),(6,'Hủ tiếu Nam Vang',47000,1,8,'hu-tieu-nam-vang.png','2025-05-04 14:17:10'),(7,'Bánh mì thịt nướng',30000,1,25,'banh-mi-thit-nuong.png','2025-05-04 14:17:10'),(8,'Cà phê sữa đá',25000,2,30,'ca-phe-sua-da.png','2025-05-04 14:17:10'),(9,'Trà đào cam sả',35000,2,20,'tra-dao-cam-sa.png','2025-05-04 14:17:10'),(10,'Nước ép cam',30000,2,18,'nuoc-ep-cam.png','2025-05-04 14:17:10'),(11,'Nước ép ổi',28000,2,22,'nuoc-ep-oi.png','2025-05-04 14:17:10'),(12,'Sinh tố bơ',40000,2,15,'sinh-to-bo.png','2025-05-04 14:17:10'),(13,'Trà sữa trân châu',38000,2,25,'tra-sua-tran-chau.png','2025-05-04 14:17:10'),(14,'Soda chanh dây',32000,2,10,'soda-chanh-day.png','2025-05-04 14:17:10'),(15,'Bánh flan',20000,3,20,'banh-flan.png','2025-05-04 14:17:10'),(16,'Chè thập cẩm',30000,3,18,'che-thap-cam.png','2025-05-04 14:17:10'),(17,'Kem dừa',35000,3,12,'kem-dua.jpg','2025-05-04 14:17:10'),(18,'Sữa chua nếp cẩm',25000,3,15,'sua-chua-nep-cam.png','2025-05-04 14:17:10'),(19,'Rau câu dừa',22000,3,25,'rau-cau-dua.png','2025-05-04 14:17:10'),(20,'Bánh mochi',40000,3,10,'banh-mochi.png','2025-05-04 14:17:10');
/*!40000 ALTER TABLE `foods` ENABLE KEYS */;
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
