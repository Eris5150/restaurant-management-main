-- ============================================================
-- Schema: Restaurant Management System
-- Purpose: Core domain (menu, inventory, orders, reviews)
--          + Security (users/roles/mappings)
-- Notes:
-- - H2/MySQL-compatible types selected for dev/test simplicity.
-- - Foreign keys and unique constraints defined explicitly.
-- ============================================================

-- -------------------------------
-- Menu items offered to customers
-- -------------------------------
CREATE TABLE menu_items (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,       -- Surrogate key
    name VARCHAR(100) NOT NULL,                  -- Display name
    description TEXT,                            -- Optional marketing copy
    price FLOAT NOT NULL,                        -- Unit price (consider DECIMAL in prod)
    category VARCHAR(50),                        -- Enum-like (APPETIZER/MAIN_COURSE/...)
    available BOOLEAN DEFAULT TRUE               -- Item toggled on/off the menu
);

-- ---------------------------------------
-- Inventory items used to prepare the menu
-- ---------------------------------------
CREATE TABLE inventory_items (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,       -- Surrogate key
    name VARCHAR(100) NOT NULL,                  -- Canonical, normalized name
    quantity INTEGER NOT NULL,                   -- On-hand quantity
    unit VARCHAR(20),                            -- Units enum (KG/LITERS/PIECES/...)
    price_per_unit FLOAT                         -- Cost per unit (consider DECIMAL in prod)
);

-- ----------------------------------------------------
-- Purchase orders to replenish inventory (simple model)
-- ----------------------------------------------------
CREATE TABLE orders (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,       -- Surrogate key
    inventory_item_id INTEGER NOT NULL,          -- FK -> inventory_items(id)
    quantity_to_order INTEGER NOT NULL,          -- Desired quantity to add
    order_date DATE DEFAULT CURRENT_TIMESTAMP,   -- Creation date
    status VARCHAR(20) NOT NULL,                 -- Pending/Completed/Cancelled
    FOREIGN KEY (inventory_item_id) REFERENCES inventory_items(id) ON DELETE CASCADE
);

-- ---------------------------
-- Public customer-facing reviews
-- ---------------------------
CREATE TABLE reviews (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,       -- Surrogate key
    author VARCHAR(50) NOT NULL,                 -- Display name of reviewer
    description VARCHAR(255),                    -- Optional review text
    rating VARCHAR(10) NOT NULL                  -- Enum-like (BAD/AVERAGE/GOOD/EXCELLENT)
);

-- ===========================
-- Security: Users & Roles
-- ===========================

-- Application users (GM / AGM, etc.)
CREATE TABLE sec_user (
    userId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Surrogate key
    email VARCHAR(75) NOT NULL UNIQUE,                 -- Login identifier
    encryptedPassword VARCHAR(128) NOT NULL,           -- BCrypt hash
    enabled BIT NOT NULL                                -- 1 = active, 0 = disabled
);

-- Role catalog (authority names)
CREATE TABLE sec_role (
    roleId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Surrogate key
    roleName VARCHAR(30) NOT NULL UNIQUE               -- e.g., ROLE_GM, ROLE_AGM
);

-- User↔Role mapping (many-to-many join table)
CREATE TABLE user_role (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,     -- Surrogate key
    userId BIGINT NOT NULL,                            -- FK -> sec_user(userId)
    roleId BIGINT NOT NULL                             -- FK -> sec_role(roleId)
);

-- Prevent duplicate role assignments per user
ALTER TABLE user_role
    ADD CONSTRAINT user_role_uk UNIQUE (userId, roleId);

-- FK: user_role.userId -> sec_user.userId
ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk1 FOREIGN KEY (userId)
        REFERENCES sec_user (userId);

-- FK: user_role.roleId -> sec_role.roleId
ALTER TABLE user_role
    ADD CONSTRAINT user_role_fk2 FOREIGN KEY (roleId)
        REFERENCES sec_role(roleId);
