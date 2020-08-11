package com.example.test.Fragment;

import android.Manifest;
import android.database.Cursor;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.test.Adapter.ImageAdapter;
import com.example.test.DB.ImageDB;
import com.example.test.DB.ImageDBHelper;
import com.example.test.Model.Folder;
import com.example.test.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;


public class GalleryFragment2 extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    GridView gridView;
    ImageDBHelper imageDBHelper;
    ImageAdapter imageAdapter;
    Folder folder;
    ArrayList images;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.gallery2_fragment, container, false);
        setViewGroup();
        return viewGroup;
    }

    public void setViewGroup(){//초기화코드.

        gridView= (GridView)viewGroup.findViewById(R.id.gridView);
        imageDBHelper=new ImageDBHelper(getContext());
        imageDBHelper.open();
        images=getImagesByFolder(folder);
        imageAdapter=new ImageAdapter(getActivity(), new ArrayList());
        imageAdapter.setImages(images);
        gridView.setAdapter(imageAdapter);
    }

    @Override
    public void onClick(View view) {

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