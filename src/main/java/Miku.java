import java.util.ArrayList;
import java.util.Scanner;

public class Miku {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();

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
                list.clear();
            } else if (user_in.equals("bye") == false) {
                System.out.println(line + "\nadded: " + user_in + "\n" + line);
                list.add(user_in);
            }
        } while (user_in.equals("bye") == false);


        System.out.println(line + "\nBye. Hope to see you again soon!\n" + line);
        sc.close();
    }
}
