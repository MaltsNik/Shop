CREATE TABLE IF NOT EXISTS orders
(
    id                 bigserial primary key,
    fullname           varchar(255) not null,
    total_cost         numeric      not null,
    deleted            boolean      not null,
    created_by_user_id bigint      not null,
    user_id            bigint references users (id) on DELETE cascade
);

CREATE TABLE IF NOT EXISTS items
(
    id           bigserial primary key,
    product_id   varchar(10)  not null,
    product_name varchar(100) not null,
    description  varchar(255) not null,
    price        numeric      not null,
    order_id     bigint references orders (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS users
(
    id    bigserial primary key,
    name  varchar(255) not null,
    email varchar(100) not null
);