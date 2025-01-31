package com.example.finalproject.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;

public class ItemProfile extends AppCompatActivity implements View.OnClickListener {

    EditText  etCity, etLocation, etDate, etDesc, etStatus, etConPer;
    Button btnBack, btnContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init_views();
    }

    private void init_views() {
        etCity = findViewById(R.id.etCity);
        etLocation = findViewById(R.id.etLocation);
        etDate = findViewById(R.id.etDate);
        etDesc = findViewById(R.id.etDesc);
        etConPer= findViewById(R.id.etConPer);
        etStatus = findViewById(R.id.etStatus);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnContact = findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }
}