package com.user.mystudio.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.mystudio.CheckNetwork;
import com.user.mystudio.Model_Schedule;
import com.user.mystudio.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 28/09/2017.
 */

public class AdminSchedule extends AppCompatActivity implements AdapterView.OnItemClickListener {
    CheckNetwork cn;
    Model_Schedule modelSchedule;
    private Context context = this;
    private ListView lv;
    List<HashMap<String, Object>> fillMaps = new ArrayList<>();
    Adapter adapter;
    Map map;
    public FirebaseAuth fAuth;
    public FirebaseAuth.AuthStateListener fStateListener;
    public final String TAG = getClass().getSimpleName();
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_schedule_list);
        lv = (ListView) findViewById(R.id.sch_listView);
        cn = new CheckNetwork(this);
        if (!cn.isConnected()) {
            Toast.makeText(this, "You are not connected internet. Pease check your connection!", Toast.LENGTH_LONG).show();
        }

        getSchedule();
    }

    public void getSchedule() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference booking = database.getReference("booking");
        booking.orderByChild("Tanggal").addValueEventListener(new ValueEventListener() {
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
                            public void onDataChange(DataSnapshot dataSnapshot) {
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

    public void loadSchedule(String key, String date, String time, String lokasi, String pemesan, final String status) {
        map = new HashMap();
        map.put("date", date);
        map.put("time", time);
        map.put("lokasi", lokasi);
        map.put("pemesan", pemesan);
        map.put("status", status);
        map.put("key", key);
        fillMaps.add((HashMap) map);
        adapter = new SimpleAdapter(getBaseContext(), fillMaps, R.layout.admin_activity_schedule,
                new String[]{"date", "time", "lokasi", "status", "pemesan"},
                new int[]{R.id.minsch_date, R.id.minsch_time, R.id.minsch_location, R.id.minsch_stats, R.id.minsch_name});
        lv.setAdapter((ListAdapter) adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.e(TAG, "onItemClick: "+fillMaps.get(position) );
                Log.e(TAG, "onItemClick: "+fillMaps.get(position).get("date") );
                fillMaps.get(position);
                LinearLayout layoutinput = new LinearLayout(context);   //layout
                layoutinput.setOrientation(LinearLayout.VERTICAL);
                layoutinput.setPadding(50, 50, 50, 50);

                if (fillMaps.get(position).get("status").toString().equalsIgnoreCase("Menunggu Persetujuan Admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Booking Detail");
                    builder.setMessage(fillMaps.get(position).get("date") + " " +
                            fillMaps.get(position).get("time") + "\n" +
                            fillMaps.get(position).get("pemesan") + "\n" +
                            fillMaps.get(position).get("lokasi") + "\n" +
                            fillMaps.get(position).get("status") + "\n" +
                            "* * * * *" + "\n" +
                            "Pemesan menunggu approval anda.");
                    builder.setView(layoutinput);
                    //posstive button
                    builder.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference booking = database.getReference("booking");
                            booking.child(fillMaps.get(position).get("key").toString())
                                    .child("Status").setValue("Disetujui Admin");
                            startActivity(new Intent(AdminSchedule.this, AdminSchedule.class));
                            finish();
                        }
                    });
                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNeutralButton("Delete Order", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference booking = database.getReference("booking");
                            booking.child(fillMaps.get(position).get("key").toString()).removeValue();
                            startActivity(new Intent(AdminSchedule.this, AdminSchedule.class));
                            finish();
                        }
                    });
                    builder.show();
                } else if (fillMaps.get(position).get("status").toString().equalsIgnoreCase("Disetujui Admin")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Booking Detail");
                    builder.setMessage(fillMaps.get(position).get("date") + " " +
                            fillMaps.get(position).get("time") + "\n" +
                            fillMaps.get(position).get("pemesan") + "\n" +
                            fillMaps.get(position).get("lokasi") + "\n" +
                            fillMaps.get(position).get("status") + "\n" +
                            "* * * * *" + "\n" +
                            "Apakah booking telah terpenuhi?");
                    builder.setView(layoutinput);
                    //posstive button
                    builder.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference booking = database.getReference("booking");
                            booking.child(fillMaps.get(position).get("key").toString())
                                    .child("Status").setValue("Booking telah selesai");
                            startActivity(new Intent(AdminSchedule.this, AdminSchedule.class));
                            finish();
                        }
                    });
                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNeutralButton("Booking Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference booking = database.getReference("booking");
                            booking.child(fillMaps.get(position).get("key").toString()).removeValue();
                            startActivity(new Intent(AdminSchedule.this, AdminSchedule.class));
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Booking Detail");
                    builder.setMessage(fillMaps.get(position).get("date") + " " +
                            fillMaps.get(position).get("time") + "\n" +
                            fillMaps.get(position).get("pemesan") + "\n" +
                            fillMaps.get(position).get("lokasi") + "\n" +
                            fillMaps.get(position).get("status") + "\n" +
                            "* * * * *" + "\n" +
                            "Booking telah selesai. Selamat.");
                    builder.setView(layoutinput);
                    //posstive button
                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
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
        builder.setMessage("Sorry, you don't have an order yet.");
        builder.setView(layoutinput);
        //negative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
