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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Confirmorder extends AppCompatActivity implements ProductAdapter.OnProductClickListener{

    private RecyclerView rvCard;
    private CardAdapter cardAdapter;

    private List<Product> cardList;
    Button toolbarButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);

        rvCard = findViewById(R.id.rvProduct);
        rvCard.setLayoutManager(new GridLayoutManager(this,1));

        List<Product> cardList = (List<Product>) getIntent().getSerializableExtra("cardList");

        cardAdapter = new CardAdapter(cardList, new CardAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int position) {
                // Handle delete button click
            }
        });

        rvCard.setAdapter(cardAdapter);
       // rvProduct = findViewById(R.id.rvProduct);
      //  rvProduct.setLayoutManager(new GridLayoutManager(this, 1));

      //  rvProduct.setAdapter(cardAdapter);



       // String[]  names = getResources().getStringArray(R.array.names);
       // String[]  dobs = getResources().getStringArray(R.array.dobs);
      //  String[]  des = getResources().getStringArray(R.array.des);
     //   TypedArray icons = getResources().obtainTypedArray(R.array.icons);

      //  for (int i = 0; i < names.length; i++) {
      //      cardList.add(new Product(names[i], dobs[i], des[i],icons.getResourceId(i, 0)));
      //  }

      //  icons.recycle();



        Spinner dateSpinner = findViewById(R.id.dateSpinner);
        Spinner timeSpinner = findViewById(R.id.timeSpinner);

        ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.date_array,
                android.R.layout.simple_spinner_item
        );
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(dateAdapter);

        // Create ArrayAdapter for the time Spinner
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.time_array,
                android.R.layout.simple_spinner_item
        );
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedDate = parentView.getItemAtPosition(position).toString();
                // Handle the selected date
                Toast.makeText(Confirmorder.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        // Set OnItemSelectedListener for the time Spinner
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedTime = parentView.getItemAtPosition(position).toString();
                // Handle the selected time
                Toast.makeText(Confirmorder.this, "Selected Time: " + selectedTime, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
        toolbarButton=findViewById(R.id.toolbarButton);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity3();
            }
        });
    }
    public void openMainActivity3(){
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
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