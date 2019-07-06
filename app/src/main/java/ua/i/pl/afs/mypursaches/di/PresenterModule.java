package ua.i.pl.afs.mypursaches.di;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.i.pl.afs.mypursaches.models.Repository;
import ua.i.pl.afs.mypursaches.presenter.IPresenter;
import ua.i.pl.afs.mypursaches.presenter.PursachePresenter;

@Module
public class PresenterModule {
    @Provides
    @NonNull
    @Singleton
    public IPresenter providePresenter(@NonNull Repository repository) {
        return new PursachePresenter(repository);
    }
}
