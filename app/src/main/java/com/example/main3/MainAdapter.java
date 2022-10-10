package com.example.main3;
import static android.content.Context.VIBRATOR_SERVICE;

//import android.app.AlertDialog;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

        holder.rightParcel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(v.getContext(),"YOUR PARCEL",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("확인 버튼을 누르면 받은 택배로 취급됩니다.");
                builder.setTitle("YOUR PARCEL");
                builder.setCancelable(false);
                builder.setNegativeButton("확인", (DialogInterface.OnClickListener) (dialog, which) -> {
                    remove(holder.getAdapterPosition());
                    dialog.dismiss();
                });
                builder.setPositiveButton("취소", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        holder.wrongParcel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(v.getContext(),"NOT YOUR PARCEL",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("\n자신의 것이 아닌 택배를 받은 경우\n      1. 받는 사람과 주소를 확인합니다.\n      2. 받는 사람의 전화번호로 연락해봅니다.\n      3. 택배기사에게 연락해봅니다.\n");
                builder.setTitle("NOT YOUR PARCEL");
                builder.setCancelable(false);
                builder.setNegativeButton("확인", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.setPositiveButton("취소", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
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
    public class CustomViewHolder extends RecyclerView.ViewHolder{

        protected ImageView iv_profile;
        protected TextView textArrival;
        protected TextView textInformation;
        protected TextView checkT;
        protected RecyclerView recyclerViewR;
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
        }
    }
}