alter table user_bets
    add constraint user_bets_users__fk
        foreign key (user_id) references users (id);