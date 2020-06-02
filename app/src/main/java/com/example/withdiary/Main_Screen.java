package com.example.withdiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Random;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class Main_Screen extends AppCompatActivity {

    public static final int Main_Screen_CODE = 1;
    ArrayList<datalist> data_list;
    RecyclerAdapter recyclerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String cur_User;
    public String cur_Group;
    Toolbar myToolbar;
    RecyclerView recyclerView;
    ImageView emptyView;
    ImageView notemptyView;
    int[] image = {R.drawable.flower3_2, R.drawable.phrase1, R.drawable.phrase2, R.drawable.phrase3, R.drawable.phrase4, R.drawable.phrase5};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Intent get_Intent = getIntent();
        cur_User = get_Intent.getExtras().getString("cur_User");
        cur_Group = get_Intent.getExtras().getString("cur_Group");
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(cur_Group);
        data_list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        emptyView=findViewById( R.id.empty_text );
        notemptyView=findViewById( R.id.not_empty_image );
        get_DB();




        //Write Diary
        ImageButton addbtn = findViewById(R.id.Diary_add);
        addbtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Main_Screen.this, Write_Diary.class );
                intent.putExtra("curUser", cur_User);
                intent.putExtra("curGroup", cur_Group);
                startActivity(intent);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete_diary:
                show();
                break;
                 }
        return true;
    }

    private void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("일기장을 삭제하시겠습니까?");
        builder.setMessage("진짜로 삭제됩니다");
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
        menuInflater.inflate(R.menu.menu2, menu);
        return true;
    }


    public void printscreen(){
        recyclerView.setHasFixedSize( true );
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout( true );//최근 글 부터 보이게 역순 출력
        layoutManager.setStackFromEnd( true );//최근 글 부터 보이게 역순 출력
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(data_list,this, cur_Group);
        recyclerView.setAdapter(recyclerAdapter);


        if (recyclerAdapter.getItemCount() == 0) {

            emptyView.setVisibility(View.VISIBLE);
            notemptyView.setVisibility( View.GONE );
        }
        else {
            Random random = new Random(  );
            int num = random.nextInt(image.length);
            notemptyView.setImageResource( image[num] );
            emptyView.setVisibility(View.GONE);
            notemptyView.setVisibility( View.VISIBLE );
        }
        recyclerAdapter.notifyDataSetChanged();
    }


    public void get_DB(){
        Intent Access_DB = new Intent(Main_Screen.this, DB.class);
        Access_DB.putExtra("CODE",Main_Screen_CODE);
        Access_DB.putExtra("curGroup", cur_Group);
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
        String curGroup;

        private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();



        public RecyclerAdapter(ArrayList <datalist> list_data, Context context, String curGroup) {
            this.list_data = list_data;
            this.context = context;
            this.curGroup = curGroup;

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

            holder.datetext.setText(list_data.get(position).getDatetext());
            holder.titletext.setText(list_data.get(position).getTitletext());
            holder.diarytext = list_data.get(position).getDiarytext();
            holder.imagepath = list_data.get(position).getImagepath();
            holder.writeUser.setText(list_data.get(position).getWriteUser());


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
            if (list_data == null) {
                return 0;
            }

            return list_data.size();

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
             String diarytext;
             TextView writeUser;

             String imagepath;
             ImageView imageview;

            public ItemViewHolder(@NonNull View itemView){

                super(itemView);
                this.datetext=itemView.findViewById(R.id.date_content);
                this.titletext=itemView.findViewById(R.id.item_titletext);
                this.imageview=itemView.findViewById(R.id.item_imageView);
                this.writeUser=itemView.findViewById(R.id.id_content);

                //Select Diary
                itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Context context = view.getContext();
                        Intent intent = new Intent(context, Select_Diary.class);
                        String date = datetext.getText().toString();
                        String title = titletext.getText().toString();
                        String diary = diarytext;

                        String imagepath = curGroup + "/" + date + "/" + title + ".png";

                        intent.putExtra("date", date );
                        intent.putExtra("title", title);
                        intent.putExtra( "diary", diary);
                        intent.putExtra("imagepath", imagepath);

                        ((Activity) context).startActivity(intent);

                    }
                } );

                //img=itemView.findViewById(R.id.item_imageView);
            }
        }


    }




