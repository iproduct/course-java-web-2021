INSERT INTO `products` (`code`, `name`, `category`, `description`, `price`) VALUES
('BK001', 'Thinking in Java', 'Books', 'Good intro to Java', 23.99);

INSERT INTO `products` (`code`, `name`, `category`, `description`, `price`) VALUES
('AC001', 'Wireless Mouse', 'Accessoaries', 'Logitech - high quality', 28.4),
('AC002', 'Keyboard', 'Accessoaries', 'Keyboard - 101 Key', 15.99),
('BK002', 'Effective Java', 'Books', 'Java in depth book. Author: Joshua Bloch', 35.2),
('AC003', 'Graphic Tablet', 'Accessoaries', 'High resolution tablet for diditizin images', 57.99);

INSERT INTO `contragents` (`name`, address, id_number, country_code, phone, corporate, email, iban, bic) VALUES
('IPT Ltd.', 'Sofia, Slatinska bl.44', '143899933', 'BG', '+359 888354545', 1, 'iliev@iproduct.org', 'UNCR231312433243234', 'UNCR'),
('ABC Ltd.', 'Sofia, 1000', '123456789', 'BG', '+359 324234343', 1, 'office@abc.com', 'PRCR23423423423423423', 'PRCR'),
('Ivan Petrov', 'Sofia, Graf Igantiev 55, vh. A ap.16', '8212234536', null, '+359 2345453', 0, null, null, null),
('Software AD', 'Sofia, 1000', '987654321', 'BG', '+359 245634567', 1, 'office@softwaread.com', null, null);
