drop table if exists student_course;
drop table if exists course;
drop table if exists student;
drop table if exists degree_program;
drop table if exists professor;
-- add drop tables

-- TODO: professor
create table professor (
        --Matrikelnummer
        professor_id bigserial primary key,
        name varchar(64) not null,
        birth_date date not null,
        password varchar(50) not null,
        is_admin boolean
);

alter sequence professor_professor_id_seq restart with 1000000;

-- Studiengang
create table degree_program (
    program_id serial primary key,
    name varchar(128) not null unique
);

create table student (
    -- Matrikelnummer
    student_id bigserial primary key,
    name varchar(64) not null,
    birth_date date not null,
    enrolled_in int references degree_program(program_id),
    enrolled_since date not null default now(),
    password varchar(50) not null,
    is_course_assistant boolean
);

alter sequence student_student_id_seq restart with 10000000;

create table course (
    course_id serial primary key,
    name varchar(128) not null unique,
    assigned_professor bigint references professor(professor_id)
);

create table student_course (
    fk_student_id bigint references student(student_id),
    fk_course_id int references course(course_id),
    grade int check (grade >= 1 and grade <= 5),
    constraint student_course_pkey primary key (fk_student_id, fk_course_id)
);


insert into degree_program(name) values('Wirtschaftsinformatik');
insert into degree_program(name) values('Software & Information Engineering');
insert into degree_program(name) values('Medieninformatik');
insert into degree_program(name) values('Technische Informatik');
insert into degree_program(name) values('Medizinische Informatik');
insert into degree_program(name) values('Maschinenbau');
insert into degree_program(name) values('Elektrotechnik');

insert into course(name) values('Algebra und diskrete Mathematik');
insert into course(name) values('Einführung in die Programmierung');
insert into course(name) values('Denkweisen der Informatik');
insert into course(name) values('Analysis');
insert into course(name) values('Grundlagen der Organisation');
insert into course(name) values('Rechnungswesen');
insert into course(name) values('Technische Grundlagen der Informatik');

insert into student(name, birth_date, enrolled_in, enrolled_since, password, is_course_assistant)
values('Benjamin Weber', '2002-02-27', 1, '2021-01-01', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', false);
