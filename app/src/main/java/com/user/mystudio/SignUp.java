package com.user.mystudio;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText username, email, password, repassword;
    String uname, mail;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = SignUp.class.getSimpleName();
    CheckNetwork cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = (EditText) findViewById(R.id.signup_username);
        uname = username.getText().toString().trim();
        email = (EditText) findViewById(R.id.signup_email);
        mail = email.getText().toString().trim();
        password = (EditText) findViewById(R.id.signup_password);
        repassword = (EditText) findViewById(R.id.signup_repassword);
        Button submit = (Button) findViewById(R.id.signup_submit);

        cn = new CheckNetwork(this);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Please check your connection!", Toast.LENGTH_SHORT).show();
        }

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
                    if (username.getText().toString().equalsIgnoreCase("")) {
                        username.setError("This Field is Required");
                    } else if (username.getText().toString().length() < 5) {
                        username.setError("Name must be at least 5 characters");
                    } else if (email.getText().toString().equalsIgnoreCase("")) {
                        email.setError("This Field is Required");
                    } else if (password.getText().toString().equalsIgnoreCase("")) {
                        password.setError("This Field is Required");
                    } else if (password.getText().toString().length() < 5) {
                        password.setError("Password must be at least 5 characters");
                    } else if (!(repassword.getText().toString().equals(password.getText().toString()))) {
                        repassword.setError("Please check your password!");
                    } else {
                        signUp(email.getText().toString(), password.getText().toString());
                    }

                }
            });
    }

    private void signUp(final String mail, String password) {
        fAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            if (!cn.isConnected()) {
                                Toast.makeText(SignUp.this, "You are offline. Please check your connection!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUp.this, "Failed to sign up. Email has been registered.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (task.isSuccessful()) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            assert user != null;
                            DatabaseReference userData = database.getReference("userData").child(user.getUid());
                            Map<String, String> data = new HashMap<>();
                            data.put("id", user.getUid());
                            data.put("email", email.getText().toString().trim());
                            data.put("name", username.getText().toString().trim());
                            userData.setValue(data);
                            Toast.makeText(SignUp.this, "Sign up Successfully. Please check email to verify account!",
                                    Toast.LENGTH_SHORT).show();
                            sendVerificationEmail();
                        }
                    }
                });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(SignUp.this, Login.class));
                            finish();
                        }
                        else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(fStateListener);
    }

    @Override
    public void onResume(){
        super.onResume();
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
