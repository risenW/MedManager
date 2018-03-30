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
//        holder.med_interval.setText(medModel.getMed_interval());
//        holder.med_start_date.setText(String.valueOf(medModel.getMed_start_date()));
//        holder.med_end_date.setText(String.valueOf(medModel.getMed_end_date()));

    }

    @Override
    public int getItemCount() {
        return medicationModelArrayList.size();
    }

    //View Holder inner class
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView med_name, med_desc, med_month; // med_interval, med_start_date, med_end_date;
//        private MyItemClickListener itemClickListener;
//        private MyItemLongClickListener itemLongClickListener;

        public RecyclerViewHolder(View arg0) {
            super(arg0);

            med_name = (TextView)arg0.findViewById(R.id.med_name);
            med_desc = (TextView)arg0.findViewById(R.id.med_descrition);
            med_month = (TextView)arg0.findViewById(R.id.med_month);
//            med_interval = (TextView)arg0.findViewById(R.id.med_name);
//            med_start_date = (TextView)arg0.findViewById(R.id.med_name);
//            med_end_date = (TextView)arg0.findViewById(R.id.med_name);



        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
