package pers.mys1024.android.bills.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tag {
    @PrimaryKey(autoGenerate = true)
    private Long tid;        // 账单号
    private String name;    // tag 名
    private Boolean in;     // 是否为“收入” tag

    public Tag(Long tid, String name, Boolean in) {
        this.tid = tid;
        this.name = name;
        this.in = in;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIn() {
        return in;
    }

    public void setIn(Boolean in) {
        this.in = in;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tag{" +
                "id=" + tid +
                ", name='" + name + '\'' +
                ", in=" + in +
                '}';
    }
}
