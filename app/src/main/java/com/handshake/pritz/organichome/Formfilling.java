package com.handshake.pritz.organichome;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Formfilling extends AppCompatActivity {
TextView hid,hadr,hpr,hin,hout,noofpeople,noofroom,hemail,hphone,hname;
ImageView Apic;
ProgressDialog progressDialog;
    String a,g,b,c,d,e,f,h,i,k,l;
    Button paynow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formfilling);
        final Bundle bundle = getIntent().getExtras();
        if(getIntent().getExtras()!=null) {
            a=bundle.getString("Price");
          //  a ="Total Amount:\t"+bundle.getString("Price")+"/-";
            b ="Name:\t"+bundle.getString("fullname");
            c ="Email:"+bundle.getString("fullemail");
            d = "No. of People:\t"+bundle.getString("fullpeople");
            e ="Mobile:\t"+bundle.getString("fullphone");
            f ="HomeStayName:\t"+bundle.getString("Aaname");
            g ="HomeStayAddress:\t"+bundle.getString("Aadress");
            h = bundle.getString("Apic");
            i ="No. of Room:\t"+bundle.getString("fullroom");
            k="Check-In:\t"+bundle.getString("cin");
            l="Check-Out:\t"+bundle.getString("cout");
        }
        progressDialog=new ProgressDialog(this);
        Apic=findViewById(R.id.Apic);
        hid=findViewById(R.id.hid);
        hadr=findViewById(R.id.hadr);
        hpr=findViewById(R.id.hpr);
        hin=findViewById(R.id.hchin);
        hout=findViewById(R.id.hcout);
        hname=findViewById(R.id.hname);
        hphone=findViewById(R.id.hphone);
        hemail=findViewById(R.id.hemail);
        noofpeople=findViewById(R.id.noofpeople);
        noofroom=findViewById(R.id.noofroom);
        paynow=findViewById(R.id.Pay);
        //This is the place to hit payTM payment gateway//
        /*paynow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Formfilling.this, PayMentGateWay.class);//here you have to add paytm activity//
        intent.putExtra("FIRST_NAME",b);
        intent.putExtra("PHONE_NUMBER",e);
        intent.putExtra("EMAIL_ADDRESS",c);
        intent.putExtra("RECHARGE_AMT",a);
        startActivity(intent);*
    }
});*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Checking Availability...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        hid.setText(f);
        hadr.setText(g);
        hpr.setText(a);
        hin.setText(k);
        hout.setText(l);
        hname.setText(b);
        hemail.setText(c);
        noofroom.setText(i);
        noofpeople.setText(d);
        hphone.setText(e);
// Hide progress bar on successful load
        Picasso.with(Formfilling.this).load(h)
                .into(Apic, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError() {

                    }
                });

    }
}
