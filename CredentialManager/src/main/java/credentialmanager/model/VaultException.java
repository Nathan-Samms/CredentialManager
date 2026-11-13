package credentialmanager.model;

/**
 * @author Jordan Parmer, Nathan Samms
 *
 * Unchecked, thrown by any repository on error w/ save or check
 */
public class VaultException extends RuntimeException {

    public VaultException(String message) {
        super(message);
    }

    public VaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
