-- create_db.ddl :: Application Database Creation Script
-- This file creates the database used for DrinkBase

-- cabinets :: stores data about a user's cabinets
CREATE TABLE IF NOT EXISTS cabinets(
  cab_id        INTEGER   PRIMARY KEY   AUTOINCREMENT,
  cab_name      TEXT      NOT NULL,
  cab_location  TEXT
);

-- ingredients :: stores data about the ingredients used
CREATE TABLE IF NOT EXISTS ingredients(
  ing_id        INTEGER   PRIMARY KEY   AUTOINCREMENT,
  ing_name      INT       NOT NULL,
  ing_type      TEXT      NOT NULL,
  ing_picture   BLOB      NOT NULL
);

-- stock :: stores the quantities of ingredients for each cabinet
CREATE TABLE IF NOT EXISTS stock(
  stock_id      INT       NOT NULL,
  cabinet_id    INT       NOT NULL,
  quantity      INT       NOT NULL,
  FOREIGN KEY(stock_id) REFERENCES ingredients(ing_id),
  FOREIGN KEY(cabinet_id) REFERENCES cabinets(cab_id)
);

-- recipes :: stores the recipes for drinks
CREATE TABLE IF NOT EXISTS recipes(
  rec_id        INTEGER   PRIMARY KEY   AUTOINCREMENT,
  rec_name      TEXT      NOT NULL,
  rec_file      BLOB      NOT NULL,
  rec_image     BLOB
);

-- uses_ingredients :: stores the ingredient usage for a recipe
CREATE TABLE IF NOT EXISTS uses_ingredient(
  rec_id        INT       NOT NULL,
  ing_id        INT       NOT NULL,
  ing_amount    INT       NOT NULL,
  FOREIGN KEY(rec_id) REFERENCES recipes(rec_id),
  FOREIGN KEY(ing_id) REFERENCES ingredients(ing_id)
);