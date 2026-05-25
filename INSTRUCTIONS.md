# INSTRUCTIONS — Chi tiết yêu cầu và Grading Rubric

> Đọc kỹ file này TRƯỚC khi bắt đầu code.

---

## 📋 Tổng quan

Project mô phỏng hệ thống quản lý **Student** và **Book** đơn giản với JPA + Repository Pattern.

**Bạn cần hoàn thành 6 TODO** để đạt 100 điểm.

```
┌──────────────────────────────────────────────────────────────┐
│ KIẾN TRÚC N-LAYER                                            │
│                                                              │
│  Chapter1Exercise1Application (Main)         ← TODO #6       │
│           │                                                  │
│           ▼                                                  │
│  StudentService ← StudentServiceImpl         ← TODO #5       │
│           │                                                  │
│           ▼                                                  │
│  StudentRepository ← StudentRepositoryImpl   ← TODO #4       │
│           │                                                  │
│           ▼                                                  │
│  StudentDAO (JPA EntityManager)              ← TODO #3       │
│           │                                                  │
│           ▼                                                  │
│  Student / Book (JPA Entity)                 ← TODO #1, #2  │
│           │                                                  │
│           ▼                                                  │
│  Database (MSSQL hoặc H2)                                    │
└──────────────────────────────────────────────────────────────┘
```

---

## 🎯 TODO #1 — Student Entity (15 điểm)

**File:** `src/main/java/traltb/fudn/chapter1_exercise1/pojo/Student.java`

### Yêu cầu
Map class `Student` với bảng `students`:

| Column | Type | Constraint |
|---|---|---|
| `id` | BIGINT | PRIMARY KEY, AUTO_GENERATED |
| `email` | VARCHAR | NOT NULL, UNIQUE |
| `password` | VARCHAR | NOT NULL |
| `first_name` | VARCHAR | NOT NULL |
| `last_name` | VARCHAR | NOT NULL |
| `marks` | INT | nullable OK |

Quan hệ: `Student 1 ─── N Book` (qua FK `student_id` trong bảng `books`).

### Checklist
- [ ] Class có `@Entity`
- [ ] Class có `@Table(name = "students")`
- [ ] Lombok: `@Getter @Setter @NoArgsConstructor @AllArgsConstructor`
- [ ] Field `id` là `Long`, có `@Id` + `@GeneratedValue(strategy = GenerationType.AUTO)`
- [ ] Field `email` là `String`, `@Column(unique=true, nullable=false)`
- [ ] Field `password` là `String`, `@Column(nullable=false)`
- [ ] Field `firstName` là `String`, `@Column(name="first_name", nullable=false)`
- [ ] Field `lastName` là `String`, `@Column(name="last_name", nullable=false)`
- [ ] Field `marks` là `int` hoặc `Integer`, `@Column(name="marks")`
- [ ] Field `books` kiểu `Set<Book>`, khởi tạo `new HashSet<>()`
- [ ] `books` có `@OneToMany(cascade=ALL, orphanRemoval=true)`
- [ ] `books` có `@JoinColumn(name="student_id")`

### Test class: `StudentEntityTest` (9 method)
Chấm điểm: **all-or-nothing** (tất cả 9 method phải pass).

---

## 🎯 TODO #2 — Book Entity (10 điểm)

**File:** `src/main/java/traltb/fudn/chapter1_exercise1/pojo/Book.java`

### Yêu cầu
Map class `Book` với bảng `books`:

| Column | Type | Constraint |
|---|---|---|
| `id` | BIGINT | PRIMARY KEY, AUTO_GENERATED |
| `title` | VARCHAR | NOT NULL |
| `author` | VARCHAR | NOT NULL |
| `isbn` | VARCHAR | NOT NULL, UNIQUE |
| `student_id` | BIGINT | FK → students.id |

### Checklist
- [ ] Class có `@Entity` + `@Table(name="books")`
- [ ] Lombok: `@Getter @Setter @NoArgsConstructor @AllArgsConstructor`
- [ ] Field `id` Long, `@Id` + `@GeneratedValue`
- [ ] Field `title` String, `@Column(nullable=false)`
- [ ] Field `author` String, `@Column(nullable=false)`
- [ ] Field `isbn` String, `@Column(unique=true, nullable=false)`
- [ ] Field `student` kiểu `Student`, `@ManyToOne` + `@JoinColumn(name="student_id")`

### Test class: `BookEntityTest` (8 method)

---

## 🎯 TODO #3 — StudentDAO (25 điểm)

**File:** `src/main/java/traltb/fudn/chapter1_exercise1/dao/StudentDAO.java`

