package ua.i.pl.afs.mypursaches.views;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import ua.i.pl.afs.mypursaches.core.PursacheApp;
import ua.i.pl.afs.mypursaches.models.Pursache;


public interface IBaseActivity {
    Context getContext();
    void forvard();
    void back();
    PursacheApp getApp();
    void updateUI();
    void updateUI(List<Pursache> pursacheList);
    void showPhoto(Uri uri);
}
