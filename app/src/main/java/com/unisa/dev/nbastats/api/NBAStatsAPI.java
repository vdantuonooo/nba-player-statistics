package com.unisa.dev.nbastats.api;

import com.unisa.dev.nbastats.models.LoginModel;
import com.unisa.dev.nbastats.models.MessageModel;
import com.unisa.dev.nbastats.models.PlayerModel;
import com.unisa.dev.nbastats.models.PodiumModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NBAStatsAPI {

    @GET("players.php")
    Call<List<PlayerModel>> getPlayerInfo(
            @Query("team_abbreviation") String teamAbbrevation
    );


    @GET("specifiedTeam.php")
    Call<List<PlayerModel>> getSpecifyYear(
            @Query("team_abbreviation") String teamAbbrevation,
            @Query("season") String season
    );


    @GET("specifiedPlayerWithTeam.php")
    Call<List<PlayerModel>> getSpecifiedPlayerWithTeam(
            @Query("team_abbreviation") String teamAbbrevation,
            @Query("player_name") String teamPlayer
    );

    @FormUrlEncoded
    @POST("createUser.php")
    Call<MessageModel> postCreateUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("findUserLogin.php")
    Call<LoginModel> postLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("allHystoryPlayer.php")
    Call<List<PlayerModel>> getAllHistoryPlayer(
            @Query("player_name") String teamPlayer
    );




    @GET("statsAssistForTeam.php")
    Call<List<PodiumModel>> getStatsAssistForTeam(
            @Query("team_abbreviation") String teamAbbrevation
    );

    @GET("statsReboundsForTeam.php")
    Call<List<PodiumModel>> getStatsReboundForTeam(
            @Query("team_abbreviation") String teamAbbrevation
    );

    @GET("statsPointsForTeam.php")
    Call<List<PodiumModel>> getStatsPointsForTeam(
            @Query("team_abbreviation") String teamAbbrevation
    );




}
