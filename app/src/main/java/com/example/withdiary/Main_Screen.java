package com.example.withdiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class Main_Screen extends AppCompatActivity {

    public static final int Main_Screen_CODE = 1;

    ArrayList<datalist> data_list;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        /*final ImageView testimage = findViewById(R.id.test_image);
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Glide.with(Main_Screen.this)
                            .load(task.getResult())
                            .into(testimage);
                } else{
                    //Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        data_list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        get_DB();

        //Write Diary
        ImageButton addbtn = findViewById(R.id.Diary_add);
        addbtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Main_Screen.this, Write_Diary.class );
                startActivityForResult( intent ,0);

            }
        });



        swipeRefreshLayout=findViewById( R.id.swipe );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                get_DB();
            }
        });


    }

    public void printscreen(){
        recyclerView.setHasFixedSize( true );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout( true );//최근 글 부터 보이게 역순 출력
        layoutManager.setStackFromEnd( true );//최근 글 부터 보이게 역순 출력
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(data_list,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }


    public void get_DB(){
        Intent Access_DB = new Intent(Main_Screen.this, DB.class);
        Access_DB.putExtra("CODE",Main_Screen_CODE);
        //+ Date recv
        startActivityForResult(Access_DB,10);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == 10) {
            if(resultCode == 11) {
                if (data.getExtras() != null) {
                    data_list = data.getExtras().getParcelableArrayList("data");
                }
                printscreen();
            }
        }
    }


    }
    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        private ArrayList<datalist> list_data;
        private Context context;

        private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();


        public RecyclerAdapter(ArrayList <datalist> list_data, Context context) {
            this.list_data = list_data;
            this.context = context;

        }



        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
            View view = LayoutInflater.from( viewGroup.getContext())
                    .inflate(R.layout.listitem, viewGroup ,false);
            ItemViewHolder holder = new ItemViewHolder( view );
            return holder;



        }

        @Override
        public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position){
            //datalist datalist = list_data.get(i);
            holder.datetext.setText(list_data.get(position).getDatetext());
            holder.titletext.setText(list_data.get(position).getTitletext());
            holder.diarytext.setText( list_data.get(position).getDiarytext() );
            holder.imagepath = list_data.get(position).getImagepath();

            Log.d("path",holder.imagepath);

            StorageReference storageReference = firebaseStorage.getReference().child(holder.imagepath);

            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Glide.with(context)
                                .load(task.getResult())
                                .into(holder.imageview);
                    }else{
                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

         }

        @Override
        public int getItemCount()
        {
            return(list_data!=null? list_data.size():0);
        }

        void additem(datalist datalist)
        {
            list_data.add(datalist);
        }

        void remove(int position){
            list_data.remove(position);
        }
        class ItemViewHolder extends RecyclerView.ViewHolder{
             TextView datetext;
             TextView titletext;
             TextView diarytext;

             String imagepath;
             ImageView imageview;

            public ItemViewHolder(@NonNull View itemView){

                super(itemView);
                this.datetext=itemView.findViewById(R.id.item_date);
                this.titletext=itemView.findViewById(R.id.item_titletext);
                this.diarytext=itemView.findViewById( R.id.item_content);
                this.imageview=itemView.findViewById(R.id.item_imageView);

                //Select Diary
                itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Context context = view.getContext();
                        Intent intent = new Intent(context, Select_Diary.class);
                        String date = datetext.getText().toString();
                        String title = titletext.getText().toString();
                        String diary = diarytext.getText().toString();

                        String imagepath = "GroupA/" + date + "/" + title + ".png";

                        intent.putExtra("date", date );
                        intent.putExtra("title", title);
                        intent.putExtra( "diary", diary );
                        intent.putExtra("imagepath", imagepath);



                        ((Activity) context).startActivity(intent);






                        //Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
                        //화면 전환 기능
                    }
                } );

                //img=itemView.findViewById(R.id.item_imageView);
            }
        }


    }



