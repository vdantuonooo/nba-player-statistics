package com.unisa.dev.nbastats.models;

import java.io.Serializable;

public class TeamModel implements Serializable {
    String teamName;
    Integer teamLogo;

    public TeamModel(String teamName, Integer teamLogo) {
        this.teamName = teamName;
        this.teamLogo = teamLogo;
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(Integer teamLogo) {
        this.teamLogo = teamLogo;
    }
}
