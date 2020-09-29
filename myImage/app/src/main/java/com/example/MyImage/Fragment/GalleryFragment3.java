package com.example.MyImage.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.MyImage.Adapter.ImagePagerAdapter;
import com.example.MyImage.DB.ImageDBHelper;
import com.example.MyImage.R;

import java.util.ArrayList;


public class GalleryFragment3 extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    ImageDBHelper imageDBHelper;
    ArrayList images;
    ViewPager imageViewPager;
    int position;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.gallery3_fragment, container, false);
        setViewGroup();
        return viewGroup;
    }

    public void setViewGroup(){//초기화코드.
        imageViewPager=viewGroup.findViewById(R.id.imageViewPager);
        imageViewPager.setClipToPadding(false);
        imageViewPager.setAdapter(new ImagePagerAdapter(getContext(), images));
        imageViewPager.setCurrentItem(position);
    }

    @Override
    public void onClick(View view) {

    }

    public void setImages(ArrayList images, int position) {
        this.images = images;
        this.position=position;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}