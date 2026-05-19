[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/y2vzUjS9)
# Chapter 1 — Exercise 1: JPA CRUD with Repository Pattern

> **Môn học:** HSF302 — JPA & Spring Framework
> **Chương:** 01 — JPA và JPA Mapping
> **Thời hạn:** Theo deadline trên Classroom
> **Tổng điểm:** 100 (chấm tự động qua GitHub Classroom)

---

## 🎯 Mục tiêu

Sau khi hoàn thành bài tập, bạn sẽ:
- Hiểu cách map Java class với bảng DB bằng **JPA annotation** (`@Entity`, `@Id`, `@Column`...).
- Cài đặt được **CRUD** đầy đủ với `EntityManager` và **transaction**.
- Hiểu được mô hình kiến trúc **N-Layer**: `Entity → DAO → Repository → Service → Application`.
- Viết được **business validation** trong tầng Service.
- Hiểu cách inject dependency bằng **constructor** để dễ test.

---

## 📂 Cấu trúc project

```
chapter1-exercise1/
├── .github/
│   ├── classroom/
│   │   └── autograding.json          # Cấu hình chấm điểm GitHub Classroom
│   └── workflows/
│       └── classroom.yml             # GitHub Actions tự động chạy test
│
├── src/main/java/traltb/fudn/chapter1_exercise1/
│   ├── Chapter1Exercise1Application.java    # TODO #6 — Main demo CRUD
│   ├── pojo/
│   │   ├── Student.java              # TODO #1 — Entity Student
│   │   └── Book.java                 # TODO #2 — Entity Book
│   ├── dao/
│   │   └── StudentDAO.java           # TODO #3 — CRUD với EntityManager
│   ├── repository/
│   │   ├── StudentRepository.java    # ✅ Interface — đã hoàn chỉnh
│   │   └── StudentRepositoryImpl.java # TODO #4 — delegate xuống DAO
│   └── service/
│       ├── StudentService.java       # ✅ Interface — đã hoàn chỉnh
│       └── StudentServiceImpl.java   # TODO #5 — Business logic + validation
│
├── src/main/resources/
│   ├── application.properties
│   └── META-INF/persistence.xml      # ✅ 2 persistence-unit (MSSQL + H2 test)
│
├── src/test/java/                    # ⚠️ KHÔNG SỬA — dùng để chấm điểm tự động
│   └── traltb/fudn/chapter1_exercise1/
│       ├── pojo/StudentEntityTest.java
│       ├── pojo/BookEntityTest.java
│       ├── dao/StudentDAOTest.java
│       ├── repository/StudentRepositoryImplTest.java
│       ├── service/StudentServiceImplTest.java
│       └── integration/CrudIntegrationTest.java
│
├── pom.xml
└── README.md (file này)
```

---

## 🔧 Cài đặt môi trường

### Yêu cầu
- **JDK 21** (Temurin / Oracle / OpenJDK)
- **Maven 3.9+** (hoặc dùng wrapper `./mvnw`)
- **IntelliJ IDEA** (khuyến nghị) hoặc IDE Java khác
- **SQL Server 2019+** (chỉ cần để chạy phần Main, KHÔNG cần cho test)

### Bước 1: Clone repository
```bash
git clone https://github.com/<your-org>/<your-repo>.git
cd chapter1-exercise1
```

### Bước 2: Mở project bằng IntelliJ
`File → Open → chọn folder chapter1-exercise1 → Import as Maven project`.

### Bước 3: Bật Lombok plugin
1. `File → Settings → Plugins → search "Lombok" → Install`.
2. `File → Settings → Build → Compiler → Annotation Processors → Enable annotation processing`.

### Bước 4: (Tuỳ chọn) Cấu hình SQL Server cho phần Main
Mở `src/main/resources/META-INF/persistence.xml`, sửa các property của persistence-unit **`hsf302-chapter1`** (KHÔNG phải `hsf302-chapter1-test`):

```xml
<property name="jakarta.persistence.jdbc.url"
          value="jdbc:sqlserver://localhost:1433;databaseName=HSF301_Chapter1_Ex1;encrypt=true;trustServerCertificate=true"/>
<property name="jakarta.persistence.jdbc.user" value="YOUR_USER"/>
<property name="jakarta.persistence.jdbc.password" value="YOUR_PASSWORD"/>
```

Tạo database `HSF301_Chapter1_Ex1` trước khi chạy:
```sql
CREATE DATABASE HSF301_Chapter1_Ex1;
```

> ✅ **Không cần SQL Server để làm bài / chạy test.** Test dùng H2 in-memory tự động.

---

## ✏️ Cách làm bài

### 1. Đọc các file có comment `TODO #x.y` — đó là chỗ bạn cần viết code.

Mỗi TODO có format:
```java
// TODO #X.Y (N điểm): Mô tả việc cần làm
//   1. Step 1...
//   2. Step 2...
throw new UnsupportedOperationException("TODO #X.Y: implement xxx()");
```

→ **Bạn xoá dòng `throw` và viết code thật.**

### 2. Danh sách TODO

| TODO | File | Mô tả | Điểm |
|---|---|---|---|
| #1 | `pojo/Student.java` | Entity Student với @Entity, @Id, @Column, @OneToMany | 15 |
| #2 | `pojo/Book.java` | Entity Book với @Entity, @Id, @Column, @ManyToOne | 10 |
| #3 | `dao/StudentDAO.java` | 5 method CRUD: save, findById, findAll, update, delete | 25 |
| #4 | `repository/StudentRepositoryImpl.java` | Delegate xuống DAO + wrap Optional | 10 |
| #5 | `service/StudentServiceImpl.java` | Validation + business logic + delegate Repository | 25 |
| #6 | `Chapter1Exercise1Application.java` | Demo 5 thao tác CRUD trên MSSQL | (chấm bằng integration test, 15đ) |
| **Tổng** | | | **100** |

