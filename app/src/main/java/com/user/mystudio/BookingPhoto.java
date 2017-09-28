package com.user.mystudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
    private Button booking, schedule;
    EditText date, time, alamat;
    RadioButton studio, alamatLain;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookingphoto);
        cn = new CheckNetwork(this);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_LONG).show();
        }
        checkSession();

        booking = (Button) findViewById(R.id.booking_now);
        schedule = (Button) findViewById(R.id.booking_schedule);
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
                    date.setError("Isi sesuai Format dd-mm-yyy");
                } else if ((!(date.getText().toString().substring(2,3).equals("-"))) && (!(date.getText().toString().substring(5,6).equals("-")))) {
                    date.setError("Isi sesuai Format dd-mm-yyy");
                } else if (time.getText().toString().equalsIgnoreCase("")) {
                    time.setError("This Field is Required");
                } else if (time.getText().toString().length() != 5) {
                    time.setError("Isi sesuai Format hh:mm");
                } else {
                    if(studio.isChecked()) {
                        setBooking(date.getText().toString().trim(), time.getText().toString().trim(), "Studio");
                        Log.e(TAG, "onClick: dipilih" );
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
                finish();
            }
        });
        setToolbar();
    }

    public void setBooking(final String tanggal, final String jam, final String lokasi) {
        Log.e(TAG, "setBooking: berhasil masuk sini" );
        final FirebaseUser user = fAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference booking = database.getReference("booking");
        booking.orderByChild("Tanggal").equalTo(date.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: data = " + dataSnapshot.getValue() );
                if (dataSnapshot.getValue() == null) {
                    saveDataBooking(tanggal, jam, lokasi);
                } else {
                    booking.orderByChild("Jam").equalTo(time.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                saveDataBooking(tanggal, jam, lokasi);
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

    public void saveDataBooking(final String tanggal, final String jam, final String lokasi) {
        final FirebaseUser user = fAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference bookingInfo = database.getReference("booking");
        final DatabaseReference userInfo = database.getReference("userData").child(user.getUid());
        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pemesan = dataSnapshot.child("name").getValue(String.class);
                Map data = new HashMap();
                data.put("idPemesan", user.getUid());
                data.put("Pemesan", pemesan);
                data.put("Tanggal", tanggal);
                data.put("Jam", jam);
                data.put("Alamat", lokasi);
                data.put("Status", "Menunggu Persetujuan Admin");
                bookingInfo.push().setValue(data);
                Toast.makeText(BookingPhoto.this, "Jadwal telah dipesan untuk Anda.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkSession() {
        fAuth = FirebaseAuth.getInstance();
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User sedang login
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User sedang logout
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        };
    }

    public void setToolbar() {
        // Menginisiasi Toolbar dan mensetting sebagai actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Menginisiasi  NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Mengatur Navigasi View Item yang akan dipanggil untuk menangani item klik menu navigasi
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Memeriksa apakah item tersebut dalam keadaan dicek  atau tidak,
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Menutup  drawer item klik
                drawerLayout.closeDrawers();
                //Memeriksa untuk melihat item yang akan dilklik dan melalukan aksi
                toolbarNav(menuItem);
                return true;
            }
        });
        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Kode di sini akan merespons setelah drawer menutup disini kita biarkan kosong
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //  Kode di sini akan merespons setelah drawer terbuka disini kita biarkan kosong
                super.onDrawerOpened(drawerView);
            }
        };
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();
    }

    public boolean toolbarNav(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // pilihan menu item navigasi akan menampilkan pesan toast klik kalian bisa menggantinya
            //dengan intent activity
            case R.id.nav_home:
                startActivity(new Intent(getApplication(), Menu.class));
                return true;
            case R.id.nav_booking:
                startActivity(new Intent(getApplication(), BookingPhoto.class));
                return true;
            case R.id.nav_schedule:
                startActivity(new Intent(getApplication(), Schedule.class));
                return true;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                return true;
            default:
                Toast.makeText(getApplication(), "Kesalahan Terjadi ", Toast.LENGTH_SHORT).show();
                return true;
        }
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