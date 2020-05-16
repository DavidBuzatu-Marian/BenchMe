package com.davidmarian_buzatu.benchme.logging;


import com.davidmarian_buzatu.benchme.timing.TimeUnit;

public class ConsoleLogger implements ILogger {
    /**
     * Writes to system out the passed long
     * @param time a long to be printed
     * */
    @Override
    public void write(long time) {
        System.out.println(time);
    }
    /**
     * Writes to system out the passed text
     * @param text a String to be printed
     * */
    @Override
    public void write(String text) {
        System.out.println(text);
    }
    /**
     * Writes to system out all
     * the parameters passed as Objects
     * @param parameters array of objects to be printed
     * */
    @Override
    public void write(Object... parameters) {
        for(Object param: parameters) {
            System.out.print(param + " ");
        }
        System.out.println();
    }
    /**
     * Closes the opened stream for writing
     * In this case, we do not close the System.out
     * */
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
