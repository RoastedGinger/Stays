package com.handshake.pritz.OrganicHomeStay;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Formfilling extends AppCompatActivity {
    TextView hid, hadr, hpr, hin, hout, noofpeople, noofroom, hemail, hphone, hname;
    ImageView Apic;
    private int randomInt = 0;
    private PaytmPGService Service = null;
    ProgressDialog progressDialog;
    String a, g, b, c, d, e, f, h, we, k, l,U_ID="123",checksum;
    public static final String MID = "Organi97915915986368";
    public static final String INDUSTRY_TYPE_ID = "Retail109";
    public static final String CHANNEL_ID = "WAP";
    public static final String WEBSITE= "APPPROD";
    public static final String CALLBACK_URL = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=ORD067878";
    Button paynow;
    String orderID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formfilling);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final Bundle bundle = getIntent().getExtras();
        if (getIntent().getExtras() != null) {
            a = bundle.getString("Price");
            b = bundle.getString("fullname");
            c = bundle.getString("fullemail");
            d = bundle.getString("fullpeople");
            e = bundle.getString("fullphone");
            f = bundle.getString("Aaname");
            g = bundle.getString("Aadress");
            h = bundle.getString("Apic");
            we = bundle.getString("fullroom");
            k = bundle.getString("cin");
            l = bundle.getString("cout");
        }
        progressDialog = new ProgressDialog(this);
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
        progressDialog = new ProgressDialog(this);

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Breathe in Breathe out!");
                progressDialog.show();
                progressDialog.setCancelable(false);
                Random randomGenerator = new Random();
                randomInt = randomGenerator.nextInt(1000000000);
                orderID= String.valueOf(randomInt);
                Generatechecksum();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void Generatechecksum() {
        String url = "https://www.sikkimprepaidtaxi.com/organichome1/checksum.php";
            StringRequest request = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            checksum = response;
                            Toast.makeText(Formfilling.this,response,Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            onStartTransaction();
                         }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String voll = error.toString();
                    Toast.makeText(Formfilling.this,voll,Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> paramMap = new HashMap<>();
                    paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderID);
                    paramMap.put("CHANNEL_ID", CHANNEL_ID);
                    paramMap.put("CUST_ID", e);
                    paramMap.put("INDUSTRY_TYPE_ID", INDUSTRY_TYPE_ID);
                    paramMap.put("MID", MID);
                    paramMap.put("ORDER_ID", orderID);
                    paramMap.put("TXN_AMOUNT", a);
                    paramMap.put("WEBSITE", WEBSITE);
                    paramMap.put("EMAIL", c);
                    paramMap.put("MOBILE_NO", e);
                    return paramMap;
                }
            };
            MySingleton.getInstance(Formfilling.this).addToRequestQue(request);

    }

    protected void onStart() {
        super.onStart();
       hid.setText("Homestay Name:\t"+f);
       hadr.setText("Homestay Address:\t"+g);
        hpr.setText("Homestay Price:\t"+a);
        hin.setText("Check In:\t"+k);
        hout.setText("Check Out:\t"+l);
        hname.setText("Name:\t"+b);
        hphone.setText("Phone:\t"+e);
        hemail.setText("Email:\t"+c);
        noofpeople.setText("No of People:\t"+d);
        noofroom.setText("No.of Room:\t"+we);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    public void onStartTransaction (){
        String url = "https://www.sikkimprepaidtaxi.com/organichome1/checksum.php";
        PaytmPGService Service = PaytmPGService.getProductionService();
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderID);
        paramMap.put("CHANNEL_ID",CHANNEL_ID);
        paramMap.put("CHECKSUMHASH", checksum);
        paramMap.put("CUST_ID",e);
        paramMap.put("INDUSTRY_TYPE_ID",INDUSTRY_TYPE_ID);
        paramMap.put("MID",MID);
        paramMap.put("ORDER_ID",orderID);
        paramMap.put("TXN_AMOUNT",a);
        paramMap.put("WEBSITE",WEBSITE);
        paramMap.put("EMAIL",c);
        paramMap.put("MOBILE_NO",e);
        JSONObject jsonObject = new JSONObject(paramMap);
        PaytmOrder Order = new PaytmOrder(paramMap);
        Service.initialize(Order, null);

        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
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

                            String p=inResponse.getString("STATUS");
                            if(p.equals("TXN_SUCCESS"))
                            {  Toast.makeText(getApplicationContext(), "Payment Transaction is Successful" + inResponse.toString(), Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Formfilling.this,Success.class);
                                i.putExtra("a","Name:"+b);
                                i.putExtra("b","Address:\t"+g);
                                i.putExtra("c","Homestay Price:\t"+a);
                                i.putExtra("d","Check In:\t"+k);
                                i.putExtra("e","Check Out:\t"+l);
                                i.putExtra("f","Home Stay Name:\t"+f);
                                i.putExtra("g","Phone:\t"+e);
                                i.putExtra("h","Email:\t"+c);
                                i.putExtra("i","No of People:\t"+d);
                                i.putExtra("j","No.of Room:\t"+we);
                                i.putExtra("k","Order ID:\t"+orderID);
                                i.putExtra("l","Customer ID:\t"+b);
                                i.putExtra("m","Response from Paytm:"+inResponse.toString());
                                startActivity(i);

                            }
                            else
                            { Toast.makeText(getApplicationContext(), "Payment Transaction is Unsussessful" + inResponse.toString(), Toast.LENGTH_LONG).show();
                            }




                    }

                    @Override
                    public void networkNotAvailable() {
                        Toast.makeText(getApplicationContext(), "Check Your Internet Connection!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        Toast.makeText(getApplicationContext(), "Authentication Failure", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {
                        Toast.makeText(getApplicationContext(), "Error Loading Payment Page", Toast.LENGTH_LONG).show();

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