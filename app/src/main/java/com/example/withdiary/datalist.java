package com.example.withdiary;

import android.widget.ImageView;

public class datalist {
    String titletext;
    String datetext;
    ImageView image;

    public datalist(String titletext, String datetext, ImageView image){
        this.titletext = titletext;
        this.datetext = datetext;
        this.image = image;
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

}
