CREATE DATABASE  IF NOT EXISTS `einvoice` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `einvoice`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: einvoice
-- ------------------------------------------------------
-- Server version	8.0.16

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
-- Table structure for table `contragents`
--

DROP TABLE IF EXISTS `contragents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contragents` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(80) COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(120) COLLATE utf8mb4_general_ci NOT NULL,
  `id_number` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `country_code` char(2) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `corporate` tinyint(1) DEFAULT '1',
  `email` varchar(80) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `iban` varchar(22) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bic` varchar(8) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_number` (`id_number`),
  UNIQUE KEY `id_number_idx` (`id_number`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contragents`
--

LOCK TABLES `contragents` WRITE;
/*!40000 ALTER TABLE `contragents` DISABLE KEYS */;
INSERT INTO `contragents` VALUES (1,'IPT Ltd.','Sofia, Slatinska bl.44','143899933','BG','+359 888354545',1,'iliev@iproduct.org','UNCR231312433243234','UNCR'),(2,'ABC Ltd.','Sofia, 1000','123456789','BG','+359 324234343',1,'office@abc.com','PRCR23423423423423423','PRCR'),(3,'Ivan Petrov','Sofia, Graf Igantiev 55, vh. A ap.16','8212234536',NULL,'+359 2345453',0,NULL,NULL,NULL),(4,'Software AD','Sofia, 1000','987654321','BG','+359 245634567',1,'office@softwaread.com',NULL,NULL);
/*!40000 ALTER TABLE `contragents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `id` int(10) unsigned NOT NULL,
  `suplier_id` int(10) unsigned NOT NULL,
  `customer_id` int(10) unsigned NOT NULL,
  `date` date DEFAULT NULL,
  `vat` decimal(4,3) DEFAULT '0.200',
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoices_supliers_idx` (`suplier_id`),
  KEY `fk_invoices_customers_idx` (`customer_id`),
  CONSTRAINT `fk_invoices_customers` FOREIGN KEY (`customer_id`) REFERENCES `contragents` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_invoices_supliers` FOREIGN KEY (`suplier_id`) REFERENCES `contragents` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,1,4,'2021-05-31',0.200,NULL),(2,1,2,'2021-05-31',0.200,NULL);
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lines`
--

DROP TABLE IF EXISTS `lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lines` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_id` int(10) unsigned NOT NULL,
  `product_id` int(10) unsigned NOT NULL,
  `quantity` decimal(8,2) NOT NULL,
  `price` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lines_products_idx` (`product_id`),
  KEY `fk_lines_invoices_idx` (`invoice_id`),
  CONSTRAINT `fk_lines_invoices` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_lines_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lines`
--

LOCK TABLES `lines` WRITE;
/*!40000 ALTER TABLE `lines` DISABLE KEYS */;
INSERT INTO `lines` VALUES (5,1,1,5.00,27.50),(6,1,4,3.00,39.40),(7,2,2,5.00,NULL),(8,2,3,3.00,NULL),(9,2,5,1.00,49.40);
/*!40000 ALTER TABLE `lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` char(5) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `category` varchar(40) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'Books',
  `description` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `price` decimal(8,2) NOT NULL,
  `is_promoted` tinyint(1) DEFAULT '0',
  `promotion_percentage` decimal(5,2) DEFAULT NULL,
  `unit` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'BK001','Thinking in Java','Programming','Good intro to Java',23.99,0,NULL,0),(2,'AC001','Wireless Mouse','Accessoaries','Logitech - high quality',28.40,0,NULL,0),(3,'AC002','Keyboard','Accessoaries','Keyboard - 101 Key',15.99,0,NULL,0),(4,'BK002','Effective Java','Books','Java in depth book. Author: Joshua Bloch',35.20,0,NULL,0),(5,'AC003','Graphic Tablet','Accessoaries','High resolution tablet for diditizin images',57.99,0,NULL,0),(8,'AC019','Monitor','Books','AlphaView',750.99,0,NULL,0),(9,'AC017','Tablet','Books','5 colors set',43.60,0,NULL,0),(10,'SV001','Mobile Internet','Books','On-demand mobile internet package',10.99,0,NULL,5),(11,'DV001','2 Band Router','Books','Supports 2.4 GHz and 5.7 GHz bands',45.50,0,NULL,0),(12,'CB001','Network Cable Cat. 6E','Books','Gbit Eternet cable UTP',0.72,0,NULL,2);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `role` tinyint(4) DEFAULT '0',
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-11  9:48:45
