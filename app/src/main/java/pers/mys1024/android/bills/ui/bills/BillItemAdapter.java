package pers.mys1024.android.bills.ui.bills;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import pers.mys1024.android.bills.R;
import pers.mys1024.android.bills.db.Bill;

public class BillItemAdapter extends RecyclerView.Adapter<BillHolder> {
    private final Bill[] bills = new Bill[]{
            new Bill("午餐", new Date(), 12.52, false),
            new Bill("晚餐", new Date(), 12.0001, false),
            new Bill("红包", new Date(), 22.56775, true),
    };

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.bill_item,
                parent,
                false
        );
        return new BillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, int position) {
        holder.setBill(bills[position]);
    }

    @Override
    public int getItemCount() {
        return bills.length;
    }
}

class BillHolder extends RecyclerView.ViewHolder {
    private final TextView tvTag;
    private final TextView tvDate;
    private final TextView tvMoney;
    private final SimpleDateFormat dateFormatter;


    public BillHolder(@NonNull View itemView) {
        super(itemView);
        this.tvTag = itemView.findViewById(R.id.tv_tag);
        this.tvDate = itemView.findViewById(R.id.tv_date);
        this.tvMoney = itemView.findViewById(R.id.tv_money);
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    public void setBill(Bill bill) {
        this.tvTag.setText(bill.getTag());
        this.tvDate.setText(this.dateFormatter.format(bill.getDate()));
        this.tvMoney.setText(String.format(
                Locale.CHINA,
                "%c¥%.2f",
                bill.getIn() ? '+' : '-',
                bill.getMoney()
        ));
        this.tvMoney.setTextColor(
                bill.getIn() ? Color.rgb(50, 200, 50) : Color.rgb(225, 50, 50)
        );
    }
}
