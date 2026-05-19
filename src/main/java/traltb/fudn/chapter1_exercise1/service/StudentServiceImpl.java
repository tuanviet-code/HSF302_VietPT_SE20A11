package traltb.fudn.chapter1_exercise1.service;

import traltb.fudn.chapter1_exercise1.pojo.Student;
import traltb.fudn.chapter1_exercise1.repository.StudentRepository;
import traltb.fudn.chapter1_exercise1.repository.StudentRepositoryImpl;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 *  TODO #5: STUDENT SERVICE IMPL  (25 điểm)
 * ============================================================================
 *
 *  YÊU CẦU:
 *  Implement business logic + validation cho Student.
 *
 *  CẤU TRÚC ĐÃ CÓ SẴN (không sửa):
 *    - Field `studentRepository`
 *    - 2 constructor: mặc định + nhận StudentRepository (cho test inject mock)
 *    - 2 helper method: isBlank(String) — ĐÃ HOÀN CHỈNH
 *
 *  CHECKLIST:
 *    [ ] create(Student)         - validate (requireId=false) + repo.save
 *    [ ] getById(Long)           - validate id > 0 + repo.findById
 *    [ ] getAll()                - return repo.findAll
 *    [ ] update(Student)         - validate (requireId=true) + repo.update
 *    [ ] deleteById(Long)        - validate id > 0 + repo.deleteById
 *    [ ] validateStudent(...)    - validation logic chi tiết bên dưới
 *
 *  CÁC RULE VALIDATION:
 *    - student == null              → throw IllegalArgumentException("Student must not be null")
 *    - requireId && id null/<=0     → throw IllegalArgumentException("Student id is required for update")
 *    - email blank/null             → throw IllegalArgumentException("Email must not be blank")
 *    - password blank/null          → throw IllegalArgumentException("Password must not be blank")
 *    - firstName blank/null         → throw IllegalArgumentException("First name must not be blank")
 *    - lastName blank/null          → throw IllegalArgumentException("Last name must not be blank")
 *    - id null/<=0 trong getById()/deleteById() → throw IllegalArgumentException("Id must be greater than 0")
 *
 *  THAM KHẢO: SlideNotes/Chapter_01_JPA_Mapping.md (mục 6 - Service Layer)
 * ============================================================================
 */
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    /**
     * Constructor mặc định: tạo StudentRepository mới (dùng MSSQL).
     */
    public StudentServiceImpl() {
        this.studentRepository = new StudentRepositoryImpl();
    }

    /**
     * Constructor inject StudentRepository.
     * CHÚ Ý: dùng cho UNIT TEST với mock Repository — KHÔNG xoá.
     */
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(Student student) {
        validateStudent(student, false);
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student update(Student student) {
        validateStudent(student, true);
        return studentRepository.update(student);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        studentRepository.deleteById(id);
    }

    /**
     * Validate Student object.
     *
     * @param student    object cần validate
     * @param requireId  nếu true → bắt buộc student.id không null và > 0 (dùng cho update)
     */
    private void validateStudent(Student student, boolean requireId) {
        if (student == null) {
            throw new IllegalArgumentException("Student must not be null");
        }

        if (requireId && (student.getId() == null || student.getId() <= 0)) {
            throw new IllegalArgumentException("Student id is required for update");
        }

        if (isBlank(student.getEmail())) {
            throw new IllegalArgumentException("Email must not be blank");
        }
        if (isBlank(student.getPassword())) {
            throw new IllegalArgumentException("Password must not be blank");
        }
        if (isBlank(student.getFirstName())) {
            throw new IllegalArgumentException("First name must not be blank");
        }
        if (isBlank(student.getLastName())) {
            throw new IllegalArgumentException("Last name must not be blank");
        }
    }

    /**
     * Helper: kiểm tra String null hoặc rỗng (sau khi trim).
     * Method này ĐÃ HOÀN CHỈNH — KHÔNG sửa.
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
