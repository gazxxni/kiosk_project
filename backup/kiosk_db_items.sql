-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: kiosk_db
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `price` int NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `items_categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'아메리카노',3000,'\"img\\americano.jpg\"',1,10),(2,'바닐라라떼',3500,'\"img\\vanilla_latte.jpg\"',1,20),(3,'헤이즐넛아메리카노',3500,'\"img\\hazelnut_americano.jpg\"',1,10),(4,'카페라떼',4000,'\"img\\caffe_latte.jpg\"',1,20),(5,'카푸치노',4000,'\"img\\cappuccino.jpg\"',1,34),(6,'카페모카',4500,'\"img\\caffe_mocha.jpg\"',1,10),(7,'꿀아메리카노',3500,'\"img\\honey_americano.jpg\"',1,20),(8,'카라멜마끼아또',4500,'\"img\\caramelmacchiato.jpg\"',1,10),(9,'유자차',3000,'\"img\\citron_tea.jpg\"',2,20),(10,'레몬차',3000,'\"img\\lemon_tea.jpg\"',2,34),(11,'자몽차',3500,'\"img\\grapefruit_tea.jpg\"',2,10),(12,'아이스초코',4000,'\"img\\ice_choco.jpg\"',2,34),(13,'녹차라떼',4000,'\"img\\green_tea_latte.jpg\"',2,45),(14,'토피넛라떼',4500,'\"img\\toffeenut_latte.jpg\"',2,20),(15,'망고스무디',5000,'\"img\\mango_smoothie.jpg\"',2,10),(16,'민트프라페',5500,'\"img\\mint_frappe.jpg\"',2,34),(18,'말차스모어 쿠키',3500,'\"img\\matcha_cookie.jpg\"',3,20),(19,'마카다미아 쿠키',3000,'\"mg\\macadamia_cookie.jpg\"',3,10),(20,'딸기마카롱',3000,'\"img\\strawberry_yogurt_macaron.jpg\"',3,20),(21,'초코마카롱',3000,'\"img\\chocolate_macaron.jpg\"',3,45),(22,'무지개마카롱',3500,'\"img\\rainbow_macaron.jpg\"',3,20),(23,'초코 케이크',5500,'\"img\\chocolate_cake.jpg\"',3,45),(24,'티라미수 케이크',6000,'\"img\\tiramisu_cake.jpg\"',3,10),(25,'허니브레드',6500,'\"img\\honeybread.jpg\"',3,20);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-18 13:04:10
