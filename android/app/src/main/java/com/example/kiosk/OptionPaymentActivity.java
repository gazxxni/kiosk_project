package com.example.kiosk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.text.NumberFormat;

public class OptionPaymentActivity extends AppCompatActivity {
    private TextView paymentAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_payment);

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        paymentAmountTextView = findViewById(R.id.payment_amount_text);
        int totalAmount = getIntent().getIntExtra("total_amount", 0);
        paymentAmountTextView.setText(NumberFormat.getInstance().format(totalAmount) + "원");

        // 버튼 클릭 이벤트 설정
        ImageButton btnCardPayment = findViewById(R.id.btn_card_payment);
        ImageButton btnPoint = findViewById(R.id.btn_point);
        ImageButton btnCoupon = findViewById(R.id.btn_coupon);

        btnCardPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionPaymentActivity.this, ReadingCardActivity.class);
                intent.putExtra("final_amount", totalAmount);
                startActivity(intent);
            }
        });

        btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionPaymentActivity.this, InputNumberActivity.class);
                intent.putExtra("final_amount", totalAmount);
                startActivity(intent);
            }
        });

        btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionPaymentActivity.this, ScanBarcodeActivity.class);
                intent.putExtra("total_amount", totalAmount);
                startActivity(intent);
            }
        });
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
