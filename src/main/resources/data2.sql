CREATE DATABASE IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `test`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category`
(
    `category_id`   bigint NOT NULL AUTO_INCREMENT,
    `category_name` varchar(255) DEFAULT NULL,
    `warehouseid`   bigint       DEFAULT NULL,
    PRIMARY KEY (`category_id`),
    KEY `FK25uxkxob70vsw8mal56qxu17c` (`warehouseid`),
    CONSTRAINT `FK25uxkxob70vsw8mal56qxu17c` FOREIGN KEY (`warehouseid`) REFERENCES `warehouse` (`warehouseid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category`
    DISABLE KEYS */;
INSERT INTO `category`
VALUES (1, 'Nở sắt', NULL),
       (2, 'Bu lông', NULL),
       (3, 'Ốc vít', NULL),
       (4, 'Lưới thép bê tông', NULL);
/*!40000 ALTER TABLE `category`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_delivery`
--

DROP TABLE IF EXISTS `contact_delivery`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact_delivery`
(
    `contactid`     bigint NOT NULL AUTO_INCREMENT,
    `address`       varchar(255) DEFAULT NULL,
    `contact_name`  varchar(255) DEFAULT NULL,
    `createed_date` datetime(6)  DEFAULT NULL,
    `email`         varchar(255) DEFAULT NULL,
    `phone`         varchar(255) DEFAULT NULL,
    `created_by`    bigint       DEFAULT NULL,
    `customer_id`   bigint       DEFAULT NULL,
    `districtid`    bigint       DEFAULT NULL,
    PRIMARY KEY (`contactid`),
    KEY `FKmyjex5wdwspmtr7wi6u2naxuh` (`created_by`),
    KEY `FKf3g9xohis4ojgypla3yt9fwfx` (`customer_id`),
    KEY `FK6e7n7gkg1hj1ke2u3lxnaejmo` (`districtid`),
    CONSTRAINT `FK6e7n7gkg1hj1ke2u3lxnaejmo` FOREIGN KEY (`districtid`) REFERENCES `district` (`districtid`),
    CONSTRAINT `FKf3g9xohis4ojgypla3yt9fwfx` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
    CONSTRAINT `FKmyjex5wdwspmtr7wi6u2naxuh` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_delivery`
--

LOCK TABLES `contact_delivery` WRITE;
/*!40000 ALTER TABLE `contact_delivery`
    DISABLE KEYS */;
INSERT INTO `contact_delivery`
VALUES (1, '20 - Thái Hà', 'Duy', '2021-10-10 00:00:00.000000', 'secondcustomer@gmail.com', '4567890435', NULL, 1, 1),
       (2, '306 - Dương Quảng Hàm', 'Vững', '2021-10-10 00:00:00.000000', 'fifthcustomer@gmail.com', '0383007243', NULL,
        2, 2),
       (3, '100 - Xuân Thuỷ', 'Quỳnh', '2021-10-10 00:00:00.000000', 'sixthcustomer@gmail.com', '0348576357', NULL, 3,
        3),
       (4, '35 - Nguyễ Trãi', 'Hoà', '2021-10-10 00:00:00.000000', 'seventhcustomer@gmail.com', '0967843267', NULL, 4,
        2),
       (5, '200 - Núi Trúc', 'Dinh', '2021-10-10 00:00:00.000000', 'eighthcustomer@gmail.com', '0967890435', NULL, 5,
        2),
       (6, '35 - Tây Hồ', 'Bình', '2021-10-10 00:00:00.000000', 'ninthcustomer@gmail.com', '0984798766', NULL, 6, 2),
       (7, '55A - Chùa Bộc', 'Lan', '2021-10-10 00:00:00.000000', 'tenthcustomer@gmail.com', '038765878767', NULL, 7,
        1),
       (8, '256 - Tôn Đức Thắng', 'Tùng', '2021-10-10 00:00:00.000000', 'eleventhcustomer@gmail.com', '0976575433',
        NULL, 8, 2);
/*!40000 ALTER TABLE `contact_delivery`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `district`
(
    `districtid`    bigint NOT NULL AUTO_INCREMENT,
    `district_name` varchar(255) DEFAULT NULL,
    `district_type` varchar(255) DEFAULT NULL,
    `provinceid`    bigint       DEFAULT NULL,
    PRIMARY KEY (`districtid`),
    KEY `FK6j13cntyo84kql99wpntb7skt` (`provinceid`),
    CONSTRAINT `FK6j13cntyo84kql99wpntb7skt` FOREIGN KEY (`provinceid`) REFERENCES `province` (`provinceid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 974
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district`
    DISABLE KEYS */;
