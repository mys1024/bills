package pers.mys1024.android.bills.ui.bills;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import pers.mys1024.android.bills.R;
import pers.mys1024.android.bills.db.entity.Bill;

public class BillItemAdapter extends RecyclerView.Adapter<BillHolder> {
    private List<Bill> bills;

    public synchronized void updateBills(List<Bill> bills) {
        Log.i("db", bills.toString());
        this.bills = bills;
        notifyItemRangeChanged(0, bills.size());
    }

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
        holder.setBill(bills.get(position));
    }

    @Override
    public int getItemCount() {
        return (bills == null) ? 0 : bills.size();
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
