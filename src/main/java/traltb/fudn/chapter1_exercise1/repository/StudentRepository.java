package traltb.fudn.chapter1_exercise1.repository;

import traltb.fudn.chapter1_exercise1.pojo.Student;

import java.util.List;
import java.util.Optional;

/**
 * Repository Pattern — định nghĩa "contract" cho việc truy cập Student data.
 *
 * Interface này ĐÃ HOÀN CHỈNH — KHÔNG sửa.
 * Sinh viên chỉ cần implement {@link StudentRepositoryImpl}.
 *
 * Repository khác DAO ở chỗ:
 *  - DAO: gắn chặt với JPA/Hibernate (EntityManager).
 *  - Repository: abstraction cao hơn, có thể đổi sang JDBC/JOOQ/MyBatis
 *    mà service layer không bị ảnh hưởng.
 *  - Repository thường return {@link Optional} thay vì null.
 */
public interface StudentRepository {

    /**
     * Lưu student mới.
     * @return entity đã được persist (id đã có sẵn).
     */
    Student save(Student student);

    /**
     * Tìm student theo id.
     * @return Optional rỗng nếu không tìm thấy.
     */
    Optional<Student> findById(Long id);

    /**
     * Trả về danh sách tất cả student.
     */
    List<Student> findAll();

    /**
     * Cập nhật thông tin student.
     */
    Student update(Student student);

    /**
     * Xoá student theo id.
     */
    void deleteById(Long id);
}
