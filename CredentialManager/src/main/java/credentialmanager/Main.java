package credentialmanager;

import java.util.List;
import credentialmanager.model.Credential;
import credentialmanager.model.CredentialRepository;
import credentialmanager.model.InMemoryCredentialRepository;
import credentialmanager.model.VaultException;

/**
 * Milestone 2: a console-only Main (no GUI). It creates, lists, updates and
 * deletes credentials through the repository and prints the result of each
 * step, then tests the bad-input cases: an empty or negative rotation period,
 * one typed as words, an empty site or password, and an id that does not exist.
 *
 * In Milestone 3 this becomes ConsoleMain and Main becomes the Swing launcher.
 *
 * @author [Partner 1 name] and [Partner 2 name]
 */
public class Main {

    public static void main(String[] args) {
        // Polymorphism (Unit 8): the variable's type is the interface.
        CredentialRepository repo = new InMemoryCredentialRepository();

        heading("1. CREATE - add ten credentials (seed data)");
        repo.save(new Credential("https://mail.example.com", "jordan.demo", "Tr0ub4dor&3-demo", "Personal email", 90, true));
        repo.save(new Credential("https://bank.example.net", "jdemo1234", "c0rrect-Horse-bat#7", "Checking account", 60, true));
        repo.save(new Credential("https://shop.example.org", "jordan@example.org", "Pa55w0rd!shop", "", 120, false));
        repo.save(new Credential("https://streaming.example.tv", "family-plan", "Str3am1ng-Fake-99", "Shared with family", 180, false));
        repo.save(new Credential("https://school.example.edu", "jdemo", "Sch00l-Demo-Pw!1", "Course portal", 90, true));
        repo.save(new Credential("https://github.example.dev", "jordan-dev", "gh-Fake-T0ken_2026", "Two-factor enabled", 90, true));
        repo.save(new Credential("https://cloud.example.io", "jordan.demo", "Cl0ud-St0rage!42", "Backups", 365, false));
        repo.save(new Credential("https://forum.example.net", "jd_forum", "F0rum-Demo-Only-7", "", 365, false));
        repo.save(new Credential("https://utility.example.com", "acct-55501", "Ut1lity-Bill-Pay#5", "Electric bill", 120, false));
        Credential travel =
                repo.save(new Credential("https://travel.example.org", "j.demo", "Tr@vel-Fake-Login-8", "Airline points", 150, false));
        System.out.println("Last credential saved was given id " + travel.getId()
                + " automatically - the user never types an id.");

        heading("2. READ - list every credential");
        printAll(repo);

        heading("3. READ ONE - findById(3)");
        System.out.println(repo.findById(3));

        heading("4. UPDATE - change password, rotation and favorite of credential #" + travel.getId());
        Credential changed = new Credential(travel.getId(), "https://travel.example.org", "j.demo",
                "N3w-Travel-Pw-2026!", "Airline points", 60, true);
        repo.save(changed);
        System.out.println("After update: " + repo.findById(travel.getId()));
        System.out.println("Credentials stored: " + repo.findAll().size() + " (update did not add a row)");

        heading("5. DELETE - remove credential #5");
        System.out.println("Deleting: " + repo.findById(5));
        repo.deleteById(5);
        System.out.println("findById(5) afterwards -> " + repo.findById(5));
        printAll(repo);

        heading("6. BAD INPUT - each is rejected and nothing stored changes");
        int before = repo.findAll().size();
        tryCreate(repo, "https://blank.example.com", "u1", "pw-1234", "");
        tryCreate(repo, "https://words.example.com", "u2", "pw-1234", "ninety");
        tryCreate(repo, "https://negative.example.com", "u3", "pw-1234", "-5");
        tryCreate(repo, "https://zero.example.com", "u4", "pw-1234", "0");
        tryCreate(repo, "", "u5", "pw-1234", "30");
        tryCreate(repo, "https://nopassword.example.com", "u6", "", "30");
        System.out.println("findById(99)   -> " + repo.findById(99));
        try {
            repo.deleteById(99);
        } catch (VaultException e) {
            System.out.println("deleteById(99) -> rejected: " + e.getMessage());
        }
        try {
            repo.save(new Credential(99, "https://ghost.example.com", "ghost", "pw-1234", "", 30, false));
        } catch (VaultException e) {
            System.out.println("update id 99   -> rejected: " + e.getMessage());
        }
        System.out.println("Credentials stored before: " + before + ", after: " + repo.findAll().size());

        heading("7. DEFENSIVE COPY - changing the returned list does not change the repository");
        List<Credential> copy = repo.findAll();
        copy.clear();
        System.out.println("Cleared the returned list (size " + copy.size() + "); repository still holds "
                + repo.findAll().size() + " credentials.");
    }

    /**
     * Mimics what the Controller will do with the form in M3: turn the typed
     * rotation text into an int, then let the entity validate the rest.
     */
    private static void tryCreate(CredentialRepository repo, String site, String username,
                                  String password, String rotationText) {
        String label = String.format("%-32s rotation=\"%s\"", site.isEmpty() ? "(no site)" : site, rotationText);
        if (rotationText.trim().isEmpty()) {
            System.out.println(label + " -> rejected: Rotation days is required.");
            return;
        }
        int rotationDays;
        try {
            rotationDays = Integer.parseInt(rotationText.trim());
        } catch (NumberFormatException e) {
            System.out.println(label + " -> rejected: Rotation days must be a whole number, for example 90.");
            return;
        }
        try {
            Credential saved = repo.save(new Credential(site, username, password, "", rotationDays, false));
            System.out.println(label + " -> saved as #" + saved.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(label + " -> rejected: " + e.getMessage());
        }
    }

    private static void printAll(CredentialRepository repo) {
        for (Credential c : repo.findAll()) {
            System.out.println(c);
        }
        System.out.println("(" + repo.findAll().size() + " credentials)");
    }

    private static void heading(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
