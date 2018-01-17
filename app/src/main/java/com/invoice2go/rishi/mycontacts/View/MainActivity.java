package com.invoice2go.rishi.mycontacts.View;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.invoice2go.rishi.mycontacts.Model.Contact;
import com.invoice2go.rishi.mycontacts.Model.ContactSource;
import com.invoice2go.rishi.mycontacts.R;

public class MainActivity extends AppCompatActivity implements ContactListFragment.OnContactListFragmentListener,
        AddContactFragment.OnAddContactFragmentListener , DisplayContactFragment.OnDisplayFragmentListener ,
        EditContactFragment.OnEditFragmentListener{

    ContactSource contactSource;
    ContactListFragment contactListFragment;

    public static final String TAG="Main Activity";

    public static String TAG_RETAINED_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactSource = ContactSource.getContactSource(getApplicationContext());

        if(savedInstanceState==null)
                    loadListFragment();
       else
           contactListFragment = (ContactListFragment) getSupportFragmentManager().findFragmentByTag("addListFragment");



    }

    private void loadListFragment() {

        contactListFragment = ContactListFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,contactListFragment,"addListFragment");
        fragmentTransaction.commit();

    }




    @Override
    public void launchDisplayContactFragment(Contact contact) {

        DisplayContactFragment displayContactFragment = DisplayContactFragment.newInstance(contact);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,displayContactFragment,"addDisplayFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void launchAddContactFragment() {

        AddContactFragment addContactFragment = AddContactFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,addContactFragment,"addContactFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void launchEditContactFragment(Contact contact) {

        Log.d(TAG,"launchEditContactFragment");
        EditContactFragment editContactFragment = EditContactFragment.newInstance(contact);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,editContactFragment,"editContactFragment");
        fragmentTransaction.addToBackStack("editContactFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void deleteContact(Contact contact) {

        Log.d(TAG,"deleteContact");
        contactSource.deleteContact(contact);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        contactListFragment.updateContacts();

    }

    @Override
    public void addContact(Contact contact) {
        contactSource.saveContact(contact);

            Log.d(TAG,"Fragment popped");
            Log.d(TAG,getSupportFragmentManager().getBackStackEntryCount()+" Count");
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            //getSupportFragmentManager().popBackStack();
            Log.d(TAG,getSupportFragmentManager().getBackStackEntryCount()+" Count");
            contactListFragment.updateContacts();


    }

    @Override
    public void editContact(Contact contact) {
        contactSource.updateContact(contact);
        Log.d(TAG,getSupportFragmentManager().getBackStackEntryCount()+" Count");
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Log.d(TAG,getSupportFragmentManager().getBackStackEntryCount()+" Count");
        contactListFragment.updateContacts();
        Log.d(TAG,getSupportFragmentManager().getBackStackEntryCount()+" Count");
    }
}
