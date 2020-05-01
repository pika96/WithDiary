package com.example.withdiary;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;

public class datalist implements Parcelable {
    String titletext;
    String datetext;
    String idtext;
    String diarytext;
    String imagepath;

    public datalist() {
    }

    public datalist(String datetext, String titletext, String diarytext, String imagepath) {
        this.datetext = datetext; //일기 날짜
        this.titletext = titletext; //일기 제목
        this.diarytext = diarytext; //일기 내용
        this.idtext = idtext;//사용자 아이디
        this.imagepath = imagepath;//사진
    }

    protected datalist(Parcel in) {
        this.datetext=in.readString();
        this.titletext=in.readString();
        this.diarytext=in.readString();
        this.imagepath=in.readString();
        //this.idtext=in.readString();
    }

    //Alt+insert를 누르면 get/set 자동으로 생성된다

    public String getDatetext() {
        return datetext;
    }

    public String getTitletext() {
        return titletext;
    }

    public String getDiarytext() {
        return diarytext;
    }

    public String getImagepath() { return imagepath; }

    public String getIdtext() {
        return idtext;
    }

    public void setDatetext(String date) {
        this.datetext = date;
    }

    public void setTitletext(String titletext) {
        this.titletext = titletext;
    }

    public void setDiarytext(String diarytext) {
        this.diarytext = diarytext;
    }

    public void setImagepath(String imagepath) { this.imagepath = imagepath; }

    public void setIdtext(String idtext) {
        this.idtext = idtext;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.datetext);
        parcel.writeString(this.titletext );
        parcel.writeString(this.diarytext );
        parcel.writeString(this.imagepath) ;
        //parcel.writeString(this.idtext);



    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public datalist createFromParcel(Parcel in) {
            return new datalist(in);
        }

        @Override
        public datalist[] newArray(int size) {
            // TODO Auto-generated method stub
            return new datalist[size];
        }


    };
}

