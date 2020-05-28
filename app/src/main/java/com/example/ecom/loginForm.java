package com.example.ecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginForm extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    EditText loginPhoneEditText;
    Button verifyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        mAuth=FirebaseAuth.getInstance();
        loginPhoneEditText=(EditText)findViewById(R.id.loginPhoneEditText);
        verifyButton=(Button)findViewById(R.id.verifyButton);
        myRef = FirebaseDatabase.getInstance().getReference("users");
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.orderByChild("userPhone").equalTo(loginPhoneEditText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            Intent intent = new Intent(getApplicationContext(),verifyLoginUser.class);
                            intent.putExtra("userPhone", loginPhoneEditText.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(loginForm.this,"User hasn't Signed Up, Proceed to Sign Up please!", Toast.LENGTH_LONG).show();

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
