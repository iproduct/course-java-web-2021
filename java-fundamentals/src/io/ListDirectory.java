package io;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ListDirectory {
    public static List<File> listDir(String path, String namePart){
        File current = new File(path);
        if(current.isDirectory()) {
            return Arrays.asList(current.listFiles((dir, name) -> name.contains(namePart) ));
        }
        return null;
    }

    public static void main(String[] args) {
        listDir(".", ".txt").forEach(f -> System.out.printf("%-25s %5s %10d%n",
                f.getName(),
                f.isDirectory()? "<DIR>" : "", f.length()));
    }
}
