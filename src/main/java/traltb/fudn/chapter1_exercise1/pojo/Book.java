package traltb.fudn.chapter1_exercise1.pojo;

import jakarta.persistence.*;
import lombok.*;

/**
 * ============================================================================
 *  TODO #2: BOOK ENTITY  (10 điểm)
 * ============================================================================
 *
 *  YÊU CẦU:
 *  Hoàn thành Entity Book tương ứng với bảng "books" trong database.
 *
 *  Bảng "books" có các cột sau:
 *  ┌─────────────┬─────────────────┬───────────────────────────────────┐
 *  │ Column      │ Type            │ Constraint                        │
 *  ├─────────────┼─────────────────┼───────────────────────────────────┤
 *  │ id          │ BIGINT          │ PRIMARY KEY, AUTO_GENERATED       │
 *  │ title       │ VARCHAR         │ NOT NULL                          │
 *  │ author      │ VARCHAR         │ NOT NULL                          │
 *  │ isbn        │ VARCHAR         │ NOT NULL, UNIQUE                  │
 *  │ student_id  │ BIGINT          │ FOREIGN KEY -> students.id        │
 *  └─────────────┴─────────────────┴───────────────────────────────────┘
 *
 *  Một Book thuộc về MỘT Student (quan hệ N-1 với Student).
 *  Sử dụng @ManyToOne với @JoinColumn(name = "student_id").
 *
 *  CHECKLIST:
 *    [ ] Đánh dấu class là @Entity
 *    [ ] Map class với bảng "books" qua @Table
 *    [ ] Khai báo các field: id, title, author, isbn
 *    [ ] Đánh dấu id là @Id + @GeneratedValue(strategy = GenerationType.AUTO)
 *    [ ] Đánh dấu các @Column với name, nullable, unique phù hợp
 *    [ ] Khai báo Student student với @ManyToOne + @JoinColumn(name = "student_id")
 *    [ ] Generate constructor (no-arg + all-arg) và getter/setter
 *        (gợi ý: dùng Lombok @Getter @Setter @NoArgsConstructor @AllArgsConstructor)
 *
 *  THAM KHẢO: SlideNotes/Chapter_01_JPA_Mapping.md (mục 5 - Relationship)
 * ============================================================================
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false)
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
