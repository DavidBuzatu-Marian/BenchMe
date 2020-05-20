package com.davidmarian_buzatu.benchme.benchmark.ram.bench;

import android.util.Log;

import com.davidmarian_buzatu.benchme.benchmark.IBenchmark;
import com.davidmarian_buzatu.benchme.benchmark.ram.memory.MemoryHandler;
import com.davidmarian_buzatu.benchme.benchmark.ram.status.BenchStatus;
import com.davidmarian_buzatu.benchme.timing.ITimer;
import com.davidmarian_buzatu.benchme.timing.TimeUnit;
import com.davidmarian_buzatu.benchme.timing.Timer;


public class AccessSpeedBenchmark implements IBenchmark {

    private BenchStatus status;
    private long blockSize;
    private MemoryHandler mem;
    private byte patternByte;
    private int patternInt;
    private ITimer timer;

    @Override
    public void run() {
        throw new RuntimeException("Unsuported function, use run(Object... options) instead!");
    }

    @Override
    public void run(Object... options) {
        status.setStatus(true);

        Object[] params = (Object[]) options;
        patternByte = Byte.parseByte(params[0].toString());
        patternInt = patternByte << 24 | patternByte << 16 | patternByte << 8 | patternByte;
        double readSpeed = 0;
        double writeSpeed = 0;

        warmUp();

        status.setCurrentExecution("Allocating memory");

        mem.allocateMemory(blockSize);


        status.setCurrentExecution("Writing pattern to block bytesize");

        timer.start();

        for (long step = 1; step < blockSize / 65536 && status.getStatus(); step *= 2) {
            for (long st = 0; st < step && status.getStatus(); st++) {
                for (long i = st; i < blockSize && status.getStatus(); i += step) {
                    mem.writeByte(i, patternByte);
                }
            }
            long wtime = timer.pause();
            writeSpeed = ((double) blockSize / RamBenchmark.MB) / ((double) wtime / 1000000000L);
            status.addScoreAverage(writeSpeed);
            timer.resume();
        }
        timer.pause();

        status.setCurrentExecution("Reading block bytesize");
        int faults = 0;

        timer.resume();
        for (long step = 1; step < blockSize / 65536 && status.getStatus(); step *= 2) {
            for (long st = 0; st < step && status.getStatus(); st++) {
                for (long i = st; i < blockSize && status.getStatus(); i += step) {
                    mem.readByte(i);
                }
            }
            long rtime = timer.pause();
            readSpeed = ((double) blockSize / RamBenchmark.MB) / ((double) rtime / 1000000000L);
            status.addScoreAverage(readSpeed);
            timer.resume();

        }
        timer.pause();
        status.setCurrentExecution("Writing pattern to block intsize");


        timer.resume();
        for (long step = 4; step < blockSize / 65536 && status.getStatus(); step *= 2) {
            for (long st = 0; st < step && status.getStatus(); st += 4) {
                for (long i = st; i < blockSize && status.getStatus(); i += step) {
                    mem.writeInt(i, patternInt);
                }
            }
            long wtime = timer.pause();
            writeSpeed = ((double) blockSize / RamBenchmark.MB) / ((double) wtime / 1000000000L);
            status.addScoreAverage(writeSpeed);
            timer.resume();
        }
        timer.pause();


        status.setCurrentExecution("Reading block intsize");


        timer.resume();
        for (long step = 4; step < blockSize / 65536 && status.getStatus(); step *= 2) {
            for (long st = 0; st < step && status.getStatus(); st += 4) {
                for (long i = st; i < blockSize && status.getStatus(); i += step) {
                    mem.readInt(i);
                }
            }
            long rtime = timer.pause();
            readSpeed = ((double) blockSize / RamBenchmark.MB) / ((double) rtime / 1000000000L);
            status.addScoreAverage(readSpeed);
            timer.resume();

        }
        timer.pause();

        status.setCurrentExecution("Finished");

        status.setProgression(100);
        mem.freeMemory();
        status.setStatus(false);
    }

    @Override
    public void initialize(Object... params) {
        status = new BenchStatus();
        try {
            mem = new MemoryHandler();
        } catch (RuntimeException e) {
            throw new RuntimeException("Memory benchmark cant be used!");
        }
        timer = new Timer();
        blockSize = Long.parseLong(params[0].toString());

    }

    @Override
    public void clean() {
        status.setScore(0);
    }

    @Override
    public void cancel() {
        status.setStatus(false);
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void warmUp() {
        mem.allocateMemory(blockSize);

        for (long i = 0L; i < blockSize; i++) {
            mem.writeByte(i, patternByte);
        }
        for (long i = 0L; i < blockSize; i++) {
            mem.readByte(i);
        }
        for (long i = 0L; i < blockSize; i += 4) {
            mem.readInt(i);
        }
        mem.freeMemory();
    }

    public BenchStatus getStatus() {
        return status;
    }

}
