-
ALTER TABLE `account` ADD `interest_rate` DECIMAL(5,2) DEFAULT 0.00 COMMENT 'Taux d''intérêt en pourcentage';

UPDATE `account` SET `interest_rate` = 0.00 WHERE `account_type` = 'Compte courant';
UPDATE `account` SET `interest_rate` = 2.00 WHERE `account_type` = 'Livret d''Épargne';
UPDATE `account` SET `interest_rate` = 3.50 WHERE `account_type` = 'Compte à terme';
UPDATE `account` SET `interest_rate` = 1.50 WHERE `account_type` = 'Compte-titre';

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL COMMENT 'Mot de passe hashé avec BCrypt',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `admin` (`username`, `password_hash`) 
VALUES ('admin', '$2a$12$dZaGy/3z1WUYpZbrG3eep.YVGGgCNpzmKKOIXeNLeXcgkwQGNXJLG');