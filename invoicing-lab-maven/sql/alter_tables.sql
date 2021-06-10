USE `einvoice`;

ALTER TABLE `lines` 
DROP FOREIGN KEY `fk_lines_products`;

ALTER TABLE `lines`
ADD CONSTRAINT `fk_lines_products`
FOREIGN KEY (`product_id`) 
REFERENCES `products` (`id`) 
ON DELETE RESTRICT 
ON UPDATE RESTRICT;

ALTER TABLE `lines` 
DROP FOREIGN KEY `fk_lines_invoices`;

ALTER TABLE `lines`
ADD CONSTRAINT `fk_lines_invoices`
FOREIGN KEY (`invoice_id`) 
REFERENCES `invoices` (`id`) 
ON DELETE RESTRICT 
ON UPDATE RESTRICT;

ALTER TABLE `invoices`
DROP FOREIGN KEY `fk_invoices_supliers`;

ALTER TABLE `invoices`
ADD CONSTRAINT `fk_invoices_supliers`
FOREIGN KEY (`suplier_id`) 
REFERENCES `contragents` (`id`) 
ON DELETE RESTRICT 
ON UPDATE RESTRICT;

ALTER TABLE `products`
ADD COLUMN `category` VARCHAR(40);

ALTER TABLE `products`
CHANGE `category` `product_category`  VARCHAR(40) NOT NULL DEFAULT 'Accessoaries'  AFTER `name`;

ALTER TABLE `products`
RENAME COLUMN `product_category` TO `category`;

ALTER TABLE `products`
MODIFY `category` VARCHAR(40) NOT NULL DEFAULT 'Books'  AFTER `name`;

ALTER TABLE `invoices`
DROP PRIMARY KEY,
MODIFY COLUMN `id` INT UNSIGNED NOT NULL PRIMARY KEY;




