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