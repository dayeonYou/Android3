package com.example.main3;
import static android.content.Context.VIBRATOR_SERVICE;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {
    private ArrayList<MainData> arrayList;
    public  MainAdapter(ArrayList<MainData> arrayList){
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position){
        holder.iv_profile.setImageResource(arrayList.get(position).getIv_profile());
        holder.textArrival.setText(arrayList.get(position).getTextArrival());
        holder.textInformation.setText(arrayList.get(position).getTextInformation());
        holder.checkT.setText(arrayList.get(position).getCheckT());
        holder.rightParcel.setText(arrayList.get(position).getRightParcel());
        holder.wrongParcel.setText(arrayList.get(position).getWrongParcel());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String curName = holder.textArrival.getText().toString();
                Toast.makeText(v.getContext(),curName,Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                remove(holder.getAdapterPosition());
                return true;
            }
        });
    }
    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount(){
        return (null!=arrayList ? arrayList.size() : 0);
    }
    public class  CustomViewHolder extends RecyclerView.ViewHolder{

        protected ImageView iv_profile;
        protected TextView textArrival;
        protected TextView textInformation;
        protected TextView checkT;
        protected RadioButton rightParcel;
        protected RadioButton wrongParcel;

        public CustomViewHolder(@NonNull View itemView){
            super(itemView);
            this.iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            this.textArrival = (TextView) itemView.findViewById(R.id.textArrival);
            this.textInformation = (TextView) itemView.findViewById(R.id.textInformation);
            this.checkT = (TextView) itemView.findViewById(R.id.checkT);
            this.rightParcel = (RadioButton) itemView.findViewById(R.id.rightParcel);
            this.wrongParcel = (RadioButton) itemView.findViewById(R.id.wrongParcel);

//            rightParcel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(this, "It will be processed as a received parcel. Thank you.", Toast.LENGTH_SHORT).show();
//                    Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                    vib.vibrate(1000);
//                }
//            });
//            wrongParcel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getApplicationContext(), "It is not your parcel.", Toast.LENGTH_SHORT).show();
//                    Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                    vib.vibrate(1000);
//                }
//            });
        }
    }
}