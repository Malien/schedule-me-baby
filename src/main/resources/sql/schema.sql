create table if not exists students
(
    id bigint auto_increment,
    name varchar(128) not null,
    constraint STUDENTS_PK
        primary key (id)
);

create table if not exists subjects
(
    id bigint auto_increment,
    name varchar(128) not null,
    constraint SUBJECTS_PK
        primary key (id)
);

create table if not exists teachers
(
    id bigint auto_increment,
    name varchar(128) not null,
    constraint TEACHERS_PK
        primary key (id)
);

create table if not exists groups
(
    id bigint auto_increment,
    subject_id bigint not null,
    teacher_id bigint not null,
    number int not null,
    type int not null,
    constraint GROUPS_PK
        primary key (id),
    constraint GROUPS_SUBJECTS_ID_FK
        foreign key (subject_id) references SUBJECTS
            on update cascade,
    constraint GROUPS_TEACHERS_ID_FK
        foreign key (teacher_id) references TEACHERS
            on update cascade
);

create table if not exists students_groups
(
    student_id bigint,
    group_id bigint,
    constraint STUDENTS_GROUPS_PK
        primary key (student_id, group_id),
    constraint STUDENTS_GROUPS_GROUPS_ID_FK
        foreign key (group_id) references GROUPS
            on update cascade,
    constraint STUDENTS_GROUPS_STUDENTS_ID_FK
        foreign key (student_id) references STUDENTS
            on update cascade
);

create table if not exists timeslots
(
    id bigint auto_increment,
    group_id bigint not null,
    day int not null,
    class int not null,
    auditorium varchar(256) not null,
    weeks array not null,
    constraint TIMESLOTS_PK
        primary key (id),
    constraint TIMESLOTS_GROUPS_ID_FK
        foreign key (group_id) references GROUPS
            on update cascade
);
