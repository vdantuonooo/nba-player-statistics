package com.unisa.dev.nbastats.api;

import com.unisa.dev.nbastats.models.LoginModel;
import com.unisa.dev.nbastats.models.MessageModel;
import com.unisa.dev.nbastats.models.PlayerModel;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNBAStats{

    private static Retrofit retrofit;
    private static String URL = "http://192.168.1.90:8888/";
    private OnPlayerReceived onPlayerListener;
    private OnAccountSigned onAccountSigned;
    private OnPostLogin onPostLoginListener;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static abstract class ResponseHandler<T> implements Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful()){
                T body = response.body();
                onResponse(body);
            }else{
                try {
                    String jsonError = response.errorBody().string();
                    JSONObject errorObject = new JSONObject(jsonError);
                    onError(new ApiException(errorObject.getInt("error_code"), errorObject.getString("error_message")));
                } catch (Exception e) {
                    onError(e);
                }
            }
        }
        @Override
        public void onFailure(Call<T> call, Throwable t) {
            onError(t);
        }
        abstract void onResponse(T response);
        abstract void onError(Throwable error);
    }


    public void getPlayerInfo(String teamAbbrevation){
        NBAStatsAPI nbaStatsAPI = RetrofitNBAStats.getClient().create(NBAStatsAPI.class);
        Call<List<PlayerModel>> call = nbaStatsAPI.getPlayerInfo(teamAbbrevation);

        call.enqueue(new ResponseHandler<List<PlayerModel>>() {
            @Override
            void onResponse(List<PlayerModel> response) {
                onPlayerListener.OnPlayerReceivedListener(response);
            }

            @Override
            void onError(Throwable error) {
                onPlayerListener.onError(error);
            }
        });
    }

    public void getSpecifiedYear(String teamAbbrevation, String season){
        NBAStatsAPI nbaStatsAPI = RetrofitNBAStats.getClient().create(NBAStatsAPI.class);
        Call<List<PlayerModel>> call = nbaStatsAPI.getSpecifyYear(teamAbbrevation, season);

        call.enqueue(new ResponseHandler<List<PlayerModel>>() {
            @Override
            void onResponse(List<PlayerModel> response) {
                onPlayerListener.OnPlayerReceivedListener(response);
            }

            @Override
            void onError(Throwable error) {
                onPlayerListener.onError(error);
            }
        });
    }

    public void getSpecifiedPlayerWithTeam(String teamAbbreviation, String playerName){
        NBAStatsAPI nbaStatsAPI = RetrofitNBAStats.getClient().create(NBAStatsAPI.class);
        Call<List<PlayerModel>> call = nbaStatsAPI.getSpecifiedPlayerWithTeam(teamAbbreviation, playerName);

        call.enqueue(new ResponseHandler<List<PlayerModel>>() {
            @Override
            void onResponse(List<PlayerModel> response) {
                onPlayerListener.OnPlayerReceivedListener(response);
            }

            @Override
            void onError(Throwable error) {
                onPlayerListener.onError(error);
            }
        });
    }

    public void postCreateAccount(String email, String password){
        NBAStatsAPI nbaStatsAPI = RetrofitNBAStats.getClient().create(NBAStatsAPI.class);
        Call<MessageModel> call = nbaStatsAPI.postCreateUser(email, password);

        call.enqueue(new ResponseHandler<MessageModel>() {
            @Override
            void onResponse(MessageModel response) {
                onAccountSigned.onAccountSigned(response);
            }

            @Override
            void onError(Throwable error) {
                onAccountSigned.onError(error);
            }
        });
    }

    public void postLogin(String email, String password){
        NBAStatsAPI nbaStatsAPI = RetrofitNBAStats.getClient().create(NBAStatsAPI.class);
        Call<LoginModel> call = nbaStatsAPI.postLogin(email, password);
        call.enqueue(new ResponseHandler<LoginModel>() {
            @Override
            void onResponse(LoginModel response) {
                onPostLoginListener.onPostLogin(response);
            }

            @Override
            void onError(Throwable error) {
                onPostLoginListener.onError(error);
            }
        });
    }


    public void getAllHystoryPlayer(String namePlayer){
        NBAStatsAPI nbaStatsAPI = RetrofitNBAStats.getClient().create(NBAStatsAPI.class);
        Call<List<PlayerModel>> call = nbaStatsAPI.getAllHistoryPlayer(namePlayer);

        call.enqueue(new ResponseHandler<List<PlayerModel>>() {
            @Override
            void onResponse(List<PlayerModel> response) {
                onPlayerListener.OnPlayerReceivedListener(response);
            }

            @Override
            void onError(Throwable error) {
                onPlayerListener.onError(error);
            }
        });


    }

    public interface OnErrorListener{
        void onError(Throwable error);
    }

    public interface OnPlayerReceived extends OnErrorListener{
        void OnPlayerReceivedListener(List<PlayerModel> playerModelList);
    }

    public void setOnPlayerListener(OnPlayerReceived onPlayerListener){
        this.onPlayerListener = onPlayerListener;
    }

    public interface OnAccountSigned extends OnErrorListener{
        void onAccountSigned(MessageModel messageModel);
    }

    public void setOnAccountSigned(OnAccountSigned onAccountSigned){
        this.onAccountSigned = onAccountSigned;
    }

    public interface OnPostLogin extends OnErrorListener{
        void onPostLogin(LoginModel loginModel);
    }

    public void setOnPostLoginListener(OnPostLogin onPostLoginListener){
        this.onPostLoginListener = onPostLoginListener;
    }

}
