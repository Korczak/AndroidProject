package com.skorczynski.aplikacja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GpsActivity extends AppCompatActivity {

    private EditText etWidth;
    private EditText etLength;
    private Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        etWidth = (EditText) findViewById(R.id.etGpsWidth);
        etLength = (EditText) findViewById(R.id.etGpsLength);
        btnAccept = (Button) findViewById(R.id.btnGpsAccept);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + etWidth.getText().toString() + "&daddr=" + etLength.getText().toString()));
                startActivity(intent);
            }
        });
    }
}
