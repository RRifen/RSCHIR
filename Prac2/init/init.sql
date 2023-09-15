CREATE DATABASE IF NOT EXISTS appDB;

USE appDB;
CREATE TABLE IF NOT EXISTS goods (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  description VARCHAR(50) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT UC_Good UNIQUE (name, description)
);

INSERT INTO goods (name, description)
SELECT * FROM (SELECT 'Laptop', 'Low-price laptop') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM goods WHERE name = 'Laptop' AND description = 'Low-price laptop'
) LIMIT 1;

INSERT INTO goods (name, description)
SELECT * FROM (SELECT 'Lamp', 'Bright lamp') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM goods WHERE name = 'Lamp' AND description = 'Bright lamp'
) LIMIT 1;

INSERT INTO goods (name, description)
SELECT * FROM (SELECT 'Kettle', 'Electric kettle') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM goods WHERE name = 'Kettle' AND description = 'Electric kettle'
) LIMIT 1;

INSERT INTO goods (name, description)
SELECT * FROM (SELECT 'Keyboard', 'Mechanic keyboard') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM goods WHERE name = 'Keyboard' AND description = 'Mechanic keyboard'
) LIMIT 1;