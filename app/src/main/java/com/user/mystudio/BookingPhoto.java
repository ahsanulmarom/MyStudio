package com.user.mystudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class BookingPhoto extends MenuAct {

    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private EditText date, time, alamat;
    private RadioButton studio, alamatLain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingphoto);
        CheckNetwork cn = new CheckNetwork(this);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_LONG).show();
        }
        checkSession();

        Button booking = (Button) findViewById(R.id.booking_now);
        Button schedule = (Button) findViewById(R.id.booking_schedule);
        date = (EditText) findViewById(R.id.booking_date);
        time = (EditText) findViewById(R.id.booking_time);
        alamat = (EditText) findViewById(R.id.booking_alamat);
        studio = (RadioButton) findViewById(R.id.radioStudio);
        alamatLain = (RadioButton) findViewById(R.id.radioLain);

        booking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (date.getText().toString().equalsIgnoreCase("")) {
                    date.setError("This Field is Required");
                } else if(date.getText().toString().length() != 10) {
                    date.setError("Isi sesuai Format dd/mm/yyy");
                } else if(Integer.parseInt(date.getText().toString().substring(0,2)) > 30) {
                    date.setError("Maksimal tanggal 30");
                } else if(Integer.parseInt(date.getText().toString().substring(3,5)) > 12) {
                    date.setError("Maksimal bulan adalah 12");
                } else if(Integer.parseInt(date.getText().toString().substring(6,10)) > 2018) {
                    date.setError("Maksimal tahun adalah 2018");
                } else if (!(date.getText().toString().substring(2,3).equals("/"))) {
                    date.setError("Isi sesuai Format dd/mm/yyyy");
                } else if (!(date.getText().toString().substring(5,6).equals("/"))) {
                    date.setError("Isi sesuai Format dd/mm/yyyy");
                } else if (time.getText().toString().equalsIgnoreCase("")) {
                    time.setError("This Field is Required");
                } else if (time.getText().toString().length() != 5) {
                    time.setError("Isi sesuai Format hh:mm");
                } else if (Integer.parseInt(time.getText().toString().substring(0,2)) < 0) {
                    time.setError("Jam salah");
                } else if (Integer.parseInt(time.getText().toString().substring(0,2)) > 23) {
                    time.setError("Jam salah");
                } else if (Integer.parseInt(time.getText().toString().substring(3,5)) < 0) {
                    time.setError("Jam salah");
                } else if (Integer.parseInt(time.getText().toString().substring(3,5)) > 59) {
                    time.setError("Jam salah");
                } else if (!(time.getText().toString().substring(2,3).equals(":"))) {
                    time.setError("Format jam salah");
                } else if(studio.isChecked()) {
                        setBooking(date.getText().toString().trim(), time.getText().toString().trim(), "Studio");
                } else if(alamatLain.isChecked()){
                    if (alamat.getText().toString().trim().equalsIgnoreCase("")) {
                        alamat.setError("This Field is Required");
                    } else {
                        setBooking(date.getText().toString().trim(), time.getText().toString().trim(),
                                alamat.getText().toString().trim());
                    }
                }
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               startActivity(new Intent(BookingPhoto.this, Schedule.class));
            }
        });
        setToolbar();
    }

    public void setBooking(final String tanggal, final String jam, final String lokasi) {
        final FirebaseUser user = fAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference bookingan = database.getReference("booking");
        assert user != null;
        DatabaseReference userInfo = database.getReference("userData").child(user.getUid());
        userInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String pemesan = dataSnapshot.child("name").getValue(String.class);
                bookingan.orderByChild("Tanggal").equalTo(date.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshotBooking) {
                        if (dataSnapshotBooking.getValue() == null) {
                            bookingan.push().setValue(saveDataBooking(user.getUid(), pemesan, tanggal, jam, lokasi));
                            Toast.makeText(BookingPhoto.this, "Jadwal telah dipesan untuk Anda.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            bookingan.orderByChild("Jam").equalTo(time.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() == null) {
                                        bookingan.push().setValue(saveDataBooking(user.getUid(), pemesan, tanggal, jam, lokasi));
                                        Toast.makeText(BookingPhoto.this, "Jadwal telah dipesan untuk Anda.",
                                                Toast.LENGTH_SHORT).show();
                                        recreate();
                                    } else {
                                        Toast.makeText(BookingPhoto.this, "Jadwal tidak tersedia. Silakan pilih jadwal lain!",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public Map<String, String> saveDataBooking(String id, String pemesan, final String tanggal, final String jam, final String lokasi) {
        Map<String, String> data = new HashMap<>();
        data.put("idPemesan", id);
        data.put("Pemesan", pemesan);
        data.put("Tanggal", tanggal);
        data.put("Jam", jam);
        data.put("Alamat", lokasi);
        data.put("Status", "Menunggu Persetujuan Admin");
        return data;
    }

    public void checkSession() {
        fAuth = FirebaseAuth.getInstance();
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                } else {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(fStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fStateListener != null) {
            fAuth.removeAuthStateListener(fStateListener);
        }
    }
}