package com.example.finalproject.screens;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.finalproject.model.User;
import com.example.finalproject.services.AuthenticationService;
import com.example.finalproject.utils.SharedPreferencesUtil;

public class UserPage extends AppCompatActivity implements View.OnClickListener {

    Button btnPublic, btnSearch, btnPer, btnLogout ,btnAdminP;
    User currentUser;
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

        currentUser = SharedPreferencesUtil.getUser(UserPage.this);
        Log.d(TAG, "User: " + currentUser);

        init_views();
    }

    private void init_views() {
        btnPublic = findViewById(R.id.btnPublic);
        btnPublic.setOnClickListener(this);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        btnPer = findViewById(R.id.btnPer);
        btnPer.setOnClickListener(this);
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);
        btnAdminP = findViewById(R.id.btnAdminP);
        btnAdminP.setOnClickListener(this);

        if (currentUser != null && currentUser.isAdmin()) {
            btnAdminP.setVisibility(View.VISIBLE);
        } else {
            btnAdminP.setVisibility(View.GONE);
        }
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
        if (id == R.id.menuLanding) {
            Intent go = new Intent(this, Landing.class);
            startActivity(go);
            return true;
        }
        if (id == R.id.menuMain) {
            Intent go = new Intent(this, MainActivity.class);
            startActivity(go);
            return true;
        }
        if (id == R.id.menuAdmin) {
            Intent go = new Intent(this, AdminPage.class);
            startActivity(go);
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == btnLogout.getId()) {
            Log.d(TAG, "Sign out button clicked");
            /// Sign out the user using the authentication service
            AuthenticationService.getInstance().signOut();
            /// Clear the user data from shared preferences
            SharedPreferencesUtil.signOutUser(UserPage.this);

            Log.d(TAG, "User signed out, redirecting to LandingActivity");
            Intent landingIntent = new Intent(UserPage.this, MainActivity.class);
            /// Clear the back stack (clear history) and start the LandingActivity
            landingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(landingIntent);
            return;

        }
        if (btnPublic == view) {
            Intent goReg = new Intent(getApplicationContext(), AddItem.class);
            startActivity(goReg);
        }

        if (btnSearch == view) {
            Intent goReg = new Intent(getApplicationContext(), ShowItems.class);
            startActivity(goReg);
        }

        if (btnPer == view) {
            Intent goReg = new Intent(getApplicationContext(), Landing.class);
            startActivity(goReg);
        }
        if (btnAdminP == view) {
            Intent goReg = new Intent(getApplicationContext(), AdminPage.class);
            startActivity(goReg);
        }
    }
}

