package com.handshake.pritz.organichome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    EditText a, b, c, d;
    Spinner spinner;
    public ImageButton imageButton;
    Button button;
    String tag;
    Uri img = null;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference mStorageRefe = FirebaseStorage.getInstance().getReference();
    private ProgressDialog progressDialog;
    final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("EastHomestays");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        // Spinner element
         spinner = (Spinner) findViewById(R.id.spinner);
        a =  findViewById(R.id.name);
       b =  findViewById(R.id.address);
        c =   findViewById(R.id.pin);
        d =   findViewById(R.id.price);
        imageButton =  findViewById(R.id.hotelpic);
        button =   findViewById(R.id.send);
        progressDialog = new ProgressDialog(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent, GALLERY_REQUEST);
            }
        });

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();

                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                tag = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Good");
        categories.add("Very Good");
        categories.add("Excellent");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Just Wait...\n While we Uploading it.");

                if (a.getText().toString().trim().equalsIgnoreCase("")) {
                    a.setError("This field can not be blank");
                }
                if (b.getText().toString().trim().equalsIgnoreCase("")) {
                    b.setError("This field can not be blank");
                }
                if (c.getText().toString().trim().equalsIgnoreCase("")) {
                    c.setError("This field can not be blank");
                }
                if (d.getText().toString().trim().equalsIgnoreCase("")) {
                    d.setError("This field can not be blank");
                }
                final String name = a.getText().toString();
                final String address = b.getText().toString();
                final String price = c.getText().toString();
                final String pin = d.getText().toString();

                if ((img != null && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(pin))) {
                   progressDialog.setMessage("Uploading Please Wait...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    StorageReference reference=mStorageRefe.child(img.getLastPathSegment());
                    reference.putFile(img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                          Uri downloaduri=taskSnapshot.getDownloadUrl();
                          DatabaseReference databaseReference=mref.push();

                          databaseReference.child("Name").setValue(name);
                            databaseReference.child("Address").setValue(address+"\t"+pin);
                            databaseReference.child("Price").setValue(price);
                            databaseReference.child("Effi").setValue(tag);
                            databaseReference.child("HomestayPic").setValue(downloaduri.toString());
                            progressDialog.dismiss();
                            Intent i=new Intent(Admin.this,MainActivity.class);
                            finish();
                            startActivity(i);

                        }
                    });

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageurl = data.getData();
            CropImage.activity(imageurl)
                    .setGuidelines(CropImageView.Guidelines.ON)
                     .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                img = result.getUri();
                imageButton.setImageURI(img);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}

