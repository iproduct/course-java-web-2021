package io;

import java.io.*;

public class InputStreamReaderDemo {
    public static void main(String[] args) {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream("./src/io/InputStreamReaderDemo.java")))){
            String line;
            int n = 0;

            while((line = in.readLine()) != null){
                System.out.printf("%3d: %s%n", ++n, line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
