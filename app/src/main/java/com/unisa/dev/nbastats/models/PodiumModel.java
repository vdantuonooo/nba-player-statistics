package com.unisa.dev.nbastats.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PodiumModel {

    @SerializedName("name")
    private String name;
    @SerializedName("avg_stat")
    private double avgStat;
    @SerializedName("NBAID")
    private int NBAID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvgStat() {
        return avgStat;
    }

    public void setAvgStat(double avgStat) {
        this.avgStat = avgStat;
    }

    public int getNBAID() {
        return NBAID;
    }

    public void setNBAID(int NBAID) {
        this.NBAID = NBAID;
    }
}