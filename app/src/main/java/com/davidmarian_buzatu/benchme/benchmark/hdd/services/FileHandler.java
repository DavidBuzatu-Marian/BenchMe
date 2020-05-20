package com.davidmarian_buzatu.benchme.benchmark.hdd.services;

import android.util.Log;

import com.davidmarian_buzatu.benchme.timing.TimeUnit;
import com.davidmarian_buzatu.benchme.timing.Timer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class FileHandler {

    private static final int MIN_BUFFER_SIZE = 1024 * 1; // KB
    private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // MB
    private static final int MIN_FILE_SIZE = 1024 * 1024 * 1; // MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 512; // MB
    private Timer timer = new Timer();
    private double benchScore;


    public double streamFixedSize(String filePrefix, String fileSuffix,
                                  int minIndex, int maxIndex, long fileSize, boolean clean, String mode)
            throws IOException {

        int currentBufferSize = MIN_BUFFER_SIZE;
        String fileName;
        int counter = 0;
        benchScore = 0;

        while (currentBufferSize <= MAX_BUFFER_SIZE
                && counter <= maxIndex - minIndex) {
            fileName = filePrefix + counter + fileSuffix;
            if (mode.equals("r")) {
                readWithBufferSize(fileName, currentBufferSize, fileSize, clean);
            } else {
                writeWithBufferSize(fileName, currentBufferSize, fileSize, clean);
            }
            currentBufferSize *= 2;
            counter++;
        }

        benchScore /= (maxIndex - minIndex + 1);
        return benchScore;
    }

    public double streamFixedBuffer(String filePrefix, String fileSuffix,
                                    int minIndex, int maxIndex, int bufferSize, boolean clean, String mode)
            throws IOException {

        int currentFileSize = MIN_FILE_SIZE;
        String fileName;
        int counter = 0;

        while (currentFileSize <= MAX_FILE_SIZE
                && counter <= maxIndex - minIndex) {
            fileName = filePrefix + counter + fileSuffix;
            if (mode.equals("r")) {
                readWithBufferSize(fileName, bufferSize, currentFileSize, clean);
            } else {
                writeWithBufferSize(fileName, bufferSize, currentFileSize, clean);
            }
            currentFileSize *= 2;
            counter++;
        }

        benchScore /= (maxIndex - minIndex + 1);
        return benchScore;

    }

    private void readWithBufferSize(String fileName, int myBufferSize, long fileSize, boolean clean) throws IOException {
        FileInputStream stream = new FileInputStream(fileName);
        final BufferedInputStream inputStream = new BufferedInputStream(stream, myBufferSize);

        byte[] buffer = new byte[myBufferSize];
        int i = 0;
        long toRead = fileSize / myBufferSize;

        timer.start();
        while (i < toRead) {
            inputStream.read(buffer);
            i++;
        }

        printStats(fileName, fileSize, myBufferSize, "r");

        inputStream.close();

    }

    private void writeWithBufferSize(String fileName, int myBufferSize,
                                     long fileSize, boolean clean) throws IOException {

        final File file = new File(fileName);
        FileOutputStream stream = new FileOutputStream(file);
        final BufferedOutputStream outputStream = new BufferedOutputStream(stream, myBufferSize);

        byte[] buffer = new byte[myBufferSize];
        int i = 0;
        long toWrite = fileSize / myBufferSize;
        Random rand = new Random();

        timer.start();
        while (i < toWrite) {
            rand.nextBytes(buffer);
            outputStream.write(buffer);
            i++;
        }
        printStats(fileName, fileSize, myBufferSize, "w");

        outputStream.close();
        if (clean) {
            file.deleteOnExit();
        }
    }

    private void printStats(String fileName, long totalBytes, int myBufferSize, String mode) {
        NumberFormat nf = new DecimalFormat("#.00");
        final long time = timer.stop();
        double seconds = TimeUnit.convertTimeDouble(time, TimeUnit.SEC);
        double megabytes = totalBytes / 1000000.0;
        double rate = megabytes / seconds;

        benchScore += rate;
    }
}