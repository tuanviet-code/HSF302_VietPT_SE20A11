package traltb.fudn.chapter1_exercise1.service;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import traltb.fudn.chapter1_exercise1.pojo.Student;
import traltb.fudn.chapter1_exercise1.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test cho StudentServiceImpl (TODO #5) — mock StudentRepository.
 */
@DisplayName("TODO #5: StudentServiceImpl (mock Repository)")
class StudentServiceImplTest {

    @Mock private StudentRepository repo;
    private StudentServiceImpl service;
    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        service = new StudentServiceImpl(repo);
    }

    @AfterEach
    void tearDown() throws Exception { mocks.close(); }

    private Student valid() {
        Student s = new Student();
        s.setEmail("a@b.com");
        s.setPassword("123456");
        s.setFirstName("First");
        s.setLastName("Last");
        s.setMarks(80);
        return s;
    }

    // ==================== create() ====================
    @Test
    @DisplayName("TODO #5.1 create(): valid student → gọi repo.save()")
    void create_validStudent_callsRepo() {
        Student s = valid();
        when(repo.save(s)).thenReturn(s);
        Student result = service.create(s);
        verify(repo).save(s);
        assertThat(result).isSameAs(s);
    }

    @Test
    @DisplayName("TODO #5.1 + #5.6 create(): null student → IllegalArgumentException")
    void create_nullStudent_throws() {
        assertThatThrownBy(() -> service.create(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student must not be null");
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("TODO #5.1 + #5.6 create(): blank email → IllegalArgumentException")
    void create_blankEmail_throws() {
        Student s = valid();
        s.setEmail("");
        assertThatThrownBy(() -> service.create(s))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email must not be blank");
    }

    @Test
    @DisplayName("TODO #5.1 + #5.6 create(): null password → IllegalArgumentException")
    void create_nullPassword_throws() {
        Student s = valid();
        s.setPassword(null);
        assertThatThrownBy(() -> service.create(s))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password must not be blank");
    }

    @Test
    @DisplayName("TODO #5.1 + #5.6 create(): blank firstName → IllegalArgumentException")
    void create_blankFirstName_throws() {
        Student s = valid();
        s.setFirstName("   ");
        assertThatThrownBy(() -> service.create(s))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("First name must not be blank");
    }

    @Test
    @DisplayName("TODO #5.1 + #5.6 create(): blank lastName → IllegalArgumentException")
    void create_blankLastName_throws() {
        Student s = valid();
        s.setLastName(null);
        assertThatThrownBy(() -> service.create(s))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Last name must not be blank");
    }

    // ==================== getById() ====================
    @Test
    @DisplayName("TODO #5.2 getById(): id hợp lệ → gọi repo.findById()")
    void getById_validId_callsRepo() {
        Student s = valid();
        s.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(s));
        Optional<Student> result = service.getById(1L);
        assertThat(result).contains(s);
    }

    @Test
    @DisplayName("TODO #5.2 getById(): id null → IllegalArgumentException")
    void getById_nullId_throws() {
        assertThatThrownBy(() -> service.getById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id must be greater than 0");
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("TODO #5.2 getById(): id 0 hoặc âm → IllegalArgumentException")
    void getById_zeroOrNegativeId_throws() {
        assertThatThrownBy(() -> service.getById(0L)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.getById(-1L)).isInstanceOf(IllegalArgumentException.class);
    }

    // ==================== getAll() ====================
    @Test
    @DisplayName("TODO #5.3 getAll(): trả về list từ repo")
    void getAll_returnsRepoList() {
        when(repo.findAll()).thenReturn(List.of(valid(), valid()));
        List<Student> all = service.getAll();
        assertThat(all).hasSize(2);
    }

    // ==================== update() ====================
    @Test
    @DisplayName("TODO #5.4 update(): id hợp lệ + valid student → gọi repo.update()")
    void update_validStudent_callsRepo() {
        Student s = valid();
        s.setId(1L);
        when(repo.update(s)).thenReturn(s);
        Student result = service.update(s);
        verify(repo).update(s);
        assertThat(result).isSameAs(s);
    }

    @Test
    @DisplayName("TODO #5.4 + #5.6 update(): student null → IllegalArgumentException")
    void update_nullStudent_throws() {
        assertThatThrownBy(() -> service.update(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student must not be null");
    }

    @Test
    @DisplayName("TODO #5.4 + #5.6 update(): id null → IllegalArgumentException")
    void update_nullId_throws() {
        Student s = valid();
        s.setId(null);
        assertThatThrownBy(() -> service.update(s))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student id is required for update");
    }

    @Test
    @DisplayName("TODO #5.4 + #5.6 update(): id <= 0 → IllegalArgumentException")
    void update_nonPositiveId_throws() {
        Student s = valid();
        s.setId(0L);
        assertThatThrownBy(() -> service.update(s)).isInstanceOf(IllegalArgumentException.class);
    }

    // ==================== deleteById() ====================
    @Test
    @DisplayName("TODO #5.5 deleteById(): id hợp lệ → gọi repo.deleteById()")
    void deleteById_validId_callsRepo() {
        service.deleteById(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    @DisplayName("TODO #5.5 deleteById(): id null → IllegalArgumentException")
    void deleteById_nullId_throws() {
        assertThatThrownBy(() -> service.deleteById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id must be greater than 0");
        verifyNoInteractions(repo);
    }

    @Test
    @DisplayName("TODO #5.5 deleteById(): id 0 hoặc âm → IllegalArgumentException")
    void deleteById_zeroOrNegative_throws() {
        assertThatThrownBy(() -> service.deleteById(0L)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.deleteById(-99L)).isInstanceOf(IllegalArgumentException.class);
    }
}
