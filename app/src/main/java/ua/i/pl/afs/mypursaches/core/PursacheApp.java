package ua.i.pl.afs.mypursaches.core;

import android.app.Application;

import ua.i.pl.afs.mypursaches.presenter.IPresenter;
import ua.i.pl.afs.mypursaches.presenter.PursachePresenter;

public class PursacheApp extends Application{
private IPresenter presenter;

    @Override
    public void onCreate() {
        super.onCreate();
        presenter=new PursachePresenter(getApplicationContext());
    }

    public IPresenter getPresenter(){
        return presenter;
    }
}
