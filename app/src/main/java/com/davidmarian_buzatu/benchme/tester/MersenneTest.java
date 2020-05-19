package com.davidmarian_buzatu.benchme.tester;

import android.util.Log;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.cpu.MersenneBenchmark;
import com.davidmarian_buzatu.benchme.timing.ITimer;
import com.davidmarian_buzatu.benchme.timing.TimeUnit;
import com.davidmarian_buzatu.benchme.timing.Timer;

import java.util.concurrent.ExecutorService;

public class MersenneTest {
    private static Thread[] thread = new Thread[4];
    private static double score = 0.0;
    private static int cnt = 0, counterT;

    public static double testMersenne() {
        IBenchmark bench = new MersenneBenchmark();
        bench.initialize(1279);
        bench.warmUp();
        for (int i = 1279; i <= 4279; i += 1000) {
            thread[counterT] = new Thread(new MersenneRunnable(i));
            thread[counterT++].start();
        }
        for (int i = 0; i < counterT; ++i) {
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        updateFinalScore();


        return getScore();
    }

    private synchronized static double getScore() {
        return score;
    }

    private synchronized static void updateFinalScore() {
        score /= 1.0 * cnt;
    }

    private synchronized static void updateScore(double time, int i) {
        score += i / ((Math.log(time)) + 1);
        Log.d("RESULTED", score + " i: " + i);
    }

    private synchronized static void increaseCnt() {
        ++cnt;
    }

    public static class MersenneRunnable implements Runnable {

        private final int i;

        public MersenneRunnable(int i ) {
            this.i = i;
        }

        @Override
        public void run() {
            ITimer timer = new Timer();
            IBenchmark bench = new MersenneBenchmark();
            increaseCnt();
            bench.initialize(i);

            timer.start();
            bench.run();
            double time = TimeUnit.convertTimeDouble(timer.stop(), TimeUnit.SEC);
            updateScore(time, i);
        }
    }
}
