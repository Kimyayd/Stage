package com.kimyayd.stage.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.kimyayd.stage.R;

public class ChoiceActivity extends AppCompatActivity {
    private static final String TAG = "ChoiceActivity";
    private TextView organisation,participant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        organisation = findViewById(R.id.organisation);
        participant = findViewById(R.id.participant);
        participant.setOnClickListener(v -> {
            Log.d(TAG, "onClick: navigating to register screen");
            Intent intent = new Intent(ChoiceActivity.this, Register.class);
            startActivity(intent);
        });

        organisation.setOnClickListener(v -> {
            Log.d(TAG, "onClick: navigating to register screen");
            Intent intent = new Intent(ChoiceActivity.this, CompanyRegister.class);
            startActivity(intent);
        });
    }
}