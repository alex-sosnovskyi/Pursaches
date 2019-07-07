package ua.i.pl.afs.mypursaches.models;

import android.net.Uri;

import java.util.List;

import ua.i.pl.afs.mypursaches.core.DataObserver;

public interface Repository {
    void getPursaches(boolean brouht);

    void addPursache(String pursacheName, String path, String pathUri);

    void addBought(long pursacheId);

    void addObserver(DataObserver observer);

    void removeFromBroughted(long id);

    void removeFromBroughtedAll();

    void delPursaches();

    void dataUpdated();

    List<Pursache> getPursachesResults();
}
