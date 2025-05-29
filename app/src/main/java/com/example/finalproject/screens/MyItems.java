package com.example.finalproject.screens;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.ItemAdapter;
import com.example.finalproject.model.Item;
import com.example.finalproject.services.AuthenticationService;
import com.example.finalproject.services.DatabaseService;

import java.util.List;
import java.util.function.Predicate;

public class MyItems extends AppCompatActivity {

    private static final String TAG = "MyItemsList";
    private RecyclerView MyItemsList;
    private ItemAdapter itemAdapter;
    private DatabaseService databaseService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseService = DatabaseService.getInstance();

        MyItemsList = findViewById(R.id.rv_MyItems_list);
        MyItemsList.setLayoutManager(new LinearLayoutManager(this));
        itemAdapter = new ItemAdapter(this, new ItemAdapter.ItemClick() {
            @Override
            public void updateDB(Item item) {
            }
        });
        MyItemsList.setAdapter(itemAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        final String currentUserId = AuthenticationService.getInstance().getCurrentUserId();
        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
            @Override
            public void onCompleted(List<Item> items) {
                items.removeIf(item -> !item.getUserId().equals(currentUserId));
                itemAdapter.setItems(items);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to get items list", e);
            }
        });
    }
}
