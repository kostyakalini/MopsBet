ALTER TABLE users
    ADD firstName VARCHAR(50) NULL;

ALTER TABLE users
    ADD lastName VARCHAR(50) NULL;

ALTER TABLE users MODIFY COLUMN username VARCHAR(50) NOT NULL;

select * from users;

delete from users where id > 1;

ALTER TABLE types_of_bet DROP COLUMN value;

use mopsbetdb;

select * from bets
                  Join types_of_bet on bets.type_of_bet_id = types_of_bet.id
                  join events on bets.event_id = events.id;

SELECT * FROM user_bets
                  JOIN bets on user_bets.bet_id = bets.id
                  JOIN types_of_bet tob on tob.id = bets.type_of_bet_id
WHERE bets.event_id = 1;


select * from bets country
                  join bets cities on country.id = cities.event_id
where
        country.id = 1;

CREATE TABLE users
(
    id int(11) NOT NULL AUTO_INCREMENT,
    username varchar(45) NOT NULL,
    password varchar(64) NOT NULL,
    firstname varchar(64) NOT NULL,
    lastname varchar(64) NOT NULL,
    role varchar(45) NOT NULL,
    enabled tinyint(4) DEFAULT NULL,
    balance double not null,
    PRIMARY KEY (user_id)
);

create table bets
(
    id int auto_increment primary key,

    coefficient double not null,
    event_id int not null,
    type_of_bet_id int not null,
    value varchar(50),

    FOREIGN KEY(event_id) REFERENCES events(id),
    FOREIGN KEY(type_of_bet_id) REFERENCES types_of_bet(id)
);

create table types_of_bet
(
    id int auto_increment primary key,

    name varchar(90) not null,
    value double
);

create table user_bets
(
    id int auto_increment primary key,

    bet_id int not null,
    user_id int not null,
    value double not null,
    status varchar(50),

    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(bet_id) REFERENCES bets(id)
);

create table events
(
    id int auto_increment primary key,
    #league_id int,
    team1 varchar(50),
    team2 varchar(50)#,

    #FOREIGN KEY (league_id) REFERENCES leagues(id)
);

create table type_of_sports
(
    id int auto_increment primary key,
    name varchar(50) not null
);

INSERT types_of_bet(name)
VALUES
    ('Победа в матче'),
    ('Тотал общий больше'),
    ('Ничья'),
    ('Тотал общий меньше');

/*create table leagues
(
    id int auto_increment primary key,
    name varchar(50),
    type_of_sport_id varchar(50) not null,
    country varchar(50),
    FOREIGN KEY (type_of_sport_id) REFERENCES type_of_sports(id)
);*/


