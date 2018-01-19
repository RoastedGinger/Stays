package com.handshake.pritz.organichome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
Button b;
TextView hname,haddress,hprice,load;
ProgressBar progressBar;
DatabaseReference mref;
ImageView himage;
String aname,aaddress,aimage,aprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("message");
        final String root = bundle.getString("keyroot");
         mref = FirebaseDatabase.getInstance().getReference().child(root);
         progressBar=findViewById(R.id.progressBar);
        b=findViewById(R.id.book);
        hname=findViewById(R.id.hname);
        load=findViewById(R.id.lod);
        himage=findViewById(R.id.himage);
        haddress=findViewById(R.id.haddress);
        hprice=findViewById(R.id.hprice);
        mref.child(message).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
           aname=(String) dataSnapshot.child("Name").getValue();
           aaddress=(String) dataSnapshot.child("Address").getValue();
                aprice=(String) dataSnapshot.child("Price").getValue();
                aimage=(String) dataSnapshot.child("HomestayPic").getValue();

                // Show progress bar
                progressBar.setVisibility(View.VISIBLE);
// Hide progress bar on successful load
                Picasso.with(Main2Activity.this).load(aimage)
                        .into(himage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.GONE);
                                }
                                if (load != null) {
                                    load.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });

             // Picasso.with(Main2Activity.this).load(aimage).into(himage);
                hname.setText(aname);
                haddress.setText(aaddress);
                hprice.setText(aprice+"/-");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Main2Activity.this, message, Toast.LENGTH_LONG).show();

                Intent i = new Intent(Main2Activity.this, Form.class);
                startActivity(i);

            }
        });



    }
}
