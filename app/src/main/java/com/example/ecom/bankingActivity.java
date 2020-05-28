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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import org.w3c.dom.Text;

public class bankingActivity extends AppCompatActivity {
    TextView currentAccount;
    EditText accountNameEditText;
    EditText accountNumberEditText;
    EditText ifsEditText;
    ImageButton uploadChequeImageButton;
    private FirebaseAuth mAuth;
    Button submitCIButton;
    private StorageReference mStorageRef;
    Uri pdfUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banking);
        currentAccount=(TextView)findViewById(R.id.currentAccount);
        accountNameEditText=(EditText)findViewById(R.id.accountNameEditText);
        accountNumberEditText=(EditText)findViewById(R.id.accountNumberEditText);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        ifsEditText=(EditText)findViewById(R.id.ifsEditText);
        uploadChequeImageButton=(ImageButton)findViewById(R.id.uploadChequeImageButton);
        submitCIButton=(Button)findViewById(R.id.submitCIButton);
        String buttonName=getIntent().getStringExtra("buttonName");
        if(buttonName.equals("INDIVIDUAL INFORMATION")){
          currentAccount.setText("SAVINGS ACCOUNT");
        }else{
            currentAccount.setText("CURRENT ACCOUNT");
        }
        uploadChequeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }else{
                    ActivityCompat.requestPermissions(bankingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });
        submitCIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("Banking Information").child("Account Name")
                        .setValue(accountNameEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("Banking Information").child("Account Number")
                        .setValue(accountNumberEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                        .getUid()).child("Settings").child("Banking Information").child("IFS Code")
                        .setValue(ifsEditText.getText().toString());
                if(pdfUri!=null){
                    uploadPdf(pdfUri);
                }else{
                    Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private void uploadPdf(Uri pdfUri){
        String fileName=System.currentTimeMillis()+"";
        mStorageRef.child("Uploaded Documents").child("BillingInfo").child("cheque").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                .getUid()).child("Settings").child("Banking Information").child("cheque URL")
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
                submitCIButton.setText("SUBMITTED");
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }
        else{
            Toast.makeText(getApplicationContext(), "Please provide permission to access the Internal Storage", Toast.LENGTH_SHORT).show();
        }
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
        else {
            Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
        }

    }

}
