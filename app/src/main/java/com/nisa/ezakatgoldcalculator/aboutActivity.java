package com.nisa.ezakatgoldcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class aboutActivity extends AppCompatActivity {

    TextView tvGithub;
    TextView tvYoutube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbarAbout);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(v -> finish());

        // GitHub Link
        tvGithub = findViewById(R.id.tvGithub);

        tvGithub.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/anisaasri17/ezakat-gold-calculator.git")
            );
            startActivity(intent);
        });

        // YouTube Link
        tvYoutube = findViewById(R.id.tvYoutube);

        tvYoutube.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://youtube.com/")
            );
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}