package com.example.test.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.test.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeSet;

public class ImageAdapter extends BaseAdapter {
    Context context=null;
    LayoutInflater layoutInflater=null;
    ArrayList<String> images;
    Bitmap bitmap;
    public ImageAdapter(Context context, ArrayList images)
    {
        this.context=context;
        this.images=images;
        layoutInflater=LayoutInflater.from(this.context);
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
        try {
            InputStream inputStream= context.getContentResolver()
                    .openInputStream(Uri.parse(images.get(position)));
            bitmap = BitmapFactory.decodeStream(inputStream);
            Bitmap Thumbnail= ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
            imageView.setImageBitmap(Thumbnail);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return imageView;
    }
    public void setImages(ArrayList images)
    {
        this.images=images;
    }
}
