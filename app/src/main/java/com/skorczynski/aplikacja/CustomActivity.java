package com.skorczynski.aplikacja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        Bundle bundle = getIntent().getExtras();
        String value = "";
        if (bundle != null) {
            value = bundle.getString("value");
        }

        Intent data = new Intent();
        String text = "Result to be returned.... GOT from main: " + value;
        data.setData(Uri.parse(text));
        setResult(RESULT_OK, data);
        finish();
    }
}
