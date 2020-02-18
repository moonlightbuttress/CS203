import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTest {
    static Simple simple;
    @BeforeAll
    static void setup() {
        simple = new Simple();
    }



    @ParameterizedTest
    @CsvSource({ "1, 1, 2", "20, 20, 40", "0, 1, 1", "-1, -3, -4" })
    @DisplayName("Add Testing")
    void add(int num1, int num2, int correctAnswer) {
        int result = simple.add(num1, num2);
    assertEquals(correctAnswer, result);
    }

    @ParameterizedTest
    @CsvSource({ "1, 1, 0", "20, 21, 1", "0, 1, -1", "-1, -3, 2" })
    @DisplayName("Subtract Testing")
    void subtract(int num1, int num2, int correctAnswer) {
        int result = simple.subtract(num1, num2);
        assertEquals((num1-num2), result);
    }

    @ParameterizedTest
    @CsvSource({ "1, 1, 1", "20, 2, 40", "0, 340221, 0"})
    @DisplayName("Multiply Testing")
    void multiply(int num1, int num2, int correctAnswer) {
        int result = simple.multiply(num1, num2);
        assertEquals((num1*num2), result);
    }

    @ParameterizedTest
    @CsvSource({ "1, 1, 1", "20, 20, 1",})
    @DisplayName("Division Testing")
    void divide(int num1, int num2, int correctAnswer) {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            simple.divide(num1, 0);
        });
        int result = simple.divide(num1, num2);
        assertEquals(num1/num2, result);
    }

    @ParameterizedTest
    @CsvSource({ "1, 1, 1", "20, 2, 400"})
    @DisplayName("Power Testing")
    void power(int num1, int num2, int correctAnswer) {
        int result = simple.power(num1, num2);
        assertEquals(Math.pow(num1, num2), result);
    }
}