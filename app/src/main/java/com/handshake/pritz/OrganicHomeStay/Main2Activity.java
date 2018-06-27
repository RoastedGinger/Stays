package com.handshake.pritz.OrganicHomeStay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
Button b,gh,del,update,cncl,cnclbtn,delbtn;
EditText newPrice;
    AlertDialog.Builder builder=null;
    AlertDialog dialog=null;
    ProgressDialog progressDialog;
TextView hname,haddress,hprice,load;
ProgressBar progressBar;
DatabaseReference mref;
      String message;
      String root;
ImageView himage;
    String postid;
String aname,aaddress,aimage,aprice;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mauth= FirebaseAuth.getInstance();
        if(getIntent().getExtras()!=null) {
            Bundle bundle = getIntent().getExtras();
              message = bundle.getString("message");
             root = bundle.getString("keyroot");
            mref = FirebaseDatabase.getInstance().getReference().child(root);
        }
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                String ps=firebaseAuth.getCurrentUser().getEmail().toString();
                if (ps.equals("pritz@gmail.com")) {
                del.setVisibility(View.VISIBLE);
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        deluser();
                    }
                });
                update.setVisibility(View.VISIBLE);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddUser();
                    }
                });

                }

            }
        };
        progressDialog = new ProgressDialog(this);
        progressBar=findViewById(R.id.progressBar);
        b=findViewById(R.id.book);
        del=findViewById(R.id.del);
        update=findViewById(R.id.update);
        hname=findViewById(R.id.hname);
        load=findViewById(R.id.lod);
        himage=findViewById(R.id.himage);
        haddress=findViewById(R.id.haddress);
        hprice=findViewById(R.id.hprice);
        mref.child(message).addValueEventListener(new ValueEventListener() {
            @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
              aname=(String) dataSnapshot.child("Name").getValue();
              aaddress=(String) dataSnapshot.child("Homeaddress").getValue();
                aprice=(String) dataSnapshot.child("Price").getValue();
                aimage=(String) dataSnapshot.child("HomestayPic").getValue();
                postid=(String)dataSnapshot.child("postid").getValue();

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
                if(aprice!=null) {
                    Toast.makeText(Main2Activity.this, message, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Main2Activity.this, Form.class);
                    intent.putExtra("kyi", message);
                    intent.putExtra("kyiroot", "EastHomestays");
                    intent.putExtra("aname", aname);
                    intent.putExtra("aadress", aaddress);
                    intent.putExtra("aimage", aimage);
                    intent.putExtra("price", aprice);
                    startActivity(intent);
                }

            }
        });
del.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    }
});


    }
    @Override
    public void onStart() {
        super.onStart();
        mauth.addAuthStateListener(authStateListener);


        }
    public void AddUser()
    {   LayoutInflater linf=LayoutInflater.from(Main2Activity.this);
        View inflator=linf.inflate(R.layout.homeupdate,null);
        newPrice=inflator.findViewById(R.id.new_price);
        newPrice.setText(aprice);
        gh=inflator.findViewById(R.id.gh);
        cncl=inflator.findViewById(R.id.cncl);
        builder=new AlertDialog.Builder(Main2Activity.this);
    //    builder.setTitle("Update Price...");
        builder.setView(inflator);
        dialog=builder.create();
        dialog.show();
        cncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = newPrice.getText().toString();
               progressDialog.setMessage("Updating...");
                progressDialog.show();
                //progressDialog.setCancelable(false);
                mref.child(message).child("Price").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            progressDialog.dismiss();
                            dialog.dismiss();
                            dialog.cancel();
                            Toast.makeText(Main2Activity.this, "Successfully Updated...", Toast.LENGTH_LONG).show();


                        } else {

                            Toast.makeText(Main2Activity.this, "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

     }
    public void deluser()
    {   LayoutInflater linf=LayoutInflater.from(Main2Activity.this);
        View inflator=linf.inflate(R.layout.homedelete,null);
        newPrice=inflator.findViewById(R.id.new_price);
        delbtn=inflator.findViewById(R.id.delbtn);
        cnclbtn=inflator.findViewById(R.id.cnclbtn);
        cnclbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        builder=new AlertDialog.Builder(Main2Activity.this);
        //    builder.setTitle("Update Price...");
        builder.setView(inflator);
        dialog=builder.create();

        dialog.show();
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Deleting HomeStays...");
                progressDialog.show();
                //progressDialog.setCancelable(false);
                mref.child(message).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            progressDialog.dismiss();
                            dialog.dismiss();
                            dialog.cancel();
                            Toast.makeText(Main2Activity.this, "Successfully Deleted...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                            finish();
                            startActivity(intent);



                        } else {

                            Toast.makeText(Main2Activity.this, "There was some error in saving Changes.", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    }
     }

