package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @Expose
    @SerializedName("uname")
    private String uname;
    @Expose
    @SerializedName("upass")
    private String upass;
    @Expose
    @SerializedName("real_name")
    private String real_name;
    @Expose
    @SerializedName("uphone")
    private String uphone;
    @Expose
    @SerializedName("uemail")
    private String uemail;
    @Expose
    @SerializedName("balance")
    private String balance;
    @Expose
    @SerializedName("status")
    private String status;

    public Users(String uname, String upass, String real_name, String uphone, String uemail, String balance) {
        this.uname = uname;
        this.upass = upass;
        this.real_name = real_name;
        this.uphone = uphone;
        this.uemail = uemail;
        this.balance = balance;
    }

    public Users(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getUname() {
        return uname;
    }

    public String getUpass() {
        return upass;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getUphone() {
        return uphone;
    }

    public String getUemail() {
        return uemail;
    }

    public String getBalance() {
        return balance;
    }
}
