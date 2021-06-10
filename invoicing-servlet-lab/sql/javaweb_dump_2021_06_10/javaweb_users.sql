CREATE DATABASE  IF NOT EXISTS `javaweb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `javaweb`;
-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: javaweb
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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  `fname` varchar(40) DEFAULT NULL,
  `lname` varchar(40) DEFAULT NULL,
  `roles` varchar(80) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','{bcrypt}$2a$10$OGw35lCrxCQeSh329MqWiO1Np0mimXgVBI2PlXZeqX/95rJFU2tqm','DEFAULT','ADMIN','ROLE_ADMIN',1,'2019-06-28 11:54:09','2019-06-28 11:54:09'),(2,'ivan','{bcrypt}$2a$10$CLiV6sbQvaGUQg1uCSU6t.oAqvMM/pT8YS4UYP/f0LIuj6YSIwERq','Ivan','Petrov','ROLE_USER',1,'2019-06-28 11:54:09','2019-06-28 11:54:09'),(3,'gosho','{bcrypt}$2a$10$8s0vXOwLnsnJScM9nrz/wewAh47iuObuCXdMljn/YMKYodI69Zxni','Georgi','Petrov','ROLE_USER',1,'2019-06-28 13:05:19','2019-06-28 13:05:19'),(4,'gosho2','{bcrypt}$2a$10$TptkJGQWTCCo7AXtPhzo7eN2flahmbAQJAcTQblaT8w82W41E70Zi','Georgi','Petrov','ROLE_USER',1,'2019-06-28 13:13:06','2019-06-28 13:13:06'),(5,'gosho3','{bcrypt}$2a$10$hx0LLWeY7Vp0UVpAc5.4uemutFntzmjjSKITl9Q3yZU.DlBrDbeXy','Georgi','Petrov','ROLE_USER',1,'2019-06-28 13:15:49','2019-06-28 13:15:49');
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

-- Dump completed on 2021-06-10 21:31:01
