package credentialmanager.model;

import java.util.List;

/**
 * The contract (Unit 11). In M2 it has one implementation
 * (InMemoryCredentialRepository); in M4 a persistent one
 * (EncryptedFileCredentialRepository) implements the same interface, with no
 * methods added, and is swapped in by changing one line of Main.
 *
 * @author [Partner 1 name]
 */
public interface CredentialRepository {

    /** Read: every stored credential, as a copy of the internal list. */
    List<Credential> findAll();

    /** Finds one credential by id; returns null if none has that id. */
    Credential findById(int id);

    /** Create when credential.getId() == 0, otherwise Update. Returns the saved credential with its id. */
    Credential save(Credential credential);

    /** Delete. Throws VaultException if no credential has that id. */
    void deleteById(int id);
}
