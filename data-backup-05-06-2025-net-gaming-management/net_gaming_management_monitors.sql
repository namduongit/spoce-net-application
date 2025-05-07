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
-- Table structure for table `monitors`
--

DROP TABLE IF EXISTS `monitors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monitors` (
  `monitor_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `price` int NOT NULL DEFAULT '0',
  `size` float NOT NULL,
  `refresh_rate` int NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`monitor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monitors`
--

LOCK TABLES `monitors` WRITE;
/*!40000 ALTER TABLE `monitors` DISABLE KEYS */;
INSERT INTO `monitors` VALUES (1,'ASUS','ASUS TUF Gaming VG27AQ',7000000,27,165,'2025-03-14','2027-03-14','Đang sử dụng'),(2,'Dell','Dell Alienware AW2723DF',15000000,27,280,'2025-02-10','2028-02-10','Đang sử dụng'),(3,'LG',' LG UltraGear 27GP950-B',0,27,144,'2024-12-15','2026-12-15','Đang sử dụng'),(4,'Samsung','Samsung Odyssey G7',13000000,32,240,'2025-01-05','2027-01-05','Đang sử dụng'),(5,'Acer','Acer Predator XB273U',14000000,27,270,'2025-03-13','2028-03-13','Đang sử dụng'),(6,'ASUS','ASUS ROG Swift PG259QN',16000000,24.5,360,'2025-02-20','2028-02-20','Đang sử dụng'),(7,'Dell','Dell S2721DGF',8000000,27,165,'2024-11-25','2025-11-25','Đang sử dụng'),(8,'LG','LG UltraGear 32GP850-B',11000000,32,180,'2025-03-01','2027-03-01','Đang sử dụng'),(9,'Samsung','Samsung Odyssey Neo G8',20000000,32,240,'2025-01-15','2028-01-15','Đang sử dụng'),(10,'Acer','Acer Nitro XV272U',7500000,27,170,'2025-02-28','2026-02-28','Đang sử dụng'),(11,'MSI','MSI Optix MAG274QRF-QD',9000000,27,165,'2024-12-01','2026-12-01','Đang sử dụng'),(12,'ASUS','ASUS ROG Strix XG27UCS',8500000,27,160,'2025-03-10','2027-03-10','Đang sử dụng'),(13,'Dell','Dell Alienware AW3423DWF',25000000,34,165,'2025-02-05','2028-02-05','Đang sử dụng'),(14,'LG','LG UltraGear 24GN650-B',6000000,24,144,'2025-03-12','2026-03-12','Đang sử dụng'),(15,'Samsung','Samsung Odyssey G5',6500000,27,144,'2024-11-10','2025-11-10','Đang sử dụng'),(16,'Acer','Acer Predator X34',18000000,34,180,'2025-01-25','2027-01-25','Đang sử dụng'),(17,'MSI','MSI Optix MPG321UR-QD',15000000,32,144,'2025-03-07','2028-03-07','Đang sử dụng'),(18,'ASUS','ASUS TUF Gaming VG279QM',9500000,27,280,'2025-02-15','2027-02-15','Đang sử dụng'),(19,'Dell','Dell G3223Q',12000000,32,144,'2024-12-20','2026-12-20','Đang sử dụng'),(20,'LG','LG UltraGear 27GL850-B',9000000,27,144,'2025-03-13','2026-03-13','Đang sử dụng'),(21,'Acer','Acer Predator X34',18000000,34,180,'2025-01-25','2027-01-25','Trong kho'),(22,'MSI','MSI Optix MPG321UR-QD',15000000,32,144,'2025-03-07','2028-03-07','Trong kho'),(23,'ASUS','ASUS TUF Gaming VG279QM',9500000,27,280,'2025-02-15','2027-02-15','Trong kho'),(24,'Dell','Dell G3223Q',12000000,32,144,'2024-12-20','2026-12-20','Trong kho'),(25,'LG','LG UltraGear 27GL850-B',9000000,27,144,'2025-03-13','2026-03-13','Trong kho');
/*!40000 ALTER TABLE `monitors` ENABLE KEYS */;
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
