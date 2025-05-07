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
-- Table structure for table `psus`
--

DROP TABLE IF EXISTS `psus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `psus` (
  `psu_id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `model` varchar(100) NOT NULL,
  `wattage` int NOT NULL,
  `purchase_date` date DEFAULT NULL,
  `warranty_expiry` date DEFAULT NULL,
  `status` enum('Trong kho','Đang sử dụng','Bảo trì','Hỏng') DEFAULT 'Trong kho',
  PRIMARY KEY (`psu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `psus`
--

LOCK TABLES `psus` WRITE;
/*!40000 ALTER TABLE `psus` DISABLE KEYS */;
INSERT INTO `psus` VALUES (1,'Corsair','Corsair RM750x',750,'2025-03-14','2030-03-14','Đang sử dụng'),(2,'EVGA','EVGA SuperNOVA 850 G5',850,'2025-02-10','2032-02-10','Đang sử dụng'),(3,'Seasonic','Seasonic Focus GX-650',650,'2024-12-15','2034-12-15','Đang sử dụng'),(4,'Cooler Master','Cooler Master MWE Gold 750',750,'2025-01-05','2030-01-05','Đang sử dụng'),(5,'Corsair','Corsair HX1200',1200,'2025-03-13','2035-03-13','Đang sử dụng'),(6,'EVGA','EVGA 650 GQ',650,'2025-02-20','2028-02-20','Đang sử dụng'),(7,'Seasonic','Seasonic Prime TX-1000',1000,'2024-11-25','2034-11-25','Đang sử dụng'),(8,'Cooler Master','Cooler Master V850 SFX',850,'2025-03-01','2030-03-01','Đang sử dụng'),(9,'Corsair','Corsair SF600',600,'2025-01-15','2027-01-15','Đang sử dụng'),(10,'EVGA','EVGA SuperNOVA 750 T2',750,'2025-02-28','2035-02-28','Đang sử dụng'),(11,'Seasonic','Seasonic Focus SGX-500',500,'2024-12-01','2029-12-01','Đang sử dụng'),(12,'Cooler Master','Cooler Master MasterWatt 550',550,'2025-03-10','2028-03-10','Đang sử dụng'),(13,'Corsair','Corsair AX860',860,'2025-02-05','2035-02-05','Đang sử dụng'),(14,'EVGA','EVGA SuperNOVA 1000 P2',1000,'2025-03-12','2032-03-12','Đang sử dụng'),(15,'Seasonic','Seasonic Snow Silent 750',750,'2024-11-10','2034-11-10','Đang sử dụng'),(16,'Cooler Master','Cooler Master V1200 Platinum',1200,'2025-01-25','2030-01-25','Đang sử dụng'),(17,'Corsair','Corsair RM650i',650,'2025-03-07','2030-03-07','Đang sử dụng'),(18,'EVGA','EVGA 450 BR',450,'2025-02-15','2026-02-15','Đang sử dụng'),(19,'Seasonic','Seasonic Prime GX-850',850,'2024-12-20','2034-12-20','Đang sử dụng'),(20,'Cooler Master','Cooler Master MWE Bronze 600',600,'2025-03-13','2028-03-13','Đang sử dụng'),(21,'Cooler Master','Cooler Master V1200 Platinum',1200,'2025-01-25','2030-01-25','Đang sử dụng'),(22,'Corsair','Corsair RM650i',650,'2025-03-07','2030-03-07','Trong kho'),(23,'EVGA','EVGA 450 BR',450,'2025-02-15','2026-02-15','Trong kho'),(24,'Seasonic','Seasonic Prime GX-850',850,'2024-12-20','2034-12-20','Trong kho'),(25,'Cooler Master','Cooler Master MWE Bronze 600',600,'2025-03-13','2028-03-13','Trong kho');
/*!40000 ALTER TABLE `psus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07  9:05:21
