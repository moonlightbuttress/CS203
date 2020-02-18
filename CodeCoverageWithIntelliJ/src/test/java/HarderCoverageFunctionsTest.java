import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HarderCoverageFunctionsTest {
    HarderCoverageFunctions testClass;

    @BeforeEach
    public void setUp() throws Exception {
        testClass = new HarderCoverageFunctions();
    }

    @ParameterizedTest
    @CsvSource({"1,1, -1, 2, The spell fizzles in front of you.","1,0, 0, 2, The spell fizzles in front of you.", "3,1,1, 2, The spell destroys the monster!",
            "4,1,1,2,The spell zaps the monster!","0,1,1,0,You win!", "1,0,0,0,Monster is still alive"})
    public void castSpellsTest(int monsterHealth, int manaLevel, int wandDurability, int queuedSpells, String result) {
        String spell = testClass.castSpells(monsterHealth, manaLevel, wandDurability, queuedSpells);
        assertEquals(result, spell);
    }


    @ParameterizedTest
    @CsvSource({"false,true,true,YOU FOUND THE TRUTH! ", "true, false, true, Well I guess that is true too",
            "false, false, false, Are you even trying to find the truth?", "false, true, false, Well that might be true",
            "true, false, true, Well I guess that is true too", "true, true, false, Well that might be true"})
    public void truthFinderTest(boolean a, boolean b, boolean c, String result){
        String truth = testClass.truthFinder(a, b, c);
        assertEquals(result, truth);
    }
}