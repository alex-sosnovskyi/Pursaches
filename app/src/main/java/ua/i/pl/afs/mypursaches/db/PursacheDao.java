package ua.i.pl.afs.mypursaches.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ua.i.pl.afs.mypursaches.models.Pursache;

@Dao
public interface PursacheDao {

    @Query("SELECT * FROM pursache where bought = :isBrought")
    Flowable<List<Pursache>> getPursaches(boolean isBrought);

    @Insert
    void insert(Pursache pursache);

    @Query("UPDATE pursache SET bought =:isbrought where id in(:pursachesIdList)")
    void update(List<Long> pursachesIdList, boolean isbrought);

    @Delete
    void delete(Pursache pursache);
}
