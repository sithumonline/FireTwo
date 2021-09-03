package com.github.sithumonline.firetwo;

import android.app.Activity;
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

public class NewRentActivity extends AppCompatActivity {
    private EditText textName;
    private EditText textAddress;
    private EditText textItems;
    private EditText textHourlyRental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_text_input);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Rent");

        textName = findViewById(R.id.curry_form_name);
        textAddress = findViewById(R.id.curry_form_steps);
        textItems = findViewById(R.id.curry_form_items);
        textHourlyRental = findViewById(R.id.curry_form_ingredients);
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

        Toast.makeText(this, name + address + items + hourlyRental, Toast.LENGTH_SHORT).show();
        System.out.println(name + address + items + hourlyRental);

        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("Rent");
        notebookRef.add(new Rent(name, address, items, hourlyRental));
        Toast.makeText(this, "Rent added", Toast.LENGTH_SHORT).show();
        finish();
    }
}
