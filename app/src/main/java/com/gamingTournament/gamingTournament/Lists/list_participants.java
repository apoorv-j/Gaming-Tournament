package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class list_participants {
    @Expose
    @SerializedName("uname")
    private String user_names;
    @Expose
    @SerializedName("players")
    private String in_game_names;
    @Expose
    @SerializedName("status")
    private String status;

    public list_participants(String user_names, String in_game_names) {
        this.user_names = user_names;
        this.in_game_names = in_game_names;
    }

    public list_participants(String status) {
        this.status = status;
    }

    public String getUser_names() {
        return user_names;
    }

    public String getIn_game_names() {
        return in_game_names;
    }

    public String getStatus() {
        return status;
    }
}
