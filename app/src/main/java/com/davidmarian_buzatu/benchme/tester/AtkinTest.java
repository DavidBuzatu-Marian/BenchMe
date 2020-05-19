package com.davidmarian_buzatu.benchme.tester;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.cpu.SieveOfAtkinBenchmark;
import com.davidmarian_buzatu.benchme.timing.ITimer;
import com.davidmarian_buzatu.benchme.timing.TimeUnit;
import com.davidmarian_buzatu.benchme.timing.Timer;

public class AtkinTest {
    public static double testAtkin() {
        ITimer timer = new Timer();
        IBenchmark bench = new SieveOfAtkinBenchmark();

        double score = 0.0;

        bench.warmUp();

        for(int i = 31; i >= 27; i--) {
            double sc;

            bench.initialize((int)(1L << i - 2));

            timer.start();

            bench.run();

            long time = timer.stop();

            sc = (Math.log(1L << i - 2) / (Math.log(TimeUnit.convertTimeDouble(time, TimeUnit.SEC)) + 1));

            score += sc;
        }

        return score / 5;
    }

}
