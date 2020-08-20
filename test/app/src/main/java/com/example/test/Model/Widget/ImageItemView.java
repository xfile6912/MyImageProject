package com.example.test.Model.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.bumptech.glide.Glide;
import com.example.test.R;

public class ImageItemView extends LinearLayout {

    ImageView imageView;
    CheckBox checkBox;
    Context context;
    public ImageItemView(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    public ImageItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.imagelayout, this, true);

        checkBox = (CheckBox) findViewById(R.id.checkbox);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setImageView(String uri)
    {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new CoordinatorLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        Glide.with(context)
                .load(uri)
                .override(300)
                .into(imageView);
    }
    public void setCheckBox(boolean checked)
    {
        checkBox.setChecked(checked);
    }
}
