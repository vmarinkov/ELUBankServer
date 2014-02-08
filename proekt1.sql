-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 08, 2014 at 12:05 PM
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
CREATE DATABASE IF NOT EXISTS `proekt1` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `proekt1`;

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `useregn` decimal(10,0) NOT NULL COMMENT 'in relation to users -> egn',
  `accounttype` varchar(255) NOT NULL,
  `iban` varchar(255) NOT NULL,
  `amount` float NOT NULL,
  `currency` varchar(5) NOT NULL,
  `lastupdated` date NOT NULL,
  PRIMARY KEY (`iban`),
  UNIQUE KEY `iban` (`iban`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`useregn`, `accounttype`, `iban`, `amount`, `currency`, `lastupdated`) VALUES
('8612123738', 'С фиксирана лихва', 'BG11ELUB16320266474710', 125.46, 'EUR', '2014-02-08'),
('8612123738', 'С фиксирана лихва', 'BG11ELUB16320299017157', 5822.51, 'BGN', '2014-02-08'),
('62353623', 'Разплащателна сметка', 'BG11ELUB1632032234', 123, 'USD', '0000-00-00'),
('234', 'Разплащателна сметка', 'BG11ELUB1632032965234', 123, 'USD', '0000-00-00'),
('8612123738', 'Разплащателна сметка', 'BG11ELUB16320329659871', 123, 'USD', '0000-00-00'),
('8612123738', 'Разплащателна сметка', 'BG11ELUB16320395480640', 234, 'USD', '2014-02-08');

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
  `date` date NOT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `currencies`
--

