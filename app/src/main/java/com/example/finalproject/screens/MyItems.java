package com.example.finalproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.finalproject.utils.SharedPreferencesUtil;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
            public void updateDB(int position, Item item) {
            }
        });
        MyItemsList.setAdapter(itemAdapter);
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
    @Override
    protected void onResume() {
        super.onResume();
        final String currentUserId = AuthenticationService.getInstance().getCurrentUserId();
        databaseService.getItems(new DatabaseService.DatabaseCallback<List<Item>>() {
            @Override
            public void onCompleted(List<Item> items) {
                items.removeIf(item -> !item.getUserId().equals(currentUserId));
                runOnUiThread(() -> itemAdapter.setItems(items));

            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to get items list", e);
            }
        });
    }
}
