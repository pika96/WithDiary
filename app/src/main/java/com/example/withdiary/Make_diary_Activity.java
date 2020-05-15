package com.example.withdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Make_diary_Activity extends AppCompatActivity {

    private RecyclerView listview;
    private MyAdapter adapter;

    private FirebaseAuth firebaseAuth  = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    public static final int Return_OK = 100;
    public static final int Return_fail = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        Intent get_intent = getIntent();

        String Login_ID = get_intent.getExtras().getString("id");
        String Login_PW = get_intent.getExtras().getString("pw");

        firebaseAuth.signInWithEmailAndPassword(Login_ID,Login_PW).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Intent send_intent = new Intent();
                if(task.isSuccessful()){
                    Toast.makeText(Make_diary_Activity.this, "로그인 성공.", Toast.LENGTH_SHORT).show();
                    setResult(Return_OK, send_intent);
                }else{
                    setResult(Return_fail, send_intent);
                    finish();
                }
            }
        });

        //Get Nickname
        if(TextUtils.isEmpty(firebaseUser.getDisplayName())){

            AlertDialog.Builder getNameDialog = new AlertDialog.Builder(this);
            getNameDialog.setTitle("닉네임 입력").setMessage("일기장에서 사용할 닉네임을 입력해주세요!");

            final EditText InputName = new EditText(Make_diary_Activity.this);
            getNameDialog.setView(InputName);
            getNameDialog.setCancelable(false);

            getNameDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String name = InputName.getText().toString();
                    //--------------************** 아무것도 입력 안하고 버튼 클릭시 다이얼로그 종료됨 후에 커스텀 다이얼로그를 사용하여 확인 버튼 비활성화 할것!!!!!!
                    if(TextUtils.isEmpty(name)){
                        Toast.makeText(Make_diary_Activity.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    }else {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                        firebaseUser.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Make_diary_Activity.this, "닉네임 등록 완료!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        dialog.dismiss();
                    }

                }
            });

            AlertDialog alertDialog = getNameDialog.create();
            alertDialog.show();

        }

        setContentView( R.layout.activity_make_diary_ );

        init();
        Button diary_add_btn = findViewById(R.id.make_new_diary_btn );
        diary_add_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Make_diary_Activity.this, make_new_diary.class );
                startActivity(intent);

            }
        });
    }



    private void init() {

        listview = findViewById(R.id.make_diary_listView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(layoutManager);

        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("일기1");
        itemList.add("일기2");
        itemList.add("일기3");


        adapter = new MyAdapter(this, itemList, onClickItem);
        listview.setAdapter(adapter);

        MyListDecoration decoration = new MyListDecoration();
        listview.addItemDecoration(decoration);
    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Make_diary_Activity.this, Main_Screen.class );
            startActivityForResult( intent ,0);
        }
    };
}


 class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> itemList;
    private Context context;
    private View.OnClickListener onClickItem;

    public MyAdapter(Context context, ArrayList<String> itemList, View.OnClickListener onClickItem) {
        this.context = context;
        this.itemList = itemList;
        this.onClickItem = onClickItem;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context)
                .inflate(R.layout.make_diary_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = itemList.get(position);

        holder.textview.setText(item);
        holder.textview.setTag(item);
        holder.textview.setOnClickListener(onClickItem);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);

            textview = itemView.findViewById(R.id.Diary_Title);
        }
    }
}
 class MyListDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.right = 30;
        }
    }
}
