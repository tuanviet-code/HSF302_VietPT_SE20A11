package traltb.fudn.chapter1_exercise1.pojo;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cho Student Entity (TODO #1).
 * Kiểm tra annotation, field, getter/setter.
 */
@DisplayName("TODO #1: Student Entity")
class StudentEntityTest {

    @Test
    @DisplayName("Student class phải có annotation @Entity")
    void shouldBeAnnotatedWithEntity() {
        assertThat(Student.class.isAnnotationPresent(Entity.class))
                .as("Student class phải có @Entity annotation")
                .isTrue();
    }

    @Test
    @DisplayName("Student class phải có @Table(name = \"students\")")
    void shouldHaveTableAnnotation() {
        Table table = Student.class.getAnnotation(Table.class);
        assertThat(table).as("Student phải có @Table annotation").isNotNull();
        assertThat(table.name()).as("Table name phải là \"students\"").isEqualTo("students");
    }

    @Test
    @DisplayName("Field id phải có @Id và @GeneratedValue")
    void idFieldShouldHaveIdAndGeneratedValue() throws NoSuchFieldException {
        Field idField = Student.class.getDeclaredField("id");
        assertThat(idField.isAnnotationPresent(Id.class))
                .as("id phải có @Id").isTrue();
        assertThat(idField.isAnnotationPresent(GeneratedValue.class))
                .as("id phải có @GeneratedValue").isTrue();
        assertThat(idField.getType()).as("id phải là Long").isEqualTo(Long.class);
    }

    @Test
    @DisplayName("Field email phải là String, unique và not null")
    void emailFieldShouldBeUniqueAndNotNull() throws NoSuchFieldException {
        Field f = Student.class.getDeclaredField("email");
        assertThat(f.getType()).as("email phải là String").isEqualTo(String.class);
        Column col = f.getAnnotation(Column.class);
        assertThat(col).as("email phải có @Column").isNotNull();
        assertThat(col.unique()).as("email phải unique=true").isTrue();
        assertThat(col.nullable()).as("email phải nullable=false").isFalse();
    }

    @Test
    @DisplayName("Field password phải là String, not null")
    void passwordFieldShouldBeNotNull() throws NoSuchFieldException {
        Field f = Student.class.getDeclaredField("password");
        assertThat(f.getType()).isEqualTo(String.class);
        Column col = f.getAnnotation(Column.class);
        assertThat(col).isNotNull();
        assertThat(col.nullable()).as("password phải nullable=false").isFalse();
    }

    @Test
    @DisplayName("Field firstName/lastName phải là String, not null")
    void firstNameAndLastNameShouldBeNotNull() throws NoSuchFieldException {
        for (String name : new String[]{"firstName", "lastName"}) {
            Field f = Student.class.getDeclaredField(name);
            assertThat(f.getType()).as(name + " phải là String").isEqualTo(String.class);
            Column col = f.getAnnotation(Column.class);
            assertThat(col).as(name + " phải có @Column").isNotNull();
            assertThat(col.nullable()).as(name + " phải nullable=false").isFalse();
        }
    }

    @Test
    @DisplayName("Field marks phải tồn tại")
    void marksFieldShouldExist() throws NoSuchFieldException {
        Field f = Student.class.getDeclaredField("marks");
        assertThat(f.getType()).as("marks phải là int hoặc Integer")
                .isIn(int.class, Integer.class);
    }

    @Test
    @DisplayName("Field books phải có @OneToMany với @JoinColumn")
    void booksFieldShouldHaveOneToMany() throws NoSuchFieldException {
        Field f = Student.class.getDeclaredField("books");
        assertThat(Set.class.isAssignableFrom(f.getType()))
                .as("books phải kiểu Set").isTrue();
        OneToMany otm = f.getAnnotation(OneToMany.class);
        assertThat(otm).as("books phải có @OneToMany").isNotNull();
        JoinColumn jc = f.getAnnotation(JoinColumn.class);
        assertThat(jc).as("books phải có @JoinColumn").isNotNull();
        assertThat(jc.name()).as("@JoinColumn name phải là student_id").isEqualTo("student_id");
    }

    @Test
    @DisplayName("Có constructor no-arg và getter/setter cho id, email")
    void shouldHaveGettersAndSetters() throws Exception {
        Student s = Student.class.getDeclaredConstructor().newInstance();
        s.getClass().getMethod("setEmail", String.class).invoke(s, "a@b.com");
        Object email = s.getClass().getMethod("getEmail").invoke(s);
        assertThat(email).isEqualTo("a@b.com");
    }
}
