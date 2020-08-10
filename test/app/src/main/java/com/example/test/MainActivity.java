package com.example.test;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Fragment.CommunityFragment;
import com.example.test.Fragment.GalleryFragment;
import com.example.test.Fragment.HomeFragment;
import com.example.test.Fragment.MenuFragment;
import com.example.test.Fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CommunityFragment communityFragment;
    GalleryFragment galleryFragment;
    HomeFragment homeFragment;
    MenuFragment menuFragment;
    SettingFragment settingFragment;
    TreeSet imageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        setFragment();
        System.out.println("activity="+this.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageList=new TreeSet();
        if(requestCode==1) {
            if (data == null) {

            } else {
                if (data.getClipData() == null)
                    Toast.makeText(this, "다중선택이 불가합니다.", Toast.LENGTH_SHORT).show();
                else {
                    ClipData clipData = data.getClipData();

                    Log.i("clipdata", String.valueOf(clipData.getItemCount()));
                    for(int i=0; i<clipData.getItemCount();i++)
                    {
                        imageList.add(String.valueOf(clipData.getItemAt(i).getUri()));
                    }

                }
            }
        }
        galleryFragment.setImages(imageList);
    }

    private void setFragment()
    {
        bottomNavigationView.setSelectedItemId(R.id.homebutton);//첫화면은 홈버튼이 클릭되어있음.
        communityFragment=new CommunityFragment();
        galleryFragment=new GalleryFragment();
        homeFragment=new HomeFragment();
        menuFragment=new MenuFragment();
        settingFragment=new SettingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout,homeFragment)
                .commitAllowingStateLoss();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.communitybutton:
                    {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_layout,communityFragment)
                                .commitAllowingStateLoss();
                        return true;
                    }

                    case R.id.gallerybutton:
                    {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_layout,galleryFragment)
                                .commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.homebutton:
                    {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_layout,homeFragment)
                                .commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.menubutton:
                    {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_layout,menuFragment)
                                .commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.settingbutton:
                    {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_layout,settingFragment)
                                .commitAllowingStateLoss();
                        return true;
                    }
                    default: return false;
                }
            }
        });
    }
}