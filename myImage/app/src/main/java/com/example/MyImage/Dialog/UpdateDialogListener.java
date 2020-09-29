package com.example.MyImage.Dialog;

import com.example.MyImage.Model.Folder;

public interface UpdateDialogListener {
    public int onPositiveClicked(Folder folder);
    public void onNegativeClicked();
}
