package com.unisa.dev.nbastats.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.adapters.GridTeamsAdapter;
import com.unisa.dev.nbastats.models.TeamModel;
import com.unisa.dev.nbastats.utilities.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements GridTeamsAdapter.OnItemClick {

    private View view;

    private List<TeamModel> teamModels = new ArrayList<>();
    private RecyclerView recyclerView;
    private GridTeamsAdapter gridTeamsAdapter;

    private NavController navController;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        teamModels.clear();
        String[] teamNames = getResources().getStringArray(R.array.teams_name);
        TypedArray teamLogos = getResources().obtainTypedArray(R.array.teams_logo);


        int arrayLength = Math.min(teamNames.length, teamLogos.length());

        for (int i = 0; i < arrayLength; i++) {
            String name = teamNames[i];
            int logoResId = teamLogos.getResourceId(i,0);

            TeamModel team = new TeamModel(name, logoResId);
            teamModels.add(team);
        }

        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        gridTeamsAdapter = new GridTeamsAdapter( teamModels, getContext());
        gridTeamsAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(gridTeamsAdapter);


    }

    @Override
    public void onItemClick(TeamModel teamModel) {

        String abbrevietedString = StringConverter.getInstance().getAbbreviatedString(teamModel.getTeamName());

        Bundle b = new Bundle();
        b.putSerializable("teamModel", teamModel);

        navController.navigate(R.id.action_homeFragment_to_teamPlayersFragment, b);
    }
}