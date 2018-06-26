package com.handshake.pritz.OrganicHomeStay;
import android.app.ProgressDialog;
//import android.provider.SyncStateContract;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
//import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;*/

public class Formfilling extends AppCompatActivity {
    TextView hid, hadr, hpr, hin, hout, noofpeople, noofroom, hemail, hphone, hname;
    ImageView Apic;
    private int randomInt = 0;
    private PaytmPGService Service = null;
    ProgressDialog progressDialog;
    String a, g, b, c, d, e, f, h, i, k, l;
    Button paynow;
    String polpepl;

    //String uniqueID = UUID.randomUUID().toString();
    //String orderID = "uniqueID"+"randomInt"
    @Override
    protected void onCreate(Boundle savedInstanceState) {
   // https://stackoverflow.com/questions/37093723/how-to-add-an-android-studio-project-to-github    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formfilling);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
       /* final Bundle bundle = getIntent().getExtras();
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
        }*/

        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(1000000000);
        progressDialog = new ProgressDialog(this);
       /* Apic = findViewById(R.id.Apic);
        hid = findViewById(R.id.hid);
        hadr = findViewById(R.id.hadr);
        hpr = findViewById(R.id.hpr);
        hin = findViewById(R.id.hchin);
        hout = findViewById(R.id.hcout);
        hname = findViewById(R.id.hname);
        hphone = findViewById(R.id.hphone);
        hemail = findViewById(R.id.hemail);
        noofpeople = findViewById(R.id.noofpeople);
        noofroom = findViewById(R.id.noofroom);*/
        paynow = findViewById(R.id.Pay);

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartTransaction(view);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void onStartTransaction (View view){
        PaytmPGService Service = PaytmPGService.getProductionService();
        HashMap<String, String> paramMap = new HashMap<String, String>();

        // these are mandatory parameters

        paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=ORD09936");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("CHECKSUMHASH", "R40zrTkkVAAba3in0moK4Io+vBxw86huWfVCY01zwTvTovllSppJzYYk1PgBICu76TP8O1iSC9RHvQbMMiOZM8gYNaqS2hEnIMGyD3iHnoI=");
        paramMap.put("CUST_ID", "CUST12345");
        paramMap.put("INDUSTRY_TYPE_ID", "Retail109");
        paramMap.put("MID", "Organi97915915986368");
        paramMap.put("ORDER_ID", "ORD09936");
        paramMap.put("TXN_AMOUNT", "1");
        paramMap.put("WEBSITE", "APPPROD");
        paramMap.put("EMAIL", "ajpcvs@gmail.com");
        paramMap.put("MOBILE_NO", "9462838364");


        PaytmOrder Order = new PaytmOrder(paramMap);

        /*PaytmMerchant Merchant = new PaytmMerchant(
                "https://beholden-effects.000webhostapp.com/organichome/generateChecksum.php",
                "https://beholden-effects.000webhostapp.com/organichome/verifyChecksum.php");*/

        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }

					/*@Override
					public void onTransactionSuccess(Bundle inResponse) {
						// After successful transaction this method gets called.
						// // Response bundle contains the merchant response
						// parameters.
						Log.d("LOG", "Payment Transaction is successful " + inResponse);
						Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onTransactionFailure(String inErrorMessage,
							Bundle inResponse) {
						// This method gets called if transaction failed. //
						// Here in this case transaction is completed, but with
						// a failure. // Error Message describes the reason for
						// failure. // Response bundle contains the merchant
						// response parameters.
						Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
						Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
					}*/

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }


                    @Override
                    public void onBackPressedCancelTransaction() {
                        Toast.makeText(Formfilling.this, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }
}