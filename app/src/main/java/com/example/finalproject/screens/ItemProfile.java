package com.example.finalproject.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;
import com.example.finalproject.model.Item;
import com.example.finalproject.services.DatabaseService;
import com.example.finalproject.utils.ImageUtil;

public class ItemProfile extends AppCompatActivity implements View.OnClickListener {

    EditText etCity, etLocation, etDate, etDesc, etPhonenum, etType;
    ImageView etImage;
    Button btnContact;
    DatabaseService databaseService;
    String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        etImage = findViewById(R.id.etImage);
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
        etImage.setImageBitmap(ImageUtil.convertFrom64base(item.getImageBase64()));

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
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("QueryPermissionsNeeded")
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
