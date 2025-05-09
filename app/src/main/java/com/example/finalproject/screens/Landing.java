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

    Button btnUserPage, btnAdminPage;

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
        btnUserPage = findViewById(R.id.btnUserPage);
        btnUserPage.setOnClickListener(this);
        btnAdminPage = findViewById(R.id.btnAdminPage);
        btnAdminPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (btnUserPage==v) {
                Intent goReg = new Intent(getApplicationContext(), UserPage.class);
                startActivity(goReg);
            }

        if (btnAdminPage==v) {
                Intent goReg = new Intent(getApplicationContext(), AdminPage.class);
                startActivity(goReg);
            }


    }
}