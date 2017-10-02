package com.user.mystudio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 27/09/2017.
 */

public class Schedule extends AppCompatActivity implements AdapterView.OnItemClickListener {
    CheckNetwork cn;
    Model_Schedule modelSchedule;
    private Context context;
    private ListView lv;
    List<HashMap<String, Object>> fillMaps;
    Adapter adapter;
    Map map;
    public FirebaseAuth fAuth;
    public FirebaseAuth.AuthStateListener fStateListener;
    public String TAG;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        lv = (ListView) findViewById(R.id.sch_listView);
        context = Schedule.this;
        fillMaps = new ArrayList<>();
        TAG = Schedule.class.getSimpleName();
        cn = new CheckNetwork(this);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_LONG).show();
        }
        checkSession();

        getSchedule();
        setToolbar();
    }

    public void getSchedule() {
        final FirebaseUser user = fAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference booking = database.getReference("booking");
        booking.orderByChild("idPemesan").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    nullSchedule();
                } else {
                    for (final DataSnapshot snap : dataSnapshot.getChildren()) {
                        Log.e(TAG, "onDataChange: testkey " + snap.getKey());
                        final String key = snap.getKey();
                        booking.child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                modelSchedule = new Model_Schedule(dataSnapshot.child("Tanggal").getValue(String.class),
                                        dataSnapshot.child("Jam").getValue(String.class),
                                        dataSnapshot.child("Alamat").getValue(String.class),
                                        dataSnapshot.child("Pemesan").getValue(String.class),
                                        dataSnapshot.child("Status").getValue(String.class));
                                        loadSchedule(key, modelSchedule.getDate(), modelSchedule.getTime(), modelSchedule.getLokasi(),
                                                modelSchedule.getPemesan(), modelSchedule.getStatus());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void loadSchedule(final String key, final String date, final String time, final String lokasi, String pemesan, final String status) {
        map = new HashMap();
        map.put("date", date);
        map.put("time", time);
        map.put("lokasi", lokasi);
        map.put("pemesan", pemesan);
        map.put("status", status);
        map.put("key", key);
        fillMaps.add((HashMap) map);
        adapter = new SimpleAdapter(this, fillMaps, R.layout.activity_schedule,
                new String[]{"date", "time", "lokasi", "status",},
                new int[]{R.id.sch_date, R.id.sch_time, R.id.sch_location, R.id.sch_stats});
        lv.setAdapter((ListAdapter) adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.e(TAG, "onItemClick: "+fillMaps.get(position) );
                Log.e(TAG, "onItemClick: "+fillMaps.get(position).get("date") );
                final String kunci = fillMaps.get(position).get("key").toString();
                LinearLayout layoutinputSchedule = new LinearLayout(context);   //layout
                layoutinputSchedule.setOrientation(LinearLayout.VERTICAL);
                layoutinputSchedule.setPadding(50, 50, 50, 50);

                if (fillMaps.get(position).get("status").toString().equalsIgnoreCase("Menunggu Persetujuan Admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("My Detail Schedule");
                    builder.setMessage(fillMaps.get(position).get("date") + " " +
                            fillMaps.get(position).get("time") + "\n" +
                            fillMaps.get(position).get("lokasi") + "\n" +
                            fillMaps.get(position).get("status") + "\n" +
                            "* * * * *" + "\n" +
                            "Anda dapat membatalkan SEBELUM mendapat approval admin.");
                    builder.setView(layoutinputSchedule);
                    //posstive button
                    builder.setPositiveButton("Cancel Booking", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference booking = database.getReference("booking");
                            recreate();
                            booking.child(kunci).child("Status").setValue("Canceled By User");
                            Log.e(TAG, "onClick: hapus data " +  fillMaps.get(position).get("key").toString());
                        }
                    });
                    builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else if(fillMaps.get(position).get("status").toString().equalsIgnoreCase("Disetujui Admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("My Detail Schedule");
                    builder.setMessage(fillMaps.get(position).get("date") + " " +
                            fillMaps.get(position).get("time") + "\n" +
                            fillMaps.get(position).get("lokasi") + "\n" +
                            fillMaps.get(position).get("status") + "\n" +
                            "* * * * *" + "\n" +
                            "Pesanan telah dikonfirmasi admin. Pesanan tidak dapat dibatalkan.");
                    builder.setView(layoutinputSchedule);
                    //posstive button
                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Booking Detail");
                    builder.setMessage(fillMaps.get(position).get("date") + " " +
                            fillMaps.get(position).get("time") + "\n" +
                            fillMaps.get(position).get("lokasi") + "\n" +
                            fillMaps.get(position).get("status") + "\n" +
                            "* * * * *" + "\n" +
                            "Booking telah selesai. Selamat.");
                    builder.setView(layoutinputSchedule);
                    //posstive button
                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            }
        });
    }

    public void nullSchedule() {
        LinearLayout layoutinput = new LinearLayout(context);   //layout
        layoutinput.setOrientation(LinearLayout.VERTICAL);
        layoutinput.setPadding(50, 50, 50, 50);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Schedule");
        builder.setMessage("Sorry, you don't have a schedule yet. You can add schedule by click Booking Photo below.");
        builder.setView(layoutinput);
        //negative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //posstive button
        builder.setPositiveButton("Booking Photo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Schedule.this, BookingPhoto.class));
                finish();
            }
        });
        builder.show();
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
                finish();
                return true;
            case R.id.nav_booking:
                startActivity(new Intent(getApplication(), BookingPhoto.class));
                finish();
                return true;
            case R.id.nav_schedule:
                startActivity(new Intent(getApplication(), Schedule.class));
                finish();
                return true;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Schedule.this, Login.class));
                finish();
                return true;
            default:
                Toast.makeText(getApplication(), "Kesalahan Terjadi ", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    public void checkSession() {
        fAuth = FirebaseAuth.getInstance();
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null)
                    startActivity(new Intent(getApplicationContext(), Login.class));
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
