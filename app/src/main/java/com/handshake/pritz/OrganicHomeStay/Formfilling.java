package com.handshake.pritz.OrganicHomeStay;
import android.app.ProgressDialog;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Formfilling extends AppCompatActivity  {
    TextView hid, hadr, hpr, hin, hout, noofpeople, noofroom, hemail, hphone, hname;
    ImageView Apic;
    private int randomInt = 0;
    private PaytmPGService Service = null;
    ProgressDialog progressDialog;
    String a, g, b, c, d, e, f, h, i, k, l;
    Button paynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formfilling);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        final Bundle bundle = getIntent().getExtras();
        if (getIntent().getExtras() != null) {
            a = bundle.getString("Price");
            //  a ="Total Amount:\t"+bundle.getString("Price")+"/-";
            b = "Name:\t" + bundle.getString("fullname");
            c = "Email:" + bundle.getString("fullemail");
            d = "No. of People:\t" + bundle.getString("fullpeople");
            e = "Mobile:\t" + bundle.getString("fullphone");
            f = "HomeStayName:\t" + bundle.getString("Aaname");
            g = "HomeStayAddress:\t" + bundle.getString("Aadress");
            h = bundle.getString("Apic");
            i = "No. of Room:\t" + bundle.getString("fullroom");
            k = "Check-In:\t" + bundle.getString("cin");
            l = "Check-Out:\t" + bundle.getString("cout");
        }

        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(1000000000);
        progressDialog = new ProgressDialog(this);
        Apic = findViewById(R.id.Apic);
        hid = findViewById(R.id.hid);
        hadr = findViewById(R.id.hadr);
        hpr = findViewById(R.id.hpr);
        hin = findViewById(R.id.hchin);
        hout = findViewById(R.id.hcout);
        hname = findViewById(R.id.hname);
        hphone = findViewById(R.id.hphone);
        hemail = findViewById(R.id.hemail);
        noofpeople = findViewById(R.id.noofpeople);
        noofroom = findViewById(R.id.noofroom);
        paynow = findViewById(R.id.Pay);
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            onStartTransaction(view);
            }
        });
    }
        @Override
        protected void onStart() {
            super.onStart();
            //initOrderId();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

 public void onStartTransaction(View view) {

    PaytmPGService Service = PaytmPGService.getStagingService();

    Map<String, String> paramMap = new HashMap<String, String>();

    // these are mandatory parameters

    paramMap.put("CALLBACK_URL"," https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=order767335678978");
    paramMap.put("CHANNEL_ID","WAP");
    paramMap.put("CHECKSUMHASH","Th1w2Aos1Mf1KLVElJdrQQg5XjylTmPZFG4c+gvbEPZBRDRqqhN6xweDcsjq/i2W2cXAPVROQHlEbo1yQiI5mhDHPIL7MLGu+TegO5czql4=");
    paramMap.put("CUST_ID","cust324567");
    paramMap.put("INDUSTRY_TYPE_ID","Retail");
    paramMap.put("MID","Organi11175565750452");
    paramMap.put("ORDER_ID","order767335678978");
    paramMap.put("TXN_AMOUNT","1");
    paramMap.put("WEBSITE","APPSTAGING");



    PaytmOrder Order = new PaytmOrder(paramMap);

		PaytmMerchant Merchant = new PaytmMerchant(
				"https://beholden-effects.000webhostapp.com/Paytm/generateChecksum.php",
				"https://beholden-effects.000webhostapp.com/Paytm/verifyChecksum.php");

     Service.initialize(Order,Merchant,null);


     Service.startPaymentTransaction(this, false, true, new PaytmPaymentTransactionCallback() {

         public void onTransactionSuccess(Bundle bundle) {


             Log.i("Success","onTransactionSuccess :"+bundle);
         }


         public void onTransactionFailure(String s, Bundle bundle) {
             Log.i("Failure", "onTransactionFailure " + s);
         }


         public void networkNotAvailable() {
             Log.i("Failure", "networkNotAvailable");
         }


         public void clientAuthenticationFailed(String s) {
             Log.i("Failure", "clientAuthenticationFailed " + s);
         }


         public void someUIErrorOccurred(String s) {
             Log.i("Failure", "someUIErrorOccurred " + s);
         }


         public void onErrorLoadingWebPage(int i, String s, String s1) {
             Log.i("Failure", "onErrorLoadingWebPage" + s + " " + s1);
         }
     });



 }
}