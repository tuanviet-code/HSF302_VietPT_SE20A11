package traltb.fudn.chapter1_exercise1.repository;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import traltb.fudn.chapter1_exercise1.dao.StudentDAO;
import traltb.fudn.chapter1_exercise1.pojo.Student;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Test cho StudentRepositoryImpl (TODO #4) — dùng Mockito để mock StudentDAO.
 * Không cần DB thật.
 */
@DisplayName("TODO #4: StudentRepositoryImpl (mock DAO)")
class StudentRepositoryImplTest {

    @Mock private StudentDAO dao;
    @InjectMocks private StudentRepositoryImpl repo;
    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        repo = new StudentRepositoryImpl(dao);
    }

    @AfterEach
    void tearDown() throws Exception { mocks.close(); }

    @Test
    @DisplayName("TODO #4.1 save() phải gọi dao.save() và return chính student")
    void save_shouldCallDaoAndReturn() {
        Student s = new Student();
        s.setEmail("a@b.com");

        Student result = repo.save(s);

        verify(dao, times(1)).save(s);
        assertThat(result).isSameAs(s);
    }

    @Test
    @DisplayName("TODO #4.2 findById() phải wrap kết quả bằng Optional")
    void findById_shouldWrapInOptional() {
        Student s = new Student();
        s.setId(1L);
        when(dao.findById(1L)).thenReturn(s);

        Optional<Student> result = repo.findById(1L);

        assertThat(result).isPresent().contains(s);
    }

    @Test
    @DisplayName("TODO #4.2 findById() return null từ DAO → Optional empty")
    void findById_nullFromDao_isEmptyOptional() {
        when(dao.findById(99L)).thenReturn(null);
        Optional<Student> result = repo.findById(99L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("TODO #4.3 findAll() phải gọi dao.findAll()")
    void findAll_shouldDelegate() {
        when(dao.findAll()).thenReturn(List.of(new Student(), new Student()));
        List<Student> all = repo.findAll();
        assertThat(all).hasSize(2);
        verify(dao).findAll();
    }

    @Test
    @DisplayName("TODO #4.4 update() phải gọi dao.update() và return chính student")
    void update_shouldCallDaoAndReturn() {
        Student s = new Student();
        s.setId(1L);

        Student result = repo.update(s);

        verify(dao).update(s);
        assertThat(result).isSameAs(s);
    }

    @Test
    @DisplayName("TODO #4.5 deleteById() phải gọi dao.delete()")
    void deleteById_shouldDelegate() {
        repo.deleteById(42L);
        verify(dao).delete(42L);
    }
}
