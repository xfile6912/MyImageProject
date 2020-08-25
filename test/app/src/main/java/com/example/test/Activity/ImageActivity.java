package com.example.test.Activity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Adapter.ImageActivityAdapter;
import com.example.test.Model.ImageCheck;
import com.example.test.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class ImageActivity extends AppCompatActivity {
    private ArrayList imageFolderList;
    private Spinner folderSpinner;
    private GridView gridView;
    private ImageActivityAdapter imageActivityAdapter;
    private ArrayList<ImageCheck> totalImageList;
    private TreeSet<String> pickedImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent=getIntent();
        pickedImages= (TreeSet<String>) intent.getSerializableExtra("pickedImages");
        setSpinner();
        setGridView();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               ((ImageActivityAdapter)adapterView.getAdapter()).setChecked(i);
            }
        });
        folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateTreeSet();//spinner에서 앨범폴더가 변경되었을 때
                if(imageFolderList.get(i).equals("전체사진"))
                {
                    getAllImages();
                }
                else
                {
                    getFolderImages((String) imageFolderList.get(i));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void setGridView()
    {
        gridView=(GridView)findViewById(R.id.gridView);
        imageActivityAdapter=new ImageActivityAdapter(this, new ArrayList());
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
    }
    public void getAllImages()
    {
        String [] projection2 = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA};
        totalImageList =new ArrayList();
        Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection2,null, null,MediaStore.Images.Media.DATE_ADDED+" desc");
        if((imageCursor!=null) && imageCursor.moveToFirst()==true)
        {
            int id=imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            do
            {
                int realid=imageCursor.getInt(id);
                Uri uri= ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,realid);
                String stringUri=(String.valueOf(uri));
                ImageCheck imageCheck=ImageCheck.builder().image(stringUri).checked(pickedImages.contains(stringUri)).build();
                totalImageList.add(imageCheck);

            }while(imageCursor.moveToNext());
        }
        imageCursor.close();

        imageActivityAdapter.setImages(totalImageList);

        gridView.setAdapter(imageActivityAdapter);
        for (int i = 0; i < gridView.getCount(); i++) {
            if (totalImageList.get(i).isChecked()) {
                gridView.setItemChecked(i, true);
            } else {
                gridView.setItemChecked(i, false);
            }
        }
    }
    public void getFolderImages(String folderName){
        String [] projection2 = {MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA};
        totalImageList =new ArrayList();
        Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection2,MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME +"='"+folderName+"'", null,MediaStore.Images.Media.DATE_ADDED+" desc");
        if((imageCursor!=null) && imageCursor.moveToFirst()==true)
        {
            int id=imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            do
            {
                int realid=imageCursor.getInt(id);
                Uri uri= ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,realid);
                String stringUri=(String.valueOf(uri));
                ImageCheck imageCheck=ImageCheck.builder().image(stringUri).checked(pickedImages.contains(stringUri)).build();
                totalImageList.add(imageCheck);

            }while(imageCursor.moveToNext());
        }
        imageCursor.close();
        imageActivityAdapter.setImages(totalImageList);
        gridView.setAdapter(imageActivityAdapter);
        for (int i = 0; i < gridView.getCount(); i++) {
            if (totalImageList.get(i).isChecked()) {
                gridView.setItemChecked(i, true);
            } else {
                gridView.setItemChecked(i, false);
            }
        }
    }


    public void setSpinner(){
        final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME};
        HashSet folder=new HashSet();
        Cursor cursor = getContentResolver().query(contentUri,projection, null, null, null);

        if((cursor!=null) && cursor.moveToFirst()==true)
        {
            final int columnBucketName=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            do
            {
                final String bucketName=cursor.getString(columnBucketName);
                folder.add(bucketName);

            } while(cursor.moveToNext());
        }
        cursor.close();
        Iterator ir=folder.iterator();
        imageFolderList=new ArrayList();
        imageFolderList.add("전체사진");
        while(ir.hasNext())
        {
            imageFolderList.add(ir.next());
        }
        folderSpinner = (Spinner)findViewById(R.id.folderSpinner);
        folderSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,imageFolderList));
    }
    public void updateTreeSet(){
        ArrayList<ImageCheck> totalImages=imageActivityAdapter.getItems();
        for(ImageCheck image:totalImages)
        {
            if(image.isChecked()==true)//폴더 안에 이미지가 check되었으면 pickedImages에 넣어줌.
            {
                pickedImages.add(image.getImage());
            }
            else//check가 안되어 있는데 pickedImages안에 있으면 제거해줌.
            {
                if(pickedImages.contains(image.getImage()))
                    pickedImages.remove(image.getImage());
            }
        }
    }
    public void clickComplete(View view) {
        updateTreeSet();//완료가 눌렸을때도 반영해주어야 하므로.
        ArrayList<String> checkedImageList=new ArrayList<>();
        for(String image:pickedImages)
        {
            checkedImageList.add(image);
        }
        Intent resultIntent=new Intent();
        resultIntent.putExtra("checkedImageList", checkedImageList);
        setResult(200, resultIntent);
        finish();
    }
}