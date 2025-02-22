package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Program that will respond to the user
 */
public class Duke {
    private TaskList list;

    /**
     * Constructor for Duke.
     * 
     * @param list
     * @param storage
     */
    public Duke(TaskList list) {
        this.list = list;
    }

    /**
     * Generates a response for the user's chat message
     */
    public String getResponse(String input) {
        return interact(input);
    }

    /**
     * Generates output for list command.
     * @return
     */
    private String list() {
        String str = "";
        // print list
        for (int i = 0; i < list.size(); i++) {
            str += (i + 1) + ". " + list.getList().get(i) + "\n";
        }
        if (list.size() == 0) {
            str += "No tasks in the list\n";
        }
        return str;
    }

    /**
     * Generate output for unmark command
     * @param userIn
     * @return
     */
    private String unmark(String userIn) {
        try {
            // get task num
            String[] userIns = userIn.split(" ");
            int userInNum = Integer.parseInt(userIns[1]) - 1;
            list = list.set(userInNum, list.getList().get(userInNum).unmark());

            // output
            String str = "";
            str += "OK, I've marked this task as not done yet:\n";
            str += "  " + list.getList().get(userInNum) + "\n";
            Storage.writeListToFile(list);
            return str;
        } catch (Exception e) {
            return "Invalid input, format: unmark <index in list>\n";
        }
    }

    /**
     * Generates output for mark command
     * @param userIn
     * @return
     */
    private String mark(String userIn) {
        try {
            // get task num
            String[] userIns = userIn.split(" ");
            int userInNum = Integer.parseInt(userIns[1]) - 1;
            list = list.set(userInNum, list.getList().get(userInNum).mark());

            // output
            String str = "";
            str += "Nice! I've marked this task as done:";
            str += "  " + list.getList().get(userInNum) + "\n";
            Storage.writeListToFile(list);
            return str;
        } catch (Exception e) {
            return "Invalid input, format: mark <index in list>\n";
        }
    }

    /**
     * Generate output for todo command
     * @param userIn
     * @return
     */
    private String todo(String userIn) {
        try {
            if (userIn.substring(4).isEmpty()) {
                throw new EmptyStringException("String cannot be empty");
            }
            ToDos td = new ToDos(userIn.substring(5));
            list = list.add(td);

            // output
            String str = "";
            str += "Got it. I've added this task:\n";
            str += " " + " " + td + "\n";
            str += "Now you have " + list.size() + " tasks in the list.\n";
            Storage.writeListToFile(list);
            return str;
        } catch (EmptyStringException es) {
            return es.getMessage() + ", format: todo <event>\n";
        } catch (Exception e) {
            return "Invalid input, format: todo <event>\n";
        }
    }

    /**
     * Generate output for deadline command
     * @param userIn
     * @return
     */
    private String deadline(String userIn) {
        try {
            String[] parts = userIn.substring(9).split("/by");
            LocalDateTime dt = Task.convert(parts[1].trim());
            Deadlines dd = new Deadlines(parts[0].trim(), dt);
            list = list.add(dd);

            // output
            String str = "";
            str += "Got it. I've added this task:\n";
            str += " " + " " + dd + "\n";
            str += "Now you have " + list.size() + " tasks in the list.\n";
            Storage.writeListToFile(list);
            return str;
        } catch (DateTimeParseException dte) {
            String str = "";
            str += "<date/time> has to be MMM d(th) yyyy hr(pm/am)\n";
            str += "e.g, Aug 22th 2025 5pm\n";
            return str;
        } catch (Exception e) {
            return "Invalid input, format: deadline <event> /by <date/time>\n";
        }
    }

    /**
     * Generate output for event command
     * @param userIn
     * @return
     */
    private String event(String userIn) {
        try {
            String[] parts = userIn.substring(6).split("/from|/to");
            LocalDateTime dt = Task.convert(parts[1].trim());
            LocalDateTime dt2 = Task.convert(parts[2].trim());
            Events ee = new Events(parts[0].trim(), dt, dt2);
            list = list.add(ee);

            // output
            String str = "";
            str += "Got it. I've added this task:\n";
            str += " " + " " + ee + "\n";
            str += "Now you have " + list.size() + " tasks in the list.\n";
            Storage.writeListToFile(list);
            return str;
        } catch (DateTimeParseException dte) {
            String str = "<date/time> has to be MMM d(th) yyyy hr(pm/am)\n";
            str += "e.g, Aug 22th 2025 5pm\n";
            return str;
        } catch (Exception e) {
            return "Invalid input, format: event <event> /from <date/time> "
                    + "/to <date/time>\n";
        }
    }

    /**
     * Generate output for delete command
     * @param userIn
     * @return
     */
    private String delete(String userIn) {
        try {
            // get task num
            String[] userIns = userIn.split(" ");
            int userInNum = Integer.parseInt(userIns[1]) - 1;
            Task tt = list.getList().get(userInNum);
            list = list.remove(userInNum);

            // output
            String str = "";
            str += "Noted. I've removed this task:\n";
            str += "  " + tt + "\nNow you have " + list.size() + " tasks in the list.\n";
            Storage.writeListToFile(list);
            return str;
        } catch (Exception e) {
            return "Invalid input, format: delete <index in list>\n";
        }
    }

    /**
     * Generate output for find command
     * @param userIn
     * @return
     */
    private String find(String userIn) {
        String[] userIns = userIn.split(" ");
        String userDesc = userIns[1];
        int i = 1;
        int count = 0;
        String str = "";
        while (count < list.size()) {
            String desc = list.getList().get(count).toString();
            if (desc.contains(userDesc)) {
                str += (i) + ". " + list.getList().get(count) + "\n";
                i++;
            }
            count++;
        }
        return str;
    }

    /**
     * Generate output for invalid command
     * @return
     */
    private String invalid() {
        // invalid input
        String str = "";
        str += "Invalid input please enter list, todo, deadline, event, find or delete\n";
        str += "Type \"bye\" to exit :)\n";
        return str;
    }

    /**
     * Runs a while loop that interacts with the user.
     */
    public String interact(String userIn) {
        if (userIn.equals("list")) {
            return this.list();
        } else if (userIn.startsWith("unmark")) {
            return this.unmark(userIn);
        } else if (userIn.startsWith("mark")) {
            return this.mark(userIn);
        } else if (userIn.startsWith("todo")) {
            return this.todo(userIn);
        } else if (userIn.startsWith("deadline")) {
            return this.deadline(userIn);
        } else if (userIn.startsWith("event")) {
            return this.event(userIn);
        } else if (userIn.startsWith("delete")) {
            return this.delete(userIn);
        } else if (userIn.startsWith("find")) {
            return this.find(userIn);
        } else if (userIn.startsWith("bye") == false) {
            return this.invalid();
        }
        return "Bye. Hope to see you again soon!\n";
    }

    /**
     * Shows the welcome message.
     */
    public static String showWelcome() {
        String logo = " __  __ _ _          \n"
                + "|  \\/  (_) | ___   _ \n"
                + "| |\\/| | | |/ / | | |\n"
                + "| |  | | |   <| |_| |\n"
                + "|_|  |_|_|_|\\_\\\\__,_|\n";
        String str1 = "\nHello! I'm\n" + logo;
        String str2 = "What can I do for you?\n";
        return str1 + str2;
    }
}
