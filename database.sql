Create database if not exists shootingstats;
use shootingstats;

Create table if not exists Player (
    String name,
    int number,
    String position
    Boolean active,
    int year
    PRIMARY KEY (name, number),
);
Create table if not exists Stats_1 (int threePointersTaken,int threePointersMade, int freeThrowsTaken , Select name from Player , Date practice_date);

