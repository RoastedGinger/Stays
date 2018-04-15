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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    EditText a, b, d;
    Spinner spinner;
    public ImageButton imageButton;
    Button button;
    String tag;
    private  FirebaseAuth mauth;
    private FirebaseUser currentuser;
    Uri img = null;
    String root;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference mStorageRefe = FirebaseStorage.getInstance().getReference();
    private ProgressDialog progressDialog;
     DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mauth= FirebaseAuth.getInstance();

        if(getIntent().getExtras()!=null) {
            Bundle bundle = getIntent().getExtras();
             root = bundle.getString("keyroot");
            mref = FirebaseDatabase.getInstance().getReference().child(root);
        }

        currentuser=mauth.getCurrentUser();

         spinner = (Spinner) findViewById(R.id.spinner);
        a =  findViewById(R.id.name);
       b =  findViewById(R.id.Haddress);
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
                if (d.getText().toString().trim().equalsIgnoreCase("")) {
                    d.setError("This field can not be blank");
                }
                final String name = a.getText().toString();
                final String jaddress = b.getText().toString();
                final String price = d.getText().toString();


                if ((img != null && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(jaddress) && !TextUtils.isEmpty(price))) {
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
                            databaseReference.child("Homeaddress").setValue(jaddress);
                            databaseReference.child("Price").setValue(price);
                            databaseReference.child("Effi").setValue(tag);
                            databaseReference.child("postid").setValue(currentuser.getUid());
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

