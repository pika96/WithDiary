package com.example.withdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class make_new_diary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_make_new_diary );
         final EditText Diary_name_text = findViewById(R.id.Diary_name);
         final EditText Friendtext = findViewById(R.id.Diary_friend);

        findViewById(R.id.Create_diary ).setOnClickListener( new View.OnClickListener() {

            // Do processing Title or Diary nullException
            @Override
            public void onClick(View v) {
                String Diary_Title = Diary_name_text.getText().toString();
                String Friend = Friendtext.getText().toString();

               finish();
            }
        });

    }
}
