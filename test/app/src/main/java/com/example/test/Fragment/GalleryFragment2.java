package com.example.test.Fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.test.Adapter.ImageAdapter;
import com.example.test.DB.FolderDBHelper;
import com.example.test.DB.ImageDB;
import com.example.test.DB.ImageDBHelper;
import com.example.test.Dialog.UpdateDialog;
import com.example.test.Dialog.UpdateDialogListener;
import com.example.test.MainActivity;
import com.example.test.Model.Folder;
import com.example.test.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class GalleryFragment2 extends Fragment implements View.OnClickListener {
    ViewGroup viewGroup;
    GridView gridView;
    TextView nameText;
    FolderDBHelper folderDBHelper;
    ImageDBHelper imageDBHelper;
    ImageAdapter imageAdapter;
    Folder folder;
    GalleryFragment3 galleryFragment3;
    ArrayList images;
    ImageButton editButton;
    ImageButton deleteButton;//TODO:구현하기
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.gallery2_fragment, container, false);
        setViewGroup();
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long id) {
                final CharSequence[] items ={"대표사진으로 설정", "사진 삭제", "취소"};
                final AlertDialog.Builder selectDialog=new AlertDialog.Builder(getContext());
                selectDialog.setTitle("작업 선택");
                selectDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        if(items[id].equals("대표사진으로 설정"))
                        {
                            final AlertDialog.Builder setDialog=new AlertDialog.Builder(getContext());
                            setDialog.setTitle("설정");
                            setDialog.setMessage("대표사진으로 설정하시겠습니까?");
                            setDialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            setDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getActivity(),"설정되었습니다.", Toast.LENGTH_SHORT).show();
                                    String image=(String)adapterView.getAdapter().getItem(position);
                                    folder.setRepImage(image);
                                    folderDBHelper.updateRecord(folder, folder.getName());
                                    images=getImagesByFolder(folder);
                                    imageAdapter.setImages(images);

                                    gridView.setAdapter(imageAdapter);
                                }
                            });
                            setDialog.show();
                        }
                        else if(items[id].equals("사진 삭제"))
                        {
                            final AlertDialog.Builder deleteDialog=new AlertDialog.Builder(getContext());
                            deleteDialog.setTitle("삭제");
                            deleteDialog.setMessage("사진을 삭제하시겠습니까?");
                            deleteDialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            deleteDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getActivity(),"삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    String image=(String)adapterView.getAdapter().getItem(position);
                                    imageDBHelper.deleteRecord(folder, image);
                                    images=getImagesByFolder(folder);
                                    imageAdapter.setImages(images);

                                    gridView.setAdapter(imageAdapter);
                                }
                            });
                            deleteDialog.show();
                        }
                        else if(items[id].equals("취소"))
                        {
                        }

                    }
                });
                selectDialog.show();


                return true;
            }
        });
        return viewGroup;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setViewGroup(){//초기화코드.
        galleryFragment3=new GalleryFragment3();
        gridView= (GridView)viewGroup.findViewById(R.id.gridView);
        nameText=(TextView)viewGroup.findViewById(R.id.nameText);
        nameText.setText(folder.getPlace());
        editButton=(ImageButton)viewGroup.findViewById(R.id.editButton);
        deleteButton=(ImageButton)viewGroup.findViewById(R.id.deleteButton);
        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        folderDBHelper=new FolderDBHelper(getContext());
        folderDBHelper.open();
        folderDBHelper.create();
        imageDBHelper=new ImageDBHelper(getContext());
        imageDBHelper.open();
        images=getImagesByFolder(folder);
        imageAdapter=new ImageAdapter(getActivity(), new ArrayList(), this);
        imageAdapter.setImages(images);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                galleryFragment3.setImages(images, position);
                ((MainActivity)getActivity()).replaceFragmentStack(galleryFragment3);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editButton:
                UpdateDialog updateDialog = new UpdateDialog(getActivity(), folder);
                updateDialog.setUpdateDialogListener(new UpdateDialogListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public int onPositiveClicked(Folder updatedFolder) {
                        if(folderDBHelper.isValidated(updatedFolder)==1) {
                            int check = folderDBHelper.updateRecord(updatedFolder, folder.getName());

                            if (check == 1) {//TODO:폴더이름 중복일 때 오류잡기.
                                imageDBHelper.updateRecord(updatedFolder, folder.getName());
                                folder = updatedFolder;
                                nameText.setText(folder.getPlace());
                                imageAdapter.setImages(images);
                                gridView.setAdapter(imageAdapter);
                            }
                            return 1;
                        }
                        else
                            return 0;
                    }

                    @Override
                    public void onNegativeClicked() {
                    }

                });
                updateDialog.callDialog();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList getImagesByFolder(Folder folder){
        ArrayList images=new ArrayList();
        Cursor cursor=imageDBHelper.findByFolder(folder);
        ContentResolver contentResolver=getContext().getContentResolver();
        while(cursor.moveToNext()){
            String image=cursor.getString(cursor.getColumnIndex(ImageDB.CreateDB.IMAGEURI));
            ParcelFileDescriptor parcelFileDescriptor=null;
            try {
                parcelFileDescriptor=contentResolver.openFileDescriptor(Uri.parse(image),"r");
                images.add(image);
            } catch (FileNotFoundException e) {
                imageDBHelper.deleteRecord(folder,image);
            }finally {
                try {
                    parcelFileDescriptor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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