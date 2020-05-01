package com.example.withdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class Select_Diary extends AppCompatActivity {

    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.select_diary);

        TextView tx1 = findViewById(R.id.Read_Title);
        TextView tx2 = findViewById(R.id.Read_date);
        TextView tx3 = findViewById(R.id.Read_Content);
        final ImageView Read_imageview = findViewById(R.id.Read_image);

        Intent intent = getIntent();
        String diarytext= intent.getExtras().getString( "diary" );
        tx3.setText( diarytext );
        String titletext= intent.getExtras().getString( "title" );
        tx1.setText( titletext );
        String datetext= intent.getExtras().getString( "date" );
        tx2.setText( datetext );

        String imagepath = intent.getExtras().getString("imagepath");
        StorageReference storageReference = firebaseStorage.getReference().child(imagepath);

        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Glide.with(Select_Diary.this)
                            .load(task.getResult())
                            .into(Read_imageview);
                }else{
                    Toast.makeText(Select_Diary.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
