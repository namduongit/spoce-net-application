drop database if exists net_gaming_management;
create database if not exists net_gaming_management;
use net_gaming_management;


-- @Author: namduongit
-- Create date: 26-02-2025

-- Giải thích một số ràng buộc SQL:
-- 	   + AUTO_INCREMEN: 			tự tạo mã khi tạo 1 hàng mới của bảng
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
                                      full_name   	VARCHAR(100),
    birth_date  	DATE,
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
                                             amount          DECIMAL(10,2) NOT NULL,
    payment_method  ENUM('Tiền mặt', 'Chuyển khoản') NOT NULL,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE
    );

-- Bảng gửi OTP để reset lại mật khẩu khi nhân viên quên
CREATE TABLE IF NOT EXISTS password_reset_otps (
                                                   otp_id      INT AUTO_INCREMENT PRIMARY KEY,
                                                   staff_id  INT NOT NULL,
                                                   otp_code    VARCHAR(10) NOT NULL,
    expires_at  DATETIME NOT NULL,
    used        BOOLEAN DEFAULT FALSE, -- Nếu đã sử dụng thì xóa
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (staff_id) REFERENCES staffs(staff_id) ON DELETE CASCADE
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
    price_per_hour	DECIMAL(10, 2) NOT NULL,
    motherboard_id 	INT NOT NULL,

    mouse_id       	INT DEFAULT NULL,
    keyboard_id    	INT DEFAULT NULL,
    monitor_id     	INT DEFAULT NULL,
    headphone_id   	INT DEFAULT NULL,
    rom_id         	INT DEFAULT NULL,
    room_id			INT DEFAULT NULL,

    status         	ENUM('Trong kho', 'Đang sử dụng', 'Thiếu linh kiện', 'Bảo trì', 'Hỏng') DEFAULT 'Trong kho',

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
    price           DECIMAL(10,2) NOT NULL,
    category_id     INT NOT NULL,
    quantity        INT DEFAULT 0,
    status          VARCHAR(10) GENERATED ALWAYS AS (CASE WHEN quantity > 0 THEN 'Còn hàng' ELSE 'Hết hàng' END) STORED,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE
    );

-- Bảng hóa đơn thức ăn
CREATE TABLE IF NOT EXISTS food_bills (
                                          bill_id     INT AUTO_INCREMENT PRIMARY KEY,
                                          player_id   INT DEFAULT NULL,  -- Mặc định NULL để hỗ trợ khách vãng lai
                                          staff_id    INT NOT NULL,  -- Bắt buộc có nhân viên xác nhận
                                          total_price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
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

-- SELECT COUNT(*) AS SoLuongBang
-- FROM information_schema.tables
-- WHERE table_schema = 'net_gaming_management';

-- Cập nhật thêm cột địa chỉ cho nhân viên

select * from staffs;