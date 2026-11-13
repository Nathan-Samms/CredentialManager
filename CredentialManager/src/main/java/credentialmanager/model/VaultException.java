package credentialmanager.model;

/**
 * Thrown by a repository when something goes wrong with storage or lookup
 * (in M2: updating or deleting an id that does not exist; in M4: unreadable
 * file, wrong master password, failed write). It is unchecked so the repository
 * interface looks the same for every implementation.
 *
 * @author [Partner 2 name]
 */
public class VaultException extends RuntimeException {

    public VaultException(String message) {
        super(message);
    }

    public VaultException(String message, Throwable cause) {
        super(message, cause);
    }
}
