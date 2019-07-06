package ua.i.pl.afs.mypursaches.presenter;


import android.content.Intent;

import java.util.List;

import dagger.Provides;
import ua.i.pl.afs.mypursaches.models.Pursache;
import ua.i.pl.afs.mypursaches.views.BaseActivity;

public interface IPresenter<V extends BaseActivity> {
    void attach(V activity);

    void detach();

    void addButtonClicked();

    void backPressed();

    void savePressed(String text);

    void photoRequest();

    void photoRequestOk(Intent data);

    boolean addBrought(long id, boolean isChecked);

    void archiveButtonClicked();

    void quenieButtonClicked();

    boolean cancellationClick();

    void dellPursaches();

    void galleryPhotoRequest();

    void galleryPhotoRequestOk(Intent data);

    void dataRequest();
}
