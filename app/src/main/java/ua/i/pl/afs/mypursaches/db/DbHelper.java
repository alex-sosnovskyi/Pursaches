package ua.i.pl.afs.mypursaches.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import ua.i.pl.afs.mypursaches.models.Pursache;
import ua.i.pl.afs.mypursaches.models.Repository;

public class DbHelper {
    private final AppDatabase database;
    private final PursacheDao pursacheDao;
    private Context context;
    private Repository repository;
    private List<Pursache> pursachesResult = new ArrayList<>();

    public DbHelper(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, AppDatabase.class, "database")
                .build();
        pursacheDao = database.pursacheDao();
    }

    public void getPursaches(boolean brought) {
        updatePursaches(brought);
    }

    private void updatePursaches(final boolean isBrought) {
        pursacheDao.getPursaches(isBrought).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Pursache>>() {
                    @Override
                    public void accept(List<Pursache> pursacheList) throws Exception {
                        pursachesResult.clear();
                        for (Pursache current : pursacheList) {
                            if (current.isBought() == isBrought) {
                                pursachesResult.add(current);
                            }
                        }
                        repository.dataUpdated();
                    }
                });
    }

    public void addPursache(final Pursache pursache) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                pursacheDao.insert(pursache);
            }
        });
        thread.start();
    }

    public void deletePursaches(final List<Long> pursacheIdList) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                pursacheDao.update(pursacheIdList, true);
            }
        });
        thread.start();
    }

    public void addObserver(Repository repository) {
        this.repository = repository;
    }

    public List<Pursache> getPursachesResult() {
        return pursachesResult;
    }
}
