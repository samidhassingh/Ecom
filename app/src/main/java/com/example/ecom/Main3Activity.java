package com.example.ecom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class Main3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar1;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private StorageReference mStorageRef;
    TextView nameHeader;
    TextView emailHeader;
    ImageView imageView;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        toolbar1=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("WhiteWular");
        mAuth= FirebaseAuth.getInstance();
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.bringToFront();
        updateNavHeader();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar1,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

       getSupportFragmentManager().beginTransaction().replace(R.id.content,new dashboardFragment()).addToBackStack(null).commit();
        navigationView.setCheckedItem(R.id.dashboard);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,new dashboardFragment()).addToBackStack(null).commit();
                //getSupportActionBar().setTitle("Dashboard");
                navigationView.setCheckedItem(R.id.dashboard);
                break;
            case R.id.catalogue:
               getSupportFragmentManager().beginTransaction().replace(R.id.content,new catalogueFragment()).addToBackStack(null).commit();
                //getSupportActionBar().setTitle("Catalogue");
                navigationView.setCheckedItem(R.id.catalogue);
                break;
            case R.id.isell:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,new isellFragment()).addToBackStack(null).commit();
                //getSupportActionBar().setTitle("iSell");
                navigationView.setCheckedItem(R.id.isell);
                break;
            case R.id.promotions:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,new promotionsFragment()).addToBackStack(null).commit();
               // getSupportActionBar().setTitle("Promotions");
                navigationView.setCheckedItem(R.id.promotions);
                break;
            case R.id.finance:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,new financeFragment()).addToBackStack(null).commit();
               // getSupportActionBar().setTitle("Finance");
                navigationView.setCheckedItem(R.id.finance);
                break;
            case R.id.settlement:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,new settlementsFragment()).addToBackStack(null).commit();
               // getSupportActionBar().setTitle("My Account");
                navigationView.setCheckedItem(R.id.settlement);
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,new settingsFragment()).addToBackStack(null).commit();
               // getSupportActionBar().setTitle("Settings");
                navigationView.setCheckedItem(R.id.settings);
                break;
            case R.id.support:
                getSupportFragmentManager().beginTransaction().replace(R.id.content,new supportFragment()).addToBackStack(null).commit();
               // getSupportActionBar().setTitle("Support");
                navigationView.setCheckedItem(R.id.support);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void updateNavHeader(){
        navigationView=findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        nameHeader=headerView.findViewById(R.id.nameHeader);
        emailHeader=headerView.findViewById(R.id.emailHeader);
        imageView=headerView.findViewById(R.id.imageView);
        myRef=FirebaseDatabase.getInstance().getReference("users");
        String userId=mAuth.getCurrentUser().getUid();
        myRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("userName").getValue().toString();
                String email = dataSnapshot.child("userEmail").getValue().toString();
                nameHeader.setText(name);
                emailHeader.setText(email);
                mStorageRef= FirebaseStorage.getInstance().getReference().child("images").child(dataSnapshot.child("imageName").getValue().toString());
                Glide.with(Main3Activity.this)
                        .load(mStorageRef)
                        .into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mAuth.signOut();
        finish();
        return super.onOptionsItemSelected(item);

    }


}
