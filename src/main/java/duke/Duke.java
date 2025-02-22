package duke;

import java.io.IOException;
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
     *
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
        str += "Current expenses is $" + list.getExpenses() + "\n";
        return str;
    }

    /**
     * Generate output for unmark command
     *
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
     *
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
     *
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
     *
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
     *
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
     *
     * @param userIn
     * @return
     */
    private String delete(String userIn, double expenses) {
        try {
            // get task num
            String[] userIns = userIn.split(" ");
            int userInNum = Integer.parseInt(userIns[1]) - 1;
            Task tt = list.getList().get(userInNum);
            list = list.remove(userInNum);
            list = list.addExpenses(expenses - tt.getExpenses());

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
     *
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
     *
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
     * Capture the expenses for this user input
     * It can only be positive, negative values are invalid
     * Keep track of expenses, will not account for earnings
     * @param userIn
     * @return
     */
    private double getExpenses(String userIn, double expenses) {
        // Check if the input contains /expenses and extract the value
        if (userIn.contains("/expenses")) {
            try {
                String[] parts = userIn.split("/expenses");
                expenses = Double.parseDouble(parts[1].trim().split("\\s+")[0]);
                if (expenses < 0.0) {
                    return -1.0;
                }
            } catch (NumberFormatException e) {
                return -1.0;
            }
        }
        return expenses;
    }

    /**
     * Save the expenses to the list and write to file
     * @param expenses
     * @return
     */
    private String saveExpenses (double expenses, String output) {
        list = list.addExpenses(expenses);
        try {
            Storage.writeListToFile(list);
        } catch (IOException e) {
            return "Error writing to file\n";
        }
        return output;
    }

    /**
     * Runs a while loop that interacts with the user.
     */
    public String interact(String userIn) {
        // handle null and whitespace inputs
        if (userIn == null || userIn.trim().isEmpty()) {
            return this.invalid(); // Handle empty input
        }

        // Trim input and extract the first word as command
        // split("\\s+") splits by one or more whitespace characters
        // will capture entire input if no space
        // limit 2 means maximum 2 resulting array elements, one split only
        String command = userIn.trim().split("\\s+", 2)[0];
        String output = "";
        double expenses = this.list.getExpenses();
        if (expenses == -1.0) {
            return "Invalid input, format: <command> /expenses <value, in double format, >= 0.0>\n";
        }

        switch (command) {
            case "list":
                output = this.list();
                break;
            case "unmark":
                output = this.unmark(userIn);
                break;
            case "mark":
                output = this.mark(userIn);
                break;
            case "todo":
                output = this.todo(userIn);
                expenses += this.getExpenses(userIn, expenses);
                break;
            case "deadline":
                output = this.deadline(userIn);
                expenses += this.getExpenses(userIn, expenses);
                break;
            case "event":
                output = this.event(userIn);
                expenses += this.getExpenses(userIn, expenses);
                break;
            case "delete":
                output = this.delete(userIn, expenses);
                return output;
            case "find":
                output = this.find(userIn);
                break;
            case "bye":
                output = "Bye. Hope to see you again soon!\n";
                break;
            default:
                output = this.invalid();
        }

        // Add expenses to the list and write to file
        saveExpenses(expenses, output);

        return output;
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
