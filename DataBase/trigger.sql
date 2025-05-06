DELIMITER $$

CREATE TRIGGER trg_update_food_quantity_after_receipt_confirm
    AFTER UPDATE ON purchase_receipts
    FOR EACH ROW
BEGIN
    IF OLD.status = 'Chờ xác nhận' AND NEW.status = 'Đã xác nhận' THEN
        -- Cập nhật số lượng món ăn
    UPDATE foods f
        JOIN purchase_receipt_detail d ON f.food_id = d.food_id
        SET f.quantity = f.quantity + d.quantity
    WHERE d.receipt_id = NEW.receipt_id;
END IF;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER after_account_insert
    AFTER INSERT ON accounts
    FOR EACH ROW
BEGIN
    -- Nếu là Quản trị viên hoặc Nhân viên, thêm vào bảng staffs với thông tin mặc định
    IF NEW.role IN ('Quản trị viên', 'Nhân viên') THEN
        INSERT INTO staffs (account_id, full_name, birth_date, gender, phone, email, address, cccd, avatar)
        VALUES (NEW.account_id, 'Chưa cập nhật', '2000-01-01', 'Chưa đặt', NULL, NULL, NULL, NULL, NULL);

    -- Nếu là Người chơi, thêm vào bảng players với số dư mặc định là 0
    ELSEIF NEW.role = 'Người chơi' THEN
        INSERT INTO players (account_id, balance)
        VALUES (NEW.account_id, 0);
END IF;
END$$

DELIMITER ;