INSERT INTO `district`
VALUES (1, 'Quận Ba Đình', 'Quận', 1),
       (2, 'Quận Hoàn Kiếm', 'Quận', 1),
       (3, 'Quận Tây Hồ', 'Quận', 1),
       (4, 'Quận Long Biên', 'Quận', 1),
       (5, 'Quận Cầu Giấy', 'Quận', 1),
       (6, 'Quận Đống Đa', 'Quận', 1),
       (7, 'Quận Hai Bà Trưng', 'Quận', 1),
       (8, 'Quận Hoàng Mai', 'Quận', 1),
       (9, 'Quận Thanh Xuân', 'Quận', 1),
       (16, 'Huyện Sóc Sơn', 'Huyện', 1),
       (17, 'Huyện Đông Anh', 'Huyện', 1),
       (18, 'Huyện Gia Lâm', 'Huyện', 1),
       (19, 'Quận Nam Từ Liêm', 'Quận', 1),
       (20, 'Huyện Thanh Trì', 'Huyện', 1),
       (21, 'Quận Bắc Từ Liêm', 'Quận', 1),
       (24, 'Thành phố Hà Giang', 'Thành phố', 2),
       (26, 'Huyện Đồng Văn', 'Huyện', 2),
       (27, 'Huyện Mèo Vạc', 'Huyện', 2),
       (28, 'Huyện Yên Minh', 'Huyện', 2),
       (29, 'Huyện Quản Bạ', 'Huyện', 2),
       (30, 'Huyện Vị Xuyên', 'Huyện', 2),
       (31, 'Huyện Bắc Mê', 'Huyện', 2),
       (32, 'Huyện Hoàng Su Phì', 'Huyện', 2),
       (33, 'Huyện Xín Mần', 'Huyện', 2),
       (34, 'Huyện Bắc Quang', 'Huyện', 2),
       (35, 'Huyện Quang Bình', 'Huyện', 2),
       (40, 'Thành phố Cao Bằng', 'Thành phố', 4),
       (42, 'Huyện Bảo Lâm', 'Huyện', 4),
       (43, 'Huyện Bảo Lạc', 'Huyện', 4),
       (44, 'Huyện Thông Nông', 'Huyện', 4),
       (45, 'Huyện Hà Quảng', 'Huyện', 4),
       (46, 'Huyện Trà Lĩnh', 'Huyện', 4),
       (47, 'Huyện Trùng Khánh', 'Huyện', 4),
       (48, 'Huyện Hạ Lang', 'Huyện', 4),
       (49, 'Huyện Quảng Uyên', 'Huyện', 4),
       (50, 'Huyện Phục Hoà', 'Huyện', 4),
       (51, 'Huyện Hoà An', 'Huyện', 4),
       (52, 'Huyện Nguyên Bình', 'Huyện', 4),
       (53, 'Huyện Thạch An', 'Huyện', 4),
       (58, 'Thành Phố Bắc Kạn', 'Thành phố', 6),
       (60, 'Huyện Pác Nặm', 'Huyện', 6),
       (61, 'Huyện Ba Bể', 'Huyện', 6),
       (62, 'Huyện Ngân Sơn', 'Huyện', 6),
       (63, 'Huyện Bạch Thông', 'Huyện', 6),
       (64, 'Huyện Chợ Đồn', 'Huyện', 6),
       (65, 'Huyện Chợ Mới', 'Huyện', 6),
       (66, 'Huyện Na Rì', 'Huyện', 6),
       (70, 'Thành phố Tuyên Quang', 'Thành phố', 8),
       (71, 'Huyện Lâm Bình', 'Huyện', 8),
       (72, 'Huyện Nà Hang', 'Huyện', 8),
       (73, 'Huyện Chiêm Hóa', 'Huyện', 8),
       (74, 'Huyện Hàm Yên', 'Huyện', 8),
       (75, 'Huyện Yên Sơn', 'Huyện', 8),
       (76, 'Huyện Sơn Dương', 'Huyện', 8),
       (80, 'Thành phố Lào Cai', 'Thành phố', 10),
       (82, 'Huyện Bát Xát', 'Huyện', 10),
       (83, 'Huyện Mường Khương', 'Huyện', 10),
       (84, 'Huyện Si Ma Cai', 'Huyện', 10),
       (85, 'Huyện Bắc Hà', 'Huyện', 10),
       (86, 'Huyện Bảo Thắng', 'Huyện', 10),
       (87, 'Huyện Bảo Yên', 'Huyện', 10),
       (88, 'Huyện Sa Pa', 'Huyện', 10),
       (89, 'Huyện Văn Bàn', 'Huyện', 10),
       (94, 'Thành phố Điện Biên Phủ', 'Thành phố', 11),
       (95, 'Thị Xã Mường Lay', 'Thị xã', 11),
       (96, 'Huyện Mường Nhé', 'Huyện', 11),
       (97, 'Huyện Mường Chà', 'Huyện', 11),
       (98, 'Huyện Tủa Chùa', 'Huyện', 11),
       (99, 'Huyện Tuần Giáo', 'Huyện', 11),
       (100, 'Huyện Điện Biên', 'Huyện', 11),
       (101, 'Huyện Điện Biên Đông', 'Huyện', 11),
       (102, 'Huyện Mường Ảng', 'Huyện', 11),
       (103, 'Huyện Nậm Pồ', 'Huyện', 11),
       (105, 'Thành phố Lai Châu', 'Thành phố', 12),
       (106, 'Huyện Tam Đường', 'Huyện', 12),
       (107, 'Huyện Mường Tè', 'Huyện', 12),
       (108, 'Huyện Sìn Hồ', 'Huyện', 12),
       (109, 'Huyện Phong Thổ', 'Huyện', 12),
       (110, 'Huyện Than Uyên', 'Huyện', 12),
       (111, 'Huyện Tân Uyên', 'Huyện', 12),
       (112, 'Huyện Nậm Nhùn', 'Huyện', 12),
       (116, 'Thành phố Sơn La', 'Thành phố', 14),
       (118, 'Huyện Quỳnh Nhai', 'Huyện', 14),
       (119, 'Huyện Thuận Châu', 'Huyện', 14),
       (120, 'Huyện Mường La', 'Huyện', 14),
       (121, 'Huyện Bắc Yên', 'Huyện', 14),
       (122, 'Huyện Phù Yên', 'Huyện', 14),
       (123, 'Huyện Mộc Châu', 'Huyện', 14),
       (124, 'Huyện Yên Châu', 'Huyện', 14),
       (125, 'Huyện Mai Sơn', 'Huyện', 14),
       (126, 'Huyện Sông Mã', 'Huyện', 14),
       (127, 'Huyện Sốp Cộp', 'Huyện', 14),
       (128, 'Huyện Vân Hồ', 'Huyện', 14),
       (132, 'Thành phố Yên Bái', 'Thành phố', 15),
       (133, 'Thị xã Nghĩa Lộ', 'Thị xã', 15),
       (135, 'Huyện Lục Yên', 'Huyện', 15),
       (136, 'Huyện Văn Yên', 'Huyện', 15),
       (137, 'Huyện Mù Căng Chải', 'Huyện', 15),
       (138, 'Huyện Trấn Yên', 'Huyện', 15),
       (139, 'Huyện Trạm Tấu', 'Huyện', 15),
       (140, 'Huyện Văn Chấn', 'Huyện', 15),
       (141, 'Huyện Yên Bình', 'Huyện', 15),
       (148, 'Thành phố Hòa Bình', 'Thành phố', 17),
       (150, 'Huyện Đà Bắc', 'Huyện', 17),
       (151, 'Huyện Kỳ Sơn', 'Huyện', 17),
       (152, 'Huyện Lương Sơn', 'Huyện', 17),
       (153, 'Huyện Kim Bôi', 'Huyện', 17),
       (154, 'Huyện Cao Phong', 'Huyện', 17),
       (155, 'Huyện Tân Lạc', 'Huyện', 17),
       (156, 'Huyện Mai Châu', 'Huyện', 17),
       (157, 'Huyện Lạc Sơn', 'Huyện', 17),
       (158, 'Huyện Yên Thủy', 'Huyện', 17),
       (159, 'Huyện Lạc Thủy', 'Huyện', 17),
       (164, 'Thành phố Thái Nguyên', 'Thành phố', 19),
       (165, 'Thành phố Sông Công', 'Thành phố', 19),
       (167, 'Huyện Định Hóa', 'Huyện', 19),
       (168, 'Huyện Phú Lương', 'Huyện', 19),
       (169, 'Huyện Đồng Hỷ', 'Huyện', 19),
       (170, 'Huyện Võ Nhai', 'Huyện', 19),
       (171, 'Huyện Đại Từ', 'Huyện', 19),
       (172, 'Thị xã Phổ Yên', 'Thị xã', 19),
       (173, 'Huyện Phú Bình', 'Huyện', 19),
       (178, 'Thành phố Lạng Sơn', 'Thành phố', 20),
       (180, 'Huyện Tràng Định', 'Huyện', 20),
       (181, 'Huyện Bình Gia', 'Huyện', 20),
       (182, 'Huyện Văn Lãng', 'Huyện', 20),
       (183, 'Huyện Cao Lộc', 'Huyện', 20),
       (184, 'Huyện Văn Quan', 'Huyện', 20),
       (185, 'Huyện Bắc Sơn', 'Huyện', 20),
       (186, 'Huyện Hữu Lũng', 'Huyện', 20),
       (187, 'Huyện Chi Lăng', 'Huyện', 20),
       (188, 'Huyện Lộc Bình', 'Huyện', 20),
       (189, 'Huyện Đình Lập', 'Huyện', 20),
       (193, 'Thành phố Hạ Long', 'Thành phố', 22),
       (194, 'Thành phố Móng Cái', 'Thành phố', 22),
       (195, 'Thành phố Cẩm Phả', 'Thành phố', 22),
       (196, 'Thành phố Uông Bí', 'Thành phố', 22),
       (198, 'Huyện Bình Liêu', 'Huyện', 22),
       (199, 'Huyện Tiên Yên', 'Huyện', 22),
       (200, 'Huyện Đầm Hà', 'Huyện', 22),
       (201, 'Huyện Hải Hà', 'Huyện', 22),
       (202, 'Huyện Ba Chẽ', 'Huyện', 22),
       (203, 'Huyện Vân Đồn', 'Huyện', 22),
       (204, 'Huyện Hoành Bồ', 'Huyện', 22),
       (205, 'Thị xã Đông Triều', 'Thị xã', 22),
       (206, 'Thị xã Quảng Yên', 'Thị xã', 22),
       (207, 'Huyện Cô Tô', 'Huyện', 22),
       (213, 'Thành phố Bắc Giang', 'Thành phố', 24),
       (215, 'Huyện Yên Thế', 'Huyện', 24),
       (216, 'Huyện Tân Yên', 'Huyện', 24),
       (217, 'Huyện Lạng Giang', 'Huyện', 24),
       (218, 'Huyện Lục Nam', 'Huyện', 24),
       (219, 'Huyện Lục Ngạn', 'Huyện', 24),
       (220, 'Huyện Sơn Động', 'Huyện', 24),
       (221, 'Huyện Yên Dũng', 'Huyện', 24),
       (222, 'Huyện Việt Yên', 'Huyện', 24),
       (223, 'Huyện Hiệp Hòa', 'Huyện', 24),
       (227, 'Thành phố Việt Trì', 'Thành phố', 25),
       (228, 'Thị xã Phú Thọ', 'Thị xã', 25),
       (230, 'Huyện Đoan Hùng', 'Huyện', 25),
       (231, 'Huyện Hạ Hoà', 'Huyện', 25),
       (232, 'Huyện Thanh Ba', 'Huyện', 25),
       (233, 'Huyện Phù Ninh', 'Huyện', 25),
       (234, 'Huyện Yên Lập', 'Huyện', 25),
       (235, 'Huyện Cẩm Khê', 'Huyện', 25),
       (236, 'Huyện Tam Nông', 'Huyện', 25),
       (237, 'Huyện Lâm Thao', 'Huyện', 25),
       (238, 'Huyện Thanh Sơn', 'Huyện', 25),
       (239, 'Huyện Thanh Thuỷ', 'Huyện', 25),
       (240, 'Huyện Tân Sơn', 'Huyện', 25),
       (243, 'Thành phố Vĩnh Yên', 'Thành phố', 26),
       (244, 'Thị xã Phúc Yên', 'Thị xã', 26),
       (246, 'Huyện Lập Thạch', 'Huyện', 26),
       (247, 'Huyện Tam Dương', 'Huyện', 26),
       (248, 'Huyện Tam Đảo', 'Huyện', 26),
       (249, 'Huyện Bình Xuyên', 'Huyện', 26),
       (250, 'Huyện Mê Linh', 'Huyện', 1),
       (251, 'Huyện Yên Lạc', 'Huyện', 26),
       (252, 'Huyện Vĩnh Tường', 'Huyện', 26),
       (253, 'Huyện Sông Lô', 'Huyện', 26),
       (256, 'Thành phố Bắc Ninh', 'Thành phố', 27),
       (258, 'Huyện Yên Phong', 'Huyện', 27),
       (259, 'Huyện Quế Võ', 'Huyện', 27),
       (260, 'Huyện Tiên Du', 'Huyện', 27),
       (261, 'Thị xã Từ Sơn', 'Thị xã', 27),
       (262, 'Huyện Thuận Thành', 'Huyện', 27),
       (263, 'Huyện Gia Bình', 'Huyện', 27),
       (264, 'Huyện Lương Tài', 'Huyện', 27),
       (268, 'Quận Hà Đông', 'Quận', 1),
       (269, 'Thị xã Sơn Tây', 'Thị xã', 1),
       (271, 'Huyện Ba Vì', 'Huyện', 1),
       (272, 'Huyện Phúc Thọ', 'Huyện', 1),
       (273, 'Huyện Đan Phượng', 'Huyện', 1),
       (274, 'Huyện Hoài Đức', 'Huyện', 1),
       (275, 'Huyện Quốc Oai', 'Huyện', 1),
       (276, 'Huyện Thạch Thất', 'Huyện', 1),
       (277, 'Huyện Chương Mỹ', 'Huyện', 1),
       (278, 'Huyện Thanh Oai', 'Huyện', 1),
       (279, 'Huyện Thường Tín', 'Huyện', 1),
       (280, 'Huyện Phú Xuyên', 'Huyện', 1),
       (281, 'Huyện Ứng Hòa', 'Huyện', 1),
       (282, 'Huyện Mỹ Đức', 'Huyện', 1),
       (288, 'Thành phố Hải Dương', 'Thành phố', 30),
       (290, 'Thị xã Chí Linh', 'Thị xã', 30),
       (291, 'Huyện Nam Sách', 'Huyện', 30),
       (292, 'Huyện Kinh Môn', 'Huyện', 30),
       (293, 'Huyện Kim Thành', 'Huyện', 30),
       (294, 'Huyện Thanh Hà', 'Huyện', 30),
       (295, 'Huyện Cẩm Giàng', 'Huyện', 30),
       (296, 'Huyện Bình Giang', 'Huyện', 30),
       (297, 'Huyện Gia Lộc', 'Huyện', 30),
       (298, 'Huyện Tứ Kỳ', 'Huyện', 30),
       (299, 'Huyện Ninh Giang', 'Huyện', 30),
       (300, 'Huyện Thanh Miện', 'Huyện', 30),
       (303, 'Quận Hồng Bàng', 'Quận', 31),
       (304, 'Quận Ngô Quyền', 'Quận', 31),
       (305, 'Quận Lê Chân', 'Quận', 31),
       (306, 'Quận Hải An', 'Quận', 31),
       (307, 'Quận Kiến An', 'Quận', 31),
       (308, 'Quận Đồ Sơn', 'Quận', 31),
       (309, 'Quận Dương Kinh', 'Quận', 31),
       (311, 'Huyện Thuỷ Nguyên', 'Huyện', 31),
       (312, 'Huyện An Dương', 'Huyện', 31),
       (313, 'Huyện An Lão', 'Huyện', 31),
       (314, 'Huyện Kiến Thuỵ', 'Huyện', 31),
       (315, 'Huyện Tiên Lãng', 'Huyện', 31),
       (316, 'Huyện Vĩnh Bảo', 'Huyện', 31),
       (317, 'Huyện Cát Hải', 'Huyện', 31),
       (318, 'Huyện Bạch Long Vĩ', 'Huyện', 31),
       (323, 'Thành phố Hưng Yên', 'Thành phố', 33),
       (325, 'Huyện Văn Lâm', 'Huyện', 33),
       (326, 'Huyện Văn Giang', 'Huyện', 33),
       (327, 'Huyện Yên Mỹ', 'Huyện', 33),
       (328, 'Huyện Mỹ Hào', 'Huyện', 33),
       (329, 'Huyện Ân Thi', 'Huyện', 33),
       (330, 'Huyện Khoái Châu', 'Huyện', 33),
       (331, 'Huyện Kim Động', 'Huyện', 33),
       (332, 'Huyện Tiên Lữ', 'Huyện', 33),
       (333, 'Huyện Phù Cừ', 'Huyện', 33),
       (336, 'Thành phố Thái Bình', 'Thành phố', 34),
       (338, 'Huyện Quỳnh Phụ', 'Huyện', 34),
       (339, 'Huyện Hưng Hà', 'Huyện', 34),
       (340, 'Huyện Đông Hưng', 'Huyện', 34),
       (341, 'Huyện Thái Thụy', 'Huyện', 34),
       (342, 'Huyện Tiền Hải', 'Huyện', 34),
       (343, 'Huyện Kiến Xương', 'Huyện', 34),
       (344, 'Huyện Vũ Thư', 'Huyện', 34),
       (347, 'Thành phố Phủ Lý', 'Thành phố', 35),
       (349, 'Huyện Duy Tiên', 'Huyện', 35),
       (350, 'Huyện Kim Bảng', 'Huyện', 35),
       (351, 'Huyện Thanh Liêm', 'Huyện', 35),
       (352, 'Huyện Bình Lục', 'Huyện', 35),
       (353, 'Huyện Lý Nhân', 'Huyện', 35),
       (356, 'Thành phố Nam Định', 'Thành phố', 36),
       (358, 'Huyện Mỹ Lộc', 'Huyện', 36),
       (359, 'Huyện Vụ Bản', 'Huyện', 36),
       (360, 'Huyện Ý Yên', 'Huyện', 36),
       (361, 'Huyện Nghĩa Hưng', 'Huyện', 36),
       (362, 'Huyện Nam Trực', 'Huyện', 36),
       (363, 'Huyện Trực Ninh', 'Huyện', 36),
       (364, 'Huyện Xuân Trường', 'Huyện', 36),
       (365, 'Huyện Giao Thủy', 'Huyện', 36),
       (366, 'Huyện Hải Hậu', 'Huyện', 36),
       (369, 'Thành phố Ninh Bình', 'Thành phố', 37),
       (370, 'Thành phố Tam Điệp', 'Thành phố', 37),
       (372, 'Huyện Nho Quan', 'Huyện', 37),
       (373, 'Huyện Gia Viễn', 'Huyện', 37),
       (374, 'Huyện Hoa Lư', 'Huyện', 37),
       (375, 'Huyện Yên Khánh', 'Huyện', 37),
       (376, 'Huyện Kim Sơn', 'Huyện', 37),
       (377, 'Huyện Yên Mô', 'Huyện', 37),
       (380, 'Thành phố Thanh Hóa', 'Thành phố', 38),
       (381, 'Thị xã Bỉm Sơn', 'Thị xã', 38),
       (382, 'Thị xã Sầm Sơn', 'Thị xã', 38),
       (384, 'Huyện Mường Lát', 'Huyện', 38),
       (385, 'Huyện Quan Hóa', 'Huyện', 38),
       (386, 'Huyện Bá Thước', 'Huyện', 38),
       (387, 'Huyện Quan Sơn', 'Huyện', 38),
       (388, 'Huyện Lang Chánh', 'Huyện', 38),
       (389, 'Huyện Ngọc Lặc', 'Huyện', 38),
       (390, 'Huyện Cẩm Thủy', 'Huyện', 38),
       (391, 'Huyện Thạch Thành', 'Huyện', 38),
       (392, 'Huyện Hà Trung', 'Huyện', 38),
       (393, 'Huyện Vĩnh Lộc', 'Huyện', 38),
       (394, 'Huyện Yên Định', 'Huyện', 38),
       (395, 'Huyện Thọ Xuân', 'Huyện', 38),
       (396, 'Huyện Thường Xuân', 'Huyện', 38),
       (397, 'Huyện Triệu Sơn', 'Huyện', 38),
       (398, 'Huyện Thiệu Hóa', 'Huyện', 38),
       (399, 'Huyện Hoằng Hóa', 'Huyện', 38),
       (400, 'Huyện Hậu Lộc', 'Huyện', 38),
       (401, 'Huyện Nga Sơn', 'Huyện', 38),
       (402, 'Huyện Như Xuân', 'Huyện', 38),
       (403, 'Huyện Như Thanh', 'Huyện', 38),
       (404, 'Huyện Nông Cống', 'Huyện', 38),
       (405, 'Huyện Đông Sơn', 'Huyện', 38),
       (406, 'Huyện Quảng Xương', 'Huyện', 38),
       (407, 'Huyện Tĩnh Gia', 'Huyện', 38),
       (412, 'Thành phố Vinh', 'Thành phố', 40),
       (413, 'Thị xã Cửa Lò', 'Thị xã', 40),
       (414, 'Thị xã Thái Hoà', 'Thị xã', 40),
       (415, 'Huyện Quế Phong', 'Huyện', 40),
       (416, 'Huyện Quỳ Châu', 'Huyện', 40),
       (417, 'Huyện Kỳ Sơn', 'Huyện', 40),
       (418, 'Huyện Tương Dương', 'Huyện', 40),
       (419, 'Huyện Nghĩa Đàn', 'Huyện', 40),
       (420, 'Huyện Quỳ Hợp', 'Huyện', 40),
       (421, 'Huyện Quỳnh Lưu', 'Huyện', 40),
       (422, 'Huyện Con Cuông', 'Huyện', 40),
       (423, 'Huyện Tân Kỳ', 'Huyện', 40),
       (424, 'Huyện Anh Sơn', 'Huyện', 40),
       (425, 'Huyện Diễn Châu', 'Huyện', 40),
       (426, 'Huyện Yên Thành', 'Huyện', 40),
       (427, 'Huyện Đô Lương', 'Huyện', 40),
       (428, 'Huyện Thanh Chương', 'Huyện', 40),
       (429, 'Huyện Nghi Lộc', 'Huyện', 40),
       (430, 'Huyện Nam Đàn', 'Huyện', 40),
       (431, 'Huyện Hưng Nguyên', 'Huyện', 40),
       (432, 'Thị xã Hoàng Mai', 'Thị xã', 40),
       (436, 'Thành phố Hà Tĩnh', 'Thành phố', 42),
       (437, 'Thị xã Hồng Lĩnh', 'Thị xã', 42),
       (439, 'Huyện Hương Sơn', 'Huyện', 42),
       (440, 'Huyện Đức Thọ', 'Huyện', 42),
       (441, 'Huyện Vũ Quang', 'Huyện', 42),
       (442, 'Huyện Nghi Xuân', 'Huyện', 42),
       (443, 'Huyện Can Lộc', 'Huyện', 42),
       (444, 'Huyện Hương Khê', 'Huyện', 42),
       (445, 'Huyện Thạch Hà', 'Huyện', 42),
       (446, 'Huyện Cẩm Xuyên', 'Huyện', 42),
       (447, 'Huyện Kỳ Anh', 'Huyện', 42),
       (448, 'Huyện Lộc Hà', 'Huyện', 42),
       (449, 'Thị xã Kỳ Anh', 'Thị xã', 42),
       (450, 'Thành Phố Đồng Hới', 'Thành phố', 44),
       (452, 'Huyện Minh Hóa', 'Huyện', 44),
       (453, 'Huyện Tuyên Hóa', 'Huyện', 44),
       (454, 'Huyện Quảng Trạch', 'Thị xã', 44),
       (455, 'Huyện Bố Trạch', 'Huyện', 44),
       (456, 'Huyện Quảng Ninh', 'Huyện', 44),
       (457, 'Huyện Lệ Thủy', 'Huyện', 44),
       (458, 'Thị xã Ba Đồn', 'Huyện', 44),
       (461, 'Thành phố Đông Hà', 'Thành phố', 45),
       (462, 'Thị xã Quảng Trị', 'Thị xã', 45),
       (464, 'Huyện Vĩnh Linh', 'Huyện', 45),
       (465, 'Huyện Hướng Hóa', 'Huyện', 45),
       (466, 'Huyện Gio Linh', 'Huyện', 45),
       (467, 'Huyện Đa Krông', 'Huyện', 45),
       (468, 'Huyện Cam Lộ', 'Huyện', 45),
       (469, 'Huyện Triệu Phong', 'Huyện', 45),
       (470, 'Huyện Hải Lăng', 'Huyện', 45),
       (471, 'Huyện Cồn Cỏ', 'Huyện', 45),
       (474, 'Thành phố Huế', 'Thành phố', 46),
       (476, 'Huyện Phong Điền', 'Huyện', 46),
       (477, 'Huyện Quảng Điền', 'Huyện', 46),
       (478, 'Huyện Phú Vang', 'Huyện', 46),
       (479, 'Thị xã Hương Thủy', 'Thị xã', 46),
       (480, 'Thị xã Hương Trà', 'Thị xã', 46),
       (481, 'Huyện A Lưới', 'Huyện', 46),
       (482, 'Huyện Phú Lộc', 'Huyện', 46),
       (483, 'Huyện Nam Đông', 'Huyện', 46),
       (490, 'Quận Liên Chiểu', 'Quận', 48),
       (491, 'Quận Thanh Khê', 'Quận', 48),
       (492, 'Quận Hải Châu', 'Quận', 48),
       (493, 'Quận Sơn Trà', 'Quận', 48),
       (494, 'Quận Ngũ Hành Sơn', 'Quận', 48),
       (495, 'Quận Cẩm Lệ', 'Quận', 48),
       (497, 'Huyện Hòa Vang', 'Huyện', 48),
       (498, 'Huyện Hoàng Sa', 'Huyện', 48),
       (502, 'Thành phố Tam Kỳ', 'Thành phố', 49),
       (503, 'Thành phố Hội An', 'Thành phố', 49),
       (504, 'Huyện Tây Giang', 'Huyện', 49),
       (505, 'Huyện Đông Giang', 'Huyện', 49),
       (506, 'Huyện Đại Lộc', 'Huyện', 49),
       (507, 'Thị xã Điện Bàn', 'Thị xã', 49),
       (508, 'Huyện Duy Xuyên', 'Huyện', 49),
       (509, 'Huyện Quế Sơn', 'Huyện', 49),
       (510, 'Huyện Nam Giang', 'Huyện', 49),
       (511, 'Huyện Phước Sơn', 'Huyện', 49),
       (512, 'Huyện Hiệp Đức', 'Huyện', 49),
       (513, 'Huyện Thăng Bình', 'Huyện', 49),
       (514, 'Huyện Tiên Phước', 'Huyện', 49),
       (515, 'Huyện Bắc Trà My', 'Huyện', 49),
       (516, 'Huyện Nam Trà My', 'Huyện', 49),
       (517, 'Huyện Núi Thành', 'Huyện', 49),
       (518, 'Huyện Phú Ninh', 'Huyện', 49),
       (519, 'Huyện Nông Sơn', 'Huyện', 49),
       (522, 'Thành phố Quảng Ngãi', 'Thành phố', 51),
       (524, 'Huyện Bình Sơn', 'Huyện', 51),
       (525, 'Huyện Trà Bồng', 'Huyện', 51),
       (526, 'Huyện Tây Trà', 'Huyện', 51),
       (527, 'Huyện Sơn Tịnh', 'Huyện', 51),
       (528, 'Huyện Tư Nghĩa', 'Huyện', 51),
       (529, 'Huyện Sơn Hà', 'Huyện', 51),
       (530, 'Huyện Sơn Tây', 'Huyện', 51),
       (531, 'Huyện Minh Long', 'Huyện', 51),
       (532, 'Huyện Nghĩa Hành', 'Huyện', 51),
       (533, 'Huyện Mộ Đức', 'Huyện', 51),
       (534, 'Huyện Đức Phổ', 'Huyện', 51),
       (535, 'Huyện Ba Tơ', 'Huyện', 51),
       (536, 'Huyện Lý Sơn', 'Huyện', 51),
       (540, 'Thành phố Qui Nhơn', 'Thành phố', 52),
       (542, 'Huyện An Lão', 'Huyện', 52),
       (543, 'Huyện Hoài Nhơn', 'Huyện', 52),
       (544, 'Huyện Hoài Ân', 'Huyện', 52),
       (545, 'Huyện Phù Mỹ', 'Huyện', 52),
       (546, 'Huyện Vĩnh Thạnh', 'Huyện', 52),
       (547, 'Huyện Tây Sơn', 'Huyện', 52),
       (548, 'Huyện Phù Cát', 'Huyện', 52),
       (549, 'Thị xã An Nhơn', 'Thị xã', 52),
       (550, 'Huyện Tuy Phước', 'Huyện', 52),
       (551, 'Huyện Vân Canh', 'Huyện', 52),
       (555, 'Thành phố Tuy Hoà', 'Thành phố', 54),
       (557, 'Thị xã Sông Cầu', 'Thị xã', 54),
       (558, 'Huyện Đồng Xuân', 'Huyện', 54),
       (559, 'Huyện Tuy An', 'Huyện', 54),
       (560, 'Huyện Sơn Hòa', 'Huyện', 54),
       (561, 'Huyện Sông Hinh', 'Huyện', 54),
       (562, 'Huyện Tây Hoà', 'Huyện', 54),
       (563, 'Huyện Phú Hoà', 'Huyện', 54),
       (564, 'Huyện Đông Hòa', 'Huyện', 54),
       (568, 'Thành phố Nha Trang', 'Thành phố', 56),
       (569, 'Thành phố Cam Ranh', 'Thành phố', 56),
       (570, 'Huyện Cam Lâm', 'Huyện', 56),
       (571, 'Huyện Vạn Ninh', 'Huyện', 56),
       (572, 'Thị xã Ninh Hòa', 'Thị xã', 56),
       (573, 'Huyện Khánh Vĩnh', 'Huyện', 56),
       (574, 'Huyện Diên Khánh', 'Huyện', 56),
       (575, 'Huyện Khánh Sơn', 'Huyện', 56),
       (576, 'Huyện Trường Sa', 'Huyện', 56),
       (582, 'Thành phố Phan Rang-Tháp Chàm', 'Thành phố', 58),
       (584, 'Huyện Bác Ái', 'Huyện', 58),
       (585, 'Huyện Ninh Sơn', 'Huyện', 58),
       (586, 'Huyện Ninh Hải', 'Huyện', 58),
       (587, 'Huyện Ninh Phước', 'Huyện', 58),
       (588, 'Huyện Thuận Bắc', 'Huyện', 58),
       (589, 'Huyện Thuận Nam', 'Huyện', 58),
       (593, 'Thành phố Phan Thiết', 'Thành phố', 60),
       (594, 'Thị xã La Gi', 'Thị xã', 60),
       (595, 'Huyện Tuy Phong', 'Huyện', 60),
       (596, 'Huyện Bắc Bình', 'Huyện', 60),
       (597, 'Huyện Hàm Thuận Bắc', 'Huyện', 60),
       (598, 'Huyện Hàm Thuận Nam', 'Huyện', 60),
       (599, 'Huyện Tánh Linh', 'Huyện', 60),
       (600, 'Huyện Đức Linh', 'Huyện', 60),
       (601, 'Huyện Hàm Tân', 'Huyện', 60),
       (602, 'Huyện Phú Quí', 'Huyện', 60),
       (608, 'Thành phố Kon Tum', 'Thành phố', 62),
       (610, 'Huyện Đắk Glei', 'Huyện', 62),
       (611, 'Huyện Ngọc Hồi', 'Huyện', 62),
       (612, 'Huyện Đắk Tô', 'Huyện', 62),
       (613, 'Huyện Kon Plông', 'Huyện', 62),
       (614, 'Huyện Kon Rẫy', 'Huyện', 62),
       (615, 'Huyện Đắk Hà', 'Huyện', 62),
       (616, 'Huyện Sa Thầy', 'Huyện', 62),
       (617, 'Huyện Tu Mơ Rông', 'Huyện', 62),
       (618, 'Huyện Ia H\' Drai', 'Huyện', 62),
       (622, 'Thành phố Pleiku', 'Thành phố', 64),
       (623, 'Thị xã An Khê', 'Thị xã', 64),
       (624, 'Thị xã Ayun Pa', 'Thị xã', 64),
       (625, 'Huyện KBang', 'Huyện', 64),
       (626, 'Huyện Đăk Đoa', 'Huyện', 64),
       (627, 'Huyện Chư Păh', 'Huyện', 64),
       (628, 'Huyện Ia Grai', 'Huyện', 64),
       (629, 'Huyện Mang Yang', 'Huyện', 64),
       (630, 'Huyện Kông Chro', 'Huyện', 64),
       (631, 'Huyện Đức Cơ', 'Huyện', 64),
       (632, 'Huyện Chư Prông', 'Huyện', 64),
       (633, 'Huyện Chư Sê', 'Huyện', 64),
       (634, 'Huyện Đăk Pơ', 'Huyện', 64),
       (635, 'Huyện Ia Pa', 'Huyện', 64),
       (637, 'Huyện Krông Pa', 'Huyện', 64),
       (638, 'Huyện Phú Thiện', 'Huyện', 64),
       (639, 'Huyện Chư Pưh', 'Huyện', 64),
       (643, 'Thành phố Buôn Ma Thuột', 'Thành phố', 66),
       (644, 'Thị Xã Buôn Hồ', 'Thị xã', 66),
       (645, 'Huyện Ea H\'leo', 'Huyện', 66),
       (646, 'Huyện Ea Súp', 'Huyện', 66),
       (647, 'Huyện Buôn Đôn', 'Huyện', 66),
       (648, 'Huyện Cư M\'gar', 'Huyện', 66),
       (649, 'Huyện Krông Búk', 'Huyện', 66),
       (650, 'Huyện Krông Năng', 'Huyện', 66),
       (651, 'Huyện Ea Kar', 'Huyện', 66),
       (652, 'Huyện M\'Đrắk', 'Huyện', 66),
       (653, 'Huyện Krông Bông', 'Huyện', 66),
       (654, 'Huyện Krông Pắc', 'Huyện', 66),
       (655, 'Huyện Krông A Na', 'Huyện', 66),
       (656, 'Huyện Lắk', 'Huyện', 66),
       (657, 'Huyện Cư Kuin', 'Huyện', 66),
       (660, 'Thị xã Gia Nghĩa', 'Thị xã', 67),
       (661, 'Huyện Đăk Glong', 'Huyện', 67),
       (662, 'Huyện Cư Jút', 'Huyện', 67),
       (663, 'Huyện Đắk Mil', 'Huyện', 67),
       (664, 'Huyện Krông Nô', 'Huyện', 67),
       (665, 'Huyện Đắk Song', 'Huyện', 67),
       (666, 'Huyện Đắk R\'Lấp', 'Huyện', 67),
       (667, 'Huyện Tuy Đức', 'Huyện', 67),
       (672, 'Thành phố Đà Lạt', 'Thành phố', 68),
       (673, 'Thành phố Bảo Lộc', 'Thành phố', 68),
       (674, 'Huyện Đam Rông', 'Huyện', 68),
       (675, 'Huyện Lạc Dương', 'Huyện', 68),
       (676, 'Huyện Lâm Hà', 'Huyện', 68),
       (677, 'Huyện Đơn Dương', 'Huyện', 68),
       (678, 'Huyện Đức Trọng', 'Huyện', 68),
       (679, 'Huyện Di Linh', 'Huyện', 68),
       (680, 'Huyện Bảo Lâm', 'Huyện', 68),
       (681, 'Huyện Đạ Huoai', 'Huyện', 68),
       (682, 'Huyện Đạ Tẻh', 'Huyện', 68),
       (683, 'Huyện Cát Tiên', 'Huyện', 68),
       (688, 'Thị xã Phước Long', 'Thị xã', 70),
       (689, 'Thị xã Đồng Xoài', 'Thị xã', 70),
       (690, 'Thị xã Bình Long', 'Thị xã', 70),
       (691, 'Huyện Bù Gia Mập', 'Huyện', 70),
       (692, 'Huyện Lộc Ninh', 'Huyện', 70),
       (693, 'Huyện Bù Đốp', 'Huyện', 70),
       (694, 'Huyện Hớn Quản', 'Huyện', 70),
       (695, 'Huyện Đồng Phú', 'Huyện', 70),
       (696, 'Huyện Bù Đăng', 'Huyện', 70),
       (697, 'Huyện Chơn Thành', 'Huyện', 70),
       (698, 'Huyện Phú Riềng', 'Huyện', 70),
       (703, 'Thành phố Tây Ninh', 'Thành phố', 72),
       (705, 'Huyện Tân Biên', 'Huyện', 72),
       (706, 'Huyện Tân Châu', 'Huyện', 72),
       (707, 'Huyện Dương Minh Châu', 'Huyện', 72),
       (708, 'Huyện Châu Thành', 'Huyện', 72),
       (709, 'Huyện Hòa Thành', 'Huyện', 72),
       (710, 'Huyện Gò Dầu', 'Huyện', 72),
       (711, 'Huyện Bến Cầu', 'Huyện', 72),
       (712, 'Huyện Trảng Bàng', 'Huyện', 72),
       (718, 'Thành phố Thủ Dầu Một', 'Thành phố', 74),
       (719, 'Huyện Bàu Bàng', 'Huyện', 74),
       (720, 'Huyện Dầu Tiếng', 'Huyện', 74),
       (721, 'Thị xã Bến Cát', 'Thị xã', 74),
       (722, 'Huyện Phú Giáo', 'Huyện', 74),
       (723, 'Thị xã Tân Uyên', 'Thị xã', 74),
       (724, 'Thị xã Dĩ An', 'Thị xã', 74),
       (725, 'Thị xã Thuận An', 'Thị xã', 74),
       (726, 'Huyện Bắc Tân Uyên', 'Huyện', 74),
       (731, 'Thành phố Biên Hòa', 'Thành phố', 75),
       (732, 'Thị xã Long Khánh', 'Thị xã', 75),
       (734, 'Huyện Tân Phú', 'Huyện', 75),
       (735, 'Huyện Vĩnh Cửu', 'Huyện', 75),
       (736, 'Huyện Định Quán', 'Huyện', 75),
       (737, 'Huyện Trảng Bom', 'Huyện', 75),
       (738, 'Huyện Thống Nhất', 'Huyện', 75),
       (739, 'Huyện Cẩm Mỹ', 'Huyện', 75),
       (740, 'Huyện Long Thành', 'Huyện', 75),
       (741, 'Huyện Xuân Lộc', 'Huyện', 75),
       (742, 'Huyện Nhơn Trạch', 'Huyện', 75),
       (747, 'Thành phố Vũng Tàu', 'Thành phố', 77),
       (748, 'Thành phố Bà Rịa', 'Thành phố', 77),
       (750, 'Huyện Châu Đức', 'Huyện', 77),
       (751, 'Huyện Xuyên Mộc', 'Huyện', 77),
       (752, 'Huyện Long Điền', 'Huyện', 77),
       (753, 'Huyện Đất Đỏ', 'Huyện', 77),
       (754, 'Huyện Tân Thành', 'Huyện', 77),
       (755, 'Huyện Côn Đảo', 'Huyện', 77),
       (760, 'Quận 1', 'Quận', 79),
       (761, 'Quận 12', 'Quận', 79),
       (762, 'Quận Thủ Đức', 'Quận', 79),
       (763, 'Quận 9', 'Quận', 79),
       (764, 'Quận Gò Vấp', 'Quận', 79),
       (765, 'Quận Bình Thạnh', 'Quận', 79),
       (766, 'Quận Tân Bình', 'Quận', 79),
       (767, 'Quận Tân Phú', 'Quận', 79),
       (768, 'Quận Phú Nhuận', 'Quận', 79),
       (769, 'Quận 2', 'Quận', 79),
       (770, 'Quận 3', 'Quận', 79),
       (771, 'Quận 10', 'Quận', 79),
       (772, 'Quận 11', 'Quận', 79),
       (773, 'Quận 4', 'Quận', 79),
       (774, 'Quận 5', 'Quận', 79),
       (775, 'Quận 6', 'Quận', 79),
       (776, 'Quận 8', 'Quận', 79),
       (777, 'Quận Bình Tân', 'Quận', 79),
       (778, 'Quận 7', 'Quận', 79),
       (783, 'Huyện Củ Chi', 'Huyện', 79),
       (784, 'Huyện Hóc Môn', 'Huyện', 79),
       (785, 'Huyện Bình Chánh', 'Huyện', 79),
       (786, 'Huyện Nhà Bè', 'Huyện', 79),
       (787, 'Huyện Cần Giờ', 'Huyện', 79),
       (794, 'Thành phố Tân An', 'Thành phố', 80),
       (795, 'Thị xã Kiến Tường', 'Thị xã', 80),
       (796, 'Huyện Tân Hưng', 'Huyện', 80),
       (797, 'Huyện Vĩnh Hưng', 'Huyện', 80),
       (798, 'Huyện Mộc Hóa', 'Huyện', 80),
       (799, 'Huyện Tân Thạnh', 'Huyện', 80),
       (800, 'Huyện Thạnh Hóa', 'Huyện', 80),
       (801, 'Huyện Đức Huệ', 'Huyện', 80),
       (802, 'Huyện Đức Hòa', 'Huyện', 80),
       (803, 'Huyện Bến Lức', 'Huyện', 80),
       (804, 'Huyện Thủ Thừa', 'Huyện', 80),
       (805, 'Huyện Tân Trụ', 'Huyện', 80),
       (806, 'Huyện Cần Đước', 'Huyện', 80),
       (807, 'Huyện Cần Giuộc', 'Huyện', 80),
       (808, 'Huyện Châu Thành', 'Huyện', 80),
       (815, 'Thành phố Mỹ Tho', 'Thành phố', 82),
       (816, 'Thị xã Gò Công', 'Thị xã', 82),
       (817, 'Thị xã Cai Lậy', 'Huyện', 82),
       (818, 'Huyện Tân Phước', 'Huyện', 82),
       (819, 'Huyện Cái Bè', 'Huyện', 82),
       (820, 'Huyện Cai Lậy', 'Thị xã', 82),
       (821, 'Huyện Châu Thành', 'Huyện', 82),
       (822, 'Huyện Chợ Gạo', 'Huyện', 82),
       (823, 'Huyện Gò Công Tây', 'Huyện', 82),
       (824, 'Huyện Gò Công Đông', 'Huyện', 82),
       (825, 'Huyện Tân Phú Đông', 'Huyện', 82),
       (829, 'Thành phố Bến Tre', 'Thành phố', 83),
       (831, 'Huyện Châu Thành', 'Huyện', 83),
       (832, 'Huyện Chợ Lách', 'Huyện', 83),
       (833, 'Huyện Mỏ Cày Nam', 'Huyện', 83),
       (834, 'Huyện Giồng Trôm', 'Huyện', 83),
       (835, 'Huyện Bình Đại', 'Huyện', 83),
       (836, 'Huyện Ba Tri', 'Huyện', 83),
       (837, 'Huyện Thạnh Phú', 'Huyện', 83),
       (838, 'Huyện Mỏ Cày Bắc', 'Huyện', 83),
       (842, 'Thành phố Trà Vinh', 'Thành phố', 84),
       (844, 'Huyện Càng Long', 'Huyện', 84),
       (845, 'Huyện Cầu Kè', 'Huyện', 84),
       (846, 'Huyện Tiểu Cần', 'Huyện', 84),
       (847, 'Huyện Châu Thành', 'Huyện', 84),
       (848, 'Huyện Cầu Ngang', 'Huyện', 84),
       (849, 'Huyện Trà Cú', 'Huyện', 84),
       (850, 'Huyện Duyên Hải', 'Huyện', 84),
       (851, 'Thị xã Duyên Hải', 'Thị xã', 84),
       (855, 'Thành phố Vĩnh Long', 'Thành phố', 86),
       (857, 'Huyện Long Hồ', 'Huyện', 86),
       (858, 'Huyện Mang Thít', 'Huyện', 86),
       (859, 'Huyện  Vũng Liêm', 'Huyện', 86),
       (860, 'Huyện Tam Bình', 'Huyện', 86),
       (861, 'Thị xã Bình Minh', 'Thị xã', 86),
       (862, 'Huyện Trà Ôn', 'Huyện', 86),
       (863, 'Huyện Bình Tân', 'Huyện', 86),
       (866, 'Thành phố Cao Lãnh', 'Thành phố', 87),
       (867, 'Thành phố Sa Đéc', 'Thành phố', 87),
       (868, 'Thị xã Hồng Ngự', 'Thị xã', 87),
       (869, 'Huyện Tân Hồng', 'Huyện', 87),
       (870, 'Huyện Hồng Ngự', 'Huyện', 87),
       (871, 'Huyện Tam Nông', 'Huyện', 87),
       (872, 'Huyện Tháp Mười', 'Huyện', 87),
       (873, 'Huyện Cao Lãnh', 'Huyện', 87),
       (874, 'Huyện Thanh Bình', 'Huyện', 87),
       (875, 'Huyện Lấp Vò', 'Huyện', 87),
       (876, 'Huyện Lai Vung', 'Huyện', 87),
       (877, 'Huyện Châu Thành', 'Huyện', 87),
       (883, 'Thành phố Long Xuyên', 'Thành phố', 89),
       (884, 'Thành phố Châu Đốc', 'Thành phố', 89),
       (886, 'Huyện An Phú', 'Huyện', 89),
       (887, 'Thị xã Tân Châu', 'Thị xã', 89),
       (888, 'Huyện Phú Tân', 'Huyện', 89),
       (889, 'Huyện Châu Phú', 'Huyện', 89),
       (890, 'Huyện Tịnh Biên', 'Huyện', 89),
       (891, 'Huyện Tri Tôn', 'Huyện', 89),
       (892, 'Huyện Châu Thành', 'Huyện', 89),
       (893, 'Huyện Chợ Mới', 'Huyện', 89),
       (894, 'Huyện Thoại Sơn', 'Huyện', 89),
       (899, 'Thành phố Rạch Giá', 'Thành phố', 91),
       (900, 'Thị xã Hà Tiên', 'Thị xã', 91),
       (902, 'Huyện Kiên Lương', 'Huyện', 91),
       (903, 'Huyện Hòn Đất', 'Huyện', 91),
       (904, 'Huyện Tân Hiệp', 'Huyện', 91),
       (905, 'Huyện Châu Thành', 'Huyện', 91),
       (906, 'Huyện Giồng Riềng', 'Huyện', 91),
       (907, 'Huyện Gò Quao', 'Huyện', 91),
       (908, 'Huyện An Biên', 'Huyện', 91),
       (909, 'Huyện An Minh', 'Huyện', 91),
       (910, 'Huyện Vĩnh Thuận', 'Huyện', 91),
       (911, 'Huyện Phú Quốc', 'Huyện', 91),
       (912, 'Huyện Kiên Hải', 'Huyện', 91),
       (913, 'Huyện U Minh Thượng', 'Huyện', 91),
       (914, 'Huyện Giang Thành', 'Huyện', 91),
       (916, 'Quận Ninh Kiều', 'Quận', 92),
       (917, 'Quận Ô Môn', 'Quận', 92),
       (918, 'Quận Bình Thuỷ', 'Quận', 92),
       (919, 'Quận Cái Răng', 'Quận', 92),
       (923, 'Quận Thốt Nốt', 'Quận', 92),
       (924, 'Huyện Vĩnh Thạnh', 'Huyện', 92),
       (925, 'Huyện Cờ Đỏ', 'Huyện', 92),
       (926, 'Huyện Phong Điền', 'Huyện', 92),
       (927, 'Huyện Thới Lai', 'Huyện', 92),
       (930, 'Thành phố Vị Thanh', 'Thành phố', 93),
       (931, 'Thị xã Ngã Bảy', 'Thị xã', 93),
       (932, 'Huyện Châu Thành A', 'Huyện', 93),
       (933, 'Huyện Châu Thành', 'Huyện', 93),
       (934, 'Huyện Phụng Hiệp', 'Huyện', 93),
       (935, 'Huyện Vị Thuỷ', 'Huyện', 93),
       (936, 'Huyện Long Mỹ', 'Huyện', 93),
       (937, 'Thị xã Long Mỹ', 'Thị xã', 93),
       (941, 'Thành phố Sóc Trăng', 'Thành phố', 94),
       (942, 'Huyện Châu Thành', 'Huyện', 94),
       (943, 'Huyện Kế Sách', 'Huyện', 94),
       (944, 'Huyện Mỹ Tú', 'Huyện', 94),
       (945, 'Huyện Cù Lao Dung', 'Huyện', 94),
       (946, 'Huyện Long Phú', 'Huyện', 94),
       (947, 'Huyện Mỹ Xuyên', 'Huyện', 94),
       (948, 'Thị xã Ngã Năm', 'Thị xã', 94),
       (949, 'Huyện Thạnh Trị', 'Huyện', 94),
       (950, 'Thị xã Vĩnh Châu', 'Thị xã', 94),
       (951, 'Huyện Trần Đề', 'Huyện', 94),
       (954, 'Thành phố Bạc Liêu', 'Thành phố', 95),
       (956, 'Huyện Hồng Dân', 'Huyện', 95),
       (957, 'Huyện Phước Long', 'Huyện', 95),
       (958, 'Huyện Vĩnh Lợi', 'Huyện', 95),
       (959, 'Thị xã Giá Rai', 'Thị xã', 95),
       (960, 'Huyện Đông Hải', 'Huyện', 95),
       (961, 'Huyện Hoà Bình', 'Huyện', 95),
       (964, 'Thành phố Cà Mau', 'Thành phố', 96),
       (966, 'Huyện U Minh', 'Huyện', 96),
       (967, 'Huyện Thới Bình', 'Huyện', 96),
       (968, 'Huyện Trần Văn Thời', 'Huyện', 96),
       (969, 'Huyện Cái Nước', 'Huyện', 96),
       (970, 'Huyện Đầm Dơi', 'Huyện', 96),
       (971, 'Huyện Năm Căn', 'Huyện', 96),
       (972, 'Huyện Phú Tân', 'Huyện', 96),
       (973, 'Huyện Ngọc Hiển', 'Huyện', 96);
