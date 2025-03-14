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
    amount          DECIMAL(10,2) NOT NULL,
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

-- Đủ 24 bảng là đúng
SELECT COUNT(*) AS SoLuongBang
FROM information_schema.tables
WHERE table_schema = 'net_gaming_management';


-- Insert dữ liệu liên quan đến đồ ăn
-- Thêm danh mục vào bảng categories
INSERT INTO categories (name) VALUES
('Món chính'),
('Đồ uống'),
('Tráng miệng');

-- Thêm món ăn vào bảng foods
INSERT INTO foods (name, price, category_id, quantity, image) VALUES
('Cơm gà xối mỡ', 50000, 1, 10, 'com-ga-xoi-mo.jpg'),
('Bún bò Huế', 45000, 1, 15, 'bun-bo-hue.jpg'),
('Phở tái chín', 40000, 1, 20, 'pho-tai-chin.jpg'),
('Cơm tấm sườn bì chả', 55000, 1, 12, 'com-tam-suon-bi-cha.jpg'),
('Mì Quảng tôm thịt', 48000, 1, 18, 'mi-quang-tom-thit.jpg'),
('Hủ tiếu Nam Vang', 47000, 1, 8, 'hu-tieu-nam-vang.jpg'),
('Bánh mì thịt nướng', 30000, 1, 25, 'banh-mi-thit-nuong.jpg'),

('Cà phê sữa đá', 25000, 2, 30, 'ca-phe-sua-da.jpg'),
('Trà đào cam sả', 35000, 2, 20, 'tra-dao-cam-sa.jpg'),
('Nước ép cam', 30000, 2, 18, 'nuoc-ep-cam.jpg'),
('Nước ép ổi', 28000, 2, 22, 'nuoc-ep-oi.jpg'),
('Sinh tố bơ', 40000, 2, 15, 'sinh-to-bo.jpg'),
('Trà sữa trân châu', 38000, 2, 25, 'tra-sua-tran-chau.jpg'),
('Soda chanh dây', 32000, 2, 10, 'soda-chanh-day.jpg'),

('Bánh flan', 20000, 3, 20, 'banh-flan.jpg'),
('Chè thập cẩm', 30000, 3, 18, 'che-thap-cam.jpg'),
('Kem dừa', 35000, 3, 12, 'kem-dua.jpg'),
('Sữa chua nếp cẩm', 25000, 3, 15, 'sua-chua-nep-cam.jpg'),
('Rau câu dừa', 22000, 3, 25, 'rau-cau-dua.jpg'),
('Bánh mochi', 40000, 3, 10, 'banh-mochi.jpg');

-- Thêm dữ liệu cho bảng mouse
INSERT INTO mouse(brand, model, purchase_date, warranty_expiry, status)
    VALUES ('Logitech', 'Logitech G102', '2025-03-14', '2026-03-14', 'Trong kho'),
           ('Logitech', 'Logitech G304', '2025-03-14', '2026-03-14', 'Trong kho'),
           ('Logitech', 'Logitech G Pro X Superlight', '2025-03-14', '2026-03-14', 'Trong kho'),
           ('Logitech', 'Logitech G Pro X Superlight 2', '2025-03-14', '2026-03-14', 'Trong kho'),
           ('Logitech', 'Logitech MX Master 3', '2025-03-01', '2028-03-01', 'Trong kho'),
           ('SteelSeries', 'SteelSeries Rival 3', '2024-12-01', '2025-12-01', 'Trong kho'),
           ('SteelSeries', 'Aerox 5 Wireless', '2025-03-13', '2027-03-13', 'Trong kho'),
           ('SteelSeries', 'Aerox 3 Wireless', '2025-02-15', '2026-02-15', 'Trong kho'),
           ('SteelSeries', 'Prime Wireless', '2024-11-01', '2026-11-01', 'Trong kho'),
           ('Corsair', 'Corsair Harpoon RGB', '2025-02-15', '2026-02-15', 'Trong kho'),
           ('Corsair', 'Corsair Harpoon RGB', '2025-02-15', '2026-02-15', 'Trong kho'),
           ('Razer', 'Razer Basilisk V3', '2024-11-20', '2025-11-20', 'Trong kho'),
           ('Razer', 'Razer DeathAdder V2', '2025-01-10', '2027-01-10', 'Trong kho'),
           ('HyperX', 'HyperX Pulsefire Surge', '2025-03-13', '2026-03-13', 'Trong kho'),
           ('Zowie', 'Zowie EC2', '2025-02-28', '2027-02-28', 'Trong kho'),
           ('DareU', 'DareU A950 Pro', '2025-03-13', '2027-03-13', 'Trong kho'),
           ('DareU', 'DareU A980 Pro Max', '2025-01-15', '2028-01-15', 'Trong kho'),
           ('DareU', 'DareU A955', '2024-12-20', '2026-12-20', 'Trong kho'),
           ('DareU', 'DareU EM901X', '2025-02-10', '2026-02-10', 'Trong kho'),
           ('DareU', 'DareU A970', '2025-03-01', '2026-03-01', 'Trong kho');


