package com.davidmarian_buzatu.benchme.tester;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.ram.bench.RamBenchmark;

public class RAMTest {

    public static double testRAM() {
        IBenchmark bench = new RamBenchmark();

        bench.initialize(RamBenchmark.GB * 1, 5);
        bench.run();

        return ((RamBenchmark) bench).getStatus().getScoreAverage();
    }
}