/*!40000 ALTER TABLE `district`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_verification`
--

DROP TABLE IF EXISTS `email_verification`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_verification`
(
    `email_verificationid` bigint NOT NULL AUTO_INCREMENT,
    `expiry_date`          datetime(6)  DEFAULT NULL,
    `verification_token`   varchar(255) DEFAULT NULL,
    `userid`               bigint       DEFAULT NULL,
    PRIMARY KEY (`email_verificationid`),
    KEY `FKjlf6fo0yqilxilidh3ce0tyn2` (`userid`),
    CONSTRAINT `FKjlf6fo0yqilxilidh3ce0tyn2` FOREIGN KEY (`userid`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_verification`
--

LOCK TABLES `email_verification` WRITE;
/*!40000 ALTER TABLE `email_verification`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `email_verification`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_file_history`
--

DROP TABLE IF EXISTS `import_file_history`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `import_file_history`
(
    `import_id`    bigint NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6)  DEFAULT NULL,
    `file`         tinyblob,
    `status`       varchar(255) DEFAULT NULL,
    `created_by`   bigint       DEFAULT NULL,
    PRIMARY KEY (`import_id`),
    KEY `FKiy4sl5mfhwpbswtn1n9ndbpog` (`created_by`),
    CONSTRAINT `FKiy4sl5mfhwpbswtn1n9ndbpog` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_file_history`
--

LOCK TABLES `import_file_history` WRITE;
/*!40000 ALTER TABLE `import_file_history`
    DISABLE KEYS */;
