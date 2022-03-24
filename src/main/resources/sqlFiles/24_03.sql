alter table user_t
    add role INTEGER default 0 not null;

comment on column user_t.role is '0 means normal role, 1 means admin.';

