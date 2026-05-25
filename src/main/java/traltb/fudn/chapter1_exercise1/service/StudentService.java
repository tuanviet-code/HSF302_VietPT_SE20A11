package traltb.fudn.chapter1_exercise1.service;

import traltb.fudn.chapter1_exercise1.pojo.Student;

import java.util.List;
import java.util.Optional;

/**
 * Service layer — chứa BUSINESS LOGIC.
 *
 * Interface này ĐÃ HOÀN CHỈNH — KHÔNG sửa.
 * Sinh viên chỉ cần implement {@link StudentServiceImpl}.
 *
 * Trách nhiệm của Service:
 *  - Validate input (kiểm tra null, blank, format...).
 *  - Áp business rule (transform, calculate...).
 *  - Phối hợp nhiều Repository nếu cần.
 *  - Trong Spring app: quản lý transaction (@Transactional).
 */
public interface StudentService {

    /**
     * Tạo student mới.
     * @throws IllegalArgumentException nếu student không hợp lệ.
     */
    Student create(Student student);

    /**
     * Lấy student theo id.
     * @throws IllegalArgumentException nếu id null hoặc <= 0.
     */
    Optional<Student> getById(Long id);

    /**
     * Lấy tất cả student.
     */
    List<Student> getAll();

    /**
     * Cập nhật student. Bắt buộc student.id phải có sẵn.
     * @throws IllegalArgumentException nếu student/id không hợp lệ.
     */
    Student update(Student student);

    /**
     * Xoá student theo id.
     * @throws IllegalArgumentException nếu id null hoặc <= 0.
     */
    void deleteById(Long id);
}
