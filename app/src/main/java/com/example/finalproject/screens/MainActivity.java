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
import com.example.finalproject.services.AuthenticationService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegHome, btnLogHome, btnAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (AuthenticationService.getInstance().isUserSignedIn()) {
            Intent intent = new Intent(this, UserPage.class);
            startActivity(intent);
            finish();
            return;
        }

        init_views();
    }

    private void init_views() {
        btnLogHome=findViewById(R.id.btnLogHome);
        btnLogHome.setOnClickListener(this);
        btnRegHome=findViewById(R.id.btnRegHome);
        btnRegHome.setOnClickListener(this);
        btnAboutUs=findViewById(R.id.btnAboutUs);
        btnAboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (btnRegHome==v) {
            Intent goReg = new Intent(getApplicationContext(), Register.class);
            startActivity(goReg);
        }
        if (btnLogHome==v){
            Intent goLog=new Intent(getApplicationContext(), Login.class);
            startActivity(goLog);
        }
        if (btnAboutUs==v){
            Intent goAboutUs = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(goAboutUs);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}


