package com.example.ecom;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import static android.app.Activity.RESULT_OK;


public class supportFragment extends Fragment {
    Spinner supportSpinner;
    Spinner accountType;
    EditText phoneEditText;
    EditText emailEditText;
    EditText messageEditText;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    FirebaseDatabase mdatabase;
    Button submitButton;
    ImageButton uploadButton;
    View v1;
    Uri pdfUri;
    TextView accountTextView;

    public supportFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v1= inflater.inflate(R.layout.fragment_support, container, false);
        return v1;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        supportSpinner =(Spinner)view.findViewById(R.id.supportSpinner);
        accountType=(Spinner)view.findViewById(R.id.accountType);
        phoneEditText=(EditText)view.findViewById(R.id.phoneEditText);
        emailEditText=(EditText)view.findViewById(R.id.emailEditText);
        messageEditText=(EditText)view.findViewById(R.id.messageEditText);
        submitButton=(Button)view.findViewById(R.id.submitCIButton);
        uploadButton=(ImageButton)view.findViewById(R.id.uploadButton);
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mdatabase=FirebaseDatabase.getInstance();
        accountTextView=(TextView)view.findViewById(R.id.accountTextView);
        supportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                accountTextView.setText(item.toString());
                String s=item.toString();
                switch (s){
                    case "Account":
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.account_type));
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        accountType.setAdapter(adapter1);
                        break;
                    case "Orders":
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.orders));
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        accountType.setAdapter(adapter2);
                        break;
                    case "Payment":
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.payment));
                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        accountType.setAdapter(adapter3);
                        break;
                    case "Shipping":
                        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.shipping));
                        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        accountType.setAdapter(adapter4);
                        break;
                    case "Catalogue":
                        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.catalogue));
                        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        accountType.setAdapter(adapter5);
                        break;

                    default:break;

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Support").child("supportType").setValue(supportSpinner.getSelectedItem().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Support").child("accoutType").setValue(accountType.getSelectedItem().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Support").child("phoneNumber").setValue(phoneEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Support").child("email").setValue(emailEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Support").child("message").setValue(messageEditText.getText().toString());
                if(pdfUri!=null){
                    uploadPdf(pdfUri);
                }else{
                    Toast.makeText(v1.getContext(), "Please select a file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(v1.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                   selectPdf();
                }else{
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });

    }

    private void uploadPdf(Uri pdfUri){
        String fileName=System.currentTimeMillis()+"";
        mStorageRef.child("Uploaded Documents").child(fileName).putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Support").child("documentUrl").setValue(url);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(v1.getContext(), "File wasn't uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                submitButton.setText("SUBMITTED");
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPdf();
        }else{
            Toast.makeText(v1.getContext(), "Please provide permission to access the Internal Storage", Toast.LENGTH_SHORT).show();
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
        if(requestCode==86 && resultCode==RESULT_OK && data!=null){
          pdfUri=data.getData();
            Toast.makeText(v1.getContext(), "File Selected", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(v1.getContext(), "Please select a file", Toast.LENGTH_SHORT).show();
        }

    }
}
