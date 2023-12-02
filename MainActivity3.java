package com.example.appassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity3 extends AppCompatActivity implements ProductAdapter.OnProductClickListener, CardAdapter.OnDeleteButtonClickListener {
    private RecyclerView rvProduct;
    private RecyclerView rvCard;
    private ProductAdapter productAdapter;
    private CardAdapter cardAdapter;
    private Toolbar toolbar1;

   // String[] names, dobs;
    Button toolbarButton,toolbarButton1,btnOrder;
    TypedArray icons;
    private List<Product> prolist;
    private List<Product> cardList;
    TextView tvTotalAmount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        prolist = new ArrayList<>();
        cardList = new ArrayList<>();

      String[]  names = getResources().getStringArray(R.array.names);
      String[]  dobs = getResources().getStringArray(R.array.dobs);
      String[]  des = getResources().getStringArray(R.array.des);
      TypedArray icons = getResources().obtainTypedArray(R.array.icons);

        for (int i = 0; i < names.length; i++) {
            prolist.add(new Product(names[i], dobs[i], des[i],icons.getResourceId(i, 0)));
        }

        icons.recycle();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        productAdapter = new ProductAdapter(prolist, this,MainActivity3.this);
        cardAdapter = new CardAdapter(cardList,this);

        cardAdapter.OnTotalAmountChangeListener(new CardAdapter.OnTotalAmountChangeListener() {
            @Override
            public void onTotalAmountChange(double newTotalAmount) {
                // Update the UI with the new total amount
                tvTotalAmount.setText(String.format(Locale.getDefault(), "%.2f", newTotalAmount));
            }
        });

        rvProduct = findViewById(R.id.rvProduct);
        tvTotalAmount=findViewById(R.id.tvTotalAmount);
        rvProduct.setLayoutManager(new GridLayoutManager(this,3));
        rvProduct.setAdapter(productAdapter);
        rvCard = findViewById(R.id.rvCard);
        rvCard.setLayoutManager(new GridLayoutManager(this,1));
        rvCard.setAdapter(cardAdapter);

        LinearLayout bottomLayout = findViewById(R.id.bottomLayout);
        toolbarButton = findViewById(R.id.toolbarButton);
        final RecyclerView finalRvCard = rvCard;
        toolbarButton1=findViewById(R.id.toolbarButton1);
        FloatingActionButton fab1 = findViewById(R.id.fab1);
        toolbar1=findViewById(R.id.toolbar1);
        btnOrder=findViewById(R.id.btnOrder);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConfirmorder(cardAdapter);
            }
        });

        toolbarButton.setOnClickListener(v -> {

                rvCard.setVisibility(View.VISIBLE);
                bottomLayout.setVisibility(View.VISIBLE);
                toolbar1.setVisibility(View.GONE);
                rvProduct.setVisibility(View.GONE);
                toolbarButton.setVisibility(View.GONE);
                toolbarButton1.setVisibility(View.VISIBLE);

            double total = cardAdapter.calculateTotalPrice();
            tvTotalAmount.setText(String.format(Locale.getDefault(), "%.2f", total));
        });
        toolbarButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rvCard.getVisibility() == View.VISIBLE) {
                    rvCard.setVisibility(View.GONE);
                    bottomLayout.setVisibility(View.GONE);
                    toolbarButton1.setVisibility(View.GONE);
                    toolbar1.setVisibility(View.VISIBLE);
                    toolbarButton.setVisibility(View.VISIBLE);
                    rvProduct.setVisibility(View.VISIBLE);
                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });
    }
    public void openConfirmorder(CardAdapter cardAdapter) {
        Intent intent = new Intent(this, Confirmorder.class);
        intent.putExtra("cardList", new ArrayList<>(cardList)); // Pass the cardList instead of cardAdapter
        startActivity(intent);
    }
    public void openHome(){
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }
    public void onProductClick(Product product,int position) {

        int existingPosition = findProductPositionInCard(product);

        if (existingPosition != -1) {
            Product existingProduct = cardList.get(existingPosition);
            existingProduct.setQuantity(existingProduct.getQuantity() + 1);
        } else {

            cardList.add(product);
        }

        cardAdapter.notifyDataSetChanged();

        double newTotalPrice = cardAdapter.calculateTotalPrice();
        tvTotalAmount.setText(String.format(Locale.getDefault(), "%.2f", newTotalPrice));
        Toast.makeText(this, "Successfull added to card", Toast.LENGTH_SHORT).show();
    }

    private int findProductPositionInCard(Product product) {
        for (int i = 0; i < cardList.size(); i++) {
            if (cardList.get(i).equals(product)) {
                return i;
            }}
        return -1;
    }
    public void onDeleteButtonClick(int position) {
        // Remove the item from the list
        cardList.remove(position);

        // Update the total price
        double newTotalPrice = cardAdapter.calculateTotalPrice();

        // Notify the adapter about the data changes
        cardAdapter.notifyDataSetChanged();

      //  updateTotalPrice();
        tvTotalAmount.setText(String.format(Locale.getDefault(), "%.2f", newTotalPrice));
    }
}