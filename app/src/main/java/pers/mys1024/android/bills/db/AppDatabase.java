package pers.mys1024.android.bills.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import pers.mys1024.android.bills.db.dao.BillDao;
import pers.mys1024.android.bills.db.entity.Bill;

@Database(entities = {Bill.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "my-database"
            ).fallbackToDestructiveMigration().build();
        }
        return sInstance;
    }

    @SuppressWarnings("WeakerAccess")
    public abstract BillDao billDao();
}
