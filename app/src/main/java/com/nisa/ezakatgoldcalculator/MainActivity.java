package com.nisa.ezakatgoldcalculator;

import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etGoldValue;

    RadioButton rbKeep, rbWear;

    TextView tvTotalGoldValue, tvUruf, tvZakatPayable, tvTotalZakat;

    Button btnCalculate, btnReset;
    ScrollView scrollViewMain;
    View cardResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
        // CONNECT XML
        etWeight = findViewById(R.id.etWeight);
        etGoldValue = findViewById(R.id.etGoldValue);

        rbKeep = findViewById(R.id.rbKeep);
        rbWear = findViewById(R.id.rbWear);

        rbKeep.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                rbWear.setChecked(false);
            }

        });

        rbWear.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                rbKeep.setChecked(false);
            }

        });

        tvTotalGoldValue = findViewById(R.id.tvTotalGoldValue);
        tvUruf = findViewById(R.id.tvUruf);
        tvZakatPayable = findViewById(R.id.tvZakatPayable);
        tvTotalZakat = findViewById(R.id.tvTotalZakat);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        scrollViewMain = findViewById(R.id.scrollViewMain);
        cardResult = findViewById(R.id.cardResult);

        // CALCULATE
        btnCalculate.setOnClickListener(v -> {

            try {
                if(etWeight.getText().toString().trim().isEmpty()){
                    etWeight.setError("Please enter gold weight");
                    etWeight.requestFocus();
                    return;
                }

                if(etGoldValue.getText().toString().trim().isEmpty()){
                    etGoldValue.setError("Please enter gold value");
                    etGoldValue.requestFocus();
                    return;
                }

                if(!rbKeep.isChecked() && !rbWear.isChecked()){

                    Toast.makeText(this, "Please select a gold type", Toast.LENGTH_SHORT).show();

                    return;
                }
                double weight = Double.parseDouble(etWeight.getText().toString());

                double goldValue = Double.parseDouble(etGoldValue.getText().toString());

                // TOTAL GOLD VALUE

                double totalGoldValue = weight * goldValue;

                // URUF

                double uruf;

                if (rbKeep.isChecked()) {

                    uruf = 85;

                } else {

                    uruf = 200;
                }

                // WEIGHT AFTER URUF

                double weightAfterUruf = weight - uruf;

                // PAYABLE WEIGHT (cannot be negative)

                double payableWeight = weightAfterUruf;

                if (payableWeight < 0) {

                    payableWeight = 0;
                }

                // ZAKAT PAYABLE

                double zakatPayable = payableWeight * goldValue;

                // TOTAL ZAKAT

                double totalZakat = zakatPayable * 0.025;

                // DISPLAY RESULT

                tvTotalGoldValue.setText("Total Gold Value: RM " + String.format("%.2f", totalGoldValue));

                tvUruf.setText("Gold Weight After Uruf: " + String.format("%.2f", weightAfterUruf) + "g");

                tvZakatPayable.setText("Zakat Payable: RM " + String.format("%.2f", zakatPayable));

                tvTotalZakat.setText("Total Zakat: RM " + String.format("%.2f", totalZakat));
                cardResult.setVisibility(View.VISIBLE);

                cardResult.setAlpha(0f);
                cardResult.setTranslationY(80f);

                cardResult.animate().alpha(1f).translationY(0f).setDuration(600).start();

                cardResult.post(() -> scrollViewMain.smoothScrollTo(0, cardResult.getTop()));

                Toast.makeText(this, "Calculation completed successfully.", Toast.LENGTH_SHORT).show();

            }

            catch (NumberFormatException e) {

                Toast.makeText(this, "Please enter all values correctly", Toast.LENGTH_SHORT).show();
            }

        });

        // RESET

        btnReset.setOnClickListener(v -> {

            etWeight.setText("");
            etGoldValue.setText("");

            rbKeep.setChecked(false);
            rbWear.setChecked(false);

            tvTotalGoldValue.setText("Total Gold Value: RM 0.00");

            tvUruf.setText("Gold Weight After Uruf: 0g");

            tvZakatPayable.setText("Zakat Payable: RM 0.00");

            tvTotalZakat.setText("Total Zakat: RM 0.00");

            cardResult.setVisibility(View.GONE);

            Toast.makeText(this, "Input Reset", Toast.LENGTH_SHORT).show();

        });

    }

    // MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    // MENU CLICK
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // SHARE

        if (item.getItemId() == R.id.menuShare) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(Intent.EXTRA_TEXT,"EZakat Gold Calculator App\n\nDownload here:\nhttps://github.com/anisaasri17/ezakat-gold-calculator.git");

            startActivity(Intent.createChooser(shareIntent, "Share via"));

            return true;
        }

        // ABOUT

        if (item.getItemId() == R.id.menuAbout) {

            Intent intent = new Intent(this, aboutActivity.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}