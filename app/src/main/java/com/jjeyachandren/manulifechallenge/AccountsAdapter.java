package com.jjeyachandren.manulifechallenge;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AccountsAdapter extends ArrayAdapter {

    List list = new ArrayList();
    private String json_string;
    private String trans_screen_balance;


    public AccountsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(Accounts object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View row_view;
        row_view = convertView;

        AccountHolder accountHolder;

        if (row_view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row_view = layoutInflater.inflate(R.layout.accounts_row_layout, parent, false);
            accountHolder = new AccountHolder();
            accountHolder.txView_account_name = (TextView) row_view.findViewById(R.id.txview_account_name);
            accountHolder.txView_account_number = (TextView) row_view.findViewById(R.id.txview_account_number);
            accountHolder.txView_balance = (TextView) row_view.findViewById(R.id.txview_balance);

            row_view.setTag(accountHolder);

        } else {
            accountHolder = (AccountHolder) row_view.getTag();
        }

        final Accounts account = (Accounts) this.getItem(position);
        accountHolder.txView_account_name.setText(account.getName());
        accountHolder.txView_account_number.setText(account.getAccountNumber());
        accountHolder.txView_balance.setText(account.getBalance());



        row_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), account.getName(), Toast.LENGTH_SHORT).show();
                if (account.getName().equals("Chequing Account")){
                    //TODO: parse chequing transactions and pass as extra to intent
                    json_string = MainActivity.loadJSONFromAsset(getContext(), "chequingAccount");
                    trans_screen_balance = "chequing";
                } else if (account.getName().equals("Savings Account")) {
                    json_string = MainActivity.loadJSONFromAsset(getContext(), "savingsAccount");
                    trans_screen_balance = "savings";
                } else if (account.getName().equals("TFSA Account")) {
                    json_string = MainActivity.loadJSONFromAsset(getContext(), "TfsaAccount");
                    trans_screen_balance = "tfsa";
                }

                Intent intent = new Intent(getContext(), TransactionsView.class);
                intent.putExtra("json_transaction_data", json_string);
                intent.putExtra("transaction_screen_balance", trans_screen_balance);
                getContext().startActivity(intent);
            }
        });

        return row_view;
    }

    static class AccountHolder {
        TextView txView_account_name, txView_account_number, txView_balance;
    }



}
