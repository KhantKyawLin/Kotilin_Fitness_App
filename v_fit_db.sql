-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 30, 2025 at 06:47 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `v_fit_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `username` varchar(40) NOT NULL,
  `useremail` varchar(40) NOT NULL,
  `userpassword` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `username`, `useremail`, `userpassword`) VALUES
(5, 'Khant Kyaw Lin', 'kkl@gmail.com', 'kkl11111'),
(6, 'Win Myat Mon', 'wmm@gmail.com', 'wmm11111'),
(7, 'Zwe Hpone Khant', 'zhk@gmail.com', 'zhk11111');

-- --------------------------------------------------------

--
-- Table structure for table `workout`
--

CREATE TABLE `workout` (
  `workoutID` int(11) NOT NULL,
  `workoutType` varchar(40) NOT NULL,
  `workoutDate` text NOT NULL,
  `workoutDay` int(11) NOT NULL,
  `workoutMonth` int(11) NOT NULL,
  `workoutYear` int(11) NOT NULL,
  `workoutTime` text DEFAULT NULL,
  `indoor_outdoor` text DEFAULT NULL,
  `equipments` text DEFAULT NULL,
  `distance` float DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `workoutWeight` float DEFAULT NULL,
  `remark` text DEFAULT NULL,
  `userID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `workout`
--

INSERT INTO `workout` (`workoutID`, `workoutType`, `workoutDate`, `workoutDay`, `workoutMonth`, `workoutYear`, `workoutTime`, `indoor_outdoor`, `equipments`, `distance`, `duration`, `workoutWeight`, `remark`, `userID`) VALUES
(5, 'Running', '22/4/2025', 22, 4, 2025, '05:00 PM', 'Outdoor', ', , ', 3, 30, 52, 'Just Jogging', 5),
(6, 'Lifting Weight', '25/4/2025', 25, 4, 2025, '05:00 PM', 'Indoor', ', Dumbbell, ', 0, 30, 50, '2 sets for 10 reps', 5),
(7, 'Cycling', '29/3/2025', 29, 3, 2025, '05:00 PM', 'Outdoor', ', , ', 30, 45, 56, '', 5),
(8, 'Walking', '16/4/2025', 16, 4, 2025, '07:00 AM', 'Outdoor', ', , ', 10, 45, 42, '', 6),
(9, 'Swimming', '1/5/2025', 1, 5, 2025, '04:00 PM', 'Indoor', ', , ', 0, 50, 42, 'Just Regular Swimming', 6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `workout`
--
ALTER TABLE `workout`
  ADD PRIMARY KEY (`workoutID`),
  ADD KEY `userID` (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `workout`
--
ALTER TABLE `workout`
  MODIFY `workoutID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `workout`
--
ALTER TABLE `workout`
  ADD CONSTRAINT `workout_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
