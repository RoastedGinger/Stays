package com.handshake.pritz.organichome;
import android.app.DatePickerDialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
 import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.handshake.pritz.organichome.Form.DatePickerDialogFragment.getDaysDifference;


public class Form extends AppCompatActivity implements View.OnClickListener {
    private static Calendar calendar,calendar2;
   static TextView p, q,pop,pol;
   public static int jk,g;
  static int psprice,bigprice;
   Button pay;
     Spinner spinner;
    public static String a;
    private DatePickerDialogFragment mDatePickerDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        spinner = findViewById(R.id.spinner);


        p = findViewById(R.id.to);
        pol = findViewById(R.id.pol);
        pop = findViewById(R.id.ppric);
        pay=findViewById(R.id.Pay);
        Bundle bundle = getIntent().getExtras();
        final String price = bundle.getString("price");
        try {
              psprice= Integer.parseInt(price);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        pop.setText(price);

        pay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        g= getDaysDifference(calendar, calendar2);
        a= String.valueOf(g);
        pol.setText(a+"\tNights");

        pop.setText(String.valueOf(bigprice));
    }
});
        q = findViewById(R.id.from);
        p.setOnClickListener(this);
        q.setOnClickListener(this);
        mDatePickerDialogFragment = new DatePickerDialogFragment();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                try {
                    jk = Integer.parseInt(item);
                    bigprice=psprice*jk*g ;
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
                pop.setText(String.valueOf(bigprice));

                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                //tag = spinner.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("1");
        categories.add("2");
        categories.add("3");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



    }
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.to) {
                if(!mDatePickerDialogFragment.isAdded())
                {
                mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
            }} else if (id == R.id.from) {
                if(!mDatePickerDialogFragment.isAdded()){
                mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
            }}
        }

    public   static class DatePickerDialogFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener{
        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        private int flag = 0;

        public DatePickerDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }
            //  return new DatePickerDialog(getActivity(), this, year, month, day);        }

        public void setFlag(int i) {
            flag = i;
        }


        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            if (flag == FLAG_START_DATE) {
                calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                p.setText(format.format(calendar.getTime()));



            } else if (flag == FLAG_END_DATE) {
                calendar2 = Calendar.getInstance();
                calendar2.set(year, monthOfYear, dayOfMonth);
                q.setText(format.format(calendar2.getTime()));
            }

            g= getDaysDifference(calendar, calendar2);
            a= String.valueOf(g);
            pol.setText(a+"\tNights");
             bigprice=psprice*jk*g ;
            pop.setText(String.valueOf(bigprice));

        }

        public static int getDaysDifference(Calendar calendar1,Calendar calendar2)
        { long diff;
            if(calendar1==null||calendar2==null)
                return 1;
            if(calendar1.equals(calendar2))
                return 1;
            if (calendar2.equals(calendar1))
                return 1;
            else {
                diff = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
                float dayCount = (float) diff / (24 * 60 * 60 * 1000);
                return ((int) dayCount);
            }
        }
    }

    }
