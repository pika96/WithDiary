package com.example.withdiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

//Receive Content & upload Server

public class Content_Add extends AppCompatActivity {

    //------------- FireBase Setting----------------
    //DB
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Group/GroupA");
    // Storage
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    //private StorageReference storageReference = firebaseStorage.getReference().child("image/image.png");
    //private StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://withdiary-973ac.appspot.com");

    //------------- UI -----------------------------
    EditText Titletext;
    EditText Contenttext;
    ImageView imageView;


    datalist temp_datalist;
    List<datalist> datalists = new ArrayList<>();
    //일단 제목과 날짜만 리스트뷰에 넣어줄꺼라서 내용부분은 아직 손을 안댔다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.write_diary);
        Titletext = findViewById( R.id.Diary_title );
        Contenttext = findViewById(R.id.Diary_input);



        findViewById( R.id.Diary_upload ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Titletext.getText().toString();
                String Content = Contenttext.getText().toString();

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
                String str_date = sdf.format(date);

                //Send to Server
                if(title.length() > 0){
                    // Group - Groupname - Date - Title
                    databaseReference.child(str_date).child(title).setValue(Content);
                }
                //Send to Content_Main
                if (title.length() > 0) {

                    Intent intent = new Intent();
                    intent.putExtra( "title", title );// 키값으로 데이터 넣어줌
                    intent.putExtra( "date", str_date );
                    setResult( RESULT_OK, intent);
                    finish();
                }
            }
        } );

        get_Diary();

    }

    public void get_Diary(){
        // Read DB
        DatabaseReference dataR = firebaseDatabase.getReference("Group/GroupA/2020-04-21");

            dataR.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot diaryData : dataSnapshot.getChildren()){
                        Log.d("test",diaryData.getKey());
                        datalists.add(new datalist(diaryData.getKey(), diaryData.getValue().toString(), "2020-04-21"));
                    }
                    temp_datalist = datalists.get(0);
                    Log.d("test", temp_datalist.diarytext);
                    Log.d("test", temp_datalist.titletext);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

}
