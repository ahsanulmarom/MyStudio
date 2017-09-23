package com.user.mystudio;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = Home.class.getSimpleName();
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fAuth = FirebaseAuth.getInstance();
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User sedang login
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Home.this, Menu.class);
                            startActivity(intent);
                            finish();
                        }
                    },SPLASH_TIME_OUT);
                } else {
                    // User sedang logout
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Home.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    },SPLASH_TIME_OUT);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(fStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fStateListener != null) {
            fAuth.removeAuthStateListener(fStateListener);
        }
    }
}
