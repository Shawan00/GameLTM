CREATE TABLE `user` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `numberOfGame` int DEFAULT '0',
  `numberOfWin` int DEFAULT '0',
  `numberOfDraw` int DEFAULT '0',
  `IsOnline` int DEFAULT '0',
  `IsPlaying` int DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `pikachu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `avatar` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `history` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idUser1` int NOT NULL,
  `idUser2` int NOT NULL,
  `scoreUser1` int NOT NULL,
  `scoreUser2` int NOT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `friend` (
  `ID_User1` int NOT NULL,
  `ID_User2` int NOT NULL,
  PRIMARY KEY (`ID_User1`,`ID_User2`),
  KEY `ID_User2` (`ID_User2`),
  CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`ID_User1`) REFERENCES `user` (`ID`),
  CONSTRAINT `friend_ibfk_2` FOREIGN KEY (`ID_User2`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `banned_user` (
  `ID_User` int NOT NULL,
  PRIMARY KEY (`ID_User`),
  CONSTRAINT `banned_user_ibfk_1` FOREIGN KEY (`ID_User`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
