-- Insert into Address table
INSERT INTO address (id, city, country, postalcode, street)
VALUES
    (1, 'Copenhagen', 'Denmark', '1330', 'Niels Bohrvej 12'),
    (2, 'Horsens', 'Denmark', '8620', 'Horsensgade 122'),
    (3, 'Odense', 'Denmark', '5000', 'Dalgas Vej'),
    (4, 'Aalborg', 'Denmark', '9000', 'Tingskoven 242'),
    (5, 'Esbjerg', 'Denmark', '6700', 'Banegården 34'),
    (6, 'Vejle', 'Denmark', '3449', 'Vejlegårdsvej 11'),
    (7, 'Esbjerg', 'Denmark', '6700', 'Larsensvej 324');


-- Insert into Customer table
INSERT INTO customer (id, email, firstname, lastname, phonenumber)
VALUES
    (1, 'john.doe@example.com', 'John', 'Doe', '12345678'),
    (2, 'jesse.pinkman@example.com', 'Jesse', 'Pinkmann', '87654321'),
    (3, 'walter.white@example.com', 'Walter', 'White', '22334455'),
    (4, 'gustavo.fring@example.com', 'Gustavo', 'Fring', '66778899'),
    (5, 'hank.schrader@example.com', 'Hank', 'Schrader', '99887766'),
    (6, 'lionel.messi@example.com', 'Lionel', 'Messi', '10101010'),
    (7, 'cristiano.ronaldo@example.com', 'Cristiano', 'Ronaldo', '20202020');


TRUNCATE TABLE product RESTART IDENTITY CASCADE;

-- Insert into Product table
INSERT INTO product (id, description, image, name, price, stockquantity)
VALUES
    (1, 'A high-performance laptop', 'laptop.jpg', 'Laptop', 1200.00, 50),
    (2, 'Latest model smartphone with advanced features', 'smartphone.jpg', 'Smartphone', 800.00, 100),
    (3, 'Noise-cancelling over-ear headphones', 'headphones.jpg', 'Headphones', 150.00, 200),
    (4, '4K UHD monitor for professional use', 'monitor.jpg', 'Monitor', 300.00, 80),
    (5, 'Mechanical keyboard with RGB lighting', 'keyboard.jpg', 'Keyboard', 100.00, 150),
    (6, 'Portable tablet with high-resolution screen', 'tablet.jpg', 'Tablet', 600.00, 75),
    (7, 'Wireless mouse with ergonomic design', 'mouse.jpg', 'Mouse', 25.00, 300);


-- Creating table for contact information to store data

CREATE TABLE contact_message (
id SERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
email VARCHAR(255) NOT NULL,
message TEXT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
is_answered boolean default false
);



