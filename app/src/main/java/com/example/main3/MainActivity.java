package com.example.main3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;

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
        String contentText = "운송장있음";
        int waybill = 12345;
        boolean b = contentText.contains("운송장");
        TextView mainText1 = (TextView) findViewById(R.id.noParcel);
        TextView mainText2 = (TextView) findViewById(R.id.yesParcel);
        if(b){ //받은 택배 있음
            mainText1.setVisibility(View.GONE);
            mainText2.setVisibility(View.VISIBLE);
            mainText2.append("\n\n 택배 정보\n운송장 번호 : 1234556");
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

}
