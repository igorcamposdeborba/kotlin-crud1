CREATE DATABASE customer_test;
DROP DATABASE customer_test;

USE customer_test;

CREATE TABLE IF NOT EXISTS customer(
	id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14),
    status ENUM('ATIVO', 'DESATIVADO', 'DELETADO') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

SELECT VERSION();
ALTER TABLE customer ADD cpf VARCHAR(14);
ALTER TABLE customer ADD status VARCHAR(255);
DESCRIBE customer; # mostrar configs das colunas 
DROP TABLE customer;

SELECT * FROM customer;

INSERT INTO customer (name, email, cpf, status) VALUES ("Teste", "teste@teste.com", NULL, "ATIVO"),
											           ("Andressa", "andressa@hotmail.com", "045.987.445-20", "ATIVO");

DELETE FROM customer WHERE email = "igor@hotmail.com";

-- ------------------------------------------------
 CREATE TABLE book(
	id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    status ENUM('ATIVO', 'VENDIDO', 'CANCELADO', 'DELETADO') NOT NULL,
    customer_id CHAR(36) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
 );
 DROP TABLE book;
 
SHOW CREATE TABLE book;
SELECT * FROM book;

DELETE FROM book WHERE id = 1;

SELECT c.name, b.*
FROM Book b
JOIN Customer c ON b.customer_id = c.id
WHERE c.name = 'Igor';

-- ------------------------------------------------

CREATE TABLE purchase (
	id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    customer_id CHAR(36) NOT NULL,
	nfe VARCHAR(255),
    total_price DECIMAL(15, 2) NOT NULL,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- PK composta
CREATE TABLE purchase_book (
	purchase_id CHAR(36) NOT NULL DEFAULT (UUID()),
    book_id INT NOT NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchase(id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    PRIMARY KEY (purchase_id, book_id)
);

DROP TABLE purchase_book, purchase;

SELECT * FROM purchase;
SELECT * FROM purchase_book;