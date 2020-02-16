package com.softwarica.lostandfound.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softwarica.lostandfound.API.URL.URL;
import com.softwarica.lostandfound.R;
import com.softwarica.lostandfound.module.Lost;
import com.softwarica.lostandfound.module.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LostAdapter extends RecyclerView.Adapter<LostAdapter.LostViewHolders> {
    List<Lost> lostList;
    Context context;

    public LostAdapter(List<Lost> lostList, Context context) {
        this.lostList = lostList;
        this.context = context;
    }

    @NonNull
    @Override
    public LostAdapter.LostViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_lost, parent, false);
        return new LostViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LostAdapter.LostViewHolders holder, int position) {
        final Lost lost = lostList.get(position);
        holder.name.setText(lost.getName());
        holder.type.setText(lost.getType());
        holder.location.setText(lost.getLocation());
        holder.phone.setText(lost.getPhone());
        holder.details.setText(lost.getDetails());
        holder.status.setText(lost.getStatus());

        String image = lost.getImage();
        String imgPath = URL.imageUserPath+image;

        Picasso.get().load(imgPath).into(holder.image);

    }


    @Override
    public int getItemCount() {
        return lostList.size();
    }


    public class LostViewHolders extends RecyclerView.ViewHolder {

        TextView name, type, location, phone, details, status;
        ImageView image;


        public LostViewHolders(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lostname);
            type = itemView.findViewById(R.id.losttype);
            location = itemView.findViewById(R.id.lostlocation);
            phone = itemView.findViewById(R.id.lostphone);
            details = itemView.findViewById(R.id.lostdetails);
            status = itemView.findViewById(R.id.loststatus);
            image = itemView.findViewById(R.id.lostimage);

        }
    }
}
