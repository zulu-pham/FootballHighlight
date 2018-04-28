package com.ciuciu.footballhighlight.model.view;

import com.ciuciu.footballhighlight.model.entity.MatchEntity;

public class Match {

    private String matchId;
    private String countryId;
    private String countryName;
    private String leagueId;
    private String leagueName;
    private String matchDate;
    private String matchStatus;
    private String matchTime;

    private String matchHomeTeamName;
    private String matchHomeTeamScore;
    private String matchAwayTeamName;
    private String matchAwayTeamScore;

    private String matchHomeTeamHalfTimeScore;
    private String matchAwayTeamHalfTimeScore;

    private String matchLive;

    private Video video;

    public Match(MatchEntity entity) {
        matchId = entity.getMatch_id();
        countryId = entity.getCountry_id();
        countryName = entity.getCountry_name();
        leagueId = entity.getLeague_id();
        leagueName = entity.getLeague_name();
        matchDate = entity.getMatch_date();
        matchStatus = entity.getMatch_status();
        matchTime = entity.getMatch_time();

        matchHomeTeamName = entity.getMatch_hometeam_name();
        matchHomeTeamScore = entity.getMatch_hometeam_score();
        matchAwayTeamName = entity.getMatch_awayteam_name();
        matchAwayTeamScore = entity.getMatch_awayteam_score();

        matchHomeTeamHalfTimeScore = entity.getMatch_hometeam_halftime_score();
        matchAwayTeamHalfTimeScore = entity.getMatch_awayteam_halftime_score();
        matchLive = entity.getMatch_live();
    }

    public String getMatchId() {
        return matchId;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public String getMatchHomeTeamName() {
        return matchHomeTeamName;
    }

    public String getMatchHomeTeamScore() {
        return matchHomeTeamScore;
    }

    public String getMatchAwayTeamName() {
        return matchAwayTeamName;
    }

    public String getMatchAwayTeamScore() {
        return matchAwayTeamScore;
    }

    public String getMatchHomeTeamHalfTimeScore() {
        return matchHomeTeamHalfTimeScore;
    }

    public String getMatchAwayTeamHalfTimeScore() {
        return matchAwayTeamHalfTimeScore;
    }

    public String getMatchLive() {
        return matchLive;
    }

    public Video getVideo() {
        return video;
    }
}
