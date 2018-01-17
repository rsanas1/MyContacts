package com.invoice2go.rishi.mycontacts.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by rishi on 1/14/2018.
 */

@Database(entities = {Contact.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
