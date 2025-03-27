package com.example.finalproject.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.adapter.UserAdapter;
import com.example.finalproject.model.User;
import com.example.finalproject.services.AuthenticationService;
import com.example.finalproject.services.DatabaseService;
import com.example.finalproject.utils.SharedPreferencesUtil;

import java.util.List;

public class UsersList extends AppCompatActivity {

    private static final String TAG = "UsersListActivity";
    private RecyclerView usersList;
    private UserAdapter userAdapter;
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseService = DatabaseService.getInstance();

        usersList = findViewById(R.id.rv_users_list);
        usersList.setLayoutManager(new LinearLayoutManager(this));
        UserAdapter.OnUserClickListener onUserClickListener = user -> {
            // Handle user click
            Log.d(TAG, "User clicked: " + user);
            Intent intent = new Intent(this, UserProfile.class);
            intent.putExtra("USER_UID", user.getId());
            startActivity(intent);

        };
        UserAdapter.OnUserClickListener onLongUserClickListener = user -> {
            // Handle long user click
            Log.d(TAG, "User long clicked: " + user);
            // show popup to delete user
            showDeleteUserDialog(user);

        };
        userAdapter = new UserAdapter(onUserClickListener, onLongUserClickListener);
        usersList.setAdapter(userAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        databaseService.getUsers(new DatabaseService.DatabaseCallback<List<User>>() {
            @Override
            public void onCompleted(List<User> users) {
                userAdapter.setUserList(users);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to get users list", e);
            }
        });
    }

    private void showDeleteUserDialog(User user) {
        Log.d(TAG, "Want to delete user: " + user);
        Log.d(TAG, "Current user: " + SharedPreferencesUtil.getUser(this));
        if (user.equals(SharedPreferencesUtil.getUser(this))) {
            Log.d(TAG, "Cannot delete current user");
            Toast.makeText(this, "Cannot delete current user", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> deleteUser(user))
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void deleteUser(User user) {
        databaseService.deleteUser(user.getId(), new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void aVoid) {
                Log.d(TAG, "User deleted: " + user);
                userAdapter.removeUser(user);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to delete user: " + user, e);
            }
        });
    }
}
