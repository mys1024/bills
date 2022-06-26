package pers.mys1024.android.bills.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tag {
    @PrimaryKey(autoGenerate = true)
    private Long id;        // 账单号
    private String name;    // tag 名
    private Boolean in;     // 是否为“收入” tag

    public Tag(Long id, String name, Boolean in) {
        this.id = id;
        this.name = name;
        this.in = in;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", in=" + in +
                '}';
    }
}
