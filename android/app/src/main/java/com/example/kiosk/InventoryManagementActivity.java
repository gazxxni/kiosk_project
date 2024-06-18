package com.example.kiosk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InventoryManagementActivity extends AppCompatActivity {

    private Button btnInventoryManagement;
    private Button btnPromotions;
    private Button btnSales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 기본 타이틀 숨기기
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        // 툴바의 뒤로가기 버튼 클릭 리스너 설정
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnInventoryManagement = findViewById(R.id.btn_inventory_management);
        btnPromotions = findViewById(R.id.btn_promotions);
        btnSales = findViewById(R.id.btn_sales);

        btnInventoryManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new InventoryManagementFragment());
                updateButtonStyles(btnInventoryManagement);
            }
        });

        btnPromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new PromotionManagementFragment());
                updateButtonStyles(btnPromotions);
            }
        });

        btnSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SalesManagementFragment());
                updateButtonStyles(btnSales);
            }
        });

        // 초기 프래그먼트 설정
        replaceFragment(new InventoryManagementFragment());
        updateButtonStyles(btnInventoryManagement);  // 초기 버튼 스타일 설정
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void updateButtonStyles(Button selectedButton) {
        btnInventoryManagement.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnPromotions.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnSales.setTextColor(getResources().getColor(android.R.color.darker_gray));

        selectedButton.setTextColor(Color.BLACK);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish(); // 액티비티 종료
    }
}
