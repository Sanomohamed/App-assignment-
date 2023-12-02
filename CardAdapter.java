package com.example.appassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private List<Product> cardList;

    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    private OnTotalAmountChangeListener onTotalAmountChangeListener;

    public void OnTotalAmountChangeListener(OnTotalAmountChangeListener listener) {
        this.onTotalAmountChangeListener = listener;
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }

    public CardAdapter(List<Product> cardList,OnDeleteButtonClickListener onDeleteButtonClickListener) {

        this.cardList = cardList;
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    private double convertStringToDouble(String stringValue) {
        try {
            return Double.parseDouble(stringValue);
        } catch (NumberFormatException e) {
            // Handle the case where the string cannot be converted to a double
            return 0;
        }
    }

    public double calculateTotalPrice() {
        double total = 0;

        for (Product product : cardList) {
            double price = convertStringToDouble(product.getDobs());
            int quantity = product.getQuantity();
            total += price * quantity;
        }

        return total;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        Product product = cardList.get(position);
        holder.tvCardName.setText(product.getName());
        holder.tvCardPrice.setText(product.getDobs());
        holder.ivCardIcon.setImageResource(product.getIconResId());

        holder.btnDecrease.setOnClickListener(v -> {
            int newQuantity = Math.max(1, product.getQuantity() - 1);
            product.setQuantity(newQuantity);
            updatePrice(holder, product);
        });
        holder.btnIncrease.setOnClickListener(v -> {
            int newQuantity = product.getQuantity() + 1;
            product.setQuantity(newQuantity);
            updatePrice(holder, product);
        });
        holder.button2.setOnClickListener(v -> {
            if (onDeleteButtonClickListener != null) {
                onDeleteButtonClickListener.onDeleteButtonClick(position);
            }
        });

        int quantity = product.getQuantity();
        holder.textView5.setText(String.valueOf(quantity));
    }
    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCardName, tvCardPrice;
        ImageView ivCardIcon;

        Button button2,btnIncrease,btnDecrease;
        TextView textView5;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardName = itemView.findViewById(R.id.tvCardName);
            tvCardPrice = itemView.findViewById(R.id.tvCardPrice);
            ivCardIcon = itemView.findViewById(R.id.ivCardIcon);
            button2=itemView.findViewById(R.id.button2);
            textView5=itemView.findViewById(R.id.textView5);
            btnIncrease=itemView.findViewById(R.id.btnIncrease);
            btnDecrease=itemView.findViewById(R.id.btnDecrease);

        }
    }

    private void updatePrice(ViewHolder holder, Product product) {
        double price = convertStringToDouble(product.getDobs());
        int quantity = product.getQuantity();
        double totalPrice = price * quantity;
        holder.tvCardPrice.setText(String.valueOf(totalPrice));
        holder.textView5.setText(String.valueOf(quantity));

        if (onTotalAmountChangeListener != null) {
            double newTotalPrice = calculateTotalPrice();
            onTotalAmountChangeListener.onTotalAmountChange(newTotalPrice);
        }
    }

    public interface OnTotalAmountChangeListener {
        void onTotalAmountChange(double newTotalAmount);
    }

}