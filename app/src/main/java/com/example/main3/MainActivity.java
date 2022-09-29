package com.example.main3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        ArrayList<String> contentTextArray = new ArrayList<>();
        //서버에서 openCV로 추출한 텍스르 가져와서 저장
        String contentText;
        String recipient,address,waybill;
        contentTextArray.add("운송장_1234567890 받으시는 분_홍*동 주소_경기도 화성시");
        contentText = contentTextArray.get(0);
        boolean b = contentText.contains("운송장");
        TextView mainText1 = (TextView) findViewById(R.id.noParcel);
        TextView mainText2 = (TextView) findViewById(R.id.yesParcel);
        TextView waybillText = (TextView)findViewById(R.id.waybillT);
        TextView addressText = (TextView) findViewById(R.id.addressT);
        TextView recipientText = (TextView) findViewById(R.id.recipientT);

        if(b){ //받은 택배 있음
            mainText1.setVisibility(View.GONE);
            mainText2.setVisibility(View.VISIBLE);
            waybill = contentText.replaceAll("[^0-9]","");
            waybillText.append(waybill);
            recipient = returnString(contentText,"받으시는 분");
            recipientText.append(recipient);
            address = returnString(contentText,"주소");
            addressText.append(address);
            boolean parcelYN;

            RadioButton rightParcelBtn = (RadioButton) findViewById(R.id.rightParcel);
            rightParcelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "It will be processed as a received parcel. Thank you.", Toast.LENGTH_SHORT).show();
                    Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vib.vibrate(1000);
                }
            });
            RadioButton wrongParcelBtn = (RadioButton) findViewById(R.id.wrongParcel);
            wrongParcelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "It is not your parcel.", Toast.LENGTH_SHORT).show();
                    Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vib.vibrate(1000);
                }
            });
        }
        else{ //받은 택배 없음
            mainText1.setVisibility(View.VISIBLE);
            mainText2.setVisibility(View.GONE);
        }
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
    private String returnString(String str,String wd){
        String result = null;
        for(int i=0;i<str.length();i++){
            if(wd == "받으시는 분") result = str.split(wd)[1].split("주소")[0];
            else result = str.split(wd)[1];
        }
        return result;
    }

}
