package com.unisa.dev.nbastats.models;
import com.google.gson.annotations.SerializedName;

public class PlayerModel {
    @SerializedName("name")
    private String name;
    @SerializedName("team_abbrevation")
    private String teamAbbrevation;
    @SerializedName("age")
    private Integer age;
    @SerializedName("player_height")
    private Double playerHeight;
    @SerializedName("player_weight")
    private Double playerWeight;
    @SerializedName("college")
    private String college;
    @SerializedName("country")
    private String country;
    @SerializedName("draft_round")
    private Integer draftRound;
    @SerializedName("draft_number")
    private Integer draftNumber;
    @SerializedName("gp")
    private Integer gp;
    @SerializedName("pts")
    private Double pts;
    @SerializedName("reb")
    private Double reb;
    @SerializedName("ast")
    private Double ast;
    @SerializedName("season")
    private String season;
    @SerializedName("NBAID")
    private String NBAID;


    public String getNBAID() {
        return NBAID;
    }

    public void setNBAID(String NBAID) {
        this.NBAID = NBAID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamAbbrevation() {
        return teamAbbrevation;
    }

    public void setTeamAbbrevation(String teamAbbrevation) {
        this.teamAbbrevation = teamAbbrevation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(double playerHeight) {
        this.playerHeight = playerHeight;
    }

    public double getPlayerWeight() {
        return playerWeight;
    }

    public void setPlayerWeight(double playerWeight) {
        this.playerWeight = playerWeight;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDraftRound() {
        return draftRound;
    }

    public void setDraftRound(int draftRound) {
        this.draftRound = draftRound;
    }

    public int getDraftNumber() {
        return draftNumber;
    }

    public void setDraftNumber(int draftNumber) {
        this.draftNumber = draftNumber;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }

    public double getPts() {
        return pts;
    }

    public void setPts(double pts) {
        this.pts = pts;
    }

    public double getReb() {
        return reb;
    }

    public void setReb(double reb) {
        this.reb = reb;
    }

    public double getAst() {
        return ast;
    }

    public void setAst(double ast) {
        this.ast = ast;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

}