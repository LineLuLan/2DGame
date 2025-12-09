# Báo cáo Data Structure & Algorithm cho Project 2D Game

## 1. Luật chơi và mục tiêu game
- Mô tả tổng quan về gameplay: điều khiển nhân vật di chuyển, chiến đấu với quái vật, thu thập vật phẩm, vượt qua các bản đồ.
- Quy tắc thắng/thua, các trạng thái game (chơi, dừng, kết thúc, v.v.).
- Các loại đối tượng: người chơi, NPC, quái vật, vật phẩm, bản đồ.

## 2. Thiết kế class (Class Diagram & Mô tả)
- **Sơ đồ class tổng thể**: thể hiện mối quan hệ kế thừa, thành phần giữa các class chính.
- **Các module chính**:
  - `Entity`: lớp cha cho mọi đối tượng di chuyển (Player, NPC, Monster, Projectile, Object).
  - `Player`: điều khiển bởi người chơi, có inventory, trạng thái, hành động.
  - `NPC`: đối tượng không điều khiển, có thể tương tác, hội thoại.
  - `Monster`: kế thừa Entity, có AI, tấn công, di chuyển tự động.
  - `Object`: các vật phẩm, vật cản, rương, vũ khí, v.v.
  - `Projectile`: đạn, phép thuật, vật thể bay.
  - `DataStorage`, `SaveLoad`: lưu/đọc trạng thái game.
  - `PathFinder`, `Node`: thuật toán tìm đường.
  - `GamePanel`, `App`: khởi tạo, quản lý vòng lặp game, giao diện.
  - `UI`, `Sound`, `KeyHandler`: giao diện, âm thanh, xử lý input.

## 3. Cấu trúc dữ liệu sử dụng
- **Mảng 2 chiều**: quản lý bản đồ, vị trí đối tượng trên map.
- **Danh sách đối tượng (ArrayList<Entity>, v.v.)**: quản lý quái vật, NPC, vật phẩm động.
- **Object/Serialization**: lưu trạng thái game (DataStorage implements Serializable).
- **Các biến trạng thái, cờ hiệu**: quản lý trạng thái nhân vật, game, sự kiện.

## 4. Thuật toán chính (Main Algorithms)
### a. Pathfinding (A* Algorithm)
- Sử dụng thuật toán tìm đường A* (A Star) với các bước:
  - Khởi tạo node, đặt điểm bắt đầu/đích (Initialize nodes, set start/goal).
  - Tính toán chi phí di chuyển (getCost - Calculate movement cost: G, H, F).
  - Mở rộng node, tìm đường đi tối ưu (openNode, search - Expand nodes, find optimal path).
  - Truy vết đường đi (trackThePath - Trace back the path).
- Ứng dụng: di chuyển quái vật, NPC theo mục tiêu (Move monsters/NPCs toward targets).

### b. Collision Detection (CollisionChecker)
- Kiểm tra va chạm giữa entity với tile, object, entity khác (Check collision between entities, tiles, objects).
- Xác định hướng di chuyển hợp lệ, xử lý tương tác khi va chạm (Determine valid movement, handle interactions).

### c. Event Handling & Data Persistence (SaveLoad, DataStorage)
- Lưu trạng thái game (vị trí, máu, vật phẩm, tiến trình) bằng serialization (Save game state using Java serialization).
- Đọc lại trạng thái khi load game (Load state from file).

### d. AI Behavior & Interaction (Monster/NPC AI)
- Quái vật tự động di chuyển, tấn công, đổi hướng theo thuật toán (Monsters move, attack, change direction using AI logic).
- NPC có hội thoại, hành động riêng biệt (NPCs have dialogue, unique actions).
- Sử dụng các hàm như setAction, update, speak, moveTowardPlayer (Use setAction, update, speak, moveTowardPlayer).

## 5. Design Pattern đã áp dụng (Applied Design Patterns)
- **OOP (Inheritance, Polymorphism)**:  
  - Entity là lớp cha cho mọi đối tượng di chuyển (Entity is the superclass for all moving objects: Player, NPC, Monster, Object, Projectile).
  - Các class con kế thừa và override các phương thức như update, use, interact (Subclasses override methods for specific behaviors).
  - Chức năng: Tăng khả năng mở rộng, tái sử dụng mã, dễ bảo trì (Function: Extensibility, code reuse, maintainability).

- **Template Method Pattern**:  
  - Các phương thức như update(), use(), interact() được định nghĩa ở Entity và được override ở các class con (update, use, interact are defined in Entity and overridden in subclasses).
  - Chức năng: Cho phép định nghĩa khung thuật toán chung, các bước cụ thể do class con quyết định (Function: Define algorithm skeleton, subclasses implement specific steps).

