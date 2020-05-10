package com.example.withdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    public static final int Register_CODE = 2;

    public static final int Return_OK = 100;
    public static final int Return_fail = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.register);

        final EditText NameText = findViewById(R.id.register_name_text);
        final EditText IDText = findViewById(R.id.register_id_text);
        final EditText PWText = findViewById(R.id.register_password_text);

        Button regi_btn = findViewById(R.id.register_btn);
        regi_btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                //
                String Name = NameText.getText().toString();
                String ID = IDText.getText().toString();
                String PW = PWText.getText().toString();

                if(Name.length() == 0){
                    Toast.makeText(Register.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                if(ID.length() == 0){
                    Toast.makeText(Register.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                if(PW.length() < 6){
                    Toast.makeText(Register.this, "비밀번호는 6자리이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent Access_Auth = new Intent(Register.this, DB.class);
                Access_Auth.putExtra("CODE", Register_CODE);
                Access_Auth.putExtra("name",Name);
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
                finish();
            }
            //Fail
            else if(resultCode == Return_fail){
                Toast.makeText(Register.this, "이미 존재하는 이메일 입니다.", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
