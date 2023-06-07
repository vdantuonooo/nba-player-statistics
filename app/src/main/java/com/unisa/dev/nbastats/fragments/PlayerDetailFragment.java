package com.unisa.dev.nbastats.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.models.PlayerModel;
import com.unisa.dev.nbastats.models.TeamModel;
import com.unisa.dev.nbastats.utilities.StringConverter;


public class PlayerDetailFragment extends Fragment {

    private View view;
    private TextView playerName;
    private Bundle bundle;
    private PlayerModel playerModel;

    private NavController navController;

    private TextView teamName,eta, nation, altezza, peso, annoDraft,
            roundDraft, draftNumber, gamePlayed;
    private TextView pts, reb, assist, season;
    private ImageView imageView, backarrow;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player_detail, container, false);

        playerName = view.findViewById(R.id.playerName);

        bundle = getArguments();

        teamName = view.findViewById(R.id.teamName);
        eta = view.findViewById(R.id.eta);
        nation = view.findViewById(R.id.nation);
        altezza = view.findViewById(R.id.altezza);
        peso = view.findViewById(R.id.peso);
        annoDraft = view.findViewById(R.id.annoDraft);
        roundDraft = view.findViewById(R.id.roundDraft);
        draftNumber = view.findViewById(R.id.draftNumber);
        gamePlayed = view.findViewById(R.id.gamePlayed);
        pts = view.findViewById(R.id.pts);
        reb = view.findViewById(R.id.reb);
        assist = view.findViewById(R.id.assist);
        season = view.findViewById(R.id.season);

        backarrow = view.findViewById(R.id.backarrow);

        imageView= view.findViewById(R.id.playerPhoto);



        if(bundle!=null){
            playerModel = (PlayerModel) bundle.getSerializable("playerSelected");
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        playerName.setText(playerModel.getName());
        teamName.setText(StringConverter.getInstance().getTeamName(playerModel.getTeamAbbrevation()));
        eta.setText(""+playerModel.getAge());
        nation.setText(playerModel.getCountry());
        altezza.setText(""+Math.round(playerModel.getPlayerHeight()) + "cm");
        peso.setText(""+Math.round(playerModel.getPlayerWeight()) + "kg");

        if(playerModel.getDraftYear()!=null){
            annoDraft.setText(""+playerModel.getDraftYear());
        }else{
            annoDraft.setText("N/A");
        }
        if(playerModel.getDraftRound()!=null){
            roundDraft.setText(""+playerModel.getDraftRound());
        }else{
            roundDraft.setText("N/A");
        }
        if(playerModel.getDraftRound()!=null) {
            draftNumber.setText("" + playerModel.getDraftNumber());
        }else{
            draftNumber.setText("N/A");
        }
        gamePlayed.setText(""+playerModel.getGp());
        pts.setText(""+playerModel.getPts());
        reb.setText(""+playerModel.getReb());
        assist.setText(""+playerModel.getAst());
        season.setText(""+playerModel.getSeason());

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });


        String URL = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+playerModel.getNBAID()+".png";
        Glide.with(getContext())
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(imageView);



    }
}