package ua.i.pl.afs.mypursaches.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.i.pl.afs.mypursaches.db.DbHelper;
import ua.i.pl.afs.mypursaches.models.PursachesModel;
import ua.i.pl.afs.mypursaches.models.Repository;

@Module
public class RepositoryModule {
    @Provides
    @NonNull
    @Singleton
    public Repository provideRepository(DbHelper helper){
        return new PursachesModel(helper);
    }
}
