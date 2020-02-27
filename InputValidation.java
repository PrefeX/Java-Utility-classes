
import java.util.Arrays;


/**
 * A collection of commonly used utilities for verifying/validating data and
 * inputs.
 *
 * @author Christian
 */
public final class InputValidation {

    /**
     * Checks is a provided string actually contains any information.
     * <p>
     * Will return {@code true} if it's {@code null}, empty or only contains
     * spaces. If any actual content is found, it returns {@code false}.
     *
     * @param value the string we want to check
     * @return true if it's null or empty, false if it contains information
     */
    public static final boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }


    /**
     * Checks is a provided byte-array actually contains any information.
     * <p>
     * Will return {@code true} if it's {@code null}, empty or only contains
     * spaces. If any actual content is found, it returns {@code false}.
     *
     * @param value the byte-array we want to check
     * @return true if it's null or empty, false if it contains information
     */
    public static final boolean isNullOrEmpty(byte[] value) {
        return value == null || value.length == 0 || isNullOrEmpty(Arrays.toString(value));
    }


    /**
     * Checks if a provided phone-number have a valid format.
     * <p>
     * It should either start with a '+' (country code), or consist of pure
     * numbers.
     *
     * @param phoneNumber the number we want to validate
     * @return true if the phone number is valid, if not it returns false
     */
    public static final boolean isValidPhoneNumber(String phoneNumber) {
        boolean valid = false;

        if (!isNullOrEmpty(phoneNumber)) {
            try {
                // Can we sucessfully cast it as a long? If so assume it's a phone number. The number can start with a '+' sign (country code).
                Long.parseUnsignedLong(phoneNumber);
                valid = true;
            } catch (NumberFormatException e) {
                // One of the verification conditions failed. We don't hava a valid phone number...
            }
        }
        return valid;
    }


    /**
     * Checks if a provided email address have a valid format.
     * <p>
     * TODO: Consider replacing with regex or another solution! This is a
     * temporary implementation!
     *
     * @param email the email we want to validate
     * @return true if the email is valid, if not it returns false
     */
    public static final boolean isValidEmail(String email) {
        boolean valid = false;

        if (!isNullOrEmpty(email)) {
            try {
                // TODO: Optimize, or replace check with regex
                String[] string = email.split("@");
                // Check the validity of several common conditions and errors that should not be present in the email
                // https://en.wikipedia.org/wiki/Email_address#Local-part
                if (string.length == 2 // Make sure there is only one '@'
                        && string[0].length() > 0 // Must have content before the '@'
                        && string[1].contains(".") // Make sure we have at least one '.' after the '@'
                        && string[1].length() > 3 // Make sure the length after the '@' is at least 3 (the '.' and a single-letter domain and topdomain)
                        && !string[0].startsWith(".") // Local-part can't start with '.'
                        && !string[0].endsWith(".") // Local-part can't end with '.'
                        && !string[1].startsWith(".") // The domain can't start with '.'
                        && !string[1].endsWith(".") // The top-domain can't end with '.'
                        && !string[0].contains("..") // We can have a repeating '.' character
                        && !string[1].contains("..") // We can have a repeating '.' character
                        && !string[0].contains(" ") // We can have a space in the email-address
                        && !string[1].contains(" ") // We can have a space in the email-address
                        ) {
                    valid = true;
                }
            } catch (Exception e) {
                // One of the verification conditions failed. We don't hava a valid email...
            }
        }
        return valid;
    }

}
