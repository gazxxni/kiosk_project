package com.example.kiosk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "KioskPrefs";
    private static final String KEY_BASKET = "ShoppingBasket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 장바구니 데이터 초기화
        clearShoppingBasket();

        Button dineInButton = findViewById(R.id.dine_in_button);
        Button takeOutButton = findViewById(R.id.take_out_button);
        ImageButton adminButton = findViewById(R.id.btn_admin);

        dineInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("orderType", "dineIn");

                startActivity(intent);
            }
        });

        takeOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("orderType", "takeOut");
                startActivity(intent);
            }
        });
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InventoryManagementActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearShoppingBasket() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_BASKET);
        editor.apply();
    }
}
