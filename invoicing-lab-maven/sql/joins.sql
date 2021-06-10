SELECT i.id, i.date, i.vat, s.id_number, s.name, c.id_number, c.name
FROM invoices i, contragents s, contragents c 
WHERE i.suplier_id = s.id AND i.customer_id = c.id
ORDER BY i.id;

SELECT i.id, i.date, i.vat, s.id_number, s.name, c.id_number, c.name
FROM invoices i 
	LEFT JOIN contragents s ON i.suplier_id = s.id 
    LEFT JOIN contragents c ON  i.customer_id = c.id
ORDER BY i.id;

SELECT i.id, i.date, i.vat, s.id_number, s.name, c.id_number, c.name, p.name, l.quantity, 
	ifnull(l.price, p.price) `price`, l.quantity * ifnull(l.price, p.price) `total`
FROM invoices i 
	LEFT JOIN contragents s ON i.suplier_id = s.id 
    LEFT JOIN contragents c ON  i.customer_id = c.id
    LEFT JOIN `lines` l ON l.invoice_id = i.id
    LEFT JOIN products p ON l.product_id = p.id
ORDER BY i.id;
