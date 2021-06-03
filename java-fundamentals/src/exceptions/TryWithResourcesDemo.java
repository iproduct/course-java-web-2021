package exceptions;

import java.io.*;

public class TryWithResourcesDemo {

    static void printFile(String fname) throws FileNotFoundException, IOException {
        try (BufferedReader in = new BufferedReader(
                new FileReader(fname));
             PrintWriter out = new PrintWriter(
                             new BufferedWriter(
                                     new FileWriter("out.txt")))) {
            String s;
            int i = 1;
            while ((s = in.readLine()) != null) {
                System.out.println(i++ + ": " + s);
                out.println(i++ + ": " + s);
            }
        }
    }

    public static void main(String[] args) {
        try {
            printFile("src/exceptions/TryWithResourcesDemo.java");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO problem.");
            e.printStackTrace();
        }
    }

}