INSERT INTO `import_file_history`
VALUES (1, '2021-06-06 00:00:00.000000', NULL, NULL, NULL),
       (2, '2021-03-03 00:00:00.000000', NULL, NULL, NULL),
       (3, '2021-05-05 00:00:00.000000', NULL, NULL, NULL),
       (4, '2021-10-10 00:00:00.000000', NULL, NULL, NULL);
/*!40000 ALTER TABLE `import_file_history`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inv_product_status`
--

DROP TABLE IF EXISTS `inv_product_status`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inv_product_status`
(
    `status_id`               bigint NOT NULL AUTO_INCREMENT,
    `prod_status_description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`status_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inv_product_status`
--

LOCK TABLES `inv_product_status` WRITE;
/*!40000 ALTER TABLE `inv_product_status`
    DISABLE KEYS */;
INSERT INTO `inv_product_status`
VALUES (1, 'còn hàng'),
       (2, 'hết hàng');
/*!40000 ALTER TABLE `inv_product_status`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory`
(
    `inventory_id`       bigint NOT NULL AUTO_INCREMENT,
    `created_date`       datetime(6)  DEFAULT NULL,
    `current_quantity`   double NOT NULL,
    `date_check`         datetime(6)  DEFAULT NULL,
    `description`        varchar(255) DEFAULT NULL,
    `is_active`          bit(1)       DEFAULT NULL,
    `last_modified_date` datetime(6)  DEFAULT NULL,
    `person_check`       bigint       DEFAULT NULL,
    `shortage_quantity`  double NOT NULL,
    `created_by`         bigint       DEFAULT NULL,
    `last_modified_by`   bigint       DEFAULT NULL,
    `product_id`         bigint       DEFAULT NULL,
    `status_id`          bigint       DEFAULT NULL,
    `warehouseid`        bigint       DEFAULT NULL,
    PRIMARY KEY (`inventory_id`),
    KEY `FK9mycklahbko02n4dkj5rckj3q` (`created_by`),
    KEY `FKatl9407yxix92u0aq41qvi79k` (`last_modified_by`),
    KEY `FKp7gj4l80fx8v0uap3b2crjwp5` (`product_id`),
    KEY `FK7prh388nge62pysb43bakbuh3` (`status_id`),
    KEY `FK9gmsmkoifvmacalmmjko4yiub` (`warehouseid`),
    CONSTRAINT `FK7prh388nge62pysb43bakbuh3` FOREIGN KEY (`status_id`) REFERENCES `inv_product_status` (`status_id`),
    CONSTRAINT `FK9gmsmkoifvmacalmmjko4yiub` FOREIGN KEY (`warehouseid`) REFERENCES `warehouse` (`warehouseid`),
    CONSTRAINT `FK9mycklahbko02n4dkj5rckj3q` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKatl9407yxix92u0aq41qvi79k` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKp7gj4l80fx8v0uap3b2crjwp5` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory`
    DISABLE KEYS */;