-- Thêm dữ liệu cho bảng keyboards
INSERT INTO keyboards(brand, model, purchase_date, warranty_expiry, status)
    VALUES ('Logitech', 'Logitech G Pro X TKL', '2025-03-14', '2027-03-14', 'Trong kho'),
           ('Razer', 'Razer BlackWidow V4 Pro', '2025-02-10', '2026-02-10', 'Trong kho'),
           ('SteelSeries', 'SteelSeries Apex Pro Mini', '2024-12-15', '2026-12-15', 'Trong kho'),
           ('Corsair', 'Corsair K95 RGB Platinum', '2025-01-05', '2027-01-05', 'Trong kho'),
           ('Keychron', 'Keychron K8 Pro', '2025-03-13', '2026-03-13', 'Trong kho'),
           ('Ducky', 'Ducky One 3 TKL', '2025-02-20', '2027-02-20', 'Trong kho'),
           ('Logitech', 'Logitech MX Keys', '2024-11-25', '2025-11-25', 'Trong kho'),
           ('Razer', 'Raxer Huntsman Mini', '2025-03-01', '2026-03-01', 'Trong kho'),
           ('SteelSeries', 'SteelSeries Apex 7 TKL', '2025-01-15', '2026-01-15', 'Trong kho'),
           ('Corsair', 'Corsair K70 RGB TKL', '2025-02-28', '2027-02-28', 'Trong kho'),
           ('DarkAlien', 'DarkAlien R65', '2025-01-24', '2027-01-24', 'Trong kho'),
           ('DarkAlien', 'DarkAlien R65', '2025-01-24', '2027-01-24', 'Trong kho'),
           ('DarkAlien', 'DarkAlien R65', '2025-01-24', '2027-01-24', 'Trong kho'),
           ('HyperX', 'HyperX Alloy Origins 60', '2024-12-01', '2025-12-01', 'Trong kho'),
           ('Logitech', 'Logitech G915 TKL', '2025-03-10', '2028-03-10', 'Trong kho'),
           ('Razer', 'Razer DeathStalker V2', '2025-02-05', '2026-02-05', 'Trong kho'),
           ('SteelSeries', 'SteelSeries Apex 3', '2025-03-12', '2026-03-12', 'Trong kho'),
           ('Corsair', 'Corsair K100 RGB', '2024-11-10', '2026-11-10', 'Trong kho'),
           ('Keychron', 'Keychron Q1', '2025-01-25', '2027-01-25', 'Trong kho'),
           ('Ducky', 'Ducky Shine 7', '2025-03-07', '2026-03-07', 'Trong kho'),
           ('HyperX', 'HyperX Alloy Elite 2', '2025-02-15', '2026-02-15', 'Trong kho'),
           ('Logitech', 'Logitech G613', '2024-12-20', '2025-12-20', 'Trong kho'),
           ('Razer', 'Razer Ornata V3', '2025-03-13', '2026-03-13', 'Trong kho');


