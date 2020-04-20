package com.example.withdiary;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class Content_Main extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<datalist> data_list;


    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        data_list = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(data_list);
        recyclerView.setAdapter(recyclerAdapter);

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
                //Toast.makeText(Content_Main.this,"Refresh!",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode==0){
        String strTitle= data.getStringExtra( "title" );
        String strDate= data.getStringExtra( "date" );


// 일기제목 "title" 일기내용"content" 일기 날짜 "date"

        datalist diary = new datalist (strTitle, "diary Content", strDate);
        recyclerAdapter.additem( diary );
        recyclerAdapter.notifyDataSetChanged();

        }
    }
    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

        private List<datalist> list_data;

        public RecyclerAdapter(List<datalist> listdata) {this.list_data=listdata;}
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
            View view = LayoutInflater.from( viewGroup.getContext())
                    .inflate(R.layout.listitem, viewGroup ,false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder ItemViewHolder, int i){
            datalist datalist = list_data.get(i);
            ItemViewHolder.titletext.setText(datalist.getTitletext());
            ItemViewHolder.datetext.setText(datalist.getDatetext());
        }

        @Override
        public int getItemCount() {return list_data.size();}

        void additem(datalist datalist){list_data.add(datalist);}

        void remove(int position){
            list_data.remove(position);
        }
        class ItemViewHolder extends RecyclerView.ViewHolder{

            private TextView titletext;
            private TextView datetext;


            public ItemViewHolder(@NonNull View itemView){

                super(itemView);
                titletext=itemView.findViewById(R.id.item_titletext);
                datetext=itemView.findViewById(R.id.item_date);

                //img=itemView.findViewById(R.id.item_imageView);
            }
        }


    }


}
