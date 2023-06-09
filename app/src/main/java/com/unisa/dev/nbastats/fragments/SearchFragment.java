package com.unisa.dev.nbastats.fragments;

import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.api.RetrofitNBAStats;
import com.unisa.dev.nbastats.models.PodiumModel;
import com.unisa.dev.nbastats.utilities.StringConverter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment implements RetrofitNBAStats.OnGetAssistStats, RetrofitNBAStats.OnGetPointsStats, RetrofitNBAStats.OnGetReboundsStats {

    private View view;

    private ImageView firstPlayer, secondPlayer, thirdPlayer;
    private TextView firstName, secondName, thirdName;
    private TextView firstStats, secondStats, thirdStats;

    private ImageView backarrow;

    private NavController navController;





    private ImageView firstPlayerAssist, secondPlayerAssist, thirdPlayerAssist;
    private TextView firstNameAssist, secondNameAssist, thirdNameAssist;
    private TextView firstStatsAssist, secondStatsAssist, thirdStatsAssist;



    private ImageView firstPlayerRebounds, secondPlayerRebounds, thirdPlayerRebounds;
    private TextView firstNameRebounds, secondNameRebounds, thirdNameRebounds;
    private TextView firstStatsRebounds, secondStatsRebounds, thirdStatsRebounds;

    private TextView teamName;
    private ImageView teamLogo;

    private RetrofitNBAStats retrofitNBAStats;


    private String receivedTeam;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);

        Bundle b = getArguments();
        if(b != null){
            receivedTeam = b.getString("team_abbrevation");
        }

        backarrow = view.findViewById(R.id.backarrow);

        firstPlayer = view.findViewById(R.id.firstPlayer);
        secondPlayer = view.findViewById(R.id.secondPlayer);
        thirdPlayer = view.findViewById(R.id.thirdPlayer);

        firstName = view.findViewById(R.id.nameFirst);
        secondName = view.findViewById(R.id.nameSecond);
        thirdName = view.findViewById(R.id.nameThird);

        firstStats = view.findViewById(R.id.statsFirst);
        secondStats = view.findViewById(R.id.statSecond);
        thirdStats = view.findViewById(R.id.statThird);

        teamLogo = view.findViewById(R.id.teamLogo);
        teamName = view.findViewById(R.id.teamName);


        firstPlayerAssist = view.findViewById(R.id.firstPlayerAssist);
        secondPlayerAssist = view.findViewById(R.id.secondPlayerAssist);
        thirdPlayerAssist = view.findViewById(R.id.thirdPlayerAssist);

        firstNameAssist = view.findViewById(R.id.nameFirstAssist);
        secondNameAssist = view.findViewById(R.id.nameSecondAssist);
        thirdNameAssist = view.findViewById(R.id.nameThirdAssist);

        firstStatsAssist = view.findViewById(R.id.statsFirstAssist);
        secondStatsAssist = view.findViewById(R.id.statSecondAssist);
        thirdStatsAssist = view.findViewById(R.id.statThirdAssist);


        firstPlayerRebounds = view.findViewById(R.id.firstPlayerRebounds);
        secondPlayerRebounds = view.findViewById(R.id.secondPlayerRebounds);
        thirdPlayerRebounds = view.findViewById(R.id.thirdPlayerRebounds);

        firstNameRebounds = view.findViewById(R.id.nameFirstRebounds);
        secondNameRebounds = view.findViewById(R.id.nameSecondRebounds);
        thirdNameRebounds = view.findViewById(R.id.nameThirdRebounds);

        firstStatsRebounds = view.findViewById(R.id.statsFirstRebounds);
        secondStatsRebounds = view.findViewById(R.id.statSecondRebounds);
        thirdStatsRebounds = view.findViewById(R.id.statThirdRebounds);


        String teamAbbrevation = StringConverter.getInstance().getAbbreviatedString(receivedTeam);
        retrofitNBAStats = new RetrofitNBAStats();
        retrofitNBAStats.getStatsReboundsForTeam(teamAbbrevation);
        retrofitNBAStats.getStatsAssistForTeam(teamAbbrevation);
        retrofitNBAStats.getStatsPointsForTeam(teamAbbrevation);


        retrofitNBAStats.setOnPointsListener(this);
        retrofitNBAStats.setOnAssistListener(this);
        retrofitNBAStats.setOnReboundsListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        teamName.setText(receivedTeam);

        int resource = getImageResouceId(StringConverter.getInstance().getAbbreviatedString(receivedTeam));
        teamLogo.setImageResource(resource);

        navController = Navigation.findNavController(view);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });
    }

    @Override
    public void onError(Throwable error) {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPoints(List<PodiumModel> podiumModel) {

        List<PodiumModel> podiumModels = podiumModel;


        firstStats.setText(riduciADueCifreDecimali((float) podiumModel.get(0).getAvgStat())+"");
        secondStats.setText(riduciADueCifreDecimali((float) podiumModel.get(1).getAvgStat())+"");
        thirdStats.setText(riduciADueCifreDecimali((float) podiumModel.get(2).getAvgStat())+"");


        firstName.setText(podiumModel.get(0).getName());
        secondName.setText(podiumModel.get(1).getName());
        thirdName.setText(podiumModel.get(2).getName());


        String URL = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(0).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(firstPlayer);


        String URL1 = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(1).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(secondPlayer);


        String URL2 = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(2).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(thirdPlayer);



    }

    @Override
    public void onGetAssist(List<PodiumModel> podiumModel) {

        List<PodiumModel> podiumModels = podiumModel;


        firstStatsAssist.setText(riduciADueCifreDecimali((float) podiumModel.get(0).getAvgStat())+"");
        secondStatsAssist.setText(riduciADueCifreDecimali((float) podiumModel.get(1).getAvgStat())+"");
        thirdStatsAssist.setText(riduciADueCifreDecimali((float) podiumModel.get(2).getAvgStat())+"");


        firstNameAssist.setText(podiumModel.get(0).getName());
        secondNameAssist.setText(podiumModel.get(1).getName());
        thirdNameAssist.setText(podiumModel.get(2).getName());


        String URL = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(0).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(firstPlayerAssist);


        String URL1 = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(1).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(secondPlayerAssist);


        String URL2 = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(2).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(thirdPlayerAssist);


    }

    @Override
    public void onGetRebounds(List<PodiumModel> podiumModel) {

        List<PodiumModel> podiumModels = podiumModel;


        firstStatsRebounds.setText(riduciADueCifreDecimali((float) podiumModel.get(0).getAvgStat())+"");
        secondStatsRebounds.setText(riduciADueCifreDecimali((float) podiumModel.get(1).getAvgStat())+"");
        thirdStatsRebounds.setText(riduciADueCifreDecimali((float) podiumModel.get(2).getAvgStat())+"");


        firstNameRebounds.setText(podiumModel.get(0).getName());
        secondNameRebounds.setText(podiumModel.get(1).getName());
        thirdNameRebounds.setText(podiumModel.get(2).getName());


        String URL = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(0).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(firstPlayerRebounds);


        String URL1 = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(1).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(secondPlayerRebounds);


        String URL2 = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+podiumModel.get(2).getNBAID()+".png";
        Glide.with(getContext())
                .load(URL2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(thirdPlayerRebounds);


    }


    public static float riduciADueCifreDecimali(float numero) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat("#.##", symbols);
        String numeroFormattato = decimalFormat.format(numero);

        return Float.parseFloat(numeroFormattato);
    }

    public int getImageResouceId(String abbreviazioneSquadra) {
        int resourceId = 0;

        switch (abbreviazioneSquadra) {
            case "ATL":
                resourceId = R.drawable.atlanta_hawks;
                break;
            case "BOS":
                resourceId = R.drawable.boston_celtics;
                break;
            case "BKN":
                resourceId = R.drawable.brooklyn_nets;
                break;
            case "CHA":
                resourceId = R.drawable.charlotte_hornes;
                break;
            case "CHI":
                resourceId = R.drawable.chicago_bulls;
                break;
            case "CLE":
                resourceId = R.drawable.cliveland_cavaliers;
                break;
            case "DAL":
                resourceId = R.drawable.dallas_mavericks;
                break;
            case "DEN":
                resourceId = R.drawable.denver_nuggets;
                break;
            case "DET":
                resourceId = R.drawable.detroit_pistons;
                break;
            case "GSW":
                resourceId = R.drawable.golden_state_warriors;
                break;
            case "HOU":
                resourceId = R.drawable.huston_rockets;
                break;
            case "IND":
                resourceId = R.drawable.indiana_pacers;
                break;
            case "LAC":
                resourceId = R.drawable.los_angeles_clippers;
                break;
            case "LAL":
                resourceId = R.drawable.los_angeles_lakers;
                break;
            case "MEM":
                resourceId = R.drawable.memphis_grizzlies;
                break;
            case "MIA":
                resourceId = R.drawable.miami_heats;
                break;
            case "MIL":
                resourceId = R.drawable.milwakee_bucks;
                break;
            case "MIN":
                resourceId = R.drawable.minnesota_timberwolfs;
                break;
            case "NOP":
                resourceId = R.drawable.new_orleans_pelicans;
                break;
            case "NYK":
                resourceId = R.drawable.new_york_knicks;
                break;
            case "OKC":
                resourceId = R.drawable.oklahoma_city_thunders;
                break;
            case "ORL":
                resourceId = R.drawable.orlando_magic;
                break;
            case "PHI":
                resourceId = R.drawable.philadelphia_76ers;
                break;
            case "PHX":
                resourceId = R.drawable.phoenix_suns;
                break;
            case "POR":
                resourceId = R.drawable.portlands_trail_blazers;
                break;
            case "SAC":
                resourceId = R.drawable.sacramento_kings;
                break;
            case "SAS":
                resourceId = R.drawable.san_antonio_spurs;
                break;
            case "TOR":
                resourceId = R.drawable.toronto_raptors;
                break;
            case "UTA":
                resourceId = R.drawable.utah_jazz;
                break;
            case "WSH":
                resourceId = R.drawable.washington_wizards;
                break;
            default:
                // Abbreviazione squadra non riconosciuta
                break;
        }

        return resourceId;
    }
}