package com.github.sithumonline.firetwo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class RentBuyActivity extends AppCompatActivity {

    private TextView textViewRentFee;

    public RentBuyActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_buy);

        TextView textViewName = findViewById(R.id.text_view_name);
        TextView textViewAddress = findViewById(R.id.text_view_address);
        TextView textRentItems = findViewById(R.id.text_rent_items);
        textViewRentFee = findViewById(R.id.rent_fee);
        NumberPicker hoursPicker = findViewById(R.id.number_picker_rent);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String name = extras.getString("Name");
        String address = extras.getString("Address");
        String items = extras.getString("Items");
        int hourlyRental = extras.getInt("HourlyRental");

        textViewName.setText(name);
        textViewAddress.setText(address);
        textRentItems.setText(items);

        hoursPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textViewRentFee.setText("Rs. " + newVal * hourlyRental + "/=");
            }
        });

    }

}