-- Thêm dữ liệu cho bảng headphones
INSERT INTO headphones(brand, model, type, purchase_date, warranty_expiry, status)
    VALUES ('Logitech', 'Logitech G733', 'Wireless', '2025-03-14', '2026-03-14', 'Trong kho'),
           ('Razer', 'Razer BlackShark V2', 'Wired', '2025-02-10', '2027-02-10', 'Trong kho'),
           ('Sony', 'Sony WH-1000XM5', 'Wireless', '2024-12-15', '2027-12-15', 'Trong kho'),
           ('HyperX', 'HyperX Cloud II', 'Wired', '2025-01-05', '2026-01-05', 'Trong kho'),
           ('SteelSeries', 'SteelSeries Arctis Nova Pro', 'Wireless', '2025-03-13', '2028-03-13', 'Trong kho'),
           ('Sennheiser', 'Sennheiser HD 660S', 'Wired', '2025-02-20', '2027-02-20', 'Trong kho'),
           ('Logitech', 'Logitech G435', 'Wireless', '2024-11-25', '2025-11-25', 'Trong kho'),
           ('Razer', 'Razer Kraken V3', 'Wired', '2025-03-01', '2026-03-01', 'Trong kho'),
           ('Sony', 'Sony WF-1000XM4', 'Wireless', '2025-01-15', '2026-01-15', 'Trong kho'),
           ('HyperX', 'HyperX Cloud Alpha', 'Wired', '2025-02-28', '2026-02-28', 'Trong kho'),
           ('SteelSeries', 'SteelSeries Arctis 7+', 'Wireless', '2024-12-01', '2026-12-01', 'Trong kho'),
           ('Logitech', 'Logitech Zone Vibe 100', 'Wireless', '2025-03-10', '2026-03-10', 'Trong kho'),
           ('Razer', 'Razer Barracuda X', 'Wireless', '2025-02-05', '2026-02-05', 'Trong kho'),
           ('Sony', 'Sony MDR-7506', 'Wired', '2025-03-12', '2026-03-12', 'Trong kho'),
           ('HyperX', 'HyperX Cloud Stinger', 'Wired', '2024-11-10', '2025-11-10', 'Trong kho'),
           ('SteelSeries', 'SteelSeries Arctis 1', 'Wired', '2025-01-25', '2026-01-25', 'Trong kho'),
           ('Sennheiser', 'Sennheiser Momentum 4', 'Wireless', '2025-03-07', '2028-03-07', 'Trong kho'),
           ('Logitech', 'Logitech G Pro X', 'Wired', '2025-02-15', '2026-02-15', 'Trong kho'),
           ('Razer', 'Razer Nari Ultimate', 'Wireless', '2024-12-20', '2026-12-20', 'Trong kho'),
           ('Sony', 'Sony Pulse 3D', 'Wireless', '2025-03-13', '2026-03-13', 'Trong kho');


