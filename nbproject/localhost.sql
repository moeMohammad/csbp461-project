-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 30, 2025 at 04:39 PM
-- Server version: 5.7.24
-- PHP Version: 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `enrollmentdb`
--
DROP DATABASE IF EXISTS `enrollmentdb`;
CREATE DATABASE IF NOT EXISTS `enrollmentdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `enrollmentdb`;

-- --------------------------------------------------------

--
-- Table structure for table `enrollments`
--

CREATE TABLE `enrollments` (
  `enrollment_id` int(11) NOT NULL,
  `student_name` varchar(100) NOT NULL,
  `course_name` varchar(100) NOT NULL,
  `semester` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `enrollments`
--

INSERT INTO `enrollments` (`enrollment_id`, `student_name`, `course_name`, `semester`) VALUES
(1, 'Hussain', 'Internet Computing', 'Spring 2025'),
(2, 'Aziz', 'SCG', 'Fall 2022'),
(3, 'Student3', 'Course3', 'Semester3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `enrollments`
--
ALTER TABLE `enrollments`
  ADD PRIMARY KEY (`enrollment_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `enrollments`
--
ALTER TABLE `enrollments`
  MODIFY `enrollment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Database: `moviedb`
--
DROP DATABASE IF EXISTS `moviedb`;
CREATE DATABASE IF NOT EXISTS `moviedb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `moviedb`;

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `director` varchar(255) NOT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `release_year` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`id`, `title`, `director`, `genre`, `release_year`) VALUES
(1, 't', 't', 'tt', 2222),
(2, 'title', 'director', 'genre', 2020),
(3, 'title', 'director', 'genre', 2020),
(4, 'title', 'director', 'genre', 2020),
(5, 't2', 'd2', 'g2', 2021),
(6, 't3', 'd3', 'g3', 2022),
(7, 'a;lsdfja;l', 'a;sldflk;a', 'alskdjf;lak', 234523);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Database: `projectdb`
--
DROP DATABASE IF EXISTS `projectdb`;
CREATE DATABASE IF NOT EXISTS `projectdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `projectdb`;

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `post_id`, `author_id`, `content`, `created_at`) VALUES
(12, 3, 10, 'comment', '2025-04-30 16:11:15'),
(13, 3, 11, 'asdfsadfasd', '2025-04-30 16:11:44');

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`id`, `author_id`, `title`, `content`, `created_at`, `updated_at`) VALUES
(3, 10, 'test1', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus mollis tempor elit, nec faucibus magna maximus at. Pellentesque quis pharetra enim. Aenean efficitur ligula vel mi sagittis suscipit. Mauris congue vel lectus quis tempus. Fusce rhoncus metus quis augue gravida, in egestas lorem gravida. Aenean lectus quam, ultricies sed tempor ut, faucibus non dui. Quisque semper volutpat dolor ac lacinia. Sed sit amet facilisis eros, vel tristique leo. Praesent facilisis odio erat, ut posuere dui laoreet vel. Nam aliquet ipsum vitae est tincidunt, non posuere lorem pharetra. Donec vitae bibendum metus, eu aliquam orci. Donec tortor dolor, cursus quis est id, tristique dignissim nunc. Vivamus sit amet interdum velit.\r\n\r\nQuisque vulputate eros vitae ex maximus lobortis blandit sed metus. Sed a dignissim est, at sagittis mauris. Suspendisse potenti. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque erat nisi, pharetra ut blandit et, interdum ut risus. Ut a lacus quis nulla blandit placerat nec mattis dui. Donec accumsan vel velit et pharetra. Quisque sit amet augue aliquam, hendrerit sapien vel, consequat dolor. Donec rutrum tincidunt elementum. Sed varius nunc vulputate diam scelerisque accumsan. Nulla at elit massa. Nunc at urna sit amet odio fermentum gravida quis fringilla augue. Ut lobortis metus ut ullamcorper facilisis. In hac habitasse platea dictumst. Vivamus semper eros at facilisis iaculis. In venenatis pellentesque lacus, sit amet posuere dolor pharetra sit amet.\r\n\r\nDuis porttitor elementum dictum. Praesent sit amet imperdiet nulla, nec interdum mi. Duis ac molestie ex, id eleifend libero. Nulla convallis sed sapien ac venenatis. In ac porttitor lacus. Nullam suscipit nibh vel arcu ornare, tristique cursus dui luctus. Vestibulum nec ultrices libero. In consectetur massa a dui semper luctus. Maecenas euismod sodales urna, id fringilla metus dictum blandit. Maecenas vitae cursus sapien.\r\n\r\nMorbi auctor urna eu tortor ornare, posuere aliquam purus maximus. Etiam aliquet dictum magna, in feugiat mi viverra ac. Ut arcu dolor, malesuada ut augue sed, lacinia sodales nunc. Sed cursus placerat lorem sed ultrices. Sed commodo rhoncus vulputate. Donec in lectus nec tortor consequat placerat. Nulla a sem nulla. Maecenas vestibulum, ex vitae convallis tempus, ex sapien volutpat mi, sit amet efficitur dolor tellus eget nibh. Donec efficitur, quam et sagittis laoreet, libero velit lacinia ex, non vulputate est justo non eros. Quisque sit amet bibendum ex. Aenean tristique eu mauris nec laoreet.\r\n\r\nUt quis laoreet magna. Nulla nec vehicula mauris. Integer venenatis, mi nec consequat condimentum, risus massa luctus dui, non sollicitudin tortor justo congue odio. Aliquam sagittis sapien mauris, eu euismod mi maximus rhoncus. Etiam a urna non tellus euismod fringilla vestibulum ut elit. Proin eget nibh eu tortor interdum rhoncus ac sodales erat. Quisque tincidunt scelerisque ante ac mollis. Sed tincidunt velit non maximus molestie. In aliquam, elit id ornare sodales, libero elit blandit ligula, id tristique ipsum ante a augue.', '2025-04-30 16:10:47', '2025-04-30 16:10:47');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `fname` varchar(100) NOT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `bio` text,
  `profile_picture_filename` varchar(255) DEFAULT NULL COMMENT 'Filename of the uploaded profile picture',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fname`, `lname`, `email`, `password`, `bio`, `profile_picture_filename`, `created_at`) VALUES
(10, 'Hussain', 'Elemam', 'husseinhassanalemam@gmail.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', '', '5da723ab-8de2-4750-a1a0-b8082e2ce7ae.jpg', '2025-04-30 16:10:14'),
(11, 'asdfasd', 'asdfs', 'adfasd@gmail.com', '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', NULL, 'default.png', '2025-04-30 16:11:34');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `post_id` (`post_id`),
  ADD KEY `author_id` (`author_id`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `author_id` (`author_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  ADD CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
