package com.ciuciu.footballhighlight.model;

public class League {

    private String countryName;
    private String countryId;
    private String leagueName;
    private String leagueId;

    public League(String countryName, String countryId,
                  String leagueName, String leagueId) {

        this.countryName = countryName;
        this.countryId = countryId;
        this.leagueName = leagueName;
        this.leagueId = leagueId;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getLeagueId() {
        return leagueId;
    }
}
