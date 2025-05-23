package com.example.finalproject.screens;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity {

    RecyclerView rvItems;
    Spinner spinner;
    EditText etSearchItem;
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

        etSearchItem = findViewById(R.id.etSearchItem);


        rvItems = findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, item -> databaseService.createNewItem(item, null));
        rvItems.setAdapter(itemAdapter);
        spinner = findViewById(R.id.spSubCategory5);
        List<String> list = new ArrayList<>();
        list.add("מיין על פי:");
        list.add(" מהחדש ביותר לישן ביותר");
        list.add(" מהישן ביותר לחדש ביותר");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // This is where you handle the selection
                String selectedItem = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
                Toast.makeText(getApplicationContext(), "Selected item: " + selectedItem, Toast.LENGTH_SHORT).show();
                Log.d("!!!!!!!", selectedItem);
                if (selectedItem.equals(" מהחדש ביותר לישן ביותר")) {
                    Log.d("!!!!!!!", "sort by " + " מהחדש ביותר לישן ביותר");
                    itemAdapter.sortNewToOld();
                } else if (selectedItem.equals(" מהישן ביותר לחדש ביותר")) {
                    Log.d("!!!!!!!", "sort by " + " מהישן ביותר לחדש ביותר");
                    itemAdapter.sortOldToNew();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner = findViewById(R.id.spCategory5);
        List<String> list1 = new ArrayList<>();
        list1.add("חיפוש על פי:");
        list1.add("סוג הפריט");
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
                Log.d("!!!!!!!", selectedItem);

                if (selectedItem.equals("סוג הפריט")) {
                    Log.d("!!!!!!!", "sort by " + "סוג הפריט");
                    itemAdapter.sortByType();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when nothing is selected, if necessary
            }
        });


        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
            @Override
            public void onCompleted(List<Item> items) {
                itemAdapter.setItems(items);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });


        etSearchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemAdapter.filterItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
