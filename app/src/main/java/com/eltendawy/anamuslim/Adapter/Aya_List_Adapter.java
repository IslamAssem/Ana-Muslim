package com.eltendawy.anamuslim.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eltendawy.anamuslim.Model.Aya;
import com.eltendawy.anamuslim.R;
import com.eltendawy.anamuslim.helper.BaseActivity;

import java.util.ArrayList;


public class Aya_List_Adapter extends RecyclerView.Adapter<Aya_List_Adapter.ViewHolder> {
    Typeface typeface;
    private ArrayList<Aya> ayat;
    private LayoutInflater inflater;
    private onItemClick onItemClickListener;

    public Aya_List_Adapter(ArrayList<Aya> Ayat, Context context) {
        this.ayat = Ayat;
        this.inflater = LayoutInflater.from(context);
        typeface = Typeface.createFromAsset(context.getAssets(), "Fonts/uthmanic_hafs_ver09.otf");

    }
    public void setOnItemClickListener(onItemClick onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_item_aya, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String aya_number=BaseActivity.getARNumber(position);
        String aya_content=ayat.get(position).getName();
        if(position>0)
            holder.aya.setText(aya_content.concat("(" ).
                    concat(aya_number).concat(")"));
        else holder.aya.setText(aya_content);
        if (onItemClickListener != null)
            holder.aya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder.getAdapterPosition(), holder.aya);
                }
            });
        holder.aya.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return ayat.size();
    }

    public interface onItemClick {
        void onClick(int position, View view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView aya;

        ViewHolder(View itemView) {
            super(itemView);
            aya = itemView.findViewById(R.id.aya);
        }

    }
}
