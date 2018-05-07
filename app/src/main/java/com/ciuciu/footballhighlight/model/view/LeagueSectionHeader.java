package com.ciuciu.footballhighlight.model.view;

import android.support.annotation.NonNull;

import com.ciuciu.footballhighlight.feature.common.adapter.sectionedadapter.Section;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class LeagueSectionHeader implements Section<Match> {

    private List<Match> matchList;
    private String countryId;
    private String countryName;
    private String leagueId;
    private String leagueName;
    private Date mDate;

    public LeagueSectionHeader(String countryId, String countryName, String leagueId, String leagueName) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.matchList = new ArrayList<>();
    }

    @Override
    public List<Match> getChildItems() {
        return matchList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeagueSectionHeader that = (LeagueSectionHeader) o;
        return Objects.equals(countryId, that.countryId) &&
                Objects.equals(countryName, that.countryName) &&
                Objects.equals(leagueId, that.leagueId) &&
                Objects.equals(leagueName, that.leagueName) &&
                Objects.equals(mDate, that.mDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, countryName, leagueId, leagueName, mDate);
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public static boolean isBelongOfLeague(@NonNull Match match, @NonNull LeagueSectionHeader leagueSectionHeader) {
        return (match.getCountryId().equals(leagueSectionHeader.countryId) &&
                match.getLeagueId().equals(leagueSectionHeader.leagueId));
    }
}
