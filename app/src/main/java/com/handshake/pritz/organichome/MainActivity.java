package com.handshake.pritz.organichome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.b);
        Button button1=findViewById(R.id.c);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Admin.class);
                 startActivity(i);
            }
        });        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, East.class);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.con) {
            String number="07585910557";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
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

}
