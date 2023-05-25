create table tables."Order"
(
    id          integer default 0                             not null
        constraint "Order_pk"
            primary key,
    username    varchar default 'username'::character varying not null
        constraint "Order_Client_username_fk"
            references tables."Client" (username),
    price       integer default 0,
    description varchar default ''::character varying
);

alter table tables."Order"
    owner to postgres;

