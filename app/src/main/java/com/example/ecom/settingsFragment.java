package com.example.ecom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class settingsFragment extends Fragment {
    View v4;
    Button individualButton;
    Button companyButton;
    Button companyInfoButton;
    Button billingButton;
    Button bankingButton;
    Button authorizedButton;
    Button supplyButton;
    Button digitalButton;
    public settingsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v4= inflater.inflate(R.layout.fragment_settings, container, false);
        return v4;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        individualButton=(Button)view.findViewById(R.id.individualButton);
        companyButton=(Button)view.findViewById(R.id.companyButton);
        companyInfoButton=(Button)view.findViewById(R.id.companyInfoButton);
        billingButton=(Button)view.findViewById(R.id.billingButton);
        bankingButton=(Button)view.findViewById(R.id.bankingButton);
        authorizedButton=(Button)view.findViewById(R.id.authorizedButton);
        supplyButton=(Button)view.findViewById(R.id.supplyButton);
        digitalButton=(Button)view.findViewById(R.id.digitalButton);
        companyInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),companyIndividualInfo.class);
                intent.putExtra("buttonName",companyInfoButton.getText().toString());
                startActivity(intent);
            }
        });
        billingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),billingActivity.class);
                intent.putExtra("buttonName",companyInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        bankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),bankingActivity.class);
                intent.putExtra("buttonName",companyInfoButton.getText().toString());
                startActivity(intent);
            }
        });
        authorizedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),authorizedActivity.class);
                intent.putExtra("buttonName",companyInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        supplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),supplyInfoActivity.class);
                intent.putExtra("buttonName",companyInfoButton.getText().toString());
                startActivity(intent);
            }
        });
        digitalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),digitalIdentity.class);
                intent.putExtra("buttonName",companyInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        companyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyInfoButton.setText("COMPANY INFORMATION");
            }
        });
        individualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyInfoButton.setText("INDIVIDUAL INFORMATION");
            }
        });

    }
}
