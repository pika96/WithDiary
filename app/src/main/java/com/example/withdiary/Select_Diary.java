package com.example.withdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Select_Diary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.select_diary);

        TextView tx1 = findViewById(R.id.Read_Title);
        TextView tx2 = findViewById(R.id.Read_date);
        TextView tx3 = findViewById(R.id.Read_Content);

        Intent intent = getIntent();
        String diarytext= intent.getExtras( ).getString( "diary" );
        tx3.setText( diarytext );
        String titletext= intent.getExtras().getString( "title" );
        tx1.setText( titletext );
        String datetext= intent.getExtras().getString( "date" );
        tx2.setText( datetext );
    }
}
