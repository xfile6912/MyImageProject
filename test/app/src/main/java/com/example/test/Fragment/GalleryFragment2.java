package com.example.test.Fragment;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.test.Adapter.ImageAdapter;
import com.example.test.DB.FolderDBHelper;
import com.example.test.DB.ImageDB;
import com.example.test.DB.ImageDBHelper;
import com.example.test.Dialog.UpdateDialog;
import com.example.test.Dialog.UpdateDialogListener;
import com.example.test.MainActivity;
import com.example.test.Model.Folder;
import com.example.test.R;

import java.util.ArrayList;


public class GalleryFragment2 extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    GridView gridView;
    TextView nameText;
    FolderDBHelper folderDBHelper;
    ImageDBHelper imageDBHelper;
    ImageAdapter imageAdapter;
    Folder folder;
    GalleryFragment3 galleryFragment3;
    ArrayList images;
    ImageButton editButton;
    ImageButton deleteButton;//구현하기
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.gallery2_fragment, container, false);
        setViewGroup();
        return viewGroup;
    }

    public void setViewGroup(){//초기화코드.
        galleryFragment3=new GalleryFragment3();
        gridView= (GridView)viewGroup.findViewById(R.id.gridView);
        nameText=(TextView)viewGroup.findViewById(R.id.nameText);
        nameText.setText(folder.getPlace());
        editButton=(ImageButton)viewGroup.findViewById(R.id.editButton);
        deleteButton=(ImageButton)viewGroup.findViewById(R.id.deleteButton);
        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        folderDBHelper=new FolderDBHelper(getContext());
        folderDBHelper.open();
        folderDBHelper.create();
        imageDBHelper=new ImageDBHelper(getContext());
        imageDBHelper.open();
        images=getImagesByFolder(folder);
        imageAdapter=new ImageAdapter(getActivity(), new ArrayList(), this);
        imageAdapter.setImages(images);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                galleryFragment3.setImages(images, position);
                ((MainActivity)getActivity()).replaceFragmentStack(galleryFragment3);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editButton:
                UpdateDialog updateDialog = new UpdateDialog(getActivity(), folder);
                updateDialog.setUpdateDialogListener(new UpdateDialogListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public int onPositiveClicked(Folder updatedFolder) {
                        int check = folderDBHelper.updateRecord(updatedFolder, folder.getName());

                        if (check == 1) {//폴더이름 중복일 때 오류잡기.
                            imageDBHelper.updateRecord(updatedFolder, folder.getName());
                    /*Iterator<String> ir = images.iterator();
                    while (ir.hasNext()) {
                        imageDBHelper.insertColumn(folder, ir.next());
                    }
                    images = new ArrayList();//데이터 베이스에 추가하고 나서 images 초기화.*/
                            folder = updatedFolder;
                            nameText.setText(folder.getPlace());
                            imageAdapter.setImages(images);
                            gridView.setAdapter(imageAdapter);
                        }
                        return check;
                    }

                    @Override
                    public void onNegativeClicked() {
                    }

                });
                updateDialog.callDialog();
                break;
        }
    }
    public ArrayList getImagesByFolder(Folder folder){
        ArrayList images=new ArrayList();
        Cursor cursor=imageDBHelper.findByFolder(folder);
        while(cursor.moveToNext()){
            String image=cursor.getString(cursor.getColumnIndex(ImageDB.CreateDB.IMAGEURI));
            images.add(image);
        }
        return images;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
    @Override
    public void onDestroy() {
        imageDBHelper.close();
        super.onDestroy();
    }
}