package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CreateNote {

    public static void toCreateNote(Scanner scanner) {

        String noteTitle;
        while (true) {
            System.out.print("Enter the note title (4-20 alphanumeric characters): ");
            noteTitle = scanner.nextLine().trim();

            if (Utils.isValidInput(noteTitle)) {
                File existingFile = new File(Main.folder, noteTitle);
                if (existingFile.exists()) {
                    System.out.println("A note with title '" + noteTitle + "' already exists. Please choose a different title.");
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid note title. It must contain only alphanumeric characters and be between 4 and 20 characters long.");
            }
        }

        File file = new File(Main.folder, noteTitle);
        try {
            if (file.createNewFile()) {
                System.out.println("Note created with title '" + noteTitle + "'.");
            } else {
                System.out.println("This note already exists. (Unexpected error - should have been caught earlier).");
                return;
            }
        } catch (IOException e) {
            System.out.println("Error creating note file: " + e.getMessage());
            return;
        }

        System.out.println("Write the body of the note. Type 'DONE' on a new line to finish and save.");
        StringBuilder bodyOfNote = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.trim().equalsIgnoreCase("Save")) {
                break;
            }
            bodyOfNote.append(line).append("\n");
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(bodyOfNote.toString());
            System.out.println("Note saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving note content: " + e.getMessage());
        }
    }
}
