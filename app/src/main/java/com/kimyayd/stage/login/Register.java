package com.kimyayd.stage.login;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kimyayd.stage.utils.FirebaseMethods;
import com.kimyayd.stage.R;

public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    private Context mContext;
    private String email, fullname, password,cpassword;
    private EditText mEmail, mPassword, mFullname,mCPassword;
    private TextView mSignUp,mSignIn;
    private ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private String append = "";
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = Register.this;
        firebaseMethods = new FirebaseMethods(mContext);
        Log.d(TAG, "onCreate: started.");
        initWidgets();
        setupFirebaseAuth();
        init();
    }

    private void init(){
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
        mSignUp.setOnClickListener(v -> {
            email = mEmail.getText().toString();
            fullname = mFullname.getText().toString();
            password = mPassword.getText().toString();
            cpassword =mCPassword.getText().toString();
            if(checkInputs(email, fullname, password)){
                if(password.equals(cpassword)) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    firebaseMethods.registerNewEmail(email, password);

                }else{
                    Toast.makeText(mContext, "The password are not corresponding", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkInputs(String email, String fullname, String password){
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if(email.equals("") || fullname.equals("") || password.equals("")){
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /**
     * Initialize the activity widgets
     */
    private void initWidgets(){
        Log.d(TAG, "initWidgets: Initializing Widgets.");
        mEmail = findViewById(R.id.email);
        mFullname =  findViewById(R.id.fullname);
        mSignUp =  findViewById(R.id.signup);
        mSignIn =  findViewById(R.id.txt_signin);
        mProgressBar =  findViewById(R.id.progressBar);
        mPassword =  findViewById(R.id.pass);
        mCPassword =  findViewById(R.id.cpass);
        mContext = Register.this;
        mProgressBar.setVisibility(View.GONE);
    }


    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        firebaseMethods.addNewUser(email,fullname,"participant");
                        Toast.makeText(mContext, "Signup successful, Sending verfication email", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                finish();

            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}