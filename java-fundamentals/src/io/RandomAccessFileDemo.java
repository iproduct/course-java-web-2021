package io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RandomAccessFileDemo {
    public static int RECORD_SIZE = 8;

    public static void main(String[] args) {
        File f = new File("data.out");
        f.delete();
        try (RandomAccessFile raf = new RandomAccessFile(new File("data.out"), "rw")) {
            double pi = 3.1415;
            for (int i = 0; i < 10; i++) {
                raf.writeDouble(i * pi);
            }
            // Find 5-th record (n-1) * RECORD_SIZE
            raf.seek(4 * RECORD_SIZE);
            raf.writeDouble(42.0);
            // Append new record
            raf.seek(raf.length());
            raf.writeDouble(108.0);
            System.out.printf("Position after append: %8d%n", raf.getFilePointer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (RandomAccessFile raf = new RandomAccessFile(new File("data.out"), "r")) {
            double pi = 3.1415;
            long numRecords = raf.length() / RECORD_SIZE;
            for (int i = 0; i < numRecords; i++) {
                double payload = raf.readDouble();
                System.out.printf("%d -> %10.5f%n", i, payload);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
