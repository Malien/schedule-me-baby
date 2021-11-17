create table if not exists roles
(
    role_id int8 generated by default as identity,
    role_name    varchar(255) not null,
    primary key (role_id),
    constraint unique_role_name unique (role_name)
);

create table if not exists user_roles
(
    user_id int8 not null references users(id) on UPDATE CASCADE,
    role_id int8 not null references roles(role_id) on UPDATE CASCADE,
    primary key (user_id, role_id),
    constraint fk_role_id_user_roles foreign key (role_id) references roles,
    constraint fk_user_id_user_roles foreign key (user_id) references users
);

INSERT INTO roles (role_id, role_name)
VALUES (1, 'ADMIN'), (2, 'USER');

INSERT INTO users (login, password)
VALUES ('admin', '${admin_password}');

INSERT INTO user_roles
VALUES ((SELECT id FROM users WHERE login = 'admin'), 1);