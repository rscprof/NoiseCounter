package ru.rsc_team.noisecounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;

import ru.rsc_team.noisecounter.model.Options;

public class OptionsActivity extends AppCompatActivity {

    public static final String OPTIONS = "OPTIONS";
    Options options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EditText editTextBorderNoise, editTextLength, editTextLengthGate;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


        options = (Options) getIntent().getSerializableExtra(OPTIONS);
        editTextBorderNoise = (EditText) findViewById(R.id.editTextBorderNoise);
        editTextBorderNoise.setText(String.format("%d", options.border));
        editTextLength = (EditText) findViewById(R.id.editTextLength);
        editTextLength.setText(String.format("%f", options.length));
        editTextLengthGate = (EditText) findViewById(R.id.editTextLengthGate);
        editTextLengthGate.setText(String.format("%f", options.gate));

        ((Button)findViewById(R.id.buttonOptionsCancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        ((Button) findViewById(R.id.buttonOptionsSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int border;
                    double length;
                    double gate;

                    //TODO optimisation
                    try {
                        border = NumberFormat.getIntegerInstance()
                                .parse(editTextBorderNoise.getText().toString())
                                .intValue();
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.options_bad_border_noise), Toast.LENGTH_SHORT)
                                .show();
                        throw e;
                    }

                    try {
                        length = NumberFormat.getNumberInstance()
                                .parse(editTextLength.getText().toString())
                                .doubleValue();
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.options_bad_length), Toast.LENGTH_SHORT)
                                .show();
                        throw e;
                    }

                    try {
                        gate = NumberFormat.getNumberInstance()
                                .parse(editTextLengthGate.getText().toString())
                                .doubleValue();
                    } catch (ParseException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.options_bad_gate), Toast.LENGTH_SHORT)
                                .show();
                        throw e;
                    }

                    options.border = border;
                    options.gate = gate;
                    options.length = length;

                    Intent intent = new Intent();
                    intent.putExtra(OPTIONS, options);
                    setResult(RESULT_OK, intent);
                    finish();

                } catch (ParseException e) {

                }
            }
        });



    }
}
