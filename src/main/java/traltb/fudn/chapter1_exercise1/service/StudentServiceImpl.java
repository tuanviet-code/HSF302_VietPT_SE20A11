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
        // TODO #5.1 (4 điểm):
        //   1. Gọi validateStudent(student, false) để validate input
        //   2. Return studentRepository.save(student)
        validateStudent(student, false);
        return studentRepository.save(student);
        //throw new UnsupportedOperationException("TODO #5.1: implement create()");
    }

    @Override
    public Optional<Student> getById(Long id) {
        // TODO #5.2 (4 điểm):
        //   1. Nếu id null HOẶC id <= 0 → throw IllegalArgumentException("Id must be greater than 0")
        //   2. Return studentRepository.findById(id)
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        return studentRepository.findById(id);
        //throw new UnsupportedOperationException("TODO #5.2: implement getById()");
    }

    @Override
    public List<Student> getAll() {
        // TODO #5.3 (3 điểm): Return studentRepository.findAll()
        return studentRepository.findAll();
        //throw new UnsupportedOperationException("TODO #5.3: implement getAll()");
    }

    @Override
    public Student update(Student student) {
        // TODO #5.4 (4 điểm):
        //   1. Gọi validateStudent(student, true) để validate input
        //   2. Return studentRepository.update(student)
        validateStudent(student, true);
        return studentRepository.update(student);
        //throw new UnsupportedOperationException("TODO #5.4: implement update()");
    }

    @Override
    public void deleteById(Long id) {
        // TODO #5.5 (4 điểm):
        //   1. Nếu id null HOẶC id <= 0 → throw IllegalArgumentException("Id must be greater than 0")
        //   2. Gọi studentRepository.deleteById(id)
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        studentRepository.deleteById(id);
        //throw new UnsupportedOperationException("TODO #5.5: implement deleteById()");
    }

    /**
     * Validate Student object.
     *
     * @param student    object cần validate
     * @param requireId  nếu true → bắt buộc student.id không null và > 0 (dùng cho update)
     */
    private void validateStudent(Student student, boolean requireId) {
        // TODO #5.6 (6 điểm): Implement validateStudent() theo các rule trong Javadoc class header.
        //   Throw IllegalArgumentException với message CHÍNH XÁC như danh sách trong header.
        //
        //   Pseudo-code:
        //   if (student == null) throw IllegalArgumentException("Student must not be null");
        //   if (requireId && (id == null || id <= 0)) throw IllegalArgumentException("Student id is required for update");
        //   if (isBlank(email))     throw IllegalArgumentException("Email must not be blank");
        //   if (isBlank(password))  throw IllegalArgumentException("Password must not be blank");
        //   if (isBlank(firstName)) throw IllegalArgumentException("First name must not be blank");
        //   if (isBlank(lastName))  throw IllegalArgumentException("Last name must not be blank");
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
        //throw new UnsupportedOperationException("TODO #5.6: implement validateStudent()");
    }

    /**
     * Helper: kiểm tra String null hoặc rỗng (sau khi trim).
     * Method này ĐÃ HOÀN CHỈNH — KHÔNG sửa.
     */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
