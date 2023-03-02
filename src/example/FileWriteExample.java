package example;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileWriteExample {
    public static void main(String[] args) {
        String filename = "newFile.txt";
        try {
            FileOutputStream fs = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
