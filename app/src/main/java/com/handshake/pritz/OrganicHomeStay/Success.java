package com.handshake.pritz.OrganicHomeStay;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Success extends AppCompatActivity {
String order;
TextView orderide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        final Bundle bundle = getIntent().getExtras();
        if (getIntent().getExtras() != null) {
            order=bundle.getString("Response");

        }
        orderide=findViewById(R.id.orderid);
        orderide.setText(order);
    }
}
