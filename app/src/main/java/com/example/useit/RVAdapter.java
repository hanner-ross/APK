package com.example.useit;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView table_name;
        TextView think;
        TextView person_name;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            table_name = (TextView)itemView.findViewById(R.id.table_name);
            think = (TextView)itemView.findViewById(R.id.think);
            person_name = (TextView)itemView.findViewById(R.id.person_name);
        }
    }

    List<TableActivity.thinks> pensamientos;

    RVAdapter(List<TableActivity.thinks> pensamientos){
        this.pensamientos = pensamientos;
    }

    @Override
    public int getItemCount() {
        return pensamientos.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tablecontent, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.table_name.setText(pensamientos.get(i).table_name);
        personViewHolder.think.setText(pensamientos.get(i).think);
        personViewHolder.person_name.setText(pensamientos.get(i).person_name);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
