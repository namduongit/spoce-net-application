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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `account_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Quản trị viên','Nhân viên','Người chơi') NOT NULL,
  `status` enum('Online','Offline','Locked') DEFAULT 'Offline',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'namduongit','89a76f3e5e7ce360ca7487cd6dfe94d1','Quản trị viên','Online','2025-05-04 14:17:10'),(2,'luanchenh','d23516af37e4fea7039b05510ee6524f','Quản trị viên','Offline','2025-05-04 14:17:10'),(3,'staff1','25f9e794323b453885f5181f1b624d0b','Nhân viên','Offline','2025-05-04 14:17:10'),(4,'staff2','25f9e794323b453885f5181f1b624d0b','Nhân viên','Offline','2025-05-04 14:17:10'),(5,'staff3','25f9e794323b453885f5181f1b624d0b','Nhân viên','Offline','2025-05-04 14:17:10'),(6,'ChienBinhMa','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'),(7,'gaconNgauLoi','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'),(8,'boanco','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'),(9,'haycamnhannoidau','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'),(10,'naruto','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'),(11,'songokuuuu','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'),(12,'cadic821','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'),(13,'alibabaDepZai','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'),(14,'DoMayVoDuoc','25f9e794323b453885f5181f1b624d0b','Người chơi','Offline','2025-05-04 14:17:10'), ;
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_account_insert` AFTER INSERT ON `accounts` FOR EACH ROW BEGIN
    -- Nếu là Quản trị viên hoặc Nhân viên, thêm vào bảng staffs với thông tin mặc định
    IF NEW.role IN ('Quản trị viên', 'Nhân viên') THEN
        INSERT INTO staffs (account_id, full_name, birth_date, gender, phone, email, address, cccd, avatar)
        VALUES (NEW.account_id, 'Chưa cập nhật', '2000-01-01', 'Chưa đặt', NULL, NULL, NULL, NULL, NULL);

    -- Nếu là Người chơi, thêm vào bảng players với số dư mặc định là 0
    ELSEIF NEW.role = 'Người chơi' THEN
        INSERT INTO players (account_id, balance)
        VALUES (NEW.account_id, 0);
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