INSERT INTO `inventory`
VALUES (1, '2021-09-09 00:00:00.000000', 100, '2021-09-10 00:00:00.000000',
        'doanh nghiệp phải chịu thêm chi phí lưu kho, bảo hiểm lưu kho và chi phí vận chuyển. Hơn nữa, nếu các mặt hàng bị lỗi thời, hư hỏng, bị đánh cắp hay giảm giá trị',
        _binary '', '2021-10-10 00:00:00.000000', NULL, 30, NULL, NULL, 1, 1, 1),
       (8, '2021-01-01 00:00:00.000000', 2000, '2020-10-10 00:00:00.000000',
        'Kế toán kiểm kê phụ trách công việc xác định số lượng và giá trị của hàng hóa trong kho', _binary '\0',
        '2021-10-10 00:00:00.000000', NULL, 100, NULL, NULL, 3, 1, 2),
       (9, '2021-04-04 00:00:00.000000', 4000, '2021-04-03 00:00:00.000000',
        'Nếu như số lượng hàng hóa trong kho quá lớn có thể ảnh hưởng đến dòng tiền của doanh nghiệp. Khi đó, doanh nghiệp phải chịu thêm chi phí lưu kho,',
        _binary '', '2021-10-10 00:00:00.000000', NULL, 50, NULL, NULL, 2, 2, 3),
       (10, '2021-02-02 00:00:00.000000', 5000, '2021-02-01 00:00:00.000000',
        'ảo hiểm lưu kho và chi phí vận chuyển. Hơn nữa, nếu các mặt hàng bị lỗi thời, hư hỏng, bị đánh cắp hay giảm giá trị, các doanh nghiệp có thể sẽ bị tổn thất tài chính',
        _binary '', '2021-10-10 00:00:00.000000', NULL, 100, NULL, NULL, 6, 1, 2),
       (11, '2021-02-20 00:00:00.000000', 1000, '2021-02-19 00:00:00.000000',
        'Nhưng nếu số lượng hàng dự trữ quá ít, doanh nghiệp sẽ không có đủ hàng hóa để đáp ứng nhu cầu của khách hàng',
        _binary '\0', '2021-10-10 00:00:00.000000', NULL, 200, NULL, NULL, 4, 2, 1);
