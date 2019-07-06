package ua.i.pl.afs.mypursaches.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.i.pl.afs.mypursaches.db.DbHelper;

@Module
public class DbModule {
   @Provides
   @NonNull
   @Singleton
    public DbHelper provideDb(Context context){
       return new DbHelper(context);
   }
}
