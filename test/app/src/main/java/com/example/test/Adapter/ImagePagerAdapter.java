package com.example.test.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList imageList;

    public ImagePagerAdapter(Context context, ArrayList imageList)
    {
        this.context=context;
        this.imageList=imageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.zoom_item, null);

        PhotoView imageView = view.findViewById(R.id.imageView);
        Glide.with(context)
                .load(imageList.get(position))
                .into(imageView);

        container.addView(view);

        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(View)object);
    }

}
