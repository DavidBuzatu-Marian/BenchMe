package com.davidmarian_buzatu.benchme.benchmark.ram.bench;

import android.util.Log;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.ram.status.BenchStatus;

import java.util.Random;

public class RamBenchmark implements IBenchmark {

    public static final long KB = 1024L;
    public static final long MB = 1024L * 1024;
    public static final long GB = 1024L * 1024 * 1024;

    private static final byte[] patterns = {-86, 85, -52, 51, 102, -103, 90, -91, -127, 126};

    private BenchStatus status;
    private long memSize;
    private int iterations;
    private double scale;

    @Override
    public void run() {

        status.setStatus(true);

        Long testSize;
        Random r = new Random();

        for (int i = 0; i <= iterations && status.getStatus(); i += 4) {
            IBenchmark bench = new AccessSpeedBenchmark();
            testSize = i != 0 ? i * (memSize / 128) : (memSize / 128);
            bench.initialize(testSize);
            ((AccessSpeedBenchmark) bench).getStatus().setScale(scale);
            Log.d("RAM", "STARTING: " + i);
            bench.warmUp();
            bench.run(patterns[r.nextInt(10)]);
            Log.d("RAM", "Score2 : " + ((AccessSpeedBenchmark) bench).getStatus().getScoreAverage());
            status.addScoreAverage(((AccessSpeedBenchmark) bench).getStatus().getScoreAverage());
            bench.clean();
        }

        status.setStatus(false);
    }

    @Override
    public void run(Object... options) {
        throw new RuntimeException("Unsuported function, use run() instead!");
    }

    @Override
    public void initialize(Object... params) {
        status = new BenchStatus();
        memSize = Long.parseLong(params[0].toString());
        iterations = Integer.parseInt(params[1].toString());
        iterations = min(iterations, 10);
        scale = 1;
    }

    private int min(int a, int b) {
        if (a > b) return b;
        return a;
    }

    @Override
    public void clean() {
        status.setScore(0);
    }

    @Override
    public void cancel() {
        status.setStatus(false);
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void warmUp() {

    }

    public BenchStatus getStatus() {
        return status;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
