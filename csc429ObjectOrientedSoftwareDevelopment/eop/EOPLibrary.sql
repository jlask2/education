-- phpMyAdmin SQL Dump
-- version 3.5.8.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 08, 2014 at 02:35 PM
-- Server version: 5.1.71
-- PHP Version: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `EOPLibrary`
--

-- --------------------------------------------------------

--
-- Table structure for table `Book`
--

DROP TABLE IF EXISTS `Book`;
CREATE TABLE IF NOT EXISTS `Book` (
  `Barcode` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `Title` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `Discipline` varchar(15) COLLATE latin1_general_ci NOT NULL,
  `Author1` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `Author2` varchar(30) COLLATE latin1_general_ci DEFAULT NULL,
  `Author3` varchar(30) COLLATE latin1_general_ci DEFAULT NULL,
  `Author4` varchar(30) COLLATE latin1_general_ci DEFAULT NULL,
  `Publisher` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `YearOfPublication` int(4) NOT NULL,
  `ISBN` varchar(15) COLLATE latin1_general_ci DEFAULT NULL,
  `Condition` enum('Good','Damaged') COLLATE latin1_general_ci NOT NULL DEFAULT 'Good',
  `SuggestedPrice` varchar(6) COLLATE latin1_general_ci NOT NULL,
  `Notes` varchar(300) COLLATE latin1_general_ci DEFAULT NULL,
  `Status` enum('Active','Lost','Inactive') COLLATE latin1_general_ci NOT NULL DEFAULT 'Active',
  `DateOfLastUpdate` varchar(12) COLLATE latin1_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `BookBarcodePrefix`
--

DROP TABLE IF EXISTS `BookBarcodePrefix`;
CREATE TABLE IF NOT EXISTS `BookBarcodePrefix` (
  `PrefixValue` varchar(3) COLLATE latin1_general_ci NOT NULL,
  `Discipline` varchar(15) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`PrefixValue`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `MaxDueDate`
--

DROP TABLE IF EXISTS `MaxDueDate`;
CREATE TABLE IF NOT EXISTS `MaxDueDate` (
  `CurrentMaxDueDate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`CurrentMaxDueDate`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Rental`
--

DROP TABLE IF EXISTS `Rental`;
CREATE TABLE IF NOT EXISTS `Rental` (
  `ID` int(5) NOT NULL AUTO_INCREMENT,
  `BorrowerID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `BookID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `CheckoutDate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `CheckoutWorkerID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `DueDate` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `CheckinDate` varchar(10) COLLATE latin1_general_ci DEFAULT NULL,
  `CheckinWorkerId` varchar(10) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `StudentBorrower`
--

DROP TABLE IF EXISTS `StudentBorrower`;
CREATE TABLE IF NOT EXISTS `StudentBorrower` (
  `BannerID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `FirstName` varchar(25) COLLATE latin1_general_ci NOT NULL,
  `LastName` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `ContactPhone` varchar(15) COLLATE latin1_general_ci NOT NULL,
  `Email` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `BorrowerStatus` enum('Delinquent','Good Standing') COLLATE latin1_general_ci NOT NULL DEFAULT 'Good Standing',
  `DateOfLatestBorrowerStatus` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `DateOfFirstRegistration` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `MonetaryPenalty` varchar(6) COLLATE latin1_general_ci DEFAULT NULL,
  `Notes` varchar(300) COLLATE latin1_general_ci DEFAULT NULL,
  `Status` enum('Active','Inactive') COLLATE latin1_general_ci NOT NULL DEFAULT 'Active',
  `DateOfLastUpdate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`BannerID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Worker`
--

DROP TABLE IF EXISTS `Worker`;
CREATE TABLE IF NOT EXISTS `Worker` (
  `BannerID` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `Password` varchar(25) COLLATE latin1_general_ci NOT NULL,
  `FirstName` varchar(25) COLLATE latin1_general_ci NOT NULL,
  `LastName` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `ContactPhone` varchar(15) COLLATE latin1_general_ci NOT NULL,
  `Email` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `Credentials` enum('Administrator','Ordinary') COLLATE latin1_general_ci NOT NULL DEFAULT 'Ordinary',
  `DateOfLatestCredentialsStatus` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `DateOfHire` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `Status` enum('Active','Inactive') COLLATE latin1_general_ci NOT NULL DEFAULT 'Active',
  `DateOfLastUpdate` varchar(12) COLLATE latin1_general_ci NOT NULL,
  PRIMARY KEY (`BannerID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