-- Thêm dữ liệu cho bảng monitors
INSERT INTO monitors (brand, model, size, refresh_rate, purchase_date, warranty_expiry, status)
    VALUES ('ASUS', 'ASUS TUF Gaming VG27AQ', 27.0, 165, '2025-03-14', '2027-03-14', 'Trong kho'),
           ('Dell', 'Dell Alienware AW2723DF', 27.0, 280, '2025-02-10', '2028-02-10', 'Trong kho'),
           ('LG', ' LG UltraGear 27GP950-B', 27.0, 144, '2024-12-15', '2026-12-15', 'Trong kho'),
           ('Samsung', 'Samsung Odyssey G7', 32.0, 240, '2025-01-05', '2027-01-05', 'Trong kho'),
           ('Acer', 'Acer Predator XB273U', 27.0, 270, '2025-03-13', '2028-03-13', 'Trong kho'),
           ('ASUS', 'ASUS ROG Swift PG259QN', 24.5, 360, '2025-02-20', '2028-02-20', 'Trong kho'),
           ('Dell', 'Dell S2721DGF', 27.0, 165, '2024-11-25', '2025-11-25', 'Trong kho'),
           ('LG', 'LG UltraGear 32GP850-B', 32.0, 180, '2025-03-01', '2027-03-01', 'Trong kho'),
           ('Samsung', 'Samsung Odyssey Neo G8', 32.0, 240, '2025-01-15', '2028-01-15', 'Trong kho'),
           ('Acer', 'Acer Nitro XV272U', 27.0, 170, '2025-02-28', '2026-02-28', 'Trong kho'),
           ('MSI', 'MSI Optix MAG274QRF-QD', 27.0, 165, '2024-12-01', '2026-12-01', 'Trong kho'),
           ('ASUS', 'ASUS ROG Strix XG27UCS', 27.0, 160, '2025-03-10', '2027-03-10', 'Trong kho'),
           ('Dell', 'Dell Alienware AW3423DWF', 34.0, 165, '2025-02-05', '2028-02-05', 'Trong kho'),
           ('LG', 'LG UltraGear 24GN650-B', 24.0, 144, '2025-03-12', '2026-03-12', 'Trong kho'),
           ('Samsung', 'Samsung Odyssey G5', 27.0, 144, '2024-11-10', '2025-11-10', 'Trong kho'),
           ('Acer', 'Acer Predator X34', 34.0, 180, '2025-01-25', '2027-01-25', 'Trong kho'),
           ('MSI', 'MSI Optix MPG321UR-QD', 32.0, 144, '2025-03-07', '2028-03-07', 'Trong kho'),
           ('ASUS', 'ASUS TUF Gaming VG279QM', 27.0, 280, '2025-02-15', '2027-02-15', 'Trong kho'),
           ('Dell', 'Dell G3223Q', 32.0, 144, '2024-12-20', '2026-12-20', 'Trong kho'),
           ('LG', 'LG UltraGear 27GL850-B', 27.0, 144, '2025-03-13', '2026-03-13', 'Trong kho');

-- Thêm dữ liệu cho bảng roms
INSERT INTO roms (brand, model, type, capacity, purchase_date, warranty_expiry, status)
    VALUES ('AMI', 'AMI BIOS 2023', 'Flash', 32, '2025-03-14', '2027-03-14', 'Trong kho'),
           ('Phoenix', 'Phoenix SecureCore Tiano', 'EEPROM', 16, '2025-02-10', '2026-02-10', 'Trong kho'),
           ('Award', 'Award BIOS Modular', 'Flash', 64, '2024-12-15', '2026-12-15', 'Trong kho'),
           ('Intel', 'Intel UEFI v2.8', 'Flash', 128, '2025-01-05', '2028-01-05', 'Trong kho'),
           ('AMI', 'AMI Aptio V', 'Flash', 32, '2025-03-13', '2027-03-13', 'Trong kho'),
           ('Phoenix', 'Phoenix BIOS 4.0', 'EPROM', 8, '2025-02-20', '2026-02-20', 'Trong kho'),
           ('Award', 'AwardBIOS Elite', 'EEPROM', 16, '2024-11-25', '2025-11-25', 'Trong kho'),
           ('Intel', 'Intel UEFI v2.7', 'Flash', 64, '2025-03-01', '2027-03-01', 'Trong kho'),
           ('AMI', 'AMI BIOS 2024', 'Flash', 32, '2025-01-15', '2026-01-15', 'Trong kho'),
           ('Phoenix', 'Phoenix SecureCore DXE', 'Flash', 64, '2025-02-28', '2027-02-28', 'Trong kho'),
           ('Award', 'Award BIOS Legacy', 'EPROM', 8, '2024-12-01', '2025-12-01', 'Trong kho'),
           ('Intel', 'Intel UEFI v2.9', 'Flash', 128, '2025-03-10', '2028-03-10', 'Trong kho'),
           ('AMI', 'AMI Aptio 6', 'Flash', 32, '2025-02-05', '2026-02-05', 'Hỏng'),
           ('Phoenix', 'Phoenix TrustedCore', 'EEPROM', 16, '2025-03-12', '2027-03-12', 'Trong kho'),
           ('Award', 'Award BIOS Pro', 'Flash', 64, '2024-11-10', '2026-11-10', 'Trong kho'),
           ('Intel', 'Intel UEFI v2.6', 'Flash', 32, '2025-01-25', '2026-01-25', 'Trong kho'),
           ('AMI', 'AMI BIOS Core', 'EEPROM', 16, '2025-03-07', '2027-03-07', 'Trong kho'),
           ('Phoenix', 'Phoenix BIOS 5.0', 'EPROM', 8, '2025-02-15', '2026-02-15', 'Trong kho'),
           ('Award', 'Award BIOS Ultra', 'Flash', 64, '2024-12-20', '2026-12-20', 'Trong kho'),
           ('Intel', 'Intel UEFI v3.0', 'Flash', 128, '2025-03-13', '2028-03-13', 'Trong kho');

