import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {
    static Complex complex;
    @BeforeAll
    static void setup() {
        complex = new Complex();
    }
    @Nested
    @DisplayName("Square Root Testing")
    class squareRoot {

        @ParameterizedTest
        @DisplayName("Equivalence Class for Square Root")
        @CsvSource({ "1, 1.0", "4, 2.0", "9, 3.0", "0 , 0.0"})
        void squareRootEquivalence(int number, double correctAnswer) {
            assertEquals(correctAnswer, complex.squareRoot(number));
        }

        @ParameterizedTest
        @DisplayName("Edge Cases for Square Root")
        @CsvSource({ "-4, 2", "-1, -1" })
        void squareRootEdge(int number, double correctAnswer) {
            Exception exception = assertThrows(ArithmeticException.class, () -> {
                complex.squareRoot(number);
            });
        }
    }


    @Nested
    @DisplayName("Factorial Testing")
    class factorial {

        @ParameterizedTest
        @DisplayName("Equivalence Class for Factorial")
        @CsvSource({ "1, 1", "2, 2", "3, 6", "0,1"})
        void factorialEquivalence(int number, int correctAnswer) {
            assertEquals(complex.factorial(number), correctAnswer);
        }

        @ParameterizedTest
        @DisplayName("Edge Cases for Factorial")
        @CsvSource({ "-1, 1", "-3, 4", "-2, 1"})
        void factorialEdge(int number, int correctAnswer) {
            Exception exception = assertThrows(ArithmeticException.class, () -> {
                complex.factorial(number);
            });
        }
    }
}