package com.gamingTournament.gamingTournament.Lists;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.content.ContentValues.TAG;

public class list_play {


    @Expose
    @SerializedName("match_id")
    private String matchID;
    @Expose
    @SerializedName("match_title")
    private String matchTitle;
    @Expose
    @SerializedName("date_time")
    private String dateTime;
    @Expose
    @SerializedName("entry_fee")
    private String entryFee;
    @Expose
    @SerializedName("team_size")
    private String teamSize;
    @Expose
    @SerializedName("mode")
    private String mode;
    @Expose
    @SerializedName("kill_prize")
    private String killPrize;
    @Expose
    @SerializedName("win_prize")
    private String winPrize;
    @Expose
    @SerializedName("map")
    private String map;
    @Expose
    @SerializedName("players_joined")
    private String playerJoined;
    @Expose
    @SerializedName("max_players")
    private String maxPlayers;
    @Expose
    @SerializedName("entry_status")
    private String entryStatus;
    @Expose
    @SerializedName("youtube_link")
    private String youtube;
    @Expose
    @SerializedName("status")
    private String status;

    public list_play(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    //constructor for mini militia

    public list_play(String matchID, String matchTitle, String dateTime, String entryFee, String winPrize, String map, String playerJoined, String maxPlayers, String entryStatus) {
        this.matchID = matchID;
        this.matchTitle = matchTitle;
        this.dateTime = dateTime;
        this.entryFee = entryFee;
        this.winPrize = winPrize;
        this.map = map;
        this.playerJoined = playerJoined;
        this.maxPlayers = maxPlayers;
        this.entryStatus = entryStatus;
    }

    //constructor for ludo
    public list_play(String matchID, String matchTitle, String dateTime, String entryFee, String winPrize, String playerJoined, String maxPlayers, String entryStatus) {
        this.matchID = matchID;
        this.matchTitle = matchTitle;
        this.dateTime = dateTime;
        this.entryFee = entryFee;
        this.winPrize = winPrize;
        this.playerJoined = playerJoined;
        this.maxPlayers = maxPlayers;
        this.entryStatus = entryStatus;
    }

    //constructor for pubg/freefire
    public list_play(String matchID, String matchTitle, String dateTime, String entryFee, String teamSize, String mode, String killPrize, String winPrize, String map, String playerJoined, String maxPlayers, String entryStatus, String youtube) {
        this.matchID = matchID;
        this.matchTitle = matchTitle;
        this.dateTime = dateTime;
        this.entryFee = entryFee;
        this.teamSize = teamSize;
        this.mode = mode;
        this.killPrize = killPrize;
        this.winPrize = winPrize;
        this.map = map;
        this.playerJoined = playerJoined;
        this.maxPlayers = maxPlayers;
        this.entryStatus = entryStatus;

        this.youtube = youtube;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getMaxPlayers() {
        return maxPlayers;
    }


    public String getTeamSize() {
        return teamSize;
    }

    public String getMatchID() {
        return matchID;
    }

    public String getMatchTitle() {
        String Title;
        Title = matchTitle.toString();
        return Title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public String getMode() {
        return mode;
    }

    public String getKillPrize() {
        return killPrize;
    }

    public String getWinPrize() {
        return winPrize;
    }

    public String getMap() {
        return map;
    }

    public String getPlayerJoined() {
        return playerJoined;
    }

    public String getEntryStatus() {
        return entryStatus;
    }


}