--Thêm dữ liệu cho bảng psus
INSERT INTO psus (brand, model, wattage, purchase_date, warranty_expiry, status)
    VALUES ('Corsair', 'Corsair RM750x', 750, '2025-03-14', '2030-03-14', 'Trong kho'),
           ('EVGA', 'EVGA SuperNOVA 850 G5', 850, '2025-02-10', '2032-02-10', 'Trong kho'),
           ('Seasonic', 'Seasonic Focus GX-650', 650, '2024-12-15', '2034-12-15', 'Trong kho'),
           ('Cooler Master', 'Cooler Master MWE Gold 750', 750, '2025-01-05', '2030-01-05', 'Trong kho'),
           ('Corsair', 'Corsair HX1200', 1200, '2025-03-13', '2035-03-13', 'Trong kho'),
           ('EVGA', 'EVGA 650 GQ', 650, '2025-02-20', '2028-02-20', 'Trong kho'),
           ('Seasonic', 'Seasonic Prime TX-1000', 1000, '2024-11-25', '2034-11-25', 'Trong kho'),
           ('Cooler Master', 'Cooler Master V850 SFX', 850, '2025-03-01', '2030-03-01', 'Trong kho'),
           ('Corsair', 'Corsair SF600', 600, '2025-01-15', '2027-01-15', 'Trong kho'),
           ('EVGA', 'EVGA SuperNOVA 750 T2', 750, '2025-02-28', '2035-02-28', 'Trong kho'),
           ('Seasonic', 'Seasonic Focus SGX-500', 500, '2024-12-01', '2029-12-01', 'Trong kho'),
           ('Cooler Master', 'Cooler Master MasterWatt 550', 550, '2025-03-10', '2028-03-10', 'Trong kho'),
           ('Corsair', 'Corsair AX860', 860, '2025-02-05', '2035-02-05', 'Trong kho'),
           ('EVGA', 'EVGA SuperNOVA 1000 P2', 1000, '2025-03-12', '2032-03-12', 'Trong kho'),
           ('Seasonic', 'Seasonic Snow Silent 750', 750, '2024-11-10', '2034-11-10', 'Trong kho'),
           ('Cooler Master', 'Cooler Master V1200 Platinum', 1200, '2025-01-25', '2030-01-25', 'Trong kho'),
           ('Corsair', 'Corsair RM650i', 650, '2025-03-07', '2030-03-07', 'Trong kho'),
           ('EVGA', 'EVGA 450 BR', 450, '2025-02-15', '2026-02-15', 'Trong kho'),
           ('Seasonic', 'Seasonic Prime GX-850', 850, '2024-12-20', '2034-12-20', 'Trong kho'),
           ('Cooler Master', 'Cooler Master MWE Bronze 600', 600, '2025-03-13', '2028-03-13', 'Trong kho');


