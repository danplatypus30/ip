import java.util.ArrayList;
import java.util.Scanner;

public class Miku {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<Task>();

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
            if (user_in.equals("list")) {
                // print list
                System.out.println(line);
                for (int i = 0; i < list.size(); i ++) {
                    System.out.println((i+1) + ". " + list.get(i));
                }      
                System.out.println(line);
            } else if (user_in.contains("unmark ")) {
                try {
                    // get task num
                    String[] user_ins = user_in.split(" ");
                    int user_in_num = Integer.parseInt(user_ins[1]) - 1;
                    list.set(user_in_num, list.get(user_in_num).unmark());

                    // output
                    System.out.println(line + "\nOK, I've marked this task as not done yet:");
                    System.out.println("  " + list.get(user_in_num) + "\n" + line);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: unmark <index in list>\n" + line);
                }
            } else if (user_in.contains("mark ")) {
                try {
                    // get task num
                    String[] user_ins = user_in.split(" ");
                    int user_in_num = Integer.parseInt(user_ins[1]) - 1;
                    list.set(user_in_num, list.get(user_in_num).mark());

                    // output
                    System.out.println(line + "\nNice! I've marked this task as done:");
                    System.out.println("  " + list.get(user_in_num) + "\n" + line);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: mark <index in list>\n" + line);
                }
            } else if (user_in.contains("todo")) {
                try {
                    if (user_in.substring(4).isEmpty()) {
                        throw new EmptyStringException("String cannot be empty");
                    }
                    ToDos td = new ToDos(user_in.substring(4));
                    list.add(td);

                    // output
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println("  " + td);
                    System.out.println("Now you have " + list.size() +  " tasks in the list.\n" + line);
                } catch (EmptyStringException es) {
                    System.out.println(line + "\n" + es.getMessage() + ", format: todo <event>\n" + line);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: todo <event>\n" + line);
                }
            } else if (user_in.contains("deadline ")) {
                try {
                    String[] parts = user_in.substring(9).split("/by");
                    Deadlines dd = new Deadlines(parts[0].trim(), parts[1].trim());
                    list.add(dd);

                    // output
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println("  " + dd);
                    System.out.println("Now you have " + list.size() +  " tasks in the list.\n" + line);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: deadline <event> /by <date/time>\n" + line);
                }
            } else if (user_in.contains("event ")) {
                try {
                    String[] parts = user_in.substring(6).split("/from|/to");
                    Events ee = new Events(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    list.add(ee);

                    // output
                    System.out.println(line + "\nGot it. I've added this task:");
                    System.out.println("  " + ee);
                    System.out.println("Now you have " + list.size() +  " tasks in the list.\n" + line);
                } catch (Exception e) {
                    System.out.println(line + "\nInvalid input, format: event <event> /from <date/time> /to <date/time>\n" + line);
                }
            } else if (user_in.equals("bye") == false) {
                // invalid input
                System.out.println(line + "\nInvalid input please enter todo, deadline or event\n" + line);
            }
            /*
            else if (user_in.equals("bye") == false) {
                System.out.println(line + "\nadded: " + user_in + "\n" + line);
                list.add(new Task(user_in));
            } */
        } while (user_in.equals("bye") == false);


        System.out.println(line + "\nBye. Hope to see you again soon!\n" + line);
        sc.close();
    }
}
