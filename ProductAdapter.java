package com.example.appassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private List<Product> prolist;
    private Context context;
    private OnProductClickListener listener;

    public ProductAdapter(List<Product> prolist, OnProductClickListener listener,Context context) {
        this.prolist = prolist;
        this.listener = listener;
        this.context  =context.getApplicationContext();
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = prolist.get(position);
        holder.tvName.setText(product.getName());
        holder.tvDOB.setText(product.getDobs());
        holder.ivIcon.setImageResource(product.getIconResId());


        holder.btnAddToCard.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && listener != null ){
                listener.onProductClick(prolist.get(adapterPosition), position);

                // Show a notification when the button is clicked
              //  showNotification(prolist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {

        return prolist.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView tvName, tvDOB;
        Button btnAddToCard;
        ImageView ivIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDOB = itemView.findViewById(R.id.tvDOB);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            btnAddToCard = itemView.findViewById(R.id.btnAddToCard);
        }
    }
  public interface OnProductClickListener {
        void onProductClick(Product product,int position) ;
   }

    private static int notificationCounter = 1;

   /* @SuppressLint("MissingPermission")
    private void showNotification(Product product) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
           .setSmallIcon(R.drawable.notification_icon)
           .setContentTitle("Product Added to Cart")
           .setContentText(product.getName() + " added to your cart")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationCounter++;
        int notificationId = notificationCounter;
       // int notificationId = (int) System.currentTimeMillis();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }*/
}