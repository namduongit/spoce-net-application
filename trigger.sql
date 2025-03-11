DELIMITER //
CREATE TRIGGER update_status_on_quantity_change
BEFORE INSERT ON foods
FOR EACH ROW
BEGIN
    IF NEW.quantity > 0 THEN
        SET NEW.status = 'Còn hàng';
    ELSE
        SET NEW.status = 'Hết hàng';
    END IF;
END;
//
DELIMITER ;

