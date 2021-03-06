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

import com.bumptech.glide.Glide;
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

public class NewRecipeActivity extends AppCompatActivity {
    private EditText textName;
    private EditText textSteps;
    private EditText textIngredients;
    private String imageUrl;
    private ImageView imgGallery;
    private Uri mGalleryUri;
    private static final int GALLERY_IMAGE_REQ_CODE = 102;
    private Bundle extras;
    private String documentId;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_form);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Recipe");

        textName = findViewById(R.id.curry_form_name);
        textSteps = findViewById(R.id.curry_form_steps);
        textIngredients = findViewById(R.id.curry_form_ingredients);
        imgGallery = findViewById(R.id.imgGallery);

        extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        String name = extras.getString("Name");
        String steps = extras.getString("Steps");
        String ingredients = extras.getString("Ingredients");
        String imageLink = extras.getString("ImageLink");
        documentId = extras.getString("DocumentId");

        textName.setText(name);
        textSteps.setText(steps);
        textIngredients.setText(ingredients);
        Glide.with(getApplicationContext()).load(imageLink)
                .into(imgGallery);
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
                if (mGalleryUri != null) {
                    uploadImage();
                }
                if (extras != null) {
                    updateRecipe();
                } else {
                    saveRecipe();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveRecipe() {
        String name = textName.getText().toString();
        String steps = textSteps.getText().toString();
        String ingredients = textIngredients.getText().toString();

        if (name.trim().isEmpty() || steps.trim().isEmpty() || textIngredients.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please insert name, steps, or ingredients", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference recipeRef = FirebaseFirestore.getInstance()
                .collection("Recipe");

        Recipe recipe = new Recipe(name, steps, ingredients);
        System.out.println("ImG uRL : " + imageUrl);
        if (imageUrl != null) {
            recipe = new Recipe(name, steps, ingredients, imageUrl);
        }
        recipeRef.add(recipe);
        Toast.makeText(this, "Recipe added", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NewRecipeActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(NewRecipeActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void updateRecipe() {
        String name = textName.getText().toString();
        String steps = textSteps.getText().toString();
        String ingredients = textIngredients.getText().toString();

        if (name.trim().isEmpty() || steps.trim().isEmpty() || textIngredients.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please insert name, steps, or ingredients", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("Doc ID at update : " + documentId);
        System.out.println("ImG uRL at update : " + imageUrl);
        if (imageUrl != null) {
            FirebaseFirestore.getInstance()
                    .collection("Recipe")
                    .document(documentId)
                    .update("name", name,
                            "steps", steps,
                            "ingredients", ingredients,
                            "imageLink", imageUrl);
            Toast.makeText(this, "Recipe updated", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        FirebaseFirestore.getInstance()
                .collection("Recipe")
                .document(documentId)
                .update("name", name,
                        "steps", steps,
                        "ingredients", ingredients);
        Toast.makeText(this, "Recipe updated", Toast.LENGTH_SHORT).show();
        finish();
    }

}
