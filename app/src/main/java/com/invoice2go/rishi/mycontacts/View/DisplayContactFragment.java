package com.invoice2go.rishi.mycontacts.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.invoice2go.rishi.mycontacts.Model.Contact;
import com.invoice2go.rishi.mycontacts.R;


public class DisplayContactFragment extends Fragment implements View.OnClickListener, Toolbar.OnMenuItemClickListener {


    public static final String TAG = "DisplayContactFragment";
    private static final String CONTACT = "contact";


    TextView firstNameTv;
    TextView lastNameTv;
    TextView phoneNumberTv;
    TextView emailTv;
    Button editContactBtn;
    Button deleteContactBtn;
    Toolbar toolbar;

    Contact contact;

    private OnDisplayFragmentListener mListener;

    public DisplayContactFragment() {

    }


    public static DisplayContactFragment newInstance(Contact contact) {
        DisplayContactFragment fragment = new DisplayContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contact = getArguments().getParcelable(CONTACT);
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_contact, container, false);
        firstNameTv = view.findViewById(R.id.firstNameTv);
        lastNameTv = view.findViewById(R.id.lastNameTv);
        phoneNumberTv = view.findViewById(R.id.phoneNumberTv);
        emailTv = view.findViewById(R.id.emailTv);
        editContactBtn = view.findViewById(R.id.editContactBtn);
        deleteContactBtn = view.findViewById(R.id.deleteContactBtn);
        editContactBtn.setOnClickListener(this);
        deleteContactBtn.setOnClickListener(this);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.display_menu);
        toolbar.setOnMenuItemClickListener(this);
        return view;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(contact!=null){
            firstNameTv.setText(contact.getFirstName());
            lastNameTv.setText(contact.getLastName());
            phoneNumberTv.setText(contact.getPhoneNumber());
            emailTv.setText(contact.getEmail());
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.display_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDisplayFragmentListener) {
            mListener = (OnDisplayFragmentListener) context;
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

        switch (v.getId())
        {

            case R.id.editContactBtn:

                mListener.launchEditContactFragment(contact);
                break;

            case R.id.deleteContactBtn:

                mListener.deleteContact(contact);
                break;

            default:
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.editItem:

                mListener.launchEditContactFragment(contact);
                break;

            case R.id.deleteItem:

                mListener.deleteContact(contact);
                break;

            default:
        }
        return false;
    }





    public interface OnDisplayFragmentListener {
        // TODO: Update argument type and name

        void launchEditContactFragment(Contact contact);
        void deleteContact(Contact contact);
    }
}
