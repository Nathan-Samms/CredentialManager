package credentialmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jordan parmer, Nathan Samms
 * Stores credentials in an ArrayList (Volatile on program end). Temporary until M4 when persistence is implemented with
 * encrypted storage to file
 */
public class InMemoryCredentialRepository implements CredentialRepository {

    private final List<Credential> credentials = new ArrayList<>();
    private int nextId = 1;

    //Return defensive copy
    @Override
    public List<Credential> findAll() {
        return new ArrayList<>(credentials);
    }

    @Override
    public Credential findById(int id) {
        for (Credential c : credentials) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public Credential save(Credential credential) {
        if (credential == null) {
            throw new IllegalArgumentException("Cannot save a missing credential.");
        }

        // Create
        if (credential.getId() == 0) {
            credential.setId(nextId++);
            credentials.add(credential);
            return credential;
        }

        //Update
        for (int i = 0; i < credentials.size(); i++) {
            if (credentials.get(i).getId() == credential.getId()) {
                //Replace in place
                credentials.set(i, credential);
                return credential;
            }
        }
        throw new VaultException("No credential with id " + credential.getId() + " to update.");
    }

    @Override
    public void deleteById(int id) {
        boolean removed = credentials.removeIf(existing -> existing.getId() == id);
        if (!removed) {
            throw new VaultException("No credential with id " + id + " to delete.");
        }
    }
}
