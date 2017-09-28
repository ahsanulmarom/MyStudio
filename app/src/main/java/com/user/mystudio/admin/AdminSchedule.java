package com.user.mystudio.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Adapter;
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

public class AdminSchedule extends AppCompatActivity {
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
                        String key = snap.getKey();
                        booking.child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                modelSchedule = new Model_Schedule(dataSnapshot.child("Tanggal").getValue(String.class),
                                        dataSnapshot.child("Jam").getValue(String.class),
                                        dataSnapshot.child("Alamat").getValue(String.class),
                                        dataSnapshot.child("Pemesan").getValue(String.class),
                                        dataSnapshot.child("Status").getValue(String.class));
                                loadSchedule(modelSchedule.getDate(), modelSchedule.getTime(), modelSchedule.getLokasi(),
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

    public void loadSchedule(String date, String time, String lokasi, String pemesan, String status) {
        map = new HashMap();
        map.put("date", date);
        map.put("time", time);
        map.put("lokasi", lokasi);
        map.put("pemesan", pemesan);
        map.put("status", status);
        fillMaps.add((HashMap) map);
        adapter = new SimpleAdapter(getBaseContext(), fillMaps, R.layout.admin_activity_schedule,
                new String[]{"date", "time", "lokasi", "status", "pemesan"},
                new int[]{R.id.minsch_date, R.id.minsch_time, R.id.minsch_location, R.id.minsch_stats, R.id.minsch_name});
        lv.setAdapter((ListAdapter) adapter);
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
}
