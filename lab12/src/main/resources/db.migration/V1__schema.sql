create table outbox
(
    event_id   uuid primary key,
    status     text not null,
    created_at timestamp with time zone
);