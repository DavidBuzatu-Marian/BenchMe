package com.davidmarian_buzatu.benchme.benchmark.cpu.fft;

public class Complex implements Cloneable{

    private double a, b;

    private final double EPS = 0.0000001;

    public Complex(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public Complex(Complex c) {
        this.a = c.getA();
        this.b = c.getB();
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Complex)) {
            return false;
        }

        Complex c = (Complex) o;
        return Math.abs(c.getA() - a) <= EPS && Math.abs(c.getB() - b) <= EPS;
    }

    public Complex add(Complex c) {
        return new Complex(a + c.a, b + c.b);
    }

    public Complex subtract(Complex c) {
        return new Complex(a - c.a, b - c.b);
    }

    public Complex multiply(Complex c) {
        return new Complex(a * c.a - b * c.b, a * c.b + b * c.a);
    }

    public Complex scalarMultiply(double d) {
        return new Complex(a * d, b * d);
    }

    public String toString() {
        return "(" + a + ", " + b + ")";
    }


}