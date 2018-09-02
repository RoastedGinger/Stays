package com.handshake.pritz.OrganicHomeStay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
private DatabaseReference mdatabase;
TextView nme,eme,phe;
    private FirebaseAuth mauth;
    FirebaseUser user;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mauth= FirebaseAuth.getInstance();
        nme=findViewById(R.id.nme);
        eme=findViewById(R.id.eme);
        phe=findViewById(R.id.phe);
       user=FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("User").child(uid);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String nmeh=  dataSnapshot.child("Name").getValue().toString();
                final String   pheh =  dataSnapshot.child("Phone").getValue().toString();
                final String  emeh= dataSnapshot.child("Email").getValue().toString();
                final String mhe=dataSnapshot.child("Details").getValue().toString();
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
