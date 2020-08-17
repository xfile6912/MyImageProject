package com.example.test.Dialog;

import com.example.test.Model.Folder;

public interface UpdateDialogListener {
    public int onPositiveClicked(Folder folder);
    public void onNegativeClicked();
}
