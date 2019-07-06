package ua.i.pl.afs.mypursaches.core;

import android.app.Application;

import ua.i.pl.afs.mypursaches.di.AppModule;
import ua.i.pl.afs.mypursaches.di.DbModule;
import ua.i.pl.afs.mypursaches.di.PresenterModule;
import ua.i.pl.afs.mypursaches.di.RepositoryModule;

public class PursacheApp extends Application {
    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = bindComponent();
    }

    protected AppComponent bindComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this))
                .presenterModule(new PresenterModule())
                .repositoryModule(new RepositoryModule())
                .dbModule(new DbModule())
                .build();
    }
}
