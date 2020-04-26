package com.example.withdiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Write_Diary extends AppCompatActivity {

    private Uri filePath;
    private ImageView imageView;
    public static final int REQUESTCODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary);
        final EditText Titletext = findViewById(R.id.Diary_title);
        final EditText Diarytext = findViewById(R.id.Diary_input);

        ImageButton imageButton = findViewById(R.id.Picture);
        imageView = findViewById(R.id.imageView);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });


        final Intent intent = new Intent(Write_Diary.this, DB.class);

        findViewById(R.id.Diary_upload).setOnClickListener(new View.OnClickListener() {

            // Do processing Title or Diary nullException
            @Override
            public void onClick(View v) {
                String Title = Titletext.getText().toString();
                String Diary = Diarytext.getText().toString();

                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
                String str_date = sdf.format(date);

                intent.putExtra("CODE",REQUESTCODE);
                intent.putExtra("keyDate",str_date);
                intent.putExtra("keyTitle",Title);
                intent.putExtra("keyDiary",Diary);
                intent.putExtra("keyPath", String.valueOf(filePath));

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            Log.d("MainActivity", "uri:" + String.valueOf(filePath));
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
