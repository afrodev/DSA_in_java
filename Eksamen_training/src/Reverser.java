import java.util.*;

public class Reverser{
    public static void main(String[] args)
    {
        Stack<Character> stack = new Stack<Character>();
        String line;
        Scanner in = new Scanner(System.in);

        System.out.print("? ");
        line = in.nextLine();

        for (int i = 0; i < line.length(); i++)
            stack.push(line.charAt(i));

        ArrayList<Character> reversed = new ArrayList<>();

        while (!stack.isEmpty()) {
            char popped = stack.pop();
//            System.out.println("popped so far: " + popped);
            reversed.add(popped);
//            System.out.println("Reversed stack: " + reversed);
        }

        boolean isPalindrome = true;
        for (int i = 0; i < reversed.size(); i++) {
            if (line.charAt(i) != reversed.get(i)){
                isPalindrome = false;
                break;
            }


        }

        if (isPalindrome){
            System.out.println("Congrats! You have discovered the mighty palindrome.\n Kneel in front of its glory you mere mortal >BD");
        } else {
            System.out.println("Not a match :////////");
        }

        System.out.println("The End.");
    }
}