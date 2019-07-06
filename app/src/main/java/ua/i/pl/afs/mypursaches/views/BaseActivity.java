package ua.i.pl.afs.mypursaches.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import ua.i.pl.afs.mypursaches.core.PursacheApp;
import ua.i.pl.afs.mypursaches.models.Pursache;


public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity {
    private PursacheApp application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (PursacheApp) getApplication();
    }

    @Override
    public PursacheApp getApp() {
        return application;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void forvard() {
    }

    @Override
    public void back() {
    }

    @Override
    public void  updateUI(){}

    @Override
    public void  updateUI(List<Pursache> pursacheList){}

    @Override
    public  void showPhoto(Uri uri){}
}
