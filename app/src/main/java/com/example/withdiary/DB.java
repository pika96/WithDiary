package com.example.withdiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

//Receive Content & upload Server

public class DB extends AppCompatActivity {

    public static final int Write_Diary_CODE = 0;
    public static final int Main_Screen_CODE = 1;
    //------------- FireBase Setting----------------
    //DB
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    // Storage
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    //private StorageReference storageReference = firebaseStorage.getReference().child("image/image.png");
    //private StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://withdiary-973ac.appspot.com");

    //------------- UI -----------------------------
    //EditText Titletext;
    //EditText Diarytext;
    //ImageView imageView;

    //datalist temp_datalist;
    //List<datalist> datalists = new ArrayList<>();

    ArrayList<datalist> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        data_list = new ArrayList<>();

        Intent get_intent = getIntent();
        int CODE = get_intent.getExtras().getInt("CODE");
        String DBPath;

        switch (CODE){
            //DB upload
            case Write_Diary_CODE:

                String Date = get_intent.getExtras().getString("keyDate");
                String Title = get_intent.getExtras().getString("keyTitle");
                String Diary = get_intent.getExtras().getString("keyDiary");

                datalist tmp_datalist = new datalist(Date, Title, Diary);

                DBPath = "Group/GroupA/";
                databaseReference = firebaseDatabase.getReference(DBPath);
                databaseReference.child(Date).push().setValue(tmp_datalist);
                break;

            //DB download
            case Main_Screen_CODE:

                DBPath = "Group/GroupA/2020-04-22";
                databaseReference = firebaseDatabase.getReference(DBPath);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("test","0");
                        data_list.clear();
                        for (DataSnapshot snapshot : dataSnapshot .getChildren()) {

                            datalist datalist = snapshot.getValue( datalist.class );
                            data_list.add( datalist );

                        }
                        Log.d("test","Asdfa");
                        Intent send_intent = new Intent();
                        send_intent.putExtra("data", data_list);
                        setResult(RESULT_OK, send_intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        }

        finish();
        //setContentView( R.layout.write_diary);
        //Titletext = findViewById( R.id.Diary_title );
        //Diarytext = findViewById(R.id.Diary_input);
       //DB에 업로드 만들어 놨던 datalist 클래스에 담는 방식으로 바꿈(리스트에 담기 편함)
        //==> 나중에 사용자 id나 다른것도 추가할 필요가있다.
        //현재는 날짜, 제목, 내용만 받았다.
//        findViewById( R.id.Diary_upload ).setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference database = firebaseDatabase.getReference("Group/GroupA");
//                String title = Titletext.getText().toString();
//                String Diary = Diarytext.getText().toString();
//                Date date = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
//                String str_date = sdf.format(date);
//
//                //Send to Server
//                if(title.length() > 0){
//                    // Group - Groupname - Date - Title
//                    datalist datalist1 = new datalist (str_date,title,Diary );
//                    database.child(str_date).push( ).setValue(datalist1); //날짜 안에 일기 계속 생성
//                    Intent intent= new Intent();
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }
//
//            }
//
//        } );

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
