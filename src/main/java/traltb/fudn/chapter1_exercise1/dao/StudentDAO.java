package traltb.fudn.chapter1_exercise1.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import traltb.fudn.chapter1_exercise1.pojo.Student;

import java.util.List;

/**
 * ============================================================================
 *  TODO #3: STUDENT DAO  (25 điểm)
 * ============================================================================
 *
 *  YÊU CẦU:
 *  Implement Data Access Object (DAO) cho Student dùng JPA EntityManager.
 *
 *  Lớp này CHỊU TRÁCH NHIỆM:
 *    - Mở/đóng EntityManager đúng cách (try-finally).
 *    - Quản lý transaction (begin / commit / rollback).
 *    - Thực hiện 5 thao tác CRUD: save, findById, findAll, update, delete.
 *
 *  CẤU TRÚC ĐÃ CÓ SẴN (không sửa):
 *    - 2 constructor: mặc định dùng prod, có constructor nhận EntityManagerFactory
 *      (dùng cho test với H2 — bắt buộc giữ nguyên).
 *    - Hằng số PERSISTENCE_UNIT_NAME.
 *
 *  CHECKLIST:
 *    [ ] save(Student)     - persist entity, commit transaction
 *    [ ] findById(Long)    - tìm theo id, return null nếu không thấy
 *    [ ] findAll()         - JPQL "SELECT s FROM Student s"
 *    [ ] update(Student)   - find + update các field + commit
 *    [ ] delete(Long)      - find + remove + commit
 *
 *  LƯU Ý QUAN TRỌNG:
 *    - Mỗi method PHẢI mở EntityManager mới (em = emf.createEntityManager())
 *    - PHẢI đóng EntityManager trong finally
 *    - Khi có exception trong transaction → PHẢI rollback
 *
 *  THAM KHẢO: SlideNotes/Chapter_01_JPA_Mapping.md (mục 6 - Demo CRUD)
 * ============================================================================
 */
public class StudentDAO {

    /**
     * Tên persistence unit cho production (MSSQL).
     * Đã định nghĩa trong src/main/resources/META-INF/persistence.xml.
     */
    public static final String PERSISTENCE_UNIT_NAME = "hsf302-chapter1";

    private final EntityManagerFactory emf;

    /**
     * Constructor mặc định: dùng MSSQL (cho main class & production).
     */
    public StudentDAO() {
        this(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
    }

    /**
     * Constructor inject EntityManagerFactory.
     * CHÚ Ý: constructor này dùng cho UNIT TEST với H2 — KHÔNG được xoá.
     */
    public StudentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Đóng EntityManagerFactory (dùng khi shutdown app).
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    // ==================== CREATE ====================

    /**
     * Lưu student mới vào DB.
     * Sau khi save thành công, student.id sẽ được DB sinh ra tự động.
     */
    public void save(Student student) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        throw new UnsupportedOperationException("TODO #3.1: implement save()");
    }

    // ==================== READ ====================

    /**
     * Tìm student theo id. Trả về null nếu không tìm thấy.
     * (Không cần transaction cho read-only operation.)
     */
    public Student findById(Long id) {
            EntityManager em = emf.createEntityManager();
            try {
                return em.find(Student.class, id);
            } finally {
                em.close();

        throw new UnsupportedOperationException("TODO #3.2: implement findById()");}
    }

    /**
     * Trả về tất cả student trong DB.
     * Dùng JPQL: "SELECT s FROM Student s"
     */
    public List<Student> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT s FROM Student s", Student.class)
                    .getResultList();
        } finally {
            em.close();
        throw new UnsupportedOperationException("TODO #3.3: implement findAll()");}
    }

    // ==================== UPDATE ====================

    /**
     * Cập nhật thông tin student.
     * Tìm student theo student.id trong DB, copy các field từ tham số vào managed entity,
     * sau đó commit để Hibernate flush update statement.
     */
    public void update(Student student) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student existingStudent = em.find(Student.class, student.getId());
            if (existingStudent != null) {
                existingStudent.setEmail(student.getEmail());
                existingStudent.setPassword(student.getPassword());
                existingStudent.setFirstName(student.getFirstName());
                existingStudent.setLastName(student.getLastName());
                existingStudent.setMarks(student.getMarks());
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        throw new UnsupportedOperationException("TODO #3.4: implement update()");
    }

    // ==================== DELETE ====================

    /**
     * Xoá student theo id. Nếu id không tồn tại, không làm gì (no-op).
     */
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        throw new UnsupportedOperationException("TODO #3.5: implement delete()");
    }
}
