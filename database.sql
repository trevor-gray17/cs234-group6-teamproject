Drop DATABASE if Exists roster;
CREATE DATABASE IF NOT EXISTS roster;
USE roster;

CREATE TABLE IF NOT EXISTS Player (
    PlayerID INT AUTO_INCREMENT PRIMARY KEY,
    number INT NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    year INT
);

CREATE TABLE IF NOT EXISTS Stats_1 (
    StatID INT AUTO_INCREMENT PRIMARY KEY,
    practice_date VARCHAR(255),
    playerName VARCHAR(255),
    threePointersTaken INT,
    threePointersMade INT,
    freeThrowsTaken INT,
    freeThrowsMade INT,
    PlayerID INT,
    FOREIGN KEY (PlayerID) REFERENCES Player(PlayerID)
);
