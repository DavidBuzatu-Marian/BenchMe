package com.davidmarian_buzatu.benchme.model;

public class Device {

    public static final String MODEL = "MODEL", OS = "OS", CPU = "CPU", CPUCORES = "CPUCORES", CPUSPEED = "CPUSPEED", RAM = "RAM", HDD = "HDD";
    public static final String ATKIN = "ATKIN", MERSENNE = "MERSENNE", ROOTS = "ROOTS", HDDT = "HDDT", RAMT = "RAMT", ID = "ID", HASH = "HASH";
    private String deviceModel, deviceOS, deviceCPU, deviceCPUCores, deviceCPUSpeed, deviceRAM, deviceHDD, id;
    private double scoreAtkin, scoreMersenne, scoreRoots, scoreHDD, scoreRAM, scoreHASH;


    public Device() {

    }

    public Device(String deviceModel, String deviceOS, String deviceCPU, String deviceCPUCores, String deviceCPUSpeed, String deviceRAM, String deviceHDD) {
        this.deviceModel = deviceModel;
        this.deviceOS = deviceOS;
        this.deviceCPU = deviceCPU;
        this.deviceCPUCores = deviceCPUCores;
        this.deviceCPUSpeed = deviceCPUSpeed;
        this.deviceRAM = deviceRAM;
        this.deviceHDD = deviceHDD;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceCPU() {
        return deviceCPU;
    }

    public void setDeviceCPU(String deviceCPU) {
        this.deviceCPU = deviceCPU;
    }

    public String getDeviceCPUCores() {
        return deviceCPUCores;
    }

    public void setDeviceCPUCores(String deviceCPUCores) {
        this.deviceCPUCores = deviceCPUCores;
    }

    public String getDeviceCPUSpeed() {
        return deviceCPUSpeed;
    }

    public void setDeviceCPUSpeed(String deviceCPUSpeed) {
        this.deviceCPUSpeed = deviceCPUSpeed;
    }

    public String getDeviceRAM() {
        return deviceRAM;
    }

    public void setDeviceRAM(String deviceRAM) {
        this.deviceRAM = deviceRAM;
    }

    public String getDeviceHDD() {
        return deviceHDD;
    }

    public void setDeviceHDD(String deviceHDD) {
        this.deviceHDD = deviceHDD;
    }

    public double getScoreAtkin() {
        return scoreAtkin;
    }

    public void setScoreAtkin(double scoreAtkin) {
        this.scoreAtkin = scoreAtkin;
    }

    public double getScoreMersenne() {
        return scoreMersenne;
    }

    public void setScoreMersenne(double scoreMersenne) {
        this.scoreMersenne = scoreMersenne;
    }

    public double getScoreRoots() {
        return scoreRoots;
    }

    public void setScoreRoots(double scoreRoots) {
        this.scoreRoots = scoreRoots;
    }

    public double getScoreHDD() {
        return scoreHDD;
    }

    public void setScoreHDD(double scoreHDD) {
        this.scoreHDD = scoreHDD;
    }

    public double getScoreRAM() {
        return scoreRAM;
    }

    public void setScoreRAM(double scoreRAM) {
        this.scoreRAM = scoreRAM;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getScoreHASH() {
        return scoreHASH;
    }

    public void setScoreHASH(double scoreHASH) {
        this.scoreHASH = scoreHASH;
    }
}
