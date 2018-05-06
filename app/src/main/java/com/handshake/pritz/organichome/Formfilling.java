package com.handshake.pritz.organichome;
import com.google.android.gms.common.api.Api;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.app.ProgressDialog;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

public class Formfilling extends AppCompatActivity implements PaytmPaymentTransactionCallback
{
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
                generateCheckSum();
            }
        });
    }


        private void generateCheckSum() {

            //getting the tax amount first.
            String txnAmount = textViewPrice.getText().toString().trim();

            //creating a retrofit object.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //creating the retrofit api service
            RTF apiService = retrofit.create(RTF.class);

            //creating paytm object
            //containing all the values required
            final Paytm paytm = new Paytm(
                    SyncStateContract.Constants.M_ID,
                    SyncStateContract.Constants.CHANNEL_ID,
                    txnAmount,
                    SyncStateContract.Constants.WEBSITE,
                    SyncStateContract.Constants.CALLBACK_URL,
                    SyncStateContract.Constants.INDUSTRY_TYPE_ID
            );

            //creating a call object from the apiService
            Call<Checksum> call = apiService.getChecksum(
                    paytm.getmId(),
                    paytm.getOrderId(),
                    paytm.getCustId(),
                    paytm.getChannelId(),
                    paytm.getTxnAmount(),
                    paytm.getWebsite(),
                    paytm.getCallBackUrl(),
                    paytm.getIndustryTypeId()
            );

            //making the call to generate checksum
            call.enqueue(new Callback<Checksum>() {
                @Override
                public void onResponse(Call<Checksum> call, Response<Checksum> response) {

                    //once we get the checksum we will initiailize the payment.
                    //the method is taking the checksum we got and the paytm object as the parameter
                    initializePaytmPayment(response.body().getChecksumHash(), paytm);
                }

                @Override
                public void onFailure(Call<Checksum> call, Throwable t) {

                }
            });
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
    private void initializePaytmPayment(String checksumHash, Paytm paytm) {

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService();

        //use this when using for production
        //PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", paytm.getOrderId());
        paramMap.put("CUST_ID", paytm.getCustId());
        paramMap.put("CHANNEL_ID", paytm.getChannelId());
        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
        paramMap.put("WEBSITE", paytm.getWebsite());
        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());


        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(this, true, true, this);

    }

    //all these overriden method is to detect the payment result accordingly

    @Override
    public void onTransactionResponse(Bundle bundle) {

        Toast.makeText(this, bundle.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();
    }
}




