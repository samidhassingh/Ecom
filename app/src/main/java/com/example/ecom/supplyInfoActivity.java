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
import android.view.View;
import android.widget.AdapterView;
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

import org.w3c.dom.Text;

public class supplyInfoActivity extends AppCompatActivity {

    Spinner sellOnlineOffline;
    TextView header1;
    TextView header2;
    TextView header4;
    EditText offlineAddressEditText;
    TextView offlineAddressProof;
    ImageButton offlineUploadChequeImageButton;
    EditText pickUpEditText;
    EditText returnEditText;
    TextView addressProof;
    ImageButton uploadChequeImageButton;
    TextView header3;
    EditText bothlineAddressEditText;
    TextView bothlineAddressProof;
    ImageButton bothlineUploadChequeImageButton;
    Button submitOfflineSupplyButton;
    Button submitOnlineSupplyButton;
    Button submitBothlineSupplyButton;
    EditText onlinePickZipEditText;
    EditText onlineReturnZipEditText;
    EditText offlineZipEditText;
    EditText bothlineZipEditText;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    Uri pdfUriOff;
    Uri pdfUriOn;
    Uri pdfUriBoth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_info);
        sellOnlineOffline=(Spinner)findViewById(R.id.sellOnlineOffline);
        header1=(TextView)findViewById(R.id.header1);
        header2=(TextView)findViewById(R.id.header2);
        header4=(TextView)findViewById(R.id.header4);
        offlineAddressEditText=(EditText)findViewById(R.id.offlineAddressEditText);
        offlineAddressProof=(TextView)findViewById(R.id.offlineAddressProof);
        offlineUploadChequeImageButton=(ImageButton)findViewById(R.id.offlineUploadChequeImageButton);
        pickUpEditText=(EditText)findViewById(R.id.pickUpEditText);
        returnEditText=(EditText)findViewById(R.id.returnEditText);
        addressProof=(TextView)findViewById(R.id.addressProof);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        uploadChequeImageButton=(ImageButton)findViewById(R.id.uploadChequeImageButton);
        header3=(TextView)findViewById(R.id.header3);
        bothlineAddressEditText=(EditText)findViewById(R.id.bothlineAddressEditText);
        bothlineAddressProof=(TextView)findViewById(R.id.bothlineAddressProof);
        bothlineUploadChequeImageButton=(ImageButton)findViewById(R.id.bothlineUploadChequeImageButton);
        submitBothlineSupplyButton=(Button)findViewById(R.id.submitBothSupplyButton);
        submitOfflineSupplyButton=(Button)findViewById(R.id.submitOfflineSupplyButton);
        submitOnlineSupplyButton=(Button)findViewById(R.id.submitOnlineSupplyButton);
        onlinePickZipEditText=(EditText)findViewById(R.id.onlinePickZipEditText);
        onlineReturnZipEditText=(EditText)findViewById(R.id.onlineReturnZipEditText);
        offlineZipEditText=(EditText)findViewById(R.id.offlineZipEditText);
        bothlineZipEditText=(EditText)findViewById(R.id.bothlineZipEditText);
        sellOnlineOffline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(item.toString().equals("Sell Offline")){
                    header2.setVisibility(View.GONE);
                    header4.setVisibility(View.VISIBLE);
                    offlineAddressEditText.setVisibility(View.VISIBLE);
                    offlineAddressProof.setVisibility(View.VISIBLE);
                    offlineUploadChequeImageButton.setVisibility(View.VISIBLE);
                    pickUpEditText.setVisibility(View.GONE);
                    returnEditText.setVisibility(View.GONE);
                    addressProof.setVisibility(View.GONE);
                    uploadChequeImageButton.setVisibility(View.GONE);
                    header3.setVisibility(View.GONE);
                    bothlineAddressEditText.setVisibility(View.GONE);
                    bothlineAddressProof.setVisibility(View.GONE);
                    bothlineUploadChequeImageButton.setVisibility(View.GONE);
                    submitOnlineSupplyButton.setVisibility(View.GONE);
                    submitBothlineSupplyButton.setVisibility(View.GONE);
                    submitOfflineSupplyButton.setVisibility(View.VISIBLE);
                    offlineZipEditText.setVisibility(View.VISIBLE);
                    onlinePickZipEditText.setVisibility(View.GONE);
                    bothlineZipEditText.setVisibility(View.GONE);
                    onlineReturnZipEditText.setVisibility(View.GONE);
                    offlineUploadChequeImageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                                selectPdfOff();
                            }else{
                                ActivityCompat.requestPermissions(supplyInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                            }
                        }
                    });

                    submitOfflineSupplyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Offline").child("Address")
                                    .setValue(offlineAddressEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Offline").child("PIN Code")
                                    .setValue(offlineZipEditText.getText().toString());
                            if(pdfUriOff!=null){
                               uploadPdfOff(pdfUriOff);
                            }else{
                                Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
                else if(item.toString().equals("Sell Bothline")){
                    header2.setVisibility(View.VISIBLE);
                    header4.setVisibility(View.GONE);
                    offlineAddressEditText.setVisibility(View.GONE);
                    offlineAddressProof.setVisibility(View.GONE);
                    offlineUploadChequeImageButton.setVisibility(View.GONE);
                    pickUpEditText.setVisibility(View.VISIBLE);
                    returnEditText.setVisibility(View.VISIBLE);
                    addressProof.setVisibility(View.VISIBLE);
                    uploadChequeImageButton.setVisibility(View.VISIBLE);
                    header3.setVisibility(View.VISIBLE);
                    bothlineAddressEditText.setVisibility(View.VISIBLE);
                    bothlineAddressProof.setVisibility(View.VISIBLE);
                    bothlineUploadChequeImageButton.setVisibility(View.VISIBLE);
                    submitOnlineSupplyButton.setVisibility(View.GONE);
                    submitBothlineSupplyButton.setVisibility(View.VISIBLE);
                    submitOfflineSupplyButton.setVisibility(View.GONE);
                    offlineZipEditText.setVisibility(View.GONE);
                    onlinePickZipEditText.setVisibility(View.VISIBLE);
                    bothlineZipEditText.setVisibility(View.VISIBLE);
                    onlineReturnZipEditText.setVisibility(View.VISIBLE);
                    uploadChequeImageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                                selectPdfOn();
                            }else{
                                ActivityCompat.requestPermissions(supplyInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},8);
                            }
                        }
                    });
                    bothlineUploadChequeImageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                                selectPdfBoth();
                            }else{
                                ActivityCompat.requestPermissions(supplyInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},7);
                            }
                        }
                    });
                    submitBothlineSupplyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Online").child("Pick Up Address")
                                    .setValue(pickUpEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Online").child("PIN Pickup Code")
                                    .setValue(onlinePickZipEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Online").child("Return Address")
                                    .setValue(returnEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Online").child("PIN Return Code")
                                    .setValue(onlineReturnZipEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Bothline/Offline").child("Address")
                                    .setValue(bothlineAddressEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Bothline/Offline").child("PIN Code")
                                    .setValue(bothlineZipEditText.getText().toString());
                            if(pdfUriOn!=null && pdfUriBoth!=null){
                                uploadPdfOn(pdfUriOn);
                                uploadPdfBoth(pdfUriBoth);
                            }else{
                                Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                else if(item.toString().equals("Sell Online")){
                    header2.setVisibility(View.VISIBLE);
                    header4.setVisibility(View.GONE);
                    offlineAddressEditText.setVisibility(View.GONE);
                    offlineAddressProof.setVisibility(View.GONE);
                    offlineUploadChequeImageButton.setVisibility(View.GONE);
                    pickUpEditText.setVisibility(View.VISIBLE);
                    returnEditText.setVisibility(View.VISIBLE);
                    addressProof.setVisibility(View.VISIBLE);
                    uploadChequeImageButton.setVisibility(View.VISIBLE);
                    header3.setVisibility(View.GONE);
                    bothlineAddressEditText.setVisibility(View.GONE);
                    bothlineAddressProof.setVisibility(View.GONE);
                    bothlineUploadChequeImageButton.setVisibility(View.GONE);
                    submitOnlineSupplyButton.setVisibility(View.VISIBLE);
                    submitBothlineSupplyButton.setVisibility(View.GONE);
                    submitOfflineSupplyButton.setVisibility(View.GONE);
                    onlinePickZipEditText.setVisibility(View.VISIBLE);
                    offlineZipEditText.setVisibility(View.GONE);
                    bothlineZipEditText.setVisibility(View.GONE);
                    onlineReturnZipEditText.setVisibility(View.VISIBLE);
                    uploadChequeImageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                                selectPdfOn();
                            }else{
                                ActivityCompat.requestPermissions(supplyInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},8);
                            }
                        }
                    });

                    submitOnlineSupplyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Online").child("Pick Up Address")
                                    .setValue(pickUpEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Online").child("PIN Pickup Code")
                                    .setValue(onlinePickZipEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Online").child("Return Address")
                                    .setValue(returnEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                    .getUid()).child("Settings").child("Supply Information/Online").child("PIN Return Code")
                                    .setValue(onlineReturnZipEditText.getText().toString());
                            if(pdfUriOn!=null){
                                uploadPdfOn(pdfUriOn);
                            }else{
                                Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void uploadPdfOff(Uri pdfUriOff){
        String fileName=System.currentTimeMillis()+"";
        mStorageRef.child("Uploaded Documents").child("Supply Information").child("Offline").child(fileName).putFile(pdfUriOff)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                .getUid()).child("Settings").child("Supply Information/Offline").child("Offline URL")
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
                submitOfflineSupplyButton.setText("SUBMITTED");
            }
        });

    }
    private void uploadPdfBoth(Uri pdfUriBoth){
        String fileName=System.currentTimeMillis()+"";
        mStorageRef.child("Uploaded Documents").child("Supply Information").child("Bothline/Offline").child(fileName).putFile(pdfUriBoth)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                .getUid()).child("Settings").child("Supply Information/Bothline/Offline").child("Offline URL")
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
                submitBothlineSupplyButton.setText("SUBMITTED");
            }
        });

    }

    private void uploadPdfOn(Uri pdfUriOn){
        String fileName=System.currentTimeMillis()+"";
        mStorageRef.child("Uploaded Documents").child("Supply Information").child("Online").child(fileName).putFile(pdfUriOn)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                                .getUid()).child("Settings").child("Supply Information/Online").child("Online URL")
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
                submitOnlineSupplyButton.setText("SUBMITTED");
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdfOff();
        }
        else if(requestCode==8 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdfOn();
        }
        else if(requestCode==7 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdfBoth();
        }
       else{
            Toast.makeText(getApplicationContext(), "Please provide permission to access the Internal Storage", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdfOn(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,85);
    }

    private void selectPdfOff(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
    }
    private void selectPdfBoth(){
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,87);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUriOff = data.getData();
            Toast.makeText(getApplicationContext(), "File Selected", Toast.LENGTH_SHORT).show();

        }
        else if(requestCode == 85 && resultCode == RESULT_OK && data != null){
            pdfUriOn = data.getData();
            Toast.makeText(getApplicationContext(), "File Selected", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == 87 && resultCode == RESULT_OK && data != null){
            pdfUriBoth = data.getData();
            Toast.makeText(getApplicationContext(), "File Selected", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
        }

    }




}
