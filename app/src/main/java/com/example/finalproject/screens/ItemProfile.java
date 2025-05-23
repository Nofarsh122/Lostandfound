package com.example.finalproject.screens;

import android.content.Intent;
import android.net.Uri;
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

    EditText etCity, etLocation, etDate, etDesc, etPhonenum, etType;
    Button btnContact;

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
        etType = findViewById(R.id.etType);
        etLocation = findViewById(R.id.etLocation);
        etDate = findViewById(R.id.etDate);
        etDesc = findViewById(R.id.etDesc);
        etPhonenum = findViewById(R.id.etPhonenum);
        btnContact = findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);
    }

    void setView(Item item) {
        etCity.setText(item.getCity());
        etType.setText(item.getType());
        etLocation.setText(item.getLocation());
        etDate.setText(item.getDate());
        etDesc.setText(item.getDesc());
        etPhonenum.setText(item.getPhonenum());
    }

    @Override
    public void onClick(View view) {
        if (btnContact == view) {
            String phoneNumber = etPhonenum.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }
}