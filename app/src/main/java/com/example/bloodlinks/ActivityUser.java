package com.example.bloodlinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityUser extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolBar;
    private FirebaseAuth firebaseAuth;
    private DrawerLayout drawer;
    private ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth=FirebaseAuth.getInstance();


        toolBar = findViewById(R.id.actionbar);
        setSupportActionBar(toolBar);
        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));


        drawer = findViewById(R.id.drawer_layout);

        final LinearLayout holder = findViewById(R.id.holder);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {

                //this code-block is the real player behind this beautiful ui
                // basically, it's a mathemetical calculation which handles the shrinking of
                // our content view.

                float scaleFactor = 7f;
                float slideX = drawerView.getWidth() * slideOffset;

                holder.setTranslationX(slideX);
                holder.setScaleX(1 - (slideOffset / scaleFactor));
                holder.setScaleY(1 - (slideOffset / scaleFactor));

                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            setTitle(mTitles.get(0));

            navigationView.setCheckedItem(R.id.nav_home);
        }





    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;

            case R.id.nav_logout:
                 firebaseAuth.signOut();
                 finish();
                 startActivity(new Intent(ActivityUser.this,ActivityHome.class));
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
