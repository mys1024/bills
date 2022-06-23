package pers.mys1024.android.bills.db;

import androidx.annotation.NonNull;

import java.util.Date;

public class Bill {
    private String tag;
    private Date date;
    private Double money;
    private Boolean in;

    public Bill() {}

    public Bill(String tag, Date date, Double money, Boolean in) {
        this.tag = tag;
        this.date = date;
        this.money = money;
        this.in = in;
    }

    @NonNull
    @Override
    public String toString() {
        return "Bill{" +
                "tag='" + tag + '\'' +
                ", date=" + date +
                ", money=" + money +
                ", in=" + in +
                '}';
    }

    public Boolean getIn() {
        return in;
    }

    public void setIn(Boolean in) {
        this.in = in;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
