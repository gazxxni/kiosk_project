package com.example.kiosk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.NumberFormat;

public class ScanBarcodeActivity extends AppCompatActivity {

    private TextView paymentAmountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_barcode);

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        paymentAmountTextView = findViewById(R.id.barcode_amount_text);

        // 전달된 결제 금액을 받아서 표시
        int finalAmount = getIntent().getIntExtra("total_amount", 0);
        paymentAmountTextView.setText(NumberFormat.getInstance().format(finalAmount) + "원");
    }
}
