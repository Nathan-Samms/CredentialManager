package credentialmanager.model;

import java.util.List;

/**
 * @author Jordan Parmer, Nathan Samms
 * Contract for repo implementation (In-memory for M2); M4 will implement with persistence
 */

public interface CredentialRepository {

    //Read every stored credential
    List<Credential> findAll();

    //Find credential by ID
    Credential findById(int id);

    //Save Credential when id == 0
    Credential save(Credential credential);

    //Delete credential (will throw exception if no credential exists with given ID)
    void deleteById(int id);
}
