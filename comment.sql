CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `additional` int DEFAULT NULL,
  `good_id` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `is_delete` bit(1) NOT NULL,
  `score` int DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `main_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci