package com.example.hecco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ThankyouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
    }

    public void onHomeClick(View view) {
        Intent intent = new Intent(ThankyouActivity.this, MainActivity.class);
        startActivity(intent);
    }
}