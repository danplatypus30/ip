package duke;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 * This is the entry point to the application.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Miku.class, args);
    }
}

