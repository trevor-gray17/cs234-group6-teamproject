CREATE DATABASE IF NOT EXISTS roster;
USE roster;

CREATE TABLE IF NOT EXISTS Player (
    PlayerID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    number INT NOT NULL,
    position VARCHAR(50),
    active BOOLEAN,
    year INT,
);

CREATE TABLE IF NOT EXISTS Stats_1 (
    StatID INT AUTO_INCREMENT PRIMARY KEY,
    threePointersTaken INT,
    threePointersMade INT,
    freeThrowsTaken INT,
    freeThrowsMade INT,
    playerName VARCHAR(255),
    practice_date DATE,
    PlayerID INT,
    FOREIGN KEY (PlayerID) REFERENCES Player(PlayerID) ON DELETE CASCADE
);
