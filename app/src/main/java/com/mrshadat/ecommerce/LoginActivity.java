package com.mrshadat.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrshadat.ecommerce.databinding.ActivityLoginBinding;
import com.mrshadat.ecommerce.model.Users;
import com.mrshadat.ecommerce.prevalent.Prevalent;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private String parentDbName = "Users";

    private ActivityLoginBinding loginBinding;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loadingBar = new ProgressDialog(this);

        loginBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        //initialize paper library
        Paper.init(this);

        loginBinding.textViewAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginBinding.button.setText(R.string.login_admin);
                loginBinding.textViewAdminLink.setVisibility(View.INVISIBLE);
                loginBinding.textViewNotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        loginBinding.textViewNotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBinding.button.setText(R.string.login);
                loginBinding.textViewAdminLink.setVisibility(View.VISIBLE);
                loginBinding.textViewNotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

    }

    private void loginUser() {
        String phoneNumber = loginBinding.loginEditTextPhoneNumber.getText().toString();
        String password = loginBinding.loginEditTextPassword.getText().toString();

        if (TextUtils.isEmpty(phoneNumber)) {
            loginBinding.loginEditTextPhoneNumber.setError("please provide phone number");
        } else if (TextUtils.isEmpty(password)) {
            loginBinding.loginEditTextPassword.setError("please set account password");
        } else {

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait while we are checking credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            loginUserToAccount(phoneNumber, password);
        }
    }

    private void loginUserToAccount(final String phoneNumber, final String password) {

        if (loginBinding.checkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.USER_PHONE_KEY, phoneNumber);
            Paper.book().write(Prevalent.USER_PASSWORD_KEY, password);

        }

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phoneNumber).exists()) {
                    Users userData = dataSnapshot.child(parentDbName).child(phoneNumber).getValue(Users.class);

                    //  Log.d(TAG, "onDataChange: " + phoneNumber + password);

                    assert userData != null;
                    if (userData.getPhone().equals(phoneNumber)) {
                        if (userData.getPassword().equals(password)) {
                            if (parentDbName.equals("Admins")) {
                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged is successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCatagoryActivity.class);
                                startActivity(intent);
                            } else if (parentDbName.equals("Users")) {
                                Toast.makeText(LoginActivity.this, "logged is successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}