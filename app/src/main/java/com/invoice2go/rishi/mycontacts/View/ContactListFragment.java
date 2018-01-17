package com.invoice2go.rishi.mycontacts.View;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.invoice2go.rishi.mycontacts.Model.Contact;
import com.invoice2go.rishi.mycontacts.Model.ContactResult;
import com.invoice2go.rishi.mycontacts.Model.ContactSource;
import com.invoice2go.rishi.mycontacts.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;



public class ContactListFragment extends Fragment implements View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private OnContactListFragmentListener mListener;

    FloatingActionButton floatingActionButtonButton;

    RecyclerView recyclerView;
    ContactListAdapter recyclerAdapter;
    List<Contact> contactList;
    RelativeLayout relativeLayout;

    SearchView searchView;

    String TAG = "ContactListFragment";

    public ContactListFragment() {

    }


    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactList = new ArrayList<>();

        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        relativeLayout = view.findViewById(R.id.contactListLinearLayout);
        floatingActionButtonButton = view.findViewById(R.id.addButton);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new ContactListAdapter(this, contactList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.search_contacts));
        searchView.setOnCloseListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (!searchView.isIconified()) {
                    Log.d(TAG, "isIconified");
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        searchView.setIconified(true);
                        searchView.clearFocus();
                    }

                    return true;
                }
                Log.d(TAG, "is Not Iconified");
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateContacts();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        floatingActionButtonButton.setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnContactListFragmentListener) {
            mListener = (OnContactListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.addButton:
                mListener.launchAddContactFragment();
                break;

            default:
        }
    }

    public void contactSelectedForDisplay(Contact contact) {
        mListener.launchDisplayContactFragment(contact);
    }

    public void contactSelectedForEdit(Contact contact) {
        mListener.launchEditContactFragment(contact);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ContactListAdapter.ContactViewHolder) {
            // get the removed item name to display it in snack bar
            String name = contactList.get(viewHolder.getAdapterPosition()).getFirstName();

            // backup of removed item for undo purpose
            final Contact deletedcontact = contactList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            recyclerAdapter.removeItem(viewHolder.getAdapterPosition());
            mListener.deleteContact(contactList.get(viewHolder.getAdapterPosition()));

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    recyclerAdapter.restoreItem(deletedcontact, deletedIndex);
                    mListener.addContact(deletedcontact);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        searchView.clearFocus();
        if (query.isEmpty()) {

            Log.d("TAG", "onQueryTextSubmit is Empty");
            updateContacts();
        } else {
            Log.d("TAG", "onQueryTextSubmit is Not Empty");
            updateContacts(query);
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.isEmpty()) {
            updateContacts();
        } else {
            updateContacts(newText);
        }
        return false;

    }

    @Override
    public boolean onClose() {
        updateContacts();
        return false;
    }


    public interface OnContactListFragmentListener {

        void launchDisplayContactFragment(Contact contact);

        void launchEditContactFragment(Contact contact);

        void launchAddContactFragment();

        void deleteContact(Contact contact);

        void addContact(Contact contact);

    }

    public void updateContacts() {

        ContactSource.getContactSource(getActivity().getApplicationContext()).getAllContacts();

    }

    public void updateContacts(String query) {

        ContactSource.getContactSource(getActivity().getApplicationContext()).getMatchingContacts(query);

    }



    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ContactResult contactResult){

        contactList.clear();
        contactList.addAll(contactResult.contactList);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}

