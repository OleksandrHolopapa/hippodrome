import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {
    private Horse horse;

    @ParameterizedTest
    @MethodSource("getWrongParametersForConstructor")
    void shouldBeExceptionIfSomeParametersOfConstructorIsWrong(String name, int speed, int distance, String message) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> horse = new Horse(name, speed, distance));
        assertEquals(message, exception.getMessage());
    }

    private static Stream<Arguments> getWrongParametersForConstructor() {
        return Stream.of(
                Arguments.of(null, 20, 40, "Name cannot be null."),
                Arguments.of("", 20, 40, "Name cannot be blank."),
                Arguments.of(" ", 20, 40, "Name cannot be blank."),
                Arguments.of("  ", 20, 40, "Name cannot be blank."),
                Arguments.of("name", -20, 40, "Speed cannot be negative."),
                Arguments.of("name", 20, -40, "Distance cannot be negative.")
        );
    }
    @Test
    void shouldReturnHorsName() {
        horse = new Horse("name", 20);
        assertEquals("name", horse.getName());
    }

    @Test
    void shouldReturnHorsSpeed() {
        horse = new Horse("name", 20);
        assertEquals(20, horse.getSpeed());
    }

    @Test
    void shouldReturnDistanceIfConstructorHasThreeParameters() {
        horse = new Horse("name", 20, 40);
        assertEquals(40, horse.getDistance());
    }

    @Test
    void shouldReturnDistanceIfConstructorHasTwoParameters() {
        horse = new Horse("name", 20);
        assertEquals(0, horse.getDistance());
    }
}