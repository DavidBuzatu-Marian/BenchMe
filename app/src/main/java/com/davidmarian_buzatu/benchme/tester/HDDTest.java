package com.davidmarian_buzatu.benchme.tester;

import android.content.Context;
import android.util.Log;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.hdd.HDDWriteSpeed;

public class HDDTest {
    private static IBenchmark bench;

    public static double getMaxReadSpeed() {
        return ((HDDWriteSpeed) bench).getMaxReadSpeed();
    }

    public static double getMaxWriteSpeed() {
        return ((HDDWriteSpeed) bench).getMaxWriteSpeed();
    }

    public static int testHDD(Context context) {
        bench = new HDDWriteSpeed();
        final int reference = 650;
        int scoreWriteFs = 0;
        int scoreReadFs = 0;
        int scoreWriteFb = 0;
        int scoreReadFb = 0;
        int totalScore = 0;

        bench.initialize(context);
        bench.run("fs", true, "w");
        scoreWriteFs = compute_score(Double.parseDouble(((HDDWriteSpeed) bench).getResult()), reference);
        bench.run("fs", true, "r");
        scoreReadFs = compute_score(Double.parseDouble(((HDDWriteSpeed) bench).getResult()), reference);
        bench.run("fb", true, "w");
        scoreWriteFb = compute_score(Double.parseDouble(((HDDWriteSpeed) bench).getResult()), reference);
        bench.run("fb", true, "r");
        scoreReadFb = compute_score(Double.parseDouble(((HDDWriteSpeed) bench).getResult()), reference);
        totalScore = ((scoreWriteFb + scoreWriteFs) * 4 / 5 + (scoreReadFb + scoreReadFs) * 1 / 5) / 4;
        return totalScore;

    }

    private static int compute_score(double speed, int reference) {
        return (int) (speed * 1000) / reference;
    }
}
