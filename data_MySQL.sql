-- Kiểm tra & tạo database
CREATE DATABASE IF NOT EXISTS net_gaming_management;
USE net_gaming_management;

-- =================== Phần cứng máy tính ===================

CREATE TABLE mouse (
    mouse_id       INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE keyboards (
    keyboard_id    INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE monitors (
    monitor_id     INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    size           FLOAT NOT NULL,
    refresh_rate   INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE psus (
    psu_id         INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    wattage        INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE storages (
    storage_id     INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    type           ENUM('HDD', 'SSD', 'NVMe') NOT NULL,
    capacity       INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rams (
    ram_id         INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    capacity       INT NOT NULL,
    speed          INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE gpus (
    gpu_id         INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    vram           INT NOT NULL,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cpus (
    cpu_id         INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    clock_speed    FLOAT NOT NULL,
    cores          INT NOT NULL,
    threads        INT NOT NULL,
    integrated_gpu BOOLEAN DEFAULT FALSE,
    purchase_date  DATE,
    warranty_expiry DATE,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rom (
    rom_id         INT AUTO_INCREMENT PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    type           ENUM('EPROM', 'EEPROM', 'Flash') NOT NULL,
    capacity       INT NOT NULL,  -- Dung lượng tính bằng MB
    purchase_date  DATE,
    warranty_expiry DATE,
    status         ENUM('Hoạt động', 'Bảo trì', 'Hỏng') DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =================== Phòng & Lầu & Máy tính ===================

CREATE TABLE floors (
    floor_id     INT AUTO_INCREMENT PRIMARY KEY,
    floor_name   VARCHAR(50) NOT NULL,
    number_of_room   INT NOT NULL,
    status      ENUM('Trống', 'Đang hoạt động', 'Bảo trì') DEFAULT 'Trống',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
)

CREATE TABLE rooms (
    room_id     INT AUTO_INCREMENT PRIMARY KEY,
    floor_id    INT NOT NULL,
    room_name   VARCHAR(50) NOT NULL,
    capacity_computer   INT NOT NULL,
    room_type   ENUM('Phòng thường', 'Phòng gaming', 'Phòng thi đấu') NOT NULL,
    status      ENUM('Trống', 'Đang hoạt động', 'Bảo trì') DEFAULT 'Trống',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP.
    FOREIGN KEY (floor_id) REFERENCES floors(floor_id)
);

CREATE TABLE computers (
    computer_id   INT AUTO_INCREMENT PRIMARY KEY,
    rom_id       INT,
    computer_name VARCHAR(50) NOT NULL,
    price_per_hour DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    cpu_id        INT NOT NULL,
    ram_id        INT NOT NULL,
    storage_id    INT NOT NULL,
    psu_id        INT NOT NULL,
    gpu_id        INT,
    monitor_id    INT,
    keyboard_id   INT,
    mouse_id      INT,
    created_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    room_id       INT,
    status_play   ENUM('Đang dùng', 'Trống', 'Bảo trì') DEFAULT 'Trống',
    status        ENUM('Hoạt động', 'Bảo trì', 'Hỏng') DEFAULT 'Hoạt động',
    account_id    INT,
    FOREIGN KEY (rom_id) REFERENCES rom(rom_id),
    FOREIGN KEY (cpu_id) REFERENCES cpus(cpu_id),
    FOREIGN KEY (gpu_id) REFERENCES gpus(gpu_id),
    FOREIGN KEY (ram_id) REFERENCES rams(ram_id),
    FOREIGN KEY (storage_id) REFERENCES storages(storage_id),
    FOREIGN KEY (psu_id) REFERENCES psus(psu_id),
    FOREIGN KEY (monitor_id) REFERENCES monitors(monitor_id),
    FOREIGN KEY (keyboard_id) REFERENCES keyboards(keyboard_id),
    FOREIGN KEY (mouse_id) REFERENCES mouse(mouse_id),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
);

-- =================== Bảng chung lưu thông tin tài khoản ===================

CREATE TABLE accounts (
    account_id  INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50) UNIQUE NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('Quản trị viên', 'Nhân viên', 'Người chơi') NOT NULL,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =================== Người chơi ===================

CREATE TABLE players (
    player_id     INT AUTO_INCREMENT PRIMARY KEY,
    account_id  INT UNIQUE NOT NULL,
    balance     DECIMAL(10,2) DEFAULT 0.00,
    status      ENUM('Đang hoạt động', 'Đang nghỉ ngơi', 'Tạm khóa') DEFAULT 'Đang nghỉ ngơi',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- =================== Nhân viên ===================

CREATE TABLE staffs (
    staff_id    INT AUTO_INCREMENT PRIMARY KEY,
    account_id  INT UNIQUE NOT NULL,
    full_name   VARCHAR(100) NOT NULL,
    birth_date  DATE NOT NULL,
    phone       VARCHAR(15) UNIQUE,
    email       VARCHAR(100) UNIQUE,
    avatar       VARCHAR(100), -- Ảnh sẽ có tên như cái ID
    status      ENUM('Đang làm', 'Đang nghỉ', 'Tạm khóa') DEFAULT 'Đang nghỉ',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- =================== Lịch sử giao dịch ===================

CREATE TABLE bills (
    bill_id        INT AUTO_INCREMENT PRIMARY KEY,
    player_id      INT NOT NULL,
    staff_id       INT,
    total_amount   DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    payment_method ENUM('Tiền mặt', 'Chuyển khoản') NOT NULL,
    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    status         ENUM('Chưa xử lý', 'Đã xử lý', 'Đã hủy') DEFAULT 'Chưa xử lý',
    FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES staffs(staff_id) ON DELETE SET NULL
);

CREATE TABLE products (
    product_id      INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) UNIQUE NOT NULL,
    origin_price    INT NOT NULL,
    sell_price      INT NOT NULL,
    purchase_date   DATE,
    warranty_expiry DATE,
    status          ENUM('Còn hàng', 'Hết hàng', 'Hết hạn sử dụng') DEFAULT 'Hết hàng',
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    bill_id     INT NOT NULL,
    product_id  INT NOT NULL,
    amount      INT NOT NULL,
    PRIMARY KEY (bill_id, product_id),
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Bảng lưu OTP khi quên mật khẩu và cần reset lại mật khẩu
CREATE TABLE password_reset_otps (
    otp_id      INT AUTO_INCREMENT PRIMARY KEY,
    account_id  INT NOT NULL,
    otp_code    VARCHAR(10) NOT NULL,
    expires_at  DATETIME NOT NULL,
    used        BOOLEAN DEFAULT FALSE,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- Bảng lưu các tầng của quán net

