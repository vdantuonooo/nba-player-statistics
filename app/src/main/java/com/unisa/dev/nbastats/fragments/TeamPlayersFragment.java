package com.unisa.dev.nbastats.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.adapters.AdapterPlayers;
import com.unisa.dev.nbastats.api.RetrofitNBAStats;
import com.unisa.dev.nbastats.models.PlayerModel;
import com.unisa.dev.nbastats.models.TeamModel;
import com.unisa.dev.nbastats.utilities.StringConverter;

import java.util.Collections;
import java.util.List;

public class TeamPlayersFragment extends Fragment implements RetrofitNBAStats.OnPlayerReceived {

    private View view;
    private Bundle bundle;
    private TeamModel receivedTeamModel;

    private ProgressBar progressBar;
    private NavController navController;


    private ImageView leftLogo, rightLogo, backarrow;
    private TextView teamName;
    private RecyclerView recyclerViewPlayers;
    private AdapterPlayers adapterPlayers;



    private RetrofitNBAStats retrofitNBAStats;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_players_list, container, false);

        leftLogo = view.findViewById(R.id.teamLogoLeft);
        rightLogo = view.findViewById(R.id.teamLogoRight);
        backarrow = view.findViewById(R.id.backarrow);
        progressBar = view.findViewById(R.id.progressBar);

        retrofitNBAStats = new RetrofitNBAStats();

        recyclerViewPlayers = view.findViewById(R.id.recyclerViewPlayers);

        retrofitNBAStats.setOnPlayerListener(this);

        teamName = view.findViewById(R.id.teamName);

        bundle = getArguments();

        if(bundle!=null){
            receivedTeamModel = (TeamModel) bundle.getSerializable("teamModel");
        }

        retrofitNBAStats.getPlayerInfo(StringConverter.getInstance().getAbbreviatedString(receivedTeamModel.getTeamName()));


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        leftLogo.setImageResource(receivedTeamModel.getTeamLogo());
        rightLogo.setImageResource(receivedTeamModel.getTeamLogo());
        teamName.setText(receivedTeamModel.getTeamName());

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });


    }

    @Override
    public void onError(Throwable error) {

    }


    @Override
    public void OnPlayerReceivedListener(List<PlayerModel> playerModelList) {
        progressBar.setVisibility(View.GONE);
        List<PlayerModel> list = playerModelList;

        Collections.reverse(list);


        recyclerViewPlayers.setVisibility(View.VISIBLE);
        recyclerViewPlayers.setHasFixedSize(true);
        recyclerViewPlayers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPlayers = new AdapterPlayers(list, getContext());
        recyclerViewPlayers.setAdapter(adapterPlayers);


    }
}