INSERT INTO menu_item (name, volume, price, deleted)
VALUES ('BigMac', 200, 10.99, false);

INSERT INTO menu_item (name, volume, price, deleted)
VALUES ('Cheese-Burger', 200, 10.99, false);

INSERT INTO orders (order_date_time, customer_name, status)
VALUES ('2023-08-11 15:30:00', 'John Doe', 'В работе');

INSERT INTO order_item (menu_item_id, quantity)
VALUES (1, 2);

INSERT INTO order_item (menu_item_id, quantity)
VALUES (2, 1);

INSERT INTO orders_order_items (order_items_id, orders_id)
VALUES (1, 1);

INSERT INTO orders_order_items (order_items_id, orders_id)
VALUES (2, 1);