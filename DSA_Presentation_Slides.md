# Nội dung Slide Thuyết Trình DSA 2D Game

---

## Slide 1: Giới thiệu đề tài
- Tên project: 2D Game
- Môn: Data Structure & Algorithm
- Thành viên nhóm, giảng viên hướng dẫn

---

## Slide 2: Luật chơi & mục tiêu
- Điều khiển nhân vật di chuyển, chiến đấu, thu thập vật phẩm
- Vượt qua các bản đồ, đánh bại quái vật, hoàn thành nhiệm vụ

---

## Slide 3: Sơ đồ class & kiến trúc tổng thể
- Sơ đồ class tổng quan (Entity, Player, NPC, Monster, Object, Projectile, Data, AI, Main, UI)
- Mối quan hệ kế thừa, thành phần

---

## Slide 4: Cấu trúc dữ liệu sử dụng
- Mảng 2 chiều: quản lý bản đồ, vị trí
- Danh sách đối tượng: quản lý quái vật, NPC, vật phẩm
- Serialization/Object: lưu trạng thái game

---

## Slide 5: Thuật toán chính (Main Algorithms)
- Pathfinding (A* Algorithm): tìm đường cho quái vật/NPC
- Collision Detection: kiểm tra va chạm
- Event Handling & Data Persistence: lưu/đọc trạng thái game
- AI Behavior: hành vi quái vật, NPC

---

## Slide 6: Design Patterns đã áp dụng
- OOP (Inheritance, Polymorphism): mở rộng, tái sử dụng mã
- Template Method: định nghĩa khung thuật toán, override ở class con
- Strategy: thay đổi hành vi AI động
- Singleton/Façade: quản lý vòng lặp game, trạng thái
- Observer: cập nhật UI khi thay đổi dữ liệu

---

## Slide 7: Giao diện đồ họa (GUI)
- Java Swing: vẽ game, UI, hiệu ứng
- Xử lý input, các màn hình: title, chơi, pause, inventory, hội thoại

---

## Slide 8: Sử dụng Git & phát triển nhóm
- Quản lý phiên bản, lịch sử commit
- Làm việc nhóm, rollback khi cần thiết

---

## Slide 9: Tính năng mở rộng & đề xuất cải tiến
- Thêm quái vật, vật phẩm, bản đồ mới
- Hiệu ứng đặc biệt, tối ưu AI
- Đề xuất tách logic, chuyển đổi sang Python/C#/JS

---

## Slide 10: Kết luận
- Tổng kết hiệu quả thuật toán, cấu trúc dữ liệu, design pattern
- Hướng phát triển: đa nền tảng, mở rộng cộng đồng

---

*Lưu ý: Slide nên có hình minh họa sơ đồ class, ví dụ code ngắn, hình ảnh gameplay, và highlight các điểm mạnh về thuật toán & cấu trúc dữ liệu.*
