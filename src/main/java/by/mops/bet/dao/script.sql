create table events
(
    id     int auto_increment
        primary key,
    team1  varchar(50) null,
    team2  varchar(50) null,
    status varchar(50) not null,
    result varchar(3)  null,
    total  int         null
);

create table types_of_bet
(
    id   int auto_increment
        primary key,
    name varchar(90) not null
);

create table bets
(
    id             int auto_increment
        primary key,
    coefficient    double      not null,
    event_id       int         not null,
    type_of_bet_id int         not null,
    value          varchar(50) null,
    constraint bets_ibfk_1
        foreign key (event_id) references events (id)
            on update cascade on delete cascade,
    constraint bets_ibfk_2
        foreign key (type_of_bet_id) references types_of_bet (id)
            on delete cascade
);

create table users
(
    id        int auto_increment
        primary key,
    username  varchar(45) not null,
    password  varchar(64) not null,
    role      varchar(45) not null,
    enabled   tinyint     null,
    balance   double      null,
    firstname varchar(50) null,
    lastname  varchar(50) null
);

create table user_bets
(
    id      int auto_increment
        primary key,
    bet_id  int         not null,
    value   double      not null,
    status  varchar(50) not null,
    user_id int         not null,
    constraint user_bets_bets__fk
        foreign key (bet_id) references bets (id)
            on update cascade on delete cascade,
    constraint user_bets_users_id_fk
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create index user_bets_users__fk
    on user_bets (user_id);