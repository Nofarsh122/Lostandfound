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
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.model.User;
import com.example.finalproject.services.AuthenticationService;
import com.example.finalproject.services.DatabaseService;
import com.example.finalproject.utils.SharedPreferencesUtil;
import com.example.finalproject.utils.Validator;


public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText etFName, etLName, etPhone, etEmail, etPass ;
    Button btnReg;
    String fName,lName, phone, email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init_views();

    }

    private void init_views() {
        btnReg=findViewById(R.id.btnReg);
        etFName=findViewById(R.id.etFName);
        etLName=findViewById(R.id.etLName);
        etPhone=findViewById(R.id.etPhone);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPass);
        btnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        fName=etFName.getText().toString();
        lName=etLName.getText().toString();
        phone=etPhone.getText().toString();
        email=etEmail.getText().toString();
        pass=etPass.getText().toString();

        //check if registration is valid
        if (!isValid()) {
            return;
        }

        AuthenticationService.getInstance().signUp(email, pass, new AuthenticationService.AuthCallback() {
            @Override
            public void onCompleted(String uid) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "createUserWithEmail:success");

                User newUser = new User(uid, fName, lName, phone, email, pass, false);
                DatabaseService.getInstance().createNewUser(newUser, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        SharedPreferencesUtil.saveUser(Register.this, newUser);
                        Intent goLog = new Intent(getApplicationContext(), UserPage.class);
                        /// Clear the back stack (clear history) and start the LandingActivity
                        goLog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(goLog);
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });

            }

            @Override
            public void onFailed(Exception e) {
                Log.w("TAG", "createUserWithEmail:failure", e);
                Toast.makeText(Register.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isValid() {
        if (!Validator.isNameValid(fName)){
            etFName.setError("שם פרטי קצר מדי");
            etFName.requestFocus();
            return false;
        }
        if (!Validator.isNameValid(lName)){
            etLName.setError("שם משפחה קצר מדי");
            etLName.requestFocus();
            return false;
        }
        if (!Validator.isPhoneValid(phone)){
            etPhone.setError("מספר הטלפון לא תקין");
            etPhone.requestFocus();
            return false;
        }

        if (!Validator.isEmailValid(email)){
            etEmail.setError("כתובת האימייל לא תקינה");
            etEmail.requestFocus();
            return false;
        }
        if(!Validator.isPasswordValid(pass)){
            etPass.setError("הסיסמה קצרה מדי");
            etPass.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}