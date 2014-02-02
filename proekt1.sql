-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 02, 2014 at 01:36 PM
-- Server version: 5.6.14
-- PHP Version: 5.5.6

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
  PRIMARY KEY (`iban`),
  UNIQUE KEY `iban` (`iban`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `currencies`
--

CREATE TABLE IF NOT EXISTS `currencies` (
  `name` varchar(255) CHARACTER SET latin1 NOT NULL,
  `rate` double NOT NULL,
  `code` varchar(5) CHARACTER SET latin1 NOT NULL,
  `ratio` double NOT NULL,
  `reverserate` double NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
('test', 'cdb1f8e74b5bcd49d5c4185522a6fcc', 'Петре', 'Pertrov', 'Georgiev', '8612123738', 0, '0', 0, 'Bulgaria', 'Sofia', 'Sofia Ovcha Kupel 1 524 D', '0878989838', 'Путко@abv.bg', 2),
('test2', '8eb2eb975fe7b813f9fae22fc7b5305c', 'Petar', 'Pertrov', 'Georgiev', '8612125557', 31, '12', 1966, 'Bulgaria', 'Sofia', 'Sofia Ovcha Kupel 1 524 D', '0878989838', 'petko@abv.bg', 3),
('test3', '3c2131bd3826358a02762f7990da4903', 'Petar', 'Pertrov', 'Georgiev', '8612123737', 0, '0', 0, 'Bulgaria', 'Sofia', 'Sofia Ovcha Kupel 1 524 D', '0878989838', 'petko@abv.bg', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
