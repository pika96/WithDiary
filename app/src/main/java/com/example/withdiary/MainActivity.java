package com.example.withdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        setContentView(R.layout.main_screen);

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
