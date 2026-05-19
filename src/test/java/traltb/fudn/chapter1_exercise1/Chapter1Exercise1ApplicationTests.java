package traltb.fudn.chapter1_exercise1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Smoke test cho main application class.
 *
 * Test này KHÔNG load Spring context (vì sẽ trigger main() rồi crash do TODO chưa làm).
 * Chỉ kiểm tra Main class tồn tại + có method main().
 */
@DisplayName("Smoke test: Application class")
class Chapter1Exercise1ApplicationTests {

    @Test
    @DisplayName("Class Chapter1Exercise1Application phải tồn tại")
    void applicationClassExists() {
        assertThat(Chapter1Exercise1Application.class).isNotNull();
    }

    @Test
    @DisplayName("Class phải có method main(String[])")
    void shouldHaveMainMethod() throws NoSuchMethodException {
        assertThat(Chapter1Exercise1Application.class.getDeclaredMethod("main", String[].class))
                .isNotNull();
    }
}
