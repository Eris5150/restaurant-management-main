CREATE TABLE menu_items (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price FLOAT NOT NULL,
    category VARCHAR(50),
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE inventory_items (
                                 id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                 name VARCHAR(100) NOT NULL,
                                 quantity INTEGER NOT NULL,
                                 unit VARCHAR(20),
                                 price_per_unit FLOAT
);

CREATE TABLE orders (
                        id INTEGER PRIMARY KEY AUTO_INCREMENT,
                        inventory_item_id INTEGER NOT NULL,
                        quantity_to_order INTEGER NOT NULL,
                        order_date DATE DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(20) NOT NULL,
                        FOREIGN KEY (inventory_item_id) REFERENCES inventory_items(id) ON DELETE CASCADE
);


CREATE TABLE reviews (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    author VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    rating VARCHAR(10) NOT NULL
);

CREATE TABLE sec_user (
                          userId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          email VARCHAR(75) NOT NULL UNIQUE,
                          encryptedPassword VARCHAR(128) NOT NULL,
                          enabled BIT NOT NULL
);

CREATE TABLE sec_role (
                          roleId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          roleName VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE user_role (
                           id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           userId BIGINT NOT NULL,
                           roleId BIGINT NOT NULL
);

ALTER TABLE user_role
    ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);

ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
        REFERENCES sec_user (userId);

ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
        REFERENCES sec_role(roleId);

