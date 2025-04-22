DELIMITER //

CREATE TRIGGER trg_update_total_price
AFTER INSERT ON food_orders
FOR EACH ROW
BEGIN
    DECLARE food_price INT DEFAULT 0;

    -- Lấy giá của món ăn từ bảng foods
    SELECT price INTO food_price
    FROM foods
    WHERE food_id = NEW.food_id;

    -- Cộng thêm vào total_price của bill tương ứng
    UPDATE food_bills
    SET total_price = total_price + (food_price * NEW.quantity)
    WHERE bill_id = NEW.bill_id;
END;
//

DELIMITER ;