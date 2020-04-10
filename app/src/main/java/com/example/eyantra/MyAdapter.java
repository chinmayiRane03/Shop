package com.example.eyantra;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<uploadinfo>  uploadinfos;

    public MyAdapter(Context c , ArrayList<uploadinfo> u){
        context = c;
        uploadinfos = u;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_details,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        uploadinfo UploadInfo = uploadinfos.get(position);
        holder.keeperName.setText(uploadinfos.get(position).getImageName());
        holder.nameShop.setText(uploadinfos.get(position).getShopName());
        holder.addShop.setText(uploadinfos.get(position).getShopAddress());
        holder.LocShop.setText(uploadinfos.get(position).getShopLocality());
        holder.call.setText(uploadinfos.get(position).getPhone());
        Picasso.get()
                .load(uploadinfos.get(position).getImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.myImage);
       /* Picasso.get().load(uploadinfos.getImageUrl()+"").into(holder.myImage);*/

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNo = uploadinfos.get(position).getPhone();
                String callShop = "tel:" + phoneNo.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(callShop));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return uploadinfos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameShop, keeperName ,addShop, LocShop, call;
        ImageView myImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameShop = (TextView) itemView.findViewById(R.id.shopnaam);
            keeperName = (TextView) itemView.findViewById(R.id.shopkeeper);
            addShop = (TextView) itemView.findViewById(R.id.shopAdd);
            LocShop = (TextView) itemView.findViewById(R.id.local);
            myImage = (ImageView) itemView.findViewById(R.id.shop_pic);
            call = (TextView) itemView.findViewById(R.id.phone);
        }
    }

   /* public void updateList(List<String> newList)

    {

        uploadinfos = new ArrayList<>();
        uploadinfos.addAll(newList);
        notifyDataSetChanged();

    }*/
}
