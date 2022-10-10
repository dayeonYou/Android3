package com.example.main3;

import android.media.Image;
import android.widget.RadioButton;

import java.net.URI;

public class MainData {
    private int iv_profile;
    private String textArrival;
    private String textInformation;
    private String checkT;
    private String rightParcel;
    private String wrongParcel;
    //alt+insert
    public MainData(int iv_profile, String textArrival, String textInformation, String checkT, String rightParcel, String wrongParcel){
        this.iv_profile = iv_profile;
        this.textArrival = textArrival;
        this.textInformation = textInformation;
        this.checkT = checkT;
        this.rightParcel = rightParcel;
        this.wrongParcel = wrongParcel;
    }
    public int getIv_profile(){
        return iv_profile;
    }
    public void setIv_profile(int iv_profile){
        this.iv_profile = iv_profile;
    }
    public String getTextArrival(){
        return textArrival;
    }
    public void setTextArrival(String textArrival){
        this.textArrival = textArrival;
    }
    public  String getTextInformation(){
        return textInformation;
    }
    public void setTextInformation(String textInformation){
        this.textInformation = textInformation;
    }
    public  String getCheckT(){
        return checkT;
    }
    public void setCheckT(String checkT){
        this.checkT = checkT;
    }
    public String getRightParcel(){
        return rightParcel;
    }
    public void setRightParcel(String rightParcel){
        this.rightParcel = rightParcel;
    }
    public String getWrongParcel(){
        return wrongParcel;
    }
    public void setWrongParcelParcel(String wrongParcel){
        this.wrongParcel = wrongParcel;
    }
}