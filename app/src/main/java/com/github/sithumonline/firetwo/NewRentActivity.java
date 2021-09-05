package com.github.sithumonline.firetwo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.util.IntentUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewRentActivity extends AppCompatActivity {
    private EditText textName;
    private EditText textAddress;
    private EditText textItems;
    private EditText textHourlyRental;
    private String imageUrl;
    private ImageView imgGallery;
    private Uri mGalleryUri;
    private static final int GALLERY_IMAGE_REQ_CODE = 102;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_main_form);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Rent");

        textName = findViewById(R.id.curry_form_name);
        textAddress = findViewById(R.id.curry_form_steps);
        textItems = findViewById(R.id.curry_form_items);
        textHourlyRental = findViewById(R.id.curry_form_ingredients);
        imgGallery = findViewById(R.id.imgGallery);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nexter_menu_items, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
//                if (extras == null) { updateNote(); }
                if (mGalleryUri != null) {
                    uploadImage();
                }
                saveRent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveRent() {
        String name = textName.getText().toString();
        String address = textAddress.getText().toString();
        String items = textItems.getText().toString();
        int hourlyRental = Integer.parseInt(textHourlyRental.getText().toString());

        if (name.trim().isEmpty() || address.trim().isEmpty() || items.trim().isEmpty() || textHourlyRental.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please insert name, address, items or hourly rental", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference rentRef = FirebaseFirestore.getInstance()
                .collection("Rent");

        Rent rent = new Rent(name, address, items, hourlyRental);
        System.out.println("ImG uRL : " + imageUrl);
        if (imageUrl != null) {
            rent = new Rent(name, address, items, imageUrl, hourlyRental);
        }
        rentRef.add(rent);
        Toast.makeText(this, "Rent added", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void showImage(View view) {
        if (mGalleryUri == null) {
            return;
        }
        startActivity(IntentUtils.getUriViewIntent(this, mGalleryUri));
    }

    public void pickGalleryImage(View view) {
        ImagePicker.with(this)
                // Crop Image(User can choose Aspect Ratio)
                .crop()
                // User can only select image from Gallery
                .galleryOnly()

                .galleryMimeTypes(new String[]{"image/png",
                        "image/jpg",
                        "image/jpeg"
                })
                // Image resolution will be less than 1080 x 1920
                .maxResultSize(1080, 1920)
                // .saveDir(getExternalFilesDir(null))
                .start(GALLERY_IMAGE_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mGalleryUri = data.getData();
            System.out.println("Img URI : " + mGalleryUri);
            imgGallery.setImageURI(mGalleryUri);
        }
    }

    private void uploadImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        String uuid = UUID.randomUUID().toString();
        String imageUri = "images/" + uuid;
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/fireone-18094.appspot.com/o/images%2F" + uuid + "?alt=media";

        StorageReference ref = storageRef.child(imageUri);

        ref.putFile(mGalleryUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(NewRentActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(NewRentActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    }
                });

    }


}
