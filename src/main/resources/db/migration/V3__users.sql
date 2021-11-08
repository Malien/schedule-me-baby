CREATE TABLE users
(
    id       bigint auto_increment,
    login    varchar(32) not null,
    password VARCHAR(32) not null,
    constraint USERS_PK
        primary key (id)
);

create unique index users_login_uindex
    on users (login);
