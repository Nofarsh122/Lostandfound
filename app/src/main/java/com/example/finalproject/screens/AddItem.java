package com.example.finalproject.screens;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.R;
import com.example.finalproject.model.Item;
import com.example.finalproject.services.AuthenticationService;
import com.example.finalproject.services.DatabaseService;
import com.example.finalproject.utils.ImageUtil;

public class AddItem extends AppCompatActivity implements View.OnClickListener {
    EditText tvItemDesc, tvItemCity, tvItemLoc, tvItemConPer, tvItemDate;
    Button btnFindItem, btnGallery, btnCamera;
    ImageView ItemImageView;
    private DatabaseService databaseService;

    private ActivityResultLauncher<Intent> selectImageLauncher;
    private ActivityResultLauncher<Intent> captureImageLauncher;
    ImageView IVPreviewImage;
    int SELECT_PICTURE = 200;
    private String desc, city, location, conper, date, imageBase64;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageUtil.requestPermission(this);
        databaseService = DatabaseService.getInstance();

        init_views();

        /// register the activity result launcher for selecting image from gallery
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        ItemImageView.setImageURI(selectedImage);
                    }
                });

        /// register the activity result launcher for capturing image from camera
        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        ItemImageView.setImageBitmap(bitmap);
                    }
                });

    }

    private void init_views() {
        tvItemDesc = findViewById(R.id.tvItemDesc);
        tvItemCity = findViewById(R.id.tvItemCity);
        tvItemLoc = findViewById(R.id.tvItemLoc);
        tvItemConPer = findViewById(R.id.tvItemConPer);
        tvItemDate = findViewById(R.id.tvItemDate);
        ItemImageView = findViewById(R.id.ItemImageView);
        btnFindItem = findViewById(R.id.btnFindItem);
        btnFindItem.setOnClickListener(this);
        btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(this);
        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnFindItem.getId()) {
            findItem();
            return;
        }
        if (view.getId() == btnGallery.getId()) {
            selectImageFromGallery();
            return;
        }
        if (view.getId() == btnCamera.getId()) {
            captureImageFromCamera();
            return;
        }
    }


    private void findItem() {
        String desc = tvItemDesc.getText().toString();
        String city = tvItemCity.getText().toString();
        String location = tvItemLoc.getText().toString();
        String conper = tvItemConPer.getText().toString();
        String date = tvItemDate.getText().toString();
        String imageBase64 = ImageUtil.convertTo64Base(ItemImageView);

        if (!isValid ( desc, date, city, location, conper))
            return;

        Log.d(TAG, "Adding item to database");
        Log.d(TAG, "Description: " + desc);
        Log.d(TAG, "City: " + city);
        Log.d(TAG, "Location: " + location);
        Log.d(TAG, "Contactperson: " + conper);
        Log.d(TAG, "Date: " + date);
        Log.d(TAG, "ImageBase64: " + imageBase64);


        /// create a new item object
        String status = "Not Found";
        String id;
        String userId;
        userId= AuthenticationService.getInstance().getCurrentUserId();
        id=databaseService.generateItemId();
        Item item = new Item(id, desc, date, city, location, conper, status, userId); // TODO

        databaseService.createNewItem(item, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Log.d(TAG, "Item added successfully");
                Toast.makeText(AddItem.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                /// clear the input fields after adding the food for the next food
                Log.d(TAG, "Clearing input fields");
                tvItemDesc.setText("");
                tvItemCity.setText("");
                tvItemLoc.setText("");
                tvItemConPer.setText("");
                tvItemDate.setText("");
                ItemImageView.setImageBitmap(null);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to add item", e);
                Toast.makeText(AddItem.this, "Failed to add item", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void selectImageFromGallery() {

        //   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //  selectImageLauncher.launch(intent);

        imageChooser();
    }

    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }

    /// validate the input
    private boolean isValid(String desc, String date, String city, String location, String conper) {
        if (this.desc.isEmpty()) {
            Log.e(TAG, "Desc is empty");
            tvItemDesc.setError("Desc is required");
            tvItemDesc.requestFocus();
            return false;
        }

        if (this.city.isEmpty()) {
            Log.e(TAG, "City is empty");
            tvItemCity.setError("City is required");
            tvItemCity.requestFocus();
            return false;
        }
        if (this.location.isEmpty()) {
            Log.e(TAG, "Location is empty");
            tvItemLoc.setError("Location is required");
            tvItemLoc.requestFocus();
            return false;
        }
        if (this.conper.isEmpty()) {
            Log.e(TAG, "Conper is empty");
            tvItemConPer.setError("Conper is required");
            tvItemConPer.requestFocus();
            return false;
        }
        if (this.date.isEmpty()) {
            Log.e(TAG, "Date is empty");
            tvItemDate.setError("Date is required");
            tvItemDate.requestFocus();
            return false;
        }

        if (imageBase64 == null) {
            Log.e(TAG, "Image is required");
            Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }

    }
}