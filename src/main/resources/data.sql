INSERT INTO commerce.users (user_id, created_date, modified_date, email, password) VALUES (2, '2022-01-03 01:06:27.589990', '2022-01-03 01:06:27.589990', 'test@test.com', '$2a$10$SwLSo7PoKJbvbs2CqlkBWeGhvKZ7qB3575bZCk76mmc2Km11FGku2');
INSERT INTO commerce.users (user_id, created_date, modified_date, email, password) VALUES (3, '2022-01-04 00:55:52.206754', '2022-01-04 00:55:52.206754', 'commerce@commerce.com', '$2a$10$DaDYn5jsOv3ZDCk/jfZWme0pyIH.u.AUwME/CJJx1fDOmw/LqeWY.');
INSERT INTO commerce.users (user_id, created_date, modified_date, email, password) VALUES (4, '2022-01-05 20:16:33.527980', '2022-01-05 20:16:33.527980', 'jake@jake.com', '$2a$10$gNK/LbNnfNVETFkqVaGeDOUOy04.HH4xIsCucGLbizHGaPB4bYIpy');

INSERT INTO commerce.items (item_id, created_date, modified_date, name, price) VALUES (1, '2022-01-03 01:12:35.562474', '2022-01-04 03:57:02.992403', 'apple', 1400);
INSERT INTO commerce.items (item_id, created_date, modified_date, name, price) VALUES (3, '2022-01-03 01:27:53.452210', '2022-01-03 01:27:53.452210', 'findapple', 1500);
INSERT INTO commerce.items (item_id, created_date, modified_date, name, price) VALUES (4, '2022-01-03 01:28:39.712135', '2022-01-03 01:28:39.712135', 'banana', 2000);
INSERT INTO commerce.items (item_id, created_date, modified_date, name, price) VALUES (5, '2022-01-04 03:57:42.294810', '2022-01-04 03:57:42.294810', 'yellowapple', 1800);

INSERT INTO commerce.orders (order_id, item_id, user_id) VALUES (1, 1, 2);
INSERT INTO commerce.orders (order_id, item_id, user_id) VALUES (2, 3, 2);
INSERT INTO commerce.orders (order_id, item_id, user_id) VALUES (3, 3, 2);