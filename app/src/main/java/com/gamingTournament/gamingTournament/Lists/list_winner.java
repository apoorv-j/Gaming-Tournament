package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class list_winner {

    @Expose
    @SerializedName("winner_uname")
    private String winner_uname;
    @Expose
    @SerializedName("winner_game_id")
    private String winnerName;
    @Expose
    @SerializedName("status")
    private String status;

    public list_winner(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public list_winner( String winner_uname, String winnerName) {
        this.winner_uname = winner_uname;
        this.winnerName = winnerName;
    }

    public String getWinner_uname() {
        return winner_uname;
    }


    public String getWinnerName() {
        return winnerName;
    }



}
