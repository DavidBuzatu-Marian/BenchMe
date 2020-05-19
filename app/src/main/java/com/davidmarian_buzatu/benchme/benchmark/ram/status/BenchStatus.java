package com.davidmarian_buzatu.benchme.benchmark.ram.status;

public class BenchStatus {

    private double scale;
    private double progression;
    private String currentExecution;
    private boolean status;
    private double score;
    private int count;
    private double avScore;

    public BenchStatus(){
        progression = 0;
        currentExecution = null;
        status = false;
        score = 0;
        scale = 1;
        count = 0;
        avScore = 0;
    }


    /**
     * Returns the progression of the benchmark in percentage
     * @return the progression
     */
    public synchronized double getProgression() {
        return progression;
    }

    /**
     * @param progression the progression to set
     */
    public synchronized void setProgression(double progression) {
        this.progression = progression;
    }

    /**
     * Returns the currently executing task
     * @return the currentExecution
     */
    public synchronized String getCurrentExecution() {
        return currentExecution;
    }

    /**
     * @param currentExecution the currentExecution to set
     */
    public synchronized void setCurrentExecution(String currentExecution) {
        this.currentExecution = currentExecution;
    }

    /**
     * Returns if the benchmark is still running or not
     * @return the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public synchronized void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the score
     */
    public synchronized double getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public synchronized void setScore(double score) {
        this.score = score * scale;
    }

    public synchronized void addScore(double score){
        this.score += score * scale;
    }

    /**
     * @return the scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    public void addScoreAverage(double score){
        avScore += score*scale;
        count++;
    }

    public double getScoreAverage(){
        return avScore/count;
    }

}
