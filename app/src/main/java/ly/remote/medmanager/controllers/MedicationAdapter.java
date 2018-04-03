package ly.remote.medmanager.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ly.remote.medmanager.R;
import ly.remote.medmanager.controllers.Interfaces.MyItemOnClickListener;
import ly.remote.medmanager.controllers.Interfaces.MyItemOnLongClickListener;
import ly.remote.medmanager.models.MedicationModel;

/**
 * Created by Risen on 3/30/2018.
 */

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.RecyclerViewHolder> {
    private ArrayList<MedicationModel> medicationModelArrayList;
    private MyItemOnClickListener clickListener;
    private MyItemOnLongClickListener longClickListener;

    //Constructor
    public MedicationAdapter(ArrayList<MedicationModel> medicationModelArrayList) {
        this.medicationModelArrayList = medicationModelArrayList;
    }

    @Override
    public MedicationAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)  {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_card_view,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,clickListener,longClickListener);
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

        if (medModel.getMed_reminder() == 1){
            holder.med_reminder_set_icon.setImageResource(R.drawable.ic_alarm_on);
        }

    }

    //item listeners for Adapter
    public void setClickListener(MyItemOnClickListener listener){
        this.clickListener = listener;
    }

    public void setLongClickListener(MyItemOnLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }


    @Override
    public int getItemCount() {
        return medicationModelArrayList.size();
    }

    //View Holder inner class
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView med_name, med_desc, med_month, med_interval, med_start_date, med_end_date, med_remind_me;
        ImageButton med_reminder_set_icon;
        private MyItemOnClickListener itemClickListener;
        private MyItemOnLongClickListener itemLongClickListener;

        public RecyclerViewHolder(View arg0, MyItemOnClickListener clickListener, MyItemOnLongClickListener longClickListener) {
            super(arg0);

            med_name = (TextView)arg0.findViewById(R.id.cardView_med_name);
            med_desc = (TextView)arg0.findViewById(R.id.cardView_med_descrition);
            med_month = (TextView)arg0.findViewById(R.id.cardView_med_month);
            med_interval = (TextView)arg0.findViewById(R.id.cardView_interval);
            med_start_date = (TextView)arg0.findViewById(R.id.cardView_start_date);
            med_end_date = (TextView)arg0.findViewById(R.id.cardView_end_date);
            med_remind_me = (TextView)arg0.findViewById(R.id.cardView_remind_me);
            med_reminder_set_icon = (ImageButton)arg0.findViewById(R.id.cardView_btn_reminder);
            this.itemClickListener = clickListener;
            this.itemLongClickListener = longClickListener;
            arg0.setOnClickListener(this);
            arg0.setOnLongClickListener(this);



        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null){
                itemClickListener.onItemClick(v,getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (itemLongClickListener != null){
                itemLongClickListener.OnItemLongClickListener(v,getAdapterPosition());
            }
            return true;
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
