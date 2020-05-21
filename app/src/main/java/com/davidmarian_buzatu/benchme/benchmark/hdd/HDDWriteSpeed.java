package com.davidmarian_buzatu.benchme.benchmark.hdd;

import android.content.Context;
import android.util.Log;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.hdd.services.FileHandler;

import java.io.File;
import java.io.IOException;

public class HDDWriteSpeed implements IBenchmark {
    private double result;
    private Context context;
    private double maxReadSpeedFs;
    private double maxReadSpeedFb;
    private double maxWriteSpeedFs;
    private double maxWriteSpeedFb;

    @Override
    public void initialize(Object... params) {
        context = (Context) params[0];
    }

    @Override
    public void warmUp() {

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Object) instead");
    }

    @Override
    public void run(Object... options) {
        FileHandler handler = new FileHandler();
        // either "fs" - fixed size, or "fb" - fixed buffer
        String option = (String) options[0];
        // true/false whether the written files should be deleted at the end
        Boolean clean = (Boolean) options[1];
        // either "r" - read or "w" - write
        String mode = (String) options[2];

        String prefix = context.getFilesDir().getAbsolutePath() + File.separator + "testHDD";

        String suffix = ".dat";
        int startIndex = 0;
        int endIndex = 8;
        long fileSize = 256 * 1024 * 1024;
        int bufferSize = 4 * 1024;

        if (mode.equals("r")) {
            try {

                if (option.equals("fs")) {
                    result = handler.streamFixedSize(prefix, suffix, startIndex,
                            endIndex, fileSize, clean, mode);
                    maxReadSpeedFs = handler.getMaxSpeed();
                }
                else if (option.equals("fb")) {
                    result = handler.streamFixedBuffer(prefix, suffix, startIndex,
                            endIndex, bufferSize, clean, mode);
                    maxReadSpeedFb = handler.getMaxSpeed();
                }
                else
                    throw new IllegalArgumentException("Argument "
                            + options[0].toString() + " is undefined");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {

                if (option.equals("fs")) {
                    result = handler.streamFixedSize(prefix, suffix, startIndex,
                            endIndex, fileSize, clean, mode);
                    maxWriteSpeedFs = handler.getMaxSpeed();
                }
                else if (option.equals("fb")) {
                    result = handler.streamFixedBuffer(prefix, suffix, startIndex,
                            endIndex, bufferSize, clean, mode);
                    maxWriteSpeedFb = handler.getMaxSpeed();
                }
                else
                    throw new IllegalArgumentException("Argument "
                            + options[0].toString() + " is undefined");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clean() {

    }

    public void cancel() {

    }


    public String getResult() {
        return String.valueOf(result);
    }

    public double getMaxReadSpeed(){
        return (maxReadSpeedFs>maxReadSpeedFb)? maxReadSpeedFs:maxReadSpeedFb;
    }

    public double getMaxWriteSpeed(){
        return (maxWriteSpeedFs>maxWriteSpeedFb)?maxWriteSpeedFs:maxWriteSpeedFb;
    }
}
