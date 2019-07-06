package ua.i.pl.afs.mypursaches.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ua.i.pl.afs.mypursaches.db.PursacheDao;
import ua.i.pl.afs.mypursaches.models.Pursache;

@Database(entities = {Pursache.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PursacheDao pursacheDao();
}
