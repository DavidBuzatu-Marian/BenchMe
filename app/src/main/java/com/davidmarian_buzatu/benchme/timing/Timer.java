package com.davidmarian_buzatu.benchme.timing;

public class Timer implements ITimer {
    private long startTime, endTime, totalTime;

    @Override
    public void start() {
        startTime = System.nanoTime();
        totalTime = 0;
    }

    @Override
    public long stop() {
        if(startTime != 0) {
            endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }
        return totalTime;
    }

    @Override
    public void resume() {
        startTime = System.nanoTime();
    }

    @Override
    public long pause() {
        endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        totalTime += elapsedTime;
        startTime = 0;
        return elapsedTime;
    }

}
