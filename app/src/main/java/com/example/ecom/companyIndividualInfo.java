package com.example.ecom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class companyIndividualInfo extends AppCompatActivity {
    EditText compNameEditText;
    private StorageReference mStorageRef;
    TextView header;
    TextView panTextView;
    Spinner typeOfAccount;
    Spinner certificate;
    ImageButton uploadCertificateImageButton;
    EditText compAddressEditText;
    EditText compAddressEditText2;
    EditText pinCodeEditText;
    EditText fixedNumberEditText;
    EditText mobileNumberEditText;
    EditText emailEditText;
    EditText panNumberEditText;
    ImageButton uploadPanImageButton;
    Uri pdfUri;
    Uri pdfCertificate;
    private FirebaseAuth mAuth;
    Button submitCIButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_individual_info);
        String buttonName=getIntent().getStringExtra("buttonName");
        compNameEditText=(EditText)findViewById(R.id.compNameEditText);
        panTextView=(TextView)findViewById(R.id.panTextView);
        typeOfAccount=(Spinner)findViewById(R.id.typeOfAccount);
        certificate=(Spinner)findViewById(R.id.certificate);
        mAuth = FirebaseAuth.getInstance();
        submitCIButton=(Button)findViewById(R.id.submitCIButton);
        uploadCertificateImageButton=(ImageButton)findViewById(R.id.uploadCertificateImageButton);
        header=(TextView)findViewById(R.id.header);
        compAddressEditText=(EditText)findViewById(R.id.compAddressEditText);
        compAddressEditText2=(EditText)findViewById(R.id.compAddressEditText2);
        pinCodeEditText=(EditText)findViewById(R.id.pinCodeEditText);
        fixedNumberEditText=(EditText)findViewById(R.id.fixedNumberEditText);
        mobileNumberEditText=(EditText)findViewById(R.id.mobileNumberEditText);
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        panNumberEditText=(EditText)findViewById(R.id.panNumberEditText);
        uploadPanImageButton=(ImageButton)findViewById(R.id.uploadPanImageButton);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        if(buttonName.equals("INDIVIDUAL INFORMATION")){
            header.setText("INDIVIDUAL INFORMATION");
            compNameEditText.setHint("Individual Name");
            panTextView.setText("Personal PAN Card");
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.typeofacc));
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeOfAccount.setAdapter(adapter1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.typeofcert));
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            certificate.setAdapter(adapter2);
        }

        uploadPanImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPdfCertificate();
                }else{
                    ActivityCompat.requestPermissions(companyIndividualInfo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},8);
                }
            }
        });
        uploadCertificateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }else{
                    ActivityCompat.requestPermissions(companyIndividualInfo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        submitCIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("IndividualCompany Information").child("Name").setValue(compNameEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("IndividualCompany Information").child("Street Address").setValue(compAddressEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("IndividualCompany Information").child("City/country").setValue(compAddressEditText2.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("IndividualCompany Information").child("PIN Code").setValue(pinCodeEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("IndividualCompany Information").child("Fixed Number")
                        .setValue(fixedNumberEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("IndividualCompany Information").child("Mobile Number")
                        .setValue(mobileNumberEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("IndividualCompany Information").child("Email ID")
                        .setValue(emailEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("IndividualCompany Information").child("PAN Nimber")
                        .setValue(panNumberEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("IndividualCompany Information").child("Type of Account")
                        .setValue(typeOfAccount.getSelectedItem().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("IndividualCompany Information").child("Document Type")
                        .setValue(certificate.getSelectedItem().toString());

                if(pdfUri!=null){
                    uploadPdf(pdfUri);
                }else{
                    Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
                }

                if(pdfCertificate!=null){
                    uploadCertificatePdf(pdfCertificate);
                }

            }
        });

    }

    private void uploadCertificatePdf(Uri pdfCertificate){
        String fileName=System.currentTimeMillis()+"";
        mStorageRef.child("Uploaded Documents").child("IndividualCompany").child("certificate").child(fileName).putFile(pdfCertificate)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                .getUid()).child("Settings").child("IndividualCompany Information").child("certificate URL")
                                .setValue(url);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "File wasn't uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void uploadPdf(Uri pdfUri){
        String fileName=System.currentTimeMillis()+"";
        mStorageRef.child("Uploaded Documents").child("IndividualCompany").child("PAN").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                .getUid()).child("Settings").child("IndividualCompany Information").child("PAN URL")
                                .setValue(url);


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "File wasn't uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }
        else if(requestCode==8 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdfCertificate();
        }else{
            Toast.makeText(getApplicationContext(), "Please provide permission to access the Internal Storage", Toast.LENGTH_SHORT).show();
        }
    }
    private void selectPdfCertificate(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,85);
    }
    private void selectPdf(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            Toast.makeText(getApplicationContext(), "File Selected", Toast.LENGTH_SHORT).show();

        }
       else if (requestCode == 85 && resultCode == RESULT_OK && data != null) {
            pdfCertificate = data.getData();
            Toast.makeText(getApplicationContext(), "File Selected", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
        }

    }
}
