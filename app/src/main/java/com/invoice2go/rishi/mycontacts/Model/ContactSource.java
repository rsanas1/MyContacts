package com.invoice2go.rishi.mycontacts.Model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Created by rishi on 1/15/2018.
 */

public class ContactSource implements ModelInterface {

    public static ContactSource contactSource;

    AppDatabase appDatabase;


    private final Executor executor = Executors.newFixedThreadPool(2);

    private ContactSource(Context context){
        appDatabase = Room.databaseBuilder(context,AppDatabase.class, "production")
                /*.allowMainThreadQueries()*/
                .fallbackToDestructiveMigration()
                .build();

    }

    public static ContactSource getContactSource(Context context){

        if(contactSource == null)
        {
            synchronized (ContactSource.class){
                if(contactSource==null){
                    contactSource = new ContactSource(context);
                }
            }
        }
        return contactSource;
    }

    @Override
    public void getAllContacts() {

        Log.d("Contact Source","getAllContacts");


        executor.execute(new Runnable() {
            @Override
            public void run() {
                ContactResult contactResult=new ContactResult();
                contactResult.contactList= appDatabase.contactDao().getAll();
                EventBus.getDefault().post(contactResult);

                //EventBus.getDefault().post(appDatabase.contactDao().getAll());
            }
        });

        //return appDatabase.contactDao().getAll();


    }

    @Override
    public void saveContact(final Contact contact) {
        Log.d("Contact Source","saveContact");

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.contactDao().insert(contact);
            }
        });
       // appDatabase.contactDao().insert(contact);
    }

    @Override
    public void updateContact(final Contact contact) {
        Log.d("Contact Source","updateContact");

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.contactDao().update(contact);
            }
        });
        //appDatabase.contactDao().update(contact);
    }

    @Override
    public void getMatchingContacts(final String query) {
       /* List<Contact> result = appDatabase.contactDao().findAllByFirst(query);
                result.addAll(appDatabase.contactDao().findAllByLast(query));

                return result; */

        executor.execute(new Runnable() {
            @Override
            public void run() {
                ContactResult contactResult=new ContactResult();
                contactResult.contactList= appDatabase.contactDao().findAll(query);
                EventBus.getDefault().post(contactResult);
                //EventBus.getDefault().post(appDatabase.contactDao().findAll(query));
            }
        });

       //return appDatabase.contactDao().findAll(query);
    }

    @Override
    public void deleteContact(final Contact contact) {
        Log.d("Contact Source","deleteContact");

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.contactDao().delete(contact);
            }
        });
       // appDatabase.contactDao().delete(contact);
    }
}
