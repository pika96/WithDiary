package com.example.withdiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class Main_Screen extends AppCompatActivity {

    public static final int Main_Screen_CODE = 1;

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
        setContentView(R.layout.main_screen);

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
//        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터베이스 연동
//        databaseReference=database.getReference("Group/GroupA/2020-04-22"); //db 테이블과 연동
//        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                //데이터 받아오는 곳
//                data_list.clear();//기존 배열 리스트 초기화
//                for (DataSnapshot snapshot : dataSnapshot .getChildren()) {
//                    //반복문으로 데이터 리스트 추출해온다.
//                    datalist datalist = snapshot.getValue( datalist.class );//datalist객체에 값을 담는다.
//                    data_list.add( datalist );//객체를 배열에 넣는다
//
//                }
//                recyclerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                //db 가져오다 에러났을 때
//                Log.e("Content_Main", String.valueOf( databaseError.toException() ) );
//            }
//        } );


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
                itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Context context = view.getContext();
                        Intent intent = new Intent(context, Select_Diary.class);
                        ((Activity) context).startActivity(intent);



                        //Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
                        //화면 전환 기능
                    }
                } );

                //img=itemView.findViewById(R.id.item_imageView);
            }
        }


    }



