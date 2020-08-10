package com.example.test.Dialog;

import com.example.test.Model.Folder;

public interface AddDialogListener {
    public long onPositiveClicked(Folder folder);
    public void onNegativeClicked();
}
