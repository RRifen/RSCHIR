CREATE DATABASE IF NOT EXISTS appDB;

USE appDB;

CREATE TABLE IF NOT EXISTS producers (
  producer_id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(50) NOT NULL,
  PRIMARY KEY (producer_id)
);

CREATE TABLE IF NOT EXISTS goods (
  good_id INT(11) NOT NULL AUTO_INCREMENT,
  producer_id INT(11) NOT NULL,
  name VARCHAR(20) NOT NULL,
  description VARCHAR(50) NOT NULL,
  PRIMARY KEY (good_id),
  FOREIGN KEY (producer_id) REFERENCES producers(producer_id), 
  CONSTRAINT UC_Good UNIQUE (name, description)
);

INSERT INTO producers (description, name) 
SELECT * FROM (SELECT 'Not so good goods', 'Producer1') AS tmp
WHERE NOT EXISTS (
    SELECT description FROM producers WHERE description = 'Not so good goods' AND name = 'Producer1'
) LIMIT 1;

INSERT INTO producers (description, name) 
SELECT * FROM (SELECT 'Very good goods', 'OOO AOA') AS tmp
WHERE NOT EXISTS (
    SELECT description FROM producers WHERE description = 'Very good goods' AND name = 'OOO AOA'
) LIMIT 1;

INSERT INTO goods (producer_id, name, description)
SELECT * FROM (SELECT 1, 'Laptop', 'Low-price laptop') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM goods WHERE producer_id = 1 AND name = 'Laptop' AND description = 'Low-price laptop'
) LIMIT 1;

INSERT INTO goods (producer_id, name, description)
SELECT * FROM (SELECT 2, 'Lamp', 'Bright lamp') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM goods WHERE producer_id = 2 AND name = 'Lamp' AND description = 'Bright lamp'
) LIMIT 1;

INSERT INTO goods (producer_id, name, description)
SELECT * FROM (SELECT 1, 'Kettle', 'Electric kettle') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM goods WHERE producer_id = 1 AND name = 'Kettle' AND description = 'Electric kettle'
) LIMIT 1;

INSERT INTO goods (producer_id, name, description)
SELECT * FROM (SELECT 2, 'Keyboard', 'Mechanic keyboard') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM goods WHERE producer_id = 2 AND name = 'Keyboard' AND description = 'Mechanic keyboard'
) LIMIT 1;