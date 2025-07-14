package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static String folder;

    public static String folderCreation(Scanner scanner){
        System.out.println("Enter the folder Name for your notes (e.g., 'my_notes'):");
        String folderName = scanner.nextLine();
        File dir = new File(folderName);

        if (dir.exists()) {
            if (dir.isDirectory()) {
                System.out.println("Folder '" + folderName + "' already exists. Continuing with this folder.");
            } else {
                System.out.println("A file with the name '" + folderName + "' exists. Cannot create folder. Please choose a different name.");
                return null;
            }
        } else {
            if (dir.mkdir()) {
                System.out.println("Folder Created with name '" + folderName + "'.");
            } else {
                System.out.println("Failed to create folder '" + folderName + "'. Check permissions or path.");
                return null;
            }
        }
        return folderName;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Note Application");

        folder = folderCreation(scanner);
        if (folder == null) {
            System.out.println("Unable to set up notes folder. Exiting application.");
            scanner.close();
            return;
        }

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Create Note");
            System.out.println("2. Read Note");
            System.out.println("3. Update Note");
            System.out.println("4. Delete Note");
            System.out.println("5. List Notes");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1-6).");
                scanner.nextLine();
                continue;
            }


            switch (option) {
                case 1:
                    CreateNote.toCreateNote(scanner);
                    break;
                case 2:
                    ReadNote.toReadNote(scanner);
                    break;
                case 3:
                    UpdateNote.toUpdateNote(scanner);
                    break;
                case 4:
                    DeleteNote.toDeleteNote(scanner);
                    break;
                case 5:
                    NoteList.toDisplayNoteList();
                    break;
                case 6:
                    System.out.println("Exiting NoteApplication. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}
