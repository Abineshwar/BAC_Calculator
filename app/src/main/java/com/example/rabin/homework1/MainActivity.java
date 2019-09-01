/*
HomeWork #1
Group#21_HW1.zip
Abinandaraj Rajendran (801081291)
Abineshwar Angamuthu Matheswaran (801075297)
 */


package com.example.rabin.homework1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    double bac1=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_name);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_main);

        final EditText weight = (EditText) findViewById(R.id.et_weight);
        final Switch tsw = (Switch) findViewById(R.id.tswitch1);
        final Button save = (Button) findViewById(R.id.b_save);
        final Button adddrink = (Button) findViewById(R.id.b_adddrink);
        final Button reset = (Button) findViewById(R.id.b_reset);
        final RadioButton radiob1 = (RadioButton) findViewById(R.id.radioButton1);
        final RadioButton radiob2 = (RadioButton) findViewById(R.id.radioButton2);
        final RadioButton radiob3 = (RadioButton) findViewById(R.id.radioButton3);
        final SeekBar seek_bar1 = (SeekBar) findViewById(R.id.seekbar1);
        final TextView tvresult = (TextView) findViewById(R.id.tv_result);
        final TextView bac = (TextView) findViewById(R.id.tv_bac);
        final TextView avalue = (TextView) findViewById(R.id.tv_value);
        final ProgressBar pgbar = (ProgressBar) findViewById(R.id.pb);

        seek_bar1.setMin(5);
        seek_bar1.setMax(25);
        seek_bar1.setProgress(5);
        avalue.setText("5");
        pgbar.setMin(0);
        pgbar.setMax(25);
        pgbar.setProgress(0);
        bac.setText("BAC Level: 0.0");
        tvresult.setTextColor(Color.GREEN);
        tvresult.setText("Your Status: You're Safe");

        seek_bar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = ((int) Math.round(progress / 5)) * 5;
                avalue.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final Double[] temp = {0.0};
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer.parseInt(weight.getText().toString());
                calculate(avalue, weight, radiob1, tsw, radiob2, radiob3, temp, bac, pgbar, save, adddrink, tvresult);
            }
        });


        tsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tsw.setText("Female");
                } else {
                    tsw.setText("Male");
                }


            }

        });


        adddrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(avalue, weight, radiob1, tsw, radiob2, radiob3, temp, bac, pgbar, save, adddrink, tvresult);
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight.setText("");
                if (tsw.isChecked()) tsw.toggle();
                radiob1.setChecked(true);
                seek_bar1.setProgress(5);
                avalue.setText("5");
                save.setEnabled(true);
                reset.setEnabled(true);
                adddrink.setEnabled(true);
                pgbar.setProgress(0);
                bac.setText("BAC Level: 0.0");
                tvresult.setTextColor(Color.GREEN);
                tvresult.setBackgroundColor(Color.WHITE);
                tvresult.setText("Your Status: You're Safe");
            }


        });


    }

    public void calculate(TextView avalue, TextView weight, RadioButton radiob1, Switch tsw, RadioButton radiob2, RadioButton radiob3, Double[] temp, TextView bac, ProgressBar pgbar, Button save, Button adddrink, TextView tvresult) {
        if (weight.getText().toString().isEmpty() || Integer.parseInt(weight.getText().toString()) < 1) {
            weight.setError("Enter the weight in lb");
        } else {
            int avalue1 = Integer.parseInt(avalue.getText().toString());
            double weight2 = Double.parseDouble(weight.getText().toString());

           if (radiob1.isChecked())
                if (tsw.isChecked()) {
                    bac1 = (1 * avalue1 * 6.24 / (weight2 * 0.55) / 100);

                } else {

                    bac1 = (1 * avalue1 * 6.24 / (weight2 * 0.68) / 100);

                }

            if (radiob2.isChecked())
                if (tsw.isChecked()) {
                    bac1 = (5 * avalue1 * 6.24 / (weight2 * 0.55) / 100);

                } else {

                    bac1 = (5 * avalue1 * 6.24 / (weight2 * 0.68) / 100);

                }

            if (radiob3.isChecked())
                if (tsw.isChecked()) {
                    bac1 = (12 * avalue1 * 6.24 / (weight2 * 0.55) / 100);

                } else {

                    bac1 = (12 * avalue1 * 6.24 / (weight2 * 0.68) / 100);

                }

            bac1 = bac1 + temp[0];
            temp[0] = bac1;
            bac.setText("BAC Level :" + String.valueOf(bac1));
            int a = (int) Math.round(bac1 * 100);
            pgbar.setProgress(a);

            if (Math.round(a) >= 25) {
                Toast t = Toast.makeText(getApplicationContext(), "No More Drinks for you", Toast.LENGTH_SHORT);
                t.show();
                save.setEnabled(false);
                adddrink.setEnabled(false);
            }

            if (bac1 <= 0.08) {
                tvresult.setTextColor(Color.WHITE);
                tvresult.setBackgroundColor(Color.GREEN);
                tvresult.setText("Your Status: You're safe");
            } else if (bac1 > 0.08 && bac1 < 0.20) {
                tvresult.setTextColor(Color.WHITE);
                tvresult.setBackgroundColor(Color.YELLOW);
                tvresult.setText("Your Status: Be Careful");
            } else if (bac1 >= 0.20) {
                tvresult.setTextColor(Color.WHITE);
                tvresult.setBackgroundColor(Color.RED);
                tvresult.setText("Your Status: Over the limit!");
            }
        }
    }

}
