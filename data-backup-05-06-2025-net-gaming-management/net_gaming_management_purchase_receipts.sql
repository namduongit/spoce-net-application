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
-- Table structure for table `purchase_receipts`
--

DROP TABLE IF EXISTS `purchase_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_receipts` (
  `receipt_id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `staff_id` int DEFAULT NULL,
  `status` enum('Chờ xác nhận','Đã xác nhận','Đã hủy') DEFAULT 'Chờ xác nhận',
  `note` text,
  `total` int DEFAULT '0',
  PRIMARY KEY (`receipt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_receipts`
--

LOCK TABLES `purchase_receipts` WRITE;
/*!40000 ALTER TABLE `purchase_receipts` DISABLE KEYS */;
INSERT INTO `purchase_receipts` VALUES (1,'2025-05-06 19:50:08','2025-05-06 19:50:23',1,'Chờ xác nhận','Nhập hàng tuần đầu tháng',404000),(2,'2025-05-06 19:50:08','2025-05-06 19:50:23',2,'Chờ xác nhận','Nhập thêm hàng do thiếu',480000),(3,'2025-05-06 19:50:08','2025-05-06 19:50:23',1,'Đã xác nhận','Phiếu nhập đã kiểm kho',390000),(4,'2025-05-07 08:56:52','2025-05-07 08:56:52',1,'Chờ xác nhận','',930000),(5,'2025-05-07 08:57:34','2025-05-07 08:59:51',1,'Đã xác nhận','',3000000);
/*!40000 ALTER TABLE `purchase_receipts` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_update_food_quantity_after_receipt_confirm` AFTER UPDATE ON `purchase_receipts` FOR EACH ROW BEGIN
    IF OLD.status = 'Chờ xác nhận' AND NEW.status = 'Đã xác nhận' THEN
        -- Cập nhật số lượng món ăn
    UPDATE foods f
        JOIN purchase_receipt_detail d ON f.food_id = d.food_id
        SET f.quantity = f.quantity + d.quantity
    WHERE d.receipt_id = NEW.receipt_id;
END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-07  9:05:19
