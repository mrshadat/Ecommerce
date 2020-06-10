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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrshadat.ecommerce.databinding.ActivityRegisterBinding;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding registerBinding;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        loadingBar = new ProgressDialog(this);

        registerBinding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createAccount();
            }
        });
    }

    private void createAccount() {

        String username = registerBinding.registerEditTextUsername.getText().toString();
        String phoneNumber = registerBinding.registerEditTextPhoneNumber.getText().toString();
        String password = registerBinding.registerEditTextPassword.getText().toString();

        if(TextUtils.isEmpty(username)) {
            registerBinding.registerEditTextUsername.setError("please provide valid username");
        } else if(TextUtils.isEmpty(phoneNumber)) {
            registerBinding.registerEditTextPhoneNumber.setError("please provide phone number");
        } else if(TextUtils.isEmpty(password)) {
            registerBinding.registerEditTextPassword.setError("please set account password");
        } else {

            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait while we check credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            validateNewAccount(username, phoneNumber, password);
        }
    }

    private void validateNewAccount(final String username, final String phoneNumber, final String password) {
         final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

         rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 if(!(dataSnapshot.child("Users").child(phoneNumber).exists())) {

                     HashMap<String, Object> userDataMap = new HashMap<>();
                     userDataMap.put("phone", phoneNumber);
                     userDataMap.put("name", username);
                     userDataMap.put("password", password);

                     rootRef.child("Users").child(phoneNumber).updateChildren(userDataMap)
                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Congratulations! Account created successfully", Toast.LENGTH_SHORT)
                                                    .show();
                                            loadingBar.dismiss();

                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);

                                        }

                                        else {
                                            loadingBar.dismiss();
                                            Toast.makeText(RegisterActivity.this, "Error! Please try again later", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                 }
                             });
                 }
                 else {

                     loadingBar.dismiss();
                     Toast.makeText(RegisterActivity.this, "Account exists! Try with another number", Toast.LENGTH_LONG).show();

                     Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                     startActivity(intent);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }
}