/*!40000 ALTER TABLE `inventory`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice`
(
    `invoice_id`         bigint NOT NULL AUTO_INCREMENT,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `time_of_payment`    time        DEFAULT NULL,
    `total_amount`       double      DEFAULT NULL,
    `total_amount_paid`  double      DEFAULT NULL,
    `last_modified_by`   bigint      DEFAULT NULL,
    `orderid`            bigint      DEFAULT NULL,
    PRIMARY KEY (`invoice_id`),
    KEY `FK7a23hhf4kn8fyh8pbyt7md8qs` (`last_modified_by`),
    CONSTRAINT `FK7a23hhf4kn8fyh8pbyt7md8qs` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice`
    DISABLE KEYS */;
INSERT INTO `invoice`
VALUES (2, NULL, '03:23:00', 3000045600, 0, NULL, 1),
       (3, NULL, '14:13:00', 1007000000, 500000, NULL, 2),
       (4, NULL, '07:00:00', 457388890, 100000, NULL, 3),
       (5, NULL, '10:50:00', 54356256600, 0, NULL, 4);
/*!40000 ALTER TABLE `invoice`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material`
(
    `materialid`               bigint NOT NULL AUTO_INCREMENT,
    `material_name`            varchar(255) DEFAULT NULL,
    `created_date`             datetime(6)  DEFAULT NULL,
    `last_modified_date`       datetime(6)  DEFAULT NULL,
    `quantity_unit_of_measure` varchar(255) DEFAULT NULL,
    `created_by`               bigint       DEFAULT NULL,
    `last_modified_by`         bigint       DEFAULT NULL,
    `warehouseid`              bigint       DEFAULT NULL,
    PRIMARY KEY (`materialid`),
    KEY `FKog8qny4p515njglw3x15stsf5` (`created_by`),
    KEY `FKje1bckwp9inogsinwu31lmv12` (`last_modified_by`),
    KEY `FKhrwnxp9uuri3jggxnsvfbpqxk` (`warehouseid`),
    CONSTRAINT `FKhrwnxp9uuri3jggxnsvfbpqxk` FOREIGN KEY (`warehouseid`) REFERENCES `warehouse` (`warehouseid`),
    CONSTRAINT `FKje1bckwp9inogsinwu31lmv12` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKog8qny4p515njglw3x15stsf5` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material`
    DISABLE KEYS */;
INSERT INTO `material`
VALUES (1, 'Thép cán cho kết cấu chung', '2021-01-01 00:00:00.000000', NULL, 'Thanh', NULL, NULL, 1),
       (2, 'Dải thép đặc biệt được đánh bóng', '2021-01-02 00:00:00.000000', NULL, 'M', NULL, NULL, 2),
       (3, 'Thép carbon cho kết cấu máy', '2021-02-10 00:00:00.000000', NULL, 'Tấn', NULL, NULL, 3),
       (4, 'Rèn thép carbon', '2021-10-01 00:00:00.000000', NULL, 'Tấn', NULL, NULL, 2),
       (5, ' Thép molypden', '2021-04-20 00:00:00.000000', NULL, 'Tấn', NULL, NULL, 2),
       (6, 'sắt', '2021-08-01 00:00:00.000000', NULL, 'Tấn', NULL, NULL, 3);
/*!40000 ALTER TABLE `material`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details`
(
    `order_detail_id` bigint NOT NULL AUTO_INCREMENT,
    `quantity`        double DEFAULT NULL,
    `order_id`        bigint DEFAULT NULL,
    `price_book_id`   bigint DEFAULT NULL,
    `productid`       bigint DEFAULT NULL,
    PRIMARY KEY (`order_detail_id`),
    KEY `FK91exgdsmgpe2x3x56w3akxlo5` (`price_book_id`),
    KEY `FKjmdsv4sh1x4akwdtaibwspa0v` (`productid`),
    CONSTRAINT `FK91exgdsmgpe2x3x56w3akxlo5` FOREIGN KEY (`price_book_id`) REFERENCES `price_book` (`price_book_id`),
    CONSTRAINT `FKjmdsv4sh1x4akwdtaibwspa0v` FOREIGN KEY (`productid`) REFERENCES `product` (`product_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details`
    DISABLE KEYS */;
INSERT INTO `order_details`
VALUES (10, 400, 1, 1, NULL),
       (11, 200, 1, 2, NULL),
       (12, 350, 2, 1, NULL),
       (13, 400, 3, 3, NULL),
       (14, 555, 2, 3, NULL);
/*!40000 ALTER TABLE `order_details`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_status`
(
    `order_statusid` bigint NOT NULL AUTO_INCREMENT,
    `status`         varchar(255) DEFAULT NULL,
    PRIMARY KEY (`order_statusid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_status`
--

LOCK TABLES `order_status` WRITE;
/*!40000 ALTER TABLE `order_status`
    DISABLE KEYS */;
INSERT INTO `order_status`
VALUES (1, 'đang chuyển '),
       (2, 'chưa được chuyển');
/*!40000 ALTER TABLE `order_status`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone_verification`
--

DROP TABLE IF EXISTS `phone_verification`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phone_verification`
(
    `phone_verification_id` bigint NOT NULL AUTO_INCREMENT,
    `expiry_date`           datetime(6)  DEFAULT NULL,
    `verification_token`    varchar(255) DEFAULT NULL,
    `userid`                bigint       DEFAULT NULL,
    PRIMARY KEY (`phone_verification_id`),
    KEY `FK8r6k5am11bxnbp36hi4arnp3j` (`userid`),
    CONSTRAINT `FK8r6k5am11bxnbp36hi4arnp3j` FOREIGN KEY (`userid`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone_verification`
--

LOCK TABLES `phone_verification` WRITE;
/*!40000 ALTER TABLE `phone_verification`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `phone_verification`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `user_id`            bigint NOT NULL AUTO_INCREMENT,
    `created_date`       datetime(6)  DEFAULT NULL,
    `date_of_birth`      date         DEFAULT NULL,
    `email`              varchar(255) DEFAULT NULL,
    `first_name`         varchar(255) DEFAULT NULL,
    `is_active`          bit(1) NOT NULL,
    `last_modified_date` datetime(6)  DEFAULT NULL,
    `last_name`          varchar(255) DEFAULT NULL,
    `password`           varchar(255) DEFAULT NULL,
    `phone`              varchar(255) DEFAULT NULL,
    `street_address`     varchar(255) DEFAULT NULL,
    `username`           varchar(255) DEFAULT NULL,
    `created_by`         bigint       DEFAULT NULL,
    `districtid`         bigint       DEFAULT NULL,
    `last_modified_by`   bigint       DEFAULT NULL,
    `roleid`             bigint NOT NULL,
    `warehouse_id`       bigint       DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    KEY `FKdltbr5t0nljpuuo4isxgslt82` (`created_by`),
    KEY `FKpv1gqvu7sqlfe8snubf2ccehb` (`districtid`),
    KEY `FKhatngr6juyosue20sjqnhe4ed` (`last_modified_by`),
    KEY `FK2ovmsl4hvm5vu1w8i308r5j6w` (`roleid`),
    KEY `FKpuo5wdkckb5mr4e8rh5q3uvoo` (`warehouse_id`),
    CONSTRAINT `FK2ovmsl4hvm5vu1w8i308r5j6w` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`),
    CONSTRAINT `FKdltbr5t0nljpuuo4isxgslt82` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKhatngr6juyosue20sjqnhe4ed` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKpuo5wdkckb5mr4e8rh5q3uvoo` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouseid`),
    CONSTRAINT `FKpv1gqvu7sqlfe8snubf2ccehb` FOREIGN KEY (`districtid`) REFERENCES `district` (`districtid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
INSERT INTO `user`
VALUES (2, NULL, '2014-10-08', 'firstcustomer@gmail.com', 'Hoa', _binary '', NULL, 'Nguyen', '123', '0967843267',
        'Nguyen Trai', 'hoanguyen', NULL, NULL, NULL, 2, 0),
       (3, NULL, '2009-10-15', 'secondcustomer@gmail.com', 'Duy', _binary '\0', NULL, 'Tran', '123', '0967890435',
        'Lang Ha', 'duytran', NULL, NULL, NULL, 3, 1),
       (4, '2021-10-10 00:00:14.469315', '2000-10-10', 'byasdasd@gmail.com', 'Vung', _binary '', NULL, 'Bui',
        '$2a$10$BpiC5bVzuix47TWaTkuXiOjkzc4gKBai8fu2StZ.V/XIp9ZfWGs8W', '034578493', 'Hoang cau', 'admin', NULL, NULL,
        NULL, 1, 0),
       (5, '2021-10-10 00:01:34.837555', '2021-10-04', 'hoanthe130318@fpt.edu.vn', 'asd', _binary '', NULL, 'asd',
        '$2a$10$EyhpdqaDyNoM/dzMmGzDdevDi.3w7FEsSQYnS3wmu6VT9TCmFVaH6', '0383007243', 'asdasdasd', 'staff1', 4, 27,
        NULL, 2, 2),
       (6, '2021-10-10 00:03:14.446008', '2021-10-09', 'hoanthe130318@fpt.edu.vn', 'ewgewg', _binary '\0', NULL, 'geg',
        '$2a$10$K60p/LgWtCPcoqmDIFnaoeDGz0L9NNgREX3KD6Np8dmhEoF81x8Ei', '0974920325', 'asdasdasd', 'staff2', 4, 82,
        NULL, 3, 1);
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'test'
--

--
-- Dumping routines for database 'test'
--
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2021-10-10 23:34:16

-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer`
(
    `customer_id`      bigint NOT NULL AUTO_INCREMENT,
    `company_name`     varchar(255) DEFAULT NULL,
    `customer_type`    varchar(255) DEFAULT NULL,
    `date_of_birth`    date         DEFAULT NULL,
    `facebook`         varchar(255) DEFAULT NULL,
    `gender`           bit(1)       DEFAULT NULL,
    `note`             varchar(255) DEFAULT NULL,
    `tax_code`         varchar(255) DEFAULT NULL,
    `created_date`     datetime(6)  DEFAULT NULL,
    `customer_code`    varchar(255) DEFAULT NULL,
    `customer_name`    varchar(255) DEFAULT NULL,
    `email`            varchar(255) DEFAULT NULL,
    `phone`            varchar(255) DEFAULT NULL,
    `street_address`   varchar(255) DEFAULT NULL,
    `created_by`       bigint       DEFAULT NULL,
    `districtid`       bigint       DEFAULT NULL,
    `last_modified_by` bigint       DEFAULT NULL,
    `warehouseid`      bigint       DEFAULT NULL,
    PRIMARY KEY (`customer_id`),
    KEY `FKs366o2n6spayvi9e8v0il1fj4` (`created_by`),
    KEY `FKpargme02htjdup00a6xdx1xxp` (`districtid`),
    KEY `FKmb4p9v3s9qv7kwumv740j6v71` (`last_modified_by`),
    KEY `FK4lqovd3recrhdnwmoccrd5oyx` (`warehouseid`),
    CONSTRAINT `FK4lqovd3recrhdnwmoccrd5oyx` FOREIGN KEY (`warehouseid`) REFERENCES `warehouse` (`warehouseid`),
    CONSTRAINT `FKmb4p9v3s9qv7kwumv740j6v71` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKpargme02htjdup00a6xdx1xxp` FOREIGN KEY (`districtid`) REFERENCES `district` (`districtid`),
    CONSTRAINT `FKs366o2n6spayvi9e8v0il1fj4` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer`
    DISABLE KEYS */;
