package com.davidmarian_buzatu.benchme.benchmark.cpu;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.cpu.fft.FFT;

import java.math.BigInteger;

public class MersenneBenchmark implements IBenchmark {

    private int number;

    private String result;
    private final BigInteger TWO = new BigInteger("2");

    private BigInteger s = new BigInteger("4");

    private BigInteger prime = new BigInteger("4");

    @Override
    public void warmUp() {
        // TODO Auto-generated method stub
        this.run();
        s = new BigInteger("4");
        prime = new BigInteger("4");
    }

    @Override
    public void run() {
        s = new BigInteger("4");
        prime = new BigInteger("4");
        for (int i = 3; i <= number; i++) {
            prime = prime.multiply(TWO);

        }
        prime = prime.subtract(BigInteger.ONE);

        for (int i = 2; i < number; i++) {
            s = FFT.multiply(s, s);
            s = s.subtract(TWO);
            s = s.mod(prime);
        }
        result = s.equals(BigInteger.ZERO) ? "Mersenne Prime" : "Not Mersenne Prime";

    }

    @Override
    public void run(Object... params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clean() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initialize(Object... params) {
        number = (Integer) params[0];

    }

    @Override
    public void cancel() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return result;
    }

}

