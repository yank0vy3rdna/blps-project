create table user_t
(
    id       serial
        primary key,
    username varchar(16)  not null,
    password varchar(256) not null
);

create unique index user_t_username_uindex
    on user_t (username);

create table project
(
    id             serial
        primary key,
    target_amount  integer default 0,
    current_amount integer default 0,
    name           varchar(32),
    description    text,
    status         integer default 0 not null
);

create table initiator_project
(
    user_id    integer not null
        constraint "FK_initiator_project.user_id"
            references user_t,
    project_id integer not null
        constraint "FK_initiator_project.project_id"
            references project,
    primary key (user_id, project_id)
);

create table back_record
(
    id         serial
        primary key,
    user_id    integer
        constraint "FK_back_record.user_id"
            references user_t,
    project_id integer
        constraint "FK_back_record.project_id"
            references project,
    amount     integer
);

create table backer_project
(
    user_id    integer not null
        constraint "FK_backer_project.user_id"
            references user_t,
    project_id integer not null
        constraint "FK_backer_project.project_id"
            references project,
    primary key (user_id, project_id)
);