INSERT INTO `customer`
VALUES (1, 'FPT', 'VIP', '1999-01-01', 'https://www.facebook.com/profile.php?id=100009266184674', _binary '', NULL,
        NULL, '2021-10-10 00:00:00.000000', NULL, 'Vũ Minh Hiếu', 'hieuvm@fpt.edu.vn', '0912393939', 'Nam Định', NULL,
        NULL, NULL, NULL),
       (2, 'FPT1', 'Nomal', '2000-02-02', 'https://www.facebook.com/thanhdo2510', _binary '', NULL, NULL,
        '2021-10-09 00:00:00.000000', NULL, 'Đỗ Trần Thành', 'thanhdt@fpt.edu.vn', '0913333333', 'Hùng Vương', NULL,
        NULL, NULL, NULL),
       (3, 'FPT2', 'Nomal', '2001-03-03', 'https://www.facebook.com/danhng0818', _binary '', NULL, NULL,
        '2021-10-08 00:00:00.000000', NULL, 'Nguyễn Đức Anh', 'anhnd@fpt.edu.vn', '0911111111', 'Nguyễn Chí Thanh',
        NULL, NULL, NULL, NULL),
       (4, 'FPT3', 'VIP', '2002-04-04', 'https://www.facebook.com/nhuduyngoc.ngoc', _binary '', NULL, NULL,
        '2021-10-07 00:00:00.000000', NULL, 'Nhữ Duy Ngọc', 'ngocnd@fpt.edu.vn', '0922222222', 'Hồ Tây', NULL, NULL,
        NULL, NULL),
       (5, 'FPT4', 'VIP', '2003-05-05', 'https://www.facebook.com/Forgottenangel1999', _binary '', NULL, NULL,
        '2021-10-06 00:00:00.000000', NULL, 'Nguyễn Thiên Long', 'longnt@fpt.edu.vn', '0944444444', 'Đông Anh', NULL,
        NULL, NULL, NULL),
       (6, 'FPT5', 'Nomal', '2004-06-06', 'https://www.facebook.com/Tildnight415', _binary '', NULL, NULL,
        '2021-10-05 00:00:00.000000', NULL, 'Phùng Tất Đạt', 'datpt@fpt.edu.vn', '0955555555', 'Thạch Thất', NULL, NULL,
        NULL, NULL),
       (7, 'FPT6', 'Nomal', '2005-07-07', 'https://www.facebook.com/chiimintk', _binary '\0', NULL, NULL,
        '2021-10-04 00:00:00.000000', NULL, 'Nguyễn Thị Kim Chi', 'chintk@fpt.edu.vn', '0988888888', 'Vân Đồn', NULL,
        NULL, NULL, NULL),
       (8, 'FPT7', 'VIP', '1998-10-10', 'https://www.facebook.com/thuyquynh1202', _binary '\0', NULL, NULL,
        '2021-10-03 00:00:00.000000', NULL, 'Phạm Thúy Quỳnh', 'qunhpt@fpt.edu.vn', '0966666666', 'Hải Dương', NULL,
        NULL, NULL, NULL);
/*!40000 ALTER TABLE `customer`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price_book`
--

DROP TABLE IF EXISTS `price_book`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `price_book`
(
    `price_book_id`          bigint NOT NULL AUTO_INCREMENT,
    `is_active`              bit(1)       DEFAULT NULL,
    `is_standard_price_book` bit(1)       DEFAULT NULL,
    `price_book_name`        varchar(255) DEFAULT NULL,
    `warehouseid`            bigint       DEFAULT NULL,
    PRIMARY KEY (`price_book_id`),
    KEY `FK3wty2kisgfisu4lkpjafrnk62` (`warehouseid`),
    CONSTRAINT `FK3wty2kisgfisu4lkpjafrnk62` FOREIGN KEY (`warehouseid`) REFERENCES `warehouse` (`warehouseid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price_book`
--

LOCK TABLES `price_book` WRITE;
/*!40000 ALTER TABLE `price_book`
    DISABLE KEYS */;
INSERT INTO `price_book`
VALUES (2, _binary '', _binary '', 'halo', NULL),
       (3, _binary '', _binary '', 'halo1', NULL),
       (4, _binary '\0', _binary '', 'halo2', NULL),
       (5, _binary '', _binary '', 'pricebook3', NULL),
       (6, _binary '\0', _binary '', 'pricebook4', NULL),
       (7, _binary '', _binary '', 'pricebook5', NULL),
       (8, _binary '', _binary '', 'pricebook6', NULL),
       (9, _binary '\0', _binary '', 'pricebook7', NULL),
       (10, _binary '', _binary '', 'pricebook8', NULL),
       (11, _binary '', _binary '', 'pricebook9', NULL);
/*!40000 ALTER TABLE `price_book`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price_book_entry`
--

DROP TABLE IF EXISTS `price_book_entry`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `price_book_entry`
(
    `price_entryid` bigint NOT NULL AUTO_INCREMENT,
    `price`         double DEFAULT NULL,
    `is_active`     bit(1) DEFAULT NULL,
    `price_book_id` bigint DEFAULT NULL,
    `product_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`price_entryid`),
    KEY `FK4vj9p4kttrnsxcmv195eptctu` (`price_book_id`),
    KEY `FK23oxqp12g5aqtv28ggq7enp43` (`product_id`),
    CONSTRAINT `FK23oxqp12g5aqtv28ggq7enp43` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
    CONSTRAINT `FK4vj9p4kttrnsxcmv195eptctu` FOREIGN KEY (`price_book_id`) REFERENCES `price_book` (`price_book_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price_book_entry`
--

LOCK TABLES `price_book_entry` WRITE;
/*!40000 ALTER TABLE `price_book_entry`
    DISABLE KEYS */;
INSERT INTO `price_book_entry`
VALUES (1, 20000, _binary '', 2, 1),
       (2, 24000, _binary '\0', 3, 2),
       (3, 480, _binary '', 4, 3),
       (4, 530, _binary '\0', 5, 5),
       (5, 640, _binary '', 6, 4),
       (6, 800, _binary '', 7, 6),
       (7, 630, _binary '\0', 8, 12),
       (8, 690, _binary '\0', 9, 14),
       (9, 830, _binary '', 5, 23),
       (10, 1030, _binary '', 4, 12),
       (11, 870, _binary '\0', 5, 6),
       (12, 1050, _binary '', 8, 8);
/*!40000 ALTER TABLE `price_book_entry`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product`
(
    `product_id`               bigint NOT NULL AUTO_INCREMENT,
    `product_name`             varchar(255) DEFAULT NULL,
    `quantity_unit_of_measure` varchar(255) DEFAULT NULL,
    `is_active`                bit(1)       DEFAULT NULL,
    `categoryid`               bigint       DEFAULT NULL,
    `created_by`               bigint       DEFAULT NULL,
    `last_modified_by`         bigint       DEFAULT NULL,
    `warehouseid`              bigint       DEFAULT NULL,
    PRIMARY KEY (`product_id`),
    KEY `FK4ort9abhumpx4t2mlngljr1vi` (`categoryid`),
    KEY `FKelarb1m1p9maeiwfbawmw0m1m` (`warehouseid`),
    KEY `FKrjh52y9ob5vyeiuxvvht404s6` (`last_modified_by`),
    KEY `FKtnjhc9s7k2hp0e9iyhebd8sn9` (`created_by`),
    CONSTRAINT `FK4ort9abhumpx4t2mlngljr1vi` FOREIGN KEY (`categoryid`) REFERENCES `category` (`category_id`),
    CONSTRAINT `FKelarb1m1p9maeiwfbawmw0m1m` FOREIGN KEY (`warehouseid`) REFERENCES `warehouse` (`warehouseid`),
    CONSTRAINT `FKrjh52y9ob5vyeiuxvvht404s6` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKtnjhc9s7k2hp0e9iyhebd8sn9` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product`
    DISABLE KEYS */;
