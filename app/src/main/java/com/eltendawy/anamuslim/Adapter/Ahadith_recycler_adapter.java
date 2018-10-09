package com.eltendawy.anamuslim.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eltendawy.anamuslim.Model.Hadith;
import com.eltendawy.anamuslim.R;

import java.util.ArrayList;

/**
 * Created by Islam_Assem on 23-09-2018.
 */

public class Ahadith_recycler_adapter extends RecyclerView.Adapter<Ahadith_recycler_adapter.ViewHolder> {


    private Typeface typeface;
    private ArrayList<Hadith>Ahadith;
    onHadithClickListener onHadithClickListener;

    public Ahadith_recycler_adapter(ArrayList<Hadith> Ahadith, Context context) {

        this.Ahadith = Ahadith;
        typeface = Typeface.createFromAsset(context.getAssets(), "Fonts/uthmanic_hafs_ver09.otf");

    }

    public void setOnHadithClickListener(Ahadith_recycler_adapter.onHadithClickListener onHadithClickListener) {
        this.onHadithClickListener = onHadithClickListener;
    }

    public void add(Hadith Hadith)
    {
        if(Ahadith==null)
            Ahadith=new ArrayList<>();
        Ahadith.add(Hadith);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_hadith,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.title.setTypeface(typeface);
        holder.title.setText(Ahadith.get(position).getTitle());
        if(onHadithClickListener !=null)
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHadithClickListener.onClick(holder.getAdapterPosition(),Ahadith.get(holder.getAdapterPosition()));
                }
            });
    }

    @Override
    public int getItemCount() {
        return Ahadith==null?0:Ahadith.size();
    }
    public interface onHadithClickListener {
        void onClick(int position,Hadith hadith);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.hadith_title);
        }
    }
}
