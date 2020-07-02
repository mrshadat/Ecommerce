package com.mrshadat.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.IntArrayEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mrshadat.ecommerce.databinding.ActivityHomeBinding;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding homeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);


        homeBinding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}