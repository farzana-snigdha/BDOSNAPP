package com.example.bdosn_app_rescue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MembersViewHolder> {
    ArrayList<CreateUser> namelist;
    Context c;

    public MemberAdapter(ArrayList<CreateUser> namelist, Context c) {
        this.namelist = namelist;
        this.c = c;
    }

    @NonNull
    @Override
    public MembersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_emergency, parent, false);
        MembersViewHolder membersViewHolder = new MembersViewHolder(v, c, namelist);

        return membersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MembersViewHolder holder, int position) {
        CreateUser currentUserObj = namelist.get(position);
        holder.name.setText(currentUserObj.getName());
    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }


    public static class MembersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        View v;
        Context c;
        ArrayList<CreateUser> arrayList;
        FirebaseAuth auth;
        FirebaseUser user;

        public MembersViewHolder(@NonNull View itemView, Context c, ArrayList<CreateUser> arrayList) {
            super(itemView);

            this.c = c;
            this.arrayList = arrayList;

            itemView.setOnClickListener(this);

            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();

            name = itemView.findViewById(R.id.textViewEmergencyName);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
