package com.example.MyImage.Fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.MyImage.Adapter.FolderAdapter;
import com.example.MyImage.DB.FolderDB;
import com.example.MyImage.DB.FolderDBHelper;
import com.example.MyImage.DB.ImageDBHelper;
import com.example.MyImage.Dialog.AddDialog;
import com.example.MyImage.Dialog.AddDialogListener;
import com.example.MyImage.Dialog.SearchDialog;
import com.example.MyImage.Dialog.SearchDialogListener;
import com.example.MyImage.MainActivity;
import com.example.MyImage.Model.Folder;
import com.example.MyImage.R;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class GalleryFragment extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    ImageButton searchButton;
    ImageButton addButton;
    FolderAdapter folderAdapter;
    FolderDBHelper folderDBHelper;
    ImageDBHelper imageDBHelper;
    GalleryFragment2 galleryFragment2;
    ListView listView;
    ArrayList images;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Folder folder=(Folder)adapterView.getAdapter().getItem(position);
                galleryFragment2.setFolder(folder);
                ((MainActivity)getActivity()).replaceFragmentStack(galleryFragment2);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long id) {
                final AlertDialog.Builder deleteDialog=new AlertDialog.Builder(getContext());
                deleteDialog.setTitle("삭제");
                deleteDialog.setMessage("삭제하시겠습니까?");
                deleteDialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                deleteDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(),"삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        Folder folder=(Folder)adapterView.getAdapter().getItem(position);
                        folderDBHelper.deleteRecord(folder);
                        imageDBHelper.deleteRecord(folder);
                        folderAdapter.setFolders(getFolders());
                        listView.setAdapter(folderAdapter);
                    }
                });
                deleteDialog.show();
                return true;
            }
        });
        return viewGroup;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setViewGroup(){//초기 설정.
        galleryFragment2=new GalleryFragment2();
        folderDBHelper=new FolderDBHelper(getContext());
        folderDBHelper.open();
        folderDBHelper.create();
        imageDBHelper=new ImageDBHelper(getContext());
        imageDBHelper.open();
        imageDBHelper.create();
        images=new ArrayList();
        ArrayList<Folder> folders=getFolders();
        listView = (ListView)viewGroup.findViewById(R.id.listView);
        folderAdapter=new FolderAdapter(getActivity(), new ArrayList<Folder>(), this);
        folderAdapter.setFolders(folders);
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
                    public long onPositiveClicked(Folder folder) {
                        long check=folderDBHelper.insertRecord(folder);
                        if(check!=-1)
                        {
                            Iterator<String> ir = images.iterator();
                            while (ir.hasNext()) {
                                imageDBHelper.insertColumn(folder, ir.next());
                            }
                            images = new ArrayList();//데이터 베이스에 추가하고 나서 images 초기화.
                            ArrayList<Folder> folders = getFolders();
                            folderAdapter.setFolders(folders);
                            listView.setAdapter(folderAdapter);
                        }
                        return check;
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
        imageDBHelper.close();
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
                    .repImage(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.REPIMAGE)))
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
                    .repImage(cursor.getString(cursor.getColumnIndex(FolderDB.CreateDB.REPIMAGE)))
                    .build();
            if(folder.getRepImage()!=null) {//대표이미지가 설정되어 있는지 설정되어 있지 않는지 여부. 설정되어 있다면 실제로 있는 이미지인지 확인
                ContentResolver contentResolver = getContext().getContentResolver();
                ParcelFileDescriptor parcelFileDescriptor = null;
                try {
                    parcelFileDescriptor = contentResolver.openFileDescriptor(Uri.parse(folder.getRepImage()), "r");
                } catch (FileNotFoundException e) {
                    folder.setRepImage(null);//대표이미지가 실제로 없는 이미지라면 DB에 반영
                    folderDBHelper.updateRecord(folder, folder.getName());
                } finally {
                    try {
                        parcelFileDescriptor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            folders.add(folder);
        }
        cursor.close();
        return folders;
    }

    public void setImages(ArrayList images) {
        this.images=images;
    }
}
