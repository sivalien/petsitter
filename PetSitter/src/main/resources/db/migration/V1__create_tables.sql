drop table review;
drop table order_history;
drop table orders;
drop table sitter;
drop table customer;
drop table users;


create table if not exists users(
    id         bigserial primary key,
    login      varchar(255) unique not null,
    password   varchar(255) not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    contacts   varchar(255) not null
);

create table if not exists sitter (
    id             bigserial primary key,
    user_id        bigint references users(id),
    title          varchar(255) not null,
    location       varchar(255) not null,
    description    text,
	is_vet         boolean not null,
    attendance_in  boolean not null,
    attendance_out boolean not null,
	with_dog       boolean not null,
	with_cat       boolean not null,
	with_other     boolean not null
);

create table if not exists customer (
    id            bigserial primary key,
    user_id   bigint references users(id),
    title          varchar(255) not null,
    location      varchar(255) not null,
	description   text,
    begin_date    date not null,
    end_date      date not null,
    attendance_in  boolean not null,
    attendance_out boolean not null,
	with_dog boolean not null,
	with_cat boolean not null,
	with_other boolean not null,
	available boolean default true not null
);

create table if not exists orders (
    id              bigserial primary key,
    sitter_id       bigint references sitter(id),
    customer_id     bigint references customer(id) on delete cascade,
    status          varchar(64) not null,
    created         timestamp not null,
    updated         timestamp not null,
    updated_by      bigint references users(id)
);

create table if not exists order_history (
	id                   bigserial primary key,
	customer_id          bigint references users(id),
	sitter_id            bigint references sitter(id),
	customer_title       varchar(255) not null,
	location             varchar(255) not null,
	customer_description varchar(255) not null,
	begin_date           date not null,
    end_date             date not null,
	attendance_in        boolean not null,
    attendance_out       boolean not null,
    with_dog             boolean not null,
    with_cat             boolean not null,
    with_other           boolean not null
);

create table if not exists review (
    sitter_id   bigint references sitter(id),
    customer_id bigint references users(id),
    score       int not null,
    comment     text,
    primary key (sitter_id, customer_id)
);
