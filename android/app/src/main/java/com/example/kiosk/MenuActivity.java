package com.example.kiosk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCoffee;
    private RecyclerView recyclerViewDrink;
    private RecyclerView recyclerViewDessert;
    private ItemAdapter coffeeAdapter;
    private ItemAdapter drinkAdapter;
    private ItemAdapter dessertAdapter;
    private List<Item> coffeeList;
    private List<Item> drinkList;
    private List<Item> dessertList;
    private List<Item> shoppingBasket;
    private TextView totalAmountTextView;
    private static final String PREFS_NAME = "KioskPrefs";
    private static final String KEY_BASKET = "ShoppingBasket";
    private String orderType;
    private static final String TAG = "MenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_menu);

        orderType = getIntent().getStringExtra("orderType");

        totalAmountTextView = findViewById(R.id.total_amount_text);
        shoppingBasket = new ArrayList<>();

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        Button btnCoffee = findViewById(R.id.btn_coffee);
        Button btnDrink = findViewById(R.id.btn_drink);
        Button btnDessert = findViewById(R.id.btn_dessert);
        Button proceedToBasketButton = findViewById(R.id.proceed_to_basket_button);

        recyclerViewCoffee = findViewById(R.id.recycler_view_coffee);
        recyclerViewDrink = findViewById(R.id.recycler_view_drink);
        recyclerViewDessert = findViewById(R.id.recycler_view_dessert);

        recyclerViewCoffee.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewDrink.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewDessert.setLayoutManager(new GridLayoutManager(this, 3));

        coffeeList = new ArrayList<>();
        drinkList = new ArrayList<>();
        dessertList = new ArrayList<>();

        coffeeAdapter = new ItemAdapter(this, coffeeList, item -> showItemDetailDialog(item));
        drinkAdapter = new ItemAdapter(this, drinkList, item -> showItemDetailDialog(item));
        dessertAdapter = new ItemAdapter(this, dessertList, item -> showItemDetailDialog(item));

        recyclerViewCoffee.setAdapter(coffeeAdapter);
        recyclerViewDrink.setAdapter(drinkAdapter);
        recyclerViewDessert.setAdapter(dessertAdapter);

        btnCoffee.setOnClickListener(v -> {
            showRecyclerView(R.id.recycler_view_coffee);
            updateButtonStyles(btnCoffee, btnDrink, btnDessert);
        });
        btnDrink.setOnClickListener(v -> {
            showRecyclerView(R.id.recycler_view_drink);
            updateButtonStyles(btnDrink, btnCoffee, btnDessert);
        });
        btnDessert.setOnClickListener(v -> {
            showRecyclerView(R.id.recycler_view_dessert);
            updateButtonStyles(btnDessert, btnCoffee, btnDrink);
        });

        proceedToBasketButton.setOnClickListener(v -> {
            saveShoppingBasket(); // 장바구니 데이터 저장
            Intent intent = new Intent(MenuActivity.this, ShoppingBasketActivity.class);
            startActivity(intent);
        });

        showRecyclerView(R.id.recycler_view_coffee);
        updateButtonStyles(btnCoffee, btnDrink, btnDessert);

        fetchData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadShoppingBasket();
        updateTotalAmount();
    }

    private void fetchData() {
        String url = "http://10.0.2.2/get_items.php"; // 에뮬레이터에서 로컬 서버에 접근할 때 사용

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d(TAG, "Response: " + response.toString()); // 응답 로그 추가
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String category = jsonObject.getString("category");
                                int price = jsonObject.getInt("price");
                                String imageUrl = jsonObject.getString("image_url");

                                // 기본 URL을 추가하여 상대 경로를 절대 경로로 변환
                                String baseUrl = "http://10.0.2.2/";
                                String fullImageUrl = baseUrl + imageUrl;

                                Log.d(TAG, "Item: " + name + ", " + category + ", " + price + ", " + fullImageUrl); // 아이템 로그 추가

                                Item item = new Item(name, category, price, fullImageUrl);

                                switch (category) {
                                    case "coffee":
                                        coffeeList.add(item);
                                        break;
                                    case "drink":
                                        drinkList.add(item);
                                        break;
                                    case "dessert":
                                        dessertList.add(item);
                                        break;
                                }
                            }
                            coffeeAdapter.notifyDataSetChanged();
                            drinkAdapter.notifyDataSetChanged();
                            dessertAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "JSON parsing error: " + e.getMessage()); // JSON 파싱 오류 로그 추가
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Volley error: " + error.toString()); // Volley 오류 로그 추가
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }






    private void showItemDetailDialog(Item item) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_detail);

        // 다이얼로그 크기 조절 추가
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        ImageView itemImageView = dialog.findViewById(R.id.iv_item_image);
        TextView itemNameTextView = dialog.findViewById(R.id.tv_item_name);
        TextView itemPriceTextView = dialog.findViewById(R.id.tv_item_price);
        TextView quantityTextView = dialog.findViewById(R.id.tv_quantity);
        TextView totalPriceTextView = dialog.findViewById(R.id.tv_total_price);
        TextView temperatureTextView = dialog.findViewById(R.id.temperature_text);
        TextView sizeTextView = dialog.findViewById(R.id.size_text);
        TextView cupTextView = dialog.findViewById(R.id.cup_text);
        RadioGroup temperatureRadioGroup = dialog.findViewById(R.id.rg_temperature);
        RadioGroup sizeRadioGroup = dialog.findViewById(R.id.rg_size);
        RadioGroup cupRadioGroup = dialog.findViewById(R.id.rg_cup);
        Button decreaseQuantityButton = dialog.findViewById(R.id.btn_decrease_quantity);
        Button increaseQuantityButton = dialog.findViewById(R.id.btn_increase_quantity);
        Button backButton = dialog.findViewById(R.id.btn_back);
        Button addButton = dialog.findViewById(R.id.btn_add);

        // 이미지 로딩
        Glide.with(this)
                .load(item.getImageUrl())
                .override(100, 100)  // 여기서 이미지의 크기를 오버라이드합니다
                .into(itemImageView);

        itemNameTextView.setText(item.getName());
        itemPriceTextView.setText(item.getPrice() + "원");

        int itemPrice = item.getPrice();
        totalPriceTextView.setText("총액: " + itemPrice + "원");

        // 수량 조절
        final int[] quantity = {1};
        decreaseQuantityButton.setOnClickListener(v -> {
            if (quantity[0] > 1) {
                quantity[0]--;
                quantityTextView.setText(String.valueOf(quantity[0]));
                totalPriceTextView.setText("총액: " + (itemPrice * quantity[0]) + "원");
            }
        });

        increaseQuantityButton.setOnClickListener(v -> {
            quantity[0]++;
            quantityTextView.setText(String.valueOf(quantity[0]));
            totalPriceTextView.setText("총액: " + (itemPrice * quantity[0]) + "원");
        });

        // 라디오 버튼 초기 선택 및 클릭 시 색상 변경
        setupRadioButtonBehavior(temperatureRadioGroup, dialog);
        setupRadioButtonBehavior(sizeRadioGroup, dialog);
        setupRadioButtonBehavior(cupRadioGroup, dialog);

        RadioButton cupRadioButton = dialog.findViewById(R.id.rb_mug);
        if ("takeOut".equals(orderType)) {
            cupRadioButton.setText("일회용");
            cupRadioButton.setChecked(true);
        } else {
            cupRadioButton.setText("매장용");
            cupRadioButton.setChecked(true);
        }

        // 디저트 아이템일 경우 라디오 버튼들을 숨김
        if (isDessertItem(item)) {
            temperatureTextView.setVisibility(View.GONE);
            sizeTextView.setVisibility(View.GONE);
            cupTextView.setVisibility(View.GONE);
            temperatureRadioGroup.setVisibility(View.GONE);
            sizeRadioGroup.setVisibility(View.GONE);
            cupRadioGroup.setVisibility(View.GONE);
        } else {
            // 라디오 버튼 초기 선택 및 클릭 시 색상 변경
            setupRadioButtonBehavior(temperatureRadioGroup, dialog);
            setupRadioButtonBehavior(sizeRadioGroup, dialog);
            setupRadioButtonBehavior(cupRadioGroup, dialog);
        }

        backButton.setOnClickListener(v -> dialog.dismiss());

        addButton.setOnClickListener(v -> {
            item.setQuantity(quantity[0]);
            addItemToBasket(item);  // 수정된 부분: addItemToBasket 메서드 사용
            dialog.dismiss();
        });
        dialog.show();
    }


    private boolean isDessertItem(Item item) {
        return "dessert".equals(item.getCategory());
    }

    private void setupRadioButtonBehavior(RadioGroup radioGroup, Dialog dialog) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setOnClickListener(v -> {
                for (int j = 0; j < radioGroup.getChildCount(); j++) {
                    RadioButton rb = (RadioButton) radioGroup.getChildAt(j);
                    rb.setTextColor(dialog.getContext().getResources().getColor(android.R.color.black));
                }
                radioButton.setTextColor(dialog.getContext().getResources().getColor(android.R.color.holo_red_dark));
            });
            // 기본 선택된 라디오 버튼의 색상 설정
            if (radioButton.isChecked()) {
                radioButton.setTextColor(dialog.getContext().getResources().getColor(android.R.color.holo_red_dark));
            }
        }
    }

    private void addItemToBasket(Item newItem) {
        boolean itemExists = false;
        for (Item item : shoppingBasket) {
            if (item.equals(newItem)) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            shoppingBasket.add(newItem);
        }
        saveShoppingBasket(); // 장바구니 데이터 저장
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

    private void showRecyclerView(int recyclerViewId) {
        recyclerViewCoffee.setVisibility(View.GONE);
        recyclerViewDrink.setVisibility(View.GONE);
        recyclerViewDessert.setVisibility(View.GONE);

        findViewById(recyclerViewId).setVisibility(View.VISIBLE);
    }

    private void updateButtonStyles(Button selectedButton, Button... otherButtons) {
        selectedButton.setBackgroundResource(R.drawable.toolbar_button_selected_background);
        selectedButton.setTextColor(getResources().getColor(android.R.color.black));

        for (Button button : otherButtons) {
            button.setBackgroundResource(R.drawable.toolbar_button_background);
            button.setTextColor(getResources().getColor(android.R.color.darker_gray)); // 선택되지 않은 버튼의 텍스트 색상
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

    private void saveShoppingBasket() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(shoppingBasket);
        editor.putString(KEY_BASKET, json);
        editor.apply();
    }
}
