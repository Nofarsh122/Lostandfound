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
   Spinner spinner;
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
     spinner = findViewById(R.id.spSubCategory5);
     List<String> list = new ArrayList<>();
     list.add("חיפוש על פי:");
     list.add("סדר מהחדש ביותר לישן ביותר");
     list.add(" סדר מהישן ביותר לחדש ביותר");
     list.add("לפי הסטטוס");
     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     spinner.setAdapter(adapter);

     spinner = findViewById(R.id.spCategory5);
     List<String> list1 = new ArrayList<>();
     list1.add("מיין:");
     list1.add("תאריך");
     list1.add(" מיקום");
     list1.add("סוג המוצר");
     ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     spinner.setAdapter(adapter1);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               // This is where you handle the selection
               String selectedItem = parentView.getItemAtPosition(position).toString();
               // Do something with the selected item
               Toast.makeText(getApplicationContext(), "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parentView) {
               // Handle case when nothing is selected, if necessary
           }
       });


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
