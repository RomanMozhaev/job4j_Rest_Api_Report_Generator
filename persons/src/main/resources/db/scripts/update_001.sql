create table person
(
    id       SERIAL PRIMARY KEY,
    login    character varying(255) NOT NULL UNIQUE,
    password character varying(255) NOT NULL

);

insert into person (login, password)
values ('Peter', '123');
insert into person (login, password)
values ('Ivan', '123');
insert into person (login, password)
values ('Jack', '123');