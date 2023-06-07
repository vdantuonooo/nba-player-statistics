package com.unisa.dev.nbastats.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unisa.dev.nbastats.R;
import com.unisa.dev.nbastats.models.TeamModel;

import java.util.List;

public class GridTeamsAdapter extends RecyclerView.Adapter<GridTeamsAdapter.ImageViewHolder> {

    private List<TeamModel> teamList;
    private Context context;
    private OnItemClick onItemClickListener;

    public GridTeamsAdapter(List<TeamModel> teamList, Context context) {
        this.teamList = teamList;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teams, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        int actualPos = position;
        int imageResId = teamList.get(position).getTeamLogo();
        holder.imageView.setImageResource(imageResId);
        holder.teamName.setText(teamList.get(position).getTeamName());
        holder.boxContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(teamList.get(actualPos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamList != null ? teamList.size() : 0;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView teamName;

        RelativeLayout boxContainer;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageLogo);
            teamName = itemView.findViewById(R.id.teamName);
            boxContainer = itemView.findViewById(R.id.boxContainer);

        }
    }

    public interface OnItemClick{
        void onItemClick(TeamModel teamModel);
    }

    public void setOnItemClickListener(OnItemClick onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}

