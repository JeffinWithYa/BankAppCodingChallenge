package com.jjeyachandren.manulifechallenge;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TransactionsView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private String json_transaction_data;
    JSONArray jsonArray;
    JSONArray jsonArrayActivity;
    //JSONObject jsonObjectActivity;
    String account_selected;
    ListView listView;
    TransactionsAdapter transactionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_account_transactions);
        json_transaction_data = getIntent().getExtras().getString("json_transaction_data");
        listView = (ListView) findViewById(R.id.listview_transactions);
        transactionsAdapter = new TransactionsAdapter(this, R.layout.transaction_row_layout);
        listView.setAdapter(transactionsAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        TextView textView_balance = (TextView) findViewById(R.id.txView_trans_balance);

        account_selected = getIntent().getExtras().getString("transaction_screen_balance");

        if (account_selected.equals("chequing")) {
            textView_balance.setText(AccountsView.chequing_balance);
        } else if (account_selected.equals("savings")) {
            textView_balance.setText(AccountsView.savings_balance);
        } else if (account_selected.equals("tfsa")) {
            textView_balance.setText(AccountsView.tfsa_balance);
        }

        parseJSON(json_transaction_data);
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

            case android.R.id.home:
                finish();
                return true;

            default:

            return super.onOptionsItemSelected(item);

        }
    }

    public void parseJSON(String json_string) {
        try {
            jsonArray = new JSONArray(json_transaction_data);

            String trans_date, trans_descrip, trans_deposit, trans_withdrawl, trans_balance, trans_uuid;

            int count = 0;
            while(count < jsonArray.length()){
                JSONObject jsonObject = jsonArray.getJSONObject(count);

                trans_date = jsonObject.getString("date");
                jsonArrayActivity = jsonObject.getJSONArray("activity");

                int count_activity = 0;
                while (count_activity < jsonArrayActivity.length()) {
                    JSONObject jsonObject_transactivity = jsonArrayActivity.getJSONObject(count_activity);
                    trans_descrip = jsonObject_transactivity.getString("description");
                    trans_balance = AccountsView.balanceFormatter(jsonObject_transactivity.getString("balance"));
                    trans_uuid = jsonObject_transactivity.getString("transaction_uid");

                    if (jsonObject_transactivity.has("withdrawal_amount")) {
                        trans_withdrawl = "-" +
                                AccountsView.balanceFormatter
                                        (jsonObject_transactivity.getString("withdrawal_amount"));
                    } else {
                        trans_withdrawl = "n/a";
                    }

                    if (jsonObject_transactivity.has("deposit_amount")) {
                        trans_deposit = AccountsView.balanceFormatter(jsonObject_transactivity.getString("deposit_amount"));
                    } else {
                        trans_deposit = "n/a";
                    }

                    Transactions transaction = new
                            Transactions(trans_date, trans_descrip, trans_deposit,
                            trans_withdrawl, trans_balance, trans_uuid);

                    transactionsAdapter.add(transaction);

                    count_activity++;
                }

                count++;
            }

        } catch (JSONException e) {
            Toast.makeText(this, "JSON exception has been thrown!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}
