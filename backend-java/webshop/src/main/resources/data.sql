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

