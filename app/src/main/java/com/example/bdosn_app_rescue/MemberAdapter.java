package com.example.bdosn_app_rescue;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class MemberAdapter extends FirebaseRecyclerAdapter<CreateUser, MemberAdapter.myviewholder> {
    private Context mContext;

    public MemberAdapter(@NonNull FirebaseRecyclerOptions<CreateUser> options, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull CreateUser model) {
        holder.name.setText("Name   : " + model.getName());


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ViewMissingPersonProfile.class);
                intent.putExtra("name", (model.getName()));
                intent.putExtra("email", model.getEmail());
                mContext.startActivity(intent);
            }
        });
        //   Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_emergency, parent, false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, contact, location;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageViewMissingPerson);
            name = (TextView) itemView.findViewById(R.id.textViewMissingName);
            contact = (TextView) itemView.findViewById(R.id.textViewMissingContact);
            location = (TextView) itemView.findViewById(R.id.textViewMissingLocation);
        }
    }
}