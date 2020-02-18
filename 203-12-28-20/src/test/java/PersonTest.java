import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PersonTest {
    private Person testPerson;

    @BeforeEach
    void setup(){
        testPerson = new Person("Joe", "Mama", LocalDate.parse("1990-01-01"));
    }
    @Test
    void testGetFullName() {
        String expectedName = "Joe Mama";
        assertEquals(expectedName, testPerson.getFullName());
    }

    @Test
    void testGetAge() {
        double expectedAge = 30;
        assertEquals(expectedAge, testPerson.getAge());
    }
}