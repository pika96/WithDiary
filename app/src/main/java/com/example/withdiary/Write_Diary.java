package com.example.withdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Write_Diary extends AppCompatActivity {

    public static final int REQUESTCODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary);
        final EditText Titletext = findViewById(R.id.Diary_title);
        final EditText Diarytext = findViewById(R.id.Diary_input);


        final Intent intent = new Intent(Write_Diary.this, DB.class);

        findViewById(R.id.Diary_upload).setOnClickListener(new View.OnClickListener() {

            // Do processing Title or Diary nullException
            @Override
            public void onClick(View v) {
                String Title = Titletext.getText().toString();
                String Diary = Diarytext.getText().toString();

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
                String str_date = sdf.format(date);

                intent.putExtra("CODE",REQUESTCODE);
                intent.putExtra("keyDate",str_date);
                intent.putExtra("keyTitle",Title);
                intent.putExtra("keyDiary",Diary);

                startActivity(intent);
                finish();
            }
        });
    }
}
