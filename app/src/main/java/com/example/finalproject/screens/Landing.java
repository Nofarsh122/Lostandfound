package com.example.finalproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;

public class Landing extends AppCompatActivity implements View.OnClickListener {

    Button btn_user_profile, btn_my_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init_views();
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