-- Thêm dữ liệu cho bảng storages
INSERT INTO storages (brand, model, type, capacity, purchase_date, warranty_expiry, status)
    VALUES ('Samsung', 'Samsung 970 EVO Plus', 'NVMe', 1000, '2025-03-14', '2030-03-14', 'Trong kho'),
           ('Western Digital', 'Western Digital WD Black SN850X', 'NVMe', 2000, '2025-02-10', '2030-02-10', 'Trong kho'),
           ('Seagate', 'Seagate Barracuda', 'HDD', 4000, '2024-12-15', '2027-12-15', 'Trong kho'),
           ('Crucial', 'Crucial MX500', 'SSD', 500, '2025-01-05', '2028-01-05', 'Trong kho'),
           ('Samsung', 'Samsung 990 PRO', 'NVMe', 1000, '2025-03-13', '2030-03-13', 'Trong kho'),
           ('Western Digital', 'Western Digital WD Blue 3D', 'SSD', 256, '2025-02-20', '2028-02-20', 'Trong kho'),
           ('Seagate', 'Seagate FireCuda 530', 'NVMe', 500, '2024-11-25', '2029-11-25', 'Trong kho'),
           ('Crucial', 'Crucial P3 Plus', 'NVMe', 2000, '2025-03-01', '2028-03-01', 'Trong kho'),
           ('Samsung', ' Samsung 870 QVO', 'SSD', 1000, '2025-01-15', '2028-01-15', 'Trong kho'),
           ('Western Digital', 'Western Digital WD Red', 'HDD', 2000, '2025-02-28', '2028-02-28', 'Trong kho'),
           ('Seagate', 'Seagate IronWolf', 'HDD', 4000, '2024-12-01', '2027-12-01', 'Trong kho'),
           ('Crucial', 'Crucial BX500', 'SSD', 500, '2025-03-10', '2028-03-10', 'Trong kho'),
           ('Samsung', 'Samsung 860 EVO', 'SSD', 256, '2025-02-05', '2027-02-05', 'Trong kho'),
           ('Western Digital', 'Western Digital WD Black SN770', 'NVMe', 1000, '2025-03-12', '2030-03-12', 'Trong kho'),
           ('Seagate', 'Seagate BarraCuda 510', 'NVMe', 512, '2024-11-10', '2029-11-10', 'Trong kho'),
           ('Crucial', 'Crucial P5', 'NVMe', 1000, '2025-01-25', '2030-01-25', 'Trong kho'),
           ('Samsung', 'Samsung 980', 'NVMe', 500, '2025-03-07', '2028-03-07', 'Trong kho'),
           ('Western Digital', 'Western Digital WD Green', 'SSD', 480, '2025-02-15', '2028-02-15', 'Trong kho'),
           ('Seagate', 'Seagate Expansion', 'HDD', 2000, '2024-12-20', '2027-12-20', 'Trong kho'),
           ('Crucial', 'Crucial P2', 'NVMe', 2000, '2025-03-13', '2030-03-13', 'Trong kho');

-- thêm dữ liệu cho bảng rams
INSERT INTO rams (brand, model, capacity, speed, purchase_date, warranty_expiry, status)
    VALUES ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-14', '2035-03-14', 'Trong kho'),
           ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-13', '2035-03-13', 'Trong kho'),
           ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-01-15', '2035-01-15', 'Trong kho'),
           ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-02-05', '2035-02-05', 'Trong kho'),
           ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-07', '2035-03-07', 'Trong kho'),
           ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-05', '2030-01-05', 'Trong kho'),
           ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-01', '2030-03-01', 'Trong kho'),
           ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-10', '2030-03-10', 'Trong kho'),
           ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-25', '2030-01-25', 'Trong kho'),
           ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-13', '2030-03-13', 'Trong kho'),
           ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-15', '2034-12-15', 'Trong kho'),
           ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-25', '2034-11-25', 'Trong kho'),
           ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-01', '2034-12-01', 'Trong kho'),
           ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-10', '2034-11-10', 'Trong kho'),
           ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-20', '2034-12-20', 'Trong kho'),
           ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-10', '2035-02-10', 'Trong kho'),
           ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-20', '2035-02-20', 'Trong kho'),
           ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-28', '2035-02-28', 'Trong kho'),
           ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-03-12', '2035-03-12', 'Trong kho'),
           ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-15', '2035-02-15', 'Trong kho');

