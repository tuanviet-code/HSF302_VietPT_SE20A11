package traltb.fudn.chapter1_exercise1.repository;

import traltb.fudn.chapter1_exercise1.dao.StudentDAO;
import traltb.fudn.chapter1_exercise1.pojo.Student;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 *  TODO #4: STUDENT REPOSITORY IMPL  (10 điểm)
 * ============================================================================
 *
 *  YÊU CẦU:
 *  Implement StudentRepository bằng cách delegate các thao tác xuống StudentDAO.
 *
 *  Lớp này KHÔNG có logic phức tạp — chỉ "wrap" StudentDAO và adapt
 *  return type (DAO trả về null/void → Repository trả về Optional/Student).
 *
 *  CẤU TRÚC ĐÃ CÓ SẴN (không sửa):
 *    - Field `studentDAO`
 *    - 2 constructor: mặc định + nhận StudentDAO (cho test inject mock)
 *
 *  CHECKLIST:
 *    [ ] save(Student)     - gọi DAO.save(), return student
 *    [ ] findById(Long)    - gọi DAO.findById(), wrap = Optional.ofNullable()
 *    [ ] findAll()         - gọi DAO.findAll()
 *    [ ] update(Student)   - gọi DAO.update(), return student
 *    [ ] deleteById(Long)  - gọi DAO.delete()
 *
 *  THAM KHẢO: SlideNotes/Chapter_01_JPA_Mapping.md (mục 6 - Repository Pattern)
 * ============================================================================
 */
public class StudentRepositoryImpl implements StudentRepository {

    private final StudentDAO studentDAO;

    /**
     * Constructor mặc định: tạo StudentDAO mới với MSSQL config.
     */
    public StudentRepositoryImpl() {
        this.studentDAO = new StudentDAO();
    }

    /**
     * Constructor inject StudentDAO.
     * CHÚ Ý: dùng cho UNIT TEST với mock StudentDAO — KHÔNG xoá.
     */
    public StudentRepositoryImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public Student save(Student student) {
        studentDAO.save(student);
        return student;
        //throw new UnsupportedOperationException("TODO #4.1: implement save()");
    }

    @Override
    public Optional<Student> findById(Long id) {
        // TODO #4.2 (2 điểm): gọi studentDAO.findById(id), wrap kết quả bằng Optional.ofNullable()
        return Optional.ofNullable(studentDAO.findById(id));
        //throw new UnsupportedOperationException("TODO #4.2: implement findById()");
    }

    @Override
    public List<Student> findAll() {
        // TODO #4.3 (2 điểm): return studentDAO.findAll()
        return studentDAO.findAll();
        //throw new UnsupportedOperationException("TODO #4.3: implement findAll()");
    }

    @Override
    public Student update(Student student) {
        // TODO #4.4 (2 điểm): gọi studentDAO.update(student), sau đó return student
        studentDAO.update(student);
        return student;
        //throw new UnsupportedOperationException("TODO #4.4: implement update()");
    }

    @Override
    public void deleteById(Long id) {
        // TODO #4.5 (2 điểm): gọi studentDAO.delete(id)
        studentDAO.delete(id);
        //throw new UnsupportedOperationException("TODO #4.5: implement deleteById()");
    }
}
