package de.ditti4.restapiissuemanager.developer;

public class DeveloperNotFoundException extends RuntimeException {
    public DeveloperNotFoundException(long id) {
        super("Developer not found: " + id);
    }
}
