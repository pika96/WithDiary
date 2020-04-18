package com.example.withdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

//    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//    private DatabaseReference databaseReference = firebaseDatabase.getReference();
//
//    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    //private StorageReference storageReference = firebaseStorage.getReference().child("image/image.png");
    //private StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://withdiary-973ac.appspot.com");

//    private Uri filePath;
//    final ImageView imageView = findViewById(R.id.imageView);
//    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

//        imageView = findViewById(R.id.imageView);

//
//        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if(task.isSuccessful()){
//                    Glide.with(MainActivity.this)
//                            .load(task.getResult())
//                            .into(imageView);
//                } else{
//                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//     Button btn = findViewById(R.id.button);
//     Button btn2 = findViewById(R.id.button2);

//     btn.setOnClickListener(new Button.OnClickListener(){
//         public void onClick(View v){
//             //databaseReference.child("message").push().setValue("2"); //////DB
//
//         }
//     });
//
//        btn.setOnClickListener(new Button.OnClickListener(){
//            public void onClick(View v){
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"이미지를 선택하세요."),0);
//            }
//        });
//
//        btn2.setOnClickListener(new Button.OnClickListener(){
//            public void onClick(View v){
//                uploadFile();
//            }
//        });

//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 0 && resultCode == RESULT_OK){
//            filePath = data.getData();
//            Log.d("MainActivity", "uri:" + String.valueOf(filePath));
//            try{
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void uploadFile(){
//
//        if(filePath != null){
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("업로드중...");
//            progressDialog.show();
//
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyMMHH_mmss");
//            Date now = new Date();
//            String filename = formatter.format(now) + ".png";
//
//            StorageReference storageReference = firebaseStorage
//                    .getReferenceFromUrl("gs://withdiary-973ac.appspot.com").child("image/" + filename);
//
//
//            storageReference.putFile(filePath)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//
//                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
//                        }
//                    });
//        }else{
//            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요", Toast.LENGTH_SHORT).show();
//        }
    }

}
