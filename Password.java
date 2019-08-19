
import java.util.UUID;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;


/**
 * A utility-class responsible for the creation and hashing of passwords.
 *
 * @author Christian
 */
public class Password {

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    private String password;


    public Password() {
        if (this.password == null) {
            this.password = new String();
        }
    }


    /**
     * Applies a hash to the selected password.
     *
     * @since 1.0
     * @return A {@code string} containing the hashed password
     */
    public String hash() {
        return passwordHash.generate(this.password.toCharArray());
    }


    /**
     * Generates a simple, user friendly password. This is less secure, but is
     * intended for situations where passwords is short lived, disposable or
     * needs to be easy to remember or enter into forms.
     * <p>
     * The passwords contains the characters {@code A-Z} and {@code 1-9}. There
     * are no mix between lower and upper case characters, and the number
     * {@code 0} and character {@code O} have been excluded to avoid confusion
     * with each other.
     * <p>
     * The length of the password is randomized between 6 and 8 characters.
     *
     * @return A {@link Password password} based on this instance, containing
     * the generated, unhashed password
     */
    public Password generateFriendly() {
        final String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        final int passwordLength = Randomize.randomBetween(6, 8);

        StringBuilder builder = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            builder.append(Randomize.randomCharFromString(validCharacters));
        }

        this.password = builder.toString();
        return this;
    }


    /**
     * Generate a secure password by using a cryptographically strong pseudo
     * random number generator.
     *
     * @return A {@link Password password} based on this instance, containing
     * the generated, unhashed password
     */
    public Password generateSecure() {
        this.password = UUID.randomUUID().toString();
        return this;
    }


    /**
     * Creates a password based on the users input
     *
     * @param password The password to use
     * @return A {@link Password password} based on this instance, containing
     * the selected, unhashed password
     */
    public Password set(String password) {
        this.password = password;
        return this;
    }


    /**
     * Converts all of the characters in the password to upper case.
     *
     * @return A {@link Password password} based on this instance, with the
     * password in upper case
     */
    public Password toUpperCase() {
        this.password = this.password.toUpperCase();
        return this;
    }


    /**
     * Converts all of the characters in the password to lower case.
     *
     * @return A {@link Password password} based on this instance, with the
     * password in lower case
     */
    public Password toLowerCase() {
        this.password = this.password.toLowerCase();
        return this;
    }


    @Override
    public String toString() {
        return this.password;
    }

}
