package com.unisa.dev.nbastats.fragments;

import android.graphics.Color;
import android.media.Image;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.adapters.AdapterPlayers;
import com.unisa.dev.nbastats.adapters.AdapterYears;
import com.unisa.dev.nbastats.api.RetrofitNBAStats;
import com.unisa.dev.nbastats.models.PlayerModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AvancedStatsFragment extends Fragment implements RetrofitNBAStats.OnPlayerReceived {

    private View view;

    private TextView playerName, bestSeason, avgPoints, bestSeasonAssist, bestSeasonRebounds, avgAssist, avgRebounds;

    private ImageView backarrow, playerPhoto;

    private RecyclerView recyclerView;

    private NavController navController;

    private RetrofitNBAStats retrofitNBAStats;


    private PlayerModel playerModel;

    private AdapterYears adapterYears;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_avanced_stats, container, false);
        playerName = view.findViewById(R.id.playerName);

        playerPhoto = view.findViewById(R.id.playerPhoto);
        backarrow = view.findViewById(R.id.backarrow);
        recyclerView = view.findViewById(R.id.totalYears);
        bestSeason = view.findViewById(R.id.bestSeason);
        avgPoints = view.findViewById(R.id.avgPoints);

        bestSeasonAssist = view.findViewById(R.id.bestSeasonAssist);
        bestSeasonRebounds = view.findViewById(R.id.bestSeasonRebounds);

        avgAssist = view.findViewById(R.id.avgAssist);
        avgRebounds = view.findViewById(R.id.avgRebounds);


        retrofitNBAStats = new RetrofitNBAStats();

        Bundle bundle = getArguments();

        if(bundle!=null){
            playerModel = (PlayerModel) bundle.getSerializable("playerSelected");
        }

        retrofitNBAStats.getAllHystoryPlayer(playerModel.getName());
        retrofitNBAStats.setOnPlayerListener(this);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    public void OnPlayerReceivedListener(List<PlayerModel> playerModelList) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");


        Collections.sort(playerModelList, new Comparator<PlayerModel>() {
            @Override
            public int compare(PlayerModel p1, PlayerModel p2) {
                return p1.getSeason().compareTo(p2.getSeason());
            }
        });

        playerName.setText(playerModel.getName());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterYears = new AdapterYears(getContext(), playerModelList);
        recyclerView.setAdapter(adapterYears);


        String URL = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/"+playerModel.getNBAID()+".png";
        Glide.with(getContext())
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.placeholder)
                .into(playerPhoto);


        //creo mappa k,v con anno e punti
        Map<String, Double> seasonPtsMap = new HashMap<>();
        double maxPts = Double.MIN_VALUE;
        String maxSeason = "";

        for (PlayerModel player : playerModelList) {
            seasonPtsMap.put(player.getSeason(), player.getPts());
            if (player.getPts() > maxPts) {
                maxPts = player.getPts();
                maxSeason = player.getSeason();
            }
        }

        //creo mappa k,v con anno e rimbalzi
        Map<String, Double> seasonRebMap = new HashMap<>();
        double maxReb = Double.MIN_VALUE;
        String maxSeasonReb = "";

        for (PlayerModel player : playerModelList) {
            seasonRebMap.put(player.getSeason(), player.getReb());
            if (player.getReb() > maxReb) {
                maxReb = player.getReb();
                maxSeasonReb = player.getSeason();
            }
        }

        //creo mappa k,v con anno e assist
        Map<String, Double> seasonAstMap = new HashMap<>();
        double maxAst = Double.MIN_VALUE;
        String maxSeasonAssist = "";


        for (PlayerModel player : playerModelList) {
            seasonAstMap.put(player.getSeason(), player.getAst());
            if (player.getAst() > maxAst) {
                maxAst = player.getAst();
                maxSeasonAssist = player.getSeason();
            }
        }



        bestSeason.setText(maxSeason);
        avgPoints.setText(maxPts + "pts");

        bestSeasonRebounds.setText(maxSeasonReb);
        avgRebounds.setText(maxReb + "reb");

        bestSeasonAssist.setText(maxSeasonAssist);
        avgAssist.setText(maxAst + "ast");



        //dichiarazione chart per punti
        List<Entry> entries = new ArrayList<>();

        int index = 0;
        for (Map.Entry<String, Double> entry : seasonPtsMap.entrySet()) {
            String label = entry.getKey();
            Double value = entry.getValue();

            String formattedValue = decimalFormat.format(value); // Arrotonda il valore alla seconda cifra decimale

            entries.add(new Entry(index, Float.parseFloat(formattedValue)));
            index++;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Punti Medi Annuali");

        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.RED);
        dataSet.setValueTextSize(12f);

        LineData lineData = new LineData(dataSet);

        LineChart lineChart = view.findViewById(R.id.lineChartPoints);
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);

        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setGranularity(1f);

        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);

        lineChart.invalidate();


        //dichiarazione chart per assist

        List<Entry> entries1 = new ArrayList<>();

        int index1 = 0;
        for (Map.Entry<String, Double> entry : seasonAstMap.entrySet()) {
            String label1 = entry.getKey();
            Double value1 = entry.getValue();

            String formattedValue = decimalFormat.format(value1); // Arrotonda il valore alla seconda cifra decimale


            entries1.add(new Entry(index1, Float.parseFloat(formattedValue)));
            index1++;
        }

        LineDataSet dataSet1 = new LineDataSet(entries1, "Punti Medi Annuali");

        dataSet1.setColor(Color.BLACK);
        dataSet1.setValueTextColor(Color.RED);
        dataSet1.setValueTextSize(12f);

        LineData lineData1 = new LineData(dataSet1);

        LineChart lineChart1 = view.findViewById(R.id.lineChartAssist);
        lineChart1.setData(lineData1);
        lineChart1.getDescription().setEnabled(false);
        lineChart1.setTouchEnabled(true);
        lineChart1.setDragEnabled(true);
        lineChart1.setScaleEnabled(true);
        lineChart1.setPinchZoom(true);

        XAxis xAxis1 = lineChart1.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis1.setGranularity(1f);

        YAxis leftYAxis1 = lineChart1.getAxisLeft();
        leftYAxis1.setGranularity(1f);

        YAxis rightYAxis1 = lineChart1.getAxisRight();
        rightYAxis1.setEnabled(false);

        lineChart1.invalidate();


        //dichiarazione chart per rebound

        List<Entry> entries2 = new ArrayList<>();
        int index2 = 0;
        for (Map.Entry<String, Double> entry : seasonRebMap.entrySet()) {
            String label2 = entry.getKey();
            Double value2 = entry.getValue();

            String formattedValue = decimalFormat.format(value2); // Arrotonda il valore alla seconda cifra decimale


            entries2.add(new Entry(index2, Float.parseFloat(formattedValue)));
            index2++;
        }

        LineDataSet dataSet2 = new LineDataSet(entries2, "Rimbalzi Medi Annuali");

        dataSet2.setColor(Color.BLACK);
        dataSet2.setValueTextColor(Color.RED);
        dataSet2.setValueTextSize(12f);

        LineData lineData2 = new LineData(dataSet2);

        LineChart lineChart2 = view.findViewById(R.id.lineChartRebounds);
        lineChart2.setData(lineData2);
        lineChart2.getDescription().setEnabled(false);
        lineChart2.setTouchEnabled(true);
        lineChart2.setDragEnabled(true);
        lineChart2.setScaleEnabled(true);
        lineChart2.setPinchZoom(true);

        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setGranularity(1f);

        YAxis leftYAxis2 = lineChart2.getAxisLeft();
        leftYAxis2.setGranularity(1f);

        YAxis rightYAxis2 = lineChart2.getAxisRight();
        rightYAxis2.setEnabled(false);

        lineChart2.invalidate();


    }
}