use net_gaming_management;
update foods set status = 'Hết hàng' where quantity = 0;


-- Insert dữ liệu liên quan đến đồ ăn 
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
('Kem dừa', 35000, 3, 12, 'kem-dua.png'),
('Sữa chua nếp cẩm', 25000, 3, 15, 'sua-chua-nep-cam.png'),
('Rau câu dừa', 22000, 3, 25, 'rau-cau-dua.png'),
('Bánh mochi', 40000, 3, 10, 'banh-mochi.png');

use net_gaming_management;
UPDATE foods SET quantity = 0 WHERE name IN 
('Hủ tiếu Nam Vang', 'Soda chanh dây', 'Kem dừa', 'Bánh mochi');

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
       ('Samsung', 'Samsung 990 PRO', 'NVMe', 1000, '2025-03-13', '2030-03-13', 'Trong kho'),
       ('Seagate', 'Seagate FireCuda 530', 'NVMe', 500, '2024-11-25', '2029-11-25', 'Trong kho'),
       ('Crucial', 'Crucial P3 Plus', 'NVMe', 2000, '2025-03-01', '2028-03-01', 'Trong kho'),
       ('Western Digital', 'Western Digital WD Black SN770', 'NVMe', 1000, '2025-03-12', '2030-03-12', 'Trong kho'),
       ('Seagate', 'Seagate BarraCuda 510', 'NVMe', 512, '2024-11-10', '2029-11-10', 'Trong kho'),
       ('Crucial', 'Crucial P5', 'NVMe', 1000, '2025-01-25', '2030-01-25', 'Trong kho'),
       ('Samsung', 'Samsung 980', 'NVMe', 500, '2025-03-07', '2028-03-07', 'Trong kho'),
       ('Crucial', 'Crucial P2', 'NVMe', 2000, '2025-03-13', '2030-03-13', 'Trong kho'),
       ('Western Digital', 'Western Digital WD Blue 3D', 'SSD', 256, '2025-02-20', '2028-02-20', 'Trong kho'),
       ('Crucial', 'Crucial MX500', 'SSD', 500, '2025-01-05', '2028-01-05', 'Trong kho'),
       ('Samsung', ' Samsung 870 QVO', 'SSD', 1000, '2025-01-15', '2028-01-15', 'Trong kho'),
       ('Samsung', 'Samsung 860 EVO', 'SSD', 256, '2025-02-05', '2027-02-05', 'Trong kho'),
       ('Crucial', 'Crucial BX500', 'SSD', 500, '2025-03-10', '2028-03-10', 'Trong kho'),
       ('Western Digital', 'Western Digital WD Green', 'SSD', 480, '2025-02-15', '2028-02-15', 'Trong kho'),
       ('Seagate', 'Seagate Barracuda', 'HDD', 4000, '2024-12-15', '2027-12-15', 'Trong kho'),
       ('Western Digital', 'Western Digital WD Red', 'HDD', 2000, '2025-02-28', '2028-02-28', 'Trong kho'),
       ('Seagate', 'Seagate IronWolf', 'HDD', 4000, '2024-12-01', '2027-12-01', 'Trong kho'),
       ('Seagate', 'Seagate Expansion', 'HDD', 2000, '2024-12-20', '2027-12-20', 'Trong kho');

