package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class list_transactions {
    @Expose
    @SerializedName("uname")
    private String user_names;
    @Expose
    @SerializedName("amnt")
    private String amount;
    @Expose
    @SerializedName("utype")
    String trans_type;
    @Expose
    @SerializedName("udescription")
    private String trans_description;
    @Expose
    @SerializedName("time_stamp")
    String time_stamp;
    @Expose
    @SerializedName("status")
    private String status;

    public list_transactions(String user_names, String amount, String trans_type, String trans_description, String time_stamp) {
        this.user_names = user_names;
        this.amount = amount;
        this.trans_type = trans_type;
        this.trans_description = trans_description;
        this.time_stamp = time_stamp;
    }

    public list_transactions(String status) {
        this.status = status;
    }

    public String getUser_names() {
        return user_names;
    }

    public String getAmount() {
        return amount;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public String getTrans_description() {
        return trans_description;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public String getStatus() {
        return status;
    }
}
