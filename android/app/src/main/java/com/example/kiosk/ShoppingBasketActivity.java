package com.example.kiosk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.NumberFormat;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBasket;
    private BasketAdapter basketAdapter;
    private List<Item> shoppingBasket;
    private TextView totalAmountTextView;
    private Button proceedToPaymentButton;

    private static final String PREFS_NAME = "KioskPrefs";
    private static final String KEY_BASKET = "ShoppingBasket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_basket);

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        recyclerViewBasket = findViewById(R.id.recycler_view_basket);
        totalAmountTextView = findViewById(R.id.total_price_text);
        proceedToPaymentButton = findViewById(R.id.proceed_to_payment_button);

        recyclerViewBasket.setLayoutManager(new LinearLayoutManager(this));

        loadShoppingBasket();

        basketAdapter = new BasketAdapter(shoppingBasket, this::removeItemFromBasket, this::onQuantityChange);
        recyclerViewBasket.setAdapter(basketAdapter);

        proceedToPaymentButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingBasketActivity.this, OptionPaymentActivity.class);
            intent.putExtra("total_amount", getTotalAmount());
            startActivity(intent);
        });

        updateTotalAmount();
    }

    private void removeItemFromBasket(int position) {
        shoppingBasket.remove(position);
        basketAdapter.notifyItemRemoved(position);
        updateTotalAmount();
        saveShoppingBasket(); // 장바구니 데이터 저장
    }
    private void onQuantityChange(Item item) {
        saveShoppingBasket();
        updateTotalAmount();
    }
    private void updateTotalAmount() {
        int totalAmount = 0;
        for (Item item : shoppingBasket) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        totalAmountTextView.setText(numberFormat.format(totalAmount) + "원");
    }
    private int getTotalAmount() {
        int totalAmount = 0;
        for (Item item : shoppingBasket) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        return totalAmount;
    }
    private void saveShoppingBasket() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(shoppingBasket);
        editor.putString(KEY_BASKET, json);
        editor.apply();
    }

    private void loadShoppingBasket() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_BASKET, null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        shoppingBasket = gson.fromJson(json, type);

        if (shoppingBasket == null) {
            shoppingBasket = new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
