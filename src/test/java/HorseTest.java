import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

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
    void shouldReturnHorseNameValue() {
        horse = new Horse("name", 20);
        assertEquals("name", horse.getName());
    }

    @Test
    void shouldReturnHorseSpeedValue() {
        horse = new Horse("name", 20);
        assertEquals(20, horse.getSpeed());
    }

    @Test
    void shouldReturnDistanceValueIfConstructorHasThreeParameters() {
        horse = new Horse("name", 20, 40);
        assertEquals(40, horse.getDistance());
    }

    @Test
    void shouldReturnDistanceValueIfConstructorHasTwoParameters() {
        horse = new Horse("name", 20);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void shouldCallMethodGetRandomDoubleWhenMethodMoveIsExecuting() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horse = new Horse("name", 20, 40);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.7, 0.8})
    void shouldMethodMoveReturnCorrectValueWhenResultOfMethodGetRandomDoubleIsKnown(double getRandomDoubleResult) {
        horse = new Horse("name", 2.5);
        double expectedValue = horse.getDistance() + horse.getSpeed() * getRandomDoubleResult;
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(getRandomDoubleResult);
            horse.move();
        }
        assertEquals(expectedValue, horse.getDistance());
    }
}