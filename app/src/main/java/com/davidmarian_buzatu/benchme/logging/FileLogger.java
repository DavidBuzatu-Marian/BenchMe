package com.davidmarian_buzatu.benchme.logging;


import com.davidmarian_buzatu.benchme.timing.TimeUnit;

public class FileLogger implements ILogger {
    @Override
    public void write(long time) {

    }

    @Override
    public void write(String text) {

    }

    @Override
    public void write(Object... parameters) {

    }

    @Override
    public void close() {

    }


    @Override
    public void writeTime(long value, TimeUnit unit) {
        String convertedTime = TimeUnit.convertTime(value, unit);
        System.out.println(convertedTime);
    }

    @Override
    public void writeTime(String string, long value, TimeUnit unit) {
        String convertedTime = TimeUnit.convertTime(value, unit);
        System.out.println(string + " " + convertedTime);
    }
}
