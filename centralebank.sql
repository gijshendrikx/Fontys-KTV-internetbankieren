-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Machine: 127.0.0.1
-- Gegenereerd op: 15 jun 2015 om 20:45
-- Serverversie: 5.6.17
-- PHP-versie: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `centralebank`
--
CREATE DATABASE IF NOT EXISTS `centralebank` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `centralebank`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `banken`
--

CREATE TABLE IF NOT EXISTS `banken` (
  `bankCode` varchar(4) NOT NULL,
  UNIQUE KEY `bankCode` (`bankCode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Gegevens worden geëxporteerd voor tabel `banken`
--

INSERT INTO `banken` (`bankCode`) VALUES
('ABNA'),
('INGB'),
('RABO'),
('RBRB');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `transactie`
--

CREATE TABLE IF NOT EXISTS `transactie` (
  `transactienummer` varchar(50) NOT NULL,
  `bedrag` int(100) NOT NULL,
  `datum` date NOT NULL,
  `beschrijving` varchar(1000) NOT NULL,
  `rekeningVan` varchar(50) NOT NULL,
  `rekeningNaar` varchar(50) NOT NULL,
  `status` int(1) NOT NULL,
  UNIQUE KEY `transactienummer` (`transactienummer`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
