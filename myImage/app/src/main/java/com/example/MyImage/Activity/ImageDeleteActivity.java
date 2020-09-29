package com.example.MyImage.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MyImage.Adapter.ImageActivityAdapter;
import com.example.MyImage.Model.Folder;
import com.example.MyImage.Model.ImageCheck;
import com.example.MyImage.R;

import java.util.ArrayList;

public class ImageDeleteActivity extends AppCompatActivity {

    private Folder folder;
    private GridView gridView;
    private ImageActivityAdapter imageActivityAdapter;
    private ArrayList<ImageCheck> totalImageList;
    private ArrayList<String> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_delete);
        Intent intent=getIntent();
        images = (ArrayList<String>) intent.getSerializableExtra("images");
        folder= (Folder) intent.getSerializableExtra("folder");
        setGridView();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((ImageActivityAdapter)adapterView.getAdapter()).setChecked(i);
            }
        });

    }
    public void setGridView()
    {
        gridView=(GridView)findViewById(R.id.gridView);
        imageActivityAdapter=new ImageActivityAdapter(this, new ArrayList());
        totalImageList= new ArrayList<>();
        for(String image : images)
        {
            ImageCheck imageCheck=new ImageCheck(image, false);
            totalImageList.add(imageCheck);
        }
        imageActivityAdapter.setImages(totalImageList);
        gridView.setAdapter(imageActivityAdapter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
    }

    public void clickComplete(View view) {

        ArrayList<String> checkedImageList=new ArrayList<>();
        totalImageList=imageActivityAdapter.getItems();
        for(ImageCheck image: totalImageList)
        {
            if(image.isChecked())
                checkedImageList.add(image.getImage());
        }
        Intent resultIntent=new Intent();
        resultIntent.putExtra("checkedImageList", checkedImageList);
        setResult(200, resultIntent);
        finish();
    }
}