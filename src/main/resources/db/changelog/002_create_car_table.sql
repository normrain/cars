CREATE TABLE cars
(
    id           SERIAL PRIMARY KEY,
    user_id      INT     NOT NULL,
    make         VARCHAR not null,
    model        VARCHAR not null,
    number_plate VARCHAR not null
);