package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class UpdateNote {

    public static void toUpdateNote(Scanner scanner) {

        String noteToUpdate = NoteList.toGetNote(scanner);

        if (noteToUpdate == null) {
            System.out.println("No note selected or available for update.");
            return;
        }

        while (true) {
            System.out.println("\n--- Update Note Menu for '" + noteToUpdate + "' ---");
            System.out.println("1. Rename Note");
            System.out.println("2. Update Note Content");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choose;
            try {
                choose = scanner.nextInt();
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1-3).");
                scanner.nextLine();
                continue;
            }

            switch (choose) {
                case 1:
                    String newTitle;
                    while (true) {
                        System.out.print("Enter the new note title (4-20 alphanumeric characters): ");
                        newTitle = scanner.nextLine().trim();
                        if (Utils.isValidInput(newTitle)) {
                            toRenameNote(noteToUpdate, newTitle);
                            noteToUpdate = newTitle;
                            break;
                        } else {
                            System.out.println("Invalid note title. Please try again.");
                        }
                    }
                    break;
                case 2:
                    toUpdateNoteBody(scanner, noteToUpdate);
                    break;
                case 3:
                    System.out.println("Exiting from Update Note options.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    public static void toRenameNote(String oldName, String newName) {
        File oldFile = new File(Main.folder, oldName);
        File newFile = new File(Main.folder, newName);

        if (newFile.exists()) {
            System.out.println("A note with the name '" + newName + "' already exists. Cannot rename.");
            return;
        }

        if (oldFile.renameTo(newFile)) {
            System.out.println("Note '" + oldName + "' renamed to '" + newName + "' successfully.");
        } else {
            System.out.println("Failed to rename the note '" + oldName + "'. Make sure it exists and you have permissions.");
        }
    }

    public static void toUpdateNoteBody(Scanner scanner, String noteName) {
        File fileName = new File(Main.folder, noteName);

        if (!Files.exists(fileName.toPath())) {
            System.out.println("Note '" + noteName + "' not found.");
            return;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(fileName.toPath());
        } catch (IOException e) {
            System.err.println("Error reading note content: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n--- Current Note Content for '" + noteName + "' ---");
            if (lines.isEmpty()) {
                System.out.println("[Note is empty]");
            } else {
                for (int i = 0; i < lines.size(); i++) {
                    System.out.printf("%d: %s%n", i + 1, lines.get(i));
                }
            }
            System.out.println("------------------------------------");
            System.out.print("Enter line number to edit (0 to cancel, -1 to append new line): ");

            int lineNumber;
            try {
                lineNumber = scanner.nextInt();
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            if (lineNumber == 0) {
                System.out.println("Exiting content editor.");
                break;
            } else if (lineNumber == -1) {
                System.out.print("Enter content to append: ");
                String newContent = scanner.nextLine();
                lines.add(newContent);
                System.out.println("New line appended.");
            } else if (lineNumber >= 1 && lineNumber <= lines.size()) {
                System.out.print("Enter new content for line " + lineNumber + ": ");
                String newContent = scanner.nextLine();
                lines.set(lineNumber - 1, newContent);
                System.out.println("Line updated successfully.");
            } else {
                System.out.println("Invalid line number. Please enter a number between 1 and " + lines.size() + ", 0 to cancel, or -1 to append.");
                continue;
            }

            try {
                Files.write(fileName.toPath(), lines);
            } catch (IOException e) {
                System.err.println("Error saving note content: " + e.getMessage());
            }
        }
    }
}
