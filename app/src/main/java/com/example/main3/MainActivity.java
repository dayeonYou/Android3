package com.example.main3;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import androidx.appcompat.app.AlertDialog;
import java.lang.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    //private String contentText2 = "운송장_1234567890 받는분_홍*동 주소_경기도 화성시 운송장2_123456789 받는분_김*수 주소_서울시 마포구";
    private String contentText = "받는분_홍*동 운송장_1234567890 주소_경기도 화성시 받는분_김*수 운송장2_123456789 주소_서울시 마포구";
    private ArrayList<String> contentTextArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ONMAJU");
        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Btn1.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Btn2.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Btn3.class);
                startActivity(intent);

            }
        });

        if(false == isConnected()) {
            Toast.makeText(this,"not connected",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (getNetworkType()){
            case ConnectivityManager.TYPE_WIFI:
                Toast.makeText(this,"connected to WI-FI",Toast.LENGTH_SHORT).show();
                HttpMgrTread httpThread = new HttpMgrTread();
                httpThread.start();
                break;
            case ConnectivityManager.TYPE_MOBILE:
                Toast.makeText(this,"connected to MOBILE",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        //서버에서 openCV로 추출한 텍스트 가져와서 저장
        //택배의 개수만큼 문자열 배열에 따로 따로 저장하기 위한 문자열 배열 생성
        //택배의 개수를 운송장이라는 단어로 선별
        boolean b = contentText.contains("운송장");

        TextView mainText1 = (TextView) findViewById(R.id.noParcel);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        if(b){ //받은 택배 있음
            mainText1.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);

            recyclerView = (RecyclerView) findViewById(R.id.rv);
            linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            arrayList = new ArrayList<>();

            mainAdapter = new MainAdapter(arrayList);
            recyclerView.setAdapter(mainAdapter);

            int count = countParcel(contentText);
            for(int i=1;i<=count;i++){
                String result = returnString(contentText,i);
                contentTextArray.add(result);
            }
            for(int i=0;i<count;i++){
                MainData mainData = new MainData(R.mipmap.ic_launcher,"Parcel Arrival","Information of Parcel\n"+contentTextArray.get(i),"Is it your parcel?","yes","no");
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();
            }

        }
        else{ //받은 택배 없음
            mainText1.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }

    }
    @Override
    public void onBackPressed() {
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);
        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
            finish();
        });



        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetWork != null) && (activeNetWork.isConnectedOrConnecting());
        return isConnected;
    }
    private int getNetworkType(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType();
    }
    private int countParcel(@NonNull String str){
        int count=0;
        String[] array = str.split("운송장");
        count = array.length-1;
        return count;
    }
    private String returnString(@NonNull String str, int num){
        String result = null;
        String[] array = str.split("운송장");
        //ArrayList<String> array2 = returnString2(str,2);
        if(array[0].equals("")){ //운송장이 맨 앞
            result = "운송장" + array[num]; //for 문으로 num 값 계속 바귐
        }
        else{
            array = str.split("받는분");
            result = "받는분" + array[num]; //for 문으로 num 값 계속 바귐
        }
        return result;
    }


}
