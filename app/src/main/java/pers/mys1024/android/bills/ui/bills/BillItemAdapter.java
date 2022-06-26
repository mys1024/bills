package pers.mys1024.android.bills.ui.bills;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import pers.mys1024.android.bills.R;
import pers.mys1024.android.bills.db.entity.Bill;

public class BillItemAdapter extends RecyclerView.Adapter<BillHolder> {
    private List<Bill> bills;
    private final List<BillItemAdapterListener> longClickListeners = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public synchronized void updateBills(List<Bill> bills) {
        this.bills = bills;
        notifyDataSetChanged();
    }

    public void onItemLongClick(BillItemAdapterListener listener) {
        longClickListeners.add(listener);
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.bill_item,
                parent,
                false
        );
        BillHolder holder = new BillHolder(view);
        view.setOnLongClickListener(v -> {
            for (BillItemAdapterListener l : longClickListeners) {
                l.run(holder.getBill());
            }
            return false;
        });
        return holder;
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
    private Bill bill;
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
        this.bill = bill;
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

    public Bill getBill() {
        return bill;
    }
}

interface BillItemAdapterListener {
    void run(Bill bill);
}
