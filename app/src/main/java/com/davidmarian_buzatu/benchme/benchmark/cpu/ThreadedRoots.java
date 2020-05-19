package com.davidmarian_buzatu.benchme.benchmark.cpu;

import android.util.Log;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;

public class ThreadedRoots implements IBenchmark {

    private double result;
    private int size;
    private boolean running;

    @Override
    public void initialize(Object... params) {
        size = (Integer) params[0];
    }

    @Override
    public void warmUp() {
        this.run(4);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not implemented. Use run(Objects...) instead");
    }

    @Override
    public void run(Object... options) {
        int nThreads = (Integer) options[0];
        Thread[] threads = new Thread[nThreads];

        final int jobPerThread = size / nThreads;
        running = true;
        for (int i = 0; i < nThreads && running; ++i) {
            threads[i] = new Thread(new SquareRootTask(i * jobPerThread + 1, (i + 1) * jobPerThread));
            threads[i].start();
        }
        // join threads
        try {
            for (int i = 0; i < nThreads; ++i) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void setResult(double x) {
        result += x;
    }

    @Override
    public void clean() {
        System.gc();
    }

    public String getResult() {
        return String.valueOf(result);
    }

    class SquareRootTask implements Runnable {

        private int from, to;
        private final double precision = 1e-4; // fixed
        private double result = 0.0;

        public SquareRootTask(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            for (int i = from; i < to && running; i++) {
                result += getNewtonian(i);
            }
            setResult(result);
        }

        private double getNewtonian(double x) {
            double s = x;
            while (Math.abs(Math.pow(s, 2) - x) > precision) {
                s = (x / s + s) / 2;
            }
            return s;
        }

    }

    @Override
    public void cancel() {
        // TODO Auto-generated method stub
    }

}