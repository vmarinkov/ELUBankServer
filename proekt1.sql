-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2013 at 01:04 PM
-- Server version: 5.5.32
-- PHP Version: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `proekt1`
--
CREATE DATABASE IF NOT EXISTS `proekt1` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `proekt1`;

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `username` varchar(255) NOT NULL,
  `iban` varchar(255) NOT NULL,
  `currency` float NOT NULL,
  `amount` float NOT NULL,
  UNIQUE KEY `username_2` (`username`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `currencies`
--

CREATE TABLE IF NOT EXISTS `currencies` (
  `name` varchar(255) NOT NULL,
  `rate` double NOT NULL,
  `code` varchar(5) NOT NULL,
  `ratio` double NOT NULL,
  `reverserate` double NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `currencies`
--

INSERT INTO `currencies` (`name`, `rate`, `code`, `ratio`, `reverserate`, `date`) VALUES
('Australian Dollar', 1.35614, 'AUD', 1, 0.737387, '2013-11-17'),
('Brazilian Real', 6.27955, 'BRL', 10, 1.59247, '2013-11-17'),
('Canadian Dollar', 1.38859, 'CAD', 1, 0.720155, '2013-11-17'),
('Swiss Franc', 1.58418, 'CHF', 1, 0.631241, '2013-11-17'),
('Chinese Yuan Renminbi ', 2.38501, 'CNY', 10, 4.19285, '2013-11-17'),
('Czech Koruna', 7.20247, 'CZK', 100, 13.8841, '2013-11-17'),
('Danish Krone', 2.62218, 'DKK', 10, 3.81362, '2013-11-17'),
('British Pound', 2.33476, 'GBP', 1, 0.42831, '2013-11-17'),
('Hong Kong Dollar', 1.87406, 'HKD', 10, 5.33601, '2013-11-17'),
('Croatian Kuna ', 2.56284, 'HRK', 10, 3.90192, '2013-11-17'),
('Hungarian Forint', 6.55417, 'HUF', 1000, 152.575, '2013-11-17'),
('Indonesian Rupiah ', 1.25049, 'IDR', 10000, 7996.87, '2013-11-17'),
('New Israel Shekel', 4.12535, 'ILS', 10, 2.42404, '2013-11-17'),
('Indian Rupee', 2.30206, 'INR', 100, 43.4394, '2013-11-17'),
('Icelandic Krona *', 1, 'ISK', 1, 1, '2013-11-17'),
('Japanese Yen', 1.44887, 'JPY', 100, 69.0193, '2013-11-17'),
('South Korean Won', 1.366, 'KRW', 1000, 732.064, '2013-11-17'),
('Lithuanian Litas', 5.66448, 'LTL', 10, 1.76539, '2013-11-17'),
('Latvian Lat', 2.7841, 'LVL', 1, 0.359183, '2013-11-17'),
('Mexican Peso', 1.11988, 'MXN', 10, 8.92953, '2013-11-17'),
('Malaysian Ringgit', 4.53757, 'MYR', 10, 2.20382, '2013-11-17'),
('Norwegian Krone', 2.3697, 'NOK', 10, 4.21994, '2013-11-17'),
('New Zealand Dollar', 1.20611, 'NZD', 1, 0.829112, '2013-11-17'),
('Philippine Peso', 3.33628, 'PHP', 100, 29.9735, '2013-11-17'),
('Polish Zloty', 4.67578, 'PLN', 10, 2.13868, '2013-11-17'),
('New Romanian Leu', 4.39384, 'RON', 10, 2.27591, '2013-11-17'),
('Russian Rouble ', 4.44676, 'RUB', 100, 22.4883, '2013-11-17'),
('Swedish Krona', 2.1868, 'SEK', 10, 4.57289, '2013-11-17'),
('Singaporean Dollar', 1.16481, 'SGD', 1, 0.858509, '2013-11-17'),
('Thai Baht ', 4.59903, 'THB', 100, 21.7437, '2013-11-17'),
('New Turkish Lira', 7.12688, 'TRY', 10, 1.40314, '2013-11-17'),
('US Dollar', 1.45307, 'USD', 1, 0.688198, '2013-11-17'),
('South African Rand', 1.42673, 'ZAR', 10, 7.00903, '2013-11-17');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE IF NOT EXISTS `transactions` (
  `transaction_id` int(10) NOT NULL,
  `accound_id` int(10) NOT NULL,
  `iban` varchar(255) NOT NULL,
  `toiban` varchar(255) NOT NULL,
  `ammount` double NOT NULL,
  `currency` varchar(5) NOT NULL,
  `timestamp` int(11) NOT NULL,
  KEY `transaction_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(255) NOT NULL COMMENT 'username',
  `password` varchar(255) NOT NULL COMMENT 'password, md5 + salt',
  `name` varchar(255) NOT NULL COMMENT 'First name ',
  `surname` varchar(255) NOT NULL COMMENT 'Middle name',
  `familyname` varchar(255) NOT NULL COMMENT 'Family name',
  `egn` decimal(10,0) NOT NULL COMMENT 'EGN',
  `country` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL COMMENT 'Home address',
  `phone` varchar(255) NOT NULL COMMENT 'Telephone number',
  `email` varchar(50) NOT NULL COMMENT 'Email',
  `usertype` int(1) NOT NULL COMMENT 'Type of the user, admin or normal',
  PRIMARY KEY (`username`),
  UNIQUE KEY `egn` (`egn`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `name`, `surname`, `familyname`, `egn`, `country`, `city`, `address`, `phone`, `email`, `usertype`) VALUES
('test', 'cdb1f8e74b5bcd49d5c4185522a6fcc', 'Petar', 'Pertrov', 'Georgiev', '8612123738', 'Bulgaria', 'Sofia', 'Sofia Ovcha Kupel 1 524 D', '0878989838', 'petko@abv.bg', 1),
('test3', '3c2131bd3826358a02762f7990da4903', 'Petar', 'Pertrov', 'Georgiev', '8612123737', 'Bulgaria', 'Sofia', 'Sofia Ovcha Kupel 1 524 D', '0878989838', 'petko@abv.bg', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
