-- Insert into Address table
INSERT INTO address (street, city, postal_code, country)
VALUES
    ('Niels Bohrvej 12', 'Copenhagen', '1330', 'Denmark'),
    ('Horsensgade 122', 'Horsens', '8620', 'Denmark'),
    ('Dalgas Vej', 'Odense', '5000', 'Denmark'),
    ('Tingskoven 242', 'Aalborg', '9000', 'Denmark'),
    ('Banegården 34', 'Esbjerg', '6700', 'Denmark'),
    ('Vejlegårdsvej 11', 'Vejle', '3449', 'Denmark'),
    ('Larsensvej 324', 'Esbjerg', '6700', 'Denmark');

-- Insert into Customer table
INSERT INTO customer (first_name, last_name, email, phone_number, address_id)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '12345678', 1),
    ('Jesse', 'Pinkmann', 'JessePinkmann@example.com', '87654321', 2),
    ('Walter', 'White', 'WalterWhite@example.com', '22334455', 3),
    ('Gustavo', 'Fring', 'GustavoFring@example.com', '66778899', 4),
    ('Hank', 'Schrader', 'HankSchrader@example.com', '99887766', 5),
    ('Lionel', 'Messi', 'lionel.messi@example.com', '10101010', 6),
    ('Cristiano', 'Ronaldo', 'cristiano.ronaldo@example.com', '20202020', 7);

TRUNCATE TABLE product RESTART IDENTITY CASCADE;

-- Insert into Product table
INSERT INTO product (name, image, price, stock_quantity)
VALUES
    ('Laptop', 'laptop.jpg', 1500.00, 50),
    ('Smartphone', 'smartphone.jpg', 800.00, 100),
    ('Tablet', 'tablet.jpg', 600.00, 75),
    ('Headphones', 'headphones.jpg', 200.00, 200),
    ('Keyboard', 'keyboard.jpg', 50.00, 150),
    ('Mouse', 'mouse.jpg', 25.00, 300),
    ('Monitor', 'monitor.jpg', 300.00, 80);

-- Insert into CustomerOrder table
INSERT INTO customer_order (status, total_price, customer_id, address_id, order_date)
VALUES
    ('Pending', 1500.00, 1, 1, '2024-12-01 10:00:00'),
    ('Shipped', 2400.00, 2, 2, '2024-11-30 15:30:00'),
    ('Completed', 600.00, 3, 3, '2024-11-25 09:00:00'),
    ('Cancelled', 800.00, 4, 4, '2024-11-20 16:45:00'),
    ('Pending', 125.00, 5, 5, '2024-12-02 12:00:00'),
    ('Pending', 2500.00, 6, 1, '2024-12-03 14:00:00'),
    ('Shipped', 1800.00, 7, 2, '2024-12-02 11:00:00');

-- OrderItem data
INSERT INTO order_item (order_id, product_id, quantity, price)
VALUES
    (1, 1, 1, 1500.00),
    (2, 2, 2, 1600.00),
    (2, 3, 1, 600.00),
    (3, 4, 3, 600.00),
    (4, 2, 1, 800.00),
    (5, 5, 5, 125.00),
    (6, 6, 1, 8100.00),
    (7, 7, 4, 100.00);


