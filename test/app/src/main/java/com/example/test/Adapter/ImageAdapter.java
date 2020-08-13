package com.example.test.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    Context context=null;
    LayoutInflater layoutInflater=null;
    ArrayList<String> images;
    Bitmap bitmap;
    Fragment fragment;
    public ImageAdapter(Context context, ArrayList images, Fragment fragment)
    {
        this.context=context;
        this.images=images;
        layoutInflater=LayoutInflater.from(this.context);
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
            imageView=new ImageView(context);
        }
        else
        {
            imageView=(ImageView)contentView;
        }
            ImageLoader imageLoader=ImageLoader.getInstance();
            String uri=images.get(position);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
            Glide.with(fragment)
                    .load(uri)
                    .override(300)
                    .into(imageView);



        return imageView;
    }
    public void setImages(ArrayList images)
    {
        this.images=images;
    }
}
