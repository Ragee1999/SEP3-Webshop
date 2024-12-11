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
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE product
    ALTER COLUMN description TYPE character varying(1000);




UPDATE product
SET
description = CASE
WHEN id = 1 THEN 'A sleek and powerful laptop featuring an Intel Core i7 processor, 16GB RAM, and a 512GB SSD for lightning-fast performance. Perfect for work, study, and entertainment. Enjoy its stunning 15.6'' Full HD display and long-lasting battery life, all packed into a lightweight and stylish design.'
WHEN id = 2 THEN 'Experience cutting-edge technology with this advanced smartphone, featuring a sleek design, high-resolution display, powerful performance, and a long-lasting battery to keep you connected all day.'
WHEN id = 3 THEN 'Immerse yourself in crystal-clear sound with these noise-cancelling headphones, offering exceptional comfort, premium audio quality, and wireless connectivity for an unmatched listening experience.'
WHEN id = 4 THEN 'Enhance your productivity and entertainment with this 4K UHD monitor, delivering stunning visuals, vibrant colors, and a sleek design perfect for work or play.'
WHEN id = 5 THEN 'Elevate your typing and gaming experience with this RGB mechanical keyboard, featuring customizable lighting, responsive keys, and durable construction for long-lasting performance.'
WHEN id = 6 THEN 'Stay productive and entertained on the go with this portable tablet, offering a high-resolution display, powerful performance, and a lightweight design for ultimate versatility.'
WHEN id = 7 THEN 'Work and play with precision using this ergonomic wireless mouse, designed for comfort, smooth navigation, and reliable performance.'
END,
price = CASE
WHEN id = 1 THEN 7999
WHEN id = 2 THEN 8999
WHEN id = 3 THEN 3200
WHEN id = 4 THEN 2300
WHEN id = 5 THEN 1400
WHEN id = 6 THEN 3500
WHEN id = 7 THEN 249
END
WHERE id IN (1, 2, 3, 4, 5, 6, 7);



