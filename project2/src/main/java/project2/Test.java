package project1;
import java.util.Scanner;

public class Test {
    public static void main( String[] args )
    {   
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputwords = input.split(" ");
        String movie = inputwords[0];
        String occup = inputwords[1];
        System.out.println(movie);
        System.out.println(occup);
    }
}
