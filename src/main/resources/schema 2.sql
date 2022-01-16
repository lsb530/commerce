CREATE TABLE `items`
(
    `item_id`       bigint NOT NULL AUTO_INCREMENT,
    `created_date`  datetime(6)  DEFAULT NULL,
    `modified_date` datetime(6)  DEFAULT NULL,
    `name`          varchar(255) DEFAULT NULL,
    `price`         int    NOT NULL,
    PRIMARY KEY (`item_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `orders`
(
    `order_id` bigint NOT NULL AUTO_INCREMENT,
    `item_id`  bigint DEFAULT NULL,
    `user_id`  bigint DEFAULT NULL,
    PRIMARY KEY (`order_id`),
    KEY `FK247nnxschdfm8lre0ssvy3k1r` (`item_id`),
    KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
    CONSTRAINT `FK247nnxschdfm8lre0ssvy3k1r` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
    CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `users`
(
    `user_id`       bigint NOT NULL AUTO_INCREMENT,
    `created_date`  datetime(6)  DEFAULT NULL,
    `modified_date` datetime(6)  DEFAULT NULL,
    `email`         varchar(255) DEFAULT NULL,
    `password`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;