package com.handshake.pritz.OrganicHomeStay;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void AddUser()
    {   AlertDialog.Builder builder=null;
        AlertDialog dialog=null;
        LayoutInflater linf=LayoutInflater.from(this);
        View inflator=linf.inflate(R.layout.update,null);
        builder=new AlertDialog.Builder(this);
        builder.setTitle("Add New User");
        builder.setView(inflator);
        //new_usrname=(EditText)inflator.findViewById(R.id.new_usrname);
        //new_pass=(EditText)inflator.findViewById(R.id.new_pass);
        //rep_new_pass=(EditText)inflator.findViewById(R.id.rep_new_pass);
        builder.setPositiveButton("Cancel",null);
        builder.setNegativeButton("Create",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {

            }
        });
        dialog=builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //builder.show();
    }


}
