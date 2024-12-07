-- Insert into Address table
INSERT INTO address (street, city, postalcode, country)
VALUES
    ('Niels Bohrvej 12', 'Copenhagen', '1330', 'Denmark'),
    ('Horsensgade 122', 'Horsens', '8620', 'Denmark'),
    ('Dalgas Vej', 'Odense', '5000', 'Denmark'),
    ('Tingskoven 242', 'Aalborg', '9000', 'Denmark'),
    ('Banegården 34', 'Esbjerg', '6700', 'Denmark'),
    ('Vejlegårdsvej 11', 'Vejle', '3449', 'Denmark'),
    ('Larsensvej 324', 'Esbjerg', '6700', 'Denmark');

INSERT INTO customer (firstname, lastname, email, phonenumber, address_id)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '12345678', 1),
    ('Jesse', 'Pinkmann', 'JessePinkmann@example.com', '87654321', 2),
    ('Walter', 'White', 'WalterWhite@example.com', '22334455', 3),
    ('Gustavo', 'Fring', 'GustavoFring@example.com', '66778899', 4),
    ('Hank', 'Schrader', 'HankSchrader@example.com', '99887766', 5),
    ('Lionel', 'Messi', 'lionel.messi@example.com', '10101010', 6),
    ('Cristiano', 'Ronaldo', 'cristiano.ronaldo@example.com', '20202020', 7);

TRUNCATE TABLE product RESTART IDENTITY CASCADE;

INSERT INTO product (name, image, price, stockquantity, description)
VALUES
    ('Laptop', 'laptop.jpg', 1500.00, 50, 'Laptop description'),
    ('Smartphone', 'smartphone.jpg', 800.00, 100, 'Smartphone description'),
    ('Tablet', 'tablet.jpg', 600.00, 75, 'Tablet description'),
    ('Headphones', 'headphones.jpg', 200.00, 200, 'Headphones description'),
    ('Keyboard', 'keyboard.jpg', 50.00, 150, 'Keyboard description'),
    ('Mouse', 'mouse.jpg', 25.00, 300, 'Mouse description'),
    ('Monitor', 'monitor.jpg', 300.00, 80, 'Monitor description');

INSERT INTO customer_order (totalprice, totalquantity, customer_id, address_id)
VALUES
    (2000.00, 3, 1, 1),
    (1200.00, 2, 2, 2),
    (450.00, 1, 3, 3);

INSERT INTO order_item (customerorder_id, product_id, quantity)
VALUES
    (1, 1, 1),  -- CustomerOrder 1, Product 1
    (1, 2, 2),  -- CustomerOrder 1, Product 2
    (2, 3, 1),  -- CustomerOrder 2, Product 3
    (2, 4, 1),  -- CustomerOrder 2, Product 4
    (3, 5, 1);  -- CustomerOrder 3, Product 5