package com.eltendawy.anamuslim.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eltendawy.anamuslim.Api.Model.RadiosItem;
import com.eltendawy.anamuslim.R;

import java.util.ArrayList;
import java.util.List;

public class RadiosRecyclerAdapter extends RecyclerView.Adapter<RadiosRecyclerAdapter.ViewHolder> {
    private ArrayList<RadiosItem>radios;
    private int recylerItemRseourceID;
    private onClickListner onChannelClickListner;
    private onClickListner onFavouriteClickListner;
    private static int current_playing=0;
    RecyclerView recyclerView;


    public RadiosRecyclerAdapter(RecyclerView recyclerView,int recylerItemRseourceID) {
        this.recyclerView=recyclerView;
        this.recylerItemRseourceID = recylerItemRseourceID;
        radios=new ArrayList<>();
    }

    public int getCurrent_playing() {
        return current_playing;
    }

    public void setCurrent_playing(int current_playing) {
        this.current_playing = current_playing;
    }

    public ArrayList<RadiosItem> getRadios() {
        return radios;
    }

    public RadiosItem getRadio(int position) {
        current_playing=position;
        return position<radios.size()?radios.get(position):null;
    }
    public void setRadios(ArrayList<RadiosItem> radios) {
        this.radios = radios;
        if(radios==null)
            this.radios=new ArrayList<>();
        notifyDataSetChanged();
    }
    public void addRadio(RadiosItem radio) {
        if (radio == null) return;
        radios.add(radio);
        int index = radios.indexOf(radio);
        notifyItemChanged(index);
    }
    public void addRadio(ArrayList<RadiosItem> radios) {
        int changeStart=this.radios.size();
        if(radios==null)return;
        this.radios.addAll(radios);
        notifyItemRangeChanged(changeStart, radios.size());
    }

    public void setOnChannelClickListner(onClickListner onChannelClickListner) {
        this.onChannelClickListner = onChannelClickListner;
    }

    public void setOnFavouriteClickListner(onClickListner onFavouriteClickListner) {
        this.onFavouriteClickListner = onFavouriteClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(recylerItemRseourceID,viewGroup,false);
        return new ViewHolder(view);
    }

    public void setPlayingIndicator(ViewHolder holder,int visible)
    {
        try{
        holder.playing.setVisibility(visible);
        }catch (Exception ignored)
        {
        }
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        viewHolder.playing.setVisibility(View.GONE);
        viewHolder.radio.setText(radios.get(i).getName());
        if(radios.get(i).isFavourite())
            viewHolder.fav.setImageResource(R.drawable.favourite_on);
        else
            viewHolder.fav.setImageResource(R.drawable.favourite_off);
        if(onChannelClickListner!=null)
            viewHolder.radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(current_playing);
                    current_playing=viewHolder.getAdapterPosition();
                    onChannelClickListner.onClick(viewHolder.getAdapterPosition(),v,radios.get(viewHolder.getAdapterPosition()));
                }
            });
        if(onFavouriteClickListner!=null)
            viewHolder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onFavouriteClickListner.onClick(viewHolder.getAdapterPosition(),v,radios.get(viewHolder.getAdapterPosition()));
                }
            });

    }

    @Override
    public int getItemCount() {
        return radios==null?0:radios.size();
    }


    public interface onClickListner
    {
        void onClick(int position,View view,RadiosItem radio);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder

    {

        ImageButton fav;
        TextView playing;
        TextView radio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radio=itemView.findViewById(R.id.channel_title);
            fav=itemView.findViewById(R.id.fav_button);
            playing=itemView.findViewById(R.id.playing);
        }
    }
}
