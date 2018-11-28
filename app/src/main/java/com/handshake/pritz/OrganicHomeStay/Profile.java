package com.handshake.pritz.OrganicHomeStay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {
    public DatabaseReference mdatabase;
    TextView nme, eme, phe;
    public String uid;
    private FirebaseAuth g;
    FirebaseUser user;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        g = FirebaseAuth.getInstance();
        nme = findViewById(R.id.nme);
        eme = findViewById(R.id.eme);
        phe = findViewById(R.id.phe);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
            mdatabase = FirebaseDatabase.getInstance().getReference().child("User").child(uid);
            mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                      String nmeh = dataSnapshot.child("Name").getValue(String.class);
                    final String pheh = dataSnapshot.child("Phone").getValue(String.class);
                    final String emeh = dataSnapshot.child("Email").getValue(String.class);
                    //    final String mhe=dataSnapshot.child("Details").getValue().toString();
                    phe.setText(pheh);
                    eme.setText(emeh);
                    nme.setText(nmeh);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
}
