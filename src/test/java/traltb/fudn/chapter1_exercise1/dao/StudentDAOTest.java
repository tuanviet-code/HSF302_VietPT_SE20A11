package traltb.fudn.chapter1_exercise1.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import traltb.fudn.chapter1_exercise1.pojo.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Test cho StudentDAO (TODO #3) — dùng H2 in-memory.
 * Persistence unit "hsf302-chapter1-test" được khai báo trong persistence.xml.
 */
@DisplayName("TODO #3: StudentDAO (H2 in-memory)")
class StudentDAOTest {

    private static EntityManagerFactory emf;
    private StudentDAO dao;

    @BeforeAll
    static void initEmf() {
        emf = Persistence.createEntityManagerFactory("hsf302-chapter1-test");
    }

    @AfterAll
    static void closeEmf() {
        if (emf != null && emf.isOpen()) emf.close();
    }

    @BeforeEach
    void setUp() {
        // Clean database trước mỗi test (đảm bảo isolation)
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Book").executeUpdate();
            em.createQuery("DELETE FROM Student").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        dao = new StudentDAO(emf);
    }

    private Student makeStudent(String email) {
        Student s = new Student();
        s.setEmail(email);
        s.setPassword("123456");
        s.setFirstName("First");
        s.setLastName("Last");
        s.setMarks(80);
        return s;
    }

    // -------- save --------
    @Test
    @DisplayName("TODO #3.1 save(): persist student → id được sinh ra")
    void save_shouldPersistStudent() {
        Student s = makeStudent("a@b.com");
        dao.save(s);
        assertThat(s.getId()).as("id phải != null sau khi save").isNotNull();
        assertThat(s.getId()).isPositive();
    }

    @Test
    @DisplayName("TODO #3.1 save(): nhiều student → có ID khác nhau")
    void save_multipleStudents() {
        Student a = makeStudent("a@b.com");
        Student b = makeStudent("c@d.com");
        dao.save(a);
        dao.save(b);
        assertThat(a.getId()).isNotEqualTo(b.getId());
    }

    // -------- findById --------
    @Test
    @DisplayName("TODO #3.2 findById(): tìm thấy student đã lưu")
    void findById_shouldReturnSavedStudent() {
        Student s = makeStudent("x@y.com");
        dao.save(s);
        Student found = dao.findById(s.getId());
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("x@y.com");
    }

    @Test
    @DisplayName("TODO #3.2 findById(): id không tồn tại → null")
    void findById_shouldReturnNullForNonExistentId() {
        Student found = dao.findById(99999L);
        assertThat(found).isNull();
    }

    // -------- findAll --------
    @Test
    @DisplayName("TODO #3.3 findAll(): DB rỗng → list rỗng")
    void findAll_emptyDb() {
        List<Student> all = dao.findAll();
        assertThat(all).isEmpty();
    }

    @Test
    @DisplayName("TODO #3.3 findAll(): trả về tất cả student")
    void findAll_returnsAll() {
        dao.save(makeStudent("a@a.com"));
        dao.save(makeStudent("b@b.com"));
        dao.save(makeStudent("c@c.com"));
        List<Student> all = dao.findAll();
        assertThat(all).hasSize(3);
    }

    // -------- update --------
    @Test
    @DisplayName("TODO #3.4 update(): các field được cập nhật trong DB")
    void update_shouldChangeFields() {
        Student s = makeStudent("u@u.com");
        dao.save(s);

        s.setFirstName("CHANGED");
        s.setMarks(99);
        dao.update(s);

        Student reloaded = dao.findById(s.getId());
        assertThat(reloaded.getFirstName()).isEqualTo("CHANGED");
        assertThat(reloaded.getMarks()).isEqualTo(99);
    }

    @Test
    @DisplayName("TODO #3.4 update(): id không tồn tại → no-op (không exception)")
    void update_nonExistentId_isNoOp() {
        Student ghost = makeStudent("ghost@x.com");
        ghost.setId(99999L);
        // Không throw
        assertThatCode(() -> dao.update(ghost)).doesNotThrowAnyException();
    }

    // -------- delete --------
    @Test
    @DisplayName("TODO #3.5 delete(): xoá student tồn tại")
    void delete_shouldRemoveStudent() {
        Student s = makeStudent("d@d.com");
        dao.save(s);
        Long id = s.getId();

        dao.delete(id);

        assertThat(dao.findById(id)).isNull();
    }

    @Test
    @DisplayName("TODO #3.5 delete(): id không tồn tại → no-op")
    void delete_nonExistentId_isNoOp() {
        assertThatCode(() -> dao.delete(99999L)).doesNotThrowAnyException();
    }
}
