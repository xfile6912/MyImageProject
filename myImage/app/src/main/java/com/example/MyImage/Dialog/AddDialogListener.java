package com.example.MyImage.Dialog;

import com.example.MyImage.Model.Folder;

public interface AddDialogListener {
    public long onPositiveClicked(Folder folder);
    public void onNegativeClicked();
}
