package com.eltendawy.anamuslim.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eltendawy.anamuslim.Model.Sura;
import com.eltendawy.anamuslim.R;

import java.util.ArrayList;


public class Suar_List_Adapter extends RecyclerView.Adapter<Suar_List_Adapter.ViewHolder> {
    private Typeface typeface;
    private ArrayList<Sura> suar;
    private onItemClick onItemClickListener;

    public Suar_List_Adapter(ArrayList<Sura> suar) {
        this.suar = suar;
    }

    public void setTypeface(Context context) {
        typeface = Typeface.createFromAsset(context.getAssets(), "Fonts/uthmanic_hafs_ver09.otf");
    }

    public void setOnItemClickListener(onItemClick onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recycler_item_sora, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.sura_name.setText(suar.get(position).getName());
        holder.sura_number.setText(String.valueOf(position+1));
        if (onItemClickListener != null)
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder.getAdapterPosition(), holder.sura_name);
                }
            });
        holder.sura_name.setTypeface(typeface);
        }

    @Override
    public int getItemCount() {
        return suar.size();
    }

    public interface onItemClick {
        void onClick(int position, View view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView sura_name;
        TextView sura_number;
        View view;

        ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            sura_name = itemView.findViewById(R.id.sura_name);
            sura_number = itemView.findViewById(R.id.sura_number);
        }

    }
}

