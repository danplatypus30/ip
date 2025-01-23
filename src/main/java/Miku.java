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
                    String status = "[" + list.get(i).getStatusIcon() + "]";
                    System.out.println((i+1) + "." + status + " " + list.get(i));
                }      
                System.out.println(line);
            } else if (user_in.contains("unmark ")) {
                // get task num
                String[] user_ins = user_in.split(" ");
                int user_in_num = Integer.parseInt(user_ins[1]) - 1;
                list.set(user_in_num, list.get(user_in_num).unmark());

                // output
                System.out.println(line + "\nOK, I've marked this task as not done yet:");
                System.out.println("  [ ] " + list.get(user_in_num) + "\n" + line);
            } else if (user_in.contains("mark ")) {
                // get task num
                String[] user_ins = user_in.split(" ");
                int user_in_num = Integer.parseInt(user_ins[1]) - 1;
                list.set(user_in_num, list.get(user_in_num).mark());

                // output
                System.out.println(line + "\nNice! I've marked this task as done:");
                System.out.println("  [X] " + list.get(user_in_num) + "\n" + line);
            } else if (user_in.equals("bye") == false) {
                System.out.println(line + "\nadded: " + user_in + "\n" + line);
                list.add(new Task(user_in));
            }
        } while (user_in.equals("bye") == false);


        System.out.println(line + "\nBye. Hope to see you again soon!\n" + line);
        sc.close();
    }
}
