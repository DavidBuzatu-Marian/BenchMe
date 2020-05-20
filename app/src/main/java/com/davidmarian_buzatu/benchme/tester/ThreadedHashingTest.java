package com.davidmarian_buzatu.benchme.tester;

import com.davidmarian_buzatu.benchme.benchmark.cpu.CPUThreadedHashing;
import com.davidmarian_buzatu.benchme.timing.ITimer;
import com.davidmarian_buzatu.benchme.timing.Timer;

public class ThreadedHashingTest {

    public static double testThreadedHashing() {
        ITimer timer = new Timer();

        CPUThreadedHashing bench_hash = new CPUThreadedHashing();
        int maxLength = 10;
        int nThreads = 8;
        int hashCode = 1682627706;

        for(int i = 0; i < 5; i++)
        {
            bench_hash.warmUp();
        }

        timer.start();
        bench_hash.run(maxLength, nThreads, hashCode);
        long time = timer.stop();
        double result = (double)time/1000000000.0;

        double points;
        double average_result = 13.69;
        double average_points = 50.0;

        points = average_points + average_points * ((average_result - result) / result);
        return points;
    }
}
