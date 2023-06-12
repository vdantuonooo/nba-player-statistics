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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.adapters.AdapterPlayers;
import com.unisa.dev.nbastats.api.RetrofitNBAStats;
import com.unisa.dev.nbastats.models.PlayerModel;
import com.unisa.dev.nbastats.models.TeamModel;
import com.unisa.dev.nbastats.utilities.StringConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class TeamPlayersFragment extends Fragment implements RetrofitNBAStats.OnPlayerReceived, AdapterPlayers.OnPlayerClicked {

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
    private Spinner spinner;

    private List<String> completeList = new ArrayList<>();

    private ImageView searchIcon;
    private EditText editTextPlayer;
    private Button buttonSearch;
    private RelativeLayout searchLayout;
    private boolean isVisible = false;

    private RelativeLayout searchButton;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_team_players_list, container, false);

        leftLogo = view.findViewById(R.id.teamLogoLeft);
        rightLogo = view.findViewById(R.id.teamLogoRight);
        backarrow = view.findViewById(R.id.backarrow);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerViewPlayers = view.findViewById(R.id.recyclerViewPlayers);
        spinner = view.findViewById(R.id.spinner);
        teamName = view.findViewById(R.id.teamName);

        searchIcon = view.findViewById(R.id.searchIcon);
        editTextPlayer = view.findViewById(R.id.insertEditText);
        buttonSearch = view.findViewById(R.id.goButton);
        searchLayout = view.findViewById(R.id.searchLayout);

        searchButton = view.findViewById(R.id.searchButton);


        retrofitNBAStats = new RetrofitNBAStats();
        retrofitNBAStats.setOnPlayerListener(this);

        bundle = getArguments();
        if(bundle!=null){
            receivedTeamModel = (TeamModel) bundle.getSerializable("teamModel");
        }

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVisible){
                    searchLayout.setVisibility(View.GONE);
                    isVisible = false;
                }else {
                    searchLayout.setVisibility(View.VISIBLE);
                    isVisible = true;
                }
            }
        });

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

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namePlayer = editTextPlayer.getText().toString();
                String teamName = StringConverter.getInstance().getAbbreviatedString(receivedTeamModel.getTeamName());
                retrofitNBAStats.getSpecifiedPlayerWithTeam( teamName , namePlayer);
                isVisible = false;
                searchLayout.setVisibility(View.GONE);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("team_abbrevation", receivedTeamModel.getTeamName());
                navController.navigate(R.id.action_teamPlayersFragment_to_searchFragment, bundle1);
            }
        });
    }

    @Override
    public void onError(Throwable error) {
        if(getContext()!=null) {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void OnPlayerReceivedListener(List<PlayerModel> playerModelList) {
        progressBar.setVisibility(View.GONE);

        List<PlayerModel> list = playerModelList;
        List<String> season = new ArrayList<>();

        for(int i = 0; i<list.size(); i++){
            season.add( list.get(i).getSeason());
        }

        HashSet<String> uniqueStrings = new HashSet<>(season);
        List<String> uniqueList = new ArrayList<>(uniqueStrings);


        if(uniqueList.size()>2){
            completeList.clear();

            completeList = uniqueList;
            Collections.sort(completeList, new Comparator<String>() {
                @Override
                public int compare(String season1, String season2) {
                    return season2.compareTo(season1);
                }
            });


            completeList.add(0, "none");
            completeList.add("Seleziona tutti gli anni");
        }else{

            uniqueList.add(0, "none");
            uniqueList.add("Seleziona tutti gli anni");
        }

        if(uniqueList.size()<=2) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, completeList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }else{
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, completeList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        Collections.reverse(list);


        recyclerViewPlayers.setVisibility(View.VISIBLE);
        recyclerViewPlayers.setHasFixedSize(true);
        recyclerViewPlayers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterPlayers = new AdapterPlayers(list, getContext());
        adapterPlayers.setOnPlayerClickedListener(this);
        recyclerViewPlayers.setAdapter(adapterPlayers);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(Objects.equals(completeList.get(i), "none")){

                }else if(Objects.equals(completeList.get(i), "Seleziona tutti gli anni")){
                    retrofitNBAStats.getPlayerInfo(StringConverter.getInstance().getAbbreviatedString(receivedTeamModel.getTeamName()));
                    searchLayout.setVisibility(View.GONE);
                    isVisible = false;
                }
                else{
                    retrofitNBAStats.getSpecifiedYear(StringConverter.getInstance().getAbbreviatedString(receivedTeamModel.getTeamName()),
                            completeList.get(i));
                    searchLayout.setVisibility(View.GONE);
                    isVisible = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onPlayerClicked(PlayerModel playerModel) {
        Bundle b = new Bundle();
        b.putSerializable("playerSelected", playerModel);
        navController.navigate(R.id.action_teamPlayersFragment_to_playerDetailFragment, b);

    }
}