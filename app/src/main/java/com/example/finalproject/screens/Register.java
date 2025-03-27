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
import com.example.finalproject.utils.SharedPreferencesUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity implements View.OnClickListener {

    EditText etFName, etLName, etPhone, etEmail, etPass ;
    Button btnReg;
    String fName,lName, phone, email, pass;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

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

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

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
        Boolean isValid=true;
        if (fName.length()<2){
            Toast.makeText(Register.this,"שם פרטי קצר מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (lName.length()<2){
            Toast.makeText(Register.this,"שם משפחה קצר מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if (phone.length()<9||phone.length()>10){
            Toast.makeText(Register.this,"מספר הטלפון לא תקין", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        if (!email.contains("@")){
            Toast.makeText(Register.this,"כתובת האימייל לא תקינה", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(pass.length()<6){
            Toast.makeText(Register.this,"הסיסמה קצרה מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }
        if(pass.length()>20){
            Toast.makeText(Register.this,"הסיסמה ארוכה מדי", Toast.LENGTH_LONG).show();
            isValid = false;
        }

        if (isValid==true){

            AuthenticationService.getInstance().signUp(email, pass, new AuthenticationService.AuthCallback() {
                @Override
                public void onCompleted(String uid) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success");

                    User newUser = new User(uid, fName, lName, phone, email, pass, false);
                    myRef.child(uid).setValue(newUser);
                    SharedPreferencesUtil.saveUser(Register.this, newUser);


                    Intent goLog = new Intent(getApplicationContext(), Login.class);
                    startActivity(goLog);
                }

                @Override
                public void onFailed(Exception e) {
                    Log.w("TAG", "createUserWithEmail:failure", e);
                    Toast.makeText(Register.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}