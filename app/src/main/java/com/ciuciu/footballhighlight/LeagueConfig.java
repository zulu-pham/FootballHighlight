package com.ciuciu.footballhighlight;

import com.ciuciu.footballhighlight.model.League;

import java.util.Arrays;
import java.util.List;

public class LeagueConfig {

    private static List<League> leagueList = Arrays.asList(
            //new League("Champions League", "163", "All", null),
            //new League("Europa League", "164", "All", null),
            //new League("England", "169", "Premier League", "62"),

            new League("England", "169", "Championship", "63")
            //new League("France", "173", "Ligue 2", "128")
    );

    public static List<League> getLeagueList() {
        return leagueList;
    }

}
