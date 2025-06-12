package com.example.finalproject.screens;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.finalproject.model.Item;
import com.example.finalproject.services.AuthenticationService;
import com.example.finalproject.utils.SharedPreferencesUtil;

public class AdminPage extends AppCompatActivity implements View.OnClickListener {

    Button btnToUsers , btnToItems, btn_admin_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnToItems = findViewById(R.id.btn_admin_to_items);
        btnToItems.setOnClickListener(v -> {
            Intent intent = new Intent(this, ItemsList.class);
            startActivity(intent);
        });

        btnToUsers = findViewById(R.id.btn_admin_to_users);
        btnToUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, UsersList.class);
            startActivity(intent);
        });

        btn_admin_logout = findViewById(R.id.btn_admin_logout);
        btn_admin_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btn_admin_logout.getId()) {
            Log.d(TAG, "Sign out button clicked");
            /// Sign out the user using the authentication service
            AuthenticationService.getInstance().signOut();
            /// Clear the user data from shared preferences
            SharedPreferencesUtil.signOutUser(AdminPage.this);

            Log.d(TAG, "User signed out, redirecting to LandingActivity");
            Intent landingIntent = new Intent(AdminPage.this, MainActivity.class);
            /// Clear the back stack (clear history) and start the LandingActivity
            landingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(landingIntent);
            return;

        }
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
}

