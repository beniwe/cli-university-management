drop table if exists student;
drop table if exists degree_program;
-- add drop tables

-- TODO: professor
create table professor (
        --Matrikelnummer
        professor_id varchar(7) primary key check (length(id) = 7),
        name varchar(64) not null,
        birth_date date not null
        is_admin boolean
);

-- Studiengang
create table degree_program (
    program_id serial primary key,
    name varchar(128)
);

create table student (
    -- Matrikelnummer
    student_id varchar(8) primary key check (length(id) = 8),
    name varchar(64) not null,
    birth_date date not null,
    enrolled_in serial references degree_program(id),
    enrolled_since date not null default now(),
    password varchar(50) not null,
    is_course_assistant boolean
);

create table courses (
    course_id serial primary key,
    name varchar(128) not null,
    assigned_professor varchar(7) references professor(id)
);

create table student_courses (
    fk_student_id varchar(8) references student(id),
    fk_course_id int references course(id),
    grade int check (grade >= 1 and grade <= 5),
    constraint student_course_pkey primary key (student_id, course_id)
);


insert into degree_program(program_id, name) values(1, 'Wirtschaftsinformatik');
insert into degree_program(program_id, name) values(2, 'Software & Information Engineering');
insert into degree_program(program_id, name) values(3, 'Medieninformatik');
insert into degree_program(program_id, name) values(4, 'Technische Informatik');
insert into degree_program(program_id, name) values(5, 'Medizinische Informatik');
insert into degree_program(program_id, name) values(6, 'Maschinenbau');
insert into degree_program(program_id, name) values(7, 'Elektrotechnik');

insert into student(student_id, name, birth_date, enrolled_in, enrolled_since, password, course_assistant)
    values('11940303', 'Benjamin Auinger', '1999-04-29', 1, '2020-04-01', '123', false);
insert into student(student_id, name, birth_date, enrolled_in, enrolled_since, password, course_assistant)
    values('12025956', 'Benjamin Weber', '2002-02-27', 1, '2020-09-01', '123', false);
insert into student(student_id, name, birth_date, enrolled_in, enrolled_since, password, course_assistant)
    values('11244343', 'Nicolas Eder', '1999-01-01', 3, '2020-09-01', '123', false);
insert into student(student_id, name, birth_date, enrolled_in, enrolled_since, password, course_assistant)
    values('11230803', 'Kristof Cserpes', '1999-01-01', 4, '2020-09-01', '123', false);
