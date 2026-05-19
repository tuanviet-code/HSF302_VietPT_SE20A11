package traltb.fudn.chapter1_exercise1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import traltb.fudn.chapter1_exercise1.pojo.Student;
import traltb.fudn.chapter1_exercise1.service.StudentService;
import traltb.fudn.chapter1_exercise1.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 *  TODO #6: MAIN APP - CRUD DEMO  (15 điểm)
 * ============================================================================
 *
 *  YÊU CẦU:
 *  Trong main(), viết code demo đủ 5 thao tác CRUD bằng StudentService.
 *
 *  ⚠️ ĐIỀU KIỆN:
 *    - SQL Server đã chạy ở localhost:1433
 *    - Đã tạo database "HSF301_Chapter1_Ex1"
 *    - Đã cập nhật user/password trong META-INF/persistence.xml
 *
 *  CHECKLIST:
 *    [ ] (6.1) CREATE: tạo 1 Student mới (đủ email/password/firstName/lastName/marks),
 *              gọi service.create(), in ra "CREATE OK -> id: <id>"
 *    [ ] (6.2) READ ALL: gọi service.getAll(), in "TOTAL STUDENTS: <count>"
 *    [ ] (6.3) READ BY ID: gọi service.getById(<id>), in "FIND BY ID: <student>"
 *    [ ] (6.4) UPDATE: đổi marks/firstName, gọi service.update(), in kết quả
 *    [ ] (6.5) DELETE: gọi service.deleteById(<id>), in "DELETE OK -> id: <id>"
 *
 *  GHI CHÚ:
 *    - Annotation @SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
 *      ĐÃ ĐƯỢC GIỮ NGUYÊN vì chúng ta dùng JPA RESOURCE_LOCAL (qua persistence.xml)
 *      thay vì Spring Data JPA — không cần Spring tự config DataSource.
 *    - SpringApplication.run() khởi tạo Spring context (cho tương lai mở rộng).
 *      CRUD demo dùng StudentService trực tiếp (không qua Spring bean).
 * ============================================================================
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Chapter1Exercise1Application {

    public static void main(String[] args) {

        SpringApplication.run(Chapter1Exercise1Application.class, args);

        StudentService studentService = new StudentServiceImpl();

        // ================== 1) CREATE ==================
        // TODO #6.1 (3 điểm): Tạo student mới và lưu vào DB
        //   - Tạo Student object với đủ field (email, password, firstName, lastName, marks)
        //   - Gọi studentService.create(student)
        //   - In ra: "CREATE OK -> id: " + created.getId()
        Student s = new Student();
        s.setEmail("vietpt@fe.edu.vn");
        s.setPassword("123");
        s.setFirstName("Viet");
        s.setLastName("PT");
        s.setMarks(85);

        Student created = studentService.create(s);
        Long createdId = s.getId();
        System.out.println("CREATE OK -> id: " + createdId);



        // ================== 2) READ ALL ==================
        // TODO #6.2 (3 điểm): Liệt kê tất cả student
        //   - Gọi studentService.getAll()
        //   - In ra: "TOTAL STUDENTS: " + list.size()
        List<Student> list = studentService.getAll();
        System.out.println("TOTAL STUDENT: " + list.size());


        // ================== 3) READ BY ID ==================
        // TODO #6.3 (3 điểm): Tìm student theo id vừa tạo
        //   - Gọi studentService.getById(createdId)
        //   - In ra: "FIND BY ID: " + optional.orElse(null)
        Optional<Student> optional =  studentService.getById(createdId);
        System.out.println("FIND BY ID: " + optional.orElse(null));


        // ================== 4) UPDATE ==================
        // TODO #6.4 (3 điểm): Cập nhật student vừa tạo
        //   - Đổi firstName và marks
        //   - Gọi studentService.update(student)
        //   - In ra: "UPDATE OK -> firstName: ..., marks: ..."
                s.setFirstName("Tuan");
                s.setMarks(90);

                Student updated = studentService.update(created);
                System.out.println("UPDATE OK -> firstName: Tuan, marks: 90");

        // ================== 5) DELETE ==================
        // TODO #6.5 (3 điểm): Xoá student
        //   - Gọi studentService.deleteById(createdId)
        //   - In ra: "DELETE OK -> id: " + createdId
        studentService.deleteById(createdId);
        System.out.println("DELETE OK -> id: " + createdId);

    }
}
