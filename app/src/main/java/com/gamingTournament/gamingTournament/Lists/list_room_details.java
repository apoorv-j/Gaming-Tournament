package com.gamingTournament.gamingTournament.Lists;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class list_room_details {

    @Expose
    @SerializedName("room_id")
    String roomID;

    @Expose
    @SerializedName("room_pass")
    String roomPass;

    @Expose
    @SerializedName("status")
    private String status;



    public list_room_details(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public list_room_details(String roomID, String roomPass) {
        this.roomID = roomID;
        this.roomPass = roomPass;
    }


    public String getRoomID() {
        return roomID;
    }

    public String getRoomPass() {
        return roomPass;
    }
}
