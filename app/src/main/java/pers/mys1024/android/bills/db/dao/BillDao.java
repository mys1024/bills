package pers.mys1024.android.bills.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import pers.mys1024.android.bills.db.entity.Bill;

@Dao
public interface BillDao {
    @Insert
    void insert(Bill... bills);

    @Insert
    void insert(List<Bill> bills);

    @Update
    void update(Bill... bills);

    @Update
    void update(List<Bill> bills);

    @Delete
    void delete(Bill... bills);

    @Delete
    void delete(List<Bill> bills);

    @Query("SELECT * FROM Bill")
    List<Bill> getAll();

    @Query("SELECT * FROM Bill ORDER BY id DESC")
    List<Bill> getDescAll();

    @Query("SELECT * FROM Bill WHERE id=:id")
    Bill findById(Long id);

    @Query("SELECT count(*) FROM Bill")
    Long getCount();
}
