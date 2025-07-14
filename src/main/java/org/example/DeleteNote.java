package org.example;

import java.io.File;
import java.util.Scanner;

public class DeleteNote {

    public static void toDeleteNote(Scanner scanner) {

        String noteTitle = NoteList.toGetNote(scanner);

        if (noteTitle == null) {
            System.out.println("No note selected for deletion.");
            return;
        }

        File file = new File(Main.folder, noteTitle);
        if (file.delete()) {
            System.out.println("Note '" + noteTitle + "' deleted successfully.");
        } else {
            System.out.println("Failed to delete note '" + noteTitle + "'. It might not exist or there was an error.");
        }
    }
}
