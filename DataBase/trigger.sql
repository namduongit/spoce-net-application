-- Trigger: Thêm vào bảng staffs hoặc players sau khi thêm accounts
DELIMITER //

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
END;
//

DELIMITER ;


-- Trigger: Cập nhật lại total khi chi tiết phiếu nhập bị cập nhật
DELIMITER //

CREATE TRIGGER trg_after_update_detail
    AFTER UPDATE ON purchase_receipt_detail
    FOR EACH ROW
BEGIN
    UPDATE purchase_receipts
    SET total = (
        SELECT SUM(quantity * price)
        FROM purchase_receipt_detail
        WHERE receipt_id = NEW.receipt_id
    )
    WHERE receipt_id = NEW.receipt_id;
END;
//

DELIMITER ;

-- Trigger: Cập nhật số lượng món ăn khi phiếu nhập được xác nhận
DELIMITER //

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
END;
//

DELIMITER ;


--  Cập nhật total sau khi thêm chi tiết phiếu nhập
DELIMITER //

-- Trigger cập nhật khi THÊM chi tiết phiếu nhập
CREATE TRIGGER trg_after_insert_detail
    AFTER INSERT ON purchase_receipt_detail
    FOR EACH ROW
BEGIN
    UPDATE purchase_receipts
    SET total = (
        SELECT SUM(quantity * price)
        FROM purchase_receipt_detail
        WHERE receipt_id = NEW.receipt_id
    )
    WHERE receipt_id = NEW.receipt_id;
END //

-- Trigger cập nhật khi XÓA chi tiết phiếu nhập
CREATE TRIGGER trg_after_delete_detail
    AFTER DELETE ON purchase_receipt_detail
    FOR EACH ROW
BEGIN
    UPDATE purchase_receipts
    SET total = (
        SELECT IFNULL(SUM(quantity * price), 0)
        FROM purchase_receipt_detail
        WHERE receipt_id = OLD.receipt_id
    )
    WHERE receipt_id = OLD.receipt_id;
END //

-- Trigger cập nhật khi SỬA quantity hoặc price trong chi tiết phiếu nhập
CREATE TRIGGER trg_after_update_detail
    AFTER UPDATE ON purchase_receipt_detail
    FOR EACH ROW
BEGIN
    UPDATE purchase_receipts
    SET total = (
        SELECT SUM(quantity * price)
        FROM purchase_receipt_detail
        WHERE receipt_id = NEW.receipt_id
    )
    WHERE receipt_id = NEW.receipt_id;
END //

DELIMITER ;
