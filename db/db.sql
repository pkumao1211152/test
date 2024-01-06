CREATE TABLE `source_data` (
                               `post_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                               `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                               `clapCount` int DEFAULT '0',
                               `mediumUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;