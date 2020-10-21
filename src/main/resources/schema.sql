drop table if exists ANSWERS;
drop table if exists QUESTIONS;

CREATE TABLE QUESTIONS (
    ID bigserial,
    TEXT varchar(250),
    PRIORITY int,
    primary key (id)
);
CREATE TABLE ANSWERS (
    ID bigserial,
    TEXT varchar(250),
    PRIORITY varchar(250),
    IS_CORRECT int,
    QUESTION_ID bigint references QUESTIONS (id),
    primary key (id)
);
