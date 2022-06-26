package pers.mys1024.android.bills.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import pers.mys1024.android.bills.db.entity.Tag;

@Dao
public interface TagDao {
    @Insert
    void insert(Tag... tags);

    @Insert
    void insert(List<Tag> tags);

    @Update
    void update(Tag... tags);

    @Update
    void update(List<Tag> tags);

    @Delete
    void delete(Tag... tags);

    @Delete
    void delete(List<Tag> tags);

    @Query("SELECT * FROM Tag")
    List<Tag> getAll();

    @Query("SELECT * FROM Tag ORDER BY id DESC")
    List<Tag> getDescAll();

    @Query("SELECT * FROM Tag WHERE id=:id")
    Tag findById(Long id);

    @Query("SELECT count(*) FROM Tag")
    Long getCount();
}
