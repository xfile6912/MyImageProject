package com.example.test;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Fragment.CommunityFragment;
import com.example.test.Fragment.GalleryFragment;
import com.example.test.Fragment.HomeFragment;
import com.example.test.Fragment.MenuFragment;
import com.example.test.Fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    CommunityFragment communityFragment;
    GalleryFragment galleryFragment;
    HomeFragment homeFragment;
    MenuFragment menuFragment;
    SettingFragment settingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        setFragment();
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