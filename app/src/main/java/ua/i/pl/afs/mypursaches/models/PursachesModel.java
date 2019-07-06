package ua.i.pl.afs.mypursaches.models;


import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.i.pl.afs.mypursaches.core.DataObserver;
import ua.i.pl.afs.mypursaches.db.DbHelper;


public class PursachesModel implements Repository {
    private List<Pursache> pursacheList = new ArrayList<>();
    private List<Long> broughtIdList = new ArrayList<>();

    DbHelper dbHelper;
    private DataObserver observer;

    public PursachesModel(DbHelper helper) {
        dbHelper = helper;
        dbHelper.addObserver(this);

    }

    @Override
    public void addObserver(DataObserver observer) {
        this.observer = observer;
    }

    @Override
    public void getPursaches(boolean brought) {
        dbHelper.getPursaches(brought);
    }


    public List<Pursache> getPursachesResults() {
        return pursacheList;
    }

    @Override
    public void dataUpdated() {
        pursacheList = dbHelper.getPursachesResult();
        observer.updateUI();
    }

    @Override
    public void addPursache(String pursacheName, String path, Uri pathUri) {
        Pursache pursache = new Pursache(pursacheName);
        if (null != path) {
            pursache.setPict(path);
            pursache.setPictUri(pathUri);
            pursache.setBought(false);
        }
        pursache.setId(System.currentTimeMillis());
        dbHelper.addPursache(pursache);
    }

    @Override
    public void addBought(long pursacheId) {
        broughtIdList.add(pursacheId);
    }

    @Override
    public void removeFromBroughtedAll() {
        broughtIdList.clear();
    }


    @Override
    public void delPursaches() {
        dbHelper.deletePursaches(broughtIdList);
    }

    @Override
    public void removeFromBroughted(long id) {
        int index = broughtIdList.indexOf(id);
        broughtIdList.remove(index);
    }
}
