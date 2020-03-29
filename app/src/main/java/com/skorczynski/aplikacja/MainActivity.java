package com.skorczynski.aplikacja;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {
    private static final int PICK_CONTACT = 1;
    private static final int PICK_CUSTOM = 2;
    private static final String TAG = "Main";

    private Context context;

    private ArrayList<Button> buttons = new ArrayList<Button>();
    private TextView chosedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        buttons.add((Button) findViewById(R.id.btnContacts));
        buttons.add((Button) findViewById(R.id.btnCustom));
        buttons.add((Button) findViewById(R.id.btnSms));
        buttons.add((Button) findViewById(R.id.btnMap));

        chosedName = (TextView) findViewById(R.id.tvChosedName);

        for(Button but : buttons) {
            but.setOnClickListener(this);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String nameToOutput = "Chosed name: " + name;
                        chosedName.setText(nameToOutput);
                    }
                }
                break;
            case (PICK_CUSTOM):
                if (resultCode == Activity.RESULT_OK) {
                    String output = data.getDataString();
                    chosedName.setText(output);
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContacts:
                goToContacts();
                break;
            case R.id.btnSms:
                goToSms();
                break;
            case R.id.btnCustom:
                goToCustom();
                break;
            case R.id.btnMap:
                goToGps();
                break;
        }
    }

    private void goToSms() {
        Intent smsIntent = new Intent(context, SmsActivity.class);
        startActivity(smsIntent);
    }

    private void goToGps() {
        Intent gpsIntent = new Intent(context, GpsActivity.class);
        startActivity(gpsIntent);
    }

    private void goToContacts() {
        Intent i=new Intent(Intent.ACTION_PICK);
        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(i, PICK_CONTACT);
    }

    private void goToCustom() {
        Intent i=new Intent(context, CustomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("value","PRZESLANY STRING");
        i.putExtras(bundle);
        startActivityForResult(i, PICK_CUSTOM);
    }
}
