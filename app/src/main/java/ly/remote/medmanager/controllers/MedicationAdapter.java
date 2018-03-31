package ly.remote.medmanager.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ly.remote.medmanager.R;
import ly.remote.medmanager.models.MedicationModel;

/**
 * Created by Risen on 3/30/2018.
 */

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.RecyclerViewHolder> {
    private ArrayList<MedicationModel> medicationModelArrayList;

    //Constructor
    public MedicationAdapter(ArrayList<MedicationModel> medicationModelArrayList) {
        this.medicationModelArrayList = medicationModelArrayList;
    }

    @Override
    public MedicationAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_card_view,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(MedicationAdapter.RecyclerViewHolder holder, int position) {
        MedicationModel medModel = medicationModelArrayList.get(position);
        holder.med_name.setText(medModel.getMed_name());
        holder.med_desc.setText(medModel.getMed_desc());
        holder.med_month.setText(medModel.getMed_month());
        holder.med_interval.setText(medModel.getMed_interval());
        holder.med_start_date.setText(medModel.getMed_start_date());
        holder.med_end_date.setText(medModel.getMed_end_date());
        holder.med_remind_me.setText(String.valueOf(medModel.getMed_reminder()));

    }

    @Override
    public int getItemCount() {
        return medicationModelArrayList.size();
    }

    //View Holder inner class
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView med_name, med_desc, med_month, med_interval, med_start_date, med_end_date, med_remind_me;
//        private MyItemClickListener itemClickListener;
//        private MyItemLongClickListener itemLongClickListener;

        public RecyclerViewHolder(View view) {
            super(view);

            med_name = (TextView)view.findViewById(R.id.cardView_med_name);
            med_desc = (TextView)view.findViewById(R.id.cardView_med_descrition);
            med_month = (TextView)view.findViewById(R.id.cardView_med_month);
            med_interval = (TextView)view.findViewById(R.id.cardView_interval);
            med_start_date = (TextView)view.findViewById(R.id.cardView_start_date);
            med_end_date = (TextView)view.findViewById(R.id.cardView_end_date);
            med_remind_me = (TextView)view.findViewById(R.id.cardView_remind_me);



        }

//        @Override
//        public void onClick(View v) {
//
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            return false;
//        }
    }
}