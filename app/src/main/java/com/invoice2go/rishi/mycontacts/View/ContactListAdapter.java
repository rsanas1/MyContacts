package com.invoice2go.rishi.mycontacts.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.invoice2go.rishi.mycontacts.Model.Contact;
import com.invoice2go.rishi.mycontacts.R;

import java.util.List;

/**
 * Created by rishi on 1/14/2018.
 */

class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>  {

    List<Contact> contactList;
    ContactListFragment contactListFragment;
    public ContactListAdapter( ContactListFragment contactListFragment, List<Contact> contactList) {
       this.contactListFragment = contactListFragment;
        this.contactList = contactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contactlist_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
    holder.nameTv.setText(contactList.get(position).getFirstName()+" "+contactList.get(position).getLastName());
    holder.nameTv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            contactListFragment.contactSelectedForDisplay(contactList.get(position));
        }
    });
    holder.imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            contactListFragment.contactSelectedForEdit(contactList.get(position));
        }
    });
    }

    public void removeItem(int position) {
        contactList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Contact contact, int position) {
        contactList.add(position, contact);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;
        ImageView imageView;
        public RelativeLayout viewBackground, viewForeground;

        public ContactViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            contactListFragment.contactSelectedForDisplay(contactList.get(getAdapterPosition()));
                }
            });
            nameTv = itemView.findViewById(R.id.nameTv);
            imageView = itemView.findViewById(R.id.editImage);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);

        }
    }
}
