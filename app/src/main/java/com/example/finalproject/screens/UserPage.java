package com.example.finalproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;

public class UserPage extends AppCompatActivity implements View.OnClickListener {

    Button btnPublic, btnSearch, btnPer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init_views();
    }

    private void init_views() {
        btnPublic = findViewById(R.id.btnPublic);
        btnPublic.setOnClickListener(this);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        btnPer = findViewById(R.id.btnPer);
        btnPer.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
         if (id == R.id.menuUserpage) {
             Intent go=new Intent(this,UserPage.class);
             startActivity(go);
             return true;
         }
        if (id == R.id.menuMain) {
            Intent go=new Intent(this,MainActivity.class);
            startActivity(go);
            return true;
        }
        if (id == R.id.menuItem) {
            Toast.makeText(getApplicationContext(),"Item 3 Selected", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public void goMyData(View view) {

        Intent go=new Intent(this,HistoryCalls.class);
        startActivity(go);
    }

    @Override
    public void onClick(View view) {
        if (btnPublic==view) {
            Intent goReg = new Intent(getApplicationContext(), AddItem.class);
            startActivity(goReg);
        }

        if (btnSearch==view) {
            Intent goReg = new Intent(getApplicationContext(), ShowItems.class);
            startActivity(goReg);
        }

        if (btnPer==view) {
            Intent goReg = new Intent(getApplicationContext(), UsersList.class);
            startActivity(goReg);
        }



    }
}

