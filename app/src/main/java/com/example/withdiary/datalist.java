package com.example.withdiary;

import android.widget.ImageView;

public class datalist {
    String titletext;
    String datetext;
    String idtext;
    String diarytext;
    ImageView image;

    public datalist(String titletext, String datetext){
        this.titletext = titletext; //일기 제목
        this.diarytext = diarytext; //일기 내용
        this.datetext = datetext; //일기 날짜
        this.idtext= idtext;//사용자 아이디
        this.image = image;//사진
    }

    //Alt+insert를 누르면 get/set 자동으로 생성된다

    public String getDiarytext() {
        return diarytext;
    }

    public void setDiarytext(String diarytext) {
        this.diarytext = diarytext;
    }

    public String getIdtext() {
        return idtext;
    }

    public void setIdtext(String idtext) {
        this.idtext = idtext;
    }

    public String getTitletext() { return titletext;}

    public void setTitletext(String titletext) {this.titletext = titletext;}



    public String getDatetext() {
        return datetext;
    }

    public void setDatetext(String subtext) {
        this.datetext = datetext;
    }

}
