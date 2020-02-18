import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleCoverageFunctionsTest {
    SimpleCoverageFunctions testClass;

    @BeforeEach
    public void setUp() throws Exception {
        testClass = new SimpleCoverageFunctions();
    }

    @Test
    public void addTwoNumTest(){
        int sum = testClass.addTwoNum(1, 2);
        assertEquals(3, sum);
    }

    @ParameterizedTest
    @CsvSource({"1,2,2", "2,1,2"})
    void returnLargest(int a, int b, int result) {
        int largest = testClass.returnLargest(a,b);
        assertEquals(result, largest);
    }

    @ParameterizedTest
    @CsvSource({"1,2,3,2", "100, 1, 4, 1", "100, 100, 1, 100"})
    void doWeirdStuff(int a, int b, int c, int result) {
        int weirdStuff = testClass.doWeirdStuff(a, b, c);
        assertEquals(result, weirdStuff);


    }

}