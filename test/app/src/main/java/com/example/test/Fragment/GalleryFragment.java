package com.example.test.Fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.test.Adapter.FolderAdapter;
import com.example.test.DB.FolderDB;
import com.example.test.DB.FolderDBHelper;
import com.example.test.Dialog.AddDialog;
import com.example.test.Dialog.AddDialogListener;
import com.example.test.Dialog.SearchDialog;
import com.example.test.Dialog.SearchDialogListener;
import com.example.test.MainActivity;
import com.example.test.Model.Folder;
import com.example.test.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class GalleryFragment extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    ImageButton searchButton;
    ImageButton addButton;
    FolderAdapter folderAdapter;
    FolderDBHelper folderDBHelper;
    ListView listView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.gallery_fragment,container,false);
        setViewGroup();
        searchButton=(ImageButton)viewGroup.findViewById(R.id.searchButton);
        addButton=(ImageButton)viewGroup.findViewById(R.id.addButton);
        searchButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        return viewGroup;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setViewGroup(){
        folderDBHelper=new FolderDBHelper(getContext());
        folderDBHelper.open();
        folderDBHelper.create();
        ArrayList<Folder> folders=getFolders();
        listView = (ListView)viewGroup.findViewById(R.id.listView);
        folderAdapter=new FolderAdapter(getActivity(), folders);
        listView.setAdapter(folderAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchButton:
                SearchDialog searchDialog=new SearchDialog(getActivity());
                searchDialog.setSearchDialogListener(new SearchDialogListener() {
                    @Override
                    public void onPositiveClicked(Folder folder) {
                        ArrayList<Folder> folders=getFoldersBySearch(folder);
                        folderAdapter.setFolders(folders);
                        listView.setAdapter(folderAdapter);
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                searchDialog.callDialog();
                break;
            case R.id.addButton:
                AddDialog addDialog=new AddDialog(getActivity());
                addDialog.setAddDialogListener(new AddDialogListener() {
                    @Override
                    public void onPositiveClicked(Folder folder) {
                        folderDBHelper.insertColumn(folder);
                        ArrayList<Folder> folders=getFolders();
                        folderAdapter.setFolders(folders);
                        listView.setAdapter(folderAdapter);
                    }

                    @Override
                    public void onNegativeClicked() {
                    }
                });
                addDialog.callDialog();
                break;
        }
    }
    @Override
    public void onDestroy() {
        folderDBHelper.close();//db helper 삭제
        super.onDestroy();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Folder> getFoldersBySearch(Folder search)
    {
        ArrayList<Folder> folders=new ArrayList<>();
        Cursor cursor= folderDBHelper.findByFolder(search);
        while(cursor.moveToNext()){
            Folder folder= Folder.builder()
                    .name(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.NAME)))
                    .place(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.PLACE)))
                    .startDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.STARTDATE))))
                    .endDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.ENDDATE))))
                    .withDescription(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.WITHDESCRIPTION)))
                    .build();
            folders.add(folder);
        }
        cursor.close();
        return folders;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Folder> getFolders()//디비에서 FOLDER 가져옴.
    {
        ArrayList<Folder> folders=new ArrayList<>();
        Cursor cursor= folderDBHelper.findAll();
        while(cursor.moveToNext()){
            Folder folder= Folder.builder()
                    .name(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.NAME)))
                    .place(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.PLACE)))
                    .startDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.STARTDATE))))
                    .endDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.ENDDATE))))
                    .withDescription(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.WITHDESCRIPTION)))
                    .build();
            folders.add(folder);
        }
        cursor.close();
        return folders;
    }
}
