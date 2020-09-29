package com.example.MyImage.Model.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.MyImage.R;

public class CheckableCoordinatorLayout extends CoordinatorLayout implements Checkable {
    public CheckableCoordinatorLayout(@NonNull Context context) {
        super(context);
    }

    public CheckableCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox) ;
        cb.setChecked(checked) ;
    }

    @Override
    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox) ;

        return cb.isChecked() ;
    }

    @Override
    public void toggle() {

    }
}
