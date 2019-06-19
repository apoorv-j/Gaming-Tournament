package com.gamingTournament.gamingTournament.Lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class list_events {

    @Expose
    @SerializedName("text_data")
    private String textData;
    @Expose
    @SerializedName("link")
    private String link;
    @Expose
    @SerializedName("image_url")
    private String imageURL;
    @Expose
    @SerializedName("status")
    private String status;

    public list_events(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public list_events(String textData, String link, String imageURL) {
        this.textData = textData;
        this.link = link;
        this.imageURL = imageURL;
    }

    public String getTextData() {
        return textData;
    }

    public String getLink() {
        return link;
    }

    public String getImageURL() {
        return imageURL;
    }

}
