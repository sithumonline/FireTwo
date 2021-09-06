package com.github.sithumonline.firetwo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class NewRequestActivity extends AppCompatActivity {
    private EditText textName;
    private EditText textFood;
    private EditText textDescription;
    private Bundle extras;
    private String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_main_form);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Request");

        textName = findViewById(R.id.curry_form_name);
        textFood = findViewById(R.id.curry_form_steps);
        textDescription = findViewById(R.id.curry_form_ingredients);

        extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        String name = extras.getString("Name");
        String food = extras.getString("Food");
        String description = extras.getString("Description");
        documentId = extras.getString("DocumentId");

        textName.setText(name);
        textFood.setText(food);
        textDescription.setText(description);
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
                if (extras != null) {
                    updateRequest();
                } else {
                    saveRequest();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveRequest() {
        String name = textName.getText().toString();
        String food = textFood.getText().toString();
        String description = textDescription.getText().toString();

        if (name.trim().isEmpty() || food.trim().isEmpty() || textDescription.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please insert name, food, or description", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference requestRef = FirebaseFirestore.getInstance()
                .collection("Request");
        Request request = new Request(name, food, description);
        requestRef.add(request);
        Toast.makeText(this, "Request added", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateRequest() {
        String name = textName.getText().toString();
        String food = textFood.getText().toString();
        String description = textDescription.getText().toString();

        if (name.trim().isEmpty() || food.trim().isEmpty() || textDescription.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please insert name, food, or ingredients", Toast.LENGTH_SHORT).show();
            return;
        }

        System.out.println("Doc ID at update : " + documentId);

        FirebaseFirestore.getInstance()
                .collection("Request")
                .document(documentId)
                .update("name", name,
                        "food", food,
                        "description", description);
        Toast.makeText(this, "Request updated", Toast.LENGTH_SHORT).show();
        finish();
    }

}
