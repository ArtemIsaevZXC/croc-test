INSERT INTO orders (order_date_time, customer_name, status)
VALUES ('2023-08-11 15:30:00', 'Arthur Morgan', 'В работе');

INSERT INTO order_item (menu_item_id, quantity)
VALUES (1, 3);

INSERT INTO order_item (menu_item_id, quantity)
VALUES (2, 3);

INSERT INTO orders_order_items (order_items_id, orders_id)
VALUES (3, 2);

INSERT INTO orders_order_items (order_items_id, orders_id)
VALUES (4, 2);