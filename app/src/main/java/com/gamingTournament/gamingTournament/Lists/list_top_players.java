package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class list_top_players {

    @Expose
    @SerializedName("uname")
    private String user_names;
    @Expose
    @SerializedName("position")
    private String position;
    @Expose
    @SerializedName("money_earned")
    String money_earned;
    @Expose
    @SerializedName("status")
    private String status;

    public list_top_players(String user_names, String position, String money_earned) {
        this.user_names = user_names;
        this.position = position;
        this.money_earned = money_earned;
    }

    public list_top_players(String status) {
        this.status = status;
    }

    public String getUser_names() {
        return user_names;
    }

    public String getPosition() {
        return position;
    }

    public String getMoney_earned() {
        return money_earned;
    }

    public String getStatus() {
        return status;
    }
}
