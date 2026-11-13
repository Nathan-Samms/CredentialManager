# Credential Manager (Milestone 2)

Model layer plus a console-only `Main` for CNIT 25501 (Nathan Samms, Jordan Parmer).

- `model/` - `Credential`, `CredentialRepository` (interface), `InMemoryCredentialRepository`, `VaultException`
- `view/` and `controller/` - only `package-info.java` until Milestone 3
- `Main.java` - console demo: create, read, update, delete, bad input, defensive copy
- `sample-output.txt` - expected output

Run in IntelliJ: open this folder, right-click `Main` > Run Main.main().
Terminal (Java 17+): `mkdir out && javac -d out $(find src/main/java -name "*.java") && java -cp out credentialmanager.Main`
