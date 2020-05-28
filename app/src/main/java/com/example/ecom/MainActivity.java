package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static int splash_timeout=2500;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                if(mAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                }
                else{
                    startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                }
            }
        },splash_timeout);
    }
}
