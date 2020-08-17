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

public class UpdateDialog implements View.OnClickListener {
    private Folder folder;
    private Context context;
    private Dialog dialog;
    private EditText nameText, placeText, withText;
    private Button startDateButton, endDateButton;
    private Button editButton, cancelButton;
    private ImageButton editImageButton;
    private LocalDate startDate;
    private LocalDate endDate;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;
    private UpdateDialogListener updateDialogListener;
    public UpdateDialog(Context context, Folder folder)
    {
        this.context=context;
        this.folder=folder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void callDialog()
    {
        dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//타이틀 바 숨김
        dialog.setContentView(R.layout.update_dialog);
        dialog.show();
        initializeWidget();
        setOnClickListener();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initializeWidget()
    {
        editImageButton=(ImageButton)dialog.findViewById(R.id.editImageButton);
        nameText=(EditText)dialog.findViewById(R.id.nameText);
        placeText=(EditText)dialog.findViewById(R.id.placeText);
        withText=(EditText)dialog.findViewById(R.id.withText);
        startDateButton=(Button)dialog.findViewById(R.id.startDate);
        endDateButton=(Button)dialog.findViewById(R.id.endDate);
        editButton=(Button)dialog.findViewById(R.id.editButton);
        cancelButton=(Button)dialog.findViewById(R.id.cancelButton);
        startDate= folder.getStartDate();
        endDate=folder.getEndDate();

        nameText.setText(folder.getName());
        placeText.setText(folder.getPlace());
        withText.setText(folder.getWithDescription());
        startDateButton.setText(startDate.format(DateTimeFormatter.ISO_DATE));
        endDateButton.setText(endDate.format(DateTimeFormatter.ISO_DATE));
    }
    public void setOnClickListener()
    {
        editImageButton.setOnClickListener(this);
        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }
    public void setUpdateDialogListener(UpdateDialogListener updateDialogListener)
    {
        this.updateDialogListener=updateDialogListener;
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
                Toast.makeText(context, "사진 수정를 취소했습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
            case R.id.editButton:
                if(nameText.getText().toString()=="")
                {
                    Toast.makeText(context, "폴더이름은 빈칸 일 수 없습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }
                Folder folder= Folder.builder()
                        .name(nameText.getText().toString())
                        .place(placeText.getText().toString())
                        .startDate(startDate)
                        .endDate(endDate)
                        .withDescription(withText.getText().toString())
                        .build();
                int check=updateDialogListener.onPositiveClicked(folder);
                if(check==1) {
                    Toast.makeText(context, "사진첩이 수정되었습니다.", Toast.LENGTH_SHORT).show();
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
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((MainActivity)context).startActivityForResult(Intent.createChooser(intent, "Get Album"), 1);
    }
}
//com.example.test.MainActivity@af4314a
