package com.gamingTournament.gamingTournament.API;

import com.gamingTournament.gamingTournament.Lists.Users;
import com.gamingTournament.gamingTournament.Lists.list_events;
import com.gamingTournament.gamingTournament.Lists.list_match_results;
import com.gamingTournament.gamingTournament.Lists.list_participants;
import com.gamingTournament.gamingTournament.Lists.list_play;
import com.gamingTournament.gamingTournament.Lists.list_room_details;
import com.gamingTournament.gamingTournament.Lists.list_winner;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    //PUBG
    @GET("pubg/match-details.php")
    Call<List<list_play>> matchDetails(@Query("salt") String salt);

    @GET("pubg/match-kills.php")
    Call<List<list_match_results>> matchResults(@Query("salt") String salt);

    @GET("pubg/match-winners.php")
    Call<List<list_winner>> matchWinner(@Query("salt") String salt);

    @GET("pubg/add-players.php")
    Call<ResponseBody> addPubgPlayers(@Query("salt") String salt,
                                        @Query("uname")String username,
                                      @Query("team_size")String team_size,
                                      @Query("match_id")String matchID,
                                      @Query("players")String playersName);

    //freefire
    @GET("freefire/match-details.php")
    Call<List<list_play>> matchDetailsFreefire(@Query("salt") String salt);

    @GET("freefire/match-kills.php")
    Call<List<list_match_results>> matchResultsFreefire(@Query("salt") String salt);

    @GET("freefire/match-winners.php")
    Call<List<list_winner>> matchWinnerFreefire(@Query("salt") String salt);

    @GET("freefire/add-players.php")
    Call<ResponseBody> addFreefirePlayers(@Query("salt") String salt,
                                      @Query("uname")String username,
                                      @Query("team_size")String team_size,
                                      @Query("match_id")String matchID,
                                      @Query("players")String playersName);

    //Mini Militia
    @GET("minimilitia/match-details.php")
    Call<List<list_play>> matchDetailsMinim(@Query("salt") String salt);

    @GET("minimilitia/match-winners.php")
    Call<List<list_winner>> matchWinnerMinim(@Query("salt") String salt);

    @GET("minimilitia/add-players.php")
    Call<ResponseBody> addMinimPlayers(@Query("salt") String salt,
                                      @Query("uname")String username,
                                      @Query("match_id")String matchID,
                                      @Query("players")String playersName);

    //Ludo
    @GET("ludo/match-details.php")
    Call<List<list_play>> matchDetailsLudo(@Query("salt") String salt);

    @GET("ludo/match-winners.php")
    Call<List<list_winner>> matchWinnerLudo(@Query("salt") String salt);

    @GET("ludo/add-players.php")
    Call<ResponseBody> addLudoPlayers(@Query("salt") String salt,
                                           @Query("uname")String username,
                                           @Query("match_id")String matchID,
                                           @Query("players")String playersName);

    //Register User
    @GET("add-profile.php")
    Call<ResponseBody> createUser(@Query("salt")String salt,
                                  @Query("uname")String username,
                                  @Query("upass")String password,
                                  @Query("real_name")String fullname,
                                  @Query("uphone")String phNumber,
                                  @Query("uemail")String email);

    //User Login
    @GET("user-auth.php")
    Call<Users> getUser(@Query("uname")String username,
                        @Query("upass")String password,
                        @Query("salt")String salt);

    //Check Balance
    @GET("check-balance.php")
    Call<ResponseBody> getBalance(@Query("salt")String salt,
                                  @Query("uname")String username);

    //Add or Sub balance
    @GET("change-balance.php")
    Call<ResponseBody> changeBalance(@Query("salt")String salt,
                                  @Query("uname")String username,
                                     @Query("tobe")String tobe,
                                     @Query("value")String value);

    //Redeem from wallet
    @GET("reedem-request.php")
    Call<ResponseBody> redeemBalance(@Query("salt")String salt,
                                     @Query("uname")String username,
                                     @Query("upass")String password,
                                     @Query("uphone")String paytmNumber,
                                     @Query("reedem_amount")String amount);

    //Forgot password
    @GET("send-otp.php")
    Call<ResponseBody> sendOTP(@Query("salt")String salt,
                                     @Query("uname")String username,
                                     @Query("otp")String otp);


    @GET("reset-pass.php")
    Call<ResponseBody> resetPass(@Query("salt")String salt,
                               @Query("uname")String username,
                                 @Query("new_pass")String password
                               );
    @GET("change-pass.php")
    Call<ResponseBody> changePass(@Query("salt")String salt,
                                 @Query("uname")String username,
                                 @Query("old_pass")String oldPassword,
                                  @Query("new_pass")String newPassword
    );



    //list_events
    @GET("app-events.php")
    Call<List<list_events>> getEvent(@Query("salt")String salt);


    //Room ID,Room Pass
    @GET("freefire/login-data.php")
    Call<List<list_room_details>> freefireRoom(@Query("salt")String salt,
                                               @Query("match_id")String matchID,
                                               @Query("uname")String username);
    @GET("pubg/login-data.php")
    Call<List<list_room_details>> pubgRoom(@Query("salt")String salt,
                                               @Query("match_id")String matchID,
                                               @Query("uname")String username);
    @GET("minimilitia/login-data.php")
    Call<List<list_room_details>> minimRoom(@Query("salt")String salt,
                                               @Query("match_id")String matchID,
                                               @Query("uname")String username);
    @GET("ludo/login-data.php")
    Call<List<list_room_details>> ludoRoom(@Query("salt")String salt,
                                               @Query("match_id")String matchID,
                                               @Query("uname")String username);

    //winner list
    @GET("winner.php")
    Call<List<list_winner>> getWinners(@Query("salt")String salt,
                                       @Query("match_id")String matchID,
                                       @Query("game")String game);

    //participants list
    @GET("participants.php")
    Call<List<list_participants>> getParticipants(@Query("salt")String salt,
                                                  @Query("match_id")String matchID,
                                                  @Query("game")String game);


}