### Yêu cầu
Implement 5 method CRUD dùng `EntityManager`. Template **đã có sẵn 2 constructor**:
```java
public StudentDAO() { ... }                            // dùng MSSQL
public StudentDAO(EntityManagerFactory emf) { ... }    // dùng cho test với H2 — KHÔNG xoá!
```

### Pattern chuẩn
Mỗi method dùng pattern này:
```java
EntityManager em = emf.createEntityManager();
try {
    em.getTransaction().begin();  // chỉ cần cho write operation
    // ... code
    em.getTransaction().commit();
} catch (Exception e) {
    if (em.getTransaction().isActive()) {
        em.getTransaction().rollback();
    }
    throw e;
} finally {
    em.close();
}
```

### Checklist
- [ ] `save(Student s)` — persist + commit (5đ)
- [ ] `findById(Long id)` — em.find() — không cần transaction (5đ)
- [ ] `findAll()` — JPQL `"SELECT s FROM Student s"` (5đ)
- [ ] `update(Student s)` — find + copy fields + commit (5đ)
- [ ] `delete(Long id)` — find + remove + commit (5đ)

### Test class: `StudentDAOTest` (12 method)

⚠️ **Lưu ý:**
- Phải **rollback** transaction khi exception.
- Phải `em.close()` trong `finally`.
- `update()`: chỉ copy email/password/firstName/lastName/marks, **KHÔNG** copy id và books.
- `update()`/`delete()` với id không tồn tại → **không throw exception** (no-op).

---

## 🎯 TODO #4 — StudentRepositoryImpl (10 điểm)

**File:** `src/main/java/traltb/fudn/chapter1_exercise1/repository/StudentRepositoryImpl.java`

### Yêu cầu
Đây là **adapter** mỏng — chỉ delegate xuống `StudentDAO`. Hai constructor đã có sẵn.

### Pattern
| Repository method | Implement |
|---|---|
| `Student save(Student s)` | `studentDAO.save(s); return s;` |
| `Optional<Student> findById(Long id)` | `return Optional.ofNullable(studentDAO.findById(id));` |
| `List<Student> findAll()` | `return studentDAO.findAll();` |
| `Student update(Student s)` | `studentDAO.update(s); return s;` |
| `void deleteById(Long id)` | `studentDAO.delete(id);` |

### Checklist (mỗi method 2 điểm)
- [ ] `save()` — delegate + return student
- [ ] `findById()` — wrap với `Optional.ofNullable()`
- [ ] `findAll()` — delegate
- [ ] `update()` — delegate + return student
- [ ] `deleteById()` — delegate

### Test class: `StudentRepositoryImplTest` (6 method, dùng Mockito mock DAO)

---

## 🎯 TODO #5 — StudentServiceImpl (25 điểm)

**File:** `src/main/java/traltb/fudn/chapter1_exercise1/service/StudentServiceImpl.java`

### Yêu cầu
Business logic + validation. Helper `isBlank()` đã có sẵn.

### Validation rules (CỰC KỲ QUAN TRỌNG — message phải CHÍNH XÁC)

| Điều kiện | Exception + Message |
|---|---|
| `student == null` | `IllegalArgumentException("Student must not be null")` |
| update() và (`id == null` hoặc `id <= 0`) | `IllegalArgumentException("Student id is required for update")` |
| `email` blank/null | `IllegalArgumentException("Email must not be blank")` |
| `password` blank/null | `IllegalArgumentException("Password must not be blank")` |
| `firstName` blank/null | `IllegalArgumentException("First name must not be blank")` |
| `lastName` blank/null | `IllegalArgumentException("Last name must not be blank")` |
| getById/deleteById với `id == null` hoặc `id <= 0` | `IllegalArgumentException("Id must be greater than 0")` |

### Checklist
- [ ] `create(Student)` — validate(false) + repo.save (4đ)
- [ ] `getById(Long)` — validate id > 0 + repo.findById (4đ)
- [ ] `getAll()` — repo.findAll (3đ)
- [ ] `update(Student)` — validate(true) + repo.update (4đ)
- [ ] `deleteById(Long)` — validate id > 0 + repo.deleteById (4đ)
- [ ] `validateStudent(Student, boolean)` — đủ 6 rule (6đ)

### Test class: `StudentServiceImplTest` (18 method)

---

## 🎯 TODO #6 — Main Demo CRUD (chấm 15đ qua Integration test)

**File:** `src/main/java/traltb/fudn/chapter1_exercise1/Chapter1Exercise1Application.java`

### Yêu cầu
Trong `main()`, viết code demo 5 thao tác CRUD bằng `StudentService`.

