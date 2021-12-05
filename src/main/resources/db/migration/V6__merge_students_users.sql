delete from students;
drop table user_roles;
drop table users;

alter table students
    add column login varchar(32) not null,
    add column password varchar(32) not null;

alter table students rename column student_id to user_id;
alter table students rename to users;

create table user_roles (
    user_id int8 not null references users(user_id) on UPDATE CASCADE,
    role_id int8 not null references roles(role_id) on UPDATE CASCADE,
    primary key (user_id, role_id),
    constraint fk_role_id_user_roles foreign key (role_id) references roles,
    constraint fk_user_id_user_roles foreign key (user_id) references users
);