package com.example.withdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity{

    public static final int Return_OK = 100;
    public static final int Return_fail = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.login);

        //register button Click
        Button regi_btn = findViewById(R.id.register_start_btn);
        regi_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent reg_intent = new Intent(getApplicationContext(), Register.class);
                startActivity(reg_intent);
            }
        });

        final EditText Login_id = findViewById(R.id.id_text);
        final EditText Login_pw = findViewById(R.id.password_text);

        Button login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                String ID = Login_id.getText().toString();
                String PW = Login_pw.getText().toString();
                if(ID.length() == 0){
                    Toast.makeText(Login.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                if(PW.length() < 6) {
                    Toast.makeText(Login.this, "비밀번호는 6자리이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent Access_Auth = new Intent(Login.this, Make_diary_Activity.class);
                Access_Auth.putExtra("id",ID);
                Access_Auth.putExtra("pw",PW);

                startActivityForResult(Access_Auth,20);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == 20) {
            //OK
            if(resultCode == Return_OK) {
                //Toast.makeText(Login.this, "로그인 성공.", Toast.LENGTH_SHORT).show();
                //Intent Login_Success = new Intent(Login.this, Make_diary_Activity.class);
                //startActivity(Login_Success);
                finish();
            }
            //Fail
            else if(resultCode == Return_fail){
                Toast.makeText(Login.this, "로그인 실패.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
