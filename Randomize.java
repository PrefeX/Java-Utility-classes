
import java.util.Random;


/**
 * A collection of commonly used utilities that randomizes numbers, strings and
 * other values.
 *
 * @author Christian
 */
public final class Randomize {

    /**
     * Create a randomized number between two given values.
     *
     * @param min The lowest number (inclusive)
     * @param max The highest number (inclusive)
     * @return The randomized number
     */
    public static final int randomBetween(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }


    /**
     * Extracts a single random char from a provided string
     *
     * @param input The string containing the values we want to randomize from
     * @return The randomized char
     */
    public static final char randomCharFromString(String input) {
        int randomizedPosition = new Random().nextInt(input.length());
        return input.charAt(randomizedPosition);
    }
}
