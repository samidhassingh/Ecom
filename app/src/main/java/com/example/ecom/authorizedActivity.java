package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class authorizedActivity extends AppCompatActivity {
    EditText nameAuthEditText;
    EditText phoneAuthEditText;
    EditText emailEditText;
    EditText addressAuthEditText;
    EditText addressAuthEditText2;
    EditText pinCodeEditText;
    Button submitAuthButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);
        nameAuthEditText=(EditText)findViewById(R.id.nameAuthEditText);
        phoneAuthEditText=(EditText)findViewById(R.id.phoneAuthEditText);
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        addressAuthEditText=(EditText)findViewById(R.id.addressAuthEditText);
        addressAuthEditText2=(EditText)findViewById(R.id.addressAuthEditText2);
        pinCodeEditText=(EditText)findViewById(R.id.pinCodeEditText);
        submitAuthButton=(Button)findViewById(R.id.submitAuthButton);

        submitAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),verifyAuthorizedUserSettings.class);
                intent.putExtra("userPhone", phoneAuthEditText.getText().toString());
                intent.putExtra("userName", nameAuthEditText.getText().toString());
                intent.putExtra("userEmail", emailEditText.getText().toString());
                intent.putExtra("userStreetAddress", addressAuthEditText.getText().toString());
                intent.putExtra("userCityAddress", addressAuthEditText2.getText().toString());
                intent.putExtra("userPinCode", pinCodeEditText.getText().toString());
                startActivity(intent);
            }
        });
            submitAuthButton.setText("SUBMITTED");


    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().beginTransaction().replace(R.id.content,new settingsFragment()).addToBackStack(null).commit();

    }*/
}
