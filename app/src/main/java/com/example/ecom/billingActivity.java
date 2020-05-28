package com.example.ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class billingActivity extends AppCompatActivity {
   Spinner billingYesNo;
   Button submitIndiBillingButton;
   Button submitBillingButton;
   EditText gstNumberEditText;
   EditText gstAddressEditText;
   EditText billingNameEditText;
   EditText billingAddressEditText;
    EditText billingAddressEditText2;
    EditText pinBillCodeEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        String buttonName=getIntent().getStringExtra("buttonName");
        billingYesNo=(Spinner)findViewById(R.id.billingYesNo);
        submitIndiBillingButton=(Button)findViewById(R.id.submitIndiBillingButton);
        submitBillingButton=(Button)findViewById(R.id.submitBillingButton);
        gstAddressEditText=(EditText)findViewById(R.id.gstAddressEditText);
        gstNumberEditText=(EditText)findViewById(R.id.gstNumberEditText);
        billingNameEditText=(EditText)findViewById(R.id.billingNameEditText);
        billingAddressEditText=(EditText)findViewById(R.id.billAddressEditText);
        billingAddressEditText2=(EditText)findViewById(R.id.billAddressEditText2);
        pinBillCodeEditText=(EditText)findViewById(R.id.pinBillCodeEditText);
        mAuth = FirebaseAuth.getInstance();
        if(buttonName.equals("INDIVIDUAL INFORMATION")){
            submitIndiBillingButton.setVisibility(View.VISIBLE);
            billingYesNo.setVisibility(View.GONE);
            submitBillingButton.setVisibility(View.GONE);
            gstAddressEditText.setVisibility(View.GONE);
            gstNumberEditText.setVisibility(View.GONE);
            submitIndiBillingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/INDIVIDUAL").child("Billing NameL")
                            .setValue(billingNameEditText.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/INDIVIDUAL").child("Billing Address Street ")
                            .setValue(billingAddressEditText.getText().toString());


                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/INDIVIDUAL").child("Billing Address City Country")
                            .setValue(billingAddressEditText2.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/INDIVIDUAL").child("Billing Pin Code")
                            .setValue(pinBillCodeEditText.getText().toString());
                    submitIndiBillingButton.setText("SUBMITTED");
                }
            });

        }else{

            billingYesNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Object item = parent.getItemAtPosition(position);
                    if(item.toString().equals("NO")){
                        gstNumberEditText.setHint("Not Mandatory");
                        gstAddressEditText.setHint("Not Mandatory");
                    }
                    else if(item.toString().equals("YES")){
                        gstNumberEditText.setHint("GST Number");
                        gstAddressEditText.setHint("GST Address");
                    }

                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            submitBillingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/COMPANY").child("Billing NAME")
                            .setValue(billingNameEditText.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/COMPANY").child("Billing Address Street ")
                            .setValue(billingAddressEditText.getText().toString());


                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/COMPANY").child("Billing Address City Country")
                            .setValue(billingAddressEditText2.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/COMPANY").child("Billing Pin Code")
                            .setValue(pinBillCodeEditText.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/COMPANY").child("Billing GST NUMBER")
                            .setValue(gstNumberEditText.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser()
                            .getUid()).child("Settings").child("Billing Information/COMPANY").child("Billing GST Address")
                            .setValue(gstAddressEditText.getText().toString());

                    submitBillingButton.setText("SUBMITTED");

                }
            });

        }


    }
}
