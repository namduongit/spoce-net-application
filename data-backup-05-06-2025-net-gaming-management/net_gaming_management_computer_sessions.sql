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
-- Table structure for table `computer_sessions`
--

DROP TABLE IF EXISTS `computer_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `computer_sessions` (
  `session_id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT NULL,
  `computer_id` int NOT NULL,
  `start_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `end_time` datetime DEFAULT NULL,
  `duration` int GENERATED ALWAYS AS (timestampdiff(MINUTE,`start_time`,`end_time`)) STORED,
  `total_cost` int NOT NULL DEFAULT '0',
  `is_guest` tinyint(1) GENERATED ALWAYS AS ((`player_id` is null)) STORED,
  `staff_id` int DEFAULT NULL,
  PRIMARY KEY (`session_id`),
  KEY `staff_id` (`staff_id`),
  KEY `player_id` (`player_id`),
  KEY `computer_id` (`computer_id`),
  CONSTRAINT `computer_sessions_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`staff_id`),
  CONSTRAINT `computer_sessions_ibfk_2` FOREIGN KEY (`player_id`) REFERENCES `players` (`player_id`),
  CONSTRAINT `computer_sessions_ibfk_3` FOREIGN KEY (`computer_id`) REFERENCES `computers` (`computer_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `computer_sessions`
--

LOCK TABLES `computer_sessions` WRITE;
/*!40000 ALTER TABLE `computer_sessions` DISABLE KEYS */;
INSERT INTO `computer_sessions` (`session_id`, `player_id`, `computer_id`, `start_time`, `end_time`, `total_cost`, `staff_id`) VALUES (1,NULL,8,'2025-05-04 14:18:59','2025-05-04 14:28:26',2250,1),(2,NULL,7,'2025-05-04 14:19:01','2025-05-04 14:28:20',2250,1),(3,NULL,5,'2025-05-04 14:19:03','2025-05-04 14:28:24',1500,1),(4,NULL,1,'2025-05-04 16:31:26','2025-05-04 16:31:49',0,1),(5,NULL,1,'2025-05-04 16:32:57','2025-05-04 16:34:27',167,1),(6,NULL,10,'2025-05-04 16:33:37','2025-05-04 16:34:32',0,1),(13,1,1,'2025-05-04 19:05:42','2025-05-04 19:06:13',83,NULL),(14,NULL,1,'2025-05-04 19:06:43','2025-05-04 19:07:12',91,1),(15,1,1,'2025-05-04 19:09:43','2025-05-04 19:09:45',2,NULL),(16,NULL,1,'2025-05-04 19:16:37','2025-05-04 19:16:41',30,1),(17,NULL,1,'2025-05-04 19:17:18','2025-05-04 19:17:21',11,1);
/*!40000 ALTER TABLE `computer_sessions` ENABLE KEYS */;
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
