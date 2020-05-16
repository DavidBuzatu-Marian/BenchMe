package com.davidmarian_buzatu.benchme.timing;

public interface ITimer {
    void start();
    long stop();
    void resume();
    long pause();

}
