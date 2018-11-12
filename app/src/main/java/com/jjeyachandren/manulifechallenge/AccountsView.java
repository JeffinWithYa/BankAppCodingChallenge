package com.jjeyachandren.manulifechallenge;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Iterator;

// should extend ListActivity
public class AccountsView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SaveSharedPreferences SaveSharedPreference;
    String json_string_accounts;
    JSONArray jsonArray;

    AccountsAdapter accountsAdapter;
    ListView listView;

    public static String chequing_balance;
    public static String savings_balance;
    public static String tfsa_balance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_accounts_list);
        json_string_accounts = getIntent().getExtras().getString("json_data");
        listView = (ListView)findViewById(R.id.listview_accounts);
        accountsAdapter = new AccountsAdapter(this, R.layout.accounts_row_layout);

        listView.setAdapter(accountsAdapter);


        parseJSON(json_string_accounts);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                // clear shared prefs
                SaveSharedPreferences.clearUserStatus(getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(this, "You are now signed out, have a great day!",
                        Toast.LENGTH_SHORT).show();
                startActivity(intent);

                return true;

            default:

                SaveSharedPreferences.clearUserStatus(getApplicationContext());
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                Toast.makeText(this, "You are now signed out, have a great day!",
                        Toast.LENGTH_SHORT).show();

                startActivity(intent2);
                return super.onOptionsItemSelected(item);

        }
    }


    public void parseJSON(String json_string) {
        try {
            jsonArray = new JSONArray(json_string_accounts);

            String account_name, account_number, balance;

            int count = 0;
            while(count < jsonArray.length()){
                JSONObject jsonObjectAccount = jsonArray.getJSONObject(count);
                account_name = jsonObjectAccount.getString("display_name");
                account_number = jsonObjectAccount.getString("account_number");
                balance = balanceFormatter(jsonObjectAccount.getString("balance"));
                Accounts account = new Accounts(account_name, account_number, balance);

                // store the balance for each account
                if (account_name.equals("Chequing Account")) {
                    chequing_balance = balance;
                } else if (account_name.equals("Savings Account")) {
                    savings_balance = balance;
                } else if (account_name.equals("TFSA Account")) {
                    tfsa_balance = balance;
                }

                accountsAdapter.add(account);

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static String balanceFormatter(String accountBalance) {

        String formattedBalance;
        DecimalFormat formatter = new DecimalFormat("#,###.00");

        String first_character = accountBalance.substring(0,1);
        if (first_character.equals("-")) {

            double amount = Double.parseDouble(accountBalance.substring(1, accountBalance.length()));
            formattedBalance = formatter.format(amount);
            return "-" + "$" + formattedBalance;

        } else {

            double amount = Double.parseDouble(accountBalance);
            formattedBalance = formatter.format(amount);
            return "$" + formattedBalance;
        }
    }


}
