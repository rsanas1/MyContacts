package com.invoice2go.rishi.mycontacts.View;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.invoice2go.rishi.mycontacts.Model.AppDatabase;

/**
 * Created by rishi on 1/14/2018.
 */

public class MyApplication extends Application {

    AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "production")
                .build();
    }
}
