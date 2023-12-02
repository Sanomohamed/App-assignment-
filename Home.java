package com.example.appassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private RecyclerView rvhome;

    private ProductAdapter productAdapter;

    private List<Product> prolist;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prolist = new ArrayList<>();


        String[]  names = getResources().getStringArray(R.array.names);
        String[]  dobs = getResources().getStringArray(R.array.dobs);
        String[]  des = getResources().getStringArray(R.array.des);
        TypedArray icons = getResources().obtainTypedArray(R.array.icons);

        for (int i = 0; i < names.length; i++) {
            prolist.add(new Product(names[i], dobs[i], des[i],icons.getResourceId(i, 0)));
        }

        icons.recycle();

        productAdapter = new ProductAdapter(prolist, this,Home.this);

        rvhome = findViewById(R.id.rvhome);
        rvhome.setLayoutManager(new GridLayoutManager(this,3));

        rvhome.setAdapter(productAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity3();

            }
        });

        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAbotus();
            }
        });
        FloatingActionButton fab3 = findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfil();
            }
        });
    }

    public void openProfil(){
        Intent intent;
        intent = new Intent(this,Profil.class);
        startActivity(intent);
    }
    public void openMainActivity3(){
        Intent intent;
        intent = new Intent(this,MainActivity3.class);
        startActivity(intent);
    }

    public void openAbotus(){
        Intent intent;
        intent = new Intent(this,Abotus.class);
        startActivity(intent);
    }
    @Override
    public void onProductClick(Product product, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Product Details")
                .setMessage("Name: " + product.getName() + "\nPrice: " +"RM"+ product.getDobs()+"\n"+product.getDes())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the OK button click
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}