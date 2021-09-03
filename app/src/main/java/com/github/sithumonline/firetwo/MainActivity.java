package com.github.sithumonline.firetwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nexter_main);

        CardView deliveryCard = findViewById(R.id.main_delivery_card);
        deliveryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeliveryMainActivity.class));
            }
        });

        CardView rentCard = findViewById(R.id.main_rent_card);
        rentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RentMainActivity.class));
            }
        });
    }

}