package com.example.finalproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.ItemAdapter;
import com.example.finalproject.model.Item;
import com.example.finalproject.services.DatabaseService;
import com.example.finalproject.utils.ImageUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity{

   RecyclerView rvItems;
   ItemAdapter itemAdapter;
   DatabaseService databaseService;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     EdgeToEdge.enable(this);
     setContentView(R.layout.activity_show_items);
     ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
       Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
       v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
       return insets;
     });
     databaseService = DatabaseService.getInstance();

     rvItems = findViewById(R.id.rvItems);
     rvItems.setLayoutManager(new LinearLayoutManager(this));
     itemAdapter = new ItemAdapter(this);
     rvItems.setAdapter(itemAdapter);

     databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
       @Override
       public void onCompleted(List<Item> items) {
         itemAdapter.addItems(items);
       }

       @Override
       public void onFailed(Exception e) {

       }
     });
   }

 }
