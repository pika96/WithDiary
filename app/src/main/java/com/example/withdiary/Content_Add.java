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
    //ImageView imageView;


    //datalist temp_datalist;
    //List<datalist> datalists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.write_diary);
        Titletext = findViewById( R.id.Diary_title );
        Contenttext = findViewById(R.id.Diary_input);


        //DB에 업로드 만들어 놨던 datalist 클래스에 담는 방식으로 바꿈(리스트에 담기 편함)
        //datalist(String titletext, String diarytext, String datetext) ==> 나중에 사용자 id나 다른것도 추가할 필요가있다.
        //현재는 제목, 내용, 날짜만 받았다.
        findViewById( R.id.Diary_upload ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = firebaseDatabase.getReference("Group/GroupA");
                String title = Titletext.getText().toString();
                String Content = Contenttext.getText().toString();


                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
                String str_date = sdf.format(date);

                //Send to Server
                if(title.length() > 0){
                    // Group - Groupname - Date - Title
                    datalist datalist1 = new datalist (title,Content,str_date );
                    database.child(str_date).push( ).setValue(datalist1); //날짜 안에 일기 계속 생성
                    Intent intent= new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }

        } );

       // get_Diary();

    }

    /*public void get_Diary(){
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

    }*/

}
