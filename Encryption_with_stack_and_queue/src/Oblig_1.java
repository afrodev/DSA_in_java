import java.util.*;

public class Oblig_1 {
    /*
    Queue: MON
    Stack: ZER

    Hypothesis for Encryption without ROT13: RMEOZN
    Check if correct when the function is made without ROT13
    */

    public static String stringROT13(String S)
    {
        char[] C = S.toCharArray();
        for (int i = 0; i < S.length(); i++)
        {
            char c = C[i];
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            C[i] = c;
        }
        return String.valueOf(C);
    }

    public static String encrypt(String S){
        // Take in whatever ROT13 returns, as a param and save as variable. Both are Strings
        // P.S. Note that for Strings with odd number of characters,
        // the second-half String will get the benefit of the extra character.

        // then add the first half into a Queue and the second into a Stack.
        // Make an empty String to hold the encrypterd version
        // To actually encrypt, pop from the stack and add into String, then dequeue and add into String
        // This will give a mix of First in First Out and LAst in Last Out

        String rotatedString = stringROT13(S);

        int midPoint = rotatedString.length() / 2;

        String pt1 = rotatedString.substring(0, (rotatedString.length()/2));
        String pt2 = rotatedString.substring(rotatedString.length() / 2);

        Queue<Character> myQueue = new ArrayDeque<>();

        for (char element : pt1.toCharArray()){
            myQueue.add(element);
        }

        Stack<Character> myStack = new Stack<>();

        for (char element : pt2.toCharArray()){
            myStack.push(element);
        }

        // for each element in total, pop stack and add to string
        // alternate, then for each element in queue, dequeue and add to string
        // I guess I need to make a while loop but idk based on what condition yet
        // Could be the lenght of both parts of the OG string!?!? Almost, but causes errors when one is empty
        // The correct condition is while stack and queue ARE NOT EMPTY

        String encryptedString = "";

        // IMPORTANT: putting !myQueue.isEmpty() first because it would be shorter when OG string is odd numbered
        // This avoids the loop stopping when stack is emp
        while (!myStack.isEmpty() && !myQueue.isEmpty()){
            encryptedString += myStack.pop();
            encryptedString += myQueue.remove();
        }

        System.out.println("The decrypted string is: " + encryptedString);
        return encryptedString;
    }

    public static String decrypt(String S){
        String unmixedString = "";
        String even = "";
        String odd = "";
        char[] C = S.toCharArray();
        for (int i = 0; i < C.length; i++ ){
            if (i % 2 == 0) {
                even += C[i];
                System.out.println("Even string so far: " + even);
            }
            else{
                odd += C[i];
                System.out.println("Odd string so far: " + odd);
            }
        }

        // Reverse the 'even' string (which holds the reversed second half)
        String reversedEven = new StringBuilder(even).reverse().toString();
        System.out.println("Reverse even string idek: " + reversedEven);


        unmixedString = odd + reversedEven;
        System.out.println("Just unmixed string. Should be Xneznaa-Tuvn: " + unmixedString);

        // since ROT13 is symmetrical, running it again should return original value
        unmixedString = stringROT13(unmixedString);
        return unmixedString;
    }

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter word to decrypt");

        String normalString = userInput.nextLine();

        String finalEncryption = encrypt(normalString);
        // System.out.println(finalEncryption);

        // Karmann-Ghia
        System.out.println(decrypt(finalEncryption));
        // System.out.println(stringROT13(decrypt(finalEncryption)));


    }

    // NO CHATGPT BABY

}
