CREATE TABLE IF NOT EXISTS cabinets(
  cab_id        INTEGER   PRIMARY KEY   AUTOINCREMENT,
  cab_name      TEXT      NOT NULL,
  cab_location  TEXT
);

CREATE TABLE IF NOT EXISTS ingredients(
  ing_id        INTEGER   PRIMARY KEY   AUTOINCREMENT,
  ing_name      INT       NOT NULL,
  ing_type      TEXT      NOT NULL,
  ing_picture   BLOB      NOT NULL
);

CREATE TABLE IF NOT EXISTS stock(
  stock_id      INT       NOT NULL,
  cabinet_id    INT       NOT NULL,
  quantity      INT       NOT NULL,
  FOREIGN KEY(stock_id) REFERENCES ingredients(ing_id),
  FOREIGN KEY(cabinet_id) REFERENCES cabinets(cab_id)
);