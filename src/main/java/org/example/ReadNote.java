package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class ReadNote {

    public static void toReadNote(Scanner scanner) {

        String noteToRead = NoteList.toGetNote(scanner);

        if (noteToRead == null) {
            System.out.println("No note selected to read.");
            return;
        }

        File file = new File(Main.folder, noteToRead);

        if (!file.exists()) {
            System.out.println("Note '" + noteToRead + "' does not exist.");
            return;
        }

        System.out.println("\n--- Content of Note: " + noteToRead + " ---");
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (lines.isEmpty()) {
                System.out.println("[Note is empty]");
            } else {
                for (String line : lines) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading note '" + noteToRead + "': " + e.getMessage());
        }
        System.out.println("------------------------------------");
    }
}
