package com.davidmarian_buzatu.benchme.tester;

import android.util.Log;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.cpu.ThreadedRoots;
import com.davidmarian_buzatu.benchme.logging.ConsoleLogger;
import com.davidmarian_buzatu.benchme.logging.ILogger;
import com.davidmarian_buzatu.benchme.timing.ITimer;
import com.davidmarian_buzatu.benchme.timing.TimeUnit;
import com.davidmarian_buzatu.benchme.timing.Timer;

public class ThreadedRootsTest {
    public static double testThreadedRoots() {
        ITimer timer = new Timer();
        double score = 0.0;

        IBenchmark bench = new ThreadedRoots();
        int workload = (int) Math.pow(10, 4);
        bench.initialize(workload);
        bench.warmUp();
        workload = (int) Math.pow(10, 8);
        bench.initialize(workload);

        for (int i = 1; i <= 32; i *= 2) {
            timer.start();
            bench.run(i);
            long time = timer.stop();
            score += (double) workload / Math.log((time / Math.pow(10, 9)) * Math.log(i));
        }
        bench.clean();

        return score;
    }
}
