USE `einvoice`;

DROP TABLE IF EXISTS `lines`;
DROP TABLE IF EXISTS `invoices`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `contragents`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(20) NOT NULL,
    `last_name` VARCHAR(20) NOT NULL,
	`username` VARCHAR(15)  NOT NULL UNIQUE,
    `password` VARCHAR(15)  NOT NULL,
    `role` TINYINT DEFAULT 0,
    `active` TINYINT(1) DEFAULT 1
);

CREATE TABLE `contragents` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(80) NOT NULL,
    `address` VARCHAR(120) NOT NULL,
    `id_number` VARCHAR(15) NOT NULL UNIQUE,
    `country_code` CHAR(2),
    `phone` VARCHAR(20),
    `corporate` TINYINT(1) DEFAULT 1,
    `email` VARCHAR(80),
    `iban` VARCHAR(22),
    `bic` VARCHAR(8)
);

CREATE UNIQUE INDEX `id_number_idx` ON `contragents`(`id_number`);

CREATE TABLE `products` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `code` CHAR(5) NOT NULL UNIQUE,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(512),
    `price` DECIMAL(8,2) NOT NULL,
    `is_promoted` TINYINT(1) DEFAULT 0,
    `promotion_percentage` DECIMAL(5,2),
    `unit` TINYINT DEFAULT 0
);

CREATE TABLE `invoices` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY,
    `suplier_id` INT UNSIGNED NOT NULL,
    `customer_id` INT UNSIGNED NOT NULL,
    `date` DATE,
    `vat` DECIMAL(4, 3) DEFAULT 0.2,
    `description` VARCHAR(255),
    INDEX `fk_invoices_supliers_idx` (`suplier_id`),
    INDEX `fk_invoices_customers_idx` (`customer_id`),
    CONSTRAINT `fk_invoices_supliers` FOREIGN KEY (`suplier_id`) REFERENCES `contragents` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_invoices_customers` FOREIGN KEY (`customer_id`) REFERENCES `contragents` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);


CREATE TABLE `lines` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `invoice_id` INT UNSIGNED NOT NULL,
    `product_id` INT UNSIGNED NOT NULL,
    `quantity` DECIMAL(8, 2) NOT NULL,
    `price` DECIMAL(8,2),
    KEY `fk_lines_products_idx` (`product_id`),
    INDEX `fk_lines_invoices_idx` (`invoice_id`),
    CONSTRAINT `fk_lines_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_lines_invoices` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
);