- **Strategy Pattern**:  
  - Hành vi AI, di chuyển, tấn công của quái vật/NPC có thể thay đổi động qua các phương thức như setAction(), moveTowardPlayer() (AI, movement, attack behaviors can be changed at runtime).
  - Chức năng: Tách biệt thuật toán hành vi, dễ mở rộng thêm chiến lược mới (Function: Separate behavior algorithms, easily add new strategies).

- **Singleton/Façade Pattern**:  
  - GamePanel hoặc App quản lý toàn bộ vòng lặp game, trạng thái, giao diện (GamePanel or App manages the game loop, state, UI).
  - Chức năng: Đảm bảo chỉ có một thể hiện quản lý game, cung cấp giao diện đơn giản cho các module khác (Function: Ensure single instance, provide unified interface).

- **Observer Pattern (nếu có)**:  
  - UI cập nhật khi có sự kiện thay đổi trạng thái game (UI updates on game state changes).
  - Chức năng: Tự động đồng bộ giao diện với dữ liệu, giảm phụ thuộc giữa các module (Function: Auto-sync UI with data, reduce module coupling).

## 6. Sử dụng Git
- Lịch sử commit thể hiện quá trình phát triển, sửa lỗi, thêm tính năng.
- Quản lý phiên bản, rollback khi cần thiết.

## 7. Giao diện đồ họa (GUI)
- Sử dụng Java Swing (JPanel, Graphics2D) để vẽ game, UI, hiệu ứng.
- Xử lý input qua KeyHandler, cập nhật màn hình qua GamePanel.
- Các màn hình: title, chơi, pause, inventory, hội thoại, game over.

## 8. Các tính năng mở rộng/bonus
- Thêm quái vật, vật phẩm, bản đồ mới.
- Hiệu ứng đặc biệt (particle, animation).
- Tính năng save/load, hội thoại, giao dịch với NPC.
- Áp dụng thêm design pattern, tối ưu thuật toán.

## 9. Tổng kết, đánh giá, hướng phát triển
- Đánh giá hiệu quả thuật toán, cấu trúc dữ liệu.
- Đề xuất cải tiến: tối ưu AI, mở rộng gameplay, nâng cấp đồ họa, đa nền tảng.

## 10. Đề xuất cải thiện để tăng tính đa dạng ngôn ngữ (Language Portability)
- **Tách biệt logic game khỏi giao diện (UI/Graphics):**  
  Thiết kế các module xử lý logic (AI, Pathfinding, Collision, Data) độc lập với phần giao diện, giúp dễ dàng chuyển đổi sang các ngôn ngữ khác như Python, C#, JavaScript.
- **Sử dụng interface/abstract class cho các thành phần chính:**  
  Định nghĩa các interface cho Entity, GameObject, Service... để khi chuyển sang ngôn ngữ khác chỉ cần implement lại interface.
- **Tránh phụ thuộc vào thư viện đặc thù Java:**  
  Hạn chế sử dụng các class chỉ có ở Java (ví dụ: Serializable, Graphics2D), thay vào đó dùng các kiểu dữ liệu và cấu trúc phổ biến (array, list, dict/map).
- **Viết lại các thuật toán cốt lõi (A*, Collision, AI) bằng pseudo-code hoặc Python:**  
  Có thể chuyển các thuật toán này sang Python dễ dàng nhờ cấu trúc rõ ràng, không phụ thuộc Java.
- **Tách phần lưu trữ dữ liệu (save/load) thành module riêng:**  
  Sử dụng định dạng file phổ biến như JSON, XML thay vì serialization Java, giúp dễ đọc/ghi ở nhiều ngôn ngữ.
- **Ví dụ chuyển đổi thuật toán A* sang Python:**

```python
# Python: A* Pathfinding Example
import heapq

def astar(start, goal, neighbors_fn, heuristic_fn):
    open_set = []
    heapq.heappush(open_set, (0, start))
    came_from = {}
    g_score = {start: 0}
    while open_set:
        _, current = heapq.heappop(open_set)
        if current == goal:
            # reconstruct path
            path = []
            while current in came_from:
                path.append(current)
                current = came_from[current]
            path.reverse()
            return path
        for neighbor in neighbors_fn(current):
            tentative_g = g_score[current] + 1
            if neighbor not in g_score or tentative_g < g_score[neighbor]:
                came_from[neighbor] = current
                g_score[neighbor] = tentative_g
                f_score = tentative_g + heuristic_fn(neighbor, goal)
                heapq.heappush(open_set, (f_score, neighbor))
    return None
```

- **Kết luận:**  
  Nếu tách biệt tốt logic và sử dụng các cấu trúc dữ liệu phổ biến, project có thể chuyển sang Python hoặc các ngôn ngữ khác dễ dàng, tăng tính đa nền tảng và mở rộng cộng đồng phát triển.

---

*Báo cáo nên có hình minh họa sơ đồ class, ví dụ code, giải thích thuật toán, và nhận xét về hiệu quả từng giải pháp.*
