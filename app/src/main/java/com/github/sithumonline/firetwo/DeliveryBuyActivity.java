package com.github.sithumonline.firetwo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shawnlin.numberpicker.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class DeliveryBuyActivity extends AppCompatActivity {

    private TextView textViewDeliveryFee;

    public DeliveryBuyActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_buy);

        TextView textViewName = findViewById(R.id.text_view_name);
        TextView textViewAddress = findViewById(R.id.text_view_address);
        textViewDeliveryFee = findViewById(R.id.delivery_fee);
        NumberPicker hoursPicker = findViewById(R.id.number_picker_delivery);
        ImageView imageCard = findViewById(R.id.image_delivery_buy);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String name = extras.getString("Name");
        String address = extras.getString("Address");
        int unitPrice = extras.getInt("UnitPrice");
        String imageLink = extras.getString("ImageLink");

        textViewName.setText(name);
        textViewAddress.setText(address);
        Glide.with(getApplicationContext()).load(imageLink)
                .placeholder(R.drawable.snack_two)
                .into(imageCard);

        hoursPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textViewDeliveryFee.setText("Rs. " + newVal * unitPrice + "/=");
            }
        });

    }
    
}
