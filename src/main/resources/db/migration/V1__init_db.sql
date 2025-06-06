-- Create table for lecturers
CREATE TABLE IF NOT EXISTS lecturers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    department VARCHAR(255)
);

-- Create table for rooms
CREATE TABLE IF NOT EXISTS rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    floor INTEGER NOT NULL,
    building VARCHAR(255) NOT NULL
);

-- Create table for room_schedules
CREATE TABLE IF NOT EXISTS room_schedules (
    id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL,
    lecturer_id INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE,
    FOREIGN KEY (lecturer_id) REFERENCES lecturers (id) ON DELETE CASCADE
);

-- Create table for room_schedule_weekdays
CREATE TABLE IF NOT EXISTS room_schedule_weekdays (
    id SERIAL PRIMARY KEY,
    room_schedule_id INTEGER NOT NULL,
    weekday VARCHAR(20) NOT NULL,
    FOREIGN KEY (room_schedule_id) REFERENCES room_schedules (id) ON DELETE CASCADE
);

INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (1, 'CO1007', 'Cấu trúc Rời rạc cho Khoa học Máy tính');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (2, 'CO2011', 'Mô hình hóa Toán học');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (3, 'MT1003', 'Giải tích 1');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (4, 'MT1005', 'Giải tích 2');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (5, 'MT1007', 'Đại số Tuyến tính');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (6, 'MT2013', 'Xác suất và Thống kê');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (7, 'CH1003', 'Hóa đại cương');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (8, 'PH1003', 'Vật lý 1');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (9, 'PH1007', 'Thí nghiệm Vật lý');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (10, 'SP1013', 'Kỹ năng Xã hội A (báo Chí)');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (11, 'SP1015', 'Kỹ năng Xã hội B (sân Khấu)');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (12, 'SP1017', 'Kỹ năng Xã hội C (Thanh Nhạc)');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (13, 'SP1019', 'Kỹ năng Xã hội D (Nhiếp Ảnh)');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (14, 'SP1021', 'Kỹ năng Xã hội E (dẫn Chương trình)');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (15, 'SP1023', 'Kỹ năng Xã hội F (nhảy Hiện đại)');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (16, 'SP1025', 'Kỹ năng Xã hội G (nhảy Đường phố)');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (17, 'SP1027', 'Kỹ năng Xã hội H (tư Duy phản Biện)');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (18, 'SP1045', 'Kỹ năng Xã hội I');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (19, 'SP1007', 'Pháp luật Việt Nam Đại cương');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (20, 'SP1031', 'Triết học Mác - Lênin');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (21, 'SP1033', 'Kinh tế Chính trị Mác - Lênin');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (22, 'SP1035', 'Chủ nghĩa Xã hội Khoa học');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (23, 'SP1037', 'Tư tưởng Hồ Chí Minh');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (24, 'SP1039', 'Lịch sử Đảng Cộng sản Việt Nam');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (25, 'SP1041', 'Kỹ năng mềm');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (26, 'LA1003', 'Anh văn 1');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (27, 'LA1005', 'Anh văn 2');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (28, 'LA1007', 'Anh văn 3');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (29, 'LA1009', 'Anh văn 4');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (30, 'CO1005', 'Nhập môn Điện toán');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (31, 'IM1013', 'Kinh tế học Đại cương');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (32, 'IM1023', 'Quản lý Sản xuất cho Kỹ sư');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (33, 'IM1025', 'Quản lý Dự án cho Kỹ sư');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (34, 'IM1027', 'Kinh tế Kỹ thuật');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (35, 'IM3001', 'Quản trị Kinh doanh cho Kỹ sư');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (36, 'CO2001', 'Kỹ năng Chuyên nghiệp cho Kỹ sư');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (37, 'CO1023', 'Hệ thống số');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (38, 'CO1027', 'Kỹ thuật Lập trình');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (39, 'CO2003', 'Cấu trúc Dữ liệu và Giải Thuật');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (40, 'CO2007', 'Kiến trúc Máy tính');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (41, 'CO2013', 'Hệ cơ sở Dữ liệu');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (42, 'CO2039', 'Lập trình Nâng cao');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (43, 'CO3101', 'Đồ án Tổng hợp - Hướng Trí tuệ Nhân tạo');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (44, 'CO3103', 'Đồ án Tổng hợp - hướng Công nghệ Phần mềm');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (45, 'CO3105', 'Đồ án Tổng hợp - Hướng Hệ thống Thông tin');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (46, 'CO3127', 'Đồ án tổng hợp - hướng kỹ thuật dữ liệu');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (47, 'CO3061', 'Nhập môn Trí tuệ Nhân tạo');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (48, 'CO3069', 'Mật mã và An ninh mạng');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (49, 'CO2017', 'Hệ điều hành');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (50, 'CO3001', 'Công nghệ Phần mềm');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (51, 'CO3005', 'Nguyên lý Ngôn ngữ Lập trình');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (52, 'CO3093', 'Mạng máy tính');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (53, 'CO2037', 'Mạch Điện - Điện tử');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (54, 'CCGDTC', 'Chứng chỉ Giáo Dục Thể Chất');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (55, 'MI1003', 'Giáo dục Quốc phòng');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (56, 'ENG_GE_2', 'Điều kiện tiếng Anh tốt nghiệp dạy và học bằng tiếng Anh');
INSERT INTO "subjects" ("id", "subject_code", "subject_name") VALUES (57, 'SA4001', 'Hoạt động sinh viên');

INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (6, 'B1', '2', 1, 'B1-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (7, 'B1', '2', 1, 'B1-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (8, 'B1', '2', 1, 'B1-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (9, 'B1', '2', 1, 'B1-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (10, 'B1', '2', 1, 'B1-105', 105);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (11, 'B1', '2', 1, 'B1-106', 106);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (12, 'B1', '2', 2, 'B1-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (13, 'B1', '2', 2, 'B1-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (14, 'B1', '2', 2, 'B1-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (15, 'B1', '2', 2, 'B1-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (16, 'B1', '2', 2, 'B1-205', 205);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (17, 'B1', '2', 2, 'B1-206', 206);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (18, 'B1', '2', 3, 'B1-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (19, 'B1', '2', 3, 'B1-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (20, 'B1', '2', 3, 'B1-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (21, 'B1', '2', 3, 'B1-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (22, 'B1', '2', 3, 'B1-305', 305);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (23, 'B1', '2', 3, 'B1-306', 306);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (24, 'B1', '2', 4, 'B1-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (25, 'B1', '2', 4, 'B1-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (26, 'B1', '2', 4, 'B1-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (27, 'B1', '2', 4, 'B1-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (28, 'B1', '2', 4, 'B1-405', 405);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (29, 'B1', '2', 4, 'B1-406', 406);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (30, 'B2', '2', 1, 'B2-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (31, 'B2', '2', 1, 'B2-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (32, 'B2', '2', 1, 'B2-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (33, 'B2', '2', 1, 'B2-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (34, 'B2', '2', 1, 'B2-105', 105);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (35, 'B2', '2', 1, 'B2-106', 106);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (36, 'B2', '2', 2, 'B2-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (37, 'B2', '2', 2, 'B2-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (38, 'B2', '2', 2, 'B2-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (39, 'B2', '2', 2, 'B2-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (40, 'B2', '2', 2, 'B2-205', 205);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (41, 'B2', '2', 2, 'B2-206', 206);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (42, 'B2', '2', 3, 'B2-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (43, 'B2', '2', 3, 'B2-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (44, 'B2', '2', 3, 'B2-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (45, 'B2', '2', 3, 'B2-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (46, 'B2', '2', 3, 'B2-305', 305);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (47, 'B2', '2', 3, 'B2-306', 306);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (48, 'B2', '2', 4, 'B2-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (49, 'B2', '2', 4, 'B2-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (50, 'B2', '2', 4, 'B2-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (51, 'B2', '2', 4, 'B2-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (52, 'B2', '2', 4, 'B2-405', 405);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (53, 'B2', '2', 4, 'B2-406', 406);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (54, 'B3', '2', 1, 'B3-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (55, 'B3', '2', 1, 'B3-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (56, 'B3', '2', 1, 'B3-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (57, 'B3', '2', 1, 'B3-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (58, 'B3', '2', 1, 'B3-105', 105);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (59, 'B3', '2', 1, 'B3-106', 106);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (60, 'B3', '2', 2, 'B3-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (61, 'B3', '2', 2, 'B3-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (62, 'B3', '2', 2, 'B3-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (63, 'B3', '2', 2, 'B3-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (64, 'B3', '2', 2, 'B3-205', 205);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (65, 'B3', '2', 2, 'B3-206', 206);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (66, 'B3', '2', 3, 'B3-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (67, 'B3', '2', 3, 'B3-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (68, 'B3', '2', 3, 'B3-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (69, 'B3', '2', 3, 'B3-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (70, 'B3', '2', 3, 'B3-305', 305);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (71, 'B3', '2', 3, 'B3-306', 306);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (72, 'B3', '2', 4, 'B3-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (73, 'B3', '2', 4, 'B3-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (74, 'B3', '2', 4, 'B3-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (75, 'B3', '2', 4, 'B3-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (76, 'B3', '2', 4, 'B3-405', 405);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (77, 'B3', '2', 4, 'B3-406', 406);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (78, 'B6', '2', 1, 'B6-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (79, 'B6', '2', 1, 'B6-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (80, 'B6', '2', 1, 'B6-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (81, 'B6', '2', 1, 'B6-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (82, 'B6', '2', 1, 'B6-105', 105);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (83, 'B6', '2', 1, 'B6-106', 106);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (84, 'B6', '2', 2, 'B6-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (85, 'B6', '2', 2, 'B6-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (86, 'B6', '2', 2, 'B6-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (87, 'B6', '2', 2, 'B6-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (88, 'B6', '2', 2, 'B6-205', 205);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (89, 'B6', '2', 2, 'B6-206', 206);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (90, 'B6', '2', 3, 'B6-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (91, 'B6', '2', 3, 'B6-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (92, 'B6', '2', 3, 'B6-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (93, 'B6', '2', 3, 'B6-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (94, 'B6', '2', 3, 'B6-305', 305);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (95, 'B6', '2', 3, 'B6-306', 306);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (96, 'B6', '2', 4, 'B6-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (97, 'B6', '2', 4, 'B6-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (98, 'B6', '2', 4, 'B6-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (99, 'B6', '2', 4, 'B6-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (100, 'B6', '2', 4, 'B6-405', 405);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (101, 'B6', '2', 4, 'B6-406', 406);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (102, 'B1', '1', 1, 'B1-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (103, 'B1', '1', 1, 'B1-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (104, 'B1', '1', 1, 'B1-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (105, 'B1', '1', 1, 'B1-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (106, 'B1', '1', 2, 'B1-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (107, 'B1', '1', 2, 'B1-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (108, 'B1', '1', 2, 'B1-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (109, 'B1', '1', 2, 'B1-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (110, 'B1', '1', 3, 'B1-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (111, 'B1', '1', 3, 'B1-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (112, 'B1', '1', 3, 'B1-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (113, 'B1', '1', 3, 'B1-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (114, 'B1', '1', 4, 'B1-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (115, 'B1', '1', 4, 'B1-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (116, 'B1', '1', 4, 'B1-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (117, 'B1', '1', 4, 'B1-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (118, 'B2', '1', 1, 'B2-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (119, 'B2', '1', 1, 'B2-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (120, 'B2', '1', 1, 'B2-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (121, 'B2', '1', 1, 'B2-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (122, 'B2', '1', 2, 'B2-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (123, 'B2', '1', 2, 'B2-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (124, 'B2', '1', 2, 'B2-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (125, 'B2', '1', 2, 'B2-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (126, 'B2', '1', 3, 'B2-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (127, 'B2', '1', 3, 'B2-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (128, 'B2', '1', 3, 'B2-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (129, 'B2', '1', 3, 'B2-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (130, 'B2', '1', 4, 'B2-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (131, 'B2', '1', 4, 'B2-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (132, 'B2', '1', 4, 'B2-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (133, 'B2', '1', 4, 'B2-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (134, 'B4', '1', 1, 'B4-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (135, 'B4', '1', 1, 'B4-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (136, 'B4', '1', 1, 'B4-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (137, 'B4', '1', 1, 'B4-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (138, 'B4', '1', 2, 'B4-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (139, 'B4', '1', 2, 'B4-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (140, 'B4', '1', 2, 'B4-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (141, 'B4', '1', 2, 'B4-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (142, 'B4', '1', 3, 'B4-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (143, 'B4', '1', 3, 'B4-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (144, 'B4', '1', 3, 'B4-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (145, 'B4', '1', 3, 'B4-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (146, 'B4', '1', 4, 'B4-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (147, 'B4', '1', 4, 'B4-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (148, 'B4', '1', 4, 'B4-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (149, 'B4', '1', 4, 'B4-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (150, 'C6', '1', 1, 'C6-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (151, 'C6', '1', 1, 'C6-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (152, 'C6', '1', 1, 'C6-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (153, 'C6', '1', 1, 'C6-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (154, 'C6', '1', 2, 'C6-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (155, 'C6', '1', 2, 'C6-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (156, 'C6', '1', 2, 'C6-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (157, 'C6', '1', 2, 'C6-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (158, 'C6', '1', 3, 'C6-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (159, 'C6', '1', 3, 'C6-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (160, 'C6', '1', 3, 'C6-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (161, 'C6', '1', 3, 'C6-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (162, 'C6', '1', 4, 'C6-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (163, 'C6', '1', 4, 'C6-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (164, 'C6', '1', 4, 'C6-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (165, 'C6', '1', 4, 'C6-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (166, 'C4', '1', 1, 'C4-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (167, 'C4', '1', 1, 'C4-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (168, 'C4', '1', 1, 'C4-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (169, 'C4', '1', 1, 'C4-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (170, 'C4', '1', 2, 'C4-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (171, 'C4', '1', 2, 'C4-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (172, 'C4', '1', 2, 'C4-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (173, 'C4', '1', 2, 'C4-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (174, 'C4', '1', 3, 'C4-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (175, 'C4', '1', 3, 'C4-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (176, 'C4', '1', 3, 'C4-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (177, 'C4', '1', 3, 'C4-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (178, 'C4', '1', 4, 'C4-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (179, 'C4', '1', 4, 'C4-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (180, 'C4', '1', 4, 'C4-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (181, 'C4', '1', 4, 'C4-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (182, 'B8', '1', 1, 'B8-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (183, 'B8', '1', 1, 'B8-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (184, 'B8', '1', 1, 'B8-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (185, 'B8', '1', 1, 'B8-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (186, 'B8', '1', 2, 'B8-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (187, 'B8', '1', 2, 'B8-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (188, 'B8', '1', 2, 'B8-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (189, 'B8', '1', 2, 'B8-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (190, 'B8', '1', 3, 'B8-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (191, 'B8', '1', 3, 'B8-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (192, 'B8', '1', 3, 'B8-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (193, 'B8', '1', 3, 'B8-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (194, 'B8', '1', 4, 'B8-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (195, 'B8', '1', 4, 'B8-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (196, 'B8', '1', 4, 'B8-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (197, 'B8', '1', 4, 'B8-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (198, 'B9', '1', 1, 'B9-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (199, 'B9', '1', 1, 'B9-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (200, 'B9', '1', 1, 'B9-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (201, 'B9', '1', 1, 'B9-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (202, 'B9', '1', 2, 'B9-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (203, 'B9', '1', 2, 'B9-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (204, 'B9', '1', 2, 'B9-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (205, 'B9', '1', 2, 'B9-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (206, 'B9', '1', 3, 'B9-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (207, 'B9', '1', 3, 'B9-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (208, 'B9', '1', 3, 'B9-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (209, 'B9', '1', 3, 'B9-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (210, 'B9', '1', 4, 'B9-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (211, 'B9', '1', 4, 'B9-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (212, 'B9', '1', 4, 'B9-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (213, 'B9', '1', 4, 'B9-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (214, 'B10', '1', 1, 'B10-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (215, 'B10', '1', 1, 'B10-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (216, 'B10', '1', 1, 'B10-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (217, 'B10', '1', 1, 'B10-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (218, 'B10', '1', 2, 'B10-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (219, 'B10', '1', 2, 'B10-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (220, 'B10', '1', 2, 'B10-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (221, 'B10', '1', 2, 'B10-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (222, 'B10', '1', 3, 'B10-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (223, 'B10', '1', 3, 'B10-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (224, 'B10', '1', 3, 'B10-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (225, 'B10', '1', 3, 'B10-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (226, 'B10', '1', 4, 'B10-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (227, 'B10', '1', 4, 'B10-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (228, 'B10', '1', 4, 'B10-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (229, 'B10', '1', 4, 'B10-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (230, 'C5', '1', 1, 'C5-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (231, 'C5', '1', 1, 'C5-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (232, 'C5', '1', 1, 'C5-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (233, 'C5', '1', 1, 'C5-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (234, 'C5', '1', 2, 'C5-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (235, 'C5', '1', 2, 'C5-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (236, 'C5', '1', 2, 'C5-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (237, 'C5', '1', 2, 'C5-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (238, 'C5', '1', 3, 'C5-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (239, 'C5', '1', 3, 'C5-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (240, 'C5', '1', 3, 'C5-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (241, 'C5', '1', 3, 'C5-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (242, 'C5', '1', 4, 'C5-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (243, 'C5', '1', 4, 'C5-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (244, 'C5', '1', 4, 'C5-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (245, 'C5', '1', 4, 'C5-404', 404);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (246, 'A4', '1', 1, 'A4-101', 101);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (247, 'A4', '1', 1, 'A4-102', 102);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (248, 'A4', '1', 1, 'A4-103', 103);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (249, 'A4', '1', 1, 'A4-104', 104);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (250, 'A4', '1', 2, 'A4-201', 201);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (251, 'A4', '1', 2, 'A4-202', 202);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (252, 'A4', '1', 2, 'A4-203', 203);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (253, 'A4', '1', 2, 'A4-204', 204);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (254, 'A4', '1', 3, 'A4-301', 301);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (255, 'A4', '1', 3, 'A4-302', 302);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (256, 'A4', '1', 3, 'A4-303', 303);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (257, 'A4', '1', 3, 'A4-304', 304);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (258, 'A4', '1', 4, 'A4-401', 401);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (259, 'A4', '1', 4, 'A4-402', 402);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (260, 'A4', '1', 4, 'A4-403', 403);
INSERT INTO "rooms" ("id", "building", "campus", "floor", "name", "number") VALUES (261, 'A4', '1', 4, 'A4-404', 404);

Select * from rooms;