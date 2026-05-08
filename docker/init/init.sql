-- ============================================================
-- Microservice Database Initialization
-- ============================================================
--
-- Architecture note:
--   MySQL  → "schema" = database (full isolation between services)
--   PostgreSQL → "schema" = logical namespace inside a database
--                            (NOT the same isolation as MySQL schema)
--
-- For true per-service isolation in PostgreSQL, each microservice
-- gets its OWN DATABASE — not just its own schema.
--
-- This script creates:
--   product_db  — owned exclusively by product_service
--   order_db    — owned exclusively by order_service
--
-- Cross-service references (e.g. order_items.product_id) are plain
-- UUID columns with NO foreign key constraints. Services talk to
-- each other via API, not via shared database joins.
-- ============================================================


-- ─────────────────────────────────────────────
-- Create the two microservice databases
-- ─────────────────────────────────────────────
CREATE DATABASE product_db;
CREATE DATABASE order_db;


-- ─────────────────────────────────────────────
-- product_db schema
-- ─────────────────────────────────────────────
\connect product_db

CREATE TABLE IF NOT EXISTS categories (
    id          UUID        NOT NULL DEFAULT gen_random_uuid(),
    name        VARCHAR(255),
    slug        VARCHAR(255),
    is_active   BOOLEAN,
    parent_id   UUID,
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
    id             UUID           NOT NULL DEFAULT gen_random_uuid(),
    name           VARCHAR(255),
    slug           VARCHAR(255),
    description    TEXT,
    price          NUMERIC(15, 2),
    stock_quantity INTEGER,
    status         VARCHAR(20),
    created_at     TIMESTAMPTZ,
    updated_at     TIMESTAMPTZ,
    deleted_at     TIMESTAMPTZ,
    created_by     VARCHAR(255),
    updated_by     VARCHAR(255),
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product_categories (
    product_id  UUID NOT NULL,
    category_id UUID NOT NULL,
    CONSTRAINT pk_product_categories PRIMARY KEY (product_id, category_id),
    CONSTRAINT fk_pc_product  FOREIGN KEY (product_id)  REFERENCES products (id),
    CONSTRAINT fk_pc_category FOREIGN KEY (category_id) REFERENCES categories (id)
);


-- ─────────────────────────────────────────────
-- order_db schema (future order_service)
-- ─────────────────────────────────────────────
\connect order_db

CREATE TABLE IF NOT EXISTS orders (
    id             UUID           NOT NULL DEFAULT gen_random_uuid(),
    customer_id    UUID           NOT NULL,
    status         VARCHAR(20),
    total_amount   NUMERIC(15, 2),
    created_at     TIMESTAMPTZ,
    updated_at     TIMESTAMPTZ,
    deleted_at     TIMESTAMPTZ,
    created_by     VARCHAR(255),
    updated_by     VARCHAR(255),
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_items (
    id          UUID           NOT NULL DEFAULT gen_random_uuid(),
    order_id    UUID           NOT NULL,
    -- product_id references product_service data.
    -- NO foreign key here — cross-service references are by UUID only.
    -- Use the product_service API to resolve product details.
    product_id  UUID           NOT NULL,
    quantity    INTEGER        NOT NULL,
    unit_price  NUMERIC(15, 2) NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id)
);
