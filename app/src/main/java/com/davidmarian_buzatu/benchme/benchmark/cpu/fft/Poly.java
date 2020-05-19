package com.davidmarian_buzatu.benchme.benchmark.cpu.fft;

import java.math.BigInteger;
import java.util.ArrayList;

public class Poly {
    private ArrayList<Complex> poly = new ArrayList<>();

    public Poly(ArrayList<Complex> poly) {
        this.poly = poly;
    }

    private boolean isPower2(int x) {
        return (x & (x - 1)) == 0;
    }

    public Poly(BigInteger big) {
        String s = big.toString();
        for(int i = s.length() - 1; i >= 0; i--) {
            poly.add(new Complex((double)(s.charAt(i) - '0'), 0.0));
        }

        while(!isPower2(poly.size()))
            poly.add(new Complex(0.0, 0.0));
    }

    public void add(Complex c) {
        poly.add(c);
    }

    public void pop() {
        poly.remove(poly.size() - 1);
    }

    public Poly multiply(Poly p) {
        Poly res = new Poly(new ArrayList<Complex>(poly));
        for(int i = 0; i < poly.size(); i++) {
            res.set(i, poly.get(i).multiply(p.get(i)));
        }
        return res;
    }

    public ArrayList<Complex> getPoly(){
        return poly;
    }

    public int size() {
        return poly.size();
    }

    public Complex get(int index) {
        return poly.get(index);
    }

    public void set(int index, Complex val) {
        poly.set(index, val);
    }

    private int revBits(int x, int dim) {
        int mask = 0;
        for(int i = 0; (1 << i) < dim; i++) {
            mask = (mask << 1) | ((x & (1 << i)) >> i);
        }
        return mask;
    }
    public Poly invSort() {
        ArrayList<Complex> newPoly = new ArrayList<>(poly);
        for(int i = 0; i < newPoly.size(); i++) {
            newPoly.set(revBits(i, poly.size()), poly.get(i));
        }

        return new Poly(newPoly);
    }

    public String toString() {

        StringBuilder s = new StringBuilder();
        int sum = 0;
        for(int i = 0; i < poly.size(); i++) {
            int digit = (int)(poly.get(i).getA() + 0.5);

            s.append((digit + sum) % 10);
            sum = (digit + sum) / 10;
        }
        return s.reverse().toString();
    }
}