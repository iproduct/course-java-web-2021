package io;

import java.io.*;

public class CharacterInputDemo {
    public static void main(String[] args) {
        try(Reader in = new FileReader("./src/io/InputStreamReaderDemo.java")){
            int ch;
            int n = 0;

            while((ch = in.read()) != -1){
                System.out.print((char) ch);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
