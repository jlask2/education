-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 10, 2014 at 10:45 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `pms`
--

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE IF NOT EXISTS `project` (
  `ProjectCode` varchar(12) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Description` varchar(160) NOT NULL,
  `Category` varchar(20) NOT NULL,
  `EndDate` date NOT NULL,
  `Library` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE IF NOT EXISTS `task` (
  `TaskID` int(11) NOT NULL AUTO_INCREMENT,
  `TaskCode` varchar(30) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `ProjectCode` varchar(12) NOT NULL,
  `Description` varchar(160) NOT NULL,
  `DueDate` date NOT NULL,
  `CBED` int(11) NOT NULL,
  `Status` varchar(30) NOT NULL,
  `ACD` date NOT NULL,
  `RCD` date NOT NULL,
  PRIMARY KEY (`TaskID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tasklibrary`
--

CREATE TABLE IF NOT EXISTS `tasklibrary` (
  `LibraryID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Description` varchar(160) NOT NULL,
  PRIMARY KEY (`LibraryID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tasklibtotasktemp`
--

CREATE TABLE IF NOT EXISTS `tasklibtotasktemp` (
  `LibraryID` int(11) NOT NULL,
  `TemplateCode` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tasktemplate`
--

CREATE TABLE IF NOT EXISTS `tasktemplate` (
  `TemplateCode` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(30) NOT NULL,
  `Description` varchar(160) NOT NULL,
  `CBED` int(11) NOT NULL,
  PRIMARY KEY (`TemplateCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
