package duke;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * UI class handles the interaction with the user.
 */
public class UI {
    private static final String LINE = "____________________________________________________________";
    private TaskList list;
    private Storage storage;

    /**
     * Constructor for UI class.
     * @param list
     * @param storage
     */
    public UI(TaskList list, Storage storage) {
        this.list = list;
        this.storage = storage;
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        String logo = " __  __ _ _          \n"
                + "|  \\/  (_) | ___   _ \n"
                + "| |\\/| | | |/ / | | |\n"
                + "| |  | | |   <| |_| |\n"
                + "|_|  |_|_|_|\\_\\\\__,_|\n";
        System.out.println(LINE + "\nHello! I'm\n" + logo);
        System.out.println("What can I do for you?\n" + LINE);
    }

    /**
     * Displays a line
     */
    public void line() {
        System.out.println(LINE);
    }

    /**
     * Runs a while loop that interacts with the user.
     */
    public void interact() {
        Scanner sc = new Scanner(System.in);
        String userIn;
        do {
            userIn = sc.nextLine();

            // list
            if (userIn.equals("list")) {
                // print list
                line();
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.getList().get(i));
                }
                line();

            // unmark
            } else if (userIn.startsWith("unmark ")) {
                try {
                    // get task num
                    String[] userIns = userIn.split(" ");
                    int userInNum = Integer.parseInt(userIns[1]) - 1;
                    list = list.set(userInNum, list.getList().get(userInNum).unmark());

                    // output
                    System.out.println(LINE + "\nOK, I've marked this task as not done yet:");
                    System.out.println("  " + list.getList().get(userInNum) + "\n" + LINE);
                    Storage.writeListToFile(list);
                } catch (Exception e) {
                    System.out.println(LINE + "\nInvalid input, format: unmark <index in list>\n" + LINE);
                }

            // mark
            } else if (userIn.startsWith("mark ")) {
                try {
                    // get task num
                    String[] userIns = userIn.split(" ");
                    int userInNum = Integer.parseInt(userIns[1]) - 1;
                    list = list.set(userInNum, list.getList().get(userInNum).mark());

                    // output
                    System.out.println(LINE + "\nNice! I've marked this task as done:");
                    System.out.println("  " + list.getList().get(userInNum) + "\n" + LINE);
                    Storage.writeListToFile(list);
                } catch (Exception e) {
                    System.out.println(LINE + "\nInvalid input, format: mark <index in list>\n" + LINE);
                }

            // todo
            } else if (userIn.startsWith("todo")) {
                try {
                    if (userIn.substring(4).isEmpty()) {
                        throw new EmptyStringException("String cannot be empty");
                    }
                    ToDos td = new ToDos(userIn.substring(5));
                    list = list.add(td);

                    // output
                    System.out.println(LINE + "\nGot it. I've added this task:");
                    System.out.println(" " + " " + td);
                    System.out.println("Now you have " + list.size() + " tasks in the list.\n" + LINE);
                    Storage.writeListToFile(list);
                } catch (EmptyStringException es) {
                    System.out.println(LINE + "\n" + es.getMessage() + ", format: todo <event>\n" + LINE);
                } catch (Exception e) {
                    System.out.println(LINE + "\nInvalid input, format: todo <event>\n" + LINE);
                }

            // deadline
            } else if (userIn.startsWith("deadline ")) {
                try {
                    String[] parts = userIn.substring(9).split("/by");
                    LocalDateTime dt = Task.convert(parts[1].trim());
                    Deadlines dd = new Deadlines(parts[0].trim(), dt);
                    list = list.add(dd);

                    // output
                    System.out.println(LINE + "\nGot it. I've added this task:");
                    System.out.println(" " + " " + dd);
                    System.out.println("Now you have " + list.size() + " tasks in the list.\n" + LINE);
                    Storage.writeListToFile(list);
                } catch (DateTimeParseException dte) {
                    System.out.println(LINE + "\n<date/time> has to be MMM d(th) yyyy hr(pm/am)");
                    System.out.println("e.g, Aug 22th 2025 5pm\n" + LINE);
                } catch (Exception e) {
                    System.out.println(LINE + "\nInvalid input, format: deadline <event> /by <date/time>\n" + LINE);
                }

            // event
            } else if (userIn.startsWith("event ")) {
                try {
                    String[] parts = userIn.substring(6).split("/from|/to");
                    LocalDateTime dt = Task.convert(parts[1].trim());
                    LocalDateTime dt2 = Task.convert(parts[2].trim());
                    Events ee = new Events(parts[0].trim(), dt, dt2);
                    list = list.add(ee);

                    // output
                    System.out.println(LINE + "\nGot it. I've added this task:");
                    System.out.println(" " + " " + ee);
                    System.out.println("Now you have " + list.size() + " tasks in the list.\n" + LINE);
                    Storage.writeListToFile(list);
                } catch (DateTimeParseException dte) {
                    System.out.println(LINE + "\n<date/time> has to be MMM d(th) yyyy hr(pm/am)");
                    System.out.println("e.g, Aug 22th 2025 5pm\n" + LINE);
                } catch (Exception e) {
                    System.out.println(LINE + "\nInvalid input, format: event <event> /from <date/time> "
                        + "/to <date/time>\n" + LINE);
                }

            // delete
            } else if (userIn.startsWith("delete ")) {
                try {
                    // get task num
                    String[] userIns = userIn.split(" ");
                    int userInNum = Integer.parseInt(userIns[1]) - 1;
                    Task tt = list.getList().get(userInNum);
                    list = list.remove(userInNum);

                    // output
                    System.out.println(LINE + "\nNoted. I've removed this task:");
                    System.out.println("  " + tt + "\nNow you have " + list.size() + " tasks in the list.\n" + LINE);
                    Storage.writeListToFile(list);
                } catch (Exception e) {
                    System.out.println(LINE + "\nInvalid input, format: delete <index in list>\n" + LINE);
                }

            // find
            } else if (userIn.startsWith("find")) {
                line();
                String[] userIns = userIn.split(" ");
                String userDesc = userIns[1];
                int i = 1;
                int count = 0;
                while (count < list.size()) {
                    String desc = list.getList().get(count).toString();
                    if (desc.contains(userDesc)) {
                        System.out.println((i) + ". " + list.getList().get(count));
                        i++;
                    }
                    count++;
                }
                line();

            // bye
            } else if (userIn.startsWith("bye") == false) {
                // invalid input
                System.out.println(LINE + "\nInvalid input please enter list, todo, deadline, event or find");
                System.out.println("Type \"bye\" to exit :)\n" + LINE);
            }
        } while (userIn.startsWith("bye") == false);


        System.out.println(LINE + "\nBye. Hope to see you again soon!\n" + LINE);
        sc.close();
    }
}
