package com.example.kiosk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UsePointActivity extends AppCompatActivity {

    private EditText pointInputEditText;
    private TextView userPointTextView;
    private int userPoints = 1200; // 현재 보유 포인트
    private int totalAmount = 3000; // 예시로 사용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_point);

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        pointInputEditText = findViewById(R.id.point_input_edit_text);
        userPointTextView = findViewById(R.id.user_point_text);
        Button savePointButton = findViewById(R.id.save_point_btn);
        Button usePointButton = findViewById(R.id.use_point_btn);

        savePointButton.setOnClickListener(v -> {
            Intent intent = new Intent(UsePointActivity.this, ReadingCardActivity.class);
            intent.putExtra("final_amount", totalAmount); // 적립하기에도 금액을 전달
            startActivity(intent);
        });

        usePointButton.setOnClickListener(v -> {
            String inputPoints = pointInputEditText.getText().toString();
            if (!inputPoints.isEmpty()) {
                int pointsToUse = Integer.parseInt(inputPoints);
                if (pointsToUse > userPoints) {
                    Toast.makeText(this, "사용할 포인트가 부족합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    int finalAmount = totalAmount - pointsToUse;
                    showPaymentDialog(finalAmount, pointsToUse);
                }
            } else {
                Toast.makeText(this, "사용할 포인트를 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPaymentDialog(int finalAmount, int pointsUsed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_payment_confirmation, null);
        builder.setView(dialogView);

        TextView confirmationMessage = dialogView.findViewById(R.id.confirmation_message);
        confirmationMessage.setText("결제금액: " + finalAmount + "원\n사용 포인트: " + pointsUsed + "점");

        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
        Button buttonConfirm = dialogView.findViewById(R.id.button_confirm);

        AlertDialog dialog = builder.create();

        buttonCancel.setOnClickListener(v -> dialog.dismiss());
        buttonConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(UsePointActivity.this, ReadingCardActivity.class);
            intent.putExtra("final_amount", finalAmount);
            startActivity(intent);
        });

        dialog.show();
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
