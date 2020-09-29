package com.example.MyImage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.MyImage.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    Context context=null;
    ArrayList<String> images;
    Fragment fragment;
    public ImageAdapter(Context context, ArrayList images, Fragment fragment)
    {
        this.context=context;
        this.images=images;
        this.fragment = fragment;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public String getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        ImageView imageView;
        if(contentView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = inflater.inflate(R.layout.imagelayout, parent, false);
        }
        imageView=(ImageView) contentView.findViewById(R.id.imageView);
            String uri=images.get(position);
        Glide.with(context)
                .load(uri)
                .override(300)
                .error(R.drawable.icon_main)
                .into(imageView);
        return imageView;
    }
    public void setImages(ArrayList images)
    {
        this.images=images;
    }
}
