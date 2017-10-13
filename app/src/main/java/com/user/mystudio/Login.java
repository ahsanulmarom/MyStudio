package com.user.mystudio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = SignUp.class.getSimpleName();
    private Context context = this;
    CheckNetwork cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cn = new CheckNetwork(this);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_LONG).show();
        }

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        Button submit = (Button) findViewById(R.id.login_btnLogin);
        Button daftar = (Button) findViewById(R.id.login_btnSignUp);

        fAuth = FirebaseAuth.getInstance();
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                } else {
                }
            }
        };

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (email.getText().toString().equalsIgnoreCase("")) {
                    email.setError("This Field is Required");
                } else if (password.getText().toString().equalsIgnoreCase("")) {
                    password.setError("This Field is Required");
                } else {
                    login(email.getText().toString(), password.getText().toString());
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
    }

    public void login(final String email, String password){
        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            if (!cn.isConnected()) {
                                Toast.makeText(Login.this, "You are offline. Pease check your connection!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "Sorry, Your Email or Password is Incorrect. Please try again!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (task.isSuccessful()) {
                            checkIfEmailVerified();
                            finish();
                        }
                    }
                });
    }

    private void checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        if (user.isEmailVerified()) {
                // user is verified, so you can finish this activity or send user to activity which you want.
            startActivity(new Intent(Login.this, MenuAct.class));
            Toast.makeText(Login.this, "You are logged in as " + user.getEmail(),
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(Login.this, "Sorry, Please Check Email and Verify Your Account!",
                    Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            recreate();
        }
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
