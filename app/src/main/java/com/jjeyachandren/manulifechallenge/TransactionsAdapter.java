package com.jjeyachandren.manulifechallenge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsAdapter extends ArrayAdapter {

    List list = new ArrayList();
    private String json_string;

    public TransactionsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row_view;
        row_view = convertView;

        TransactionHolder transactionHolder;

        if (row_view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row_view = layoutInflater.inflate(R.layout.transaction_row_layout, parent, false);
            transactionHolder = new TransactionHolder();
            transactionHolder.txView_transaction_date = (TextView) row_view.findViewById(R.id.txview_date);
            transactionHolder.txView_transaction_description = (TextView) row_view.findViewById(R.id.txview_trans_descrip);
            transactionHolder.txView_transaction_amount = (TextView) row_view.findViewById(R.id.tx_view_trans_amount);

            row_view.setTag(transactionHolder);

        } else {
            transactionHolder = (TransactionHolder) row_view.getTag();
        }

        final Transactions transactions = (Transactions) this.getItem(position);
        transactionHolder.txView_transaction_description.setText(transactions.getDescription());
        transactionHolder.txView_transaction_date.setText(dateFormatter(transactions.getDate()));

        if (transactions.getWithdrawl_amount() != "n/a") {
            transactionHolder.txView_transaction_amount.setTextColor(Color.BLACK);
            transactionHolder.txView_transaction_amount.setText(transactions.getWithdrawl_amount());
        } else {
            transactionHolder.txView_transaction_amount.setTextColor(Color.parseColor("#228B22"));
            transactionHolder.txView_transaction_amount.setText(transactions.getDeposit_amount());
        }

        return row_view;

    }

    static class TransactionHolder {
        TextView txView_transaction_date, txView_transaction_description, txView_transaction_amount;
    }

    public static String dateFormatter(String trans_date) {

        String formattedDate = null;
        //DecimalFormat formatter = new DecimalFormat("#,###.00");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterOut = new SimpleDateFormat("EEE dd MMM yyyy");

        try {
            Date date = formatter.parse(trans_date);
            formattedDate = formatterOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;


    }


}
