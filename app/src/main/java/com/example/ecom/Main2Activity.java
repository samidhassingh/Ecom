package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
     Button kycButton3;
     Button loginButton1;
     Button signupButton2;
     private FirebaseAuth mAuth;
     Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
       /* if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), dashboard.class));
        }*/
        ImageSlider imageSlider=findViewById(R.id.image_slider);
        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.one));
        slideModels.add(new SlideModel(R.drawable.two));
        slideModels.add(new SlideModel(R.drawable.three));
        imageSlider.setImageList(slideModels,true);
        myDialog=new Dialog(this);
        loginButton1=(Button)findViewById(R.id.loginButton1);
        kycButton3=(Button) findViewById(R.id.kycButton3);
        signupButton2=(Button)findViewById(R.id.signupButton2);
        kycButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton img;
                myDialog.setContentView(R.layout.custompopup);
                img=(ImageButton)myDialog.findViewById(R.id.imageButton);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
            }
        });

        signupButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),signUpForm.class));

            }
        });
        loginButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),loginForm.class));
            }
        });


    }
}
