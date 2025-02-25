-- Kiểm tra & tạo database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'net_gaming_management')
    CREATE DATABASE net_gaming_management;
GO
USE net_gaming_management;
GO

-- =================== Phần cứng máy tính ===================

CREATE TABLE mouse (
    mouse_id       INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

CREATE TABLE keyboards (
    keyboard_id    INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

CREATE TABLE monitors (
    monitor_id     INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    size           FLOAT NOT NULL,
    refresh_rate   INT NOT NULL,
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

CREATE TABLE psus (
    psu_id         INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    wattage        INT NOT NULL,
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

CREATE TABLE storages (
    storage_id     INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    type           VARCHAR(10) NOT NULL CHECK (type IN ('HDD', 'SSD', 'NVMe')),
    capacity       INT NOT NULL,
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

CREATE TABLE rams (
    ram_id         INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    capacity       INT NOT NULL,
    speed          INT NOT NULL,
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

CREATE TABLE gpus (
    gpu_id         INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    vram           INT NOT NULL,
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

CREATE TABLE cpus (
    cpu_id         INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    clock_speed    FLOAT NOT NULL,
    cores          INT NOT NULL,
    threads        INT NOT NULL,
    integrated_gpu BIT DEFAULT 0,
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

CREATE TABLE rom (
    rom_id         INT IDENTITY(1,1) PRIMARY KEY,
    brand          VARCHAR(50) NOT NULL,
    model          VARCHAR(100) NOT NULL,
    type           VARCHAR(10) NOT NULL CHECK (type IN ('EPROM', 'EEPROM', 'Flash')),
    capacity       INT NOT NULL,  -- Dung lượng tính bằng MB
    purchase_date  DATE NULL,
    warranty_expiry DATE NULL,
    status         VARCHAR(20) DEFAULT 'Hoạt động',
    created_at     DATETIME DEFAULT GETDATE()
);

-- =================== Phòng & Máy tính ===================

CREATE TABLE rooms (
    room_id     INT IDENTITY(1,1) PRIMARY KEY,
    room_name   VARCHAR(50) NOT NULL,
    room_type   VARCHAR(20) NOT NULL CHECK (room_type IN ('Phòng thường', 'Phòng gaming', 'Phòng thi đấu')),
    status      VARCHAR(20) DEFAULT 'Trống' CHECK (status IN ('Trống', 'Đang hoạt động', 'Bảo trì')),
    created_at  DATETIME DEFAULT GETDATE()
);

CREATE TABLE accounts (
    account_id  INT IDENTITY(1,1) PRIMARY KEY,
    username    VARCHAR(50) NOT NULL UNIQUE,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    created_at  DATETIME DEFAULT GETDATE()
);

CREATE TABLE computers (
    computer_id    INT IDENTITY(1,1) PRIMARY KEY,
    rom_id         INT NULL,
    computer_name  VARCHAR(50) NOT NULL,
    price_per_hour DECIMAL(10,2) NOT NULL CONSTRAINT DF_computers_price DEFAULT 0.00,
    cpu_id         INT NOT NULL,
    ram_id         INT NOT NULL,
    storage_id     INT NOT NULL,
    psu_id         INT NOT NULL,
    gpu_id         INT NULL,
    monitor_id     INT NULL,
    keyboard_id    INT NULL,
    mouse_id       INT NULL,
    created_at     DATETIME DEFAULT GETDATE(),
    room_id        INT NULL,
    status_play    VARCHAR(20) DEFAULT 'Trống' CHECK (status_play IN ('Đang dùng', 'Trống', 'Bảo trì')),
    status         VARCHAR(20) DEFAULT 'Hoạt động' CHECK (status IN ('Hoạt động', 'Bảo trì', 'Hỏng')),
    account_id     INT NULL,

    FOREIGN KEY (rom_id) REFERENCES rom(rom_id) ON DELETE SET NULL,
    FOREIGN KEY (cpu_id) REFERENCES cpus(cpu_id) ON DELETE CASCADE,
    FOREIGN KEY (gpu_id) REFERENCES gpus(gpu_id) ON DELETE SET NULL,
    FOREIGN KEY (ram_id) REFERENCES rams(ram_id) ON DELETE CASCADE,
    FOREIGN KEY (storage_id) REFERENCES storages(storage_id) ON DELETE CASCADE,
    FOREIGN KEY (psu_id) REFERENCES psus(psu_id) ON DELETE CASCADE,
    FOREIGN KEY (monitor_id) REFERENCES monitors(monitor_id) ON DELETE SET NULL,
    FOREIGN KEY (keyboard_id) REFERENCES keyboards(keyboard_id) ON DELETE SET NULL,
    FOREIGN KEY (mouse_id) REFERENCES mouse(mouse_id) ON DELETE SET NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE SET NULL,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE SET NULL
);
-- =================== Bảng chung lưu thông tin tài khoản ===================
CREATE TABLE accounts (
    account_id  INT IDENTITY(1,1) PRIMARY KEY,
    username    VARCHAR(50) UNIQUE NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20) NOT NULL CHECK (role IN ('Quản trị viên', 'Nhân viên', 'Người chơi')),
    created_at  DATETIME DEFAULT GETDATE()
);

-- =================== Người chơi ===================
CREATE TABLE players (
    player_id   INT IDENTITY(1,1) PRIMARY KEY,
    account_id  INT UNIQUE NOT NULL,
    balance     DECIMAL(10,2) DEFAULT 0.00,
    status      VARCHAR(20) DEFAULT 'Đang nghỉ ngơi' CHECK (status IN ('Đang hoạt động', 'Đang nghỉ ngơi', 'Tạm khóa')),
    created_at  DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- =================== Nhân viên ===================
CREATE TABLE staffs (
    staff_id    INT IDENTITY(1,1) PRIMARY KEY,
    account_id  INT UNIQUE NOT NULL,
    full_name   VARCHAR(100) NOT NULL,
    birth_date  DATE NOT NULL,
    phone       VARCHAR(15) UNIQUE NULL,
    email       VARCHAR(100) UNIQUE NULL,
    avata       VARCHAR(100) NULL,
    status      VARCHAR(20) DEFAULT 'Đang nghỉ' CHECK (status IN ('Đang làm', 'Đang nghỉ', 'Tạm khóa')),
    created_at  DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

-- =================== Lịch sử giao dịch ===================
CREATE TABLE bills (
    bill_id        INT IDENTITY(1,1) PRIMARY KEY,
    player_id      INT NOT NULL,
    staff_id       INT NULL,
    total_amount   DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('Tiền mặt', 'Chuyển khoản')),
    created_at     DATETIME DEFAULT GETDATE(),
    status         VARCHAR(20) DEFAULT 'Chưa xử lý' CHECK (status IN ('Chưa xử lý', 'Đã xử lý', 'Đã hủy')),
    FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES staffs(staff_id) ON DELETE SET NULL
);

-- =================== Sản phẩm ===================
CREATE TABLE products (
    product_id      INT IDENTITY(1,1) PRIMARY KEY,
    name            VARCHAR(100) UNIQUE NOT NULL,
    origin_price    DECIMAL(10,2) NOT NULL,
    sell_price      DECIMAL(10,2) NOT NULL,
    purchase_date   DATE NULL,
    warranty_expiry DATE NULL,
    status          VARCHAR(30) DEFAULT 'Hết hàng' CHECK (status IN ('Còn hàng', 'Hết hàng', 'Hết hạn sử dụng')),
    created_at      DATETIME DEFAULT GETDATE()
);

-- =================== Đơn hàng ===================
CREATE TABLE orders (
    bill_id     INT NOT NULL,
    product_id  INT NOT NULL,
    amount      INT NOT NULL,
    PRIMARY KEY (bill_id, product_id),
    FOREIGN KEY (bill_id) REFERENCES bills(bill_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
