package traltb.fudn.chapter1_exercise1.integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import traltb.fudn.chapter1_exercise1.dao.StudentDAO;
import traltb.fudn.chapter1_exercise1.pojo.Student;
import traltb.fudn.chapter1_exercise1.repository.StudentRepository;
import traltb.fudn.chapter1_exercise1.repository.StudentRepositoryImpl;
import traltb.fudn.chapter1_exercise1.service.StudentService;
import traltb.fudn.chapter1_exercise1.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test end-to-end: Service → Repository → DAO → H2 database.
 *
 * Test này chạy được khi TẤT CẢ TODO #1-5 đã hoàn thành đúng.
 * Dùng H2 in-memory (không cần MSSQL).
 */
@DisplayName("Integration: Full CRUD flow (Service → Repo → DAO → H2)")
class CrudIntegrationTest {

    private static EntityManagerFactory emf;
    private StudentService service;

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
        // Clean DB
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Book").executeUpdate();
            em.createQuery("DELETE FROM Student").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        // Build full stack: DAO ← Repository ← Service (constructor injection)
        StudentDAO dao = new StudentDAO(emf);
        StudentRepository repo = new StudentRepositoryImpl(dao);
        service = new StudentServiceImpl(repo);
    }

    @Test
    @DisplayName("Full CRUD flow end-to-end")
    void fullCrudFlow() {
        // 1) CREATE
        Student s = new Student();
        s.setEmail("integration@test.com");
        s.setPassword("secret");
        s.setFirstName("Inte");
        s.setLastName("Gration");
        s.setMarks(75);

        Student created = service.create(s);
        assertThat(created.getId()).isPositive();
        Long id = created.getId();

        // 2) READ ALL
        List<Student> all = service.getAll();
        assertThat(all).hasSize(1);

        // 3) READ BY ID
        Optional<Student> found = service.getById(id);
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("integration@test.com");

        // 4) UPDATE
        created.setFirstName("UPDATED");
        created.setMarks(95);
        service.update(created);

        Optional<Student> updated = service.getById(id);
        assertThat(updated).isPresent();
        assertThat(updated.get().getFirstName()).isEqualTo("UPDATED");
        assertThat(updated.get().getMarks()).isEqualTo(95);

        // 5) DELETE
        service.deleteById(id);
        assertThat(service.getById(id)).isEmpty();
        assertThat(service.getAll()).isEmpty();
    }
}
