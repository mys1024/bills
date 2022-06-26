package pers.mys1024.android.bills.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import pers.mys1024.android.bills.db.dao.BillDao;
import pers.mys1024.android.bills.db.dao.TagDao;
import pers.mys1024.android.bills.db.entity.Bill;
import pers.mys1024.android.bills.db.entity.Tag;

@Database(entities = {Bill.class, Tag.class}, version = 6, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "my-database"
            ).fallbackToDestructiveMigration().build();
        }
        return sInstance;
    }

    public abstract BillDao billDao();

    public abstract TagDao tagDao();
}
