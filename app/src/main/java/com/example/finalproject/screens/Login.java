package com.example.finalproject.screens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.model.User;
import com.example.finalproject.services.AuthenticationService;
import com.example.finalproject.services.DatabaseService;
import com.example.finalproject.utils.SharedPreferencesUtil;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText etPassLogin, etEmailLogin;
    Button btnSignIn, btnBack;
    String email, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init_views();
    }

    private void init_views() {
        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPassLogin = findViewById(R.id.etPassLogin);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        email = etEmailLogin.getText().toString() + "";
        pass = etPassLogin.getText().toString() + "";
        Log.d("TAG", "onClick:btnSignIn");

        boolean isValid = true;
        if (!email.contains("@")) {
            etPassLogin.setError("כתובת אימייל אינה תקינה");
            isValid = false;
        }
        if (pass.length() < 6) {
            etPassLogin.setError("סיסמא צריכה להיות בעלת שש תווים לפחות");
            isValid = false;
        }
        if (pass.length() > 20) {
            etPassLogin.setError("סיסמא צריכה להיות מקסימום בעלת 20 תווים");
            isValid = false;
        }

        if (!isValid) {
            return;
        }
        AuthenticationService.getInstance().signIn(email, pass, new AuthenticationService.AuthCallback() {
            @Override
            public void onCompleted(String uid) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "signInWithEmail:success");

                DatabaseService.getInstance().getUser(uid, new DatabaseService.DatabaseCallback<User>() {
                    @Override
                    public void onCompleted(User user) {
                        if (user == null) {
                            throw new RuntimeException("ahhhhhhhh!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        }
                        Log.i("Login activity", user.toString());
                        SharedPreferencesUtil.saveUser(Login.this, user);
                        Intent go = new Intent(getApplicationContext(), UserPage.class);
                        startActivity(go);
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                Log.w("etroor sign in", "signInWithEmail:failure", e);
                Toast.makeText(getApplicationContext(), "משתמש אינו קיים",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}

