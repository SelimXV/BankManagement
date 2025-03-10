-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : ven. 07 mars 2025 à 15:10
-- Version du serveur : 5.7.39
-- Version de PHP : 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `banksystem`
--

-- --------------------------------------------------------

--
-- Structure de la table `account`
--

CREATE TABLE `account` (
  `formno` varchar(20) NOT NULL,
  `card_number` varchar(20) NOT NULL,
  `pin` varchar(10) NOT NULL,
  `account_type` varchar(50) NOT NULL,
  `e_statement` tinyint(1) DEFAULT '0',
  `credit_card` tinyint(1) DEFAULT '0',
  `chequebook` tinyint(1) DEFAULT '0',
  `account_insurance` tinyint(1) DEFAULT '0',
  `mobile_banking` tinyint(1) DEFAULT '0',
  `balance_alerts` tinyint(1) DEFAULT '0',
  `balance` double DEFAULT '0',
  `is_closed` tinyint(1) DEFAULT '0',
  `currency` varchar(3) DEFAULT 'EUR'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `account`
--

INSERT INTO `account` (`formno`, `card_number`, `pin`, `account_type`, `e_statement`, `credit_card`, `chequebook`, `account_insurance`, `mobile_banking`, `balance_alerts`, `balance`, `is_closed`, `currency`) VALUES
('2054', '3371547192078977', '6160', 'Compte à terme', 0, 1, 0, 1, 0, 0, 308, 0, 'EUR'),
('3017', '7307131148596477', '5169', 'Compte-titre', 0, 0, 0, 0, 0, 0, 0, 1, 'USD'),
('4415', '5434928916213709', '5152', 'Livret d\'Épargne', 0, 1, 1, 1, 0, 0, 400, 0, 'EUR');

-- --------------------------------------------------------

--
-- Structure de la table `signup`
--

CREATE TABLE `signup` (
  `form_no` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `fName` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `gender` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `postal_code` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `signup`
--

INSERT INTO `signup` (`form_no`, `name`, `fName`, `dob`, `gender`, `email`, `address`, `city`, `postal_code`) VALUES
('2054', 's', 's', '2025-03-07', 'Homme', 's', 's', 'ss', 's'),
('3017', 'dst', 'ap2', '2025-03-03', 'Homme', 'test', '29 avenue', 'Poissy', '78300'),
('3311', 'z', 'z', '2025-03-05', 'Femme', 'zzz', 'z', 'z', 'z'),
('4415', 'a', 'a', '2025-03-12', 'Femme', 'aa', 'a', 'aa', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `card_number` varchar(20) NOT NULL,
  `amount` double NOT NULL,
  `type` enum('deposit','withdraw') NOT NULL,
  `transaction_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `balance_after` double NOT NULL COMMENT 'Solde après transaction'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `transactions`
--

INSERT INTO `transactions` (`id`, `card_number`, `amount`, `type`, `transaction_date`, `balance_after`) VALUES
(1, '3371547192078977', 600, 'deposit', '2025-03-07 09:35:12', 0),
(2, '3371547192078977', 300, 'withdraw', '2025-03-07 09:35:57', 0),
(3, '5434928916213709', 200, 'deposit', '2025-03-07 13:25:08', 200),
(4, '7307131148596477', 500, 'deposit', '2025-03-07 14:02:05', 500),
(5, '7307131148596477', 1000, 'withdraw', '2025-03-07 14:31:05', 0),
(6, '7307131148596477', 1000, 'deposit', '2025-03-07 14:31:18', 0),
(7, '7307131148596477', 0, 'deposit', '2025-03-07 14:31:25', 1000),
(8, '7307131148596477', 0, 'withdraw', '2025-03-07 14:31:37', 1000),
(9, '3371547192078977', 300, 'withdraw', '2025-03-07 14:36:29', 0),
(10, '3371547192078977', 300, 'deposit', '2025-03-07 14:41:31', 300),
(11, '3371547192078977', 300, 'withdraw', '2025-03-07 14:41:42', 0),
(12, '3371547192078977', 300, 'deposit', '2025-03-07 14:55:36', 300),
(13, '3371547192078977', 1, 'deposit', '2025-03-07 14:56:02', 302),
(14, '3371547192078977', 1, 'deposit', '2025-03-07 14:56:11', 304),
(15, '3371547192078977', 300, 'deposit', '2025-03-07 14:56:35', 704),
(16, '3371547192078977', 200, 'withdraw', '2025-03-07 15:02:37', 504),
(17, '3371547192078977', 2, 'deposit', '2025-03-07 15:02:50', 306),
(18, '3371547192078977', 308, 'withdraw', '2025-03-07 15:03:01', 0),
(19, '3371547192078977', 308, 'deposit', '2025-03-07 15:08:00', 0);

--
-- Déclencheurs `transactions`
--
DELIMITER $$
CREATE TRIGGER `update_balance_after_transaction` BEFORE INSERT ON `transactions` FOR EACH ROW BEGIN
    DECLARE current_balance DOUBLE;
    
    SELECT balance INTO current_balance 
    FROM account 
    WHERE card_number = NEW.card_number;
    
    IF NEW.type = 'deposit' THEN
        SET NEW.balance_after = current_balance + NEW.amount;
        UPDATE account SET balance = NEW.balance_after 
        WHERE card_number = NEW.card_number;
    ELSEIF NEW.type = 'withdraw' THEN
        IF current_balance >= NEW.amount THEN
            SET NEW.balance_after = current_balance - NEW.amount;
            UPDATE account SET balance = NEW.balance_after 
            WHERE card_number = NEW.card_number;
        ELSE
            SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Solde insuffisant';
        END IF;
    END IF;
END
$$
DELIMITER ;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`formno`),
  ADD UNIQUE KEY `card_number` (`card_number`);

--
-- Index pour la table `signup`
--
ALTER TABLE `signup`
  ADD PRIMARY KEY (`form_no`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Index pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `card_number` (`card_number`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `fk_account_signup` FOREIGN KEY (`formno`) REFERENCES `signup` (`form_no`) ON DELETE CASCADE;

--
-- Contraintes pour la table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`card_number`) REFERENCES `account` (`card_number`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
