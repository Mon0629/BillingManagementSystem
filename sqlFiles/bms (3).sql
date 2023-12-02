-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 02, 2023 at 03:22 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bms`
--

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE `bills` (
  `billID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `shipCustomerID` int(11) NOT NULL,
  `issueDate` date NOT NULL,
  `dueDate` date NOT NULL,
  `docType` enum('RECEIPT','INVOICE','BILL') NOT NULL,
  `transactionAdded` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`billID`, `customerID`, `shipCustomerID`, `issueDate`, `dueDate`, `docType`, `transactionAdded`) VALUES
(1, 8, 14, '2023-12-02', '2024-01-01', 'INVOICE', '2023-12-01 17:36:32'),
(2, 13, 13, '2023-12-02', '2024-01-01', 'BILL', '2023-12-01 18:28:19'),
(3, 11, 13, '2023-12-02', '2024-01-01', 'BILL', '2023-12-02 03:14:43'),
(4, 11, 14, '2023-12-02', '2024-01-01', 'BILL', '2023-12-02 06:48:26'),
(5, 10, 15, '2023-12-02', '2024-01-01', 'BILL', '2023-12-02 08:22:50'),
(6, 10, 28, '2023-12-02', '2024-01-01', 'BILL', '2023-12-02 14:16:10');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `customerID` int(11) NOT NULL,
  `creationDate` datetime NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `contactNumber` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `town` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `postal` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`customerID`, `creationDate`, `firstName`, `lastName`, `contactNumber`, `email`, `address`, `town`, `country`, `postal`) VALUES
(8, '2023-11-24 01:30:02', 'japher', 'saqs', '2147483647', 'poks@gmail.com', 'Karakura', '1234 Thing St.', 'KZ', '1234'),
(10, '2023-11-19 03:03:11', 'Fluffy', 'Formy', '1234567894', 'fluffy@mail.com', 'queensville', 'king', 'USA', '1324'),
(11, '2023-11-19 03:16:26', 'Kang', 'Seulgi', '09994498312', 'rvevers@mail.com', 'Ikuranade', 'Itaewon 1234', 'SoKor', '1234'),
(13, '2023-11-24 00:57:51', 'chunchun', 'Exarta', '09568899449', 'miner@mail.com', 'Andrage 7890 Kung St.', 'Namable', 'Congo', '4561'),
(14, '2023-11-19 03:30:01', 'Stephen', 'Stange', '09664488332', 'trybeyonce@mail.com', 'Harskanaurgh', 'Queensville 4568 ANdrago St.', 'Poland', '4561'),
(15, '2023-11-19 03:39:27', 'Loki', 'Laufeyson', '09569834562', 'keylimepie@mail.com', 'TVA', 'TVA Bldg 678', 'Cosmos', '4561'),
(28, '2023-11-22 22:23:34', 'Jasper', 'Villanueva', '09559877894', 'nyonyiks@gmail.com', 'Matarik', 'Bagong Silang po Kanan estudyante', 'Philippines', '1234'),
(29, '2023-11-22 22:38:45', 'Christian', 'Serrano', '01327894562', 'tiantian@mail.com', 'Malapit ata sa may paresan', 'Saranai daw don sa malayo lagpas ng UCC', 'Philippines', '4561'),
(30, '2023-11-22 22:42:42', 'Gwyneth', 'Uy', '04561237892', 'pinagawangplaccards@mail.com', 'Sa lagpas ng uhh Brgy. 171 na lang', 'Sa ano yon eh lagpas ng St.Benedict ata diko na tanda sowre', 'Philippines', '7844'),
(31, '2023-11-23 12:29:01', 'Stephen', 'Strange', '01234567895', 'darkholdpeeker@mail.com', 'New York Sanctum', 'The Multiverse', 'USA', '4511'),
(35, '2023-11-25 12:50:01', 'Kerang', 'Chuners', '09876543211', 'munerchi@mail.com', 'there', 'therethere', 'Philippines', '7890'),
(36, '2023-11-25 13:07:54', 'Kapre', 'Tree', 'jkjjk', 'kjgjdrjk', 'jkjj', 'jjj', 'jkjk', 'jkj'),
(40, '2023-11-25 17:37:24', 'k', 'k', 'k', 'k', 'k', 'k', 'k', 'k');

-- --------------------------------------------------------

--
-- Table structure for table `lineitems`
--

CREATE TABLE `lineitems` (
  `lineItemID` int(11) NOT NULL,
  `billID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `productName` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitPrice` decimal(12,2) NOT NULL,
  `lineItemTotal` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lineitems`
--

INSERT INTO `lineitems` (`lineItemID`, `billID`, `productID`, `productName`, `quantity`, `unitPrice`, `lineItemTotal`) VALUES
(1, 6, 1, 'Taylor Chuck ConverseXPlay', 1, 1200.00, 1200.00),
(2, 6, 1, 'Taylor Chuck ConverseXPlay', 3, 1200.00, 3600.00),
(3, 6, 1, 'Taylor Chuck ConverseXPlay', 4, 1200.00, 4800.00);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`) VALUES
('abc', 'abc'),
('abc', 'abc');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` int(8) NOT NULL,
  `ProductName` varchar(255) NOT NULL,
  `Price` double NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Remarks` varchar(255) NOT NULL,
  `Image` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `ProductName`, `Price`, `Description`, `Remarks`, `Image`) VALUES
(1, 'Taylor Chuck ConverseXPlay', 1200, 'Maganda to', 'Available', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`billID`),
  ADD KEY `FK_customerID` (`customerID`),
  ADD KEY `FK_shipCustomerID` (`shipCustomerID`);

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`customerID`);

--
-- Indexes for table `lineitems`
--
ALTER TABLE `lineitems`
  ADD PRIMARY KEY (`lineItemID`),
  ADD KEY `FK_billID` (`billID`),
  ADD KEY `FK_productID` (`productID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bills`
--
ALTER TABLE `bills`
  MODIFY `billID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `customerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `lineitems`
--
ALTER TABLE `lineitems`
  MODIFY `lineItemID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `ProductID` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `FK_customerID` FOREIGN KEY (`customerID`) REFERENCES `customers` (`customerID`),
  ADD CONSTRAINT `FK_shipCustomerID` FOREIGN KEY (`shipCustomerID`) REFERENCES `customers` (`customerID`);

--
-- Constraints for table `lineitems`
--
ALTER TABLE `lineitems`
  ADD CONSTRAINT `FK_billID` FOREIGN KEY (`billID`) REFERENCES `bills` (`billID`),
  ADD CONSTRAINT `FK_productID` FOREIGN KEY (`productID`) REFERENCES `products` (`ProductID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
