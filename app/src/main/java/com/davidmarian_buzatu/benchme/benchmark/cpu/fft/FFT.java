package com.davidmarian_buzatu.benchme.benchmark.cpu.fft;

import java.math.BigInteger;

public class FFT {

    private static Poly convertPoly(Poly poly, boolean inverse) {

        Poly auxPoly = poly.invSort();

        int start = inverse ? -1 : 1;

        for(int len = 1; len < poly.size(); len *= 2) {

            Complex radix = new Complex(Math.cos(start * 2.0 * Math.PI / (len * 2)), Math.sin(start * 2.0 * Math.PI / (len * 2)));



            for(int i = 0; i < poly.size(); i += len * 2) {
                Complex w = new Complex(1.0, 0.0);
                for(int j = i; j < i + len; j++) {
                    int k = j + len;
                    Complex u = new Complex(auxPoly.get(j));
                    Complex v = new Complex(auxPoly.get(k));

                    auxPoly.set(j, u.add(v.multiply(w)));
                    auxPoly.set(j + len, u.subtract(v.multiply(w)));
                    w = w.multiply(radix);
                }

            }
        }

        if(inverse) {
            for(int i = 0; i < poly.size(); i++) {
                auxPoly.set(i, auxPoly.get(i).scalarMultiply(1.0 / poly.size()));
            }
        }

        return auxPoly;
    }

    public static BigInteger multiply(BigInteger x, BigInteger y) {
        Poly px = new Poly(x);
        Poly py = new Poly(y);

        int dim = 2 * (px.size() < py.size() ? py.size() : px.size());

        while(px.size() < dim)
            px.add(new Complex(0.0, 0.0));
        while(py.size() < dim)
            py.add(new Complex(0.0, 0.0));


        Poly convertedPx = FFT.convertPoly(px, false);
        Poly convertedPy = FFT.convertPoly(py, false);

        Poly result = convertedPx.multiply(convertedPy);

        result = FFT.convertPoly(result, true);

		/*while(result.size() > 0 && result.get(result.size() - 1).equals(new Complex(0.0, 0.0))) {
			result.pop();
		}*/

        //System.out.println(result);
        return new BigInteger(result.toString());
    }

}