CREATE DATABASE customer_test;
DROP DATABASE customer_test;

USE customer_test;

CREATE TABLE IF NOT EXISTS customer(
	 id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

SELECT VERSION();
ALTER TABLE customer ADD cpf VARCHAR(14);
DESCRIBE customer; # mostrar configs das colunas 
DROP TABLE customer;

SELECT * FROM customer;

INSERT INTO customer (name, email, cpf) VALUES ("Teste", "teste@teste.com", NULL),
											   ("Andressa", "andressa@hotmail.com", "045.987.445-20");

DELETE FROM customer WHERE email = "igor@hotmail.com";