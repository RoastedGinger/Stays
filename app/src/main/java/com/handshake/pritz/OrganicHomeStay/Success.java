package com.handshake.pritz.OrganicHomeStay;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Success extends AppCompatActivity {
    TextView a,b,c,d,e,f,g,h,i,j,k,l,m;
String aa,bb,cc,dd,ee,ff,gg,hh,ii,jj,kk,ll,mm;
    String url = "https://beholden-effects.000webhostapp.com/Registration_form/add_info1.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        final Bundle bundle = getIntent().getExtras();
        if (getIntent().getExtras() != null) {
            aa = bundle.getString("a");
            bb = bundle.getString("b");
            cc = bundle.getString("c");
            dd = bundle.getString("d");
            ee = bundle.getString("e");
            ff = bundle.getString("f");
            gg = bundle.getString("g");
            hh = bundle.getString("h");
            ii = bundle.getString("i");
            jj = bundle.getString("j");
            kk = bundle.getString("k");
            ll = bundle.getString("l");
            mm = bundle.getString("m");
        }
        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        e = findViewById(R.id.e);
        f = findViewById(R.id.f);
        g = findViewById(R.id.g);
        h = findViewById(R.id.h);
        i = findViewById(R.id.i);
        j = findViewById(R.id.j);
        k = findViewById(R.id.k);
        l = findViewById(R.id.l);
        m = findViewById(R.id.m);
    }
    protected void onStart() {
        super.onStart();
        a.setText(aa);
        b.setText(bb);
        c.setText(cc);
        d.setText(dd);
        e.setText(ee);
        f.setText(ff);
        g.setText(gg);
        h.setText(hh);
        i.setText(ii);
        j.setText(jj);
        k.setText(kk);
        l.setText(ll);
        m.setText(mm);
        send();

        Thread t =new Thread(){
            public void run(){
                try{
                    sleep(1500);
                    send();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent i =new Intent(Success.this,MainActivity.class);
                    startActivity(i);
                }
            }
        };
        t.start();
    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }


        public void send()
    {
        StringRequest request = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Success.this,response,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String voll = error.toString();
                Toast.makeText(Success.this,voll,Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("request","1" );
                paramMap.put("Data",aa+"\n"+bb+"\n"+cc+"\n"+dd+"\n"+ee+"\n"+ff+"\n"+gg+"\n"+hh+"\n"+ii+"\n"+jj+"\n"+kk+"\n"+ll+"\n"+mm);
                return paramMap;
            }
        };
        MySingleton.getInstance(Success.this).addToRequestQue(request);
    }

}
