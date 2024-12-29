import java.awt.*;
import java.util.*;
public class Main {
    public static void main(String[] arg) {
        Scanner input = new Scanner(System.in);
        Boolean bot = false;
        System.out.println("Bot or no bot? (Y OR N)");
        String choice = input.next().toLowerCase();
        while (!choice.equals("y") && !choice.equals("n")) {
            System.out.println("Bot or no bot? (Y OR N)");
            choice = input.next();
        }
        if (choice.equals("y")) bot = false;
        else bot = true;

        @SuppressWarnings("unused")
        App app = new App(new Player(-20, -20, Color.ORANGE, 2), bot);
    }
}
