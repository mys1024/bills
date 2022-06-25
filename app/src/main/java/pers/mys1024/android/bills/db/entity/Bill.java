package pers.mys1024.android.bills.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private Long id;        // 账单号
    private String tag;     // 账单分类
    private Date date;      // 账单日期
    private Double money;   // 账单金额
    private Boolean in;     // 是否为收入

    public Bill(Long id, String tag, Date date, Double money, Boolean in) {
        this.id = id;
        this.tag = tag;
        this.date = date;
        this.money = money;
        this.in = in;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", date=" + date +
                ", money=" + money +
                ", in=" + in +
                '}';
    }
}
