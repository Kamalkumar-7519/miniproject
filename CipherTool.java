import java.util.Scanner;

public class CipherTool {

    public static String caesarCipher(String text, int shift, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        shift = encrypt ? shift : -shift;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + shift + 26) % 26 + base);
            }
            result.append(c);
        }

        return result.toString();
    }

    public static String vigenereCipher(String text, String key, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        key = key.toLowerCase();
        int keyIndex = 0;

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int shift = key.charAt(keyIndex % key.length()) - 'a';
                shift = encrypt ? shift : -shift;
                c = (char) ((c - base + shift + 26) % 26 + base);
                keyIndex++;
            }
            result.append(c);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an algorithm: 1) Caesar Cipher 2) Vigenère Cipher");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the text: ");
        String text = scanner.nextLine();

        System.out.print("Encrypt or Decrypt (e/d): ");
        boolean encrypt = scanner.next().equalsIgnoreCase("e");

        if (choice == 1) {
            System.out.print("Enter shift value: ");
            int shift = scanner.nextInt();
            System.out.println("Result: " + caesarCipher(text, shift, encrypt));
        } else if (choice == 2) {
            System.out.print("Enter key: ");
            String key = scanner.next();
            System.out.println("Result: " + vigenereCipher(text, key, encrypt));
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}

