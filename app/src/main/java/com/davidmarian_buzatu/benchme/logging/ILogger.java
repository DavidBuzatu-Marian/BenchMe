package com.davidmarian_buzatu.benchme.logging;


import com.davidmarian_buzatu.benchme.timing.TimeUnit;

public interface ILogger {
    /**
     * Writes to the stream of the instanced class
     * the passed in long
     * @param time a long to be printed
     * */
    void write(long time);
    /**
     * Writes to the stream of the instanced class
     * the passed in text
     * @param text a String to be printed
     * */
    void write(String text);
    /**
     * Writes to the stream of the instanced class
     * the parameters passed as Objects
     * @param parameters array of objects to be printed
     * */
    void write(Object... parameters);
    /**
     * Closes the opened stream for writing
     * */
    void close();

    void writeTime(long value, TimeUnit unit);
    void writeTime(String string, long value, TimeUnit unit);
}
