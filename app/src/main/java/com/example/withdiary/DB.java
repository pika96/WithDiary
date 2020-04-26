package com.example.withdiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private Uri filePath;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data_list = new ArrayList<>();

        final Intent get_intent = getIntent();
        int CODE = get_intent.getExtras().getInt("CODE");
        String DBPath;

        switch (CODE) {
            //DB upload
            case Write_Diary_CODE:

                String Date = get_intent.getExtras().getString("keyDate");
                String Title = get_intent.getExtras().getString("keyTitle");
                String Diary = get_intent.getExtras().getString("keyDiary");
                String strUri = get_intent.getExtras().getString("keyPath");
                filePath = Uri.parse(strUri);

                datalist tmp_datalist = new datalist(Date, Title, Diary);

                DBPath = "Group/GroupA/";
                databaseReference = firebaseDatabase.getReference(DBPath);
                databaseReference.child(Date).push().setValue(tmp_datalist);
                uploadFile();
                finish();
                break;

            //DB download
            case Main_Screen_CODE:

                DBPath = "Group/GroupA/2020-04-26/";
                databaseReference = firebaseDatabase.getReference(DBPath);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        data_list.clear();

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            datalist datalist = snapshot.getValue(datalist.class);
                            data_list.add(datalist);
                        }

                        Intent send_intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("data", data_list);
                        send_intent.putExtras(bundle);
                        setResult(11, send_intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
        }

    }
    private void uploadFile() {

        if (filePath != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";

            StorageReference storageReference = firebaseStorage
                    .getReferenceFromUrl("gs://withdiary-973ac.appspot.com").child("image/" + filename);


            storageReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(progressDialog != null){
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }
}
