package com.example.withdiary;

import android.widget.ImageView;

import java.io.Serializable;

public class datalist implements Serializable{
    String titletext;
    String datetext;
    String idtext;
    String diarytext;
    //ImageView image;

    public  datalist(){}

    public datalist(String datetext, String titletext,String diarytext){
        this.datetext = datetext; //일기 날짜
        this.titletext = titletext; //일기 제목
        this.diarytext = diarytext; //일기 내용
        this.idtext= idtext;//사용자 아이디
        //this.image = image;//사진
    }

    //Alt+insert를 누르면 get/set 자동으로 생성된다

    public String getDatetext() {
        return datetext;
    }
    public String getTitletext() { return titletext;}
    public String getDiarytext() {
        return diarytext;
    }
    public String getIdtext() {
        return idtext;
    }

    public void setDatetext(String date) {
        this.datetext = date;
    }
    public void setTitletext(String titletext) {this.titletext = titletext;}
    public void setDiarytext(String diarytext) {
        this.diarytext = diarytext;
    }
    public void setIdtext(String idtext) {
        this.idtext = idtext;
    }



}
