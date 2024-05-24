CREATE TABLE `collections` (
                               `collection_id` varchar(36) NOT NULL,
                               `name` varchar(255) NOT NULL,
                               `description` mediumtext,
                               `category` varchar(255) NOT NULL,
                               `acquisition_date` date DEFAULT NULL,
                               `status` enum('on_display','in_storage','on_loan') NOT NULL DEFAULT 'in_storage',
                               `image_url` varchar(255) DEFAULT NULL,
                               PRIMARY KEY (`collection_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exhibition_items` (
                                    `exhibition_id` char(36) NOT NULL,
                                    `collection_id` varchar(36) NOT NULL,
                                    PRIMARY KEY (`exhibition_id`,`collection_id`),
                                    KEY `collection_id_idx` (`collection_id`),
                                    CONSTRAINT `collection_id_fk` FOREIGN KEY (`collection_id`) REFERENCES `collections` (`collection_id`),
                                    CONSTRAINT `exhibition_id_fk` FOREIGN KEY (`exhibition_id`) REFERENCES `exhibitions` (`exhibition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exhibitions` (
                               `exhibition_id` char(36) NOT NULL,
                               `title` varchar(255) NOT NULL,
                               `start_date` date NOT NULL,
                               `end_date` date NOT NULL,
                               `description` mediumtext,
                               `is_active` tinyint DEFAULT '1',
                               PRIMARY KEY (`exhibition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
                         `user_id` char(36) NOT NULL,
                         `username` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `role` enum('admin','staff','visitor') NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `is_visitor` tinyint NOT NULL DEFAULT '0',
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `username_UNIQUE` (`username`),
                         UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `visitor_logs` (
                                `log_id` int NOT NULL AUTO_INCREMENT,
                                `visitor_id` int NOT NULL,
                                `entry_time` datetime NOT NULL,
                                `exit_time` datetime DEFAULT NULL,
                                PRIMARY KEY (`log_id`),
                                KEY `visitor_id_idx` (`visitor_id`),
                                CONSTRAINT `visitor_id_fk` FOREIGN KEY (`visitor_id`) REFERENCES `visitors` (`visitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `visitors` (
                            `visitor_id` int NOT NULL AUTO_INCREMENT,
                            `full_name` varchar(255) NOT NULL,
                            `email` varchar(255) DEFAULT NULL,
                            `phone` varchar(15) DEFAULT NULL,
                            `visit_date` date DEFAULT NULL,
                            `group_size` int DEFAULT '1',
                            PRIMARY KEY (`visitor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
