package com.kimyayd.stage.home;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kimyayd.stage.HomeActivity;
import com.kimyayd.stage.R;
import com.kimyayd.stage.models.Organisation;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Context mContext = getApplicationContext();
    private EditText mSearchParam;
    private ListView mListView;
    private List<Organisation> mOrganisationList;
    private OrganisationListAdapter adapter;
    private static final String TAG = "SearchActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSearchParam =  findViewById(R.id.search);
        mListView =  findViewById(R.id.searchView);
        setupFirebaseAuth();
        readUsers();
        hideSoftKeyboard();
        initTextListener();
    }
    public void hideSoftKeyboard(){
        if(getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void initTextListener(){
        Log.d(TAG, "initTextListener: initializing");

        mOrganisationList = new ArrayList<>();

        mSearchParam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchForMatch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }
    private void searchForMatch(String keyword){
        FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("Organisations")
                .orderByChild("Fullname").startAt(keyword)
                .endAt(keyword +"\uf0ff");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!mSearchParam.getText().toString().equals("")) {
                    mOrganisationList.clear();
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        Organisation user = singleSnapshot.getValue(Organisation.class);
                        assert user != null;
                        assert fuser != null;
                        Log.d(TAG,"User... :" +user.getUser_id());
                        if (!user.getUser_id().equals(fuser.getUid())) {
                            mOrganisationList.add(user);
                        }
                        updateUsersList();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateUsersList(){
        Log.d(TAG, "updateUsersList: updating users list");
//        adapter = new OrganisationListAdapter(getContext(), R.layout.org_item, mOrganisationList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("intent_user", mOrganisationList.get(position));
            startActivity(intent);
        });
    }

    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
            // ...
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
    private void readUsers(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Organisations");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mOrganisationList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    Organisation user=snapshot1.getValue(Organisation.class);
                    assert user!=null;
                    assert firebaseUser!=null;

                    if(!user.getUser_id().equals(firebaseUser.getUid())){

                        mOrganisationList.add(user);

                    }
                }

                updateUsersList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}