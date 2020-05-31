package com.example.withdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Select_Diary extends AppCompatActivity {

    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.select_diary);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar3);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                show();
                break;
            case R.id.action_change:
                Toast.makeText(this, "일기 수정",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("일기를 삭제하시겠습니까?");

        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"예",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"아니오",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu3, menu);
        return true;
    }
   

}
