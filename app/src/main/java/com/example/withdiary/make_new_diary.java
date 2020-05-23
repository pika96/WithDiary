package com.example.withdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class make_new_diary extends AppCompatActivity {

    public static final int Group_Create_CODE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_make_new_diary );
         final EditText Diary_name_text = findViewById(R.id.Diary_name);
         final EditText Friendtext = findViewById(R.id.Diary_friend);

        Intent get_intent = getIntent();
        final String UID = get_intent.getExtras().getString("UID");

        findViewById(R.id.Create_diary ).setOnClickListener( new View.OnClickListener() {

            // Do processing Title or Diary nullException
            @Override
            public void onClick(View v) {
                String Diary_Title = UID + "_" + Diary_name_text.getText().toString();
                String Friend_UID1 = Friendtext.getText().toString();

                //그룹 이름 검사 //그룹 이름 제한 //그룹 이름에 언더바 _ 들어가지 않도록!
                Intent send_intent = new Intent(make_new_diary.this, DB.class );
                send_intent.putExtra("CODE", Group_Create_CODE);
                send_intent.putExtra("Diary_Title", Diary_Title);
                send_intent.putExtra("UID1", UID);
                send_intent.putExtra("UID2", Friend_UID1);

                startActivity(send_intent);
                finish();
            }
        });

    }
}
