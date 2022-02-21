insert into user_t (login, password)
values ('Andrey', '123456'),
       ('siyuan', '123456'),
       ('sergey', '123456'),
       ('olya', '123456'),
       ('motherFunner', '123456');

-- Please delete field initiator_id in table project.
-- We have initiator_project so we don't need that field.
alter table project
    drop column initiator_id;


-- I think project at the least need name and description.
alter table project
    add name varchar(32);

alter table project
    add description text;

insert into project (name, target_amount, current_amount, description)
values ('Pornhub builder', 1000000, 0, 'Let''s build a site for free porn!!!'),
       ('ITMO Gang', 9999999, 123456,
        'Do you wanna do anything in ITMO? Let''s join ITMO GANG! REAL GTA5 LIFE IN ITMO!'),
       ('Keyboard project', 100000, 10,
        'Do you want to type fluently and find out what kind of mechanical keyboard you will like? Give me money! Right now!'),
       ('Pay me for no reason', 10000000, 0,
        'I don''t why! JUST GIVE ME YOUR MONEY! YOU MOTHER GOOD BOY! SON OF BEACH!');

insert into initiator_project(user_id, project_id)
VALUES (4, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (2, 3),
       (5, 4);

insert into backer_project(user_id, project_id)
VALUES (1, 1),
       (1, 2),
       (4, 1),
       (2, 2),
       (3, 2),
       (2, 3),
       (5, 4);

insert into back_record(user_id, project_id, amount)
VALUES (4, 2, 1000),
       (1, 3, 1000),
       (2, 3, 500),
       (4, 3, 4000),
       (5, 1, 1000),
       (1, 3, 1000),
       (1, 3, 1000),
       (5, 1, 1000),
       (4, 3, 1000),
       (3, 2, 1000),
       (1, 2, 1000),
       (4, 4, 1000);