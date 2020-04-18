package com.example.withdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class Content_Add extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    //일단 제목과 날짜만 리스트뷰에 넣어줄꺼라서 내용부분은 아직 손을 안댔다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.write_diary);
        editText = findViewById( R.id.Diary_title );
        findViewById( R.id.Diary_upload ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText.getText().toString();
                if (title.length() > 0) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
                    String substr = sdf.format(date);

                    Intent intent = new Intent();
                    intent.putExtra( "title",title );// 키값으로 데이터 넣어줌
                    intent.putExtra( "date",substr );
                    setResult( RESULT_OK,intent );
                    finish();

                }
            }
        } );
}
}
