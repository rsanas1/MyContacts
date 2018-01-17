package com.invoice2go.rishi.mycontacts.View;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.invoice2go.rishi.mycontacts.Model.Contact;
import com.invoice2go.rishi.mycontacts.R;


public class AddContactFragment extends Fragment implements View.OnClickListener {


    EditText firstNameEt;
    EditText lastNameEt;
    EditText phoneNumberEt;
    EditText emailEt;
    Button submitBtn;

    private OnAddContactFragmentListener mListener;

    public AddContactFragment() {
        // Required empty public constructor
    }


    public static AddContactFragment newInstance() {
        AddContactFragment fragment = new AddContactFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstNameEt = view.findViewById(R.id.firstNameEt);
        lastNameEt = view.findViewById(R.id.lastNameEt);
        phoneNumberEt = view.findViewById(R.id.phoneNumberEt);
        emailEt = view.findViewById(R.id.emailEt);
        submitBtn = view.findViewById(R.id.submitContactBtn);
        submitBtn.setOnClickListener(this);
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddContactFragmentListener) {
            mListener = (OnAddContactFragmentListener) context;
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

                if(!firstNameEt.getText().toString().isEmpty()) {


                    Contact contact = new Contact();
                    contact.setFirstName(firstNameEt.getText().toString());
                    contact.setLastName(lastNameEt.getText().toString());
                    contact.setEmail(emailEt.getText().toString());
                    contact.setPhoneNumber(phoneNumberEt.getText().toString());

                    mListener.addContact(contact);

                }

                else
                {
                    Log.d("AddContactFragment","Empty");
                    firstNameEt.setError("Cannot be Empty");
                }

                break;
        }
    }


    public interface OnAddContactFragmentListener {
        // TODO: Update argument type and name
        void addContact(Contact contact);
    }

}
