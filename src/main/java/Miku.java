import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Miku {
    private static void parseTaskLine(String line, ArrayList<Task> list) {
        // split the line using '|' as a delimiter and trim spaces
        String[] parts = line.split("\\|");
        if (parts.length > 5 || parts.length < 3) {
            System.out.println("Invalid task: " + line);
            return;
        }

        // extract variables
        char type = parts[0].trim().charAt(0); // T, D, or E
        boolean isMarked = parts[1].trim().equals("1"); // convert 1/0 to boolean
        String description = parts[2].trim();

        // add the Task obj to list
        if (type == 'T') {
            ToDos td = new ToDos(description, isMarked);
            list.add(td);
        } else if (type == 'D') {
            String by = parts[3].trim();
            Deadlines dd = new Deadlines(description, isMarked, by);
            list.add(dd);
        } else if (type == 'E') {
            String from = parts[3].trim();
            String to = parts[4].trim();
            Events ee = new Events(description, isMarked, from, to);
            list.add(ee);
        // ensure valid type 
        } else {
            System.out.println("Unknown task type: " + type);
            return;
        }
    }

    private static void writeListToFile(ArrayList<Task> list) {
        try (FileWriter writer = new FileWriter("./data/tasklist.txt")) {
            for (Task tasks : list) {
                writer.write(tasks.toFileString() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("unable to update file, error...");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<Task>();

        // define file and dir paths
        String dir = "./data";
        String filepath = dir+ "/tasklist.txt";

        // create dir if it does not exist
        File directory = new File(dir);
        if (directory.exists() == false) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + dir);
            } else {
                System.out.println("Failed to create directory: " + dir);
                sc.close();
                return; // exit if create dir fails
            }
        }

        // create file obj, dir should exist
        File file = new File(filepath);

        // create file if file does not exist
        if (file.exists() == false) {
            try {
                if (file.createNewFile()) {
                    System.out.println("file created: " + filepath);
                } else {
                    System.out.println("file already exists: " + filepath);
                }
            } catch (IOException e) {
                System.out.println("error creating file: " + filepath);
                e.printStackTrace();
                sc.close();
                return;
            }
        }

        // read the file contents
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                // parse the data into Task object
                String line = scanner.nextLine();
                parseTaskLine(line, list); // list is passed by reference
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found: " + file.getAbsolutePath());
            System.out.println("error, please try again later..");
            e.printStackTrace();
            return;
        }

        String logo = " __  __ _ _          \n"
                + "|  \\/  (_) | ___   _ \n"
                + "| |\\/| | | |/ / | | |\n"
                + "| |  | | |   <| |_| |\n"
                + "|_|  |_|_|_|\\_\\\\__,_|\n";
        String line = "____________________________________________________________";
        System.out.println(line + "\nHello! I'm\n" + logo);
        System.out.println("What can I do for you?\n" + line);


        String user_in;
        do { 
            user_in = sc.nextLine();
            
            // list 
            if (user_in.equals("list")) {
                // print list
                System.out.println(line);
                for (int i = 0; i < list.size(); i ++) {
                    System.out.println((i+1) + ". " + list.get(i));
                }      
                System.out.println(line);

            // unmark
            } else if (user_in.contains("unmark ")) {
                try {
                    // get task num
                    String[] user_ins = user_in.split(" ");
                    int user_in_num = Integer.parseInt(user_ins[1]) - 1;
                    list.set(user_in_num, list.get(user_in_num).unmark());

                    // output
                    System.out.println(line + "\nOK, I've marked this task as not done yet:");
                    System.out.println("  " + list.get(user_in_num) + "\n" + line);
                    writeListToFile(list);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: unmark <index in list>\n" + line);
                }
            
            // mark
            } else if (user_in.contains("mark ")) {
                try {
                    // get task num
                    String[] user_ins = user_in.split(" ");
                    int user_in_num = Integer.parseInt(user_ins[1]) - 1;
                    list.set(user_in_num, list.get(user_in_num).mark());

                    // output
                    System.out.println(line + "\nNice! I've marked this task as done:");
                    System.out.println("  " + list.get(user_in_num) + "\n" + line);
                    writeListToFile(list);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: mark <index in list>\n" + line);
                }

            // todo
            } else if (user_in.contains("todo")) {
                try {
                    if (user_in.substring(4).isEmpty()) {
                        throw new EmptyStringException("String cannot be empty");
                    }
                    ToDos td = new ToDos(user_in.substring(5));
                    list.add(td);

                    // output
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println("  " + td);
                    System.out.println("Now you have " + list.size() +  " tasks in the list.\n" + line);
                    writeListToFile(list);
                } catch (EmptyStringException es) {
                    System.out.println(line + "\n" + es.getMessage() + ", format: todo <event>\n" + line);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: todo <event>\n" + line);
                }
            
            // deadline
            } else if (user_in.contains("deadline ")) {
                try {
                    String[] parts = user_in.substring(9).split("/by");
                    Deadlines dd = new Deadlines(parts[0].trim(), parts[1].trim());
                    list.add(dd);

                    // output
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println("  " + dd);
                    System.out.println("Now you have " + list.size() +  " tasks in the list.\n" + line);
                    writeListToFile(list);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: deadline <event> /by <date/time>\n" + line);
                }
            
            // event
            } else if (user_in.contains("event ")) {
                try {
                    String[] parts = user_in.substring(6).split("/from|/to");
                    Events ee = new Events(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    list.add(ee);

                    // output
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println("  " + ee);
                    System.out.println("Now you have " + list.size() +  " tasks in the list.\n" + line);
                    writeListToFile(list);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: event <event> /from <date/time> /to <date/time>\n" + line);
                }

            // delete
            } else if (user_in.contains("delete ")) {
                try {
                    // get task num
                    String[] user_ins = user_in.split(" ");
                    int user_in_num = Integer.parseInt(user_ins[1]) - 1;
                    Task tt = list.remove(user_in_num);

                    // output
                    System.out.println(line + "\nNoted. I've removed this task:");
                    System.out.println("  " + tt + "\nNow you have " + list.size() + " tasks in the list.\n" + line);
                    writeListToFile(list);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: delete <index in list>\n" + line);
                }
            
            // bye
            } else if (user_in.equals("bye") == false) {
                // invalid input
                System.out.println(line + "\nInvalid input please enter list, todo, deadline or event");
                System.out.println("Type \"bye\" to exit :)\n" + line);
            }
        } while (user_in.equals("bye") == false);


        System.out.println(line + "\nBye. Hope to see you again soon!\n" + line);
        sc.close();
    }
}
