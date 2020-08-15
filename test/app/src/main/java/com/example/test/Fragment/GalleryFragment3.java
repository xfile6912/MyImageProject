package com.example.test.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.test.Adapter.ImagePagerAdapter;
import com.example.test.DB.ImageDBHelper;
import com.example.test.R;

import java.util.ArrayList;

import pl.polidea.view.ZoomView;


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