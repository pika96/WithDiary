package com.example.withdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class Content_Main extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<datalist> datalist;

    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        datalist = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(datalist);
        recyclerView.setAdapter(recyclerAdapter);

        Button addbtn = findViewById(R.id.Diary_add);
        swipeRefreshLayout = findViewById(R.id.swipe_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(Content_Main.this,"Refresh!",Toast.LENGTH_SHORT).show();
            }
        });

        addbtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(
                        getApplicationContext(), Diary_Add.class);
                startActivity(intent);
            }
        });

    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{

        private List<datalist> listdata;

        public RecyclerAdapter(List<datalist> listdata) {this.listdata=listdata;}
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
            View view = LayoutInflater.from( viewGroup.getContext()).inflate(R.layout.listitem, viewGroup ,false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder ItemViewHolder, int i){
            datalist datalist = listdata.get(i);
            ItemViewHolder.titletext.setText(datalist.getTitletext());
            ItemViewHolder.datetext.setText(datalist.getDatetext());
        }

        @Override
        public int getItemCount() {return listdata.size();}

        void additem(datalist datalist){listdata.add(datalist);}

        void remove(int position){
            listdata.remove(position);
        }
        class ItemViewHolder extends RecyclerView.ViewHolder{

            private TextView titletext;
            private TextView datetext;
            private ImageView img;

            public ItemViewHolder(@NonNull View itemView){

                super(itemView);
                titletext=itemView.findViewById(R.id.item_titletext);
                datetext=itemView.findViewById(R.id.item_date);
                img=itemView.findViewById(R.id.item_imageView);
            }
        }

    }


}
