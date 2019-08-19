
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A collection of commonly used security-utilities for salting, hashing and
 * encryption.
 * <p>
 * If available for your Java version, please use the new, builtin hashing
 * classes provided by {@link
 * javax.security.enterprise.identitystore} (Soteria) that got introduced with
 * the new Java EE 8 security API!
 *
 * @author Christian
 */
public final class Security {

    /**
     * What algorithm we should use when performing hashing. The algorithm has
     * to be one supported by the {@link MessageDigest} class.
     * <p>
     * This setting only applies to the hashing used in the
     * {@link Security}-class, and have no impact on what is used by
     * IdentityStore and DatabaseIdentityStoreDefinition.
     * <p>
     * The current value is '{@value #DEFAULT_HASH_ALGORITHM}'
     */
    private static final String DEFAULT_HASH_ALGORITHM = "SHA-256";

    private static final String DEFAULT_TEXT_ENCODING = "UTF-8";

    private static final int DEFAULT_SALT_LENGTH = 128;


    /**
     * Generates a secure, randomized salt value.
     * <p>
     * Uses the default salt length of {@value #DEFAULT_SALT_LENGTH}. For a
     * custom salt length, use {@link #generateSalt(int)} instead.
     *
     * @return A byte-array containing a secure, random salt
     * @since 1.0
     * @see #generateSalt(length)
     */
    public static final byte[] generateSalt() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }


    /**
     * Generates a secure, randomized salt value.
     *
     * @param length the length of the generated salt
     * @return A byte-array containing a secure, random salt
     */
    public static final byte[] generateSalt(int length) {
        byte[] salt = new byte[length];
        try {
            SecureRandom.getInstanceStrong().nextBytes(salt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, "A critical exception happened while generating a salt value!", ex);
            System.err.println("A critical exception happened while generating a salt value:");
            System.err.println(ex);
        }
        return salt;
    }


    /**
     * Hashes a string using a specified algorithm and a salt.
     * <p>
     * Uses the default algorithm {@value #DEFAULT_HASH_ALGORITHM}. If you want
     * to use another algorithm, use {@link #hash(String, byte[], String)}
     * instead.
     *
     * @param unhashedString The string we want to hash
     * @param salt The salt used for the hashing
     * @return The hashed string
     *
     * @see #hash(String, byte[], String)
     * @see #generateSalt()
     * @see #validateHash(String, String, byte[])
     */
    public static final String hash(String unhashedString, byte[] salt) {
        return hash(unhashedString, salt, DEFAULT_HASH_ALGORITHM);
    }


    /**
     * Hashes a string using a specified algorithm and a salt.
     *
     * @param unhashedString The string we want to hash
     * @param salt The salt used for the hashing
     * @param algorithm The algorithm we want to use
     * @return The hashed string
     *
     * @see #hash(String, byte[])
     * @see #generateSalt()
     * @see #validateHash(String, String, byte[])
     *
     */
    private static String hash(String unhashedString, byte[] salt, String algorithm) {
        StringBuilder hashedString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);

            // Apply the salt, and hash the password
            md.update(salt);
            byte[] stringBytes = md.digest(unhashedString.getBytes(DEFAULT_TEXT_ENCODING));

            // Convert the bytes to hex-values
            for (byte stringByte : stringBytes) {
                hashedString.append(Integer.toString((stringByte & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Security.class.getName()).log(Level.SEVERE, "A critical exception happened during a password hashing!", ex);
            System.err.println("A critical exception happened during a password hashing: \n" + ex);
        }

        return hashedString.toString();
    }


    /**
     * See if a provided string matches with an existing set of hash and salt
     * values.
     * <p>
     * Returns {@code true} if the string matches, otherwise it returns
     * {@code false}.
     *
     * @param providedString The new string we want to verify
     * @param hashedString The existing hashed string we want to compare to
     * @param salt The salt we used for the old hashing
     * @return True if the string matches, false if it don't
     */
    public static final boolean validateHash(String providedString, String hashedString, byte[] salt) {
        boolean match = false;
        String s = hash(providedString, salt);

        if (s.equals(hashedString)) {
            match = true;
        }

        return match;
    }


    /**
     * Generates a simple, user friendly password. This is less secure then
     * common passwords, but is easier for users to read and enter. This is
     * primarily intended for situations where passwords is short lived,
     * disposable or needs to be easier to remember or enter into forms.
     * <p>
     * The passwords contains the characters {@code A-Z} and {@code 1-9}. There
     * are no mix between lower and upper case characters, and the number
     * {@code 0} and character {@code O} have been excluded to avoid confusion
     * with each other.
     * <p>
     * The length of the password is randomized between 6 and 8 characters.
     *
     * @return The generated password
     */
    public static final String generateFriendlyPassword() {
        String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

        int minLength = 6;
        int maxLength = 8;
        int passwordLength = Randomize.randomBetween(minLength, maxLength);

        StringBuilder builder = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            builder.append(Randomize.randomCharFromString(validCharacters));
        }

        return builder.toString();
    }


    /**
     * Generate a pseudo-random token, using a cryptographically strong pseudo
     * random number generator
     *
     * @return The generated token
     * @see Password#generateSecure()
     */
    public static final String generateToken() {
        // Generate a unique token id
        String token = UUID.randomUUID().toString();

        //Random random = new SecureRandom();
        //String token = new BigInteger(130, random).toString(32);
        return token;
    }
}
