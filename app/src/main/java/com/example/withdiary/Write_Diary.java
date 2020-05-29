package com.example.withdiary;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.RestrictionsManager.RESULT_ERROR;


public class Write_Diary extends AppCompatActivity {

    private Uri filePath;
    private ImageView imageView;
    public static final int REQUESTCODE = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;


    String curUser;
    String curGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary);
        final EditText Titletext = findViewById(R.id.Diary_title);
        final EditText Diarytext = findViewById(R.id.Diary_input);

        //ImageButton imageButton = findViewById(R.id.Picture);

        Intent get_intent = getIntent();
        curUser = get_intent.getExtras().getString("curUser");
        curGroup = get_intent.getExtras().getString("curGroup");
        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Crop.pickImage(Write_Diary.this);
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);


                try{
                    intent.putExtra( "return-data",true );
                    startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
                }catch(ActivityNotFoundException e) {

                }*/



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

                intent.putExtra("CODE", REQUESTCODE);
                intent.putExtra("keyGroup", curGroup);
                intent.putExtra("keyUser", curUser);
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

        if(requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK){
            filePath = data.getData();
            Uri destinetion = Uri.fromFile( new File(getCacheDir(),"cropped"));
            Crop.of( filePath,destinetion ).asSquare().start(this);
            imageView.setImageURI( Crop.getOutput(data));
            /*Log.d("MainActivity", "uri:" + String.valueOf(filePath));
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e){
                e.printStackTrace();
            }*/
        }
            else if (requestCode==Crop.REQUEST_CROP)
            {
              handle_crop(resultCode,data);
            }



        }

    private void handle_crop(int Code, Intent result) {
        if(Code == RESULT_OK){
            imageView.setImageURI( Crop.getOutput(result));


    }
        else if (Code == RESULT_ERROR)
        {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();

        }}

}
