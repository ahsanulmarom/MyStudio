package com.user.mystudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class BookingPhoto extends AppCompatActivity {

    public FirebaseAuth fAuth;
    public FirebaseAuth.AuthStateListener fStateListener;
    public final String TAG = getClass().getSimpleName();
    CheckNetwork cn;
    MenuToolbar mt;
    Button booking;
    EditText date, time, alamat;
    RadioButton studio, alamatLain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingphoto);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_LONG).show();
        }
        booking = (Button) findViewById(R.id.booking_now);
        date = (EditText) findViewById(R.id.booking_date);
        time = (EditText) findViewById(R.id.booking_time);
        alamat = (EditText) findViewById(R.id.booking_alamat);
        studio = (RadioButton) findViewById(R.id.radioStudio);
        alamatLain = (RadioButton) findViewById(R.id.radioLain);

        fAuth = FirebaseAuth.getInstance();
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User sedang login
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    booking.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if(studio.isSelected()) {
                                setBooking(date.getText().toString().trim(), time.getText().toString().trim(), "Studio");
                            } else if(alamatLain.isSelected()) {
                                setBooking(date.getText().toString().trim(), time.getText().toString().trim(),
                                        alamatLain.getText().toString().trim());
                            }
                        }
                    });

                } else {
                    // User sedang logout
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(BookingPhoto.this, Login.class));
                }
            }
        };

        mt = new MenuToolbar();
    }

    public void setBooking(final String tanggal, final String jam, final String lokasi) {
        final FirebaseUser user = fAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference booking = database.getReference("booking");
        booking.orderByChild("tanggal").equalTo(tanggal).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference bookingInfo = database.getReference("booking");
                    bookingInfo.child("tanggal").setValue(tanggal);
                    bookingInfo.child("tanggal").child("jam").setValue(jam);
                    Map data = new HashMap();
                    data.put("idUSer", user.getUid());
                    data.put("alamat", lokasi);
                    bookingInfo.child("tanggal").child("jam").setValue(data);
                    Toast.makeText(BookingPhoto.this, "Jadwal telah dipesan untuk Anda.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    booking.orderByChild("jam").equalTo(jam).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                FirebaseUser user = fAuth.getCurrentUser();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference bookingInfo = database.getReference("booking");
                                bookingInfo.child("tanggal").setValue(tanggal);
                                bookingInfo.child("tanggal").child("jam").setValue(jam);
                                Map data = new HashMap();
                                data.put("idUSer", user.getUid());
                                data.put("alamat", lokasi);
                                bookingInfo.child("tanggal").child("jam").setValue(data);
                                Toast.makeText(BookingPhoto.this, "Jadwal telah dipesan untuk Anda.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(BookingPhoto.this, "Maaf jadwal tidak tersedia, silakan pilih jadwal lain!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
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