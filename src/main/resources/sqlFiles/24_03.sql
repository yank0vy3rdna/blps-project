alter table user_t
    add role INTEGER default 0 not null;

comment on column user_t.role is '0 means normal role, 1 means admin.';

ALTER TABLE user_t
    ADD CHECK (role = 0 OR role = 1);

alter table project add check ( status = 0 or status = 1 or status = 2 );