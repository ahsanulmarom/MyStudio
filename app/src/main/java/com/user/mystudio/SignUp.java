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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText username, email, password, repassword;
    String uname, mail;
    private Button submit;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = SignUp.class.getSimpleName();
    CheckNetwork cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        cn = new CheckNetwork(this);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_SHORT).show();
        }
            fAuth = FirebaseAuth.getInstance();
            fStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User sedang login
                        if (!(user.isEmailVerified())) {
                            sendVerificationEmail();
                        }
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    } else {
                        // User sedang logout
                        Log.d(TAG, "onAuthStateChanged:signed_out");
                    }
                }
            };

            username = (EditText) findViewById(R.id.signup_username);
            uname = username.getText().toString().trim();
            email = (EditText) findViewById(R.id.signup_email);
            mail = email.getText().toString().trim();
            password = (EditText) findViewById(R.id.signup_password);
            repassword = (EditText) findViewById(R.id.signup_repassword);
            submit = (Button) findViewById(R.id.signup_submit);

            submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (username.getText().toString().equalsIgnoreCase("")) {
                        username.setError("This Field is Required");
                    } else if (username.getText().toString().length() < 5) {
                        username.setError("Username must be at least 5 characters");
                    } else if (email.getText().toString().equalsIgnoreCase("")) {
                        email.setError("This Field is Required");
                    } else if (password.getText().toString().equalsIgnoreCase("")) {
                        password.setError("This Field is Required");
                    } else if (password.getText().toString().length() < 5) {
                        password.setError("Password must be at least 5 characters");
                    } else if (!(repassword.getText().toString().equals(password.getText().toString()))) {
                        repassword.setError("Please check your password!");
                    } else {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference usernama = database.getReference("userData");
                        usernama.orderByChild("username").equalTo(username.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e(TAG, "onDataChange: asasa " + dataSnapshot.getValue());
                                if (dataSnapshot.getValue() == null) {
                                    signUp(email.getText().toString(), password.getText().toString());
                                } else {
                                    Toast.makeText(SignUp.this, "Username already exist. Please try another username.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
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
                                Toast.makeText(SignUp.this, "You are offline. Pease check your connection!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(SignUp.this, "Failed to sign up. Email has been registered.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (task.isSuccessful()) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference userData = database.getReference("userData").child(user.getUid());
                            Map data = new HashMap();
                            data.put("email", email.getText().toString().trim());
                            data.put("username", username.getText().toString().trim());
                            data.put("displayName", 0);
                            userData.setValue(data);
                            Toast.makeText(SignUp.this, "Sign up Successfully. Please check email to verify account! ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
    protected void onStop() {
        super.onStop();
        if (fStateListener != null) {
            fAuth.removeAuthStateListener(fStateListener);
        }
    }
}
