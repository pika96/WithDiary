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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
    public static final int Register_CODE = 2;
    public static final int Login_CODE = 3;

    public static final int Return_OK = 100;
    public static final int Return_fail = 200;
    //------------- FireBase Setting----------------
    //DB
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    // Storage
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    //private StorageReference storageReference = firebaseStorage.getReference().child("image/image.png");
    //private StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://withdiary-973ac.appspot.com");

    private FirebaseAuth firebaseAuth  = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

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
                String Imagepath = "GroupA/" + Date + "/" + Title + ".png";
                filePath = Uri.parse(strUri);

                datalist tmp_datalist = new datalist(Date, Title, Diary, Imagepath);

                DBPath = "Group/GroupA/";
                databaseReference = firebaseDatabase.getReference(DBPath);
                databaseReference.child(Date).push().setValue(tmp_datalist);
                uploadFile(Date, Title);
                finish();
                break;

            //DB download
            case Main_Screen_CODE:

                DBPath = "Group/GroupA/2020-05-01/";
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

            //Register
            case Register_CODE:
                String Regist_ID = get_intent.getExtras().getString("id");
                String Regist_PW = get_intent.getExtras().getString("pw");

                firebaseAuth.createUserWithEmailAndPassword(Regist_ID, Regist_PW)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Intent send_intent = new Intent();
                                if(task.isSuccessful()){
                                    Toast.makeText(DB.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                                    setResult(Return_OK, send_intent);
                                    finish();
                                }else{
                                    Toast.makeText(DB.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                    setResult(Return_fail, send_intent);
                                    finish();
                                }
                            }
                        });

            case Login_CODE :
                String Login_ID = get_intent.getExtras().getString("id");
                String Login_PW = get_intent.getExtras().getString("pw");


                firebaseAuth.signInWithEmailAndPassword(Login_ID, Login_PW).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Intent send_intent = new Intent();

                        if(task.isSuccessful()){
                           // String LogName = firebaseUser.getDisplayName();
                            //String LogEmail = firebaseUser.getEmail();

                            //Log.d("Name",LogName);

                          /*  if(firebaseUser.getDisplayName() == null){

                                Intent intent = new Intent (DB.this,name.class);
                                String name = intent.getExtras().getString( "name" );
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name)
                                        .build();
                                firebaseUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.d("test","12345");
                                                if(task.isSuccessful()){
                                                    Log.d("suc","User profile updated");
                                                }
                                            }
                                        });
                            }*/
                            //Log.d("Name", firebaseUser.getDisplayName());
                            setResult(Return_OK, send_intent);
                            finish();
                        }else{
                            setResult(Return_fail, send_intent);
                            finish();
                        }
                    }
                });


        }

    }
    private void uploadFile(String Date, String Title) {

        String savePath = "GroupA/" + Date + "/";

        if (filePath != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            String filename = Title + ".png";

            StorageReference storageReference = firebaseStorage.getReference().child(savePath + filename);


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
