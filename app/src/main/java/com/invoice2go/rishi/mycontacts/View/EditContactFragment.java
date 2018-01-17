package com.invoice2go.rishi.mycontacts.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.invoice2go.rishi.mycontacts.Model.Contact;
import com.invoice2go.rishi.mycontacts.R;


public class EditContactFragment extends Fragment implements View.OnClickListener {

    private static final String CONTACT = "contact";



    EditText firstNameEt;
    EditText lastNameEt;
    EditText phoneNumberEt;
    EditText emailEt;

    Button submitContact;


    Contact contact;
    private OnEditFragmentListener mListener;

    public EditContactFragment() {
        // Required empty public constructor
    }


    public static EditContactFragment newInstance(Contact contact) {
        EditContactFragment fragment = new EditContactFragment();
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
        return inflater.inflate(R.layout.fragment_edit_contact, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstNameEt = view.findViewById(R.id.firstNameEt);
        lastNameEt = view.findViewById(R.id.lastNameEt);
        phoneNumberEt = view.findViewById(R.id.phoneNumberEt);
        emailEt = view.findViewById(R.id.emailEt);
        submitContact = view.findViewById(R.id.submitContactBtn);
        submitContact.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(contact!=null){
            firstNameEt.setText(contact.getFirstName());
            lastNameEt.setText(contact.getLastName());
            phoneNumberEt.setText(contact.getPhoneNumber());
            emailEt.setText(contact.getEmail());
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEditFragmentListener) {
            mListener = (OnEditFragmentListener) context;
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

        switch (v.getId()){

            case R.id.submitContactBtn:

                    if(!firstNameEt.getText().toString().isEmpty()){


                        contact.setFirstName(firstNameEt.getText().toString());
                        contact.setLastName(lastNameEt.getText().toString());
                        contact.setEmail(emailEt.getText().toString());
                        contact.setPhoneNumber(phoneNumberEt.getText().toString());
                        mListener.editContact(contact);
                    }
                    else
                        firstNameEt.setError("Cannot be Empty");

        }
    }


    public interface OnEditFragmentListener {
        // TODO: Update argument type and name
        void editContact(Contact contact);
    }
}
