package com.example.test.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.test.DB.ImageDBHelper;
import com.example.test.Model.Folder;
import com.example.test.R;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class FolderAdapter extends BaseAdapter {
    Context context=null;
    LayoutInflater layoutInflater=null;
    ArrayList<Folder> folders;
    ImageDBHelper imageDBHelper;
    Fragment fragment;
    public FolderAdapter(Context context, ArrayList<Folder> folders,  Fragment fragment)
    {
        this.context=context;
        this.folders=folders;
        layoutInflater=LayoutInflater.from(this.context);
        imageDBHelper=new ImageDBHelper(context);
        imageDBHelper.open();
        this.fragment=fragment;
    }
    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public Folder getItem(int position) {
        return folders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= layoutInflater.inflate(R.layout.folderlayout, null);
        String image=folders.get(position).getRepImage();
        if(image==null) {
            image = imageDBHelper.findRepImage(folders.get(position));
        }
        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
        TextView nameText=(TextView)view.findViewById(R.id.nameText);
        TextView placeText=(TextView)view.findViewById(R.id.placeText);
        TextView dateText=(TextView)view.findViewById(R.id.dateText);
        TextView withText=(TextView)view.findViewById(R.id.withText);
        String date=folders.get(position).getStartDate().format(DateTimeFormatter.ISO_DATE)+" ~ "+folders.get(position).getEndDate().format(DateTimeFormatter.ISO_DATE);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(fragment)
                .load(image)
                .into(imageView);
        nameText.setText(folders.get(position).getName());
        placeText.setText(folders.get(position).getPlace());
        dateText.setText(date);
        withText.setText(folders.get(position).getWithDescription());
        return view;
    }
    public void setFolders(ArrayList<Folder> folders){
        this.folders=folders;
    }
}

