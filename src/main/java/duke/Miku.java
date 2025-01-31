package duke;

import java.io.IOException;


public class Miku {
    private UI ui;
    private TaskList list;
    private Storage storage;
    
    public Miku() {
        this.list = new TaskList();
        // define file and dir paths
        String dir = "./data";
        String filepath = dir+ "/tasklist.txt";
        Storage s = new Storage(dir, filepath, this.list);
        this.storage = s;
        this.ui = new UI(list, s);
    }

    public void run() {  
        try {
            this.storage = this.storage.run();
            this.list = storage.getList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        this.ui = new UI(list, storage);
        ui.showWelcome();
        ui.interact();
    }

    public static void main(String[] args) {
        try {
            new Miku().run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
