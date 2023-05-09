CREATE TABLE student
(
    id            BIGSERIAL,
    first_name    VARCHAR(50)                         NOT NULL,
    last_name     VARCHAR(50)                         NOT NULL,
    email         VARCHAR(100)                        NOT NULL,
    date_of_birth DATE                                NOT NULL,
    degree        VARCHAR(20) DEFAULT 'UNDERGRADUATE' NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE            NOT NULL,
    modified_at   TIMESTAMP WITH TIME ZONE,
    CONSTRAINT student_pk PRIMARY KEY (id),
    CONSTRAINT student_email_unique UNIQUE (email)
);

CREATE TABLE instructor
(
    id          BIGSERIAL,
    first_name  VARCHAR(50)              NOT NULL,
    last_name   VARCHAR(50)              NOT NULL,
    email       VARCHAR(100)             NOT NULL,
    salary      NUMERIC                  NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT instructor_pk PRIMARY KEY (id),
    CONSTRAINT instructor_email_unique UNIQUE (email)
);

CREATE TABLE course
(
    id            BIGSERIAL,
    name          VARCHAR(50)              NOT NULL,
    ects          SMALLINT                 NOT NULL,
    price         NUMERIC                  NOT NULL,
    status        VARCHAR(50)              NOT NULL,
    start_date    TIMESTAMP WITH TIME ZONE NOT NULL,
    instructor_id BIGINT,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at   TIMESTAMP WITH TIME ZONE,
    CONSTRAINT course_pk PRIMARY KEY (id),
    CONSTRAINT course_name_unique UNIQUE (name),
    CONSTRAINT course_instructor_id_fk FOREIGN KEY (instructor_id) REFERENCES instructor (id)
);

CREATE TABLE student_course
(
    student_id  BIGINT                   NOT NULL,
    course_id   BIGINT                   NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT student_course_pk PRIMARY KEY (student_id, course_id),
    CONSTRAINT student_course_student_id_fk FOREIGN KEY (student_id) REFERENCES student (id),
    CONSTRAINT student_course_course_id_fk FOREIGN KEY (course_id) REFERENCES course (id)
);