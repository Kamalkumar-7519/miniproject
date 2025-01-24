import java.io.*;
import java.util.Scanner;
import java.util.Base64;

public class simplediary{
    private static final String DATA_FILE = "diary.txt"; // File to store user data and notes
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Personal Diary!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (choice == 1) {
            register();
        } else if (choice == 2) {
            if (login()) {
                diaryMenu();
            } else {
                System.out.println("Invalid username or password.");
            }
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private static void register() throws IOException {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        // Save credentials to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE, true))) {
            writer.write(username + ":" + encode(password) + "\n");
        }
        System.out.println("Registration successful!");
    }

    private static boolean login() throws IOException {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username) && parts[1].equals(encode(password))) {
                    System.out.println("Login successful!");
                    return true;
                }
            }
        }
        return false;
    }

    private static void diaryMenu() throws IOException {
        while (true) {
            System.out.println("\nDiary Menu:");
            System.out.println("1. Add a Note");
            System.out.println("2. View Notes");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (choice == 1) {
                addNote();
            } else if (choice == 2) {
                viewNotes();
            } else if (choice == 3) {
                System.out.println("Logged out. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option, try again.");
            }
        }
    }

    private static void addNote() throws IOException {
        System.out.print("Enter your note: ");
        String note = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("notes.txt", true))) {
            writer.write(encode(note) + "\n");
        }
        System.out.println("Note saved!");
    }

    private static void viewNotes() throws IOException {
        System.out.println("Your Notes:");
        try (BufferedReader reader = new BufferedReader(new FileReader("notes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("- " + decode(line));
            }
        }
    }

    // Simple encoding (base64) for storing passwords and notes
    private static String encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    private static String decode(String encodedText) {
        return new String(Base64.getDecoder().decode(encodedText));
    }
}

