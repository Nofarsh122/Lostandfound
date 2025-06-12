package com.example.finalproject.screens;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.ItemAdapter;
import com.example.finalproject.adapter.UserAdapter;
import com.example.finalproject.model.Item;
import com.example.finalproject.model.User;
import com.example.finalproject.services.DatabaseService;
import com.example.finalproject.utils.SharedPreferencesUtil;

import java.util.List;

public class ItemsList extends AppCompatActivity {

    private static final String TAG = "ItemsList";
    private RecyclerView ItemsList;
    private ItemAdapter itemAdapter;
    private DatabaseService databaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_items_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseService = DatabaseService.getInstance();


        ItemsList = findViewById(R.id.rv_items_list);
        ItemsList.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, new ItemAdapter.ItemClick() {
            @Override
            public void updateDB(int position, Item item) {
                databaseService.createNewItem(item, null);
            }
        });
        ItemsList.setAdapter(itemAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
            @Override
            public void onCompleted(List<Item> items) {
                itemAdapter.setItems(items);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to get items list", e);
            }
        });
    }
}
