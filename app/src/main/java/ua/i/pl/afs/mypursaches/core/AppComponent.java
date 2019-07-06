package ua.i.pl.afs.mypursaches.core;

import javax.inject.Singleton;

import dagger.Component;
import ua.i.pl.afs.mypursaches.di.AppModule;
import ua.i.pl.afs.mypursaches.di.DbModule;
import ua.i.pl.afs.mypursaches.di.PresenterModule;
import ua.i.pl.afs.mypursaches.di.RepositoryModule;
import ua.i.pl.afs.mypursaches.views.AddActivity;
import ua.i.pl.afs.mypursaches.views.MainActivity;

@Component(modules = {AppModule.class, DbModule.class, PresenterModule.class, RepositoryModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(AddActivity secondActivity);
}
