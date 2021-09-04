package com.github.sithumonline.firetwo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RentBuyActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewAddress;
    private Bundle extras;

    public RentBuyActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_buy);

        textViewName = findViewById(R.id.text_view_name);
        textViewAddress = findViewById(R.id.text_view_address);

        extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String name = extras.getString("Name");
        String address = extras.getString("Address");

        textViewName.setText(name);
        textViewAddress.setText(address);

    }
}
