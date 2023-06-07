package com.unisa.dev.nbastats.utilities;

public class StringConverter {

    private static StringConverter instance;

    private StringConverter() {

    }

    public static StringConverter getInstance() {
        if (instance == null) {
            instance = new StringConverter();
        }
        return instance;
    }


    public String getAbbreviatedString(String teamName){
        String abbreviation;

        switch (teamName) {
            case "Atlanta Hawks":
                abbreviation = "ATL";
                break;
            case "Boston Celtics":
                abbreviation = "BOS";
                break;
            case "Brooklyn Nets":
                abbreviation = "BKN";
                break;
            case "Charlotte Hornets":
                abbreviation = "CHA";
                break;
            case "Chicago Bulls":
                abbreviation = "CHI";
                break;
            case "Cleveland Cavaliers":
                abbreviation = "CLE";
                break;
            case "Dallas Mavericks":
                abbreviation = "DAL";
                break;
            case "Denver Nuggets":
                abbreviation = "DEN";
                break;
            case "Detroit Pistons":
                abbreviation = "DET";
                break;
            case "Golden State Warriors":
                abbreviation = "GSW";
                break;
            case "Houston Rockets":
                abbreviation = "HOU";
                break;
            case "Indiana Pacers":
                abbreviation = "IND";
                break;
            case "LA Clippers":
                abbreviation = "LAC";
                break;
            case "Los Angeles Lakers":
                abbreviation = "LAL";
                break;
            case "Memphis Grizzlies":
                abbreviation = "MEM";
                break;
            case "Miami Heat":
                abbreviation = "MIA";
                break;
            case "Milwaukee Bucks":
                abbreviation = "MIL";
                break;
            case "Minnesota Timberwolves":
                abbreviation = "MIN";
                break;
            case "New Orleans Pelicans":
                abbreviation = "NOP";
                break;
            case "New York Knicks":
                abbreviation = "NYK";
                break;
            case "Oklahoma City Thunder":
                abbreviation = "OKC";
                break;
            case "Orlando Magic":
                abbreviation = "ORL";
                break;
            case "Philadelphia 76ers":
                abbreviation = "PHI";
                break;
            case "Phoenix Suns":
                abbreviation = "PHX";
                break;
            case "Portland Trail Blazers":
                abbreviation = "POR";
                break;
            case "Sacramento Kings":
                abbreviation = "SAC";
                break;
            case "San Antonio Spurs":
                abbreviation = "SAS";
                break;
            case "Toronto Raptors":
                abbreviation = "TOR";
                break;
            case "Utah Jazz":
                abbreviation = "UTA";
                break;
            case "Washington Wizards":
                abbreviation = "WAS";
                break;
            default:
                abbreviation = "N/A";
                break;
        }

        return abbreviation;
    }


    public String getTeamName(String abbreviation) {
        String teamName;

        switch (abbreviation) {
            case "ATL":
                teamName = "Atlanta Hawks";
                break;
            case "BOS":
                teamName = "Boston Celtics";
                break;
            case "BKN":
                teamName = "Brooklyn Nets";
                break;
            case "CHA":
                teamName = "Charlotte Hornets";
                break;
            case "CHI":
                teamName = "Chicago Bulls";
                break;
            case "CLE":
                teamName = "Cleveland Cavaliers";
                break;
            case "DAL":
                teamName = "Dallas Mavericks";
                break;
            case "DEN":
                teamName = "Denver Nuggets";
                break;
            case "DET":
                teamName = "Detroit Pistons";
                break;
            case "GSW":
                teamName = "Golden State Warriors";
                break;
            case "HOU":
                teamName = "Houston Rockets";
                break;
            case "IND":
                teamName = "Indiana Pacers";
                break;
            case "LAC":
                teamName = "LA Clippers";
                break;
            case "LAL":
                teamName = "Los Angeles Lakers";
                break;
            case "MEM":
                teamName = "Memphis Grizzlies";
                break;
            case "MIA":
                teamName = "Miami Heat";
                break;
            case "MIL":
                teamName = "Milwaukee Bucks";
                break;
            case "MIN":
                teamName = "Minnesota Timberwolves";
                break;
            case "NOP":
                teamName = "New Orleans Pelicans";
                break;
            case "NYK":
                teamName = "New York Knicks";
                break;
            case "OKC":
                teamName = "Oklahoma City Thunder";
                break;
            case "ORL":
                teamName = "Orlando Magic";
                break;
            case "PHI":
                teamName = "Philadelphia 76ers";
                break;
            case "PHX":
                teamName = "Phoenix Suns";
                break;
            case "POR":
                teamName = "Portland Trail Blazers";
                break;
            case "SAC":
                teamName = "Sacramento Kings";
                break;
            case "SAS":
                teamName = "San Antonio Spurs";
                break;
            case "TOR":
                teamName = "Toronto Raptors";
                break;
            case "UTA":
                teamName = "Utah Jazz";
                break;
            case "WAS":
                teamName = "Washington Wizards";
                break;
            default:
                teamName = "N/A";
                break;
        }

        return teamName;
    }
}
