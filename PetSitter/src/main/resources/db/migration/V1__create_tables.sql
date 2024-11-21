create table if not exists Users(
    id         int primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null
);

create table if not exists PetSitterAnnouncement (
    id             bigserial primary key,
    user_id        bigint references Users(id),
    description    text,
    attendance_in  boolean not null,
    attendance_out boolean not null,
    with_dog       boolean not null,
    with_cat       boolean not null,
    with_other     boolean not null,
    is_vet         boolean not null,
    location       varchar(255) not null,
);

create table if not exists ConsumerAnnouncement (
    id            bigserial primary key,
    begin_date    date not null,
    end_date      date not null,
    customer_id   bigint references Users(id),
    location      varchar(255) not null,
    description   text,
    attendance_in boolean not null,
    with_dog       boolean not null,
    with_cat       boolean not null,
    with_other     boolean not null,
);

create table if not exists Orders (
    id              bigserial primary key,
    sitter_id       bigint references Users(id),
    announcement_id bigint references Announcement(id),
    status          varchar(64) not null
);

create table if not exists Review (
    id       bigserial primary key,
    order_id bigint references Orders(id),
    score    int not null,
    comment  text
);
