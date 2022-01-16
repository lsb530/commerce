create table if not exists items
(
    item_id bigint auto_increment
        primary key,
    created_date datetime(6) null,
    modified_date datetime(6) null,
    name varchar(255) null,
    price int not null
);

create index idx_name
    on items (name);

create table if not exists users
(
    user_id bigint auto_increment
        primary key,
    created_date datetime(6) null,
    modified_date datetime(6) null,
    email varchar(255) null,
    password varchar(255) null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);

create table if not exists orders
(
    order_id bigint auto_increment
        primary key,
    item_id bigint null,
    user_id bigint null,
    constraint FK247nnxschdfm8lre0ssvy3k1r
        foreign key (item_id) references items (item_id),
    constraint FK32ql8ubntj5uh44ph9659tiih
        foreign key (user_id) references users (user_id)
);