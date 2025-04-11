drop database if exists net_gaming_management;
create database if not exists net_gaming_management;
use net_gaming_management;


-- @Author: namduongit
-- Create date: 26-02-2025

-- Giải thích một số ràng buộc SQL:
-- 	   + AUTO_INCREMENT: 			tự tạo mã khi tạo 1 hàng mới của bảng
--     + CURRENT_TIMESTAMP: 		tự lấy thời gian lúc tạo bảng
--     + ON DELETE SET NULL: 		khi bản ghi trong bảng cha bị xóa, các bản ghi con sẽ có giá trị NULL trong cột khóa ngoại.
--     + ON DELETE CASCADE: 		khi bản ghi trong bảng cha bị xóa, tất cả các bản ghi con liên quan cũng sẽ bị xóa.


-- Tạo các bảng tài khoản (người chơi, nhân viên, quản trị viên)
CREATE TABLE IF NOT EXISTS accounts (
                                        account_id  INT AUTO_INCREMENT PRIMARY KEY,
                                        username    VARCHAR(50) UNIQUE NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('Quản trị viên', 'Nhân viên', 'Người chơi') NOT NULL,
    status      ENUM('Online', 'Offline', 'Locked') DEFAULT 'Offline',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS staffs (
                                      staff_id    	INT AUTO_INCREMENT PRIMARY KEY,
                                      account_id  	INT UNIQUE NOT NULL,
                                      full_name   	VARCHAR(100) NOT NULL,
    birth_date  	DATE NOT NULL,
    gender			ENUM ('Nam', 'Nữ', 'Chưa đặt') DEFAULT 'Chưa đặt',
    phone       	VARCHAR(15) UNIQUE,
    email       	VARCHAR(100) UNIQUE,
    address			VARCHAR(100),
    cccd			VARCHAR(12) UNIQUE,
    avatar      	VARCHAR(100),

    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS players (
                                       player_id     	INT AUTO_INCREMENT PRIMARY KEY,
                                       account_id  	INT UNIQUE NOT NULL,
                                       balance     	DECIMAL(10,2) DEFAULT 0.00,

    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
    );

-- Lịch sử nạp tiền của người chơi
CREATE TABLE IF NOT EXISTS  transactions (
                                             transaction_id  INT AUTO_INCREMENT PRIMARY KEY,
                                             player_id       INT NOT NULL,
                                             amount          INT NOT NULL,
                                             payment_method  ENUM('Tiền mặt', 'Chuyển khoản') NOT NULL,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE
    );

-- Bảng phòng để thêm các máy tính
CREATE TABLE IF NOT EXISTS rooms (
                                     room_id     		INT AUTO_INCREMENT PRIMARY KEY,
                                     room_name   		VARCHAR(50) NOT NULL,
    max_computers   	INT NOT NULL,
    type		   		ENUM('Phòng thường', 'Phòng gaming', 'Phòng thi đấu') NOT NULL,
    status      		ENUM('Trống', 'Đang hoạt động', 'Bảo trì') DEFAULT 'Trống',
    created_at  		DATETIME DEFAULT CURRENT_TIMESTAMP
    );

-- Các linh kiện không cần vẫn chạy được của máy tính
CREATE TABLE IF NOT EXISTS mouse ( -- Chuột
                                     mouse_id       	INT AUTO_INCREMENT PRIMARY KEY,
                                     brand          	VARCHAR(50) NOT NULL,
    model          	VARCHAR(100) NOT NULL,
    purchase_date  	DATE,
    warranty_expiry DATE,
    status         	ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

CREATE TABLE IF NOT EXISTS keyboards ( -- Bàn phím
                                         keyboard_id    INT AUTO_INCREMENT PRIMARY KEY,
                                         brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

CREATE TABLE IF NOT EXISTS headphones (
                                          headphone_id   		INT AUTO_INCREMENT PRIMARY KEY,
                                          brand         		VARCHAR(50) NOT NULL,  -- Hãng sản xuất (Logitech, Razer, Sony, etc.)
    model         		VARCHAR(100) NOT NULL, -- Model cụ thể (VD: HyperX Cloud II, Sony WH-1000XM4)
    type          		ENUM('Wired', 'Wireless') NOT NULL, -- Loại kết nối (có dây hoặc không dây)
    purchase_date 		DATE,                   -- Ngày mua
    warranty_expiry 	DATE,                 -- Ngày hết hạn bảo hành
    status       		ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

CREATE TABLE IF NOT EXISTS monitors ( -- Màn hình
                                        monitor_id     INT AUTO_INCREMENT PRIMARY KEY,
                                        brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    size           FLOAT NOT NULL,
    refresh_rate   INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         	ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

CREATE TABLE IF NOT EXISTS roms ( -- ROM chứa BIOS/UEFI, nhưng nếu hệ thống đã khởi động từ ổ cứng hoặc USB thì không cần ROM hoạt động liên tục.
                                    rom_id         INT AUTO_INCREMENT PRIMARY KEY,
                                    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    type           ENUM('EPROM', 'EEPROM', 'Flash') NOT NULL,
    capacity       INT NOT NULL,  -- Dung lượng tính bằng MB
    purchase_date  DATE,
    warranty_expiry DATE,
    status         	ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

-- Các linh kiện cần để chạy máy tính
CREATE TABLE IF NOT EXISTS psus ( -- Nguồn điện
                                    psu_id         INT AUTO_INCREMENT PRIMARY KEY,
                                    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    wattage        INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         	ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

CREATE TABLE IF NOT EXISTS storages ( -- Bộ nhớ
                                        storage_id     INT AUTO_INCREMENT PRIMARY KEY,
                                        brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    type           ENUM('HDD', 'SSD', 'NVMe') NOT NULL,
    capacity       INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         	ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

CREATE TABLE IF NOT EXISTS rams ( -- Bộ nhớ tạm thời
                                    ram_id         INT AUTO_INCREMENT PRIMARY KEY,
                                    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    capacity       INT NOT NULL,
    speed          INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         	ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

CREATE TABLE IF NOT EXISTS gpus ( -- Card đồ họa
                                    gpu_id         INT AUTO_INCREMENT PRIMARY KEY,
                                    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    vram           INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         	ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

CREATE TABLE IF NOT EXISTS cpus ( -- Bộ xử lý
                                    cpu_id         INT AUTO_INCREMENT PRIMARY KEY,
                                    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    clock_speed    FLOAT NOT NULL,
    cores          INT NOT NULL,
    threads        INT NOT NULL,
    integrated_gpu BOOLEAN DEFAULT FALSE,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         	ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho'
    );

-- Mạch chủ
CREATE TABLE IF NOT EXISTS motherboards (
                                            motherboard_id      	INT AUTO_INCREMENT PRIMARY KEY,
                                            brand              		VARCHAR(50) NOT NULL,   -- Hãng sản xuất (ASUS, MSI, Gigabyte, etc.)
    model              		VARCHAR(100) NOT NULL,  -- Model cụ thể (VD: B450 Tomahawk, Z690 Aorus Elite)
    socket             		VARCHAR(20) NOT NULL,   -- Loại socket CPU hỗ trợ (LGA1700, AM4, etc.)
    chipset            		VARCHAR(50) NOT NULL,   -- Chipset của mainboard (Z690, B450, X570, etc.)

    ram_slots          		INT NOT NULL,           -- Số khe RAM (2, 4, 8...)
    max_ram            		INT NOT NULL,           -- Dung lượng RAM tối đa hỗ trợ (GB)
    ram_speed          		INT NOT NULL,           -- Tốc độ RAM tối đa hỗ trợ (MHz)

    storage_slots      		INT NOT NULL,           -- Tổng số khe lưu trữ (SATA + M.2)
    sata_ports         		INT NOT NULL,           -- Số cổng SATA hỗ trợ (HDD, SSD)
    m2_slots				INT NOT NULL,           -- Số khe M.2 (NVMe)
    max_storage_capacity 	INT NOT NULL,         -- Dung lượng lưu trữ tối đa hỗ trợ (GB hoặc TB)
    status             		ENUM('Trong kho', 'Đang sử dụng', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho',
    -- Khóa ngoại cho các linh kiện quan trọng
    cpu_id             		INT DEFAULT NULL,
    psu_id             		INT DEFAULT NULL,
    gpu_id             		INT DEFAULT NULL,

    purchase_date           DATE,
    warranty_expiry         DATE,

    FOREIGN KEY (cpu_id) REFERENCES cpus(cpu_id) ON DELETE SET NULL,	-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    FOREIGN KEY (psu_id) REFERENCES psus(psu_id) ON DELETE SET NULL,	-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    FOREIGN KEY (gpu_id) REFERENCES gpus(gpu_id) ON DELETE SET NULL 	-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    );

-- Bảng trung gian để cắm các thiết bị nhiều nhiều
CREATE TABLE IF NOT EXISTS motherboard_ram ( -- Mạch chủ với RAM
                                               motherboard_id  INT NOT NULL,
                                               ram_id         INT NOT NULL,

                                               PRIMARY KEY (motherboard_id, ram_id),
    FOREIGN KEY (motherboard_id) REFERENCES motherboards(motherboard_id) ON DELETE CASCADE,
    FOREIGN KEY (ram_id) REFERENCES rams(ram_id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS motherboard_storage ( -- Mạch chủ với bộ nhớ
                                                   motherboard_id  INT NOT NULL,
                                                   storage_id     INT NOT NULL,

                                                   PRIMARY KEY (motherboard_id, storage_id),
    FOREIGN KEY (motherboard_id) REFERENCES motherboards(motherboard_id) ON DELETE CASCADE,
    FOREIGN KEY (storage_id) REFERENCES storages(storage_id) ON DELETE CASCADE
    );

-- Máy tính
CREATE TABLE IF NOT EXISTS computers (
                                         computer_id    	INT AUTO_INCREMENT PRIMARY KEY,
                                         name           	VARCHAR(100) NOT NULL,
    price_per_hour	INT NOT NULL,
    motherboard_id 	INT NOT NULL,

    mouse_id       	INT DEFAULT NULL,
    keyboard_id    	INT DEFAULT NULL,
    monitor_id     	INT DEFAULT NULL,
    headphone_id   	INT DEFAULT NULL,
    rom_id         	INT DEFAULT NULL,
    room_id			INT DEFAULT NULL,

    status         	ENUM('Trong kho', 'Đang sử dụng', 'Thiếu linh kiện', 'Bảo trì', 'Hỏng', 'Đang chờ sử dụng') DEFAULT 'Trong kho',

    FOREIGN KEY (motherboard_id) REFERENCES motherboards(motherboard_id) ON DELETE CASCADE,
    FOREIGN KEY (mouse_id) REFERENCES mouse(mouse_id) ON DELETE SET NULL, 							-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    FOREIGN KEY (keyboard_id) REFERENCES keyboards(keyboard_id) ON DELETE SET NULL,					-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    FOREIGN KEY (monitor_id) REFERENCES monitors(monitor_id) ON DELETE SET NULL,					-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    FOREIGN KEY (headphone_id) REFERENCES headphones(headphone_id) ON DELETE SET NULL,				-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    FOREIGN KEY (rom_id) REFERENCES roms(rom_id) ON DELETE SET NULL,								-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE SET NULL								-- Khi xóa bộ phận nào đó thì nó sẽ chuyển thành NULL
    );

-- Bảng thức ăn
CREATE TABLE IF NOT EXISTS categories (
                                          category_id INT AUTO_INCREMENT PRIMARY KEY,
                                          name	    varchar(100) not null
    );

CREATE TABLE IF NOT EXISTS foods (
                                     food_id         INT AUTO_INCREMENT PRIMARY KEY,
                                     name            VARCHAR(100) UNIQUE NOT NULL,
    price           INT NOT NULL,
    category_id     INT NOT NULL,
    quantity        INT DEFAULT 0,
    image           VARCHAR(100),
    status          VARCHAR(10) GENERATED ALWAYS AS (CASE WHEN quantity > 0 THEN 'Còn hàng' ELSE 'Hết hàng' END) STORED,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE
    );

-- Bảng hóa đơn thức ăn
CREATE TABLE IF NOT EXISTS food_bills (
                                          bill_id     INT AUTO_INCREMENT PRIMARY KEY,
                                          player_id   INT DEFAULT NULL,  -- Mặc định NULL để hỗ trợ khách vãng lai
                                          staff_id    INT NOT NULL,  -- Bắt buộc có nhân viên xác nhận
                                          total_price INT NOT NULL DEFAULT 0.00,
                                          payment_method ENUM('Tiền mặt', 'Chuyển khoản') NOT NULL,
    status      ENUM('Chưa xử lý', 'Đã xử lý', 'Đã hủy') DEFAULT 'Chưa xử lý',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (player_id) REFERENCES players(player_id),
    FOREIGN KEY (staff_id) REFERENCES staffs(staff_id) ON DELETE CASCADE
    );

-- Bảng chi tiết món ăn trong hóa đơn
CREATE TABLE IF NOT EXISTS food_orders (
                                           bill_id  INT NOT NULL,
                                           food_id  INT NOT NULL,
                                           quantity INT NOT NULL CHECK (quantity > 0),

    PRIMARY KEY (bill_id, food_id),
    FOREIGN KEY (bill_id) REFERENCES food_bills(bill_id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES foods(food_id) ON DELETE CASCADE
    );

-- Bảng lịch sử chơi máy
CREATE TABLE IF NOT EXISTS computer_sessions (
                                                 session_id   INT AUTO_INCREMENT PRIMARY KEY,
                                                 player_id    INT DEFAULT NULL,
                                                 computer_id  INT NOT NULL,
                                                 start_time   DATETIME DEFAULT CURRENT_TIMESTAMP,
                                                 end_time     DATETIME DEFAULT NULL,
                                                 duration     INT GENERATED ALWAYS AS (TIMESTAMPDIFF(MINUTE, start_time, end_time)) STORED,
    total_cost   DECIMAL(10,2) NOT NULL DEFAULT 0.00,  -- Cột lưu giá tiền, cập nhật sau khi kết thúc phiên
    is_guest     BOOLEAN GENERATED ALWAYS AS (player_id IS NULL) STORED,

    FOREIGN KEY (player_id) REFERENCES players(player_id),
    FOREIGN KEY (computer_id) REFERENCES computers(computer_id) ON DELETE CASCADE
    );

-- Đủ 24 bảng là đúng
SELECT COUNT(*) AS SoLuongBang
FROM information_schema.tables
WHERE table_schema = 'net_gaming_management';

-- Trigger

DELIMITER $$

CREATE TRIGGER after_account_insert
    AFTER INSERT ON accounts
    FOR EACH ROW
BEGIN
    -- Nếu là Quản trị viên hoặc Nhân viên, thêm vào bảng staffs với thông tin mặc định
    IF NEW.role IN ('Quản trị viên', 'Nhân viên') THEN
        INSERT INTO staffs (account_id, full_name, birth_date, gender, phone, email, address, cccd, avatar)
        VALUES (NEW.account_id, 'Chưa cập nhật', '2000-01-01', 'Chưa đặt', NULL, NULL, NULL, NULL, NULL);

    -- Nếu là Người chơi, thêm vào bảng players với số dư mặc định là 0.00
    ELSEIF NEW.role = 'Người chơi' THEN
        INSERT INTO players (account_id, balance)
        VALUES (NEW.account_id, 0.00);
END IF;
END$$

DELIMITER ;



-- Thêm danh mục vào bảng categories
INSERT INTO categories (name) VALUES
                                  ('Món chính'),
                                  ('Đồ uống'),
                                  ('Tráng miệng');

-- Thêm món ăn vào bảng foods
INSERT INTO foods (name, price, category_id, quantity, image) VALUES
                                                                  ('Cơm gà xối mỡ', 50000, 1, 10, 'com-ga-xoi-mo.png'),
                                                                  ('Bún bò Huế', 45000, 1, 15, 'bun-bo-hue.png'),
                                                                  ('Phở tái chín', 40000, 1, 20, 'pho-tai-chin.png'),
                                                                  ('Cơm tấm sườn bì chả', 55000, 1, 12, 'com-tam-suon-bi-cha.png'),
                                                                  ('Mì Quảng tôm thịt', 48000, 1, 18, 'mi-quang-tom-thit.png'),
                                                                  ('Hủ tiếu Nam Vang', 47000, 1, 8, 'hu-tieu-nam-vang.png'),
                                                                  ('Bánh mì thịt nướng', 30000, 1, 25, 'banh-mi-thit-nuong.png'),

                                                                  ('Cà phê sữa đá', 25000, 2, 30, 'ca-phe-sua-da.png'),
                                                                  ('Trà đào cam sả', 35000, 2, 20, 'tra-dao-cam-sa.png'),
                                                                  ('Nước ép cam', 30000, 2, 18, 'nuoc-ep-cam.png'),
                                                                  ('Nước ép ổi', 28000, 2, 22, 'nuoc-ep-oi.png'),
                                                                  ('Sinh tố bơ', 40000, 2, 15, 'sinh-to-bo.png'),
                                                                  ('Trà sữa trân châu', 38000, 2, 25, 'tra-sua-tran-chau.png'),
                                                                  ('Soda chanh dây', 32000, 2, 10, 'soda-chanh-day.png'),

                                                                  ('Bánh flan', 20000, 3, 20, 'banh-flan.png'),
                                                                  ('Chè thập cẩm', 30000, 3, 18, 'che-thap-cam.png'),
                                                                  ('Kem dừa', 35000, 3, 12, 'kem-dua.jpg'),
                                                                  ('Sữa chua nếp cẩm', 25000, 3, 15, 'sua-chua-nep-cam.png'),
                                                                  ('Rau câu dừa', 22000, 3, 25, 'rau-cau-dua.png'),
                                                                  ('Bánh mochi', 40000, 3, 10, 'banh-mochi.png');

-- Thêm dữ liệu phòng
INSERT INTO rooms (room_name, max_computers, type, status)
VALUES ('Phòng chiến thắng', 20, 'Phòng thường', 'Trống'),
       ('Phòng hủy diệt', 20, 'Phòng thường', 'Trống'),
       ('Phòng bất bại', 20, 'Phòng thường', 'Trống'),
       ('Phòng chiến tướng', 20, 'Phòng thường', 'Trống'),
       ('Phòng thách đấu', 20, 'Phòng thường', 'Trống');

-- Thêm dữ liệu cho bảng mouse
INSERT INTO mouse(brand, model, purchase_date, warranty_expiry, status)
VALUES ('Logitech', 'Logitech G102', '2025-03-14', '2026-03-14', 'Đang sử dụng'),
       ('Logitech', 'Logitech G304', '2025-03-14', '2026-03-14', 'Đang sử dụng'),
       ('Logitech', 'Logitech G Pro X Superlight', '2025-03-14', '2026-03-14', 'Đang sử dụng'),
       ('Logitech', 'Logitech G Pro X Superlight 2', '2025-03-14', '2026-03-14', 'Đang sử dụng'),
       ('Logitech', 'Logitech MX Master 3', '2025-03-01', '2028-03-01', 'Đang sử dụng'),
       ('SteelSeries', 'SteelSeries Rival 3', '2024-12-01', '2025-12-01', 'Đang sử dụng'),
       ('SteelSeries', 'Aerox 5 Wireless', '2025-03-13', '2027-03-13', 'Đang sử dụng'),
       ('SteelSeries', 'Aerox 3 Wireless', '2025-02-15', '2026-02-15', 'Đang sử dụng'),
       ('SteelSeries', 'Prime Wireless', '2024-11-01', '2026-11-01', 'Đang sử dụng'),
       ('Corsair', 'Corsair Harpoon RGB', '2025-02-15', '2026-02-15', 'Đang sử dụng'),
       ('Corsair', 'Corsair Harpoon RGB', '2025-02-15', '2026-02-15', 'Đang sử dụng'),
       ('Razer', 'Razer Basilisk V3', '2024-11-20', '2025-11-20', 'Đang sử dụng'),
       ('Razer', 'Razer DeathAdder V2', '2025-01-10', '2027-01-10', 'Đang sử dụng'),
       ('HyperX', 'HyperX Pulsefire Surge', '2025-03-13', '2026-03-13', 'Đang sử dụng'),
       ('Zowie', 'Zowie EC2', '2025-02-28', '2027-02-28', 'Đang sử dụng'),
       ('DareU', 'DareU A950 Pro', '2025-03-13', '2027-03-13', 'Đang sử dụng'),
       ('DareU', 'DareU A980 Pro Max', '2025-01-15', '2028-01-15', 'Đang sử dụng'),
       ('DareU', 'DareU A955', '2024-12-20', '2026-12-20', 'Đang sử dụng'),
       ('DareU', 'DareU EM901X', '2025-02-10', '2026-02-10', 'Đang sử dụng'),
       ('DareU', 'DareU A970', '2025-03-01', '2026-03-01', 'Đang sử dụng'),
       ('Logitech', 'Logitech G102', '2025-03-14', '2026-03-14', 'Trong kho'),
       ('Logitech', 'Logitech G304', '2025-03-14', '2026-03-14', 'Trong kho'),
       ('Logitech', 'Logitech G Pro X Superlight', '2025-03-14', '2026-03-14', 'Trong kho'),
       ('Logitech', 'Logitech G Pro X Superlight 2', '2025-03-14', '2026-03-14', 'Trong kho'),
       ('Logitech', 'Logitech MX Master 3', '2025-03-01', '2028-03-01', 'Trong kho');


-- Thêm dữ liệu cho bảng keyboards
INSERT INTO keyboards(brand, model, purchase_date, warranty_expiry, status)
VALUES ('Logitech', 'Logitech G Pro X TKL', '2025-03-14', '2027-03-14', 'Đang sử dụng'),
       ('Razer', 'Razer BlackWidow V4 Pro', '2025-02-10', '2026-02-10', 'Đang sử dụng'),
       ('SteelSeries', 'SteelSeries Apex Pro Mini', '2024-12-15', '2026-12-15', 'Đang sử dụng'),
       ('Corsair', 'Corsair K95 RGB Platinum', '2025-01-05', '2027-01-05', 'Đang sử dụng'),
       ('Keychron', 'Keychron K8 Pro', '2025-03-13', '2026-03-13', 'Đang sử dụng'),
       ('Ducky', 'Ducky One 3 TKL', '2025-02-20', '2027-02-20', 'Đang sử dụng'),
       ('Logitech', 'Logitech MX Keys', '2024-11-25', '2025-11-25', 'Đang sử dụng'),
       ('Razer', 'Raxer Huntsman Mini', '2025-03-01', '2026-03-01', 'Đang sử dụng'),
       ('SteelSeries', 'SteelSeries Apex 7 TKL', '2025-01-15', '2026-01-15', 'Đang sử dụng'),
       ('Corsair', 'Corsair K70 RGB TKL', '2025-02-28', '2027-02-28', 'Đang sử dụng'),
       ('DarkAlien', 'DarkAlien R65', '2025-01-24', '2027-01-24', 'Đang sử dụng'),
       ('DarkAlien', 'DarkAlien R65', '2025-01-24', '2027-01-24', 'Đang sử dụng'),
       ('DarkAlien', 'DarkAlien R65', '2025-01-24', '2027-01-24', 'Đang sử dụng'),
       ('HyperX', 'HyperX Alloy Origins 60', '2024-12-01', '2025-12-01', 'Đang sử dụng'),
       ('Logitech', 'Logitech G915 TKL', '2025-03-10', '2028-03-10', 'Đang sử dụng'),
       ('Razer', 'Razer DeathStalker V2', '2025-02-05', '2026-02-05', 'Đang sử dụng'),
       ('SteelSeries', 'SteelSeries Apex 3', '2025-03-12', '2026-03-12', 'Đang sử dụng'),
       ('Corsair', 'Corsair K100 RGB', '2024-11-10', '2026-11-10', 'Đang sử dụng'),
       ('Keychron', 'Keychron Q1', '2025-01-25', '2027-01-25', 'Đang sử dụng'),
       ('Ducky', 'Ducky Shine 7', '2025-03-07', '2026-03-07', 'Đang sử dụng'),
       ('HyperX', 'HyperX Alloy Elite 2', '2025-02-15', '2026-02-15', 'Đang sử dụng'),
       ('Logitech', 'Logitech G613', '2024-12-20', '2025-12-20', 'Đang sử dụng'),
       ('Razer', 'Razer Ornata V3', '2025-03-13', '2026-03-13', 'Đang sử dụng'),
       ('Keychron', 'Keychron Q1', '2025-01-25', '2027-01-25', 'Trong kho'),
       ('Ducky', 'Ducky Shine 7', '2025-03-07', '2026-03-07', 'Trong kho'),
       ('HyperX', 'HyperX Alloy Elite 2', '2025-02-15', '2026-02-15', 'Trong kho'),
       ('Logitech', 'Logitech G613', '2024-12-20', '2025-12-20', 'Trong kho'),
       ('Razer', 'Razer Ornata V3', '2025-03-13', '2026-03-13', 'Trong kho');


-- Thêm dữ liệu cho bảng headphones
INSERT INTO headphones(brand, model, type, purchase_date, warranty_expiry, status)
VALUES ('Logitech', 'Logitech G733', 'Wireless', '2025-03-14', '2026-03-14', 'Đang sử dụng'),
       ('Razer', 'Razer BlackShark V2', 'Wired', '2025-02-10', '2027-02-10', 'Đang sử dụng'),
       ('Sony', 'Sony WH-1000XM5', 'Wireless', '2024-12-15', '2027-12-15', 'Đang sử dụng'),
       ('HyperX', 'HyperX Cloud II', 'Wired', '2025-01-05', '2026-01-05', 'Đang sử dụng'),
       ('SteelSeries', 'SteelSeries Arctis Nova Pro', 'Wireless', '2025-03-13', '2028-03-13', 'Đang sử dụng'),
       ('Sennheiser', 'Sennheiser HD 660S', 'Wired', '2025-02-20', '2027-02-20', 'Đang sử dụng'),
       ('Logitech', 'Logitech G435', 'Wireless', '2024-11-25', '2025-11-25', 'Đang sử dụng'),
       ('Razer', 'Razer Kraken V3', 'Wired', '2025-03-01', '2026-03-01', 'Đang sử dụng'),
       ('Sony', 'Sony WF-1000XM4', 'Wireless', '2025-01-15', '2026-01-15', 'Đang sử dụng'),
       ('HyperX', 'HyperX Cloud Alpha', 'Wired', '2025-02-28', '2026-02-28', 'Đang sử dụng'),
       ('SteelSeries', 'SteelSeries Arctis 7+', 'Wireless', '2024-12-01', '2026-12-01', 'Đang sử dụng'),
       ('Logitech', 'Logitech Zone Vibe 100', 'Wireless', '2025-03-10', '2026-03-10', 'Đang sử dụng'),
       ('Razer', 'Razer Barracuda X', 'Wireless', '2025-02-05', '2026-02-05', 'Đang sử dụng'),
       ('Sony', 'Sony MDR-7506', 'Wired', '2025-03-12', '2026-03-12', 'Đang sử dụng'),
       ('HyperX', 'HyperX Cloud Stinger', 'Wired', '2024-11-10', '2025-11-10', 'Đang sử dụng'),
       ('SteelSeries', 'SteelSeries Arctis 1', 'Wired', '2025-01-25', '2026-01-25', 'Đang sử dụng'),
       ('Sennheiser', 'Sennheiser Momentum 4', 'Wireless', '2025-03-07', '2028-03-07', 'Đang sử dụng'),
       ('Logitech', 'Logitech G Pro X', 'Wired', '2025-02-15', '2026-02-15', 'Đang sử dụng'),
       ('Razer', 'Razer Nari Ultimate', 'Wireless', '2024-12-20', '2026-12-20', 'Đang sử dụng'),
       ('Sony', 'Sony Pulse 3D', 'Wireless', '2025-03-13', '2026-03-13', 'Đang sử dụng'),
       ('SteelSeries', 'SteelSeries Arctis 1', 'Wired', '2025-01-25', '2026-01-25', 'Trong kho'),
       ('Sennheiser', 'Sennheiser Momentum 4', 'Wireless', '2025-03-07', '2028-03-07', 'Trong kho'),
       ('Logitech', 'Logitech G Pro X', 'Wired', '2025-02-15', '2026-02-15', 'Trong kho'),
       ('Razer', 'Razer Nari Ultimate', 'Wireless', '2024-12-20', '2026-12-20', 'Trong kho'),
       ('Sony', 'Sony Pulse 3D', 'Wireless', '2025-03-13', '2026-03-13', 'Trong kho');


-- Thêm dữ liệu cho bảng monitors
INSERT INTO monitors (brand, model, size, refresh_rate, purchase_date, warranty_expiry, status)
VALUES ('ASUS', 'ASUS TUF Gaming VG27AQ', 27.0, 165, '2025-03-14', '2027-03-14', 'Đang sử dụng'),
       ('Dell', 'Dell Alienware AW2723DF', 27.0, 280, '2025-02-10', '2028-02-10', 'Đang sử dụng'),
       ('LG', ' LG UltraGear 27GP950-B', 27.0, 144, '2024-12-15', '2026-12-15', 'Đang sử dụng'),
       ('Samsung', 'Samsung Odyssey G7', 32.0, 240, '2025-01-05', '2027-01-05', 'Đang sử dụng'),
       ('Acer', 'Acer Predator XB273U', 27.0, 270, '2025-03-13', '2028-03-13', 'Đang sử dụng'),
       ('ASUS', 'ASUS ROG Swift PG259QN', 24.5, 360, '2025-02-20', '2028-02-20', 'Đang sử dụng'),
       ('Dell', 'Dell S2721DGF', 27.0, 165, '2024-11-25', '2025-11-25', 'Đang sử dụng'),
       ('LG', 'LG UltraGear 32GP850-B', 32.0, 180, '2025-03-01', '2027-03-01', 'Đang sử dụng'),
       ('Samsung', 'Samsung Odyssey Neo G8', 32.0, 240, '2025-01-15', '2028-01-15', 'Đang sử dụng'),
       ('Acer', 'Acer Nitro XV272U', 27.0, 170, '2025-02-28', '2026-02-28', 'Đang sử dụng'),
       ('MSI', 'MSI Optix MAG274QRF-QD', 27.0, 165, '2024-12-01', '2026-12-01', 'Đang sử dụng'),
       ('ASUS', 'ASUS ROG Strix XG27UCS', 27.0, 160, '2025-03-10', '2027-03-10', 'Đang sử dụng'),
       ('Dell', 'Dell Alienware AW3423DWF', 34.0, 165, '2025-02-05', '2028-02-05', 'Đang sử dụng'),
       ('LG', 'LG UltraGear 24GN650-B', 24.0, 144, '2025-03-12', '2026-03-12', 'Đang sử dụng'),
       ('Samsung', 'Samsung Odyssey G5', 27.0, 144, '2024-11-10', '2025-11-10', 'Đang sử dụng'),
       ('Acer', 'Acer Predator X34', 34.0, 180, '2025-01-25', '2027-01-25', 'Đang sử dụng'),
       ('MSI', 'MSI Optix MPG321UR-QD', 32.0, 144, '2025-03-07', '2028-03-07', 'Đang sử dụng'),
       ('ASUS', 'ASUS TUF Gaming VG279QM', 27.0, 280, '2025-02-15', '2027-02-15', 'Đang sử dụng'),
       ('Dell', 'Dell G3223Q', 32.0, 144, '2024-12-20', '2026-12-20', 'Đang sử dụng'),
       ('LG', 'LG UltraGear 27GL850-B', 27.0, 144, '2025-03-13', '2026-03-13', 'Đang sử dụng'),
       ('Acer', 'Acer Predator X34', 34.0, 180, '2025-01-25', '2027-01-25', 'Trong kho'),
       ('MSI', 'MSI Optix MPG321UR-QD', 32.0, 144, '2025-03-07', '2028-03-07', 'Trong kho'),
       ('ASUS', 'ASUS TUF Gaming VG279QM', 27.0, 280, '2025-02-15', '2027-02-15', 'Trong kho'),
       ('Dell', 'Dell G3223Q', 32.0, 144, '2024-12-20', '2026-12-20', 'Trong kho'),
       ('LG', 'LG UltraGear 27GL850-B', 27.0, 144, '2025-03-13', '2026-03-13', 'Trong kho');

-- Thêm dữ liệu cho bảng roms
INSERT INTO roms (brand, model, type, capacity, purchase_date, warranty_expiry, status)
VALUES ('AMI', 'AMI BIOS 2023', 'Flash', 32, '2025-03-14', '2027-03-14', 'Đang sử dụng'),
       ('Phoenix', 'Phoenix SecureCore Tiano', 'EEPROM', 16, '2025-02-10', '2026-02-10', 'Đang sử dụng'),
       ('Award', 'Award BIOS Modular', 'Flash', 64, '2024-12-15', '2026-12-15', 'Đang sử dụng'),
       ('Intel', 'Intel UEFI v2.8', 'Flash', 128, '2025-01-05', '2028-01-05', 'Đang sử dụng'),
       ('AMI', 'AMI Aptio V', 'Flash', 32, '2025-03-13', '2027-03-13', 'Đang sử dụng'),
       ('Phoenix', 'Phoenix BIOS 4.0', 'EPROM', 8, '2025-02-20', '2026-02-20', 'Đang sử dụng'),
       ('Award', 'AwardBIOS Elite', 'EEPROM', 16, '2024-11-25', '2025-11-25', 'Đang sử dụng'),
       ('Intel', 'Intel UEFI v2.7', 'Flash', 64, '2025-03-01', '2027-03-01', 'Đang sử dụng'),
       ('AMI', 'AMI BIOS 2024', 'Flash', 32, '2025-01-15', '2026-01-15', 'Đang sử dụng'),
       ('Phoenix', 'Phoenix SecureCore DXE', 'Flash', 64, '2025-02-28', '2027-02-28', 'Đang sử dụng'),
       ('Award', 'Award BIOS Legacy', 'EPROM', 8, '2024-12-01', '2025-12-01', 'Đang sử dụng'),
       ('Intel', 'Intel UEFI v2.9', 'Flash', 128, '2025-03-10', '2028-03-10', 'Đang sử dụng'),
       ('AMI', 'AMI Aptio 6', 'Flash', 32, '2025-02-05', '2026-02-05', 'Đang sử dụng'),
       ('Phoenix', 'Phoenix TrustedCore', 'EEPROM', 16, '2025-03-12', '2027-03-12', 'Đang sử dụng'),
       ('Award', 'Award BIOS Pro', 'Flash', 64, '2024-11-10', '2026-11-10', 'Đang sử dụng'),
       ('Intel', 'Intel UEFI v2.6', 'Flash', 32, '2025-01-25', '2026-01-25', 'Đang sử dụng'),
       ('AMI', 'AMI BIOS Core', 'EEPROM', 16, '2025-03-07', '2027-03-07', 'Đang sử dụng'),
       ('Phoenix', 'Phoenix BIOS 5.0', 'EPROM', 8, '2025-02-15', '2026-02-15', 'Đang sử dụng'),
       ('Award', 'Award BIOS Ultra', 'Flash', 64, '2024-12-20', '2026-12-20', 'Đang sử dụng'),
       ('Intel', 'Intel UEFI v3.0', 'Flash', 128, '2025-03-13', '2028-03-13', 'Đang sử dụng'),
       ('Intel', 'Intel UEFI v2.6', 'Flash', 32, '2025-01-25', '2026-01-25', 'Trong kho'),
       ('AMI', 'AMI BIOS Core', 'EEPROM', 16, '2025-03-07', '2027-03-07', 'Trong kho'),
       ('Phoenix', 'Phoenix BIOS 5.0', 'EPROM', 8, '2025-02-15', '2026-02-15', 'Trong kho'),
       ('Award', 'Award BIOS Ultra', 'Flash', 64, '2024-12-20', '2026-12-20', 'Trong kho'),
       ('Intel', 'Intel UEFI v3.0', 'Flash', 128, '2025-03-13', '2028-03-13', 'Trong kho');
-- Thêm dữ liệu cho bảng psus
INSERT INTO psus (brand, model, wattage, purchase_date, warranty_expiry, status)
VALUES ('Corsair', 'Corsair RM750x', 750, '2025-03-14', '2030-03-14', 'Đang sử dụng'),
       ('EVGA', 'EVGA SuperNOVA 850 G5', 850, '2025-02-10', '2032-02-10', 'Đang sử dụng'),
       ('Seasonic', 'Seasonic Focus GX-650', 650, '2024-12-15', '2034-12-15', 'Đang sử dụng'),
       ('Cooler Master', 'Cooler Master MWE Gold 750', 750, '2025-01-05', '2030-01-05', 'Đang sử dụng'),
       ('Corsair', 'Corsair HX1200', 1200, '2025-03-13', '2035-03-13', 'Đang sử dụng'),
       ('EVGA', 'EVGA 650 GQ', 650, '2025-02-20', '2028-02-20', 'Đang sử dụng'),
       ('Seasonic', 'Seasonic Prime TX-1000', 1000, '2024-11-25', '2034-11-25', 'Đang sử dụng'),
       ('Cooler Master', 'Cooler Master V850 SFX', 850, '2025-03-01', '2030-03-01', 'Đang sử dụng'),
       ('Corsair', 'Corsair SF600', 600, '2025-01-15', '2027-01-15', 'Đang sử dụng'),
       ('EVGA', 'EVGA SuperNOVA 750 T2', 750, '2025-02-28', '2035-02-28', 'Đang sử dụng'),
       ('Seasonic', 'Seasonic Focus SGX-500', 500, '2024-12-01', '2029-12-01', 'Đang sử dụng'),
       ('Cooler Master', 'Cooler Master MasterWatt 550', 550, '2025-03-10', '2028-03-10', 'Đang sử dụng'),
       ('Corsair', 'Corsair AX860', 860, '2025-02-05', '2035-02-05', 'Đang sử dụng'),
       ('EVGA', 'EVGA SuperNOVA 1000 P2', 1000, '2025-03-12', '2032-03-12', 'Đang sử dụng'),
       ('Seasonic', 'Seasonic Snow Silent 750', 750, '2024-11-10', '2034-11-10', 'Đang sử dụng'),
       ('Cooler Master', 'Cooler Master V1200 Platinum', 1200, '2025-01-25', '2030-01-25', 'Đang sử dụng'),
       ('Corsair', 'Corsair RM650i', 650, '2025-03-07', '2030-03-07', 'Đang sử dụng'),
       ('EVGA', 'EVGA 450 BR', 450, '2025-02-15', '2026-02-15', 'Đang sử dụng'),
       ('Seasonic', 'Seasonic Prime GX-850', 850, '2024-12-20', '2034-12-20', 'Đang sử dụng'),
       ('Cooler Master', 'Cooler Master MWE Bronze 600', 600, '2025-03-13', '2028-03-13', 'Đang sử dụng'),
       ('Cooler Master', 'Cooler Master V1200 Platinum', 1200, '2025-01-25', '2030-01-25', 'Đang sử dụng'),
       ('Corsair', 'Corsair RM650i', 650, '2025-03-07', '2030-03-07', 'Trong kho'),
       ('EVGA', 'EVGA 450 BR', 450, '2025-02-15', '2026-02-15', 'Trong kho'),
       ('Seasonic', 'Seasonic Prime GX-850', 850, '2024-12-20', '2034-12-20', 'Trong kho'),
       ('Cooler Master', 'Cooler Master MWE Bronze 600', 600, '2025-03-13', '2028-03-13', 'Trong kho');


-- Thêm dữ liệu cho bảng storages
INSERT INTO storages (brand, model, type, capacity, purchase_date, warranty_expiry, status)
VALUES ('Samsung', 'Samsung 970 EVO Plus', 'NVMe', 1000, '2025-03-14', '2030-03-14', 'Đang sử dụng'),
       ('Western Digital', 'Western Digital WD Black SN850X', 'NVMe', 2000, '2025-02-10', '2030-02-10', 'Đang sử dụng'),
       ('Samsung', 'Samsung 990 PRO', 'NVMe', 1000, '2025-03-13', '2030-03-13', 'Đang sử dụng'),
       ('Seagate', 'Seagate FireCuda 530', 'NVMe', 500, '2024-11-25', '2029-11-25', 'Đang sử dụng'),
       ('Crucial', 'Crucial P3 Plus', 'NVMe', 2000, '2025-03-01', '2028-03-01', 'Đang sử dụng'),
       ('Western Digital', 'Western Digital WD Black SN770', 'NVMe', 1000, '2025-03-12', '2030-03-12', 'Đang sử dụng'),
       ('Seagate', 'Seagate BarraCuda 510', 'NVMe', 512, '2024-11-10', '2029-11-10', 'Đang sử dụng'),
       ('Crucial', 'Crucial P5', 'NVMe', 1000, '2025-01-25', '2030-01-25', 'Đang sử dụng'),
       ('Samsung', 'Samsung 980', 'NVMe', 500, '2025-03-07', '2028-03-07', 'Đang sử dụng'),
       ('Crucial', 'Crucial P2', 'NVMe', 2000, '2025-03-13', '2030-03-13', 'Đang sử dụng'),
       ('Western Digital', 'Western Digital WD Blue 3D', 'SSD', 256, '2025-02-20', '2028-02-20', 'Đang sử dụng'),
       ('Crucial', 'Crucial MX500', 'SSD', 500, '2025-01-05', '2028-01-05', 'Đang sử dụng'),
       ('Samsung', ' Samsung 870 QVO', 'SSD', 1000, '2025-01-15', '2028-01-15', 'Đang sử dụng'),
       ('Samsung', 'Samsung 860 EVO', 'SSD', 256, '2025-02-05', '2027-02-05', 'Đang sử dụng'),
       ('Crucial', 'Crucial BX500', 'SSD', 500, '2025-03-10', '2028-03-10', 'Đang sử dụng'),
       ('Western Digital', 'Western Digital WD Green', 'SSD', 480, '2025-02-15', '2028-02-15', 'Đang sử dụng'),
       ('Seagate', 'Seagate Barracuda', 'HDD', 4000, '2024-12-15', '2027-12-15', 'Đang sử dụng'),
       ('Western Digital', 'Western Digital WD Red', 'HDD', 2000, '2025-02-28', '2028-02-28', 'Đang sử dụng'),
       ('Seagate', 'Seagate IronWolf', 'HDD', 4000, '2024-12-01', '2027-12-01', 'Đang sử dụng'),
       ('Seagate', 'Seagate Expansion', 'HDD', 2000, '2024-12-20', '2027-12-20', 'Đang sử dụng'),
       ('Western Digital', 'Western Digital WD Green', 'SSD', 480, '2025-02-15', '2028-02-15', 'Đang sử dụng'),
       ('Seagate', 'Seagate Barracuda', 'HDD', 4000, '2024-12-15', '2027-12-15', 'Trong kho'),
       ('Western Digital', 'Western Digital WD Red', 'HDD', 2000, '2025-02-28', '2028-02-28', 'Trong kho'),
       ('Seagate', 'Seagate IronWolf', 'HDD', 4000, '2024-12-01', '2027-12-01', 'Trong kho'),
       ('Seagate', 'Seagate Expansion', 'HDD', 2000, '2024-12-20', '2027-12-20', 'Trong kho');

-- thêm dữ liệu cho bảng rams
INSERT INTO rams (brand, model, capacity, speed, purchase_date, warranty_expiry, status)
VALUES ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-14', '2035-03-14', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-13', '2035-03-13', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-01-15', '2035-01-15', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-02-05', '2035-02-05', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-07', '2035-03-07', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-14', '2035-03-14', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-13', '2035-03-13', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-01-15', '2035-01-15', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-02-05', '2035-02-05', 'Đang sử dụng'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-07', '2035-03-07', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-10', '2035-02-10', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-20', '2035-02-20', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-28', '2035-02-28', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-03-12', '2035-03-12', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-15', '2035-02-15', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-10', '2035-02-10', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-20', '2035-02-20', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-28', '2035-02-28', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-03-12', '2035-03-12', 'Đang sử dụng'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-15', '2035-02-15', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-15', '2034-12-15', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-25', '2034-11-25', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-01', '2034-12-01', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-10', '2034-11-10', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-20', '2034-12-20', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-15', '2034-12-15', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-25', '2034-11-25', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-01', '2034-12-01', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-10', '2034-11-10', 'Đang sử dụng'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-20', '2034-12-20', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-05', '2030-01-05', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-01', '2030-03-01', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-10', '2030-03-10', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-25', '2030-01-25', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-13', '2030-03-13', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-05', '2030-01-05', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-01', '2030-03-01', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-10', '2030-03-10', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-25', '2030-01-25', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-13', '2030-03-13', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-05', '2030-01-05', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-01', '2030-03-01', 'Đang sử dụng'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-10', '2030-03-10', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-25', '2030-01-25', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-13', '2030-03-13', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-05', '2030-01-05', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-01', '2030-03-01', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-10', '2030-03-10', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-25', '2030-01-25', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-13', '2030-03-13', 'Trong kho');

-- thêm dữ lieu cho bảng gpus
INSERT INTO gpus (brand, model, vram, purchase_date, warranty_expiry, status)
VALUES ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-14', '2028-03-14', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-13', '2028-03-13', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-01-15', '2028-01-15', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-02-05', '2028-02-05', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-07', '2028-03-07', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-10', '2029-02-10', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-20', '2029-02-20', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-28', '2029-02-28', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-03-12', '2029-03-12', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-15', '2029-02-15', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-15', '2029-12-15', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-11-25', '2029-11-25', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-01', '2029-12-01', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-11-10', '2029-11-10', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-20', '2029-12-20', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-01-05', '2030-01-05', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-01', '2030-03-01', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-10', '2030-03-10', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-01-25', '2030-01-25', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-13', '2030-03-13', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-01-05', '2030-01-05', 'Đang sử dụng'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-01', '2030-03-01', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-10', '2030-03-10', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-01-25', '2030-01-25', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-13', '2030-03-13', 'Trong kho');


-- thêm dữ liệu cho bảng cpus
INSERT INTO cpus (brand, model, clock_speed, cores, threads, integrated_gpu, purchase_date, warranty_expiry, status)
VALUES ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-14', '2028-03-14', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-13', '2028-03-13', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-01-15', '2028-01-15', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-02-05', '2028-02-05', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-07', '2028-03-07', 'Đang sử dụng'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-20', '2028-02-20', 'Đang sử dụng'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-10', '2028-02-10', 'Đang sử dụng'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-28', '2028-02-28', 'Đang sử dụng'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-03-12', '2028-03-12', 'Đang sử dụng'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-15', '2028-02-15', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-15', '2029-12-15', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-11-25', '2029-11-25', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-01', '2029-12-01', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-11-10', '2029-11-10', 'Đang sử dụng'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-20', '2029-12-20', 'Đang sử dụng'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-01-05', '2030-01-05', 'Đang sử dụng'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-01', '2030-03-01', 'Đang sử dụng'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-10', '2030-03-10', 'Đang sử dụng'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-01-25', '2030-01-25', 'Đang sử dụng'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-13', '2030-03-13', 'Đang sử dụng'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-01-05', '2030-01-05', 'Đang sử dụng'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-01', '2030-03-01', 'Trong kho'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-10', '2030-03-10', 'Trong kho'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-01-25', '2030-01-25', 'Trong kho'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-13', '2030-03-13', 'Trong kho');

-- thêm dữ liệu cho bảng motherboards chưa gồm cpu, gpu, psu
INSERT INTO motherboards (brand, model, socket, chipset, ram_slots, max_ram, ram_speed, storage_slots, sata_ports, m2_slots, max_storage_capacity, status, cpu_id, psu_id, gpu_id, purchase_date, warranty_expiry)
VALUES ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Đang sử dụng', 1, 1, 1, '2025-03-14', '2028-03-14'),
       ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Đang sử dụng', 2, 2, 3, '2025-03-13', '2028-03-13'),
       ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Đang sử dụng', 3, 3, 3, '2025-01-15', '2028-01-15'),
       ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Đang sử dụng', 4, 4, 4, '2025-02-05', '2028-02-05'),
       ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Đang sử dụng', 5, 5, 5, '2025-03-07', '2028-03-07'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Đang sử dụng', 6, 6, 6, '2025-02-10', '2028-02-10'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Đang sử dụng', 7, 7, 7, '2025-02-20', '2028-02-20'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Đang sử dụng', 8, 8, 8, '2025-02-28', '2028-02-28'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Đang sử dụng', 9, 9, 9, '2025-03-12', '2028-03-12'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Đang sử dụng', 10, 10, 10, '2025-02-15', '2028-02-15'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Đang sử dụng', 11, 11, 11, '2024-12-15', '2029-12-15'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Đang sử dụng', 12, 12, 12, '2024-11-25', '2029-11-25'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Đang sử dụng', 13, 13, 13, '2024-12-01', '2029-12-01'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Đang sử dụng', 14, 14, 14, '2024-11-10', '2029-11-10'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Đang sử dụng', 15, 15, 15, '2024-12-20', '2029-12-20'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Đang sử dụng', 16, 16, 16, '2025-01-05', '2030-01-05'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Đang sử dụng', 17, 17, 17, '2025-03-01', '2030-03-01'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Đang sử dụng', 18, 18, 18, '2025-03-10', '2030-03-10'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Đang sử dụng', 19, 19, 19, '2025-01-25', '2030-01-25'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Đang sử dụng', 20, 20, 20, '2025-03-13', '2030-03-13'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', 21, 21, 21, '2025-03-13', '2030-03-13');

-- thêm dữ liệu vào bang motherboard_ram
INSERT INTO motherboard_ram (motherboard_id, ram_id) VALUES
                                                         (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6), (4, 7), (4, 8), (5, 9), (5, 10), (6, 11), (6, 12),
                                                         (7, 13), (7, 14), (8, 15), (8, 16), (9, 17), (9, 18), (10, 19), (10, 20), (11, 21), (11, 22), (12, 23),
                                                         (12, 24), (13, 25), (13, 26), (14, 27), (14, 28), (15, 29), (15, 30),  (16, 31), (16, 32), (17, 33), (17, 34),
                                                         (18, 35),(18, 36), (19, 37), (19, 38), (20, 39), (20, 40), (21, 41), (21, 42);

-- them du lieu cho bang motherboard_storage
INSERT INTO motherboard_storage (motherboard_id, storage_id)
VALUES (1, 20),
       (2, 19),
       (3, 18),
       (4, 17),
       (5, 16),
       (6, 15),
       (7, 14),
       (8, 13),
       (9, 12),
       (10, 11),
       (11, 10),
       (12, 9),
       (13, 8),
       (14, 7),
       (15, 6),
       (16, 5),
       (17, 4),
       (18, 3),
       (19, 2),
       (20, 1),
       (20, 21);

-- thêm dữ liệu vào bảng computers
INSERT INTO computers (name, price_per_hour, motherboard_id, mouse_id, keyboard_id, monitor_id, headphone_id, rom_id, room_id, status)
VALUES ('Máy thường Spoce 1', 10000.00, 1, 1, 1, 1, 1, 1, 1, 'Bảo trì'),
       ('Máy thường Spoce 2', 10000.00, 2, 2, 2, 2, 2, 2, 1, 'Bảo trì'),
       ('Máy thường Spoce 3', 10000.00, 3, 3, 3, 3, 3, 3, 1,  'Đang sử dụng'),
       ('Máy thường Spoce 4', 10000.00, 4, 4, 4, 4, 4, 4, 1, 'Bảo trì'),
       ('Máy thường Spoce 5', 10000.00, 5, 5, 5, 5, 5, 5, 1, 'Đang sử dụng'),
       ('Máy vip Spoce 1', 15000.00, 6, 6, 6, 6, 6, 6, 1, 'Bảo trì'),
       ('Máy vip Spoce 2', 15000.00, 7, 7, 7, 7, 7, 7, 1, 'Đang sử dụng'),
       ('Máy vip Spoce 3', 15000.00, 8, 8, 8, 8, 8, 8, 1, 'Bảo trì'),
       ('Máy vip Spoce 4', 15000.00, 9, 9, 9, 9, 9, 9, 1, 'Đang sử dụng'),
       ('Máy vip Spoce 5', 15000.00, 10, 10, 10, 10, 10, 10, 1, 'Đang sử dụng'),
       ('Máy cao cấp Spoce 1', 20000.00, 11, 11, 11, 11, 11, 11, 1, 'Bảo trì'),
       ('Máy cao cấp Spoce 2', 20000.00, 12, 12, 12, 12, 12, 12, 1, 'Đang sử dụng'),
       ('Máy cao cấp Spoce 3', 20000.00, 13, 13, 13, 13, 13, 13, 1, 'Bảo trì'),
       ('Máy cao cấp Spoce 4', 20000.00, 14, 14, 14, 14, 14, 14, 1, 'Đang sử dụng'),
       ('Máy cao cấp Spoce 5', 20000.00, 15, 15, 15, 15, 15, 15, 1, 'Đang sử dụng'),
       ('Siêu máy tính Spoce 1', 30000.00, 16, 16, 16, 16, 16, 16, 1, 'Đang sử dụng'),
       ('Siêu máy tính Spoce 2', 30000.00, 17, 17, 17, 17, 17, 17, 1, 'Bảo trì'),
       ('Siêu máy tính Spoce 3', 30000.00, 18, 18, 18, 18, 18, 18, 1, 'Đang sử dụng'),
       ('Siêu máy tính Spoce 4', 30000.00, 19, 19, 19, 19, 19, 19, 1, 'Đang sử dụng'),
       ('Siêu máy tính Spoce 5', 30000.00, 20, 20, 20, 20, 20, 20, 1, 'Đang sử dụng');


INSERT INTO accounts (username, password, role, status)
VALUES
    ('namduongit', MD5('NDuong205'), 'Quản trị viên', 'Offline'),
    ('luanchenh', MD5('luanChenh'), 'Quản trị viên', 'Offline');

-- Thêm 3 nhân viên
INSERT INTO accounts (username, password, role, status)
VALUES
    ('staff1', MD5('123456789'), 'Nhân viên', 'Offline'),
    ('staff2', MD5('123456789'), 'Nhân viên', 'Offline'),
    ('staff3', MD5('123456789'), 'Nhân viên', 'Offline');

INSERT INTO accounts (username, password, role, status)
VALUES
    ('ChienBinhMa', MD5('123456789'), 'Người chơi', 'Offline'),
    ('gaconNgauLoi', MD5('123456789'), 'Người chơi', 'Offline'),
    ('boanco', MD5('123456789'), 'Người chơi', 'Offline'),
    ('haycamnhannoidau', MD5('123456789'), 'Người chơi', 'Offline'),
    ('naruto', MD5('123456789'), 'Người chơi', 'Offline'),
    ('songokuuuu', MD5('123456789'), 'Người chơi', 'Offline'),
    ('cadic821', MD5('123456789'), 'Người chơi', 'Offline'),
    ('alibabaDepZai', MD5('123456789'), 'Người chơi', 'Offline'),
    ('DoMayVoDuoc', MD5('123456789'), 'Người chơi', 'Offline'),
    ('BeNgocLan', MD5('123456789'), 'Người chơi', 'Offline');

-- Thêm cột price vào bảng roms
ALTER TABLE roms
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

-- Thêm cột price vào bảng cpus
ALTER TABLE cpus
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

-- Thêm cột price vào bảng storages (Memory)
ALTER TABLE storages
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

-- Thêm cột price vào bảng gpus
ALTER TABLE gpus
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

-- Thêm cột price vào bảng motherboards
ALTER TABLE motherboards
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

-- Thêm cột price vào bảng mouse
ALTER TABLE mouse
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

-- Thêm cột price vào bảng keyboards
ALTER TABLE keyboards
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

-- Thêm cột price vào bảng monitors
ALTER TABLE monitors
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

-- Thêm cột price vào bảng headphones
ALTER TABLE headphones
    ADD COLUMN price DECIMAL(10,2) NOT NULL DEFAULT 0.00 AFTER model;

UPDATE roms SET price = CASE
                            WHEN model = 'Phoenix TrustedCore' THEN 800000.00
                            WHEN model = 'Phoenix SecureCore Tiano' THEN 1200000.00
                            WHEN model = 'Phoenix SecureCore DXE' THEN 1500000.00
                            WHEN model = 'Phoenix BIOS 5.0' THEN 1800000.00
                            WHEN model = 'Phoenix BIOS 4.0' THEN 1700000.00
                            WHEN model = 'Intel UEFI v3.0' THEN 1600000.00
                            WHEN model = 'Intel UEFI v2.9' THEN 1500000.00
                            WHEN model = 'Intel UEFI v2.8' THEN 1400000.00
                            WHEN model = 'Intel UEFI v2.7' THEN 1300000.00
                            WHEN model = 'Intel UEFI v2.6' THEN 1200000.00
                            WHEN model = 'AwardBIOS Elite' THEN 1100000.00
                            WHEN model = 'Award BIOS Ultra' THEN 1890000.00
                            WHEN model = 'Award BIOS Pro' THEN 1880000.00
                            WHEN model = 'Award BIOS Modular' THEN 1870000.00
                            WHEN model = 'Award BIOS Legacy' THEN 1860000.00
                            WHEN model = 'AMI BIOS Core' THEN 1850000.00
                            WHEN model = 'AMI BIOS 2024' THEN 1840000.00
                            WHEN model = 'AMI BIOS 2023' THEN 1870000.00
                            WHEN model = 'AMI Aptio V' THEN 1830000.00
                            WHEN model = 'AMI Aptio 6' THEN 1820000.00
                            ELSE 0.00
    END;

UPDATE cpus SET price = CASE
                            WHEN model = 'AMD Ryzen 5 5600X' THEN 4500000.00
                            WHEN model = 'Intel Core i5-13400' THEN 5500000.00
                            WHEN model = 'AMD Ryzen 7 7700X' THEN 8500000.00
                            WHEN model = 'Intel Core i9-13900K' THEN 14500000.00
                            ELSE 0.00
    END;

UPDATE storages SET price = CASE
                                WHEN model = 'Samsung 970 EVO Plus' THEN 2500000.00
                                WHEN model = 'Western Digital WD Black SN850X' THEN 4500000.00
                                WHEN model = 'Samsung 990 PRO' THEN 3000000.00
                                WHEN model = 'Seagate FireCuda 530' THEN 2000000.00
                                WHEN model = 'Crucial P3 Plus' THEN 3500000.00
                                WHEN model = 'Western Digital WD Black SN770' THEN 2200000.00
                                WHEN model = 'Seagate BarraCuda 510' THEN 1500000.00
                                WHEN model = 'Crucial P5' THEN 2300000.00
                                WHEN model = 'Samsung 980' THEN 1800000.00
                                WHEN model = 'Crucial P2' THEN 3200000.00
                                WHEN model = 'Western Digital WD Blue 3D' THEN 1200000.00
                                WHEN model = 'Crucial MX500' THEN 1400000.00
                                WHEN model = 'Samsung 870 QVO' THEN 2000000.00
                                WHEN model = 'Samsung 860 EVO' THEN 1000000.00
                                WHEN model = 'Crucial BX500' THEN 1100000.00
                                WHEN model = 'Western Digital WD Green' THEN 1300000.00
                                WHEN model = 'Seagate Barracuda' THEN 1500000.00
                                WHEN model = 'Western Digital WD Red' THEN 2000000.00
                                WHEN model = 'Seagate IronWolf' THEN 2500000.00
                                WHEN model = 'Seagate Expansion' THEN 1800000.00
                                ELSE 0.00
    END;

UPDATE gpus SET price = CASE
                            WHEN model = 'NVIDIA GTX 1660 Super' THEN 5500000.00
                            WHEN model = 'NVIDIA RTX 3060' THEN 8500000.00
                            WHEN model = 'NVIDIA RTX 4070' THEN 14500000.00
                            WHEN model = 'NVIDIA RTX 4090' THEN 45000000.00
                            ELSE 0.00
    END;

UPDATE motherboards SET price = CASE
                                    WHEN model = 'ASUS TUF Gaming B550-Plus' THEN 3500000.00
                                    WHEN model = 'MSI PRO B760M-A' THEN 4000000.00
                                    WHEN model = 'Gigabyte X670 AORUS Elite' THEN 6500000.00
                                    WHEN model = 'ASUS ROG Strix Z790-E' THEN 9500000.00
                                    ELSE 0.00
    END;

UPDATE mouse SET price = CASE
                             WHEN model = 'Logitech G102' THEN 500000.00
                             WHEN model = 'Logitech G304' THEN 800000.00
                             WHEN model = 'Logitech G Pro X Superlight' THEN 3000000.00
                             WHEN model = 'Logitech G Pro X Superlight 2' THEN 3500000.00
                             WHEN model = 'Logitech MX Master 3' THEN 2500000.00
                             WHEN model = 'SteelSeries Rival 3' THEN 600000.00
                             WHEN model = 'Aerox 5 Wireless' THEN 2000000.00
                             WHEN model = 'Aerox 3 Wireless' THEN 1800000.00
                             WHEN model = 'Prime Wireless' THEN 2500000.00
                             WHEN model = 'Corsair Harpoon RGB' THEN 700000.00
                             WHEN model = 'Razer Basilisk V3' THEN 1500000.00
                             WHEN model = 'Razer DeathAdder V2' THEN 1200000.00
                             WHEN model = 'HyperX Pulsefire Surge' THEN 900000.00
                             WHEN model = 'Zowie EC2' THEN 1800000.00
                             WHEN model = 'DareU A950 Pro' THEN 1200000.00
                             WHEN model = 'DareU A980 Pro Max' THEN 1500000.00
                             WHEN model = 'DareU A955' THEN 1000000.00
                             WHEN model = 'DareU EM901X' THEN 800000.00
                             WHEN model = 'DareU A970' THEN 900000.00
                             ELSE 0.00
    END;

UPDATE keyboards SET price = CASE
                                 WHEN model = 'Logitech G Pro X TKL' THEN 3000000.00
                                 WHEN model = 'Razer BlackWidow V4 Pro' THEN 4500000.00
                                 WHEN model = 'SteelSeries Apex Pro Mini' THEN 4000000.00
                                 WHEN model = 'Corsair K95 RGB Platinum' THEN 3500000.00
                                 WHEN model = 'Keychron K8 Pro' THEN 2000000.00
                                 WHEN model = 'Ducky One 3 TKL' THEN 2500000.00
                                 WHEN model = 'Logitech MX Keys' THEN 2500000.00
                                 WHEN model = 'Raxer Huntsman Mini' THEN 3000000.00
                                 WHEN model = 'SteelSeries Apex 7 TKL' THEN 2800000.00
                                 WHEN model = 'Corsair K70 RGB TKL' THEN 3200000.00
                                 WHEN model = 'DarkAlien R65' THEN 1800000.00
                                 WHEN model = 'HyperX Alloy Origins 60' THEN 2000000.00
                                 WHEN model = 'Logitech G915 TKL' THEN 5000000.00
                                 WHEN model = 'Razer DeathStalker V2' THEN 3500000.00
                                 WHEN model = 'SteelSeries Apex 3' THEN 1500000.00
                                 WHEN model = 'Corsair K100 RGB' THEN 4500000.00
                                 WHEN model = 'Keychron Q1' THEN 3000000.00
                                 WHEN model = 'Ducky Shine 7' THEN 3500000.00
                                 WHEN model = 'HyperX Alloy Elite 2' THEN 2500000.00
                                 WHEN model = 'Logitech G613' THEN 2000000.00
                                 WHEN model = 'Razer Ornata V3' THEN 1800000.00
                                 ELSE 0.00
    END;

UPDATE monitors SET price = CASE
                                WHEN model = 'ASUS TUF Gaming VG27AQ' THEN 7000000.00
                                WHEN model = 'Dell Alienware AW2723DF' THEN 15000000.00
                                WHEN model = 'LG UltraGear 27GP950-B' THEN 12000000.00
                                WHEN model = 'Samsung Odyssey G7' THEN 13000000.00
                                WHEN model = 'Acer Predator XB273U' THEN 14000000.00
                                WHEN model = 'ASUS ROG Swift PG259QN' THEN 16000000.00
                                WHEN model = 'Dell S2721DGF' THEN 8000000.00
                                WHEN model = 'LG UltraGear 32GP850-B' THEN 11000000.00
                                WHEN model = 'Samsung Odyssey Neo G8' THEN 20000000.00
                                WHEN model = 'Acer Nitro XV272U' THEN 7500000.00
                                WHEN model = 'MSI Optix MAG274QRF-QD' THEN 9000000.00
                                WHEN model = 'ASUS ROG Strix XG27UCS' THEN 8500000.00
                                WHEN model = 'Dell Alienware AW3423DWF' THEN 25000000.00
                                WHEN model = 'LG UltraGear 24GN650-B' THEN 6000000.00
                                WHEN model = 'Samsung Odyssey G5' THEN 6500000.00
                                WHEN model = 'Acer Predator X34' THEN 18000000.00
                                WHEN model = 'MSI Optix MPG321UR-QD' THEN 15000000.00
                                WHEN model = 'ASUS TUF Gaming VG279QM' THEN 9500000.00
                                WHEN model = 'Dell G3223Q' THEN 12000000.00
                                WHEN model = 'LG UltraGear 27GL850-B' THEN 9000000.00
                                ELSE 0.00
    END;

UPDATE headphones SET price = CASE
                                  WHEN model = 'Logitech G733' THEN 2500000.00
                                  WHEN model = 'Razer BlackShark V2' THEN 2000000.00
                                  WHEN model = 'Sony WH-1000XM5' THEN 8500000.00
                                  WHEN model = 'HyperX Cloud II' THEN 2000000.00
                                  WHEN model = 'SteelSeries Arctis Nova Pro' THEN 6000000.00
                                  WHEN model = 'Sennheiser HD 660S' THEN 9000000.00
                                  WHEN model = 'Logitech G435' THEN 1500000.00
                                  WHEN model = 'Razer Kraken V3' THEN 2500000.00
                                  WHEN model = 'Sony WF-1000XM4' THEN 6000000.00
                                  WHEN model = 'HyperX Cloud Alpha' THEN 2200000.00
                                  WHEN model = 'SteelSeries Arctis 7+' THEN 4000000.00
                                  WHEN model = 'Logitech Zone Vibe 100' THEN 2000000.00
                                  WHEN model = 'Razer Barracuda X' THEN 2500000.00
                                  WHEN model = 'Sony MDR-7506' THEN 3000000.00
                                  WHEN model = 'HyperX Cloud Stinger' THEN 1200000.00
                                  WHEN model = 'SteelSeries Arctis 1' THEN 1500000.00
                                  WHEN model = 'Sennheiser Momentum 4' THEN 7500000.00
                                  WHEN model = 'Logitech G Pro X' THEN 3000000.00
                                  WHEN model = 'Razer Nari Ultimate' THEN 4000000.00
                                  WHEN model = 'Sony Pulse 3D' THEN 2500000.00
                                  ELSE 0.00
    END;
