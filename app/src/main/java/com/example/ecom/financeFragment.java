package com.example.ecom;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class financeFragment extends Fragment {
    View vFinance;
    EditText fromDateEditText;
    EditText toDateEditText;
    String currentDate;
    RadioGroup radioGroup;
    Button revenueButton;

    public financeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vFinance= inflater.inflate(R.layout.fragment_finance, container, false);
        return vFinance;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fromDateEditText=(EditText)view.findViewById(R.id.fromDateEditText);
        toDateEditText=(EditText)view.findViewById(R.id.toDateEditText);
        radioGroup = (RadioGroup)view.findViewById(R.id.dateCategory);
        revenueButton=(Button)view.findViewById(R.id.revenueButton);
        ActivityCompat.requestPermissions(getActivity(),new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        createPDF();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId){
                RadioButton rb=(RadioButton)vFinance.findViewById(checkedId);
                if(rb.getText().equals("Monthly")){
                    currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    toDateEditText.setText(currentDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, -1);
                    Date date = calendar.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String dateOutput = format.format(date);
                    fromDateEditText.setText(dateOutput);

                }
                else if(rb.getText().equals("Yearly")){
                    currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    toDateEditText.setText(currentDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.YEAR, -1);
                    Date date = calendar.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String dateOutput = format.format(date);
                    fromDateEditText.setText(dateOutput);

                }
                else if(rb.getText().equals("Weekly")){
                    currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    toDateEditText.setText(currentDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -7);
                    Date date = calendar.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String dateOutput = format.format(date);
                    fromDateEditText.setText(dateOutput);

                }
                else if(rb.getText().equals("Custom")){
                    toDateEditText.getText().clear();
                    fromDateEditText.getText().clear();

                }
            }
        });

    }

    private void createPDF() {
        revenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PdfDocument myPdfDocument=new PdfDocument();
                Paint myPaint= new Paint();
                Paint titlePaint=new Paint();
                PdfDocument.PageInfo myPageInfo1=new PdfDocument.PageInfo.Builder(250,400,1).create();
                PdfDocument.Page myPage1=myPdfDocument.startPage(myPageInfo1);
                Canvas canvas=myPage1.getCanvas();
                myPdfDocument.finishPage(myPage1);

               titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
                titlePaint.setTextSize(20);
                //canvas.drawText("REVENUE STATEMENT",60,30,titlePaint);

                File file=new File(Environment.getExternalStorageDirectory(),"/RevenuePDF.pdf");
                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(vFinance.getContext(), "Invoice has been successfully downloaded", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myPdfDocument.close();

            }
        });

    }


}
