import java.util.Scanner;

public class Miku {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String logo = " __  __ _ _          \n"
                + "|  \\/  (_) | ___   _ \n"
                + "| |\\/| | | |/ / | | |\n"
                + "| |  | | |   <| |_| |\n"
                + "|_|  |_|_|_|\\_\\\\__,_|\n";
        String line = "____________________________________________________________";
        System.out.println(line);
        System.out.println("Hello! I'm\n" + logo);
        System.out.println("What can I do for you?");
        System.out.println(line);
        String user_in = "";
        do { 
            user_in = sc.nextLine();
            if (user_in.equals("bye") == false) {
                System.out.println(line + "\n" + user_in + "\n" + line);
            }
        } while (user_in.equals("bye") == false);
        System.out.println(line + "\nBye. Hope to see you again soon!\n" + line);
        sc.close();
    }
}
