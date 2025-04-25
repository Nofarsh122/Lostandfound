package com.example.finalproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;
import com.example.finalproject.model.Item;
import com.example.finalproject.services.DatabaseService;

public class ItemProfile extends AppCompatActivity implements View.OnClickListener {

    EditText  etCity, etLocation, etDate, etDesc, etConPer, etType;
    TextView tvStatus;
    Button btnBack, btnContact;


    DatabaseService databaseService;
    String itemId;

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
        databaseService = DatabaseService.getInstance();
        init_views();
        itemId = getIntent().getStringExtra("ITEM_ID");
        databaseService.getItem(itemId, new DatabaseService.DatabaseCallback<Item>() {
            @Override
            public void onCompleted(Item item) {
                setView(item);
            }
            @Override
            public void onFailed(Exception e) {

            }
        });
    }
    private void init_views() {
        etCity = findViewById(R.id.etCity);
        tvStatus=findViewById(R.id.tvStatus);
        etType = findViewById(R.id.etType);
        etLocation = findViewById(R.id.etLocation);
        etDate = findViewById(R.id.etDate);
        etDesc = findViewById(R.id.etDesc);
        etConPer= findViewById(R.id.etConPer);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnContact = findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);
    }
    void setView(Item item) {
        etCity.setText(item.getCity());
        tvStatus.setText(item.getStatus());
        etType.setText(item.getType());
        etLocation.setText(item.getLocation());
        etDate.setText(item.getDate());
        etDesc.setText(item.getDesc());
        etConPer.setText(item.getConper());
    }




    @Override
    public void onClick(View view) {
        if (btnBack==view) {
            Intent goReg = new Intent(getApplicationContext(), ShowItems.class);
            startActivity(goReg);
        }

        if (btnContact==view) {
            Intent goReg = new Intent(getApplicationContext(), ShowItems.class);
            startActivity(goReg);
        }
    }
}