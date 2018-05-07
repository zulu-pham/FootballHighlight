package com.ciuciu.footballhighlight.model.view;

import com.ciuciu.footballhighlight.model.entity.MatchEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Match {

    private String matchId;
    private String countryId;
    private String countryName;
    private String leagueId;
    private String leagueName;

    private Date matchDate;
    private String matchStatus;

    private String homeTeamName;
    private String homeTeamScore;
    private String awayTeamName;
    private String awayTeamScore;

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
        matchDate = parseDate(entity.getMatch_date(), entity.getMatch_time());
        matchStatus = entity.getMatch_status();

        homeTeamName = entity.getMatch_hometeam_name();
        homeTeamScore = entity.getMatch_hometeam_score();
        awayTeamName = entity.getMatch_awayteam_name();
        awayTeamScore = entity.getMatch_awayteam_score();

        matchHomeTeamHalfTimeScore = entity.getMatch_hometeam_halftime_score();
        matchAwayTeamHalfTimeScore = entity.getMatch_awayteam_halftime_score();
        matchLive = entity.getMatch_live();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(matchId, match.matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId);
    }

    public static Date parseDate(String date, String time) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+02");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setTimeZone(timeZone);
        try {
            return dateFormat.parse(date + " " + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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

    public Date getMatchDate() {
        return matchDate;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public String getMatchHomeTeamName() {
        return homeTeamName;
    }

    public String getMatchHomeTeamScore() {
        return homeTeamScore;
    }

    public String getMatchAwayTeamName() {
        return awayTeamName;
    }

    public String getMatchAwayTeamScore() {
        return awayTeamScore;
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
