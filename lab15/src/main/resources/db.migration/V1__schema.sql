create table users
(
    user_id uuid primary key,
    "name"  text not null,
    email   text not null,
    address text
);
