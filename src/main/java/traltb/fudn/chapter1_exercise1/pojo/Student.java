package traltb.fudn.chapter1_exercise1.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.awt.print.Book;
import java.util.HashSet;
import java.util.Set;

/**
 * ============================================================================
 *  TODO #1: STUDENT ENTITY  (15 điểm)
 * ============================================================================
 *
 *  YÊU CẦU:
 *  Hoàn thành Entity Student tương ứng với bảng "students" trong database.
 *
 *  Bảng "students" có các cột sau:
 *  ┌─────────────┬─────────────────┬───────────────────────────────────┐
 *  │ Column      │ Type            │ Constraint                        │
 *  ├─────────────┼─────────────────┼───────────────────────────────────┤
 *  │ id          │ BIGINT          │ PRIMARY KEY, AUTO_GENERATED       │
 *  │ email       │ VARCHAR         │ NOT NULL, UNIQUE                  │
 *  │ password    │ VARCHAR         │ NOT NULL                          │
 *  │ first_name  │ VARCHAR         │ NOT NULL                          │
 *  │ last_name   │ VARCHAR         │ NOT NULL                          │
 *  │ marks       │ INT             │ (nullable OK)                     │
 *  └─────────────┴─────────────────┴───────────────────────────────────┘
 *
 *  Một Student có thể mượn NHIỀU Book (quan hệ 1-N với Book).
 *  Sử dụng @OneToMany với cascade=ALL và orphanRemoval=true.
 *  Foreign key column trong bảng books là "student_id".
 *
 *  CHECKLIST:
 *    [ ] Đánh dấu class là @Entity
 *    [ ] Map class với bảng "students" qua @Table
 *    [ ] Khai báo các field: id, email, password, firstName, lastName, marks
 *    [ ] Đánh dấu id là @Id + @GeneratedValue(strategy = GenerationType.AUTO)
 *    [ ] Đánh dấu các @Column với name, nullable, unique phù hợp
 *    [ ] Khai báo Set<Book> books với @OneToMany + @JoinColumn
 *    [ ] Generate constructor (no-arg + all-arg) và getter/setter
 *        (gợi ý: dùng Lombok @Getter @Setter @NoArgsConstructor @AllArgsConstructor)
 *
 *  GỢI Ý: Đã có sẵn các import cần thiết ở đầu file.
 *
 *  THAM KHẢO: SlideNotes/Chapter_01_JPA_Mapping.md (mục 3-5)
 * ============================================================================
 */
// TODO: Thêm các Lombok annotation: @Getter @Setter @NoArgsConstructor @AllArgsConstructor
// TODO: Thêm @Entity và @Table(name = "students")
    @Entity
    @Table(name="students")
public class Student {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Getter
    @Setter
    @Column(name = "marks")
    private int marks;

    @Setter
    @Getter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    private Set<Book> books;
    public Student() {
        this.books = new HashSet<>();
    }
}
