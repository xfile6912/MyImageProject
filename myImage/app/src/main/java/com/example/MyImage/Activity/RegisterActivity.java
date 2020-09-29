package com.example.MyImage.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MyImage.Model.Network.PostURLConnector;
import com.example.MyImage.R;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText idText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private EditText emailText;
    private Button registerButton;
    private TextView validateText;
    private TextInputLayout idTextLayout;
    private TextInputLayout passwordTextLayout;
    private TextInputLayout confirmPasswordTextLayout;
    private TextInputLayout emailTextLayout;
    private PostURLConnector task;
    private AlertDialog dialog;
    private String gender;
    private boolean isValidated;
    private RadioGroup genderGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setViewGroup();
        setTextWatcher();
    }
    private void setViewGroup() {
        validateText=(TextView)findViewById(R.id.validateText);
        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        confirmPasswordText = (EditText) findViewById(R.id.confirmPasswordText);
        emailText = (EditText) findViewById(R.id.emailText);
        registerButton=(Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
        idTextLayout=(TextInputLayout)findViewById(R.id.idTextLayout);
        passwordTextLayout=(TextInputLayout)findViewById(R.id.passwordTextLayout);
        confirmPasswordTextLayout=(TextInputLayout)findViewById(R.id.confirmPasswordTextLayout);
        emailTextLayout=(TextInputLayout)findViewById(R.id.emailTextLayout);
        genderGroup=(RadioGroup)findViewById(R.id.genderGroup);
        isValidated=false;
        int genderId=genderGroup.getCheckedRadioButtonId();
        gender=((RadioButton)findViewById(genderId)).getText().toString();
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                gender=((RadioButton)findViewById(id)).getText().toString();
            }
        });
    }
    private void setTextWatcher(){
        idText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable str) {
                validateText.setText("");
                if(str.toString().length()==0)
                {
                    idTextLayout.setError("아이디는 빈칸일 수 없습니다.");
                }
                else if(str.toString().length()>20 || str.toString().length()<6)
                {
                    idTextLayout.setError("아이디는 6자이상 20자이하로 설정가능합니다.");
                }
                else {
                    idValidate();
                    idTextLayout.setError(null);
                }
            }
        });
        passwordText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable str) {
                if(!str.toString().equals(confirmPasswordText.getText().toString()) && !(confirmPasswordText.getText().toString().equals("")))
                {
                    confirmPasswordTextLayout.setError("패스워드가 일치하지 않습니다.");
                }
                else
                {
                    confirmPasswordTextLayout.setError(null);
                }
                if(str.toString().length()==0)
                {
                    passwordTextLayout.setError("패스워드는 빈칸일 수 없습니다.");
                }
                else if(str.toString().length()>20 || str.toString().length()<8)
                {
                    passwordTextLayout.setError("패스워드는 8자이상 20자이하로 설정가능합니다.");
                }
                else {
                    passwordTextLayout.setError(null);
                }
            }
        });
        confirmPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable str) {
                if(!str.toString().equals(passwordText.getText().toString()))
                {
                    confirmPasswordTextLayout.setError("패스워드가 일치하지 않습니다.");
                }
                else {
                    confirmPasswordTextLayout.setError(null);
                }
            }
        });
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable str) {
                if(!(str.toString().contains("@")))
                {
                    emailTextLayout.setError("올바른 이메일 양식이 아닙니다.");
                }
                else if(!(str.toString().contains(".kr")) && !(str.toString().contains(".com")) && !(str.toString().contains(".org")))
                {
                    emailTextLayout.setError("올바른 이메일 양식이 아닙니다.");
                }
                else {
                    emailTextLayout.setError(null);
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerButton:
                register();
                break;
        }
    }
    @SuppressLint("ResourceAsColor")
    private void idValidate(){
        try
        {
            String postData="userID=" + idText.getText().toString();
            task=new PostURLConnector("UserValidate.php",postData);
            task.start();
            try{
                task.join();
                System.out.println("waiting... for result");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result=task.getResult();
            if(result.equals("1\n")){
                validateText.setText("사용할 수 있는 아이디입니다.");
                isValidated=true;
            }
            else{
                validateText.setText("이미 존재하는 아이디입니다.");
                isValidated=false;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void register()
    {
        if(!TextUtils.isEmpty(idTextLayout.getError())
        || !TextUtils.isEmpty(passwordTextLayout.getError())
        ||!TextUtils.isEmpty(confirmPasswordTextLayout.getError())
        ||!TextUtils.isEmpty(emailTextLayout.getError()))
        {
            Toast.makeText(this, "입력정보를 다시 확인하여 주십시오.", Toast.LENGTH_SHORT).show();
        }
        else if(idText.getText().toString().length()==0
                ||passwordText.getText().toString().length()==0
                ||confirmPasswordText.getText().toString().length()==0
                ||emailText.getText().toString().length()==0)
        {
            Toast.makeText(this, "입력정보들은 빈 칸일 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(isValidated==false)
        {
            Toast.makeText(this, "사용할 수 없는 아이디 입니다.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
            if(gender.equals("남자"))
            {
                gender="MALE";
            }
            else if(gender.equals("여자"))
            {
                gender="FEMALE";
            }
            String postData = "userID=" + idText.getText().toString() + "&" + "userPassword=" + passwordText.getText().toString()
                    + "&" + "userType=" + gender + "&"+ "userEmail=" + emailText.getText().toString();
            try {
                task = new PostURLConnector("UserRegister.php", postData);
                task.start();
                try {
                    task.join();
                    System.out.println("waiting... for result");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String result = task.getResult();
                if (result.equals("1\n")) {
                    dialog = builder.setMessage("회원등록에 성공했습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .create();
                    dialog.show();
                } else {
                    dialog = builder.setMessage("회원등록에 실패했습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}