INSERT INTO `product`
VALUES (1, 'NS14x10', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (2, 'NS14x12', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (3, 'NS14x15', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (4, 'NS14x20', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (5, 'NS16x10', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (6, 'NS16x12', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (7, 'NS16x15', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (8, 'NS16x20', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (9, 'NS18x12', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (10, 'NS18x15', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (11, 'NS18x10', 'cái', _binary '\0', NULL, NULL, NULL, NULL),
       (12, 'NS18x20', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (13, 'NS20x12', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (14, 'NS20x15', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (15, 'NS20x20', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (16, 'NS12x15', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (17, 'NS12x20', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (18, 'NS12x10', 'cái', _binary '\0', NULL, NULL, NULL, NULL),
       (19, 'NS12x8', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (20, 'NS12x9', 'cái', _binary '\0', NULL, NULL, NULL, NULL),
       (21, 'NS10x8', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (22, 'NS10x10', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (23, 'BL14x12', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (24, 'BL14x15', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (25, 'BL14x10', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (26, 'BL14x20', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (27, 'BL16x10', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (28, 'BL16x12', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (29, 'BL16x15', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (30, 'BL16x20', 'cái', _binary '', NULL, NULL, NULL, NULL),
       (31, 'BL16x30', 'cái', _binary '\0', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `product`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `province`
(
    `provinceid`    bigint NOT NULL AUTO_INCREMENT,
    `province_name` varchar(255) DEFAULT NULL,
    `province_type` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`provinceid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province`
--

LOCK TABLES `province` WRITE;
/*!40000 ALTER TABLE `province`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `province`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_history`
--

DROP TABLE IF EXISTS `purchase_history`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase_history`
(
    `purchaseid`       bigint NOT NULL AUTO_INCREMENT,
    `purchase_date`    datetime(6)  DEFAULT NULL,
    `quantity`         double       DEFAULT NULL,
    `unit_price`       varchar(255) DEFAULT NULL,
    `created_by`       bigint       DEFAULT NULL,
    `last_modified_by` bigint       DEFAULT NULL,
    `material_id`      bigint       DEFAULT NULL,
    `supplierid`       bigint       DEFAULT NULL,
    PRIMARY KEY (`purchaseid`),
    KEY `FKrmqrft67dlqpjaaxemx7g760o` (`created_by`),
    KEY `FKs2tiv6e2u19o56nwhebeqybgm` (`last_modified_by`),
    KEY `FKpywk0ushtyx90db8rit590ig` (`material_id`),
    KEY `FK9s9eihq963i28lxr85pvb1209` (`supplierid`),
    CONSTRAINT `FK9s9eihq963i28lxr85pvb1209` FOREIGN KEY (`supplierid`) REFERENCES `supplier` (`supplier_id`),
    CONSTRAINT `FKpywk0ushtyx90db8rit590ig` FOREIGN KEY (`material_id`) REFERENCES `material` (`materialid`),
    CONSTRAINT `FKrmqrft67dlqpjaaxemx7g760o` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKs2tiv6e2u19o56nwhebeqybgm` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 15
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_history`
--

LOCK TABLES `purchase_history` WRITE;
/*!40000 ALTER TABLE `purchase_history`
    DISABLE KEYS */;
INSERT INTO `purchase_history`
VALUES (1, '2021-10-10 00:00:00.000000', 100, '480', NULL, NULL, NULL, 1),
       (2, '2021-10-11 00:00:00.000000', 200, '530', NULL, NULL, NULL, 1),
       (3, '2021-10-12 00:00:00.000000', 300, '640', NULL, NULL, NULL, 2),
       (4, '2021-10-13 00:00:00.000000', 100, '800', NULL, NULL, NULL, 4),
       (5, '2021-10-14 00:00:00.000000', 100, '830', NULL, NULL, NULL, 6),
       (6, '2021-10-15 00:00:00.000000', 100, '870', NULL, NULL, NULL, 7),
       (7, '2021-10-16 00:00:00.000000', 200, '1050', NULL, NULL, NULL, 1),
       (8, '2021-10-17 00:00:00.000000', 300, '1300', NULL, NULL, NULL, 2),
       (9, '2021-10-18 00:00:00.000000', 150, '1320', NULL, NULL, NULL, 4),
       (10, '2021-10-19 00:00:00.000000', 250, '1500', NULL, NULL, NULL, 6),
       (11, '2021-10-20 00:00:00.000000', 140, '550', NULL, NULL, NULL, 7),
       (12, '2021-10-21 00:00:00.000000', 130, '640', NULL, NULL, NULL, 7),
       (13, '2021-10-22 00:00:00.000000', 200, '152', NULL, NULL, NULL, 1),
       (14, '2021-10-23 00:00:00.000000', 250, '175', NULL, NULL, NULL, 2);
/*!40000 ALTER TABLE `purchase_history`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6)  DEFAULT NULL,
    `token`        varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `refresh_token`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role`
(
    `roleid`    bigint NOT NULL AUTO_INCREMENT,
    `role_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`roleid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site`
--

DROP TABLE IF EXISTS `site`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `site`
(
    `site_id`     bigint NOT NULL AUTO_INCREMENT,
    `address`     varchar(255) DEFAULT NULL,
    `site_name`   varchar(255) DEFAULT NULL,
    `warehouseid` bigint       DEFAULT NULL,
    PRIMARY KEY (`site_id`),
    KEY `FKrx5lx0tbcd88x5aw37u1nyhdq` (`warehouseid`),
    CONSTRAINT `FKrx5lx0tbcd88x5aw37u1nyhdq` FOREIGN KEY (`warehouseid`) REFERENCES `warehouse` (`warehouseid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site`
--

LOCK TABLES `site` WRITE;
/*!40000 ALTER TABLE `site`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `site`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_history`
--

DROP TABLE IF EXISTS `stock_history`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_history`
(
    `stock_hisid`        bigint NOT NULL AUTO_INCREMENT,
    `current_quantity`   double      DEFAULT NULL,
    `stock_in_quantity`  double      DEFAULT NULL,
    `unit_cost_price`    double      DEFAULT NULL,
    `created_date`       datetime(6) DEFAULT NULL,
    `last_modified_date` datetime(6) DEFAULT NULL,
    `created_by`         bigint      DEFAULT NULL,
    `last_modified_by`   bigint      DEFAULT NULL,
    `product_id`         bigint      DEFAULT NULL,
    PRIMARY KEY (`stock_hisid`),
    KEY `FKagxpscifg8twc8v3g3jshtn1o` (`created_by`),
    KEY `FKa00jkxyw3vchsr4797r401g9s` (`last_modified_by`),
    KEY `FK9cnhyljdkdsx04j2t7s8hu8u6` (`product_id`),
    CONSTRAINT `FK9cnhyljdkdsx04j2t7s8hu8u6` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
    CONSTRAINT `FKa00jkxyw3vchsr4797r401g9s` FOREIGN KEY (`last_modified_by`) REFERENCES `product` (`last_modified_by`),
    CONSTRAINT `FKagxpscifg8twc8v3g3jshtn1o` FOREIGN KEY (`created_by`) REFERENCES `product` (`created_by`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_history`
--

LOCK TABLES `stock_history` WRITE;
/*!40000 ALTER TABLE `stock_history`
    DISABLE KEYS */;
INSERT INTO `stock_history`
VALUES (1, 300, 100, 1030, '2021-09-09 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 2),
       (2, 200, 100, 1050, '2021-09-10 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 3),
       (3, 400, 50, 850, '2021-09-11 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 4),
       (4, 100, 60, 1500, '2021-09-12 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 5),
       (5, 500, 200, 550, '2021-09-13 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 6),
       (6, 200, 150, 640, '2021-09-14 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 7),
       (7, 1000, 250, 720, '2021-09-15 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 8),
       (8, 2000, 400, 1080, '2021-09-16 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 9),
       (9, 1000, 100, 2001, '2021-09-17 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 10),
       (10, 3000, 500, 480, '2021-09-18 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 11),
       (11, 700, 100, 120, '2021-09-19 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 12),
       (12, 800, 100, 1150, '2021-09-20 00:00:00.000000', '2021-10-10 00:00:00.000000', NULL, NULL, 13);
/*!40000 ALTER TABLE `stock_history`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier`
(
    `supplier_id`      bigint NOT NULL AUTO_INCREMENT,
    `supplier_code`    varchar(255) DEFAULT NULL,
    `supplier_name`    varchar(255) DEFAULT NULL,
    `email`            varchar(255) DEFAULT NULL,
    `is_active`        bit(1)       DEFAULT NULL,
    `phone`            varchar(255) DEFAULT NULL,
    `street_address`   varchar(255) DEFAULT NULL,
    `created_by`       bigint       DEFAULT NULL,
    `district_id`      bigint       DEFAULT NULL,
    `last_modified_by` bigint       DEFAULT NULL,
    `warehouseid`      bigint       DEFAULT NULL,
    PRIMARY KEY (`supplier_id`),
    KEY `FK9uqhuusosbieemsxvamosxrdm` (`created_by`),
    KEY `FKae15uaqxio5el4c6qt5e1nqyw` (`district_id`),
    KEY `FK8p51q80bcrc4kcjrw6xmp7qss` (`last_modified_by`),
    KEY `FKpf5ch226iljye7cfk370hm4uc` (`warehouseid`),
    CONSTRAINT `FK8p51q80bcrc4kcjrw6xmp7qss` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FK9uqhuusosbieemsxvamosxrdm` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKae15uaqxio5el4c6qt5e1nqyw` FOREIGN KEY (`district_id`) REFERENCES `district` (`districtid`),
    CONSTRAINT `FKpf5ch226iljye7cfk370hm4uc` FOREIGN KEY (`warehouseid`) REFERENCES `warehouse` (`warehouseid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier`
    DISABLE KEYS */;
INSERT INTO `supplier`
VALUES (1, 'SPL1', 'suppleier1', 'suppleier1@gmail.com', _binary '', '0922222222', 'Nguyễn Chí Thanh', NULL, NULL,
        NULL, NULL),
       (2, 'SPL2', 'suppleier2', 'suppleier2@gmail.com', _binary '', '0912393939', 'Hồ Gương', NULL, NULL, NULL, NULL),
       (3, 'SPL3', 'suppleier3', 'suppleier3@gmail.com', _binary '\0', '0913333333', 'Hồ Tây', NULL, NULL, NULL, NULL),
       (4, 'SPL4', 'suppleier4', 'suppleier4@gmail.com', _binary '', '0911111111', 'Trần Duy Hưng', NULL, NULL, NULL,
        NULL),
       (5, 'SPL5', 'suppleier5', 'suppleier5@gmail.com', _binary '\0', '0922222222', 'Lê Văn Lương', NULL, NULL, NULL,
        NULL),
       (6, 'SPL6', 'suppleier6', 'suppleier6@gmail.com', _binary '', '0944444444', 'Nguyễn Trãi', NULL, NULL, NULL,
        NULL),
       (7, 'SPL7', 'suppleier7', 'suppleier7@gmail.com', _binary '', '0955555555', 'Xuân Thủy', NULL, NULL, NULL,
        NULL),
       (8, 'SPL8', 'suppleier8', 'suppleier8@gmail.com', _binary '\0', '0966666666', 'Mỹ Đình', NULL, NULL, NULL, NULL),
       (9, 'SPL9', 'suppleier9', 'suppleier9@gmail.com', _binary '', '0977777777', 'Hoàng Hoa Thám', NULL, NULL, NULL,
        NULL),
       (10, 'SPL10', 'suppleier10', 'suppleier10@gmail.com', _binary '\0', '0988888888', 'Ngụy Như Kon Tum', NULL, NULL,
        NULL, NULL);
/*!40000 ALTER TABLE `supplier`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user`
(
    `user_id`            bigint NOT NULL AUTO_INCREMENT,
    `created_date`       datetime(6)  DEFAULT NULL,
    `date_of_birth`      date         DEFAULT NULL,
    `email`              varchar(255) DEFAULT NULL,
    `first_name`         varchar(255) DEFAULT NULL,
    `is_active`          bit(1) NOT NULL,
    `last_modified_date` datetime(6)  DEFAULT NULL,
    `last_name`          varchar(255) DEFAULT NULL,
    `password`           varchar(255) DEFAULT NULL,
    `phone`              varchar(255) DEFAULT NULL,
    `street_address`     varchar(255) DEFAULT NULL,
    `username`           varchar(255) DEFAULT NULL,
    `created_by`         bigint       DEFAULT NULL,
    `districtid`         bigint       DEFAULT NULL,
    `last_modified_by`   bigint       DEFAULT NULL,
    `roleid`             bigint NOT NULL,
    `warehouse_id`       bigint       DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    KEY `FKdltbr5t0nljpuuo4isxgslt82` (`created_by`),
    KEY `FKpv1gqvu7sqlfe8snubf2ccehb` (`districtid`),
    KEY `FKhatngr6juyosue20sjqnhe4ed` (`last_modified_by`),
    KEY `FK2ovmsl4hvm5vu1w8i308r5j6w` (`roleid`),
    KEY `FKpuo5wdkckb5mr4e8rh5q3uvoo` (`warehouse_id`),
    CONSTRAINT `FK2ovmsl4hvm5vu1w8i308r5j6w` FOREIGN KEY (`roleid`) REFERENCES `role` (`roleid`),
    CONSTRAINT `FKdltbr5t0nljpuuo4isxgslt82` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKhatngr6juyosue20sjqnhe4ed` FOREIGN KEY (`last_modified_by`) REFERENCES `user` (`user_id`),
    CONSTRAINT `FKpuo5wdkckb5mr4e8rh5q3uvoo` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouseid`),
    CONSTRAINT `FKpv1gqvu7sqlfe8snubf2ccehb` FOREIGN KEY (`districtid`) REFERENCES `district` (`districtid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_token`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `expiry_date` datetime(6)  DEFAULT NULL,
    `token`       varchar(255) DEFAULT NULL,
    `userid`      bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK8wtgvvujmyh9igjbsj8rbh9ee` (`userid`),
    CONSTRAINT `FK8wtgvvujmyh9igjbsj8rbh9ee` FOREIGN KEY (`userid`) REFERENCES `user` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_token`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse`
(
    `warehouseid`    bigint NOT NULL AUTO_INCREMENT,
    `address`        varchar(255) DEFAULT NULL,
    `warehouse_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`warehouseid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'test'
--

--
-- Dumping routines for database 'test'
--
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2021-10-10 23:25:43