INSERT INTO `currencies` (`name`, `rate`, `code`, `ratio`, `reverserate`, `date`) VALUES
('Australian Dollar', 1.2886, 'AUD', 1, 0.776036, '2014-02-08'),
('Brazilian Real', 6.03744, 'BRL', 10, 1.65633, '2014-02-08'),
('Canadian Dollar', 1.30172, 'CAD', 1, 0.768214, '2014-02-08'),
('Swiss Franc', 1.59829, 'CHF', 1, 0.625669, '2014-02-08'),
('Chinese Yuan Renminbi ', 2.37618, 'CNY', 10, 4.20844, '2014-02-08'),
('Czech Koruna', 7.11133, 'CZK', 100, 14.0621, '2014-02-08'),
('Danish Krone', 2.62095, 'DKK', 10, 3.81541, '2014-02-08'),
('Euro', 1.95583, 'EUR', 1, 0.511292, '2014-02-08'),
('British Pound', 2.35245, 'GBP', 1, 0.425089, '2014-02-08'),
('Hong Kong Dollar', 1.85331, 'HKD', 10, 5.39575, '2014-02-08'),
('Croatian Kuna ', 2.55681, 'HRK', 10, 3.91112, '2014-02-08'),
('Hungarian Forint', 6.33344, 'HUF', 1000, 157.892, '2014-02-08'),
('Indonesian Rupiah ', 1.18481, 'IDR', 10000, 8440.17, '2014-02-08'),
('New Israel Shekel', 4.07634, 'ILS', 10, 2.45318, '2014-02-08'),
('Indian Rupee', 2.30839, 'INR', 100, 43.3202, '2014-02-08'),
('Icelandic Krona *', 1, 'ISK', 1, 1, '2014-02-08'),
('Japanese Yen', 1.4092, 'JPY', 100, 70.9622, '2014-02-08'),
('South Korean Won', 1.34011, 'KRW', 1000, 746.207, '2014-02-08'),
('Lithuanian Litas', 5.66448, 'LTL', 10, 1.76539, '2014-02-08'),
('Mexican Peso', 1.07982, 'MXN', 10, 9.2608, '2014-02-08'),
('Malaysian Ringgit', 4.31798, 'MYR', 10, 2.3159, '2014-02-08'),
('Norwegian Krone', 2.32505, 'NOK', 10, 4.30098, '2014-02-08'),
('New Zealand Dollar', 1.18809, 'NZD', 1, 0.841687, '2014-02-08'),
('Philippine Peso', 3.20203, 'PHP', 100, 31.2302, '2014-02-08'),
('Polish Zloty', 4.67064, 'PLN', 10, 2.14103, '2014-02-08'),
('New Romanian Leu', 4.36326, 'RON', 10, 2.29186, '2014-02-08'),
('Russian Rouble ', 4.15074, 'RUB', 100, 24.0921, '2014-02-08'),
('Swedish Krona', 2.20761, 'SEK', 10, 4.52979, '2014-02-08'),
('Singaporean Dollar', 1.13559, 'SGD', 1, 0.8806, '2014-02-08'),
('Thai Baht ', 4.38409, 'THB', 100, 22.8098, '2014-02-08'),
('New Turkish Lira', 6.48442, 'TRY', 10, 1.54216, '2014-02-08'),
('US Dollar', 1.44086, 'USD', 1, 0.69403, '2014-02-08'),
('Gold (1 troy ounce)', 1816.43, 'XAU', 1, 1, '2014-02-08'),
('South African Rand', 1.29805, 'ZAR', 10, 7.70386, '2014-02-08');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE IF NOT EXISTS `transactions` (
  `useregn` decimal(10,0) NOT NULL COMMENT 'user egn + iban should give us a key to search',
  `subject` varchar(255) NOT NULL,
  `receiver` varchar(255) NOT NULL,
  `iban` varchar(255) NOT NULL,
  `toiban` varchar(255) NOT NULL,
  `amount` double NOT NULL,
  `currency` varchar(5) NOT NULL,
  `timestamp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `bday` int(2) NOT NULL COMMENT 'Day of birth',
  `bmonth` varchar(355) NOT NULL COMMENT 'Month of birth',
  `byear` int(4) NOT NULL COMMENT 'Year of birth',
  `country` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL COMMENT 'Home address',
  `phone` varchar(255) NOT NULL COMMENT 'Telephone number',
  `email` varchar(50) NOT NULL COMMENT 'Email',
  `usertype` int(1) NOT NULL COMMENT 'Type of the user, admin or normal',
  PRIMARY KEY (`username`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `egn_usertype` (`egn`,`usertype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `name`, `surname`, `familyname`, `egn`, `bday`, `bmonth`, `byear`, `country`, `city`, `address`, `phone`, `email`, `usertype`) VALUES
('ry', '8b1e4ae8ccfd71468bc42a9d64fc7cde', 'йк', 'здрхтй', 'егру', '1111111111', 1, 'Jan', 1950, 'Австралия', 'фндг', 'фхдй', 'гйфгй', 'фгйфгй', 1),
('ry2', '8b1e4ae8ccfd71468bc42a9d64fc7cde', 'fgre', 'фгршгш', 'хтхшхт', '1111111111', 2, 'March', 1951, 'Азербайджан', 'frge', 'ghrhery', '7457865484', 'hrehjej', 2),
('test', 'cdb1f8e74b5bcd49d5c4185522a6fcc', 'Петре', 'Pertrov', 'Georgiev', '8612123738', 0, '0', 0, 'Bulgaria', 'Sofia', 'Sofia Ovcha Kupel 1 524 D', '08789898382', '12@abv.bg', 2),
('test2', '8eb2eb975fe7b813f9fae22fc7b5305c', 'Petar', 'Pertrov', 'Georgiev', '8612125557', 31, '12', 1966, 'Bulgaria', 'Sofia', 'Sofia Ovcha Kupel 1 524 D', '0878989838', 'petko@abv.bg', 3),
('test3', '3c2131bd3826358a02762f7990da4903', 'Petar', 'Pertrov', 'Georgiev', '8612123737', 0, '0', 0, 'Bulgaria', 'Sofia', 'Sofia Ovcha Kupel 1 524 D', '0878989838', 'petko@abv.bg', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
