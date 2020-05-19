package com.davidmarian_buzatu.benchme.benchmark.cpu;


import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;

import java.util.BitSet;

public class SieveOfAtkinBenchmark implements IBenchmark {
    private BitSet sieve;

    private int limit;

    @Override
    public void initialize(Object... params) {
        limit = (int)params[0];

        sieve = new BitSet(limit + 1);
    }

    @Override
    public void warmUp() {
        initialize((int)(1L << 20));
        int repeat = 10;

        while(repeat > 0) {
            run();
            repeat--;
        }
    }

    @Override
    public void run() {

        if (limit > 2)
            sieve.set(2, true);

        if (limit > 3)
            sieve.set(3, true);

        for (int x = 1; x * x < limit; x++) {
            for (int y = 1; y * y < limit; y++) {

                // Main part of Sieve of Atkin
                int n = (4 * x * x) + (y * y);

                if (n <= limit && (n % 12 == 1 || n % 12 == 5))
                    sieve.flip(n);

                n = (3 * x * x) + (y * y);

                if (n <= limit && n % 12 == 7)
                    sieve.flip(n);

                n = (3 * x * x) - (y * y);

                if (x > y && n <= limit && n % 12 == 11)
                    sieve.flip(n);
            }
        }

        // Mark all multiples of squares as
        // non-prime
        for (int r = 5; r * r < limit; r++) {
            if (sieve.get(r)) {
                for (int i = r * r; i < limit; i += r * r)
                    sieve.set(i, false);
            }
        }

        /*// Print primes using sieve[]

        for (int a = 2; a < limit; a++)
            if (sieve.get(a))
                log.write(a + " ");

         */
    }

    @Override
    public void run(Object... params) {

    }



    @Override
    public void clean() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public String getResult() {
        return null;
    }
}
