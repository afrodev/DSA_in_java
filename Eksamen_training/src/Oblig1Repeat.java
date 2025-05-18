public class Oblig1Repeat {
    // kryptere tekststrenger med både stack og kø
    // Krypter en streng S med n tegn

    public String englishRot13(String textToRotate){
        StringBuilder rotatedText = new StringBuilder();
        for (int i = 0; i < textToRotate.length(); i++){
            // må konvertere input til char
            char letter = textToRotate.charAt(i);
            if (letter >= 'a' && letter <= 'm'){
                letter += 13;
            } else if (letter >= 'm' && letter <= 'z'){
                letter -= 13;
            } else if (letter >= 'A' && letter <= 'M') {
                letter += 13;
            } else if (letter >= 'M' && letter <= 'Z') {
                letter -= 13;
            }

            rotatedText.append(letter);
        }
      return rotatedText.toString();
    }

    public static void Main(String[] args){

        Oblig1Repeat ob = new Oblig1Repeat();
        String rot13Result = ob.englishRot13("abc");
    }
}