-- thêm dữ liệu cho bảng rams
INSERT INTO rams (brand, model, capacity, speed, purchase_date, warranty_expiry, status)
VALUES ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-14', '2035-03-14', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-13', '2035-03-13', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-01-15', '2035-01-15', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-02-05', '2035-02-05', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-07', '2035-03-07', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-14', '2035-03-14', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-13', '2035-03-13', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-01-15', '2035-01-15', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-02-05', '2035-02-05', 'Trong kho'),
       ('Corsair', 'Corsair Vengeance LPX', 8, 3200, '2025-03-07', '2035-03-07', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-10', '2035-02-10', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-20', '2035-02-20', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-28', '2035-02-28', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-03-12', '2035-03-12', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-15', '2035-02-15', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-10', '2035-02-10', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-20', '2035-02-20', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-28', '2035-02-28', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-03-12', '2035-03-12', 'Trong kho'),
       ('Kingston', 'Kingston Fury Beast', 8, 5200, '2025-02-15', '2035-02-15', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-15', '2034-12-15', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-25', '2034-11-25', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-01', '2034-12-01', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-10', '2034-11-10', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-20', '2034-12-20', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-15', '2034-12-15', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-25', '2034-11-25', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-01', '2034-12-01', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-11-10', '2034-11-10', 'Trong kho'),
       ('G.Skill', 'G.Skill Ripjaws V', 16, 3600, '2024-12-20', '2034-12-20', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-01-05', '2030-01-05', 'Trong kho'),
       ('Crucial', 'Crucial DDR5 Pro', 16, 5600, '2025-03-01', '2030-03-01', 'Trong kho'),
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
VALUES ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-14', '2028-03-14', 'Trong kho'),
       ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-13', '2028-03-13', 'Trong kho'),
       ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-01-15', '2028-01-15', 'Trong kho'),
       ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-02-05', '2028-02-05', 'Trong kho'),
       ('NVIDIA', 'NVIDIA GTX 1660 Super', 6, '2025-03-07', '2028-03-07', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-10', '2029-02-10', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-20', '2029-02-20', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-28', '2029-02-28', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-03-12', '2029-03-12', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 3060', 12, '2025-02-15', '2029-02-15', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-15', '2029-12-15', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-11-25', '2029-11-25', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-01', '2029-12-01', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-11-10', '2029-11-10', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4070', 12, '2024-12-20', '2029-12-20', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-01-05', '2030-01-05', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-01', '2030-03-01', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-10', '2030-03-10', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-01-25', '2030-01-25', 'Trong kho'),
       ('NVIDIA', 'NVIDIA RTX 4090', 24, '2025-03-13', '2030-03-13', 'Trong kho');


-- thêm dữ liệu cho bảng cpus
INSERT INTO cpus (brand, model, clock_speed, cores, threads, integrated_gpu, purchase_date, warranty_expiry, status)
VALUES ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-14', '2028-03-14', 'Trong kho'),
       ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-13', '2028-03-13', 'Trong kho'),
       ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-01-15', '2028-01-15', 'Trong kho'),
       ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-02-05', '2028-02-05', 'Trong kho'),
       ('AMD', 'AMD Ryzen 5 5600X', 4.6, 6, 12, FALSE, '2025-03-07', '2028-03-07', 'Trong kho'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-20', '2028-02-20', 'Trong kho'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-10', '2028-02-10', 'Trong kho'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-28', '2028-02-28', 'Trong kho'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-03-12', '2028-03-12', 'Trong kho'),
       ('Intel', 'Intel Core i5-13400', 4.6, 10, 16, TRUE, '2025-02-15', '2028-02-15', 'Trong kho'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-15', '2029-12-15', 'Trong kho'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-11-25', '2029-11-25', 'Trong kho'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-01', '2029-12-01', 'Trong kho'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-11-10', '2029-11-10', 'Trong kho'),
       ('AMD', 'AMD Ryzen 7 7700X', 5.4, 8, 16, TRUE, '2024-12-20', '2029-12-20', 'Trong kho'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-01-05', '2030-01-05', 'Trong kho'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-01', '2030-03-01', 'Trong kho'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-10', '2030-03-10', 'Trong kho'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-01-25', '2030-01-25', 'Trong kho'),
       ('Intel', 'Intel Core i9-13900K', 5.8, 24, 32, TRUE, '2025-03-13', '2030-03-13', 'Trong kho');

-- thêm dữ liệu cho bảng motherboards chưa gồm cpu, gpu, psu
INSERT INTO motherboards (brand, model, socket, chipset, ram_slots, max_ram, ram_speed, storage_slots, sata_ports, m2_slots, max_storage_capacity, status, cpu_id, psu_id, gpu_id, purchase_date, warranty_expiry)
VALUES ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', 1, 1, 1, '2025-03-14', '2028-03-14'),
       ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', 2, 2, 3, '2025-03-13', '2028-03-13'),
       ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', 3, 3, 3, '2025-01-15', '2028-01-15'),
       ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', 4, 4, 4, '2025-02-05', '2028-02-05'),
       ('ASUS', 'ASUS TUF Gaming B550-Plus', 'AM4', 'B550', 4, 128, 4400, 6, 4, 2, 96000, 'Trong kho', 5, 5, 5, '2025-03-07', '2028-03-07'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', 6, 6, 6, '2025-02-10', '2028-02-10'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', 7, 7, 7, '2025-02-20', '2028-02-20'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', 8, 8, 8, '2025-02-28', '2028-02-28'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', 9, 9, 9, '2025-03-12', '2028-03-12'),
       ('MSI', 'MSI PRO B760M-A', 'LGA1700', 'B760', 4, 128, 5333, 6, 4, 2, 96000, 'Trong kho', 10, 10, 10, '2025-02-15', '2028-02-15'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', 11, 11, 11, '2024-12-15', '2029-12-15'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', 12, 12, 12, '2024-11-25', '2029-11-25'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', 13, 13, 13, '2024-12-01', '2029-12-01'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', 14, 14, 14, '2024-11-10', '2029-11-10'),
       ('Gigabyte', 'Gigabyte X670 AORUS Elite', 'AM5', 'X670', 4, 128, 6000, 8, 6, 2, 144000, 'Trong kho', 15, 15, 15, '2024-12-20', '2029-12-20'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', 16, 16, 16, '2025-01-05', '2030-01-05'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', 17, 17, 17, '2025-03-01', '2030-03-01'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', 18, 18, 18, '2025-03-10', '2030-03-10'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', 19, 19, 19, '2025-01-25', '2030-01-25'),
       ('ASUS', 'ASUS ROG Strix Z790-E', 'LGA1700', 'Z790', 4, 192, 7200, 8, 6, 2, 144000, 'Trong kho', 20, 20, 20, '2025-03-13', '2030-03-13');

-- thêm dữ liệu vào bang motherboard_ram
INSERT INTO motherboard_ram (motherboard_id, ram_id)
VALUES (1, 1),
       (1, 2),

       (2, 3),
       (2, 4),

       (3, 5),
       (3, 6),

       (4, 7),
       (4, 8),

       (5, 9),
       (5, 10),

       (6, 11),
       (6, 12),

       (7, 13),
       (7, 14),

       (8, 15),
       (8, 16),

       (9, 17),
       (9, 18),

       (10, 19),
       (10, 20),

       (11, 21),
       (11, 22),

       (12, 23),
       (12, 24),

       (13, 25),
       (13, 26),

       (14, 27),
       (14, 28),

       (15, 29),
       (15, 30),

       (16, 31),
       (16, 32),

       (17, 33),
       (17, 34),

       (18, 35),
       (18, 36),

       (19, 37),
       (19, 38),

       (20, 39),
       (20, 40);

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
       (20, 1);

-- thêm dữ liệu vào bảng computers
INSERT INTO computers (name, price_per_hour, motherboard_id, mouse_id, keyboard_id, monitor_id, headphone_id, rom_id, status)
VALUES ('Máy thường Spoce 1', 10000.00, 1, 1, 1, 1, 1, 1, 'Trong kho'),
       ('Máy thường Spoce 2', 10000.00, 2, 2, 2, 2, 2, 2, 'Trong kho'),
       ('Máy thường Spoce 3', 10000.00, 3, 3, 3, 3, 3, 3, 'Trong kho'),
       ('Máy thường Spoce 4', 10000.00, 4, 4, 4, 4, 4, 4, 'Trong kho'),
       ('Máy thường Spoce 5', 10000.00, 5, 5, 5, 5, 5, 5, 'Trong kho'),
       ('Máy vip Spoce 1', 15000.00, 6, 6, 6, 6, 6, 6, 'Trong kho'),
       ('Máy vip Spoce 2', 15000.00, 7, 7, 7, 7, 7, 7, 'Trong kho'),
       ('Máy vip Spoce 3', 15000.00, 8, 8, 8, 8, 8, 8, 'Trong kho'),
       ('Máy vip Spoce 4', 15000.00, 9, 9, 9, 9, 9, 9, 'Trong kho'),
       ('Máy vip Spoce 5', 15000.00, 10, 10, 10, 10, 10, 10, 'Trong kho'),
       ('Máy cao cấp Spoce 1', 20000.00, 11, 11, 11, 11, 11, 11, 'Trong kho'),
       ('Máy cao cấp Spoce 2', 20000.00, 12, 12, 12, 12, 12, 12, 'Trong kho'),
       ('Máy cao cấp Spoce 3', 20000.00, 13, 13, 13, 13, 13, 13, 'Trong kho'),
       ('Máy cao cấp Spoce 4', 20000.00, 14, 14, 14, 14, 14, 14, 'Trong kho'),
       ('Máy cao cấp Spoce 5', 20000.00, 15, 15, 15, 15, 15, 15, 'Trong kho'),
       ('Siêu máy tính Spoce 1', 30000.00, 16, 16, 16, 16, 16, 16, 'Trong kho'),
       ('Siêu máy tính Spoce 2', 30000.00, 17, 17, 17, 17, 17, 17, 'Trong kho'),
       ('Siêu máy tính Spoce 3', 30000.00, 18, 18, 18, 18, 18, 18, 'Trong kho'),
       ('Siêu máy tính Spoce 4', 30000.00, 19, 19, 19, 19, 19, 19, 'Trong kho'),
       ('Siêu máy tính Spoce 5', 30000.00, 20, 20, 20, 20, 20, 20, 'Trong kho');