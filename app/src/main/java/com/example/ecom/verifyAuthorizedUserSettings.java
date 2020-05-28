package com.example.ecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class verifyAuthorizedUserSettings extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText otpEditText;
    EditText otpEditText1;
    EditText otpEditText2;
    EditText otpEditText3;
    EditText otpEditText4;
    EditText otpEditText5;
    Button verifyButton;
    ProgressBar progressBar1;
    String verificationCodeBySystem;
    TextView textView15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_authorized_user_settings);
        mAuth = FirebaseAuth.getInstance();
        otpEditText=(EditText)findViewById(R.id.otpEditText);
        otpEditText1=(EditText)findViewById(R.id.otpEditText1);
        otpEditText2=(EditText)findViewById(R.id.otpEditText2);
        otpEditText3=(EditText)findViewById(R.id.otpEditText3);
        otpEditText4=(EditText)findViewById(R.id.otpEditText4);
        otpEditText5=(EditText)findViewById(R.id.otpEditText5);
        verifyButton=(Button)findViewById(R.id.verifyButton);
        progressBar1=(ProgressBar)findViewById(R.id.progressBar1);
        //editText2=(EditText)findViewById(R.id.editText2);
        textView15=(TextView)findViewById(R.id.textView15);
        progressBar1.setVisibility(View.GONE);
        String userPhone=getIntent().getStringExtra("userPhone");
        String userName=getIntent().getStringExtra("userName");
        String userEmail=getIntent().getStringExtra("userEmail");
        String userStreetAddress=getIntent().getStringExtra("userStreetAddress");
        String userCityAddress=getIntent().getStringExtra("userCityAddress");
        String userPinCode=getIntent().getStringExtra("userPinCode");

        textView15.setText(userPhone);
        sendVerificationCodeToUser(userPhone);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=otpEditText.getText().toString()+otpEditText1.getText().toString()+otpEditText2.getText().toString()+otpEditText3.getText().toString()+otpEditText4.getText().toString()+otpEditText5.getText().toString();
                if(code.isEmpty() || code.length()<6){
                    otpEditText.setError("Wrong OTP");
                    otpEditText.requestFocus();
                    return;

                }else{
                    progressBar1.setVisibility(View.VISIBLE);
                    verifyCode(code);
                }
            }
        });
    }

    private void sendVerificationCodeToUser(String userPhone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + userPhone ,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code= phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressBar1.setVisibility(View.VISIBLE);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(verifyAuthorizedUserSettings.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String codeByUser){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationCodeBySystem,codeByUser);
        signInTheUserByCredentials(credential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential).addOnCompleteListener(verifyAuthorizedUserSettings.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Authorized Person's Information").child("Name")
                            .setValue(getIntent().getStringExtra("userName"));
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Authorized Person's Information").child("Phone")
                            .setValue(getIntent().getStringExtra("userPhone"));
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Authorized Person's Information").child("Email")
                            .setValue(getIntent().getStringExtra("userEmail"));
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Authorized Person's Information").child("Street Address")
                            .setValue(getIntent().getStringExtra("userStreetAddress"));
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Authorized Person's Information").child("userCityAddress")
                            .setValue(getIntent().getStringExtra("userCityAddress"));
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Authorized Person's Information").child("userPinCode")
                            .setValue(getIntent().getStringExtra("userPinCode"));
                    Intent intent = new Intent(getApplicationContext(),authorizedActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(verifyAuthorizedUserSettings.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
