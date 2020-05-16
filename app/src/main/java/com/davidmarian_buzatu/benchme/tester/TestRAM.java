package com.davidmarian_buzatu.benchme.tester;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.logging.ConsoleLogger;
import com.davidmarian_buzatu.benchme.logging.ILogger;
import com.davidmarian_buzatu.benchme.timing.ITimer;
import com.davidmarian_buzatu.benchme.timing.TimeUnit;
import com.davidmarian_buzatu.benchme.timing.Timer;

public class TestRAM {
    public void test() {
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
//        IBenchmark benchmark = new CPUDigitsOfPi();
        TimeUnit timeUnit = TimeUnit.SEC;
        final int limit = 123456;

//        for (int i = 0; i < 1; i++) {
//            timer.start();
//            benchmark.run(2);
//
//            log.writeTime("PI3 -> Finished in ", timer.stop(), timeUnit);
//            log.write(benchmark.getResult(3));
//            benchmark.clean();
//        }
    }
}
