# University Management

The goal of this project is to create a minimal version of an IT system for a university.

## Requirements

Within the context of a university many things can happen. In order to keep things viable,
we want to focus on the following aspects:

- Courses
- Students
- Professors
- Grades

### Authentication / Authorization

Each user (Student / Professor) has to have a password. Password shall not be stored in plain text
but rather should make use of a hashing algorithm to safely store secret information in the database.

Upon successful authentication, a user session gets created. The session includes the user ID
as well as all the roles that the user owns.

The following roles exist:

- professor
- student
- assistant

### Students

Students can enroll in courses and receive grades. Some students can also be
elected by a Professor to become a course assistant.

In order to become a course assisant, a student has to be currently enrolled in the respective course.

Assistants have some special rights: they can grade other students within the course.
Because great power comes with great responsibility, assistants are rewarded. 

When getting graded, an assistant always receives `grade + 1` except for when the `grade = 5`.

### Professors

Professors hold multiple courses. They can create new courses 
and grade students for courses that they hold.

Professors have an ID (length = 7) and a name.

### Courses

During their studies, students partake in a lot of courses.
Such courses typically have a name, are led by a specific professor
and take place during a particular semester in a given year.

In order to participate in a course, students have to manually enroll
in the course.
### Grades

After completing a course, students get assigned a grade.
This assignment gets done by the professor that runs the respective course.
Only students that are actually enrolled in a course should be able
to receive a grade for this specific course.

The following grades exist:

- Excellent (1)
- Good (2)
- Satisfactory (3)
- Pass (4)
- Fail (5)

## Command Line Interface (CLI)

### Start Screen

```
UNIVERSITY MANAGEMENT SYSTEM
-----------------------------
1) Login
2) Exit
```

### Login Screen

```
LOGIN
-----------------------------
ID: <user input>
Password: <user input>
```

#### Login Failed Screen
```
LOGIN
-----------------------------
ID: <user input>
Password: <user input>
ERROR: Login Failed
$ _
```

### Menu (Student)
```
MENU
-----------------------------
1) List currently enrolled courses
2) Enroll in a course
3) List grades

Your Choice: <user input>
```

### Menu (Professor)
```
MENU
-----------------------------
1) List courses held and enrolled students
2) Create a new course
3) Grade a student

Your Choice: <user input>
```

## Architecture

### Data Access

To enable data access we're using the [Repository Pattern](http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx).
This allows us to hide database specific implementation details from the other layers of the system.

### Business Logic

Our business logic (e.g. enrolling a student in a course) lives in Commands and / or Queries.

A Command holds all necessary data as well as dependencies in order to perform its action correctly.

A Query allows us to retrieve data.