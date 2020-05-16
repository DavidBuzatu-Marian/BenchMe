package com.davidmarian_buzatu.benchme.benchmark;

public interface IBenchmark {
    /**
     * Called to explicitly initialize benchmark data. <br>
     * This call is not benchmarked.
     *
     * @param parameters variable list of arguments (of any type) needed to setup the benchmark
     */
    void initialize(Object ...parameters);
    /**
     * Calls the actual benchmarking algorithm, optionally after <b>initialize</b>
     * was called. <br>
     * This call should be benchmarked.
     */
    void run();
    /**
     * Calls the actual benchmarking algorithm, optionally after <b>initialize</b>
     * was called. <br>
     * This call should be benchmarked.
     *
     * @param parameters May pass a benchmark option defined by the benchmark class
     */
    void run(Object ...parameters);
    /**
     * Called right before running the algorithm itself to "warm-up" the task at
     * hand. <br>
     * The warm up should do the exact task as the run method, however it should not
     * be timed. <br>
     * The amount of warm-up data/time should be between 10-100% of the total time.
     * <br>
     * This call should not be benchmarked.
     */
    void warmUp();
    /**
     * Called to explicitly release allocated data. <br>
     * This call should not be benchmarked.
     */
    void clean();
    /**
     * Stops a benchmark during execution. <br>
     * Should be checked in the main for-loop, or main thread of the benchmark.
     */
    void cancel();

    /**
     * Return result of benchmark as String
     * uses parameter to switch between type
     */
    String getResult(int type);
}
