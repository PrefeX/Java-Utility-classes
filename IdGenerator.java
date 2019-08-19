

/**
 * A utility class that generates unique, incremented ID's. This should ideally
 * be handled by a database, or be stored to be persistent.
 *
 * @author Christian
 */
public class IdGenerator {

    private static Long id = 0L;


    /**
     * Return the ID-number, and increase it by one so we have a new number for
     * the next iteration.
     *
     * @return The generated, unique ID
     */
    public static Long generateId() {
        return id++;
    }
}
