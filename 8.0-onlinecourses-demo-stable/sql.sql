USE `onlinecourses`;

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `pw` varchar(68) DEFAULT NULL,
  `active` tinyint NOT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DETAIL_idx` (`role_id`),
  CONSTRAINT `FK_DETAIL` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Inserting data for table `roles`
--

INSERT INTO `roles`
VALUES
('1','admin','ROLE_ADMIN');

--
-- Inserting data for table `members`
--

INSERT INTO `members`
VALUES
('1','admin','{bcrypt}$2a$10$0cUWqcnzOGwn3PHWTd05BupdS9UMAu8oQw2wdivkXL9VzvqzQKBsa',1,'1');
