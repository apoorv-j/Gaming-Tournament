package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class list_match_results {

    @Expose
    @SerializedName("match_id")
    private int matchID;
    @Expose
    @SerializedName("uname")
    private String username;
    @Expose
    @SerializedName("player_pubg_id")
    private String playerID;
    @Expose
    @SerializedName("kills")
    private String kills;
    @Expose
    @SerializedName("status")
    private String status;

    public list_match_results(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public list_match_results(int matchID, String username, String playerID, String kills) {
        this.matchID = matchID;
        this.username = username;
        this.playerID = playerID;
        this.kills = kills;
    }



    public String getMatchID() {
        return String.valueOf(matchID);
    }


    public String getPlayerID() {
        return playerID;
    }


    public String getKills() {
        return kills;
    }


}
