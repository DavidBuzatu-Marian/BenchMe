package com.davidmarian_buzatu.benchme.benchmark.cpu;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPUThreadedHashing implements IBenchmark {

    private String result;
    volatile boolean running = true;

    @Override
    public void initialize(Object... params) {

    }

    @Override
    public void warmUp() {
        HashManager hasher = new HashManager();
        String text = "aa";
        int length = 2;
        int max_length = 5;
        while (running) {
            text = hasher.getNextString(text);

            if (length > max_length) {
                running = false;
            }

            if (text == null) {
                length++;
                text = "aa";
                for (int i = 2; i < length; ++i)
                    text += "a";
            }
        }
        running = true;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Object) instead");
    }

    @Override
    public void run(Object... options) {

        int maxTextLength = (Integer)options[0];
        int nThreads = (Integer)options[1];
        int hashCode = (Integer)options[2];

        int length = 2;

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        HashManager hasher = new HashManager();
        String text = "aa";

        while (running) {
            HashBreakerTask worker = new HashBreakerTask(hasher, text, hashCode);
            executor.execute(worker);
            text = hasher.getNextString(text);

            if (length > maxTextLength) {
                running = false;
            }

            if (text == null) {
                length++;
                text = "aa";
                for (int i = 2; i < length; ++i)
                    text += "a";
            }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

    }

    @Override
    public void clean() {
    }

    public String getResult() {
        return String.valueOf(result);
    }

    class HashBreakerTask implements Runnable {

        private final HashManager hasher;
        private final String text;
        private final int expectedHash;

        public HashBreakerTask(HashManager hasher, String text, int expectedHash) {
            this.hasher = hasher;
            this.text = text;
            this.expectedHash = expectedHash;
        }

        @Override
        public void run() {

            if (expectedHash == hasher.MurmurHash(text)) {

                running = false;

                result = text;
            }
        }
    }

    class HashManager {

        private final String charSet = "abcdefghijklmnopqrstuvwxyz";

        public int MurmurHash( final byte[] data, int length, int seed) {

            final int m = 0x5bd1e995;
            final int r = 24;
            int h = seed^length;
            int length4 = length/4;

            for (int i=0; i<length4; i++) {
                final int i4 = i*4;
                int k = (data[i4+0]&0xff) + ((data[i4+1]&0xff)<<8) + ((data[i4+2]&0xff)<<16) + ((data[i4+3]&0xff)<<24);
                k *= m;
                k ^= k >>> r;
                k *= m;
                h *= m;
                h ^= k;
            }

            switch (length%4) {
                case 3: h ^= (data[(length&~3) +2]&0xff) << 16;
                case 2: h ^= (data[(length&~3) +1]&0xff) << 8;
                case 1: h ^= (data[length&~3]&0xff);
                    h *= m;
            }

            h ^= h >>> 13;
            h *= m;
            h ^= h >>> 15;

            return h;
        }

        public int MurmurHash( final byte[] data, int length) {
            return MurmurHash( data, length, 0x9747b28c);
        }

        public int MurmurHash( final String text) {
            final byte[] bytes = text.getBytes();
            return MurmurHash( bytes, bytes.length);
        }

        public String getNextString(String text) {
            int[] index = new int[text.length()];
            int end = charSet.length() - 1;
            int k = index.length - 1;
            int i = 0;
            for(i=0;i < index.length; i++)
            {
                index[i] = charSet.indexOf(text.charAt(i));
            }
            if(index[k] < end)
            {
                index[k]++;
            }
            else
            {
                while(k >= 0 && index[k] >= end)
                {
                    index[k] = 0;
                    if(k > 0)
                    {
                        index[k - 1]++;
                    }
                    k--;
                }

                if(k == -1)
                {
                    return null;
                }
            }
            String result = "";
            for(i = 0; i < index.length; i++)
            {
                result = result + charSet.charAt(index[i]);
            }

            return result;
        }

    }

    @Override
    public void cancel() {

    }

}