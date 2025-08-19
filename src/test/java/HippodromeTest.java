import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {
    Hippodrome hippodrome;

    @ParameterizedTest
    @MethodSource("getWrongParametersForConstructor")
    void shouldBeExceptionIfConstructorHasWrongParameters(List<Horse> horses, String message) {
        var exception = assertThrows(IllegalArgumentException.class, () -> hippodrome = new Hippodrome(horses));
        assertEquals(message, exception.getMessage());
    }

    private static Stream<Arguments> getWrongParametersForConstructor() {
        return Stream.of(
                Arguments.of(null, "Horses cannot be null."),
                Arguments.of(List.of(), "Horses cannot be empty.")
        );
    }

    @Test
    void shouldReturnHorsesList() {
        List<Horse> horses = generateListOfHorses();
        hippodrome = new Hippodrome(horses);
        List<Horse> horses2 = new ArrayList<>(horses);
        assertEquals(horses2, hippodrome.getHorses());
    }

    @Test
    void shouldReturnHorseWithTheBiggestDistanceValue() {
        Horse horse1 = new Horse("horse_1", 20, 10);
        Horse horse2 = new Horse("horse_1", 20, 20);
        Horse horse3 = new Horse("horse_1", 20, 15);
        hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));
        assertEquals(horse2, hippodrome.getWinner());
    }

    @Test
    //This test does not run in Java version 24
    void shouldCallMoveFiftyTimes() {
        List<Horse> horses = generateMockHorsesList();
        hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse hors : horses) {
            Mockito.verify(hors).move();
        }
    }

    private static List<Horse> generateListOfHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horses.add(new Horse("horse_" + i, 20 + i / 10.0));
        }
        return horses;
    }

    private static List<Horse> generateMockHorsesList() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }
        return horses;
    }
}