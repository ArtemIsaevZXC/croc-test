create table menu_item
(
    id      bigserial not null,
    name    varchar(255),
    volume  float(53) not null,
    price   float(53) not null,
    deleted boolean   not null,
    primary key (id)
);

create table order_item
(
    id           bigserial not null,
    menu_item_id bigint,
    quantity     integer   not null,
    primary key (id),
    foreign key (menu_item_id) references menu_item
);

create table orders
(
    id              bigserial not null,
    order_date_time timestamp(6),
    customer_name   varchar(255),
    status          varchar(255),
    primary key (id)
);

create table orders_order_items
(
    order_items_id bigint not null unique,
    orders_id      bigint not null,
    foreign key (order_items_id) references order_item,
    foreign key (orders_id) references orders
);