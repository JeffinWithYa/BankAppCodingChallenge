package com.jjeyachandren.manulifechallenge;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private SaveSharedPreferences SaveSharedPreference;
    Button openButton;

    String list_accounts_filename = "listOfAccounts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String jsonAccountData = loadJSONFromAsset(getApplicationContext(), list_accounts_filename);

        if(SaveSharedPreference.getUserStatus(MainActivity.this).length() == 0)
        {
            // The app has not been opened before, stay at the current activity
            openButton = (Button) findViewById(R.id.openbutton);
            openButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SaveSharedPreferences.setStatus(getApplicationContext(), "Open");
                    Intent intent = new Intent(getApplicationContext(), AccountsView.class);
                    intent.putExtra("json_data", jsonAccountData);
                    startActivity(intent);

                }
            });
        }
        else
        {
            // The app has been opened before, show accounts
            Intent intent = new Intent(this, AccountsView.class);
            intent.putExtra("json_data", jsonAccountData);
            startActivity(intent);

        }
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}