-- thêm dữ lieu cho bảng gpus
INSERT INTO gpus (brand, model, vram, purchase_date, warranty_expiry, status)
    VALUES ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-14', '2028-03-14', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-10', '2029-02-10', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-15', '2029-12-15', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-01-05', '2030-01-05', 'Trong kho'),
           ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-13', '2028-03-13', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-20', '2029-02-20', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-11-25', '2029-11-25', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-01', '2030-03-01', 'Trong kho'),
           ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-01-15', '2028-01-15', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-28', '2029-02-28', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-01', '2029-12-01', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-10', '2030-03-10', 'Trong kho'),
           ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-02-05', '2028-02-05', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-03-12', '2029-03-12', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-11-10', '2029-11-10', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-01-25', '2030-01-25', 'Trong kho'),
           ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-07', '2028-03-07', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-15', '2029-02-15', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-20', '2029-12-20', 'Trong kho'),
           ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-13', '2030-03-13', 'Trong kho');


-- thêm dữ liệu cho bảng cpus
INSERT INTO cpus (brand, model, clock_speed, cores, threads, integrated_gpu, purchase_date, warranty_expiry, status)
    VALUES ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-14', '2028-03-14', 'Trong kho'),
           ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-10', '2028-02-10', 'Trong kho'),
           ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-15', '2029-12-15', 'Trong kho'),
           ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-01-05', '2030-01-05', 'Trong kho'),
           ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-13', '2028-03-13', 'Trong kho'),
           ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-20', '2028-02-20', 'Trong kho'),
           ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-11-25', '2029-11-25', 'Trong kho'),
           ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-01', '2030-03-01', 'Trong kho'),
           ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-01-15', '2028-01-15', 'Trong kho'),
           ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-28', '2028-02-28', 'Trong kho'),
           ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-01', '2029-12-01', 'Trong kho'),
           ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-10', '2030-03-10', 'Trong kho'),
           ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-02-05', '2028-02-05', 'Trong kho'),
           ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-03-12', '2028-03-12', 'Trong kho'),
           ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-11-10', '2029-11-10', 'Trong kho'),
           ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-01-25', '2030-01-25', 'Trong kho'),
           ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-07', '2028-03-07', 'Trong kho'),
           ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-15', '2028-02-15', 'Trong kho'),
           ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-20', '2029-12-20', 'Trong kho'),
           ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-13', '2030-03-13', 'Trong kho');

-- thêm dữ liệu cho bảng motherboards chưa gồm cpu, gpu, psu
INSERT INTO motherboards (brand, model, socket, chipset, ram_slots, max_ram, ram_speed, storage_slots, sata_ports, m2_slots, max_storage_capacity, status, purchase_date, warranty_expiry)
    VALUES ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', '2025-03-14', '2028-03-14'),
           ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', '2025-02-10', '2028-02-10'),
           ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', '2024-12-15', '2029-12-15'),
           ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', '2025-01-05', '2030-01-05'),
           ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', '2025-03-13', '2028-03-13'),
           ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', '2025-02-20', '2028-02-20'),
           ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', '2024-11-25', '2029-11-25'),
           ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', '2025-03-01', '2030-03-01'),
           ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', '2025-01-15', '2028-01-15'),
           ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', '2025-02-28', '2028-02-28'),
           ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', '2024-12-01', '2029-12-01'),
           ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', '2025-03-10', '2030-03-10'),
           ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', '2025-02-05', '2028-02-05'),
           ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', '2025-03-12', '2028-03-12'),
           ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', '2024-11-10', '2029-11-10'),
           ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', '2025-01-25', '2030-01-25'),
           ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', '2025-03-07', '2028-03-07'),
           ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', '2025-02-15', '2028-02-15'),
           ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', '2024-12-20', '2029-12-20'),
           ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', '2025-03-13', '2030-03-13');

