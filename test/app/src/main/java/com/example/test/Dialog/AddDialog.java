package com.example.test.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.test.MainActivity;
import com.example.test.Model.Folder;
import com.example.test.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddDialog implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private EditText nameText, placeText, withText;
    private Button startDateButton, endDateButton;
    private Button addButton, cancelButton;
    private ImageButton addImageButton;
    private LocalDate startDate;
    private LocalDate endDate;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private AddDialogListener addDialogListener;
    public AddDialog(Context context)
    {
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void callDialog()
    {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 바 숨김
        dialog.setContentView(R.layout.add_dialog);
        dialog.show();
        initializeWidget();
        setOnClickListener();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initializeWidget()
    {
        addImageButton=(ImageButton)dialog.findViewById(R.id.addImageButton);
        nameText=(EditText)dialog.findViewById(R.id.nameText);
        placeText=(EditText)dialog.findViewById(R.id.placeText);
        withText=(EditText)dialog.findViewById(R.id.withText);
        startDateButton=(Button)dialog.findViewById(R.id.startDate);
        endDateButton=(Button)dialog.findViewById(R.id.endDate);
        addButton=(Button)dialog.findViewById(R.id.addButton);
        cancelButton=(Button)dialog.findViewById(R.id.cancelButton);
        startDate= LocalDate.now();
        endDate=LocalDate.now();
        startDateButton.setText(startDate.format(DateTimeFormatter.ISO_DATE));
        endDateButton.setText(endDate.format(DateTimeFormatter.ISO_DATE));
    }
    public void setOnClickListener()
    {
        addImageButton.setOnClickListener(this);
        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }
    public void setAddDialogListener(AddDialogListener addDialogListener)
    {
        this.addDialogListener=addDialogListener;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startDate:
                startDateSetListener= new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;//0이 1월을 가리킴.
                        startDate=LocalDate.of(year, month, dayOfMonth);//startDate저장
                        startDateButton.setText(startDate.format(DateTimeFormatter.ISO_DATE));
                        if(endDate.getYear()<startDate.getYear())//종료날짜는 무조건 시작날짜 뒤거나 같아야함.
                        {
                            endDate=LocalDate.of(startDate.getYear(), startDate.getMonthValue(),startDate.getDayOfMonth());
                            endDateButton.setText(endDate.format(DateTimeFormatter.ISO_DATE));
                        }
                        else if(endDate.getYear()==startDate.getYear() && endDate.getMonthValue()<startDate.getMonthValue())
                        {
                            endDate=LocalDate.of(startDate.getYear(), startDate.getMonthValue(),startDate.getDayOfMonth());
                            endDateButton.setText(endDate.format(DateTimeFormatter.ISO_DATE));
                        }
                        else if(endDate.getYear()==startDate.getYear() && endDate.getMonthValue()==startDate.getMonthValue() && endDate.getDayOfMonth()<startDate.getDayOfMonth())
                        {
                            endDate=LocalDate.of(startDate.getYear(), startDate.getMonthValue(),startDate.getDayOfMonth());
                            endDateButton.setText(endDate.format(DateTimeFormatter.ISO_DATE));
                        }
                    }
                };
                DatePickerDialog dateDialog = new DatePickerDialog(context, startDateSetListener, startDate.getYear(), startDate.getMonthValue()-1, startDate.getDayOfMonth());

                dateDialog.show();
                break;
            case R.id.endDate:
                endDateSetListener= new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;//0이 1월을 가리킴.
                        endDate=LocalDate.of(year, month, dayOfMonth);//endDate저장
                        endDateButton.setText(endDate.format(DateTimeFormatter.ISO_DATE));
                        if(endDate.getYear()<startDate.getYear())//시작날짜는 무조건 종료날짜 앞이나 같아야함.
                        {
                            startDate=LocalDate.of(endDate.getYear(), endDate.getMonthValue(),endDate.getDayOfMonth());
                            startDateButton.setText(startDate.format(DateTimeFormatter.ISO_DATE));
                        }
                        else if(endDate.getYear()==startDate.getYear() && endDate.getMonthValue()<startDate.getMonthValue())
                        {
                            startDate=LocalDate.of(endDate.getYear(), endDate.getMonthValue(),endDate.getDayOfMonth());
                            startDateButton.setText(startDate.format(DateTimeFormatter.ISO_DATE));
                        }
                        else if(endDate.getYear()==startDate.getYear() && endDate.getMonthValue()==startDate.getMonthValue() && endDate.getDayOfMonth()<startDate.getDayOfMonth())
                        {
                            startDate=LocalDate.of(endDate.getYear(), endDate.getMonthValue(),endDate.getDayOfMonth());
                            startDateButton.setText(startDate.format(DateTimeFormatter.ISO_DATE));
                        }
                    }
                };
                dateDialog = new DatePickerDialog(context, endDateSetListener, endDate.getYear(), endDate.getMonthValue()-1, endDate.getDayOfMonth());

                dateDialog.show();
                break;
            case R.id.cancelButton:
                Toast.makeText(context, "사진 추가를 취소했습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
            case R.id.addButton:
                Folder folder= Folder.builder()
                        .name(nameText.getText().toString())
                        .place(placeText.getText().toString())
                        .startDate(startDate)
                        .endDate(endDate)
                        .withDescription(withText.getText().toString())
                        .build();
                long check=addDialogListener.onPositiveClicked(folder);
                if(check!=-1) {
                    Toast.makeText(context, "사진첩이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(context, "중복되는 폴더이름이 있습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.addImageButton:
                getPicturesFromAlbum();
                break;

        }
    }

    private void getPicturesFromAlbum() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((MainActivity)context).startActivityForResult(intent, 1);
    }
}
//com.example.test.MainActivity@af4314a
