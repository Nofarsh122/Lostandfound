package com.example.finalproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;
import com.example.finalproject.services.AuthenticationService;
import com.example.finalproject.utils.SharedPreferencesUtil;

public class Landing extends AppCompatActivity implements View.OnClickListener {

    Button btn_user_profile, btn_my_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init_views();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuUserPage) {
            startActivity(new Intent(this, UserPage.class));
            return true;
        } else if (id == R.id.menuAddItem) {
            startActivity(new Intent(this, AddItem.class));
            return true;
        } else if (id == R.id.menuShowItems) {
            startActivity(new Intent(this, ShowItems.class));
            return true;
        } else if (id == R.id.menuLanding) {
            startActivity(new Intent(this, Landing.class));
            return true;
        } else if (id == R.id.menuAboutUs) {
            startActivity(new Intent(this, AboutUs.class));
            return true;
        } else if (id == R.id.menuIte) {
            AuthenticationService.getInstance().signOut();
            SharedPreferencesUtil.signOutUser(this);
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void init_views() {
        btn_user_profile=findViewById(R.id.btn_user_profile);
        btn_user_profile.setOnClickListener(this);
        btn_my_items=findViewById(R.id.btn_my_items);
        btn_my_items.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (btn_user_profile==v) {
            Intent goReg = new Intent(getApplicationContext(), UserProfile.class);
            startActivity(goReg);
        }
        if (btn_my_items==v) {
            Intent goReg = new Intent(getApplicationContext(), MyItems.class);
            startActivity(goReg);
        }
    }
}