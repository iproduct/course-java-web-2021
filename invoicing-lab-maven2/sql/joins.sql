SELECT i.id, i.date, i.vat, s.id_number, s.name, c.id_number, c.name
FROM invoices i, contragents s, contragents c 
WHERE i.suplier_id = s.id AND i.customer_id = c.id
ORDER BY i.id;

SELECT i.id, i.date, i.vat, s.id_number, s.name, c.id_number, c.name
FROM invoices i 
	LEFT JOIN contragents s ON i.suplier_id = s.id 
    LEFT JOIN contragents c ON  i.customer_id = c.id
ORDER BY i.id;

