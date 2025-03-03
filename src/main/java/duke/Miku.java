package duke;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Represents the main class of the program.
 */
public class Miku extends Application {
    private TaskList list;
    private Storage storage;
    private Duke duke;

    /**
     * Constructor for Miku.
     */
    public Miku() {
        this.list = new TaskList();
        // define file and dir paths
        String dir = "./data";
        String filepath = dir + "/tasklist.txt";
        Storage s = new Storage(dir, filepath, this.list);
        this.storage = s;
    }

    /**
     * Runs the program.
     */
    public void run() {
        try {
            this.storage = this.storage.run();
            this.list = storage.getList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    /**
     * Main method of the program.
     */
    @Override
    public void start(Stage stage) {
        try {
            // setup the file IO
            run();

            // Initialize Duke after running the storage
            this.duke = new Duke(this.list);

            FXMLLoader fxmlLoader = new FXMLLoader(Miku.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(this.duke); // inject the Duke instance
            // Set the title of the stage 
            stage.setTitle("Miku Helper");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
