package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class update_details {

    @Expose
    @SerializedName("ver")
    private String version;
    @Expose
    @SerializedName("update_url")
    private String update_url;
    @Expose
    @SerializedName("critical_update")
    private String critical_update;

    public update_details(String version, String update_url, String critical_update) {
        this.version = version;
        this.update_url = update_url;
        this.critical_update = critical_update;
    }

    public String getVersion() {
        return version;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public String getCritical_update() {
        return critical_update;
    }
}
