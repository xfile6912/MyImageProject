package com.example.test.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.Model.Network.PostURLConnector;
import com.example.test.Model.User;
import com.example.test.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText idText;
    EditText passwordText;
    Button imageLoginButton;
    TextView registerButton;
    AlertDialog dialog;
    PostURLConnector task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViewGroup();
    }

    private void setViewGroup() {
        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        imageLoginButton = (Button) findViewById(R.id.imageLoginButton);
        registerButton=(TextView)findViewById(R.id.registerButton);
        imageLoginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    private void imageLogin() {
        String id=idText.getText().toString();
        String password=passwordText.getText().toString();
        String postData = "userID=" + id + "&" + "userPassword=" + password;
        AlertDialog.Builder builder=new AlertDialog.Builder(LoginActivity.this);
        if(id.equals(""))
        {
            Toast.makeText(this, "아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return ;
        }
        if(password.equals(""))
        {
            Toast.makeText(this, "비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return ;
        }
        String url="UserLogin.php";
        try
        {
            task=new PostURLConnector(url,postData);
            task.start();
            try{
                task.join();
                System.out.println("waiting... for result");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result=task.getResult();
            if(result.equals("1\n")){
                User.setUser(id, 1);//TODO:AUTHORIZATION 설정하기.
                dialog=builder.setMessage("로그인 되었습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create();
                dialog.show();
            }
            else{
                Toast.makeText(this, "아이디 또는 비밀번호가 잘못되었습니다.",Toast.LENGTH_SHORT).show();
                return;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void startRegisterActivity(){
        Intent intent=new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageLoginButton:
                imageLogin();
                break;
            case R.id.registerButton:
                startRegisterActivity();
                break;
        }

    }
}