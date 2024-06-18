package com.example.kiosk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InputNumberActivity extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private static final String VALID_PHONE_NUMBER = "01012345678"; // 일치하는 번호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_number);

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        }

        phoneNumberEditText = findViewById(R.id.editTextTextMultiLine);

        // 버튼 클릭 이벤트 설정
        Button button1 = findViewById(R.id.button7);
        Button button2 = findViewById(R.id.button8);
        Button button3 = findViewById(R.id.button9);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button2);
        Button button6 = findViewById(R.id.button3);
        Button button7 = findViewById(R.id.button10);
        Button button8 = findViewById(R.id.button6);
        Button button9 = findViewById(R.id.button11);
        Button button0 = findViewById(R.id.button5);
        Button buttonDelete = findViewById(R.id.button12);
        Button buttonEnter = findViewById(R.id.button13);

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            phoneNumberEditText.append(b.getText().toString());
        };

        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        button0.setOnClickListener(listener);

        buttonDelete.setOnClickListener(v -> {
            String currentText = phoneNumberEditText.getText().toString();
            if (currentText.length() > 0) {
                phoneNumberEditText.setText(currentText.substring(0, currentText.length() - 1));
            }
        });

        buttonEnter.setOnClickListener(v -> {
            String enteredNumber = phoneNumberEditText.getText().toString();
            if (VALID_PHONE_NUMBER.equals(enteredNumber)) {
                Toast.makeText(InputNumberActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InputNumberActivity.this, UsePointActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(InputNumberActivity.this, "번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
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
