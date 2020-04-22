package com.example.withdiary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.FixedSizeDrawable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class Content_Main extends AppCompatActivity {
    ArrayList<datalist> data_list;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference databaseReference;


    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        data_list = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



//새 메모 추가
        ImageButton addbtn = findViewById(R.id.Diary_add);
        addbtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Content_Main.this,Content_Add.class );
                startActivityForResult( intent ,0);
            }
        });
        swipeRefreshLayout=findViewById( R.id.swipe );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                /*database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
                databaseReference=database.getReference("Group/GroupA/2020-04-22"); //db 테이블과 연동
                databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //데이터 받아오는 곳
                        data_list.clear();//기존 배열 리스트 초기화
                        for (DataSnapshot snapshot : dataSnapshot .getChildren()) {
                            //반복문으로 데이터 리스트 추출해온다.
                            datalist datalist = snapshot.getValue( datalist.class );//datalist객체에 값을 담는다.
                            data_list.add( datalist );//객체를 배열에 넣는다

                        }
                        recyclerAdapter.notifyDataSetChanged();
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //db가져오다 에러났을 때
                        Log.e("Content_Main", String.valueOf( databaseError.toException() ) );
                    }
                } );*/
                //Toast.makeText(Content_Main.this,"Refresh!",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerAdapter = new RecyclerAdapter(data_list,this);
        recyclerView.setAdapter(recyclerAdapter);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
        databaseReference=database.getReference("Group/GroupA/2020-04-22"); //db 테이블과 연동
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //데이터 받아오는 곳
                data_list.clear();//기존 배열 리스트 초기화
                for (DataSnapshot snapshot : dataSnapshot .getChildren()) {
                    //반복문으로 데이터 리스트 추출해온다.
                    datalist datalist = snapshot.getValue( datalist.class );//datalist객체에 값을 담는다.
                    data_list.add( datalist );//객체를 배열에 넣는다

                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //db가져오다 에러났을 때
                Log.e("Content_Main", String.valueOf( databaseError.toException() ) );
            }
        } );

        }
    }
    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        private ArrayList<datalist> list_data;
        private Context context;

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
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
            //datalist datalist = list_data.get(i);
            holder.datetext.setText(list_data.get(position).getDatetext());
            holder.titletext.setText(list_data.get(position).getTitletext());


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

            public ItemViewHolder(@NonNull View itemView){

                super(itemView);
                this.datetext=itemView.findViewById(R.id.item_date);
                this.titletext=itemView.findViewById(R.id.item_titletext);


                //img=itemView.findViewById(R.id.item_imageView);
            }
        }


    }



