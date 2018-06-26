package com.handshake.pritz.OrganicHomeStay;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
Button log;
    EditText new_usrname,new_pass;
    ProgressDialog progressDialog;
    private FirebaseAuth mauth;
    AlertDialog.Builder builder=null;
    AlertDialog dialog=null;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog=new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();

                }
                else {
                    Intent i = new Intent(MainActivity.this, Logi.class);
                    startActivity(i);
                    finish();
                }

            }
        };
        mauth.addAuthStateListener(authStateListener);
        Button button=findViewById(R.id.b);
        Button geyzing=findViewById(R.id.c);
        Button namhci=findViewById(R.id.d);
        Button mangan=findViewById(R.id.e);

        log=findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AddUser();
            }
        });

        geyzing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Geyzing.class);

                 startActivity(i);
            }
        });        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, East.class);
                 startActivity(i);
            }
        });
        namhci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Namchi.class);
                startActivity(i);
            }
        });
        mangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Mangan.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.profile) {
            Intent i = new Intent(MainActivity.this, Profile.class);
            startActivity(i);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.con) {
            String number="07585910557";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
        }
        if(id==R.id.abt) {
            Intent i = new Intent(MainActivity.this, About.class);
            startActivity(i);
        }


        if (id == R.id.mail) {

            String uriText =
                    "mailto:"+"organichomestays@gmail.com"+
                            "?subject=" + Uri.encode("From");
            Uri uri= Uri.parse(uriText);

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);

            Intent i = Intent.createChooser(emailIntent, "Send email to OrganicHomeStays");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
        }
        if (id == R.id.exit)
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            //builder.setTitle("Exit");
            builder.setMessage("Are you sure you want to Exit ?");
            builder.setPositiveButton("No",null);
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    //bt.disable();
                    finish();
                    System.exit(0);
                }
            });
            builder.create();
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void AddUser()
    {
        LayoutInflater linf=LayoutInflater.from(MainActivity.this);
        View inflator=linf.inflate(R.layout.update,null);
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Hey, Admin Login to Gain Access...");
        builder.setView(inflator);
        new_usrname=inflator.findViewById(R.id.new_usrname);
        new_pass=inflator.findViewById(R.id.new_pass);
         builder.setPositiveButton("Cancel",null);
        builder.setNegativeButton("Login",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {

            }
        });
        dialog=builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getusername=new_usrname.getText().toString().trim();
                String getuserpas=new_pass.getText().toString().trim();
                if (new_usrname.getText().toString().trim().equalsIgnoreCase("")) {
                    new_usrname.setError("This field can not be blank");}
                if (new_pass.getText().toString().trim().equalsIgnoreCase("")) {
                    new_pass.setError("This field can not be blank");}
                    if(!TextUtils.isEmpty(getusername)&&!TextUtils.isEmpty(getuserpas)){
                      progressDialog.setMessage("Please Wait...");
                          progressDialog.show();
            mauth.signInWithEmailAndPassword(getusername,getuserpas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful())

               {progressDialog.dismiss();

                   Toast.makeText(MainActivity.this, "Logged In...", Toast.LENGTH_LONG).show();

                   log.setVisibility(View.GONE);
               }
               else
               {
                   Toast.makeText(MainActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();

               }
                }
            });
                    }
            }
        });
        //builder.show();
    }
}
