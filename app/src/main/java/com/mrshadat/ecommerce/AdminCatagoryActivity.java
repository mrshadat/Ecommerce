package com.mrshadat.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mrshadat.ecommerce.databinding.ActivityAdminCatagoryBinding;

public class AdminCatagoryActivity extends AppCompatActivity {

    private ActivityAdminCatagoryBinding categoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_catagory);


        categoryBinding.jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(AdminCatagoryActivity.this, AdminAddNewProductActivity.class);
                    intent.putExtra("category", "Jacket");
                    startActivity(intent);
            }
        });

        categoryBinding.shoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCatagoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Shoe");
                startActivity(intent);
            }
        });

        categoryBinding.computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCatagoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Computer");
                startActivity(intent);
            }
        });

        categoryBinding.glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCatagoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });

        categoryBinding.hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCatagoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Hat");
                startActivity(intent);
            }
        });

        categoryBinding.audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCatagoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Audio");
                startActivity(intent);
            }
        });
    }
}