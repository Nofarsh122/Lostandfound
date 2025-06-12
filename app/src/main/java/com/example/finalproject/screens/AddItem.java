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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    EditText tvItemDesc, tvItemLoc, tvItemConPer, tvItemDate;
    AutoCompleteTextView tvItemCity ,tvItemType;;
    Button btnFindItem, btnGallery, btnCamera;
    ImageView ItemImageView;
    private DatabaseService databaseService;
    private ActivityResultLauncher<Intent> selectImageLauncher;
    private ActivityResultLauncher<Intent> captureImageLauncher;

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
        tvItemType = findViewById(R.id.tvItemType);
        ItemImageView = findViewById(R.id.ItemImageView);
        btnFindItem = findViewById(R.id.btnFindItem);
        btnFindItem.setOnClickListener(this);
        btnGallery = findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(this);
        btnCamera = findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(this);


        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                ISRAEL_CITIES
        );
        tvItemCity.setAdapter(cityAdapter);
        tvItemCity.setThreshold(1); // מתחיל להציע כבר מהתו הראשון


        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                ITEM_TYPES
        );
        tvItemType.setAdapter(typeAdapter);
        tvItemType.setOnClickListener(v -> tvItemType.showDropDown());
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
        String location = tvItemLoc.getText().toString();
        String city = tvItemCity.getText().toString();
        String conper = tvItemConPer.getText().toString();
        String date = tvItemDate.getText().toString();
        String type = tvItemType.getText().toString();
        String imageBase64 = ImageUtil.convertTo64Base(ItemImageView);

        if (!isValid ( desc, date, city, location, conper, type))
            return;

        Log.d(TAG, "Adding item to database");
        Log.d(TAG, "Description: " + desc);
        Log.d(TAG, "City: " + city);
        Log.d(TAG, "Location: " + location);
        Log.d(TAG, "Contactperson: " + conper);
        Log.d(TAG, "Date: " + date);
        Log.d(TAG, "Type: " + type);
        Log.d(TAG, "ImageBase64: " + imageBase64);


        /// create a new item object
        String status = "Not Found";
        String id;
        String userId;
        userId= AuthenticationService.getInstance().getCurrentUserId();
        id=databaseService.generateItemId();
        Item item = new Item(id, desc, date, city, location, conper, status, type, userId, ImageUtil.convertTo64Base(ItemImageView));

        databaseService.createNewItem(item, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Log.d(TAG, "Item added successfully");
                Toast.makeText(AddItem.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                /// clear the input fields after adding the food for the next food
                Log.d(TAG, "Clearing input fields");
                tvItemDesc.setText("");
                tvItemLoc.setText("");
                tvItemConPer.setText("");
                tvItemDate.setText("");
                tvItemType.setText("");
                ItemImageView.setImageBitmap(null);
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to add item", e);
                Toast.makeText(AddItem.this, "Failed to add item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static final String[] ISRAEL_CITIES = {
            "אום אל-פחם",
            "אופקים",
            "אור עקיבא",
            "אור יהודה",
            "אילת",
            "אלעד",
            "אריאל",
            "אשדוד",
            "אשקלון",
            "באקה אל-גרבייה",
            "באר שבע",
            "באר יעקב",
            "בית שאן",
            "בית שמש",
            "ביתר עילית",
            "בני ברק",
            "בת ים",
            "גבעתיים",
            "גבעת שמואל",
            "דימונה",
            "הוד השרון",
            "הרצליה",
            "חדרה",
            "חולון",
            "חיפה",
            "טבריה",
            "טייבה",
            "טירה",
            "טירת כרמל",
            "יבנה",
            "יהוד-מונוסון",
            "יקנעם עילית",
            "ירושלים",
            "כפר סבא",
            "כפר קאסם",
            "כרמיאל",
            "לוד",
            "מגדל העמק",
            "מודיעין עילית",
            "מודיעין-מכבים-רעות",
            "מעלה אדומים",
            "מעלה עירון",
            "נהריה",
            "נוף הגליל",
            "נחף",
            "נשר",
            "נצרת",
            "נשר",
            "נס ציונה",
            "נתיבות",
            "נתניה",
            "סח'נין",
            "עכו",
            "עפולה",
            "עראבה",
            "ערד",
            "ערערה",
            "פתח תקווה",
            "צפת",
            "קלנסווה",
            "קריית אונו",
            "קריית אתא",
            "קריית ביאליק",
            "קריית גת",
            "קריית מלאכי",
            "קריית מוצקין",
            "קריית שמונה",
            "ראש העין",
            "ראשון לציון",
            "רחובותר",
            "רהט",
            "רמלה",
            "רמת גן",
            "רמת השרון",
            "רעננה",
            "שפרעם",
            "תל אביב-יפו",
            "שדרות"
    };

    private static final String[] ITEM_TYPES = {
            "אופניים",
            "בגדים",
            "נעליים",
            "תיק",
            "משקפיים",
            "מצלמה",
            "דרכון",
            "מפתחות",
            "נייד",
            "מחשב נייד",
            "ספר",
            "צעצוע",
            "שעון יד",
            "כובע",
            "נעליים",
            "מוצרי חשמל קטנים",
            "כסף / ארנק",
            "תכשיטים",
            "מסמכים",
            "כלי עבודה",
            "כלי בית",
            "כרטיס אשראי",
            "מזוודה",
            "מזרן / שמיכה",
            "מכשירי ספורט",
            "כלי כתיבה",
            "משקפי שמש",
            "אוזניות",
            "פנס",
            "כרטיסים",
            "אחר",
    };



    private void selectImageFromGallery() {
       Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      selectImageLauncher.launch(intent);
    }

    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }

    /// validate the input
    private boolean isValid(String desc, String date, String city, String location, String conper, String type) {
        if (desc.isEmpty()) {
            Log.e(TAG, "Desc is empty");
            tvItemDesc.setError("Desc is required");
            tvItemDesc.requestFocus();
            return false;
        }

        if (city.isEmpty()) {
            Log.e(TAG, "City is empty");
            tvItemCity.setError("City is required");
            tvItemCity.requestFocus();
            return false;
        }
        if (location.isEmpty()) {
            Log.e(TAG, "Location is empty");
            tvItemLoc.setError("Location is required");
            tvItemLoc.requestFocus();
            return false;
        }
        if (conper.isEmpty()) {
            Log.e(TAG, "Conper is empty");
            tvItemConPer.setError("Conper is required");
            tvItemConPer.requestFocus();
            return false;
        }
        if (date.isEmpty()) {
            Log.e(TAG, "Date is empty");
            tvItemDate.setError("Date is required");
            tvItemDate.requestFocus();
            return false;
        }

        if (type.isEmpty()) {
            Log.e(TAG, "Type is empty");
            tvItemType.setError("Type is required");
            tvItemType.requestFocus();
            return false;
        }

//        if (imageBase64 == null) {
//            Log.e(TAG, "Image is required");
//            Toast.makeText(this, "Image is required", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }

}