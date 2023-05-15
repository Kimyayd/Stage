package com.kimyayd.stage.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kimyayd.stage.HomeActivity;
import com.kimyayd.stage.R;
import com.kimyayd.stage.models.User;
import com.kimyayd.stage.principal.CompanyMainActivity;
import com.kimyayd.stage.principal.MainActivity;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    private TextView mSignUp,mSignIn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = Login.this;
        setContentView(R.layout.activity_login);
        Toast.makeText(mContext, "View gone", Toast.LENGTH_SHORT).show();
        mProgressBar =  findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
        mEmail =  findViewById(R.id.login_email);
        mPassword =  findViewById(R.id.login_password);
        mSignUp =  findViewById(R.id.txt_signup);
        mSignIn =  findViewById(R.id.signin);
        Log.d(TAG, "onCreate: started.");
        mProgressBar.setVisibility(View.GONE);
        setupFirebaseAuth();
        init();
    }

    private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: checking string if null.");
        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    private void init() {

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to log in.");
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (isStringNull(email) || isStringNull(password)) {
                    Toast.makeText(mContext, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, task -> {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());

                                    Toast.makeText(Login.this, "Authentification failed",
                                            Toast.LENGTH_SHORT).show();
                                    mProgressBar.setVisibility(View.GONE);

                                } else {
                                    try {

                                        if (user.isEmailVerified()) {
                                            Log.d(TAG, "onComplete: success. email is verified.");
                                            mProgressBar.setVisibility(View.GONE);
                                            FirebaseDatabase.getInstance().getReference("users").child(user.getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    User user=snapshot.getValue(User.class);
                                                    if(user.getType().equals("participant")){
                                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                                        mEmail.setText("");
                                                        mPassword.setText("");
                                                        startActivity(intent);
                                                    }else {
                                                        Intent intent = new Intent(Login.this, CompanyMainActivity.class);
                                                        mEmail.setText("");
                                                        mPassword.setText("");
                                                        startActivity(intent);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });




                                        } else {
                                            Toast.makeText(mContext, "Email is not verified \n check your email inbox.", Toast.LENGTH_SHORT).show();
                                            mProgressBar.setVisibility(View.GONE);
                                            mAuth.signOut();
                                        }

                                    } catch (NullPointerException e) {
                                        Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage());
                                    }
                                }

                            });
                }

            }
        });

        mSignUp.setOnClickListener(v -> {
            Log.d(TAG, "onClick: navigating to register screen");
            Intent intent = new Intent(Login.this, ChoiceActivity.class);
            startActivity(intent);
        });


    }

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {

                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {

                Log.d(TAG, "onAuthStateChanged:signed_out");
            }

        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(Login.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }    @Override
    protected void onResume() {
        super.onResume();

        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            finish();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

