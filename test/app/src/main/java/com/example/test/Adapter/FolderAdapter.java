package com.example.test.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.test.Model.Folder;
import com.example.test.R;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FolderAdapter extends BaseAdapter {
    Context context=null;
    LayoutInflater layoutInflater=null;
    ArrayList<Folder> folders;
    public FolderAdapter(Context context, ArrayList<Folder> folders)
    {
        this.context=context;
        this.folders=folders;
        layoutInflater=LayoutInflater.from(this.context);
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

        TextView nameText=(TextView)view.findViewById(R.id.nameText);
        TextView placeText=(TextView)view.findViewById(R.id.placeText);
        TextView dateText=(TextView)view.findViewById(R.id.dateText);
        TextView withText=(TextView)view.findViewById(R.id.withText);
        String date=folders.get(position).getStartDate().format(DateTimeFormatter.ISO_DATE)+" ~ "+folders.get(position).getEndDate().format(DateTimeFormatter.ISO_DATE);
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

