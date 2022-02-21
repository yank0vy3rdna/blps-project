CREATE TABLE user_t
(
    id       serial,
    login    varchar(16),
    password varchar(16),
    PRIMARY KEY (id)
);

CREATE TABLE project
(
    id             serial,
    name           varchar(32),
    target_amount  integer,
    current_amount integer,
    description    text,
    PRIMARY KEY (id)
);

CREATE TABLE initiator_project
(
    user_id    integer,
    project_id integer,
    PRIMARY KEY (user_id, project_id),
    CONSTRAINT "FK_initiator_project.project_id"
        FOREIGN KEY (project_id)
            REFERENCES project (id),
    CONSTRAINT "FK_initiator_project.user_id"
        FOREIGN KEY (user_id)
            REFERENCES user_t (id)
);

CREATE TABLE back_record
(
    id         serial,
    user_id    integer,
    project_id integer,
    amount     integer,
    PRIMARY KEY (id),
    CONSTRAINT "FK_back_record.project_id"
        FOREIGN KEY (project_id)
            REFERENCES project (id),
    CONSTRAINT "FK_back_record.user_id"
        FOREIGN KEY (user_id)
            REFERENCES user_t (id)
);

CREATE TABLE backer_project
(
    user_id    integer,
    project_id integer,
    PRIMARY KEY (user_id, project_id),
    CONSTRAINT "FK_backer_project.project_id"
        FOREIGN KEY (project_id)
            REFERENCES project (id),
    CONSTRAINT "FK_backer_project.user_id"
        FOREIGN KEY (user_id)
            REFERENCES user_t (id)
);

