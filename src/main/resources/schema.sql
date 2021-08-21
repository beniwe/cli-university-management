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
    ects decimal(3,1) not null,
    assigned_professor bigint references professor(professor_id)
);

create table student_course (
    fk_student_id bigint references student(student_id),
    fk_course_id int references course(course_id),
    is_course_assistant boolean not null,
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

insert into professor(name, birth_date, password, is_admin)
values('Bernhard Gittenberger', '1800-01-01', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', true);


insert into student(name, birth_date, enrolled_in, enrolled_since, password, is_course_assistant)
values('Benjamin Weber', '2002-02-27', 1, '2021-01-01', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', true);

insert into student(name, birth_date, enrolled_in, enrolled_since, password, is_course_assistant)
values('Elias Pinter', '2002-06-02', 2, '2021-01-01', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', false);

insert into student(name, birth_date, enrolled_in, enrolled_since, password, is_course_assistant)
values('Thomas Mayerl', '2000-08-01', 4, '2021-01-01', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', true);

insert into student(name, birth_date, enrolled_in, enrolled_since, password, is_course_assistant)
values('Benjamin Auinger', '1998-05-23', 1, '2021-01-01', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', false);


insert into course(name, ects, assigned_professor) values('Algebra und diskrete Mathematik', 8.0, 1000000);
insert into course(name, ects) values('Einführung in die Programmierung', 5.5);
insert into course(name, ects) values('Denkweisen der Informatik', 3.0);
insert into course(name, ects, assigned_professor) values('Analysis', 6.5, 1000000);
insert into course(name, ects) values('Grundlagen der Organisation', 3.0);
insert into course(name, ects) values('Rechnungswesen', 2.0);
insert into course(name, ects) values('Technische Grundlagen der Informatik', 5.5);
insert into course(name, ects, assigned_professor) values('Statistik', 6.0, 1000000);


insert into student_course(fk_student_id, fk_course_id, is_course_assistant, grade)
values(10000000, 1, true, 3);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant, grade)
values(10000000, 3, false, 2);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant, grade)
values(10000000, 5, false, 1);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant)
values(10000000, 6, true);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant)
values(10000000, 8, false);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant)
values(10000001, 1, false);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant)
values(10000001, 8, false);


insert into student_course(fk_student_id, fk_course_id, is_course_assistant)
values(10000002, 1, true);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant)
values(10000002, 8, false);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant)
values(10000003, 1, false);

insert into student_course(fk_student_id, fk_course_id, is_course_assistant)
values(10000003, 8, false);



