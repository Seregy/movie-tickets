USE `movietickets`;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `Cinema` (
  `id` binary(16) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `website` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `city_id` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `City` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Hall` (
  `id` binary(16) NOT NULL,
  `rowsAmount` int(11) NOT NULL,
  `seatTypes` longblob,
  `seatsAmount` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `cinema_id` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Movie` (
  `id` binary(16) NOT NULL,
  `annotation` longtext,
  `cast` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `contentRating` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `director` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `duration` int(11) NOT NULL,
  `genres` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `premiereEndDate` date DEFAULT NULL,
  `screeningDate` date DEFAULT NULL,
  `year` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Permission` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Role` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Role_Permission` (
  `roles_id` binary(16) NOT NULL,
  `permissions_id` binary(16) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Seat` (
  `id` binary(16) NOT NULL,
  `price` int(11) NOT NULL,
  `rowNumber` int(11) NOT NULL,
  `seatNumber` int(11) NOT NULL,
  `seatStatus` int(11) DEFAULT NULL,
  `seatType` int(11) DEFAULT NULL,
  `session_id` binary(16) DEFAULT NULL,
  `ticket_id` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Session` (
  `id` binary(16) NOT NULL,
  `defaultPrice` int(11) NOT NULL,
  `sessionStart` datetime DEFAULT NULL,
  `technology` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `hall_id` binary(16) DEFAULT NULL,
  `movie_id` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Ticket` (
  `id` binary(16) NOT NULL,
  `seat_id` binary(16) DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

CREATE TABLE `User` (
  `id` binary(16) NOT NULL,
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `role_id` binary(16) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

ALTER TABLE `Cinema`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1e4o7nfxe8limuiuh91nbo6ko` (`city_id`);

ALTER TABLE `City`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `Hall`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK38tpcvut2ra2ipbb6795exd12` (`cinema_id`);

ALTER TABLE `Movie`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `Permission`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `Role`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `Role_Permission`
  ADD PRIMARY KEY (`roles_id`,`permissions_id`),
  ADD KEY `FKfyklb4wucbjtbga57tgvg44ec` (`permissions_id`);

ALTER TABLE `Seat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7mdoky6xqsu3w0dt5l5g2bl8e` (`session_id`),
  ADD KEY `FKse0v58w0d82blivngeb1hx4m9` (`ticket_id`);

ALTER TABLE `Session`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8k4yqj0h0yl8x6ssh80d5mjhd` (`hall_id`),
  ADD KEY `FK4024w1wiven074754k28bnmtp` (`movie_id`);

ALTER TABLE `Ticket`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKokajjvg4ha2ltd3xqhs7qjb29` (`seat_id`),
  ADD KEY `FKm1xvucrvrnk55qhem8xmvju6h` (`user_id`);

ALTER TABLE `User`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK84qlpfci484r1luck11eno6ec` (`role_id`);

  
INSERT INTO `Permission` (`id`, `name`) VALUES
(0x328de01bb5e047c284917c16c871492a, 'PM_ADD_PERMISSION_ALL'),
(0xc713937662f44e5d919daa5f5a13198b, 'PM_EDIT_PERMISSION_ALL'),
(0xaa0418ce34a54606809892329853602f, 'PM_DELETE_PERMISSION_ALL'),
(0x065cbcd50a704b6b936f18383c40df22, 'PM_ADD_ROLE_ALL'),
(0x4c270a9799d94b95a2189de70e567e2d, 'PM_EDIT_ROLE_ALL'),
(0x962f42370a5945f480fdd0593dd9afd6, 'PM_DELETE_ROLE_ALL'),
(0x89d116b6831c4ab5b584854223919835, 'PM_EDIT_USER_ALL'),
(0x4d7f9b585aec4a01a89996e7d401431b, 'PM_DELETE_USER_ALL');

INSERT INTO `Role` (`id`, `name`) VALUES
(0x2662aa74a57c4027be0fcbc48b099c3f, 'User'),
(0xe682eebb3eb944fd9271c356eaa5e904, 'Admin');

INSERT INTO `Role_Permission` (`roles_id`, `permissions_id`) VALUES
(0xe682eebb3eb944fd9271c356eaa5e904, 0x065cbcd50a704b6b936f18383c40df22),
(0xe682eebb3eb944fd9271c356eaa5e904, 0x328de01bb5e047c284917c16c871492a),
(0xe682eebb3eb944fd9271c356eaa5e904, 0x4c270a9799d94b95a2189de70e567e2d),
(0xe682eebb3eb944fd9271c356eaa5e904, 0x4d7f9b585aec4a01a89996e7d401431b),
(0xe682eebb3eb944fd9271c356eaa5e904, 0x89d116b6831c4ab5b584854223919835),
(0xe682eebb3eb944fd9271c356eaa5e904, 0x962f42370a5945f480fdd0593dd9afd6),
(0xe682eebb3eb944fd9271c356eaa5e904, 0xaa0418ce34a54606809892329853602f),
(0xe682eebb3eb944fd9271c356eaa5e904, 0xc713937662f44e5d919daa5f5a13198b);

INSERT INTO `User` (`id`, `email`, `name`, `password`, `role_id`) VALUES
(0x7f1d59ad588f4828a8fc0c074ffb51ae, 'admin@mail.com', 'admin', '$2a$10$mGq6qqRPH7c8nA5Sfa32ee7hCogbHRlE54FEogTbkd.44/L.mngSa', 0xe682eebb3eb944fd9271c356eaa5e904);
COMMIT;