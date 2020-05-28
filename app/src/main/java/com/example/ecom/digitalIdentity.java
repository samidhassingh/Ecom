package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class digitalIdentity extends AppCompatActivity {

    EditText fbEditText;
    EditText instaEditText;
    EditText whatsappEditText;
    EditText youtubeEditText;
    EditText twitterEditText;
    Button digitalSubmitButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_identity);
        mAuth = FirebaseAuth.getInstance();
        fbEditText=(EditText)findViewById(R.id.fbEditText);
        instaEditText=(EditText)findViewById(R.id.instaEditText);
        whatsappEditText=(EditText)findViewById(R.id.whatsappEditText);
        youtubeEditText=(EditText)findViewById(R.id.youtubeEditText);
        twitterEditText=(EditText)findViewById(R.id.twitterEditText);
        digitalSubmitButton=(Button)findViewById(R.id.digitalSubmitButton);
        digitalSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("Digital Identity").child("Facebook").setValue(fbEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("Digital Identity").child("Instagram").setValue(instaEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("Digital Identity").child("whatsApp").setValue(whatsappEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("Digital Identity").child("YouTube").setValue(youtubeEditText.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("Settings").child("Digital Identity").child("Twitter").setValue(twitterEditText.getText().toString());
                digitalSubmitButton.setText("submitted");
            }
        });

    }
}
