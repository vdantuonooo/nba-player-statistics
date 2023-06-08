package com.unisa.dev.nbastats.api;

import com.unisa.dev.nbastats.models.PlayerModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<Void> postCreateUser(
            @Field("email") String email,
            @Field("password") String password
    );



}
