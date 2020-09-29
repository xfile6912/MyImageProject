package com.example.MyImage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.MyImage.Activity.LoginActivity;
import com.example.MyImage.Model.User;
import com.example.MyImage.R;

public class SettingFragment extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    Button loginButton;
    TextView loginText;
    Button alarmButton;
    Button accountSettingButton;
    Button securityButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.setting_fragment,container,false);
        loginButton=(Button)viewGroup.findViewById(R.id.loginButton);
        alarmButton=(Button)viewGroup.findViewById(R.id.alarmButton);
        accountSettingButton=(Button)viewGroup.findViewById(R.id.accountSettingButton);
        securityButton=(Button)viewGroup.findViewById(R.id.securityButton);
        loginText=(TextView)viewGroup.findViewById(R.id.loginText);
        loginButton.setOnClickListener(this);
        alarmButton.setOnClickListener(this);
        accountSettingButton.setOnClickListener(this);
        securityButton.setOnClickListener(this);
        loginText.setOnClickListener(this);
        setLoginButton();
        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();
        setLoginButton();
    }

    public void setLoginButton()
    {
        if(User.getLoginUser().getId()!=null)
        {
            loginText.setText(User.getLoginUser().getId()+"님");
            loginButton.setText("로그아웃");
        }
        else
        {
            loginText.setText("로그인 하십시오.");
            loginButton.setText("로그인");
        }
    }
    public void startLoginActivity()
    {
        if(User.getLoginUser().getId()!=null)//로그인 유저가 있다면 로그아웃 시킴.
        {
            User.setUser(null, 0);
            setLoginButton();
            Toast.makeText(getContext(),"로그아웃 되었습니다.", Toast.LENGTH_SHORT);
        }
        else//로그인 유저가 없다면 로그인 창으로 이동.
        {
            Intent intent=new Intent(getContext(), LoginActivity.class);
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                startLoginActivity();
                break;
            case R.id.alarmButton://TODO
                break;
            case R.id.accountSettingButton://TODO
                break;
            case R.id.securityButton://TODO
                break;
            case R.id.loginText:
                startLoginActivity();
                break;
        }
    }
}