### Checklist
- [ ] (6.1) CREATE: tạo student → in `"CREATE OK -> id: <id>"`
- [ ] (6.2) READ ALL: in `"TOTAL STUDENTS: <count>"`
- [ ] (6.3) READ BY ID: in `"FIND BY ID: <student>"`
- [ ] (6.4) UPDATE: đổi firstName + marks → in kết quả
- [ ] (6.5) DELETE: xoá → in `"DELETE OK -> id: <id>"`

### Test class: `CrudIntegrationTest` (1 method end-to-end)

Test này validate **toàn bộ pipeline** Service → Repository → DAO → H2 hoạt động đúng.

> Ghi chú: Phần Main chạy thật trên MSSQL, nhưng autograding chấm qua integration test trên H2.
> Bạn vẫn cần viết code Main đầy đủ — giảng viên sẽ check thủ công khi nghi ngờ.

---

## 📊 Grading Rubric (chi tiết)

### Mỗi test class là "all-or-nothing"
- Pass 100% method trong class → nhận điểm tối đa.
- Fail ≥ 1 method → 0 điểm cho TODO đó.

### Cách autograding hoạt động
1. Sinh viên push code lên GitHub.
2. GitHub Actions trigger workflow `classroom.yml`.
3. Workflow chạy `mvn test -Dtest=<TestClass>` cho từng TODO.
4. Mỗi command thành công (exit code 0) → cộng điểm.
5. Kết quả tổng hợp post lên tab **Commits** và **Pull Requests**.

### Bảng điểm

| # | TODO | Test command | Điểm |
|---|---|---|---|
| 1 | Student Entity | `mvn test -Dtest=StudentEntityTest` | 15 |
| 2 | Book Entity | `mvn test -Dtest=BookEntityTest` | 10 |
| 3 | StudentDAO | `mvn test -Dtest=StudentDAOTest` | 25 |
| 4 | StudentRepositoryImpl | `mvn test -Dtest=StudentRepositoryImplTest` | 10 |
| 5 | StudentServiceImpl | `mvn test -Dtest=StudentServiceImplTest` | 25 |
| 6 | Integration CRUD | `mvn test -Dtest=CrudIntegrationTest` | 15 |
| | **TỔNG** | | **100** |

---

## 🛠️ Workflow đề xuất

```
1. Đọc README + INSTRUCTIONS         (10 phút)
2. Đọc SlideNotes/Chapter_01...       (30 phút)
3. Code TODO #1 (Student)             (15 phút)
   → mvn test -Dtest=StudentEntityTest
4. Code TODO #2 (Book)                (10 phút)
   → mvn test -Dtest=BookEntityTest
5. Code TODO #3 (DAO) — quan trọng nhất (45 phút)
   → mvn test -Dtest=StudentDAOTest
6. Code TODO #4 (Repository)          (10 phút)
   → mvn test -Dtest=StudentRepositoryImplTest
7. Code TODO #5 (Service)             (30 phút)
   → mvn test -Dtest=StudentServiceImplTest
8. Chạy integration test
   → mvn test -Dtest=CrudIntegrationTest
9. Code TODO #6 (Main demo)           (15 phút)
10. mvn clean test (chạy tất cả)
11. git add . && git commit -m "..." && git push
12. Check GitHub Actions → xem điểm
```

**Tổng thời gian dự kiến:** 3-4 giờ.

---

## 🔍 Checklist trước khi nộp

- [ ] Tất cả file có TODO đã được implement (không còn `UnsupportedOperationException`)
- [ ] `./mvnw clean test` local pass tất cả test
- [ ] Code có comment giải thích logic phức tạp (nếu có)
- [ ] KHÔNG sửa file trong `src/test/`
- [ ] KHÔNG sửa persistence-unit `hsf302-chapter1-test` trong `persistence.xml`
- [ ] KHÔNG sửa `pom.xml` (trừ khi GV yêu cầu)
- [ ] KHÔNG sửa file `.github/`
- [ ] Đã push commit cuối cùng trước deadline
- [ ] Đã verify điểm trên tab **Actions** của repo

---

## 💡 Tips để đạt điểm cao

1. **Đọc test trước khi viết code.** Test cho biết chính xác yêu cầu (input/output, exception message).
2. **Chạy từng test một** khi đang code (nhanh hơn chạy tất cả).
3. **Đừng over-engineer.** Template chỉ yêu cầu code đơn giản, đừng thêm interface/abstract class không cần thiết.
4. **Copy paste error message chính xác** — đặc biệt cho TODO #5 (validation). Sai 1 ký tự là 0 điểm.
5. **Commit thường xuyên** — mỗi TODO 1 commit, tránh mất code.
6. **Test integration cuối cùng** — chỉ pass khi tất cả layer hoạt động đúng.

Chúc bạn làm bài tốt! 🚀
