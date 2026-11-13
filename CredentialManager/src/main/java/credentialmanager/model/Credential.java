package credentialmanager.model;

/**
 * @author Jordan Parmer, Nathan Samms
 * Entity: one Credential object = one record (one future table row), a
 * saved website login.
 *
 * Fields: id, siteUrl, username, password, notes, rotationDays (how often the
 * password should be changed) and favorite (true/false).
 */
public class Credential {

    public static final int MAX_TEXT = 1000;

    private int id;                 // 0 = not saved yet; the repository assigns it
    private String siteUrl = "";
    private String username = "";
    private String password = "";
    private String notes = "";
    private int rotationDays = 90;
    private boolean favorite;

    /**Constructor chain */

    //Empty credential w/ default values
    public Credential() {
    }

    //Brand new credential
    public Credential(String siteUrl, String username, String password,
                      String notes, int rotationDays, boolean favorite) {
        this(0, siteUrl, username, password, notes, rotationDays, favorite);
    }

    //Credential with an ID
    public Credential(int id, String siteUrl, String username, String password,
                      String notes, int rotationDays, boolean favorite) {
        setId(id);
        setSiteUrl(siteUrl);
        setUsername(username);
        setPassword(password);
        setNotes(notes);
        setRotationDays(rotationDays);
        setFavorite(favorite);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative: " + id);
        }
        this.id = id;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = requireText(siteUrl, "Site URL");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = requireText(username, "Username");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // Not trimmed: spaces can be part of a password. It just cannot be empty.
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        checkLength(password, "Password");
        this.password = password;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        String cleaned = (notes == null) ? "" : notes.trim();
        checkLength(cleaned, "Notes");
        this.notes = cleaned;
    }

    public int getRotationDays() {
        return rotationDays;
    }

    //Setter rejects bad values
    public void setRotationDays(int rotationDays) {
        if (rotationDays <= 0) {
            throw new IllegalArgumentException(
                    "Rotation days must be greater than zero: " + rotationDays);
        }
        this.rotationDays = rotationDays;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    //Avoid leaking password
    @Override
    public String toString() {
        return String.format("#%-2d %-30s %-20s rotate %3d days  %s",
                id, siteUrl, username, rotationDays, favorite ? "favorite" : "");
    }

    private static String requireText(String value, String label) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(label + " cannot be empty.");
        }
        String cleaned = value.trim();
        checkLength(cleaned, label);
        return cleaned;
    }

    private static void checkLength(String value, String label) {
        if (value.length() > MAX_TEXT) {
            throw new IllegalArgumentException(
                    label + " is too long (maximum " + MAX_TEXT + " characters).");
        }
    }
}
