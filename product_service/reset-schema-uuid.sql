-- ============================================================
-- Manual reset script for product_db
-- Connect to product_db first:
--   psql -U postgres -d product_db
-- Then run this file.
-- ============================================================

DROP TABLE IF EXISTS product_categories;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;

CREATE TABLE IF NOT EXISTS categories (
    id          VARCHAR(36)  NOT NULL,
    name        VARCHAR(255),
    slug        VARCHAR(255),
    is_active   BOOLEAN,
    parent_id   VARCHAR(36),
    created_at  TIMESTAMPTZ,
    updated_at  TIMESTAMPTZ,
    deleted_at  TIMESTAMPTZ,
    created_by  VARCHAR(255),
    updated_by  VARCHAR(255),
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT fk_categories_parent FOREIGN KEY (parent_id)
        REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS products (
    id                  VARCHAR(36)    NOT NULL,
    name                VARCHAR(255),
    slug                VARCHAR(255),
    description         TEXT,
    price               NUMERIC(15, 2),          -- current selling price
    compare_at_price    NUMERIC(15, 2),          -- original price, NULL if not on sale
    stock_quantity      INTEGER,
    status              VARCHAR(20),
    created_at          TIMESTAMPTZ,
    updated_at          TIMESTAMPTZ,
    deleted_at          TIMESTAMPTZ,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product_categories (
    product_id  VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    CONSTRAINT pk_product_categories PRIMARY KEY (product_id, category_id),
    CONSTRAINT fk_pc_product  FOREIGN KEY (product_id)  REFERENCES products (id),
    CONSTRAINT fk_pc_category FOREIGN KEY (category_id) REFERENCES categories (id)
);

-- Insert a test category:
-- INSERT INTO categories (id, name, slug, is_active, created_at, updated_at)
-- VALUES ('paste-uuid-string-here', 'Electronics', 'electronics', true, NOW(), NOW());
-- SELECT id, name FROM categories;
