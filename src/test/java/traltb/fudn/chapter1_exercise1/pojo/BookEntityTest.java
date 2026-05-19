package traltb.fudn.chapter1_exercise1.pojo;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cho Book Entity (TODO #2).
 */
@DisplayName("TODO #2: Book Entity")
class BookEntityTest {

    @Test
    @DisplayName("Book class phải có @Entity")
    void shouldBeAnnotatedWithEntity() {
        assertThat(Book.class.isAnnotationPresent(Entity.class)).isTrue();
    }

    @Test
    @DisplayName("Book class phải có @Table(name = \"books\")")
    void shouldHaveTableAnnotation() {
        Table table = Book.class.getAnnotation(Table.class);
        assertThat(table).isNotNull();
        assertThat(table.name()).isEqualTo("books");
    }

    @Test
    @DisplayName("Field id phải có @Id + @GeneratedValue, type Long")
    void idFieldShouldHaveIdAndGeneratedValue() throws NoSuchFieldException {
        Field f = Book.class.getDeclaredField("id");
        assertThat(f.isAnnotationPresent(Id.class)).isTrue();
        assertThat(f.isAnnotationPresent(GeneratedValue.class)).isTrue();
        assertThat(f.getType()).isEqualTo(Long.class);
    }

    @Test
    @DisplayName("Field title phải String, nullable=false")
    void titleFieldShouldBeNotNull() throws NoSuchFieldException {
        Field f = Book.class.getDeclaredField("title");
        Column c = f.getAnnotation(Column.class);
        assertThat(c).isNotNull();
        assertThat(c.nullable()).isFalse();
    }

    @Test
    @DisplayName("Field author phải String, nullable=false")
    void authorFieldShouldBeNotNull() throws NoSuchFieldException {
        Field f = Book.class.getDeclaredField("author");
        Column c = f.getAnnotation(Column.class);
        assertThat(c).isNotNull();
        assertThat(c.nullable()).isFalse();
    }

    @Test
    @DisplayName("Field isbn phải String, nullable=false, unique=true")
    void isbnFieldShouldBeUniqueAndNotNull() throws NoSuchFieldException {
        Field f = Book.class.getDeclaredField("isbn");
        Column c = f.getAnnotation(Column.class);
        assertThat(c).isNotNull();
        assertThat(c.nullable()).isFalse();
        assertThat(c.unique()).isTrue();
    }

    @Test
    @DisplayName("Field student phải có @ManyToOne + @JoinColumn(name=\"student_id\")")
    void studentFieldShouldHaveManyToOne() throws NoSuchFieldException {
        Field f = Book.class.getDeclaredField("student");
        assertThat(f.getType()).isEqualTo(Student.class);
        ManyToOne mto = f.getAnnotation(ManyToOne.class);
        assertThat(mto).as("Phải có @ManyToOne").isNotNull();
        JoinColumn jc = f.getAnnotation(JoinColumn.class);
        assertThat(jc).as("Phải có @JoinColumn").isNotNull();
        assertThat(jc.name()).isEqualTo("student_id");
    }

    @Test
    @DisplayName("Có constructor no-arg và getter/setter")
    void shouldHaveGettersAndSetters() throws Exception {
        Book b = Book.class.getDeclaredConstructor().newInstance();
        b.getClass().getMethod("setTitle", String.class).invoke(b, "Spring in Action");
        Object t = b.getClass().getMethod("getTitle").invoke(b);
        assertThat(t).isEqualTo("Spring in Action");
    }
}
