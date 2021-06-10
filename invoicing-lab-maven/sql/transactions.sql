SET autocommit = 0;

SHOW VARIABLES LIKE 'autocommit';

-- 1. start a new transaction
START TRANSACTION;

-- 2. Get the latest order number
SELECT @orderNumber:=IFNULL(MAX(id)+1, 1) FROM `invoices`;
    
# SELECT @orderNumber; 

-- 3. insert a new order for customer 145
INSERT INTO `invoices`(id, suplier_id, customer_id, date, vat, description)
VALUES(@orderNumber, 1, 2, '2021-05-31', null, null);
        
-- 4. Insert order line items
INSERT INTO `lines` (invoice_id, product_id, quantity, price) VALUES
	(@orderNumber, 2, 5, null),
	(@orderNumber, 3, 3, null),
	(@orderNumber, 5, 1, 49.4); 
      
# ROLLBACK
-- 5. commit changes    
COMMIT; 