### 3. Chạy test local trước khi push

```bash
# Chạy tất cả test
./mvnw clean test

# Chạy 1 nhóm test (ví dụ TODO #1)
./mvnw test -Dtest=StudentEntityTest

# Chạy 1 test method cụ thể
./mvnw test -Dtest=StudentDAOTest#save_shouldPersistStudent
```

Nếu thấy:
- ✅ `BUILD SUCCESS` + `Tests run: X, Failures: 0` → TODO đó đã đúng.
- ❌ `BUILD FAILURE` với `UnsupportedOperationException` → còn TODO chưa làm.
- ❌ `AssertionError: Expected X but was Y` → code sai logic, đọc lại comment và sửa.

### 4. Commit và push

```bash
git add .
git commit -m "Complete TODO #1: Student entity"
git push origin main
```

→ GitHub Actions tự động chạy autograding (xem tab **Actions** trên GitHub).
→ Điểm hiển thị ở tab **Pull Requests** hoặc **Commits** (icon ✅/❌).

---

## 🏆 Cách chấm điểm

Điểm được chấm tự động qua **GitHub Classroom Autograding** + **GitHub Actions**.

| Test class | TODO tương ứng | Điểm tối đa | Pass/Fail rule |
|---|---|---|---|
| `StudentEntityTest` | #1 | 15 | All-or-nothing |
| `BookEntityTest` | #2 | 10 | All-or-nothing |
| `StudentDAOTest` | #3 | 25 | All-or-nothing |
| `StudentRepositoryImplTest` | #4 | 10 | All-or-nothing |
| `StudentServiceImplTest` | #5 | 25 | All-or-nothing |
| `CrudIntegrationTest` | (tất cả) | 15 | All-or-nothing |

> ⚠️ **Quan trọng:** Mỗi test class chỉ tính điểm nếu **TẤT CẢ method test trong class đó pass**.
> → Nếu chỉ pass 4/5 method → 0 điểm cho TODO đó.

Xem chi tiết grading rubric trong [INSTRUCTIONS.md](./INSTRUCTIONS.md).

---

## 🐛 Troubleshooting

### Lỗi: "Lombok cannot resolve symbol"
- Cài Lombok plugin trong IntelliJ.
- Enable annotation processing.
- Rebuild project: `Build → Rebuild Project`.

### Lỗi: "Cannot find driver org.h2.Driver" khi chạy test
- Kiểm tra `pom.xml` có dependency `com.h2database:h2` với `<scope>test</scope>` chưa.
- Chạy `./mvnw clean install -DskipTests` để refresh dependency.

### Lỗi: "No persistence provider for EntityManager named hsf302-chapter1-test"
- Kiểm tra file `src/main/resources/META-INF/persistence.xml` có persistence-unit name là **`hsf302-chapter1-test`**.
- KHÔNG sửa nội dung persistence-unit này.

### Lỗi: Test pass local nhưng fail trên GitHub Actions
- Check log ở tab **Actions** → click vào workflow run → xem step nào fail.
- Tải artifact **surefire-reports** để xem chi tiết.
- Có thể do: file encoding, path Windows vs Linux, hoặc chưa commit hết file.

### Lỗi: "ClassNotFoundException" trong test
- Chạy `./mvnw clean compile` trước khi test.

---

## 📚 Tài liệu tham khảo

- 📖 [SlideNotes/Chapter_01_JPA_Mapping.md](../SlideNotes/Chapter_01_JPA_Mapping.md) — lý thuyết JPA + ví dụ code.
- 🔗 [Jakarta Persistence 3.1 Specification](https://jakarta.ee/specifications/persistence/3.1/)
- 🔗 [Hibernate 6 Documentation](https://docs.jboss.org/hibernate/orm/6.5/userguide/html_single/Hibernate_User_Guide.html)
- 🔗 [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- 🔗 [AssertJ Documentation](https://assertj.github.io/doc/)
- 🔗 [Mockito Documentation](https://site.mockito.org/)

---

## ❓ FAQ

**Q: Tôi có được sửa file test không?**
A: ❌ **KHÔNG.** Test là để chấm điểm. Sửa test = vi phạm academic policy. GitHub Classroom có log thay đổi.

**Q: Tôi có được thêm method/field mới vào Entity không?**
A: ✅ Có, miễn là không xoá field/method có sẵn (test cần chúng).

**Q: Tôi có được thay Lombok bằng getter/setter tự viết không?**
A: ✅ Có. Lombok chỉ là tiện ích — viết getter/setter thủ công cũng pass test.

**Q: Có thể commit nhiều lần không?**
A: ✅ Tha hồ. Mỗi push trigger 1 lần grading. Điểm cuối = lần grading cuối cùng trước deadline.

**Q: Test fail trên CI nhưng pass local thì sao?**
A: Check Java version (CI dùng 21). Check encoding file (UTF-8 không BOM). Check `mvn clean test` không phải chỉ `mvn test`.

---

## 📜 Academic Integrity

- ✅ Được tham khảo SlideNotes, tài liệu chính thức, Stack Overflow.
- ✅ Được hỏi giảng viên / TA.
- ❌ KHÔNG copy code từ bạn khác.
- ❌ KHÔNG share solution lên GitHub public / Discord / Facebook.
- ❌ KHÔNG sửa file test để hack điểm.

> Vi phạm sẽ bị **0 điểm toàn bài** và báo lên trường.

---

## 📝 License & Credits

- Template by **traltb** — FPT University Da Nang
- HSF302 — Spring 2026
