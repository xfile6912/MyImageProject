package com.example.MyImage.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.MyImage.Model.Board;
import com.example.MyImage.R;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class BoardAdapter extends BaseAdapter {
    Context context=null;
    LayoutInflater layoutInflater=null;
    ArrayList<Board> boards;
    public BoardAdapter(Context context, ArrayList<Board> boards)
    {
        this.context=context;
        this.boards=boards;
        layoutInflater=LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return boards.size();
    }

    @Override
    public Board getItem(int position) {
        return boards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= layoutInflater.inflate(R.layout.boardlayout, null);
        TextView titleText=(TextView)view.findViewById(R.id.titleText);
        TextView locationText=(TextView)view.findViewById(R.id.locationText);
        TextView updatedAtText=(TextView)view.findViewById(R.id.updatedAtText);
        TextView updatedByText=(TextView)view.findViewById(R.id.updatedByText);
        titleText.setText(boards.get(position).getTitle());
        locationText.setText(boards.get(position).getLocation());
        updatedByText.setText(boards.get(position).getUpdatedBy());
        updatedAtText.setText(DateTimeFormatter.ISO_LOCAL_DATE.format(boards.get(position).getUpdatedAt()));
        return view;
    }
    public void setBoards(ArrayList<Board> boards){
        this.boards=boards;
    }
}

