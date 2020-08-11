package com.example.test.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.test.DB.ImageDBHelper;
import com.example.test.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import pl.polidea.view.ZoomView;


public class GalleryFragment3 extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    ImageView imageView;
    ImageDBHelper imageDBHelper;
    String image;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.gallery3_fragment, container, false);
        setViewGroup();
        return viewGroup;
    }

    public void setViewGroup(){//초기화코드.
        View v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(getContext());
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.

        LinearLayout container = (LinearLayout) viewGroup.findViewById(R.id.container);
        container.addView(zoomView);
        imageView= (ImageView)v.findViewById(R.id.imageView);

        imageDBHelper=new ImageDBHelper(getContext());
        imageDBHelper.open();
        InputStream inputStream= null;
        try {
            inputStream = getContext().getContentResolver().openInputStream(Uri.parse(image));
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
}

    @Override
    public void onClick(View view) {

    }

    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public void onDestroy() {
        imageDBHelper.close();
        super.onDestroy();
    }
}