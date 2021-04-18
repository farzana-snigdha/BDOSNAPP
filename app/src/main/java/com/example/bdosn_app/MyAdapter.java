package com.example.bdosn_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;


public class MyAdapter extends FirebaseRecyclerAdapter<MissingPerson, MyAdapter.myviewholder> {
    private Context mContext;

    public MyAdapter(@NonNull FirebaseRecyclerOptions<MissingPerson> options, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull MissingPerson model) {
        holder.name.setText(model.getName());
        holder.contact.setText(model.getContact());
        holder.location.setText(model.getLocation());
        Picasso.get().load(model.getImage()).into(holder.img);


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(mContext, ViewMissingPersonProfile.class);
                intent.putExtra("name", (model.getName()));
                intent.putExtra("location", (model.getLocation()));
                intent.putExtra("contact", (model.getContact()));
                intent.putExtra("image", (model.getImage()));
                mContext.startActivity(intent);
            }
        });
        //   Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
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