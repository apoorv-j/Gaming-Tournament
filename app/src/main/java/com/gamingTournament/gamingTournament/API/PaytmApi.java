package com.gamingTournament.gamingTournament.API;

import com.gamingTournament.gamingTournament.Lists.Checksum;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaytmApi {

    String BASE_URL = "http://pubg.purplebytesolutions.com/paytm/";

    @FormUrlEncoded
    @POST("generateChecksum.php")
    Call<Checksum> getChecksum(
            @Field("MID") String mId,
            @Field("ORDER_ID") String orderId,
            @Field("CUST_ID") String custId,
            @Field("CHANNEL_ID") String channelId,
            @Field("TXN_AMOUNT") String txnAmount,
            @Field("WEBSITE") String website,
            @Field("CALLBACK_URL") String callbackUrl,
            @Field("INDUSTRY_TYPE_ID") String industryTypeId
    );

    @FormUrlEncoded
    @POST("transactionStatus.php")
    Call<ResponseBody> getStatus(
            @Field("orderId") String orderId,
            @Field("merchantMid") String merchantMid
    );


}
