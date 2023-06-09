package com.unisa.dev.nbastats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.models.PlayerModel;
import com.unisa.dev.nbastats.utilities.StringConverter;

import java.util.List;

public class AdapterYears extends  RecyclerView.Adapter<AdapterYears.ViewHolder>{

    private Context context;
    private List<PlayerModel> playerModelList;

    public AdapterYears(Context context, List<PlayerModel> playerModelList) {
        this.context = context;
        this.playerModelList = playerModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.single_year_for_player, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.season.setText(playerModelList.get(position).getSeason());

        holder.teamName.setText(StringConverter.getInstance().
                getTeamName(playerModelList.get(position).getTeamAbbrevation()));

        int drawable = getImageResouceId(playerModelList.get(position).getTeamAbbrevation());
        holder.teamLogo.setImageResource(drawable);
    }

    @Override
    public int getItemCount() {
        return playerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView teamLogo;
        private TextView season, teamName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            teamLogo = itemView.findViewById(R.id.imageLogo);
            season = itemView.findViewById(R.id.seasonYaer);
            teamName = itemView.findViewById(R.id.teamName);

        }
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
