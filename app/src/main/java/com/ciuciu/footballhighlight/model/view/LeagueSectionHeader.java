package com.ciuciu.footballhighlight.model.view;

import android.support.annotation.NonNull;

import com.ciuciu.footballhighlight.feature.common.adapter.sectionedadapter.Section;

import java.util.ArrayList;
import java.util.List;

public class LeagueSectionHeader implements Section<Match> {

    private List<Match> matchList;
    private String countryId;
    private String countryName;
    private String leagueId;
    private String leagueName;


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

    public static boolean isBelongOfLeague(@NonNull Match match, @NonNull LeagueSectionHeader leagueSectionHeader) {
        return (match.getCountryId().equals(leagueSectionHeader.countryId) &&
                match.getLeagueId().equals(leagueSectionHeader.leagueId));
    }
}
