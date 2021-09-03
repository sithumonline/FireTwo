package com.github.sithumonline.firetwo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RentMainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_main);

        FloatingActionButton buttonAddDelivery = findViewById(R.id.button_add_rent);
        buttonAddDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RentMainActivity.this, NewRentActivity.class));
            }
        });
    }
}
