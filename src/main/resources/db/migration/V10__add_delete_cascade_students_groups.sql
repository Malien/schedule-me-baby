alter table students_groups
    drop constraint students_groups_students_id_fk,
    add constraint students_groups_students_id_fk
        foreign key (student_id)
            references users
            on update cascade on delete cascade;

alter table students_groups
    drop constraint students_groups_groups_id_fk,
    add constraint students_groups_groups_id_fk
        foreign key (group_id)
            references groups
            on update cascade on delete cascade;