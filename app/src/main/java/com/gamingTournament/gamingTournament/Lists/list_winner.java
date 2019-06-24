package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class list_winner {

    @Expose
    @SerializedName("winner_uname")
    private String winner_uname;
    @Expose
    @SerializedName("status")
    private String status;

    public list_winner(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public list_winner( String winner_uname, String placeholder) {
        this.winner_uname = winner_uname;
    }

    public String getWinnerName() {
        return winner_uname;
    }

}
