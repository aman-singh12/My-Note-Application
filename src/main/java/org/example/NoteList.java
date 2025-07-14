package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;


public class NoteList {

    private static List<File> getSortedNoteFiles() {
        File folder = new File(Main.folder);
        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("No notes found in the directory or directory does not exist.");
            return Collections.emptyList();
        }

        List<File> fileList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }

        Collections.sort(fileList, new DateCompare());
        return fileList;
    }


    public static String toGetNote(Scanner scanner) {
        List<File> fileList = getSortedNoteFiles();

        if (fileList.isEmpty()) {
            return null;
        }

        System.out.println("\n--- Select a Note ---");
        int count = 0;
        for (File file : fileList) {
            System.out.println(++count + ". " + file.getName());
        }

        int userInput;
        while (true) {
            System.out.print("Enter the number of the note you want to select (0 to cancel): ");
            try {
                userInput = scanner.nextInt();
                scanner.nextLine();

                if (userInput == 0) {
                    System.out.println("Note selection cancelled.");
                    return null;
                }
                if (userInput >= 1 && userInput <= fileList.size()) {
                    return fileList.get(userInput - 1).getName();
                } else {
                    System.out.println("Invalid note number. Please enter a number between 1 and " + fileList.size() + ".");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public static void toDisplayNoteList() {
        List<File> fileList = getSortedNoteFiles();

        if (fileList.isEmpty()) {
            System.out.println("No notes available to display.");
            return;
        }

        System.out.println("\n--- List of Notes ---");
        int count = 0;
        for (File file : fileList) {
            System.out.println(++count + ". " + file.getName());
        }
    }

    // This nested class for comparison is fine here
    public static class DateCompare implements Comparator<File> {
        @Override
        public int compare(File f1, File f2) {
            try {
                FileTime creationTime1 = Files.readAttributes(f1.toPath(), BasicFileAttributes.class).creationTime();
                FileTime creationTime2 = Files.readAttributes(f2.toPath(), BasicFileAttributes.class).creationTime();
                // Sort in descending order (newest first)
                return creationTime2.compareTo(creationTime1);
            } catch (IOException e) {
                System.err.println("Error in comparing files: " + e.getMessage());
                // If comparison fails, treat them as equal for sorting purposes
                return 0;
            }
        }
    }
}
