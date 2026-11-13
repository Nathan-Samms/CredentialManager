package credentialmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores credentials in an ArrayList and nothing else; everything is lost when
 * the program ends. Written first (brief 2.2) so the View and Controller can be
 * built in M3 before any file storage exists - and kept as the demo fallback.
 *
 * @author [Partner 2 name]
 */
public class InMemoryCredentialRepository implements CredentialRepository {

    private final List<Credential> credentials = new ArrayList<>();
    private int nextId = 1;

    @Override
    public List<Credential> findAll() {
        return new ArrayList<>(credentials);     // defensive copy - Unit 7
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
        if (credential.getId() == 0) {                       // Create
            credential.setId(nextId++);                      // carry the new id back onto the object
            credentials.add(credential);
            return credential;
        }
        for (int i = 0; i < credentials.size(); i++) {       // Update
            if (credentials.get(i).getId() == credential.getId()) {
                credentials.set(i, credential);              // replace in place: order is kept
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
