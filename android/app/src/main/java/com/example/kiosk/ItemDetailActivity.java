package com.example.kiosk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ItemDetailActivity extends AppCompatActivity {

    private ImageView itemImageView;
    private TextView itemNameTextView;
    private TextView itemPriceTextView;
    private TextView quantityTextView;
    private RadioGroup temperatureRadioGroup;
    private RadioGroup sizeRadioGroup;
    private RadioGroup cupRadioGroup;
    private Button decreaseQuantityButton;
    private Button increaseQuantityButton;
    private Button backButton;
    private Button addButton;

    private Item selectedItem;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        itemImageView = findViewById(R.id.iv_item_image);
        itemNameTextView = findViewById(R.id.tv_item_name);
        itemPriceTextView = findViewById(R.id.tv_item_price);
        quantityTextView = findViewById(R.id.tv_quantity);
        temperatureRadioGroup = findViewById(R.id.rg_temperature);
        sizeRadioGroup = findViewById(R.id.rg_size);
        cupRadioGroup = findViewById(R.id.rg_cup);
        decreaseQuantityButton = findViewById(R.id.btn_decrease_quantity);
        increaseQuantityButton = findViewById(R.id.btn_increase_quantity);
        backButton = findViewById(R.id.btn_back);
        addButton = findViewById(R.id.btn_add);

        // 선택된 아이템 정보를 받아옴
        Intent intent = getIntent();
        selectedItem = (Item) intent.getSerializableExtra("selected_item");

        if (selectedItem != null) {
            // 이미지 로딩
            Glide.with(this)
                    .load(selectedItem.getImageUrl())
                    .into(itemImageView);
            itemNameTextView.setText(selectedItem.getName());
            itemPriceTextView.setText(selectedItem.getPrice() + "원");
        }

        decreaseQuantityButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityTextView.setText(String.valueOf(quantity));
            }
        });

        increaseQuantityButton.setOnClickListener(v -> {
            quantity++;
            quantityTextView.setText(String.valueOf(quantity));
        });

        backButton.setOnClickListener(v -> finish());

        addButton.setOnClickListener(v -> {
            // 선택된 옵션을 바탕으로 아이템을 장바구니에 추가
            int selectedTemperatureId = temperatureRadioGroup.getCheckedRadioButtonId();
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            int selectedCupId = cupRadioGroup.getCheckedRadioButtonId();

            // 선택된 옵션들을 바탕으로 아이템 객체 생성 및 장바구니에 추가
            Item itemToAdd = new Item(selectedItem.getName(), selectedItem.getCategory(), selectedItem.getPrice(), selectedItem.getImageUrl());
            itemToAdd.setQuantity(quantity);

            // 여기서 선택된 옵션들을 아이템에 추가하는 로직 추가 가능

            Intent resultIntent = new Intent();
            resultIntent.putExtra("item_to_add", itemToAdd);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
