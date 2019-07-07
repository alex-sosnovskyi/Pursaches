package ua.i.pl.afs.mypursaches.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

@Entity
public class Pursache {
    @PrimaryKey
    private long id;
    private String name;
    private String pictUri;
    private String pict;
    private boolean bought;

    public Pursache(String name) {
        this.name = name;
        this.bought = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictUri() {
        return pictUri;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public String getPict() {
        return pict;
    }

    public void setPictUri(String pictUri) {
        this.pictUri = pictUri;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean val) {
        bought = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pursache)) return false;

        Pursache pursache = (Pursache) o;

        if (id != pursache.id) return false;
        return name.equals(pursache.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}
